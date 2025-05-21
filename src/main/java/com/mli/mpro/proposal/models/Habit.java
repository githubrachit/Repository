
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "tobaccoNicotine", "liquor", "drugs" })
public class Habit {

    @JsonProperty("tobaccoNicotine")
    private TobaccoNicotine tobaccoNicotine;
    @JsonProperty("liquor")
    private Liquor liquor;
    @JsonProperty("drugs")
    private Drugs drugs;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Habit() {
    }

    /**
     * 
     * @param drugs
     * @param liquor
     * @param tobaccoNicotine
     */
    public Habit(TobaccoNicotine tobaccoNicotine, Liquor liquor, Drugs drugs) {
	super();
	this.tobaccoNicotine = tobaccoNicotine;
	this.liquor = liquor;
	this.drugs = drugs;
    }

    @JsonProperty("tobaccoNicotine")
    public TobaccoNicotine getTobaccoNicotine() {
	return tobaccoNicotine;
    }

    @JsonProperty("tobaccoNicotine")
    public void setTobaccoNicotine(TobaccoNicotine tobaccoNicotine) {
	this.tobaccoNicotine = tobaccoNicotine;
    }

    @JsonProperty("liquor")
    public Liquor getLiquor() {
	return liquor;
    }

    @JsonProperty("liquor")
    public void setLiquor(Liquor liquor) {
	this.liquor = liquor;
    }

    @JsonProperty("drugs")
    public Drugs getDrugs() {
	return drugs;
    }

    @JsonProperty("drugs")
    public void setDrugs(Drugs drugs) {
	this.drugs = drugs;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Habit [tobaccoNicotine=" + tobaccoNicotine + ", liquor=" + liquor + ", drugs=" + drugs + "]";
    }

}
