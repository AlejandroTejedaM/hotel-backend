package com.hotelreservation.huespedes.repositories;

import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.huespedes.entities.Huesped;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

@Repository

public interface HuespedRepository extends JpaRepository <Huesped, Long> {

    List<Huesped> findAllByEstadoRegistro(EstadoRegistro estado);

    Optional<Huesped> findByEmailAndEstadoRegistro(String email, EstadoRegistro estado);

    Optional<Huesped> findByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estado);

    Optional<Huesped> findByDocumentoAndEstadoRegistro(String documento, EstadoRegistro estado);

    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estado);


}


