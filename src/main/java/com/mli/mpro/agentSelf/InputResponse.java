package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;

public class InputResponse {
    @JsonProperty("response")
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
        return "InputResponse{" +
                "response=" + response +
                '}';
    }
}
