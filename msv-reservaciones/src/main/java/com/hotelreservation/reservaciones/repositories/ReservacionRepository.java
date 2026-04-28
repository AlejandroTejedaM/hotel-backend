package com.hotelreservation.reservaciones.repositories;

import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.reservaciones.entities.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    Optional<Reservacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
}
