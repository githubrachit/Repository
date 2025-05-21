package com.mli.mpro.prannumber.response;

public class ResponseHeader {
    private String soaCorrelationId;
    private String soaAppId;

    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    public String getSoaAppId() {
        return soaAppId;
    }

    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "soaCorrelationId='" + soaCorrelationId + '\'' +
                ", soaAppId='" + soaAppId + '\'' +
                '}';
    }
}

