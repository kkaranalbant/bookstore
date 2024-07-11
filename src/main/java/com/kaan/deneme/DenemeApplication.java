package com.kaan.deneme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DenemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DenemeApplication.class, args);
	}

}
