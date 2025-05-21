package com.mli.mpro.location.models.questionModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.common.models.Metadata;

import javax.validation.Valid;

public class Request <T>{
    @JsonProperty("metadata")
    private Metadata metadata;
    @Valid
    @JsonProperty("requestData")
    private RequestData<T> requestData;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public RequestData<T> getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData<T> requestData) {
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "Request{" +
                "metadata=" + metadata +
                ", requestData=" + requestData +
                '}';
    }

    public Request() {
    }
}
