package com.hotelreservation.commons.dto.huespedes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
        String apellidoMaterno,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener exactamente 10 dígitos")
        String telefono,

        @NotBlank(message = "El documento es requerido")
        String documento,

        @NotBlank(message = "La nacionalidad es obligatoria")
        String nacionalidad

){
}








