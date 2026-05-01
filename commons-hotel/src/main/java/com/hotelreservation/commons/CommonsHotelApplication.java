package com.hotelreservation.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CommonsHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonsHotelApplication.class, args);
	}

}
