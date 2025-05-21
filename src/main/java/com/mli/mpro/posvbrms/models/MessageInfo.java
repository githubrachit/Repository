package com.mli.mpro.posvbrms.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"msgCode,msg,msgDescription"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageInfo {

    @JsonProperty("msgCode")
    private String msgCode;


    @JsonProperty("msg")
    private String msg;


    @JsonProperty("msgDescription")
    private String msgDescription;

    public MessageInfo() {

    }

    public MessageInfo(String msgCode, String msg, String msgDescription) {
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
        return "MessageInfo{" +
                "msgCode='" + msgCode + '\'' +
                ", msg='" + msg + '\'' +
                ", msgDescription='" + msgDescription + '\'' +
                '}';
    }



}
