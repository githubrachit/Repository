package com.mli.mpro.location.otp.exception;

public class InvalidRequestException extends Exception{

    private String keyName;
    private String message;

    public InvalidRequestException(String keyName, String message) {
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
