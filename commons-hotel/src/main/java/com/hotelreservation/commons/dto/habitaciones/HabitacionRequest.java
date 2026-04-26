package com.hotelreservation.commons.dto.habitaciones;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record HabitacionRequest(

        @NotNull(message = "El número de habitación es requerido")
        @Positive(message = "El número de la habitación debe ser positivo")
        Integer numero,

        @NotBlank(message = "El tipo de habitación es obligatorio")
        String tipo,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad

) {
}