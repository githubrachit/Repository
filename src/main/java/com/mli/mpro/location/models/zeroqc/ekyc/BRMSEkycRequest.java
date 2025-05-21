package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycRequest {
    @JsonProperty("Input")
    private BRMSEkycRequestInput input;
    @JsonProperty("Output")
    private BRMSEkycRequestOutput output;

    //setters and getters
    public BRMSEkycRequestInput getInput() {
        return input;
    }
    public void setInput(BRMSEkycRequestInput input) {
        this.input = input;
    }
    public BRMSEkycRequestOutput getOutput() {
        return output;
    }
    public void setOutput(BRMSEkycRequestOutput output) {
        this.output = output;
    }
    //toString
    @Override
    public String toString() {
        return "EkycRequest [input=" + input + ", output=" + output + "]";
    }
}
