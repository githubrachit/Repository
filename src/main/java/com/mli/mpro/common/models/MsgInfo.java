package com.mli.mpro.common.models;

public class MsgInfo {
    private String msgCode;
    private String msg;
    private String msgDescription;

    public String getMsgCode() {
        return msgCode;
    }

    public MsgInfo() {
    }

    public MsgInfo(String msgCode, String msg, String msgDescription) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.msgDescription = msgDescription;
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
        return "MsgInfo{" +
                "msgCode='" + msgCode + '\'' +
                ", msg='" + msg + '\'' +
                ", msgDescription='" + msgDescription + '\'' +
                '}';
    }
}
