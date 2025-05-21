
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "question", "userSelectedValue" })
public class QuestionSet {

    @JsonProperty("question")
    private String question;
    @JsonProperty("userSelectedValue")
    private String userSelectedValue;

    /**
     * No args constructor for use in serialization
     * 
     */
    public QuestionSet() {
    }

    /**
     * 
     * @param question
     * @param userSelectedValue
     */
    public QuestionSet(String question, String userSelectedValue) {
	super();
	this.question = question;
	this.userSelectedValue = userSelectedValue;
    }

    @JsonProperty("question")
    public String getQuestion() {
	return question;
    }

    @JsonProperty("question")
    public void setQuestion(String question) {
	this.question = question;
    }

    @JsonProperty("userSelectedValue")
    public String getUserSelectedValue() {
	return userSelectedValue;
    }

    @JsonProperty("userSelectedValue")
    public void setUserSelectedValue(String userSelectedValue) {
	this.userSelectedValue = userSelectedValue;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "QuestionSet [question=" + question + ", userSelectedValue=" + userSelectedValue + "]";
    }

}
