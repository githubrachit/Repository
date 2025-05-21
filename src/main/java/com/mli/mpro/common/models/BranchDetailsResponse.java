package com.mli.mpro.common.models;

public class BranchDetailsResponse {

    public BranchResponse response;

    public BranchResponse getResponse() {
        return response;
    }

    public void setResponse(BranchResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "BranchDetailsResponse{" +
                "response=" + response +
                '}';
    }
}
