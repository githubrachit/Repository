
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "neverBeenDiagnosedOrTreated", "diagnosedOrTreatedDetails" })
public class Health {

    @JsonProperty("neverBeenDiagnosedOrTreated")
    private String neverBeenDiagnosedOrTreated;
    @JsonProperty("diagnosedOrTreatedDetails")
    private DiagnosedOrTreatedDetails diagnosedOrTreatedDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Health() {
    }

    /**
     * 
     * @param neverBeenDiagnosedOrTreated
     * @param diagnosedOrTreatedDetails
     */
    public Health(String neverBeenDiagnosedOrTreated, DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
	super();
	this.neverBeenDiagnosedOrTreated = neverBeenDiagnosedOrTreated;
	this.diagnosedOrTreatedDetails = diagnosedOrTreatedDetails;
    }

    @JsonProperty("neverBeenDiagnosedOrTreated")
    public String getNeverBeenDiagnosedOrTreated() {
	return neverBeenDiagnosedOrTreated;
    }

    @JsonProperty("neverBeenDiagnosedOrTreated")
    public void setNeverBeenDiagnosedOrTreated(String neverBeenDiagnosedOrTreated) {
	this.neverBeenDiagnosedOrTreated = neverBeenDiagnosedOrTreated;
    }

    @JsonProperty("diagnosedOrTreatedDetails")
    public DiagnosedOrTreatedDetails getDiagnosedOrTreatedDetails() {
	return diagnosedOrTreatedDetails;
    }

    @JsonProperty("diagnosedOrTreatedDetails")
    public void setDiagnosedOrTreatedDetails(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
	this.diagnosedOrTreatedDetails = diagnosedOrTreatedDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Health [neverBeenDiagnosedOrTreated=" + neverBeenDiagnosedOrTreated + ", diagnosedOrTreatedDetails=" + diagnosedOrTreatedDetails + "]";
    }

}
