package com.mli.mpro.agentSelf.Encryption;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputRequest {
    @JsonProperty("request")
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public InputRequest() {
    }

    public InputRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "InputRequest{" +
                "request=" + request +
                '}';
    }
}
