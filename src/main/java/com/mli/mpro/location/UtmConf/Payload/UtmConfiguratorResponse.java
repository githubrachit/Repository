package com.mli.mpro.location.UtmConf.Payload;

import com.mli.mpro.location.UtmConf.models.MsgInfo;

public class UtmConfiguratorResponse {
    private MsgInfo msgInfo;
    private UtmConfiguratorResponsePayload payload;
    private String errors;

    public UtmConfiguratorResponse() {
    }

    public UtmConfiguratorResponse(String errors) { this.errors = errors; }

    public String getErrors() { return errors; }

    public void setErrors(String errors) { this.errors = errors;}

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public UtmConfiguratorResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(UtmConfiguratorResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "UtmConfiguratorResponse{" +
                "msgInfo=" + msgInfo +
                ", payload=" + payload +
                ", errors='" + errors + '\'' +
                '}';
    }

}
