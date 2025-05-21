package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

public class PolicyHistoryResponse {

    public Response response;

    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PolicyHistoryResponse{" +
                "response=" + response +
                '}';
    }

}
