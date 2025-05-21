package com.mli.mpro.agent.models;


public class SoaApiSatgCdcResponse {
    private SoaStagCdcInnerResponse response;

    public SoaStagCdcInnerResponse getResponse() {
        return response;
    }

    public SoaApiSatgCdcResponse setResponse(SoaStagCdcInnerResponse response) {
        this.response = response;
        return this;
    }

    @Override
    public String toString() {
        return "SoaApiSatgCdcResponse{" +
                "response=" + response +
                '}';
    }
}
