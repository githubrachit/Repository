package com.mli.mpro.location.newApplication.model;

import com.mli.mpro.onboarding.partner.model.ErrorResponse;

import java.util.Set;

public class ResponseMsgInfo {
    private int msgCode;
    private String msg;
    private String msgDescription;
    private Set<ErrorResponse> errors;
    public ResponseMsgInfo() {
    }

    public ResponseMsgInfo(int msgCode, String msg, String msgDescription) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.msgDescription = msgDescription;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgDescription() {
        return msgDescription;
    }

    public void setMsgDescription(String msgDescription) {
        this.msgDescription = msgDescription;
    }

    @Override
    public String toString() {
        return "ResponseMsgInfo{" +
                "msgCode=" + msgCode +
                ", msg='" + msg + '\'' +
                ", msgDescription='" + msgDescription + '\'' +
                ",errors='"+errors+
                '}';
    }

    public Set<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(Set<ErrorResponse> errors) {
        this.errors = errors;
    }
}
