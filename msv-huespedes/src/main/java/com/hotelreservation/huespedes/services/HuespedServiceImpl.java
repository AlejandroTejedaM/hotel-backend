package com.hotelreservation.huespedes.services;

import com.hotelreservation.commons.dto.habitaciones.HuespedRequest;
import com.hotelreservation.commons.dto.habitaciones.HuespedResponse;
import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.exceptions.EntidadRelacionadaException;import com.hotelreservation.commons.exceptions.RecursoNoEncontradoException;import com.hotelreservation.huespedes.entities.Huesped;
import com.hotelreservation.huespedes.mappers.HuespedMapper;
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
                .orElseThrow(() -> new RecursoNoEncontradoException("El huesped no ha sido encontrado"));

        return huespedMapper.entidadARespuesta(huesped);

    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {

        validarDuplicados(request);

        Huesped huesped = huespedMapper.requestAEntidad(request);
        Huesped guardado = huespedRepository.save(huesped);

        return huespedMapper.entidadARespuesta(guardado);

    }
    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {

        Huesped huesped = huespedRepository
                .findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huésped no encontrado"));

        validarDuplicadosUpdate(request, id);

        huesped.setNombre(request.nombre());
        huesped.setApellidoPaterno(request.apellidoPaterno());
        huesped.setApellidoMaterno(request.apellidoMaterno());
        huesped.setEmail(request.email());
        huesped.setTelefono(request.telefono());
        huesped.setDocumento(request.documento());
        huesped.setNacionalidad(request.nacionalidad());

        Huesped actualizado = huespedRepository.save(huesped);

        return huespedMapper.entidadARespuesta(actualizado);

    }

    @Override
    public void eliminar(Long id) {

        Huesped huesped = huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huésped no encontrado"));

        huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        huespedRepository.save(huesped);

    }

    private void validarDuplicados(HuespedRequest request){

        if (huespedRepository.findByEmailAndEstadoRegistro(request.email(), EstadoRegistro.ACTIVO).isPresent()) {
            throw new EntidadRelacionadaException("El email ya registrado");
        }

        if (huespedRepository.findByTelefonoAndEstadoRegistro(request.telefono(), EstadoRegistro.ACTIVO).isPresent()) {
            throw new EntidadRelacionadaException("El telefono ya registrado");
        }
        if (huespedRepository.findByDocumentoAndEstadoRegistro(request.documento(), EstadoRegistro.ACTIVO).isPresent()) {
            throw new EntidadRelacionadaException("El documento ya registrado");
        }

    }
    private void validarDuplicadosUpdate(HuespedRequest request, Long id) {

        var email = huespedRepository.findByEmailAndEstadoRegistro(request.email(), EstadoRegistro.ACTIVO);
        if (email.isPresent() && !email.get().getId().equals(id)) {
            throw new EntidadRelacionadaException("Email ya registrado");
        }

        var telefono = huespedRepository.findByTelefonoAndEstadoRegistro(request.telefono(), EstadoRegistro.ACTIVO);
        if (telefono.isPresent() && !telefono.get().getId().equals(id)) {
            throw new EntidadRelacionadaException("Teléfono ya registrado");
        }

        var documento = huespedRepository.findByDocumentoAndEstadoRegistro(request.documento(), EstadoRegistro.ACTIVO);
        if (documento.isPresent() && !documento.get().getId().equals(id)) {
            throw new EntidadRelacionadaException("Documento ya registrado");
        }
    }
}
