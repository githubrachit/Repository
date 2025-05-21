
package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cvgNum",
    "cvgFaceAmt",
    "origPlanIdDesc",
    "insuredCliCvg",
    "uwUserId",
    "cvgUwdcnCd",
    "cvgRateAge",
    "loading"
})
public class CoverageDetail implements Serializable
{

    @JsonProperty("cvgNum")
    private String cvgNum;
    @JsonProperty("cvgFaceAmt")
    private String cvgFaceAmt;
    @JsonProperty("origPlanIdDesc")
    private String origPlanIdDesc;
    @JsonProperty("insuredCliCvg")
    private String insuredCliCvg;
    @JsonProperty("uwUserId")
    private String uwUserId;
    @JsonProperty("cvgUwdcnCd")
    private String cvgUwdcnCd;
    @JsonProperty("cvgRateAge")
    private String cvgRateAge;
    @JsonProperty("loading")
    private String loading;
    private final static long serialVersionUID = -2333865794661813768L;

    @JsonProperty("cvgNum")
    public String getCvgNum() {
        return cvgNum;
    }

    @JsonProperty("cvgNum")
    public void setCvgNum(String cvgNum) {
        this.cvgNum = cvgNum;
    }

    public CoverageDetail withCvgNum(String cvgNum) {
        this.cvgNum = cvgNum;
        return this;
    }

    @JsonProperty("cvgFaceAmt")
    public String getCvgFaceAmt() {
        return cvgFaceAmt;
    }

    @JsonProperty("cvgFaceAmt")
    public void setCvgFaceAmt(String cvgFaceAmt) {
        this.cvgFaceAmt = cvgFaceAmt;
    }

    public CoverageDetail withCvgFaceAmt(String cvgFaceAmt) {
        this.cvgFaceAmt = cvgFaceAmt;
        return this;
    }

    @JsonProperty("origPlanIdDesc")
    public String getOrigPlanIdDesc() {
        return origPlanIdDesc;
    }

    @JsonProperty("origPlanIdDesc")
    public void setOrigPlanIdDesc(String origPlanIdDesc) {
        this.origPlanIdDesc = origPlanIdDesc;
    }

    public CoverageDetail withOrigPlanIdDesc(String origPlanIdDesc) {
        this.origPlanIdDesc = origPlanIdDesc;
        return this;
    }

    @JsonProperty("insuredCliCvg")
    public String getInsuredCliCvg() {
        return insuredCliCvg;
    }

    @JsonProperty("insuredCliCvg")
    public void setInsuredCliCvg(String insuredCliCvg) {
        this.insuredCliCvg = insuredCliCvg;
    }

    public CoverageDetail withInsuredCliCvg(String insuredCliCvg) {
        this.insuredCliCvg = insuredCliCvg;
        return this;
    }

    @JsonProperty("uwUserId")
    public String getUwUserId() {
        return uwUserId;
    }

    @JsonProperty("uwUserId")
    public void setUwUserId(String uwUserId) {
        this.uwUserId = uwUserId;
    }

    public CoverageDetail withUwUserId(String uwUserId) {
        this.uwUserId = uwUserId;
        return this;
    }

    @JsonProperty("cvgUwdcnCd")
    public String getCvgUwdcnCd() {
        return cvgUwdcnCd;
    }

    @JsonProperty("cvgUwdcnCd")
    public void setCvgUwdcnCd(String cvgUwdcnCd) {
        this.cvgUwdcnCd = cvgUwdcnCd;
    }

    public CoverageDetail withCvgUwdcnCd(String cvgUwdcnCd) {
        this.cvgUwdcnCd = cvgUwdcnCd;
        return this;
    }

    @JsonProperty("cvgRateAge")
    public String getCvgRateAge() {
        return cvgRateAge;
    }

    @JsonProperty("cvgRateAge")
    public void setCvgRateAge(String cvgRateAge) {
        this.cvgRateAge = cvgRateAge;
    }

    public CoverageDetail withCvgRateAge(String cvgRateAge) {
        this.cvgRateAge = cvgRateAge;
        return this;
    }

    @JsonProperty("loading")
    public String getLoading() {
        return loading;
    }

    @JsonProperty("loading")
    public void setLoading(String loading) {
        this.loading = loading;
    }

    public CoverageDetail withLoading(String loading) {
        this.loading = loading;
        return this;
    }

	@Override
	public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "CoverageDetail [cvgNum=" + cvgNum + ", cvgFaceAmt=" + cvgFaceAmt + ", origPlanIdDesc=" + origPlanIdDesc
				+ ", insuredCliCvg=" + insuredCliCvg + ", uwUserId=" + uwUserId + ", cvgUwdcnCd=" + cvgUwdcnCd
				+ ", cvgRateAge=" + cvgRateAge + ", loading=" + loading + "]";
	}

  

}
