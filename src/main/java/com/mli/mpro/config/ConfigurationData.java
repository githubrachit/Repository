package com.mli.mpro.config;

import com.mli.mpro.configuration.repository.ConfigurationRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationData {
	
	   private static final Logger logger = LoggerFactory.getLogger(ConfigurationData.class);

	    private ConfigurationRepository configurationRepository;

	    public static List<com.mli.mpro.configuration.models.Configuration> configurationDetails = new ArrayList<>();

	    @Autowired
	    public ConfigurationData(ConfigurationRepository configurationRepository){
	        this.configurationRepository = configurationRepository;
	        logger.info("loading configuration details collection from database");
	        configurationDetails = configurationRepository.findAll();
	    }

}
