package com.mli.mpro.location.models.soaCloudModels;

public class SoaCloudResponse<T> {

    private SoaResponse<T> response;

    public SoaResponse<T> getResponse() {
        return response;
    }

    public void setResponse(SoaResponse<T> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SoaCloudResponse{" +
                "response=" + response +
                '}';
    }
}
