package com.hotelreservation.auth.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) { }
