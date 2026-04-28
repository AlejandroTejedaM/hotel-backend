package com.hotelreservation.commons.dto.reservaciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ReservacionRequest(
        @NotNull(message = "El id del huesped es requerido")
        @Positive(message = "El id del huesped debe ser positivo")
        Long idHuesped,

        @NotNull(message = "El id de la habitación es requerido")
        @Positive(message = "El id de la habitación debe ser positivo")
        Long idHabitacion,

        @NotNull(message = "La fecha de entrada es requerida")
        @Pattern(
                regexp = "^\\d{2}/\\d{2}/\\d{4}$",
                message = "La fecha de entrada debe tener el formato dd/MM/yyyy"
        )
        String fechaEntrada,

        @NotNull(message = "La fecha de salida es requerida")
        @Pattern(
                regexp = "^\\d{2}/\\d{2}/\\d{4}$",
                message = "La fecha de salida debe tener el formato dd/MM/yyyy"
        )
        String fechaSalida
) {
}
