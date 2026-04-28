package com.hotelreservation.commons.enums;

import com.hotelreservation.commons.exceptions.RecursoNoEncontradoException;
import com.hotelreservation.commons.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReserva {

    CONFIRMADA(1, "Reserva creada"),
    EN_CURSO(2, "Check-in realizado"),
    FINALIZADA(3, "Check-out realizado"),
    CANCELADA(4, "Reserva cancelada");

    private final Integer codigo;
    private final String descripcion;

    public static EstadoReserva encontrarPorCodigo(Integer codigo) {
        for (EstadoReserva estado : values()) {
            if (estado.codigo.equals(codigo)) {
                return estado;
            }
        }
        throw new RecursoNoEncontradoException(
                "Código de estado de reserva no encontrado: " + codigo
        );
    }

    public static EstadoReserva encontrarPorDescripcion(String descripcion) {
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (EstadoReserva estado : values()) {
            if (StringCustomUtils.quitarAcentos(estado.descripcion)
                    .equalsIgnoreCase(descripcionNormalizada.trim())) {
                return estado;
            }
        }

        throw new RecursoNoEncontradoException(
                "Estado de reserva no encontrado por descripción: " + descripcion
        );
    }
}