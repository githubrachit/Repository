package com.mli.mpro.location.models.journeyQuestions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Questions {
    private String questionID;
    private String label;
    private String type;
    private String[] options;
    private String order;
    private List<Questions> subQuestion;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String category;

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Questions> getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(List<Questions> subQuestions) {
        this.subQuestion = subQuestions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "questionID='" + questionID + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", options=" + Arrays.toString(options) +
                ", order='" + order + '\'' +
                ", subQuestion=" + subQuestion +
                ", parentId='" + parentId + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
