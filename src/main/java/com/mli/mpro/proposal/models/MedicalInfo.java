
package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "height", "familyDetails", "questionSet" })
public class MedicalInfo {

    @JsonProperty("height")
    private String height;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("familyDetails")
    private List<FamilyDetail> familyDetails = null;
    @JsonProperty("questionSet")
    private List<QuestionSet> questionSet = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MedicalInfo() {
    }

    /**
     * 
     * @param familyDetails
     * @param height
     * @param questionSet
     */
    public MedicalInfo(String height, List<FamilyDetail> familyDetails, List<QuestionSet> questionSet) {
	super();
	this.height = height;
	this.familyDetails = familyDetails;
	this.questionSet = questionSet;
    }

   

    public MedicalInfo(MedicalInfo medicalInfo) {
	if(medicalInfo!=null)
	{
	    
	    this.height = medicalInfo.height;
	  
	    
	}
	
    }

    @JsonProperty("height")
    public String getHeight() {
	return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
	this.height = height;
    }

    @JsonProperty("familyDetails")
    public List<FamilyDetail> getFamilyDetails() {
	return familyDetails;
    }

    @JsonProperty("familyDetails")
    public void setFamilyDetails(List<FamilyDetail> familyDetails) {
	this.familyDetails = familyDetails;
    }

    @JsonProperty("questionSet")
    public List<QuestionSet> getQuestionSet() {
	return questionSet;
    }

    @JsonProperty("questionSet")
    public void setQuestionSet(List<QuestionSet> questionSet) {
	this.questionSet = questionSet;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "MedicalInfo [height=" + height + ", familyDetails=" + familyDetails + ", questionSet=" + questionSet + "]";
    }

}
