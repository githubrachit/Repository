package com.mli.mpro.common.models.genericModels;

public class GenericApiResponse<T> {
    private ApiResponseData<T> response;

    public ApiResponseData<T> getResponse() {
        return response;
    }

    public GenericApiResponse(T uiResponsePayload) {
        this.response = new ApiResponseData<>();
        this.response.setPayload(uiResponsePayload);
    }

    public GenericApiResponse() {
    }

    public void setResponse(ApiResponseData<T> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "GenericApiResponse{" +
                "response=" + response +
                '}';
    }
}
