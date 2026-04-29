package com.hotelreservation.commons.client;

import com.hotelreservation.commons.dto.huespedes.HuespedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-huespedes")
public interface HuespedClient {

    @GetMapping("/{id}")
    HuespedResponse findActiveById(@PathVariable Long id);

    @GetMapping("/id-huesped/{id}")
    HuespedResponse findById(@PathVariable Long id);
}
