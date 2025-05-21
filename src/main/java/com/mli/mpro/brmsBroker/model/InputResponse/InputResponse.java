package com.mli.mpro.brmsBroker.model.InputResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import javax.validation.Valid;

public class InputResponse {
    @Valid
    @JsonProperty("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "InputResponse{" +
                "response=" + response +
                '}';
    }
}
