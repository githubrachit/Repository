package com.mli.mpro.location.models.questionModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRequest<T> {
    @Valid
    @JsonProperty("request")
    private Request<T> request;

    public Request<T> getRequest() {
        return request;
    }

    public void setRequest(Request<T> request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "InputRequest{" +
                "request=" + request +
                '}';
    }
}
