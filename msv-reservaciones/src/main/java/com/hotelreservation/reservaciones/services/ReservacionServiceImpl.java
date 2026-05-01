package com.hotelreservation.reservaciones.services;

import com.hotelreservation.commons.client.HabitacionClient;
import com.hotelreservation.commons.client.HuespedClient;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.commons.dto.huespedes.HuespedResponse;
import com.hotelreservation.commons.dto.reservaciones.ReservacionRequest;
import com.hotelreservation.commons.dto.reservaciones.ReservacionResponse;
import com.hotelreservation.commons.enums.EstadoHabitacion;
import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.enums.EstadoReserva;
import com.hotelreservation.commons.exceptions.RecursoNoEncontradoException;
import com.hotelreservation.commons.utils.StringCustomUtils;
import com.hotelreservation.reservaciones.entities.Reservacion;
import com.hotelreservation.reservaciones.mappers.ReservacionMapper;
import com.hotelreservation.reservaciones.repositories.ReservacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReservacionServiceImpl implements ReservacionService {
    private final ReservacionRepository reservacionRepository;
    private final ReservacionMapper reservacionMapper;
    private final HabitacionClient habitacionClient;
    private final HuespedClient huespedClient;

    @Override
    @Transactional(readOnly = true)
    public List<ReservacionResponse> list() {
        log.info("Listando reservaciones");
        return reservacionRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(reservacion -> {
                    HabitacionResponse habitacionResponse = findHabitacionById(reservacion.getIdHabitacion());
                    HuespedResponse huespedResponse = findHuespedById(reservacion.getIdHuesped());
                    return reservacionMapper.entidadARespuesta(reservacion, habitacionResponse, huespedResponse);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservacionResponse encontrarPorId(Long id) {
        log.info("Buscando reservación con id: {}", id);
        Reservacion reservacion = findActiveByIdOrException(id);
        HabitacionResponse habitacionResponse = findHabitacionById(reservacion.getIdHabitacion());
        HuespedResponse huespedResponse = findHuespedById(reservacion.getIdHuesped());
        return reservacionMapper.entidadARespuesta(reservacion, habitacionResponse, huespedResponse);
    }

    @Override
    public ReservacionResponse registrar(ReservacionRequest request) {
        log.info("Registrando una nueva reservación...");
        StringCustomUtils.validarFechasReservacion(request.fechaEntrada(), request.fechaSalida());
        HuespedResponse huespedResponse = findActiveHuespedById(request.idHuesped());
        HabitacionResponse habitacionResponse = findHabitacionActivaById(request.idHabitacion());
        ensureHabitacionIsAvailable(habitacionResponse);

        Reservacion reservacion = reservacionMapper.requestAEntidad(request, habitacionResponse, huespedResponse);
        reservacionRepository.save(reservacion);

        changeEstadoHabitacion(habitacionResponse.id(), EstadoHabitacion.OCUPADA);
        log.info("Habitación apartada por la reservación con id: {}", reservacion.getId());
        return reservacionMapper.entidadARespuesta(reservacion, habitacionResponse, huespedResponse);
    }

    @Override
    public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
        log.info("Actualizando reservación con id: {}", id);
        Reservacion reservacion = findActiveByIdOrException(id);
        LocalDate fechaEntradaActual = reservacion.getFechaEntrada();
        HabitacionResponse habitacionOriginal = findHabitacionActivaById(reservacion.getIdHabitacion());
        HuespedResponse huespedOriginal = findActiveHuespedById(reservacion.getIdHuesped());

        LocalDate fechaEntrada = StringCustomUtils.stringToLocalDate(request.fechaEntrada());
        LocalDate fechaSalida = StringCustomUtils.stringToLocalDate(request.fechaSalida());
        HuespedResponse huespedActualizado = null;
        HabitacionResponse habitacionActualizada = null;
        switch (reservacion.getEstadoReserva()) {
            case CONFIRMADA -> {
                StringCustomUtils.validarFechasReservacion(request.fechaEntrada(), request.fechaSalida());
                if (request.idHuesped() != huespedOriginal.idHuesped()) {
                    huespedActualizado = findActiveHuespedById(request.idHuesped());
                    reservacion.changeHuesped(huespedActualizado.idHuesped());
                }

                if (request.idHabitacion() != habitacionOriginal.id()) {
                    habitacionActualizada = findHabitacionActivaById(request.idHabitacion());
                    ensureHabitacionIsAvailable(habitacionActualizada);
                    changeEstadoHabitacion(habitacionOriginal.id(), EstadoHabitacion.DISPONIBLE);
                    changeEstadoHabitacion(habitacionActualizada.id(), EstadoHabitacion.OCUPADA);
                    reservacion.changeHabitacion(request.idHabitacion());
                }

                reservacion.changeFechaEntradaYSalida(fechaEntrada, fechaSalida);
            }
            case EN_CURSO -> {
                if (!fechaEntradaActual.isEqual(fechaEntrada)) {
                    throw new IllegalArgumentException("No se puede modificar la fecha de entrada después de Check-In");
                }
                StringCustomUtils.validarFechasReservacion(StringCustomUtils.localDateToString(fechaEntradaActual), StringCustomUtils.localDateToString(fechaSalida));
                reservacion.changeFechaSalida(fechaSalida);
            }
            case FINALIZADA, CANCELADA ->
                    throw new IllegalStateException("No se pueden modificar reservaciones: " + List.of(EstadoReserva.CANCELADA, EstadoReserva.FINALIZADA));
        }
        log.info("Reservación con id: {} para habitación número: {} actualizada", id, habitacionOriginal.numero());
        return reservacionMapper.entidadARespuesta(reservacion,
                habitacionActualizada != null ? habitacionActualizada : habitacionOriginal,
                huespedActualizado != null ? huespedActualizado : huespedOriginal
        );
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando reservación con id: {}", id);
        Reservacion reservacion = findActiveByIdOrException(id);
        reservacion.delete();
    }

    @Override
    public void changeEstadoReserva(Long idReserva, Integer idEstado) {
        EstadoReserva estado = EstadoReserva.encontrarPorCodigo(idEstado);
        log.info("Cambiando el estado de la reservación con id: {} a: {}", idReserva, estado);
        Reservacion reservacion = findActiveByIdOrException(idReserva);
        updateRoomAvailabilityByEstadoReservacion(idReserva, estado, reservacion.getIdHabitacion());
        reservacion.changeEstado(estado);
        log.info("Estado de la reservación con id: {} cambiado a: {}", idReserva, estado);
    }

    @Override
    public Boolean tieneReservacionesPorIdHuespedYEstadoReserva(Long idHuesped, Integer idEstado) {
        log.info("Buscando reservaciones por idHuesped: {} y estadoReserva: {}", idHuesped, idEstado);
        EstadoReserva estado = EstadoReserva.encontrarPorCodigo(idEstado);
        return reservacionRepository.existsByIdHuespedAndEstadoReserva(idHuesped, estado);
    }

    public Reservacion findActiveByIdOrException(Long id) {
        return reservacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reservación no encontrada con id: " + id));
    }

    public HuespedResponse findHuespedById(Long id) {
        return huespedClient.findById(id);
    }

    public HuespedResponse findActiveHuespedById(Long id) {
        return huespedClient.findActiveById(id);
    }

    public HabitacionResponse findHabitacionActivaById(Long id) {
        return habitacionClient.findHabitacionActivaById(id);
    }

    public HabitacionResponse findHabitacionById(Long id) {
        return habitacionClient.findHabitacionById(id);
    }

    public void ensureHabitacionIsAvailable(HabitacionResponse habitacionResponse) {
        if (!EstadoHabitacion.encontrarPorDescripcion(habitacionResponse.estado()).equals(EstadoHabitacion.DISPONIBLE)) {
            throw new IllegalStateException("La habitación debe estar disponible");
        }
    }

    public void changeEstadoHabitacion(Long idHabitacion, EstadoHabitacion estadoHabitacion) {
        log.info("Cambiando el estado de la habitación con id: {} a: {}", idHabitacion, estadoHabitacion);
        habitacionClient.actualizarEstadoHabitacion(idHabitacion, estadoHabitacion.getCodigo());
    }

    public void updateRoomAvailabilityByEstadoReservacion(Long idReservacion, EstadoReserva estadoNuevo, Long idHabitacion) {
        Reservacion reservacion = findActiveByIdOrException(idReservacion);
        EstadoReserva estadoAnterior = reservacion.getEstadoReserva();

        switch (estadoNuevo) {
            case CONFIRMADA -> {
                if (estadoAnterior == EstadoReserva.EN_CURSO || estadoAnterior == EstadoReserva.FINALIZADA) {
                    throw new IllegalStateException("Una reservación no puede volver a confirmada una vez realizado Check-in o Check-out");
                }
                if (estadoAnterior == EstadoReserva.CANCELADA) {
                    throw new IllegalStateException("Una reservación cancelada no puede volver a ser confirmada");
                }
                changeEstadoHabitacion(idHabitacion, EstadoHabitacion.DISPONIBLE);
            }
            case EN_CURSO -> {
                if (!estadoAnterior.equals(EstadoReserva.CONFIRMADA)) {
                    throw new IllegalStateException("Solo se puede hacer Check-in desde:" + EstadoReserva.CONFIRMADA);
                }
                changeEstadoHabitacion(idHabitacion, EstadoHabitacion.OCUPADA);
            }
            case FINALIZADA -> {
                if (estadoAnterior != EstadoReserva.EN_CURSO) {
                    throw new IllegalStateException("Solo se puede hacer check-out desde: " + EstadoReserva.EN_CURSO);
                }
                changeEstadoHabitacion(idHabitacion, EstadoHabitacion.DISPONIBLE);

            }
            case CANCELADA -> {
                if (estadoAnterior != EstadoReserva.CONFIRMADA) {
                    throw new IllegalStateException("Solo se puede cancelar desde: " + EstadoReserva.CONFIRMADA);
                }
                changeEstadoHabitacion(idHabitacion, EstadoHabitacion.DISPONIBLE);
            }
        }
    }
}
