package com.mighty.ninda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NindaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NindaApplication.class, args);
	}

}
