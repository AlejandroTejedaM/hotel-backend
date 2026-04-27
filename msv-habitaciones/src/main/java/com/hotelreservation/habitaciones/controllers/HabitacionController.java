package com.hotelreservation.habitaciones.controllers;

import com.hotelreservation.commons.controller.CommonController;
import com.hotelreservation.commons.dto.habitaciones.HabitacionRequest;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.habitaciones.services.HabitacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {
    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> buscarPorIdIgnorandoEstadoRegistro(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorIdIgnorandoEstadoRegistro(id));
    }

    @PutMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<Void> actualizarEstadoHabitacion(@PathVariable Long id, @PathVariable Integer idEstado) {
        service.actualizarEstadoHabitacion(id, idEstado);
        return ResponseEntity.noContent().build();
    }
}
