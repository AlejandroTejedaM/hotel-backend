package com.hotelreservation.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hotelreservation.habitaciones", "com.hotelreservation.commons"})
public class MsvHabitacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvHabitacionesApplication.class, args);
	}

}
