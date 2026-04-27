package com.hotelreservation.habitaciones.repositories;

import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.habitaciones.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
    Boolean existsByNumeroAndEstadoRegistro(Integer numero, EstadoRegistro estadoRegistro);
    Boolean existsByNumeroAndEstadoRegistroAndIdNot(Integer numero, EstadoRegistro estadoRegistro, Long id);
}
