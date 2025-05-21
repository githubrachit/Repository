package com.mli.mpro.tmb.model.urlshortner;

public class Header {

    private String soaCorrelationId;

    private String soaAppId;

    public Header() {
    }

    public Header(String soaCorrelationId, String soaAppId) {
        this.soaCorrelationId = soaCorrelationId;
        this.soaAppId = soaAppId;
    }

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
        return "Header{" +
                "soaCorrelationId='" + soaCorrelationId + '\'' +
                ", soaAppId='" + soaAppId + '\'' +
                '}';
    }
}
