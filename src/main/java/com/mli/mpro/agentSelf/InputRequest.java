package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputRequest {
    @JsonProperty("request")
    private com.mli.mpro.agentSelf.Request request;

    public com.mli.mpro.agentSelf.Request getRequest() {
        return request;
    }

    public void setRequest(com.mli.mpro.agentSelf.Request request) {
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
