package com.mli.mpro.location.models.journeyQuestions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Category {
    String categoryName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Questions> proposerPartyType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Questions> insuredPartyType;

    public Category(String categoryName, List<Questions> proposerPartyType, List<Questions> insuredPartyType) {
        this.categoryName = categoryName;
        this.proposerPartyType = proposerPartyType;
        this.insuredPartyType = insuredPartyType;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Questions> getProposerPartyType() {
        return proposerPartyType;
    }

    public void setProposerPartyType(List<Questions> proposerPartyType) {
        this.proposerPartyType = proposerPartyType;
    }

    public List<Questions> getInsuredPartyType() {
        return insuredPartyType;
    }

    public void setInsuredPartyType(List<Questions> insuredPartyType) {
        this.insuredPartyType = insuredPartyType;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", proposerPartyType=" + proposerPartyType +
                ", insuredPartyType=" + insuredPartyType +
                '}';
    }
}
