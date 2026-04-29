package com.hotelreservation.commons.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringCustomUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String quitarAcentos(String texto) {
        return texto.toLowerCase()
                .replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o")
                .replace("ú", "u").replace("ú", "u");
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate != null ? localDate.format(DATE_FORMATTER) : null;
    }

    public static LocalDate stringToLocalDate(String fecha) {
        return (fecha != null && !fecha.isBlank())
                ? LocalDate.parse(fecha.trim(), DATE_FORMATTER)
                : null;
    }

    public static void validarFechasReservacion(String fechaEntrada, String fechaSalida) {
        LocalDate entrada = stringToLocalDate(fechaEntrada);
        LocalDate salida = stringToLocalDate(fechaSalida);

        if (entrada == null || salida == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (!entrada.isBefore(salida)) {
            throw new IllegalArgumentException(
                    "La fecha de entrada debe ser anterior a la fecha de salida"
            );
        }
    }
}
