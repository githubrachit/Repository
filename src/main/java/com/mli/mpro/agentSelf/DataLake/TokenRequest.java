package com.mli.mpro.agentSelf.DataLake;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class TokenRequest {
    @JsonProperty("payload")
    TokenRequestPayload tokenRequestPayload;
    @JsonProperty("header")
    TokenRequestHeader tokenRequestHeader;

    public TokenRequestPayload getTokenRequestPayload() {
        return tokenRequestPayload;
    }

    public void setTokenRequestPayload(TokenRequestPayload tokenRequestPayload) {
        this.tokenRequestPayload = tokenRequestPayload;
    }

    public TokenRequestHeader getTokenRequestHeader() {
        return tokenRequestHeader;
    }

    public void setTokenRequestHeader(TokenRequestHeader tokenRequestHeader) {
        this.tokenRequestHeader = tokenRequestHeader;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "TokenRequest{" +
                "tokenRequestPayload=" + tokenRequestPayload +
                ", tokenRequestHeader=" + tokenRequestHeader +
                '}';
    }
}
