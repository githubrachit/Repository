package com.mli.mpro.location.models;

import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "companyDetailsMasterData")
public class CompanyDetailsMasterData {

    @JsonProperty("scripCode")
    private String scripCode;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("isinNo")
    private String isinNo;
    @JsonProperty("closePrice")
    private String closePrice;
    @JsonProperty("type")
    private String type;

    public CompanyDetailsMasterData(String scripCode, String companyName, String isinNo, String closePrice) {
	super();
	this.scripCode = scripCode;
	this.companyName = companyName;
	this.isinNo = isinNo;
	this.closePrice = closePrice;
    }

    public String getScripCode() {
	return scripCode;
    }

    public void setScripCode(String scripCode) {
	this.scripCode = scripCode;
    }

    public String getCompanyName() {
	return companyName;
    }

    public void setCompanyName(String companyName) {
	this.companyName = companyName;
    }

    public String getIsinNo() {
	return isinNo;
    }

    public void setIsinNo(String isinNo) {
	this.isinNo = isinNo;
    }

    public String getClosePrice() {
	return closePrice;
    }

    public void setClosePrice(String closePrice) {
	this.closePrice = closePrice;
    }

    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "CompanyDetailsMasterData [scripCode=" + scripCode + ", companyName=" + companyName + ", isinNo=" + isinNo + ", closePrice=" + closePrice
		+ ", type=" + type + "]";
    }

   

}
