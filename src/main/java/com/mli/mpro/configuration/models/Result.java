package com.mli.mpro.configuration.models;
public class Result {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public Result() {
    }

    public Result(Response response) {
        this.response = response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Result{" +
                "response=" + response +
                '}';
    }
}
