package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class NativePlantTrackerApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(NativePlantTrackerApiApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "secrectpasswordforthenativeplanttrackerapp";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println("Encoded password: " + encodedPassword);

	}

}
