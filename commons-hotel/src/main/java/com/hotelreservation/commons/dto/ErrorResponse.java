package com.hotelreservation.commons.dto;

public record ErrorResponse(
        Integer code,
        String message
) { }
