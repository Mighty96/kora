package com.mighty.kora;

import com.mighty.kora.utils.Crawler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoraApplication.class, args);
	}

}
