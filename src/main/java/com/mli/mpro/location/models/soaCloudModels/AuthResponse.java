package com.mli.mpro.location.models.soaCloudModels;

import com.mli.mpro.agentSelfIdentifiedSkip.Header;

public class AuthResponse {
    private Header header;
    private TokenPayload payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TokenPayload getPayload() {
        return payload;
    }

    public void setPayload(TokenPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
