package com.hotelreservation.reservaciones.controllers;

import com.hotelreservation.commons.controller.CommonController;
import com.hotelreservation.commons.dto.reservaciones.ReservacionRequest;
import com.hotelreservation.commons.dto.reservaciones.ReservacionResponse;
import com.hotelreservation.reservaciones.services.ReservacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ReservacionController extends CommonController<ReservacionRequest, ReservacionResponse, ReservacionService> {
    public ReservacionController(ReservacionService service) {
        super(service);
    }

    @PutMapping("/{idReserva}/estado/{idEstado}")
    public ResponseEntity<Void> changeEstadoReserva(@PathVariable Long idReserva, @PathVariable Integer idEstado) {
        service.changeEstadoReserva(idReserva, idEstado);
        return ResponseEntity.ok().build();
    }
}
