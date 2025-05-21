package com.mli.mpro.location.auth.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RequestScopeConfig {

	@Bean
	@RequestScope
	public Map<String, String> headerMap() {
		return new HashMap<>();
	}
}