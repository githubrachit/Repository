package com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.brmsBroker.model.Header;
import com.mli.mpro.utils.Utility;

public class Request {

    @JsonProperty("header")
    private Header header;

    @JsonProperty("payload")
    private RequestPayload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public RequestPayload getPayload() {
        return payload;
    }

    public void setPayload(RequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Request{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
