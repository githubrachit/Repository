package com.mli.mpro.agentSelf.DataLake;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class OutputResponse {
    @JsonProperty("response")
    private TokenResponse tokenResponse;

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "OutputResponse{" +
                "tokenResponse=" + tokenResponse +
                '}';
    }
}
