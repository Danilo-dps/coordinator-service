package br.com.danilodps.coordinator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoordinatorServiceApplication {

	private CoordinatorServiceApplication(){}

	static void main(String[] args) {
		SpringApplication.run(CoordinatorServiceApplication.class, args);
	}

}
