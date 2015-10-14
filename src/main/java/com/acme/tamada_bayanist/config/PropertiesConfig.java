package com.acme.tamada_bayanist.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.acme.tamada_bayanist.properties.CbrProperties;

@Configuration
@EnableConfigurationProperties({ CbrProperties.class })
public class PropertiesConfig {

}
