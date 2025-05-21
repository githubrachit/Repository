package com.mli.mpro.location.altfinInquiry.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.docsApp.models.MsgInfo;
import com.mli.mpro.location.altfinInquiry.input.AltfinMetadata;
import com.mli.mpro.utils.Utility;

public class AltfinSoaInputResponse {

    @JsonProperty("payload")
    private AltfinSoaPayload payload;
    @JsonProperty("metadata")
    private AltfinMetadata metadata;
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    public AltfinSoaPayload getPayload() {
        return payload;
    }

    public void setPayload(AltfinSoaPayload payload) {
        this.payload = payload;
    }

    public AltfinMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(AltfinMetadata metadata) {
        this.metadata = metadata;
    }

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AltfinSoaResponse{" +
                "payload=" + payload +
                ", metadata=" + metadata +
                ", msgInfo=" + msgInfo +
                '}';
    }
}
