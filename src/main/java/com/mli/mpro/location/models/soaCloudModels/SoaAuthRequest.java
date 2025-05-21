package com.mli.mpro.location.models.soaCloudModels;

public class SoaAuthRequest {
    private AuthRequest request;

    public AuthRequest getRequest() {
        return request;
    }

    public void setRequest(AuthRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SoaAuthRequest{" +
                "request=" + request +
                '}';
    }
}
