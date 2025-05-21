package com.mli.mpro.location.altfinInquiry.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AuthOutputResponse {
    @JsonProperty("payload")
    private AuthResponsePayload payload;

    public AuthResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(AuthResponsePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AuthOutputResponse{" +
                "payload=" + payload +
                '}';
    }
}
