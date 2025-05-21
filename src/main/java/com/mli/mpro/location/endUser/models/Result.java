package com.mli.mpro.location.endUser.models;

import com.mli.mpro.utils.Utility;

public class Result {
    private Object Headers;
    private Object body;
    private Integer statusCode;

    public Object getHeaders() {
        return Headers;
    }

    public void setHeaders(Object headers) {
        Headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Result{" +
                "Headers=" + Headers +
                ", body=" + body +
                ", statusCode=" + statusCode +
                '}';
    }
}
