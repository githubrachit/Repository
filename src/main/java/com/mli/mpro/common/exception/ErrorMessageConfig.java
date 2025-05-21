package com.mli.mpro.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author arpita
 * <p>
 * This is a singleton class which loads on application start up.
 * <p>
 * This class reads all the error messages required at various places in application during exception
 * handling from the configured properties file.
 * <p>
 * Any new message added in the properties file will be automatically loaded in the application after service restart.
 * <p>
 * A key value map is configured with message keyword and the message. To read any message the key shall be
 * passed as a parameter
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-error.properties")
@ConfigurationProperties
public class ErrorMessageConfig {

    private Map<String, String> errorMessages = new HashMap<>();

    /**
     * @return the error
     */
    public Map<String, String> getErrorMessages() {
	return errorMessages;
    }

    /**
     * @param error the error to set
     */
    public void setErrorMessages(Map<String, String> error) {
	this.errorMessages = error;
    }

    @Override
    public String toString() {
	return "ErrorMsgConfig [error=" + errorMessages + "]";
    }
}
