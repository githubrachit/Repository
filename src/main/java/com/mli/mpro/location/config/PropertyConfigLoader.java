package com.mli.mpro.location.config;

import com.mli.mpro.location.models.PropertyConfig;
import com.mli.mpro.location.repository.PropertyConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class PropertyConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyConfigLoader.class);

    private final PropertyConfigRepository configRepository;
    private final ConfigurableEnvironment environment;

    @Value("${service.name:location}")
    private String serviceName;

    @Value("${spring.profiles.active:dev}")
    private String env;

    public PropertyConfigLoader(PropertyConfigRepository configRepository, ConfigurableEnvironment environment) {
        this.configRepository = configRepository;
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        logger.info("Initializing PropertyConfigLoader for service: {} and environment: {}", serviceName, env);

        if (serviceName == null || serviceName.isBlank() || env == null || env.isBlank()) {
            logger.error("SERVICE_NAME and ENVIRONMENT must be set!");
            return;
        }

        Map<String, Object> finalConfigs = new HashMap<>();

        try {
            // Load common properties
            Optional<PropertyConfig> commonConfig = configRepository.findByServiceNameAndEnvironment(serviceName, "all");
            commonConfig.ifPresent(config -> {
                logger.info("Loading common properties for service: {}", serviceName);
                finalConfigs.putAll(getSafeProperties(config));
            });

            // Load service-specific properties
            Optional<PropertyConfig> serviceConfig = configRepository.findByServiceNameAndEnvironment(serviceName, env);
            serviceConfig.ifPresent(config -> {
                logger.info("Loading service-specific properties for service: {} in environment: {}", serviceName, env);
                finalConfigs.putAll(getSafeProperties(config));
            });

            if (finalConfigs.isEmpty()) {
                logger.warn("No properties loaded for service: {} in environment: {}", serviceName, env);
            } else {
                injectProperties(finalConfigs);
                logger.info("Successfully injected {} properties into environment.", finalConfigs.size());
            }
        } catch (Exception e) {
            logger.error("Error while loading properties for service: {} in environment: {}", serviceName, env, e);
        }
    }

    private void injectProperties(Map<String, Object> properties) {
        synchronized (this) { // Ensure thread safety while modifying properties
            MutablePropertySources propertySources = environment.getPropertySources();
            propertySources.addFirst(new MapPropertySource(serviceName, properties));
            logger.debug("Properties injected: {}", properties);
        }
    }

    private Map<String, Object> getSafeProperties(PropertyConfig config) {
        return config.getProperties() != null ? config.getProperties() : Collections.emptyMap();
    }
}
