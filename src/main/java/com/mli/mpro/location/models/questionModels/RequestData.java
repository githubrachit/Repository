package com.mli.mpro.location.models.questionModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.List;

public class RequestData<T> {
    @Valid
    @JsonProperty("utm")
    public T utm;

    @JsonProperty("requestPayload")
    public List<T> requestPayload;

    public List<T> getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(List<T> requestPayload) {
        this.requestPayload = requestPayload;
    }

    public T getUtm() {
        return utm;
    }

    public void setUtm(T utm) {
        this.utm = utm;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "utm=" + utm +
                ", requestPayload=" + requestPayload +
                '}';
    }
}
