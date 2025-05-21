package com.mli.mpro.emailPolicyPack;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoaRequest {
    @JsonProperty("header")
    private SoaHeader soaHeader;
    @JsonProperty("requestData")
    private SoaRequestData soaRequestData;

    public SoaHeader getSoaHeader() {
        return soaHeader;
    }

    public void setSoaHeader(SoaHeader soaHeader) {
        this.soaHeader = soaHeader;
    }

    public SoaRequestData getSoaRequestData() {
        return soaRequestData;
    }

    public void setSoaRequestData(SoaRequestData soaRequestData) {
        this.soaRequestData = soaRequestData;
    }

    @Override
    public String toString() {
        return "SoaRequest{" +
                "soaHeader=" + soaHeader +
                ", soaRequestData=" + soaRequestData +
                '}';
    }
}
