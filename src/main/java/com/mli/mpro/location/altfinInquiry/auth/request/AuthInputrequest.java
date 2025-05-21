package com.mli.mpro.location.altfinInquiry.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;


public class AuthInputrequest {
    @JsonProperty("metadata")
    private AuthMetadata metadata;

    @JsonProperty("payload")
    private AuthPayload authPayload;

    public AuthMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(AuthMetadata metadata) {
        this.metadata = metadata;
    }

    public AuthPayload getAuthPayload() {
        return authPayload;
    }

    public void setAuthPayload(AuthPayload authPayload) {
        this.authPayload = authPayload;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AuthInputrequest{" +
                "metadata=" + metadata +
                ", authPayload=" + authPayload +
                '}';
    }
}
