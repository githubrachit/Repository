package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgInfo {
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

    public MsgInfo(String msgCode, String msg, String msgDescription) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.msgDescription = msgDescription;
    }

    public MsgInfo() {
    }
}
