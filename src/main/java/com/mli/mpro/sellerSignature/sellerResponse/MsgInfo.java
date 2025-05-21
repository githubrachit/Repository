package com.mli.mpro.sellerSignature.sellerResponse;

import com.mli.mpro.utils.Utility;

public class MsgInfo {

    private String msgCode;
    private String msg;
    private String msgDescription;

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
