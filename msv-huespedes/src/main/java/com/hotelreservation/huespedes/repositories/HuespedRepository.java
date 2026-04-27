package com.hotelreservation.huespedes.repositories;

import com.hotelreservation.huespedes.entities.Huesped;
import com.hotelreservation.huespedes.enums.EstadoRegistro;

import java.util.Optional;

@Repository

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByIdHuespedAndEstadoRegistro(Long idHuesped, EstadoRegistro estado);


}
