package com.hotelreservation.habitaciones.services;

import com.hotelreservation.commons.dto.habitaciones.HabitacionRequest;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.commons.services.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
    HabitacionResponse buscarPorIdIgnorandoEstadoRegistro(Long id);
    void actualizarEstadoHabitacion(Long id, Integer idEstadoHabitacion);
}
