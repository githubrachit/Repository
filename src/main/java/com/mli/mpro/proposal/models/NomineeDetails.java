
package com.mli.mpro.proposal.models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "partyDetails" })
public class NomineeDetails {

    @JsonProperty("partyDetails")
    private List<PartyDetails> partyDetails = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NomineeDetails(NomineeDetails nomineeDetails) {
	
	if(nomineeDetails!=null)
	{
	List<PartyDetails> partyDetailsList = nomineeDetails.partyDetails;
	partyDetailsList = (partyDetailsList != null && partyDetailsList.size() != 0) ? partyDetailsList.stream().collect(Collectors.toList())
		: partyDetailsList;
	this.partyDetails = partyDetailsList;
    }
    }

    /**
     * 
     * @param partyDetails
     */
    public NomineeDetails(List<PartyDetails> partyDetails) {
	super();
	this.partyDetails = partyDetails;
    }

    public NomineeDetails() {}

    @JsonProperty("partyDetails")
    public List<PartyDetails> getPartyDetails() {
	return partyDetails;
    }

    @JsonProperty("partyDetails")
    public void setPartyDetails(List<PartyDetails> partyDetails) {
	this.partyDetails = partyDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "NomineeDetails [partyDetails=" + partyDetails + "]";
    }

}
