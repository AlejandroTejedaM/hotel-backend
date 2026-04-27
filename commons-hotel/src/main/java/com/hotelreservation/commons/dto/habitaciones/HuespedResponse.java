package com.hotelreservation.commons.dto.habitaciones;

import com.hotelreservation.commons.enums.EstadoRegistro;

public record HuespedResponse(

        Long idHuesped,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String telefono,
        String documento,
        String nacionalidad,
        EstadoRegistro estadoRegistro

) {

}