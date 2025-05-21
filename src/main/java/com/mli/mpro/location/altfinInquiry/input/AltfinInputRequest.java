package com.mli.mpro.location.altfinInquiry.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class AltfinInputRequest {

    @JsonProperty("payload")
    private AltfinPayload payload;

    @JsonProperty("metadata")
    private AltfinMetadata metadata;

    public AltfinPayload getPayload() {
        return payload;
    }

    public void setPayload(AltfinPayload payload) {
        this.payload = payload;
    }

    public AltfinMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(AltfinMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AltfinRequest{" +
                "payload=" + payload +
                ", metadata=" + metadata +
                '}';
    }
}
