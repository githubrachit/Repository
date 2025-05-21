package com.mli.mpro.agent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AgtAddDtlType{
    @JsonProperty("city") 
    public String getCity() { 
		 return this.city; } 
    public void setCity(String city) { 
		 this.city = city; }
    @Sensitive(MaskType.ADDRESS)
    String city;
    @JsonProperty("stateCd") 
    public String getStateCd() { 
		 return this.stateCd; } 
    public void setStateCd(String stateCd) { 
		 this.stateCd = stateCd; }
    @Sensitive(MaskType.ADDRESS)
    String stateCd;
    @JsonProperty("stateDesc") 
    public String getStateDesc() { 
		 return this.stateDesc; } 
    public void setStateDesc(String stateDesc) { 
		 this.stateDesc = stateDesc; } 
    String stateDesc;
    @JsonProperty("pinCode") 
    public String getPinCode() { 
		 return this.pinCode; } 
    public void setPinCode(String pinCode) { 
		 this.pinCode = pinCode; }
    @Sensitive(MaskType.PINCODE)
    String pinCode;
    @JsonProperty("AddrLine1") 
    public String getAddrLine1() { 
		 return this.addrLine1; } 
    public void setAddrLine1(String addrLine1) { 
		 this.addrLine1 = addrLine1; }
    @Sensitive(MaskType.ADDRESS)
    String addrLine1;
    @JsonProperty("AddrLine2") 
    public String getAddrLine2() { 
		 return this.addrLine2; } 
    public void setAddrLine2(String addrLine2) { 
		 this.addrLine2 = addrLine2; }
    @Sensitive(MaskType.ADDRESS)
    String addrLine2;
    @JsonProperty("AddrLine3") 
    public String getAddrLine3() { 
		 return this.addrLine3; } 
    public void setAddrLine3(String addrLine3) { 
		 this.addrLine3 = addrLine3; }
    @Sensitive(MaskType.ADDRESS)
    String addrLine3;
    @JsonProperty("AddrCategoryCd") 
    public String getAddrCategoryCd() { 
		 return this.addrCategoryCd; } 
    public void setAddrCategoryCd(String addrCategoryCd) { 
		 this.addrCategoryCd = addrCategoryCd; } 
    String addrCategoryCd;
    @JsonProperty("AddrCategoryDesc") 
    public String getAddrCategoryDesc() { 
		 return this.addrCategoryDesc; } 
    public void setAddrCategoryDesc(String addrCategoryDesc) { 
		 this.addrCategoryDesc = addrCategoryDesc; } 
    String addrCategoryDesc;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtAddDtlType{" +
                "city='" + city + '\'' +
                ", stateCd='" + stateCd + '\'' +
                ", stateDesc='" + stateDesc + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", addrLine1='" + addrLine1 + '\'' +
                ", addrLine2='" + addrLine2 + '\'' +
                ", addrLine3='" + addrLine3 + '\'' +
                ", addrCategoryCd='" + addrCategoryCd + '\'' +
                ", addrCategoryDesc='" + addrCategoryDesc + '\'' +
                '}';
    }
}
