package com.acme.tamada_bayanist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationTest {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationTest.class, args);
	}

}
