package com.hotelreservation.huespedes.controllers;


import com.hotelreservation.commons.controller.CommonController;
import com.hotelreservation.commons.dto.huespedes.HuespedRequest;
import com.hotelreservation.commons.dto.huespedes.HuespedResponse;
import com.hotelreservation.huespedes.services.HuespedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {
    public HuespedController(HuespedService service){

        super(service);
    }
    @GetMapping("/id-huesped/{id}")
    public HuespedResponse encontrarPorIdSinValidarEstado (@PathVariable Long id) {
        return service.encontrarPorIdSinValidarEstado(id);
    }

}
