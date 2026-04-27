package com.hotelreservation.huespedes.mappers;

import com.hotelreservation.commons.dto.habitaciones.HuespedRequest;
import com.hotelreservation.commons.dto.habitaciones.HuespedResponse;
import com.hotelreservation.commons.mappers.CommonMapper;
import com.hotelreservation.huespedes.entities.Huesped;
import com.hotelreservation.huespedes.enums.EstadoRegistro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor

public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

    @Override
    public Huesped requestAEntidad(HuespedRequest request) {

        if ( request== null) return null;

        return Huesped.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .email(request.email())
                .telefono(request.telefono())
                .documento(request.documento())
                .nacionalidad(request.nacionalidad())


    }

    @Override
    public HuespedResponse entidadARespuesta(Huesped entity) {

        if (request == null) return null;

        return Huesped.builder()
                entity.getId(),
                String.join(" "
                        entity.getNombre(),
                        entity.getapellidoPaterno(),
                        entity.getapellidoMaterno(),
                        entity.getemail(),
                        entity.gettelefono(),
                        entity.getdocumento(),
                        entity.getNacionalidad(),
                        entity.getEstadoRegistro.getEstadoRegistro());

    }
}



