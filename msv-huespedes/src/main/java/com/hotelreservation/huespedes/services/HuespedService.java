package com.hotelreservation.huespedes.services;

import com.hotelreservation.commons.dto.huespedes.HuespedRequest;
import com.hotelreservation.commons.dto.huespedes.HuespedResponse;
import com.hotelreservation.commons.services.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse>{

    HuespedResponse encontrarPorIdSinValidarEstado(Long id);

}
