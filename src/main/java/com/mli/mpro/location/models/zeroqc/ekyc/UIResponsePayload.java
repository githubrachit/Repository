package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UIResponsePayload {
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Output")
    private BRMSEkycOutput output;

    public String getStatus() {
        return status;
    }

    public UIResponsePayload setStatus(String status) {
        this.status = status;
        return this;
    }

    public BRMSEkycOutput getOutput() {
        return output;
    }

    public UIResponsePayload setOutput(BRMSEkycOutput output) {
        this.output = output;
        return this;
    }

    @Override
    public String toString() {
        return "UIResponsePayload{" +
                "status='" + status + '\'' +
                ", output=" + output +
                '}';
    }
}
