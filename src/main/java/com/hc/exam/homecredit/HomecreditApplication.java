package com.hc.exam.homecredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class HomecreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomecreditApplication.class, args);
	}

	@Bean
	public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
	    return builder.build();
    }

    @Bean
    public String keyAppId() {
	    return "8e700960513516e6da41751968acb401";
    }

    @Bean
    public URI apiURI(String keyAppId) throws URISyntaxException {
	    return new URI("http://api.openweathermap.org/data/2.5/weather?APPID=" + keyAppId);
    }
}


