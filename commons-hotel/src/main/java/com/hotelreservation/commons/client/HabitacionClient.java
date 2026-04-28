package com.hotelreservation.commons.client;

import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "msv-habitaciones")
public interface HabitacionClient {

    @GetMapping("/id-habitacion/{id}")
    HabitacionResponse findHabitacionById(@PathVariable() Long id);

    @GetMapping("/{id}")
    HabitacionResponse findHabitacionActivaById(@PathVariable() Long id);

    @PutMapping("/{id}/estado/{idEstado}")
    void actualizarEstadoHabitacion(@PathVariable() Long id, @PathVariable() Integer idEstado);
}
