package com.mli.mpro.productRestriction.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class ErrorResponseParams {

    @JsonProperty("encryptionSource")
    private String encryptionSource ;
    @JsonProperty("communicationAddress")
    String[] IVandKey;

    public String getEncryptionSource() {
        return encryptionSource;
    }

    public void setEncryptionSource(String encryptionSource) {
        this.encryptionSource = encryptionSource;
    }

    public String[] getIVandKey() {
        return IVandKey;
    }

    public void setIVandKey(String[] IVandKey) {
        this.IVandKey = IVandKey;
    }

    @Override
    public String toString() {
        return "ErrorResponseParams{" +
                "encryptionSource='" + encryptionSource + '\'' +
                ", IVandKey=" + Arrays.toString(IVandKey) +
                '}';
    }
}
