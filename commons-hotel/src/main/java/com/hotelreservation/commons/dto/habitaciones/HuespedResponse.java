package com.hotelreservation.commons.dto.habitaciones;



public record HuespedResponse(

        Long idHuesped,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String telefono,
        String documento,
        String nacionalidad,
        String estadoRegistro

) {

}