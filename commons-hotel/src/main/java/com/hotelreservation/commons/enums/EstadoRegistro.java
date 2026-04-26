package com.hotelreservation.commons.enums;

import com.hotelreservation.commons.exceptions.RecursoNoEncontradoException;
import com.hotelreservation.commons.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EstadoRegistro {
    ACTIVO("Activo"),
    ELIMINADO("Eliminado");

    private final String description;

    public static EstadoRegistro encontrarPorDescripcion(String description) {
        String normalizedDescription = StringCustomUtils.quitarAcentos(description);
        for (EstadoRegistro estado : values()) {
            if (StringCustomUtils.quitarAcentos(estado.description).equalsIgnoreCase(normalizedDescription.trim())) {
                return estado;
            }
        }
        throw new RecursoNoEncontradoException("Register state not found by description:" + description);
    }
}
