package com.mli.mpro.tmb.model.urlshortner;

public class UrlShortnerResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "UrlShortnerResponse{" +
                "response=" + response +
                '}';
    }
}
