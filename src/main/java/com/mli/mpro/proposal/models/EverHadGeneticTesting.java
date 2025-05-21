package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EverHadGeneticTesting {

    @JsonProperty("everHadGenetictesting")
    private String everHadGenetictesting;
    @JsonProperty("specifyGeneticDetails")
    private String specifyGeneticDetails;

    public String getEverHadGenetictesting() {
        return everHadGenetictesting;
    }

    public void setEverHadGenetictesting(String everHadGenetictesting) {
        this.everHadGenetictesting = everHadGenetictesting;
    }

    public String getSpecifyGeneticDetails() {
        return specifyGeneticDetails;
    }

    public void setSpecifyGeneticDetails(String specifyGeneticDetails) {
        this.specifyGeneticDetails = specifyGeneticDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "EverHadGeneticTesting{" +
                "everHadGenetictesting='" + everHadGenetictesting + '\'' +
                ", specifyGeneticDetails='" + specifyGeneticDetails + '\'' +
                '}';
    }
}
