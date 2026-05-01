package com.hotelreservation.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.hotelreservation.huespedes", "com.hotelreservation.commons"})
public class MsvHuespedesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvHuespedesApplication.class, args);
	}

}
