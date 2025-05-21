package com.mli.mpro.ccms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("header")
    private transient Header header;

    @JsonProperty("msgInfo")
    private transient MessageInfo msgInfo;

    public Response(Header header, MessageInfo msgInfo) {
        this.header = header;
        this.msgInfo = msgInfo;
    }

    public Response() {
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MessageInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MessageInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Response{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                '}';
    }
}
