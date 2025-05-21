package com.mli.mpro.productRestriction.models;

public class OutputPayload {
    private Integer sumAssuredAllowed;
    private String errorMessages;

    public OutputPayload() {
    }

    public OutputPayload(Integer sumAssuredAllowed, String errorMessages) {
        this.sumAssuredAllowed = sumAssuredAllowed;
        this.errorMessages = errorMessages;
    }

    public Integer getSumAssuredAllowed() {
        return sumAssuredAllowed;
    }

    public void setSumAssuredAllowed(Integer sumAssuredAllowed) {
        this.sumAssuredAllowed = sumAssuredAllowed;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "OutputPayload{" +
                "sumAssuredAllowed=" + sumAssuredAllowed +
                ", errorMessages='" + errorMessages + '\'' +
                '}';
    }
}
