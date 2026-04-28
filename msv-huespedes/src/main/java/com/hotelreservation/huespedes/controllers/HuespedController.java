package com.hotelreservation.huespedes.controllers;


import com.hotelreservation.commons.controller.CommonController;
import com.hotelreservation.commons.dto.habitaciones.HuespedRequest;
import com.hotelreservation.commons.dto.habitaciones.HuespedResponse;
import com.hotelreservation.huespedes.services.HuespedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {
    public HuespedController(HuespedService service){

        super(service);
    }
    @GetMapping("/id-huesped/{id}")
    public HuespedResponse obtenerPorIdHuesped(@PathVariable Long id) {
        return service.encontrarPorId(id);
    }
}
