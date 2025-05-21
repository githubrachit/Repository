package com.mli.mpro.psm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.Arrays;


public class PsmRequest {

    @JsonProperty("header")
    private Header header;

    @Valid
    @JsonProperty("payload")
    private PsmInfoPayload psmInfoPayload;


    public PsmRequest(PsmInfoPayload psmInfoPayload) {
        this.psmInfoPayload = psmInfoPayload;
    }

    public PsmRequest() {

    }


    @JsonProperty("header")
    public Header getHeader() {
        return header;
    }

    @JsonProperty("header")
    public void setHeader(Header header) {
        this.header = header;
    }

    @JsonProperty("payload")
    public PsmInfoPayload getPayload() {
        return psmInfoPayload;
    }

    @JsonProperty("payload")
    public void setPayload(PsmInfoPayload psmInfoPayload) {
        this.psmInfoPayload = psmInfoPayload;
    }

    public PsmInfoPayload getPsmInfoPayload() {
        return psmInfoPayload;
    }

    public void setPsmInfoPayload(PsmInfoPayload psmInfoPayload) {
        this.psmInfoPayload = psmInfoPayload;
    }

    @Override
    public String toString() {
        return "PsmRequest{" +
                "header=" + header +
                ", psmInfoPayload=" + psmInfoPayload +
                '}';
    }
}
