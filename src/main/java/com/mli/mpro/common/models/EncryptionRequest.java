package com.mli.mpro.common.models;

import java.util.Arrays;

public class EncryptionRequest {

    private String encryptedPayload;
    private String kek;


    public EncryptionRequest(String encryptedPayload, String kek) {
        this.encryptedPayload = encryptedPayload;
        this.kek = kek;
    }

    public EncryptionRequest(String encryptedPayload) {
        this.encryptedPayload = encryptedPayload;
    }

    public String getEncryptedPayload() {
        return encryptedPayload;
    }

    public void setEncryptedPayload(String encryptedPayload) {
        this.encryptedPayload = encryptedPayload;
    }

    public String getKek() {
        return kek;
    }

    public void setKek(String kek) {
        this.kek = kek;
    }

    @Override
    public String toString() {
        return "EncryptionRequest{" +
                "encryptedPayload='" + encryptedPayload + '\'' +
                ", kek='" + kek + '\'' +
                '}';
    }
}
