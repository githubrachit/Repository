package com.mli.mpro.agentSelfIdentifiedSkip;

public class AgentSelfResponseData {
    public Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "AgentSelfResponseData{" +
                "payload=" + payload +
                '}';
    }
}
