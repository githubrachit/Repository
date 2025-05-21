package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycOutput {
    @JsonProperty("ekyc_input")
    private BRMSEkycResponseInput ekycInput;
    @JsonProperty("ekyc_output")
    private BRMSEkycResponseOutput ekycOutput;

    //setters and getters
    public BRMSEkycResponseInput getEkycInput() {
        return ekycInput;
    }

    public void setEkycInput(BRMSEkycResponseInput ekycInput) {
        this.ekycInput = ekycInput;
    }

    public BRMSEkycResponseOutput getEkycOutput() {
        return ekycOutput;
    }

    public void setEkycOutput(BRMSEkycResponseOutput ekycOutput) {
        this.ekycOutput = ekycOutput;
    }

    //toString
    @Override
    public String toString() {
        return "BRMSEkycOutput{" +
                "ekycInput=" + ekycInput +
                ", ekycOutput=" + ekycOutput +
                '}';
    }
}
