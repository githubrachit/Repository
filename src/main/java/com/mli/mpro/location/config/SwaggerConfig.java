package com.mli.mpro.location.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public OpenAPI api() {
		return new OpenAPI().info(new Info().title("Location Microservice")
				.description("Location Microservice Documentation").version("1.0"));
	}
}