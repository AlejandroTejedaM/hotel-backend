package com.hotelreservation.reservaciones.services;

import com.hotelreservation.commons.dto.reservaciones.ReservacionRequest;
import com.hotelreservation.commons.dto.reservaciones.ReservacionResponse;
import com.hotelreservation.commons.services.CrudService;

public interface ReservacionService extends CrudService<ReservacionRequest, ReservacionResponse> {
    void changeEstadoReserva(Long idReserva, Integer idEstado);
    Boolean tieneReservacionesPorIdHuespedYEstadoReserva(Long idHuesped, Integer idEstado);
    Boolean isRoomBooked(Long idHabitacion);
}