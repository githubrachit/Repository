package com.mli.mpro.location.models.soaCloudModels;

import com.mli.mpro.agentSelfIdentifiedSkip.Header;

public class AuthRequest {
    private Header header;
    private AuthPayload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AuthPayload getPayload() {
        return payload;
    }

    public void setPayload(AuthPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
