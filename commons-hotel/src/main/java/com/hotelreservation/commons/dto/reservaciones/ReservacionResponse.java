package com.hotelreservation.commons.dto.reservaciones;

import com.hotelreservation.commons.dto.habitaciones.DatosHabitacion;
import com.hotelreservation.commons.dto.huespedes.DatosHuesped;

public record ReservacionResponse(
        Long id,
        DatosHabitacion habitacion,
        DatosHuesped huesped,
        String estado,
        String fechaInicial,
        String fechaFinal
) {
}
