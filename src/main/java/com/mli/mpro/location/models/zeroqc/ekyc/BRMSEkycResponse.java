package com.mli.mpro.location.models.zeroqc.ekyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BRMSEkycResponse {
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Output")
    private BRMSEkycOutput output;
    private List<String> rulesExecuted;

    //setters and getters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BRMSEkycOutput getOutput() {
        return output;
    }

    public void setOutput(BRMSEkycOutput output) {
        this.output = output;
    }

    public List<String> getRulesExecuted() {
        return rulesExecuted;
    }

    public void setRulesExecuted(List<String> rulesExecuted) {
        this.rulesExecuted = rulesExecuted;
    }

    //toString
    @Override
    public String toString() {
        return "BRMSEkycResponse{" +
                "status='" + status + '\'' +
                ", output=" + output +
                ", rulesExecuted=" + rulesExecuted +
                '}';
    }
}
