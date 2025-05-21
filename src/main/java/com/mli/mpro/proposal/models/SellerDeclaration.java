package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"declarationSubmitTime","sellerDisclosure","mhrSellerQuestions","isDeclarationCompleted","sellerConfirmation"})
public class SellerDeclaration {
    @JsonProperty("declarationSubmitTime")
    private Date declarationSubmitTime;
    @JsonProperty("sellerDisclosure")
    private String sellerDisclosure;
    @JsonProperty("mhrSellerQuestions")
    private List<SellerQuestion> mhrSellerQuestions;
    @JsonProperty("isDeclarationCompleted")
    private String isDeclarationCompleted;
    @JsonProperty("sellerConfirmation")
    private boolean sellerConfirmation;
    private int period;
    private String periodUnit;

    public Date getDeclarationSubmitTime() {
        return declarationSubmitTime;
    }

    public void setDeclarationSubmitTime(Date declarationSubmitTime) {
        this.declarationSubmitTime = declarationSubmitTime;
    }

    public String getSellerDisclosure() {
        return sellerDisclosure;
    }

    public void setSellerDisclosure(String sellerDisclosure) {
        this.sellerDisclosure = sellerDisclosure;
    }

    public List<SellerQuestion> getMhrSellerQuestions() {
        return mhrSellerQuestions;
    }

    public void setMhrSellerQuestions(List<SellerQuestion> mhrSellerQuestions) {
        this.mhrSellerQuestions = mhrSellerQuestions;
    }

    public String getIsDeclarationCompleted() {
        return isDeclarationCompleted;
    }

    public void setIsDeclarationCompleted(String isDeclarationCompleted) {
        this.isDeclarationCompleted = isDeclarationCompleted;
    }

    public boolean isSellerConfirmation() {
        return sellerConfirmation;
    }

    public void setSellerConfirmation(boolean sellerConfirmation) {
        this.sellerConfirmation = sellerConfirmation;
    }
    public int getPeriod() { return period; }

    public void setPeriod(int period) { this.period = period; }

    public String getPeriodUnit() { return periodUnit; }

    public void setPeriodUnit(String periodUnit) { this.periodUnit = periodUnit; }
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "SellerDeclaration{" +
                "declarationSubmitTime=" + declarationSubmitTime +
                ", sellerDisclosure='" + sellerDisclosure + '\'' +
                ", mhrSellerQuestions=" + mhrSellerQuestions +
                ", isDeclarationCompleted='" + isDeclarationCompleted + '\'' +
                ", sellerConfirmation='" + sellerConfirmation + '\'' +
                '}';
    }
}
