package com.mli.mpro.agentSelf.DataLake;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class InputRequest {
    @JsonProperty("request")
    private TokenRequest tokenRequest;

    public TokenRequest getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(TokenRequest tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "InputRequest{" +
                "tokenRequest=" + tokenRequest +
                '}';
    }
}
