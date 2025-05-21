
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "phoneNumber", "type", "stdIsdCode", "phoneType" })
public class Phone {
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("stdIsdCode")
    private String stdIsdCode;
    @JsonProperty("phoneType")
    private String phoneType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Phone() {
    }

    /**
     * 
     * @param phoneNumber
     * @param phoneType
     * @param stdIsdCode
     */
    public Phone(String phoneNumber, String stdIsdCode, String phoneType) {
	super();
	this.phoneNumber = phoneNumber;

	this.stdIsdCode = stdIsdCode;
	this.phoneType = phoneType;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
	return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    @JsonProperty("stdIsdCode")
    public String getStdIsdCode() {
	return stdIsdCode;
    }

    @JsonProperty("stdIsdCode")
    public void setStdIsdCode(String stdIsdCode) {
	this.stdIsdCode = stdIsdCode;
    }

    @JsonProperty("phoneType")
    public String getPhoneType() {
	return phoneType;
    }

    @JsonProperty("phoneType")
    public void setPhoneType(String phoneType) {
	this.phoneType = phoneType;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "Phone [phoneNumber=" + phoneNumber + ",  stdIsdCode=" + stdIsdCode + ", phoneType=" + phoneType + "]";
    }

}
