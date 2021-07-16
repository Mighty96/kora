package com.mighty.ninda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NindaApplication {

	public static void main(String[] args) {

		String profile = System.getProperty("spring.profiles.active");

		if(profile == null) {
			System.setProperty("spring.profiles.active", "local-real");
		}

		SpringApplication.run(NindaApplication.class, args);
	}

}
