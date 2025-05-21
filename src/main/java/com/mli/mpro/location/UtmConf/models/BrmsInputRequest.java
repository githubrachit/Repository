package com.mli.mpro.location.UtmConf.models;

import javax.validation.Valid;

public class BrmsInputRequest {
    @Valid
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
