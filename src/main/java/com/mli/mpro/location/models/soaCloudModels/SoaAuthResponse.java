package com.mli.mpro.location.models.soaCloudModels;

public class SoaAuthResponse {
    private AuthResponse response;

    public AuthResponse getResponse() {
        return response;
    }

    public void setResponse(AuthResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SoaAuthResponse{" +
                "response=" + response +
                '}';
    }
}
