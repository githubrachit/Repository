package com.mli.mpro.common.models.genericModels;

public class GenericApiRequest<T> {
    private ApiRequestData<T> request;
    // getters and setters
    public ApiRequestData<T> getRequest() {
        return request;
    }
    public void setRequest(ApiRequestData<T> request) {
        this.request = request;
    }
    @Override
    public String toString() {
        return "GenericApiRequest [request=" + request + "]";
    }

}
