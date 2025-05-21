package com.mli.mpro.ccms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

@JsonPropertyOrder({"msgCode","msg","msgDescription"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageInfo {

    @JsonProperty("msgCode")
    private String msgCode;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("msgDescription")
    private String msgDescription;
    /**
     * @param msgCode
     * @param msg
     * @param msgDescription
     */
    public MessageInfo(String msgCode, String msg, String msgDescription) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.msgDescription = msgDescription;
    }
    /**
     *
     */
    public MessageInfo() {

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
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "MessageInfo [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
    }


}
