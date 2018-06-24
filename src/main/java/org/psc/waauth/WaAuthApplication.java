package org.psc.waauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration 
public class WaAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaAuthApplication.class, args);
	}

}
