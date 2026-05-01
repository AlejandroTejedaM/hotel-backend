package com.hotelreservation.habitaciones.services;

import com.hotelreservation.commons.client.ReservaClient;
import com.hotelreservation.commons.dto.habitaciones.HabitacionRequest;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.commons.enums.EstadoHabitacion;
import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.exceptions.RecursoNoEncontradoException;
import com.hotelreservation.habitaciones.entities.Habitacion;
import com.hotelreservation.habitaciones.mappers.HabitacionMapper;
import com.hotelreservation.habitaciones.repositories.HabitacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final ReservaClient reservaClient;

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse buscarPorIdIgnorandoEstadoRegistro(Long id) {
        return habitacionMapper.entidadARespuesta(findByIdOrException(id));
    }

    @Override
    public void actualizarEstadoHabitacion(Long id, Integer idEstadoHabitacion) {
        log.info("Actualizando el estado de la habitación con id: {} a: {}", id, idEstadoHabitacion);
        Habitacion habitacion = findActiveByIdOrException(id);
        EstadoHabitacion estadoAnterior = habitacion.getEstadoHabitacion();
        EstadoHabitacion estadoNuevo = EstadoHabitacion.encontrarPorCodigo(idEstadoHabitacion);
        if (estadoNuevo == EstadoHabitacion.DISPONIBLE) {
            this.confirmRoomStatusToSetItAvailable(id);
        }
        habitacion.cambiarEstadoHabitacion(estadoNuevo);
        log.info("Se a actualizado el estado de la habitación con id: {} de: {} a: {}", id, estadoAnterior, habitacion.getEstadoHabitacion());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> list() {
        log.info("Listando habitaciones");
        return habitacionRepository.findAllByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(habitacionMapper::entidadARespuesta).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse encontrarPorId(Long id) {
        return habitacionMapper.entidadARespuesta(findActiveByIdOrException(id));
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando una nueva habitación");
        validarNumeroDeHabitacionUnico(request.numero());
        Habitacion habitacion = habitacionMapper.requestAEntidad(request);
        habitacionRepository.save(habitacion);
        log.info("Habitación registrada con id: {} y número: {}", habitacion.getId(), habitacion.getNumero());
        return habitacionMapper.entidadARespuesta(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        log.info("Actualizando habitación con id: {}", id);
        Habitacion habitacion = findActiveByIdOrException(id);
        verifyUniqueRoomNumberOnUpdate(request, id);
        habitacion.actualizar(
                request.numero(),
                request.tipo(),
                request.precio(),
                request.capacidad()
        );
        return habitacionMapper.entidadARespuesta(habitacion);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando a la habitación co  id: {}", id);
        Habitacion habitacion = findActiveByIdOrException(id);
        if (habitacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA) {
            throw new IllegalStateException("No se puede eliminar una habitación OCUPADA");
        }
        habitacion.eliminar();
        log.info("Habitación número {} eliminada con id: {}", habitacion.getEstadoHabitacion(), habitacion.getId());
    }

    public Habitacion findByIdOrException(Long id) {
        return habitacionRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("No se a encontrado a la habitación con id: " + id)
        );
    }

    public Habitacion findActiveByIdOrException(Long id) {
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
                () -> new RecursoNoEncontradoException("No se a encontrado a la habitación con id: " + id)
        );
    }

    public void validarNumeroDeHabitacionUnico(Integer numero) {
        log.info("Validando que el número de habitación: {} sea único", numero);
        if (habitacionRepository.existsByNumeroAndEstadoRegistro(numero, EstadoRegistro.ACTIVO)) {
            throw new IllegalArgumentException("El número de habitación: " + numero + " ya existe");
        }
    }

    public void verifyUniqueRoomNumberOnUpdate(HabitacionRequest request, Long id) {
        log.info("Validando que el número de habitación: {} sea único durante una actualización", request.numero());
        if (habitacionRepository.existsByNumeroAndEstadoRegistroAndIdNot(request.numero(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalArgumentException("El número de habitación: " + request.numero() + " ya existe");
        }
    }

    private void confirmRoomStatusToSetItAvailable(Long idHabitacion) {
        if (reservaClient.isRoomBooked(idHabitacion)) {
            throw new IllegalStateException("No puede volver a disponible, esta vinculada a una (Reserva creada o una Reserva con Check-in)");
        }
    }
}
