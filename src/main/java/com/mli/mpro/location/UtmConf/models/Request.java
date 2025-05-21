package com.mli.mpro.location.UtmConf.models;

import javax.validation.Valid;

public class Request {
    @Valid
    private RequestData requestData;

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }
}
