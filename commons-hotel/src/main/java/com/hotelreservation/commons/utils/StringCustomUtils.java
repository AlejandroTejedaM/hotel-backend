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
}
