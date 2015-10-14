package com.acme.tamada_bayanist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		final ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
	}

}
