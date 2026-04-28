package com.hotelreservation.commons.dto.habitaciones;

import java.math.BigDecimal;

public record HabitacionResponse(

        Long id,
        Integer numero,
        String tipo,
        BigDecimal precio,
        Short capacidad,
        String estado
) {
}