package com.mli.mpro.location.otp.exception;

public class RateLimitException extends Exception{

    private String keyName;
    private String message;

    public RateLimitException(String keyName, String message) {
        super(String.format("Exception occured : %s", message));
        this.keyName = keyName;
        this.message = message;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getMessage() {
        return message;
    }
}
