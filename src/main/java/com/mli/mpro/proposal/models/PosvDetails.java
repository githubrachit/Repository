package com.mli.mpro.proposal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "posvStatus", "posvQuestions" })
public class PosvDetails {

    @JsonProperty("posvStatus")
    private PosvStatus posvStatus;
    @JsonProperty("posvQuestions")
    private List<PosvQuestion> posvQuestions = null;
    @JsonProperty("goGreen")
    private String goGreen;
    @JsonProperty("selectedLanguage")
    private String selectedLanguage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PosvDetails() {
    }

    /**
     * 
     * @param posvQuestions
     * @param posvStatus
     */
    public PosvDetails(PosvStatus posvStatus, List<PosvQuestion> posvQuestions) {
	super();
	this.posvStatus = posvStatus;
	this.posvQuestions = posvQuestions;
    }

    @JsonProperty("posvStatus")
    public PosvStatus getPosvStatus() {
	return posvStatus;
    }

    @JsonProperty("posvStatus")
    public void setPosvStatus(PosvStatus posvStatus) {
	this.posvStatus = posvStatus;
    }

    @JsonProperty("posvQuestions")
    public List<PosvQuestion> getPosvQuestions() {
	return posvQuestions;
    }

    @JsonProperty("posvQuestions")
    public void setPosvQuestions(List<PosvQuestion> posvQuestions) {
	this.posvQuestions = posvQuestions;
    }

    public String getGoGreen() {
        return goGreen;
    }

    public void setGoGreen(String goGreen) {
        this.goGreen = goGreen;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "PosvDetails{" +
                "posvStatus=" + posvStatus +
                ", posvQuestions=" + posvQuestions +
                ", goGreen='" + goGreen + '\'' +
                ", selectedLanguage='" + selectedLanguage + '\'' +
                '}';
    }
}