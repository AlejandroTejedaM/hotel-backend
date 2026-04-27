package com.hotelreservation.habitaciones.mappers;

import com.hotelreservation.commons.dto.habitaciones.HabitacionRequest;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.commons.enums.EstadoHabitacion;
import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.mappers.CommonMapper;
import com.hotelreservation.habitaciones.entities.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {
    @Override
    public Habitacion requestAEntidad(HabitacionRequest request) {
        if (request == null) return null;

        return Habitacion.builder()
                .numero(request.numero())
                .tipo(request.tipo())
                .precio(request.precio())
                .capacidad(request.capacidad())
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    @Override
    public HabitacionResponse entidadARespuesta(Habitacion entity) {
        if (entity == null) return null;

        return new HabitacionResponse(
                entity.getId(),
                entity.getNumero(),
                entity.getTipo(),
                entity.getPrecio(),
                entity.getCapacidad()
        );
    }
}
