package com.mli.mpro.location.models.soaCloudModels.master360ResponseModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lifeinsuredwithtittle",
    "insuredDob",
    "insrdCliId",
    "insrdGender",
    "insuredOccupation"
})
public class LifeInsuredDetail
{

    @JsonProperty("lifeinsuredwithtittle")
    private String lifeinsuredwithtittle;
    @Sensitive(MaskType.DOB)
    @JsonProperty("insuredDob")
    private String insuredDob;
    @JsonProperty("insrdCliId")
    private String insrdCliId;

    @Sensitive(MaskType.GENDER)
    @JsonProperty("insrdGender")
    private String insrdGender;
    @JsonProperty("insuredOccupation")
    private String insuredOccupation;

    @JsonProperty("lifeinsuredwithtittle")
    public String getLifeinsuredwithtittle() {
        return lifeinsuredwithtittle;
    }

    @JsonProperty("lifeinsuredwithtittle")
    public void setLifeinsuredwithtittle(String lifeinsuredwithtittle) {
        this.lifeinsuredwithtittle = lifeinsuredwithtittle;
    }

    public LifeInsuredDetail withLifeinsuredwithtittle(String lifeinsuredwithtittle) {
        this.lifeinsuredwithtittle = lifeinsuredwithtittle;
        return this;
    }

    @JsonProperty("insuredDob")
    public String getInsuredDob() {
        return insuredDob;
    }

    @JsonProperty("insuredDob")
    public void setInsuredDob(String insuredDob) {
        this.insuredDob = insuredDob;
    }

    public LifeInsuredDetail withInsuredDob(String insuredDob) {
        this.insuredDob = insuredDob;
        return this;
    }

    @JsonProperty("insrdCliId")
    public String getInsrdCliId() {
        return insrdCliId;
    }

    @JsonProperty("insrdCliId")
    public void setInsrdCliId(String insrdCliId) {
        this.insrdCliId = insrdCliId;
    }

    public LifeInsuredDetail withInsrdCliId(String insrdCliId) {
        this.insrdCliId = insrdCliId;
        return this;
    }

    @JsonProperty("insrdGender")
    public String getInsrdGender() {
        return insrdGender;
    }

    @JsonProperty("insrdGender")
    public void setInsrdGender(String insrdGender) {
        this.insrdGender = insrdGender;
    }

    public LifeInsuredDetail withInsrdGender(String insrdGender) {
        this.insrdGender = insrdGender;
        return this;
    }

    @JsonProperty("insuredOccupation")
    public String getInsuredOccupation() {
        return insuredOccupation;
    }

    @JsonProperty("insuredOccupation")
    public void setInsuredOccupation(String insuredOccupation) {
        this.insuredOccupation = insuredOccupation;
    }

    public LifeInsuredDetail withInsuredOccupation(String insuredOccupation) {
        this.insuredOccupation = insuredOccupation;
        return this;
    }

	@Override
	public String toString() {

        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
		return "LifeInsuredDetail [lifeinsuredwithtittle=" + lifeinsuredwithtittle + ", insuredDob=" + insuredDob
				+ ", insrdCliId=" + insrdCliId + ", insrdGender=" + insrdGender + ", insuredOccupation="
				+ insuredOccupation + "]";
	}

  
}
