package com.mli.mpro.psm.Exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value = "classpath:psm/validation-error.properties")
@ConfigurationProperties
public class CustomErrorMessagePsm {
    private Map<String, String> customErrorCodeList = new HashMap<>();
    private Map<String, String> customErrorMessageList = new HashMap<>();

    public Map<String, String> getCustomErrorCodeList() {
        return customErrorCodeList;
    }


    public void setCustomErrorCodeList(Map<String, String> customErrorCodeList) {
        this.customErrorCodeList = customErrorCodeList;
    }

    public Map<String, String> getCustomErrorMessageList() {
        return customErrorMessageList;
    }

    public void setCustomErrorMessageList(Map<String, String> customErrorMessageList) {
        this.customErrorMessageList = customErrorMessageList;
    }

    @Override
    public String toString() {
        return "ErrorMsgConfig [errorMessages=" + customErrorCodeList + "]";
    }
}

