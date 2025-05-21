package com.mli.mpro.common.models;

public class BranchDetailsRequest {
    public BranchRequest request;

    public BranchRequest getRequest() {
        return request;
    }

    public void setRequest(BranchRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "BranchDetailsRequest{" +
                "request=" + request +
                '}';
    }
}
