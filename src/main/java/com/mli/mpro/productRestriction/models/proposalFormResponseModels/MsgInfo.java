package com.mli.mpro.productRestriction.models.proposalFormResponseModels;

import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;


public class MsgInfo {
    private int msgCode;
    private String msg;
    private String msgDescription;

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
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "MsgInfo [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
    }

}
