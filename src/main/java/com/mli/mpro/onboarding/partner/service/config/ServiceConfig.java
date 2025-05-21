package com.mli.mpro.onboarding.partner.service.config;

import com.mli.mpro.onboarding.partner.service.RequestTransformationRegistry;
import com.mli.mpro.onboarding.partner.service.SOAServiceRegistry;
import com.mli.mpro.onboarding.partner.service.ValidationServiceRegistry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

	
	@Bean
	public FactoryBean<Object> getValidationService() {
		
		ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
		bean.setServiceLocatorInterface(ValidationServiceRegistry.class);
		
		return bean;
	}
	
	@Bean
	public FactoryBean<Object> getSOAService() {
		
		ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
		bean.setServiceLocatorInterface(SOAServiceRegistry.class);
		
		return bean;
	}
	
	@Bean
	public FactoryBean<Object> getRequestTransformationService() {
		
		ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
		bean.setServiceLocatorInterface(RequestTransformationRegistry.class);
		
		return bean;
	}
}
