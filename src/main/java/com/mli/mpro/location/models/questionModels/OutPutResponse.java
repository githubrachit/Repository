package com.mli.mpro.location.models.questionModels;

public class OutPutResponse<T> {
    private Response<T> response;

    public OutPutResponse() {
    }

    public OutPutResponse(Response<T> response) {
        this.response = response;
    }
    public Response<T> getResponse() {
        return response;
    }
    @Override
    public String toString() {
        return "OutPutResponse{" +
                "response=" + response +
                '}';
    }
}
