package com.mli.mpro.posvbrms.models;

public class PosvBrmsResponse {


    private Response response;

    public PosvBrmsResponse() {

    }

    public PosvBrmsResponse(Response response) {
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
        return "PosvBrmsResponse{" +
                "response=" + response +
                '}';
    }
}
