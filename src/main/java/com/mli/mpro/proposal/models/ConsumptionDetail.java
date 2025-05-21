
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "frequency", "quantity", "numberOfYears" })
public class ConsumptionDetail {

    @JsonProperty("type")
    private String type;
    @JsonProperty("frequency")
    private String frequency;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("numberOfYears")
    private String numberOfYears;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ConsumptionDetail() {
    }

    /**
     * 
     * @param numberOfYears
     * @param quantity
     * @param frequency
     * @param type
     */
    public ConsumptionDetail(String type, String frequency, String quantity, String numberOfYears) {
	super();
	this.type = type;
	this.frequency = frequency;
	this.quantity = quantity;
	this.numberOfYears = numberOfYears;
    }

    @JsonProperty("type")
    public String getType() {
	return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
	this.type = type;
    }

    @JsonProperty("frequency")
    public String getFrequency() {
	return frequency;
    }

    @JsonProperty("frequency")
    public void setFrequency(String frequency) {
	this.frequency = frequency;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
	return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
	this.quantity = quantity;
    }

    @JsonProperty("numberOfYears")
    public String getNumberOfYears() {
	return numberOfYears;
    }

    @JsonProperty("numberOfYears")
    public void setNumberOfYears(String numberOfYears) {
	this.numberOfYears = numberOfYears;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "ConsumptionDetail [type=" + type + ", frequency=" + frequency + ", quantity=" + quantity + ", numberOfYears=" + numberOfYears + "]";
    }

}
