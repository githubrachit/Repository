
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "consumeSubstance", "consumptionDetails" })
public class Drugs {

    @JsonProperty("consumeSubstance")
    private String consumeSubstance;
    @JsonProperty("consumptionDetails")
    private List<ConsumptionDetail> consumptionDetails = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Drugs() {
    }

    /**
     *
     * @param consumeSubstance
     * @param consumptionDetails
     */
    public Drugs(String consumeSubstance, List<ConsumptionDetail> consumptionDetails) {
	super();
	this.consumeSubstance = consumeSubstance;
	this.consumptionDetails = consumptionDetails;
    }

    @JsonProperty("consumeSubstance")
    public String getConsumeSubstance() {
	return consumeSubstance;
    }

    @JsonProperty("consumeSubstance")
    public void setConsumeSubstance(String consumeSubstance) {
	this.consumeSubstance = consumeSubstance;
    }

    @JsonProperty("consumptionDetails")
    public List<ConsumptionDetail> getConsumptionDetails() {
	return consumptionDetails;
    }

    @JsonProperty("consumptionDetails")
    public void setConsumptionDetails(List<ConsumptionDetail> consumptionDetails) {
	this.consumptionDetails = consumptionDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Drugs{" +
                "consumeSubstance='" + consumeSubstance + '\'' +
                ", consumptionDetails=" + consumptionDetails +
                '}';
    }
}
