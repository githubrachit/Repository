package com.mli.mpro.posvbrms.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PosvBrmsRequest {


    private Request request;



    public PosvBrmsRequest() {

    }

    public PosvBrmsRequest(Request request) {
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
        return "PosvBrmsRequest{" +
                "request=" + request +
                '}';
    }
}
