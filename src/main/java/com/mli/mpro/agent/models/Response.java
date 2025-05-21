package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class Response{
    @JsonProperty("header") 
    public Header getHeader() { 
		 return this.header; } 
    public void setHeader(Header header) { 
		 this.header = header; } 
    Header header;
    @JsonProperty("msgInfo") 
    public MsgInfo getMsgInfo() { 
		 return this.msgInfo; } 
    public void setMsgInfo(MsgInfo msgInfo) { 
		 this.msgInfo = msgInfo; } 
    MsgInfo msgInfo;
    @JsonProperty("payload") 
    public Payload getPayload() { 
		 return this.payload; } 
    public void setPayload(Payload payload) { 
		 this.payload = payload; } 
    Payload payload;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Response{" +
                "header=" + header +
                ", msgInfo=" + msgInfo +
                ", payload=" + payload +
                '}';
    }
}
