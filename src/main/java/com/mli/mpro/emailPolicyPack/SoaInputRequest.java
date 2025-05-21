package com.mli.mpro.emailPolicyPack;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoaInputRequest {
    @JsonProperty("request")
    private SoaRequest soaRequest;

    public SoaRequest getSoaRequest() {
        return soaRequest;
    }

    public void setSoaRequest(SoaRequest soaRequest) {
        this.soaRequest = soaRequest;
    }

    @Override
    public String toString() {
        return "SoaInputRequest{" +
                "soaRequest=" + soaRequest +
                '}';
    }
}
