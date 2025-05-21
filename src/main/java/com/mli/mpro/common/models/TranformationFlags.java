package com.mli.mpro.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "passportNumberTPPFlag" })
public class TranformationFlags {

    @JsonProperty("passportNumberTPPFlag")
    private String passportNumberTPPFlag;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TranformationFlags() {
    }

    /**
     * 
     * @param passportNumberTPPFlag
     */
    public TranformationFlags(String passportNumberTPPFlag) {
	super();
	this.passportNumberTPPFlag = passportNumberTPPFlag;
    }

    public TranformationFlags(TranformationFlags tranformationFlags) {
	// TODO Auto-generated constructor stub
    }

    @JsonProperty("passportNumberTPPFlag")
    public String getPassportNumberTPPFlag() {
	return passportNumberTPPFlag;
    }

    @JsonProperty("passportNumberTPPFlag")
    public void setPassportNumberTPPFlag(String passportNumberTPPFlag) {
	this.passportNumberTPPFlag = passportNumberTPPFlag;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
	return "TranformationFlags [passportNumberTPPFlag=" + passportNumberTPPFlag + "]";
    }

}