package com.mli.mpro.tmb.model.urlshortner;

public class RequestPayload {

    private String url;

    private String expiry;

    private String isPromotional;

    public RequestPayload() {
    }

    public RequestPayload(String url, String expiry, String isPromotional) {
        this.url = url;
        this.expiry = expiry;
        this.isPromotional = isPromotional;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getIsPromotional() {
        return isPromotional;
    }

    public void setIsPromotional(String isPromotional) {
        this.isPromotional = isPromotional;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "url='" + url + '\'' +
                ", expiry='" + expiry + '\'' +
                ", isPromotional='" + isPromotional + '\'' +
                '}';
    }
}
