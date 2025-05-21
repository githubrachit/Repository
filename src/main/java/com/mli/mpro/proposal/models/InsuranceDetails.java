
package com.mli.mpro.proposal.models;

import java.util.List;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isLICIPolicyExist", "isLIPolicyRejected", "liciPolicyDetails" })
public class InsuranceDetails {

    @JsonProperty("isLICIPolicyExist")
    private boolean isLICIPolicyExist;
    @JsonProperty("isLIPolicyRejected")
    private boolean isLIPolicyRejected;
    @JsonProperty("liciPolicyDetails")
    private List<LICIPolicyDetails> liciPolicyDetails = null;
    @JsonProperty("everLIIssuedPendingLapsed")
    private String everLIIssuedPendingLapsed;
    @JsonProperty("everLIIssuedPendingLapsedDetails")
    private List<LICIPolicyDetails> everLIIssuedPendingLapsedDetails = null;
    @JsonProperty("everLIRejectedPostponed")
    private String everLIRejectedPostponed;
    @JsonProperty("everLIRejectedPostponedDetails")
    private List<LICIPolicyDetails> everLIRejectedPostponedDetails = null;
    @JsonProperty("statusOfPolicy")
    private String statusOfPolicy;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalLifeSumAssured")
    private String totalLifeSumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalCISumAssured")
    private String totalCISumAssured;
    @JsonProperty("activeLIPolicySA")
    private  String activeLIPolicySA;
    @JsonProperty("iibDetails")
    private List<IibDetails> iibDetails = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InsuranceDetails() {
    }

    public InsuranceDetails(boolean isLICIPolicyExist, boolean isLIPolicyRejected, List<LICIPolicyDetails> liciPolicyDetails) {
	super();
	this.isLICIPolicyExist = isLICIPolicyExist;
	this.isLIPolicyRejected = isLIPolicyRejected;
	this.liciPolicyDetails = liciPolicyDetails;
    }

    public boolean isLICIPolicyExist() {
	return isLICIPolicyExist;
    }

    public void setLICIPolicyExist(boolean isLICIPolicyExist) {
	this.isLICIPolicyExist = isLICIPolicyExist;
    }

    public boolean isLIPolicyRejected() {
	return isLIPolicyRejected;
    }

    public void setLIPolicyRejected(boolean isLIPolicyRejected) {
	this.isLIPolicyRejected = isLIPolicyRejected;
    }

    public List<LICIPolicyDetails> getLiciPolicyDetails() {
	return liciPolicyDetails;
    }

    public void setLiciPolicyDetails(List<LICIPolicyDetails> liciPolicyDetails) {
	this.liciPolicyDetails = liciPolicyDetails;
    }

    public String getEverLIIssuedPendingLapsed() {
        return everLIIssuedPendingLapsed;
    }

    public InsuranceDetails setEverLIIssuedPendingLapsed(String everLIIssuedPendingLapsed) {
        this.everLIIssuedPendingLapsed = everLIIssuedPendingLapsed;
        return this;
    }

    public List<LICIPolicyDetails> getEverLIIssuedPendingLapsedDetails() {
        return everLIIssuedPendingLapsedDetails;
    }

    public InsuranceDetails setEverLIIssuedPendingLapsedDetails(List<LICIPolicyDetails> everLIIssuedPendingLapsedDetails) {
        this.everLIIssuedPendingLapsedDetails = everLIIssuedPendingLapsedDetails;
        return this;
    }

    public String getEverLIRejectedPostponed() {
        return everLIRejectedPostponed;
    }

    public InsuranceDetails setEverLIRejectedPostponed(String everLIRejectedPostponed) {
        this.everLIRejectedPostponed = everLIRejectedPostponed;
        return this;
    }

    public List<LICIPolicyDetails> getEverLIRejectedPostponedDetails() {
        return everLIRejectedPostponedDetails;
    }

    public InsuranceDetails setEverLIRejectedPostponedDetails(List<LICIPolicyDetails> everLIRejectedPostponedDetails) {
        this.everLIRejectedPostponedDetails = everLIRejectedPostponedDetails;
        return this;
    }

    public String getStatusOfPolicy() {
        return statusOfPolicy;
    }

    public InsuranceDetails setStatusOfPolicy(String statusOfPolicy) {
        this.statusOfPolicy = statusOfPolicy;
        return this;
    }

    public String getTotalLifeSumAssured() {
        return totalLifeSumAssured;
    }

    public InsuranceDetails setTotalLifeSumAssured(String totalLifeSumAssured) {
        this.totalLifeSumAssured = totalLifeSumAssured;
        return this;
    }

    public String getTotalCISumAssured() {
        return totalCISumAssured;
    }

    public InsuranceDetails setTotalCISumAssured(String totalCISumAssured) {
        this.totalCISumAssured = totalCISumAssured;
        return this;
    }

    public String getActiveLIPolicySA() {
        return activeLIPolicySA;
    }

    public InsuranceDetails setActiveLIPolicySA(String activeLIPolicySA) {
        this.activeLIPolicySA = activeLIPolicySA;
        return this;
    }

    public List<IibDetails> getIibDetails() {
        return iibDetails;
    }

    public void setIibDetails(List<IibDetails> iibDetails) {
        this.iibDetails = iibDetails;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", InsuranceDetails.class.getSimpleName() + "[", "]")
                .add("isLICIPolicyExist=" + isLICIPolicyExist)
                .add("isLIPolicyRejected=" + isLIPolicyRejected)
                .add("liciPolicyDetails=" + liciPolicyDetails)
                .add("everLIIssuedPendingLapsed='" + everLIIssuedPendingLapsed + "'")
                .add("everLIIssuedPendingLapsedDetails=" + everLIIssuedPendingLapsedDetails)
                .add("everLIRejectedPostponed='" + everLIRejectedPostponed + "'")
                .add("everLIRejectedPostponedDetails=" + everLIRejectedPostponedDetails)
                .add("statusOfPolicy='" + statusOfPolicy + "'")
                .add("totalLifeSumAssured='" + totalLifeSumAssured + "'")
                .add("totalCISumAssured='" + totalCISumAssured + "'")
                .add("activeLIPolicySA='" + activeLIPolicySA + "'")
                .add("iibDetails='" + iibDetails + "'")
                .toString();
    }
}