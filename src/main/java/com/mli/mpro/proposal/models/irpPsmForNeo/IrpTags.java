package com.mli.mpro.proposal.models.irpPsmForNeo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class IrpTags {
    @JsonProperty("fund")
    public ArrayList<Fund> fund;

    // getters and setters
    public ArrayList<Fund> getFund() {
        return fund;
    }

    public void setFund(ArrayList<Fund> fund) {
        this.fund = fund;
    }
    //toString
    @Override
    public String toString() {
        return "IrpTags [fund=" + fund + "]";
    }


}
