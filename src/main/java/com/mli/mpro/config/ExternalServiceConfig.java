package com.mli.mpro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties
public class ExternalServiceConfig {
    private Map<String, String> urlDetails = new HashMap<>();
    private Map<String, String> spCodes = new HashMap<>();

    public Map<String, String> getSpCodes() {
        return spCodes;
    }

    public void setSpCodes(Map<String, String> spCodes) {
        this.spCodes = spCodes;
    }

    public Map<String, String> getUrlDetails() {
        return urlDetails;
    }

    public void setUrlDetails(Map<String, String> urlDetails) {
        this.urlDetails = urlDetails;
    }

    @Override
    public String toString() {
        return "ExternalServiceConfig [urlDetails=" + urlDetails + "], [spCodes="+spCodes+"]";
    }
}
