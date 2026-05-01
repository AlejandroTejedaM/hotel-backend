package com.hotelreservation.huespedes.mappers;

import com.hotelreservation.commons.dto.huespedes.HuespedRequest;
import com.hotelreservation.commons.dto.huespedes.HuespedResponse;
import com.hotelreservation.commons.mappers.CommonMapper;
import com.hotelreservation.huespedes.entities.Huesped;
import com.hotelreservation.commons.enums.EstadoRegistro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor

public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

@Override
    public Huesped requestAEntidad(HuespedRequest request) {
      if(request == null)  return null;

      return Huesped.builder()
              .nombre(request.nombre())
              .apellidoPaterno(request.apellidoPaterno())
              .apellidoMaterno(request.apellidoMaterno())
              .email(request.email())
              .telefono(request.telefono())
              .documento(request.documento())
              .nacionalidad(request.nacionalidad())
              .estadoRegistro(EstadoRegistro.ACTIVO)
              .build();


    }

    @Override
    public HuespedResponse entidadARespuesta(Huesped entity) {

        if(entity == null) return null;

        return new HuespedResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getDocumento(),
                entity.getNacionalidad(),
                entity.getEstadoRegistro().getDescription());

    }
}










