package com.mli.mpro.prannumber.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PranRequest {
    @JsonProperty("request")
    private Request request;
    public PranRequest() {
    }

    public PranRequest(Request request) {
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
        return "RequestWrapper{" +
                "request=" + request +
                '}';
    }

}
