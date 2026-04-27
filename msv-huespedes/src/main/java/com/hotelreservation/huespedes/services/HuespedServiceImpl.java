package com.hotelreservation.huespedes.services;

import com.hotelreservation.commons.dto.habitaciones.HuespedRequest;
import com.hotelreservation.commons.dto.habitaciones.HuespedResponse;
import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.huespedes.entities.Huesped;import com.hotelreservation.huespedes.mappers.HuespedMapper;
import com.hotelreservation.huespedes.repositories.HuespedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor

public class HuespedServiceImpl implements HuespedService{

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;


    @Override
    public List<HuespedResponse> list() {

        return huespedRepository
                .findAllByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(huespedMapper::entidadARespuesta)
                .toList();
    }
    @Override
    public HuespedResponse encontrarPorId(Long id) {

        Huesped huesped = huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RuntimeException("El huesped no ha sido encontrado"));

        return huespedMapper.entidadARespuesta(huesped);

    }
    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        return null;
    }
    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        return null;
    }
    @Override
    public void eliminar(Long id) {

    }
}
