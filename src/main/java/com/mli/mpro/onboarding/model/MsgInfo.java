package com.mli.mpro.onboarding.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgInfo {

    private String msgCode; 
    private String msg;
    private String msgDescription;

    @JsonProperty("errors")
    private List<Object> errors;

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public MsgInfo(String msgCode, String msg, String msgDescription, List<Object> errors) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.msgDescription = msgDescription;
        this.errors = errors;
    }

    public MsgInfo() {
       
    }
    
    public MsgInfo(String msgCode, String msg, String msgDescription) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.msgDescription = msgDescription;
    }
    public String getMsgCode() {
        return msgCode;
    }
    public void setMsgCode(String msgCode) {
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
        return "MsgInfo [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
    }
    
}
