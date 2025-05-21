package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "questionId", "questionType", "category", "answerType", "answer", "parentId" })
public class PosvQuestion {

    @JsonProperty("questionId")
    private String questionId;
    @JsonProperty("questionType")
    private String questionType;
    @JsonProperty("category")
    private String category;
    @JsonProperty("answerType")
    private String answerType;
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("parentId")
    private String parentId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PosvQuestion() {
    }

    /**
     * 
     * @param parentId
     * @param category
     * @param questionId
     * @param answer
     * @param answerType
     * @param questionType
     */
    public PosvQuestion(String questionId, String questionType, String category, String answerType, String answer, String parentId) {
	super();
	this.questionId = questionId;
	this.questionType = questionType;
	this.category = category;
	this.answerType = answerType;
	this.answer = answer;
	this.parentId = parentId;
    }

    @JsonProperty("questionId")
    public String getQuestionId() {
	return questionId;
    }

    @JsonProperty("questionId")
    public void setQuestionId(String questionId) {
	this.questionId = questionId;
    }

    @JsonProperty("questionType")
    public String getQuestionType() {
	return questionType;
    }

    @JsonProperty("questionType")
    public void setQuestionType(String questionType) {
	this.questionType = questionType;
    }

    @JsonProperty("category")
    public String getCategory() {
	return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
	this.category = category;
    }

    @JsonProperty("answerType")
    public String getAnswerType() {
	return answerType;
    }

    @JsonProperty("answerType")
    public void setAnswerType(String answerType) {
	this.answerType = answerType;
    }

    @JsonProperty("answer")
    public String getAnswer() {
	return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(String answer) {
	this.answer = answer;
    }

    @JsonProperty("parentId")
    public String getParentId() {
	return parentId;
    }

    @JsonProperty("parentId")
    public void setParentId(String parentId) {
	this.parentId = parentId;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PosvQuestion [questionId=" + questionId + ", questionType=" + questionType + ", category=" + category + ", answerType=" + answerType
		+ ", answer=" + answer + ", parentId=" + parentId + "]";
    }

}