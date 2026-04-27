package com.hotelreservation.huespedes.controllers;


import com.hotelreservation.commons.dto.habitaciones.HuespedRequest;
import com.hotelreservation.commons.dto.habitaciones.HuespedResponse;
import com.hotelreservation.huespedes.services.HuespedService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/huespedes")

public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService>  {


}
