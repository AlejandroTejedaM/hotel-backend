package com.hotelreservation.commons.dto.huespedes;

public record DatosHuesped(
        String nombre,
        String email,
        String telefono,
        String documento,
        String nacionalidad
) {
}
