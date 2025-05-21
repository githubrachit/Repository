package com.mli.mpro.neo.models;

import com.mli.mpro.utils.Utility;

public class MsgInfo {

    private String msgCode;
    private String msg;
    private String msgDescription;

    public String getMsgCode() {
        return msgCode;
    }

    public MsgInfo setMsgCode(String msgCode) {
        this.msgCode = msgCode;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MsgInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getMsgDescription() {
        return msgDescription;
    }

    public MsgInfo setMsgDescription(String msgDescription) {
        this.msgDescription = msgDescription;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MsgInfo{" +
                "msgCode='" + msgCode + '\'' +
                ", msg='" + msg + '\'' +
                ", msgDescription='" + msgDescription + '\'' +
                '}';
    }
}
