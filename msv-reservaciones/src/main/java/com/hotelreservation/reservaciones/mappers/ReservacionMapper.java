package com.hotelreservation.reservaciones.mappers;

import com.hotelreservation.commons.dto.habitaciones.DatosHabitacion;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.commons.dto.huespedes.HuespedResponse;
import com.hotelreservation.commons.dto.huespedes.DatosHuesped;
import com.hotelreservation.commons.dto.reservaciones.ReservacionRequest;
import com.hotelreservation.commons.dto.reservaciones.ReservacionResponse;
import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.enums.EstadoReserva;
import com.hotelreservation.commons.mappers.CommonMapper;
import com.hotelreservation.commons.utils.StringCustomUtils;
import com.hotelreservation.reservaciones.entities.Reservacion;
import org.springframework.stereotype.Component;

@Component
public class ReservacionMapper implements CommonMapper<ReservacionRequest, ReservacionResponse, Reservacion> {

    @Override
    public Reservacion requestAEntidad(ReservacionRequest request) {
        if (request == null) return null;

        return Reservacion.builder()
                .fechaEntrada(StringCustomUtils.stringToLocalDate(request.fechaEntrada()))
                .fechaSalida(StringCustomUtils.stringToLocalDate(request.fechaSalida()))
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    public Reservacion requestAEntidad(ReservacionRequest request, HabitacionResponse habitacionResponse, HuespedResponse huespedResponse) {
        if (request == null) return null;
        Reservacion reservacion = requestAEntidad(request);
        reservacion.setIdHabitacion(habitacionResponse.id());
        reservacion.setIdHuesped(huespedResponse.id());
        return reservacion;
    }

    @Override
    public ReservacionResponse entidadARespuesta(Reservacion entity) {
        if (entity == null) return null;

        return new ReservacionResponse(
                entity.getId(),
                null,
                null,
                entity.getEstadoReserva().getDescripcion(),
                StringCustomUtils.localDateToString(entity.getFechaEntrada()),
                StringCustomUtils.localDateToString(entity.getFechaSalida())
        );
    }

    public ReservacionResponse entidadARespuesta(Reservacion entity, HabitacionResponse habitacion, HuespedResponse huespedResponse) {
        if (entity == null) return null;

        return new ReservacionResponse(
                entity.getId(),
                habitacionARespuesta(habitacion),
                huespedARespuesta(huespedResponse),
                entity.getEstadoReserva().getDescripcion(),
                StringCustomUtils.localDateToString(entity.getFechaEntrada()),
                StringCustomUtils.localDateToString(entity.getFechaSalida())
        );
    }

    public DatosHabitacion habitacionARespuesta(HabitacionResponse habitacion) {
        if (habitacion == null) return null;

        return new DatosHabitacion(
                habitacion.id(),
                habitacion.numero(),
                habitacion.tipo(),
                habitacion.precio(),
                habitacion.capacidad()
        );
    }

    public DatosHuesped huespedARespuesta(HuespedResponse huesped) {
        if (huesped == null) return null;

        return new DatosHuesped(
                huesped.id(),
                String.join(" ",
                        huesped.nombre(),
                        huesped.apellidoPaterno(),
                        huesped.apellidoMaterno()
                ),
                huesped.email(),
                huesped.telefono(),
                huesped.documento(),
                huesped.nacionalidad()
        );
    }
}
