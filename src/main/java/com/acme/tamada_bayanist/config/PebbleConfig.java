package com.acme.tamada_bayanist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.StringLoader;

@Configuration
public class PebbleConfig {

	@Bean
	public PebbleEngine getPebbleEngine() {
		PebbleEngine eng = new PebbleEngine();
		StringLoader loader = new StringLoader();
		eng.setLoader(loader);
		return eng;
	}

}
