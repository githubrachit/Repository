package com.mli.mpro.location.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Document(collection = "property_config")
public class PropertyConfig {

    @Id
    private String id;
    private String serviceName;
    private String environment;
    private Map<String, Object> properties; // Stores key-value pairs

    // Default constructor
    public PropertyConfig() {
    }

    // Parameterized constructor
    public PropertyConfig(String id, String serviceName, String environment, Map<String, Object> properties) {
        this.id = id;
        this.serviceName = serviceName;
        this.environment = environment;
        this.properties = properties;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Map<String, Object> getProperties() {
        return properties != null ? Collections.unmodifiableMap(properties) : Collections.emptyMap();
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    // Override equals() and hashCode() for correct object comparisons
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PropertyConfig that = (PropertyConfig) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(environment, that.environment) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceName, environment, properties);
    }

    // Override toString() for better logging and debugging
    @Override
    public String toString() {
        return "PropertyConfig{" +
                "id='" + id + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", environment='" + environment + '\'' +
                ", properties=" + properties +
                '}';
    }
}
