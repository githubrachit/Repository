package com.mli.mpro.tmb.model.urlshortner;

public class UrlShortnerRequest {

    private Request request;

    public UrlShortnerRequest() {
    }

    public UrlShortnerRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "UrlShortnerRequest{" +
                "request=" + request +
                '}';
    }
}
