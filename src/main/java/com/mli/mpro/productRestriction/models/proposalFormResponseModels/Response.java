package com.mli.mpro.productRestriction.models.proposalFormResponseModels;

import com.mli.mpro.utils.Utility;

import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class Response {
    private Header header;
    private MsgInfo msgInfo;
    private Payload payload;
    
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }
    public MsgInfo getMsgInfo() {
        return msgInfo;
    }
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }
    public Payload getPayload() {
        return payload;
    }
    public void setPayload(Payload payload) {
        this.payload = payload;
    }
    
    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Response [header=" + header + ", msgInfo=" + msgInfo + ", payload=" + payload + "]";
    }
   

}
