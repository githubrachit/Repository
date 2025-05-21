package com.mli.mpro.common.models;

public class Meta {
    private String msg;
    private String statusCode;
    private boolean error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "msg='" + msg + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", error=" + error +
                '}';
    }
}
