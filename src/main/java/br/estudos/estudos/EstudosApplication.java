package br.estudos.estudos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class EstudosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudosApplication.class, args);
	}

	@RequestMapping("/")
	public String hello() {
		return "Hello buddy!";
	}

}
