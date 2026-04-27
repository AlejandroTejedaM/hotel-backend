package com.hotelreservation.habitaciones.controllers;

import com.hotelreservation.commons.controller.CommonController;
import com.hotelreservation.commons.dto.habitaciones.HabitacionRequest;
import com.hotelreservation.commons.dto.habitaciones.HabitacionResponse;
import com.hotelreservation.habitaciones.services.HabitacionService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {
    public HabitacionController(HabitacionService service) {
        super(service);
    }
}
