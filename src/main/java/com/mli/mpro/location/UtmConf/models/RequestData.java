package com.mli.mpro.location.UtmConf.models;

import com.mli.mpro.location.UtmConf.Payload.UtmConfiguratorInputRequest;

import javax.validation.Valid;

public class RequestData {
    @Valid
    private UtmConfiguratorInputRequest inputRequest;

    public UtmConfiguratorInputRequest getInputRequest() {
        return inputRequest;
    }

    public void setInputRequest(UtmConfiguratorInputRequest inputRequest) {
        this.inputRequest = inputRequest;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "utmConfiguratorInputRequest=" + inputRequest +
                '}';
    }
}
