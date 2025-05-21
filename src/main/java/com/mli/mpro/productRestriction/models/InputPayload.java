package com.mli.mpro.productRestriction.models;

public class InputPayload {
    private String pinCode;

    public InputPayload(String pinCode) {
        this.pinCode = pinCode;
    }

    public InputPayload() {
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "InputPayload{" +
                "pinCode='" + pinCode + '\'' +
                '}';
    }
}
