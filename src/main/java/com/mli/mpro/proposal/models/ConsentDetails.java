
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "digitalPromotionConsent", "vernaculerConsent", "disabilityConsent" })
public class ConsentDetails {


    @JsonProperty("digitalPromotionConsent")
    private String digitalPromotionConsent;


    @JsonProperty("vernaculerConsent")
    private String vernaculerConsent;

    @JsonProperty("disabilityConsent")
    private String disabilityConsent;


    /**
     * No args constructor for use in serialization
     *
     */
    public ConsentDetails() {
    }

    public ConsentDetails(String digitalPromotionConsent, String vernaculerConsent, String disabilityConsent) {
	super();
	this.digitalPromotionConsent = digitalPromotionConsent;
	this.vernaculerConsent = vernaculerConsent;
	this.disabilityConsent = disabilityConsent;
    }

    public ConsentDetails(ConsentDetails consentDetails) {
	if(consentDetails!=null)
	{
	this.digitalPromotionConsent = consentDetails.digitalPromotionConsent;
    this.vernaculerConsent = consentDetails.vernaculerConsent;
    this.disabilityConsent = consentDetails.disabilityConsent;
	}
	
    }

    public String getDigitalPromotionConsent() {
	return digitalPromotionConsent;
    }

    public void setDigitalPromotionConsent(String digitalPromotionConsent) {
	this.digitalPromotionConsent = digitalPromotionConsent;
    }

    public String getVernaculerConsent() {
	return vernaculerConsent;
    }

    public void setVernaculerConsent(String vernaculerConsent) {
	this.vernaculerConsent = vernaculerConsent;
    }

    public String getDisabilityConsent() {
	return disabilityConsent;
    }

    public void setDisabilityConsent(String disabilityConsent) {
	this.disabilityConsent = disabilityConsent;
    }


    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "ConsentDetails [digitalPromotionConsent=" + digitalPromotionConsent + ", vernaculerConsent=" + vernaculerConsent + ", disabilityConsent=" + disabilityConsent + "]";
    }

}
