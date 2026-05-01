package com.hotelreservation.commons.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-reservaciones")
public interface ReservaClient {

    @GetMapping("/estado/{idEstado}/id-huesped/{idHuesped}")
    Boolean tieneReservacionesPorIdHuespedYIdEstadoReservacion(
            @PathVariable Integer idEstado,
            @PathVariable Long idHuesped);

    @GetMapping("/id-habitacion/{idHabitacion}/isRoomBooked")
    Boolean isRoomBooked(@PathVariable Long idHabitacion);
}
