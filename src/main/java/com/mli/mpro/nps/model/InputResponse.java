package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.validation.Valid;

public class InputResponse {
    @JsonProperty("request")
    @Valid
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "InputResponse{" +
                "request=" + response +
                '}';
    }
}
