package com.mli.mpro.location.otp.exception;

public class OtpRetryException extends Exception{

    private String keyName;
    private String message;

    public OtpRetryException(String keyName, String message) {
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
