package com.mli.mpro.tmb.model.urlshortner;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;

public class MsgInfo {

    private String msgCode;
    private boolean success;
    private List<?> errors;

    public MsgInfo() {
    }

    public MsgInfo(String msgCode, boolean success) {
        this(msgCode, success, EMPTY_LIST);
    }

    public MsgInfo(String msgCode, boolean success, List<?> errors) {
        this.msgCode = msgCode;
        this.success = success;
        this.errors = errors;
    }
    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<?> getErrors() {
        return errors;
    }
    public void setErrors(List<?> errors) {
        this.errors = errors;
    }
    @Override
    public String toString() {
        return "MsgInfo{" +
                "msgCode='" + msgCode + '\'' +
                ", success=" + success +
                ", errors=" + errors +
                '}';
    }
}
