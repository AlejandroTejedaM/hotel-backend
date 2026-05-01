package com.hotelreservation.commons.dto.huespedes;

public record DatosHuesped(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String nacionalidad
) {
}
