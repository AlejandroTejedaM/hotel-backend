package com.hotelreservation.commons.enums;

import com.hotelreservation.commons.exceptions.RecursoNoEncontradoException;
import com.hotelreservation.commons.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHabitacion {

    DISPONIBLE(1, "Lista para asignarse"),
    OCUPADA(2, "Asignada a una reserva"),
    LIMPIEZA(3, "En limpieza"),
    MANTENIMIENTO(4, "En reparación");

    private final Integer codigo;
    private final String descripcion;

    public static EstadoHabitacion encontrarPorCodigo(Integer codigo) {
        for (EstadoHabitacion estado : values()) {
            if (estado.codigo.equals(codigo)) {
                return estado;
            }
        }
        throw new RecursoNoEncontradoException("Código de estado no encontrado: " + codigo);
    }

    public static EstadoHabitacion encontrarPorDescripcion(String descripcion) {
        String normalizedDescription = StringCustomUtils.quitarAcentos(descripcion);
        for (EstadoHabitacion estado : values()) {
            if (StringCustomUtils.quitarAcentos(estado.descripcion).equalsIgnoreCase(normalizedDescription.trim())) {
                return estado;
            }
        }
        throw new RecursoNoEncontradoException("Register state not found by descripción:" + descripcion);
    }
}