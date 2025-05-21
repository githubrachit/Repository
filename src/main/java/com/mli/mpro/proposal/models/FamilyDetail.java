
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "familyMember", "diagnosisAge", "condition" })
public class FamilyDetail {

    @JsonProperty("type")
    private String type;
    @JsonProperty("familyMember")
    private String familyMember;
    @JsonProperty("diagnosisAge")
    private String diagnosisAge;
    @JsonProperty("condition")
    private String condition;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FamilyDetail() {
    }

    /**
     * 
     * @param condition
     * @param familyMember
     * @param diagnosisAge
     * @param type
     */
    public FamilyDetail(String type, String familyMember, String diagnosisAge, String condition) {
	super();
	this.type = type;
	this.familyMember = familyMember;
	this.diagnosisAge = diagnosisAge;
	this.condition = condition;
    }

    @JsonProperty("type")
    public String getType() {
	return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
	this.type = type;
    }

    @JsonProperty("familyMember")
    public String getFamilyMember() {
	return familyMember;
    }

    @JsonProperty("familyMember")
    public void setFamilyMember(String familyMember) {
	this.familyMember = familyMember;
    }

    @JsonProperty("diagnosisAge")
    public String getDiagnosisAge() {
	return diagnosisAge;
    }

    @JsonProperty("diagnosisAge")
    public void setDiagnosisAge(String diagnosisAge) {
	this.diagnosisAge = diagnosisAge;
    }

    @JsonProperty("condition")
    public String getCondition() {
	return condition;
    }

    @JsonProperty("condition")
    public void setCondition(String condition) {
	this.condition = condition;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "FamilyDetail [type=" + type + ", familyMember=" + familyMember + ", diagnosisAge=" + diagnosisAge + ", condition=" + condition + "]";
    }

}
