package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.partner.model.ErrorResponse;
import com.mli.mpro.utils.Utility;

import java.util.Set;

public class MsgInfo{
    @JsonProperty("msgCode") 
    public String getMsgCode() { 
		 return this.msgCode; } 
    public void setMsgCode(String msgCode) { 
		 this.msgCode = msgCode; } 
    String msgCode;
    @JsonProperty("msg") 
    public String getMsg() { 
		 return this.msg; } 
    public void setMsg(String msg) { 
		 this.msg = msg; } 
    String msg;
    @JsonProperty("msgDescription") 
    public String getMsgDescription() { 
		 return this.msgDescription; } 
    public void setMsgDescription(String msgDescription) { 
		 this.msgDescription = msgDescription; } 
    String msgDescription;

    private Set<ErrorResponse> errors;

    public MsgInfo() {
    }

    public MsgInfo(String msgDescription, String msgCode, String msg) {
        super();
        this.msgDescription = msgDescription;
        this.msgCode = msgCode;
        this.msg = msg;
    }

    public Set<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(Set<ErrorResponse> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MsgInfo{" +
                "msgCode='" + msgCode + '\'' +
                ", msg='" + msg + '\'' +
                ", msgDescription='" + msgDescription +
                ", errors='" + errors +
                '\'' +
                '}';
    }
}
