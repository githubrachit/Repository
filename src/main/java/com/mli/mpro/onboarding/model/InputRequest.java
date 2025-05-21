package com.mli.mpro.onboarding.model;

public class InputRequest {
       Request request;
    public InputRequest() {
        
    }

    public InputRequest(Request request) {
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
        return "InputRequest [request=" + request + "]";
    }
       
       
}
