package com.mli.mpro.location.newApplication.model;


public class Result {

    private Response response;

    public Result() {
    }

    public Result(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
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
