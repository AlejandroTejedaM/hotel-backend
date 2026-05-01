package com.hotelreservation.reservaciones.repositories;

import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.enums.EstadoReserva;
import com.hotelreservation.reservaciones.entities.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    Optional<Reservacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    List<Reservacion> findAllByEstadoRegistro(EstadoRegistro estadoRegistro);

    Boolean existsByIdHuespedAndEstadoReserva(Long idHuesped, EstadoReserva estadoReserva);

    Boolean existsByIdHuespedAndEstadoReservaAndEstadoRegistro(Long idHuesped, EstadoReserva estadoReserva, EstadoRegistro estadoRegistro);

    Boolean existsByIdHabitacionAndEstadoReservaInAndEstadoRegistro(Long idHabitacion, Collection<EstadoReserva> estadoReservas, EstadoRegistro estadoRegistro);
}
