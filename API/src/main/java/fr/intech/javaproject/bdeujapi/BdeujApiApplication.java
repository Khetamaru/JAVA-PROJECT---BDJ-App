package fr.intech.javaproject.bdeujapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BdeujApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BdeujApiApplication.class, args);
	}

	@Bean
	public ObjectMapper getObj() {
		return new ObjectMapper();
	}
}