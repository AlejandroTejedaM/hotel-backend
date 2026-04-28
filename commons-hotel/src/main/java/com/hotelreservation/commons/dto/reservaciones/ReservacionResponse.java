package com.hotelreservation.commons.dto.reservaciones;

import com.hotelreservation.commons.dto.habitaciones.DatosHabitacion;
import com.hotelreservation.commons.enums.EstadoReserva;

public record ReservacionResponse(
        Long id,
        DatosHabitacion habitacion,
        //TODO: Respuesta de huesped,
        String estado,
        String fechaInicial,
        String fechaFinal
) {
}
