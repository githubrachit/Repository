package com.mli.mpro.onboarding.model.datalakesms;

public class ResponseData {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "response=" + response +
                '}';
    }
}
