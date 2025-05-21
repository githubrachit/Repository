package com.mli.mpro.ekyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class OutputResponse {

    @JsonProperty("response")
    private Response response;

    public OutputResponse(Response response) {
        this.response = response;
    }

    public OutputResponse() {
    }

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
        return "OutputResponse{" +
                "response=" + response +
                '}';
    }
}
