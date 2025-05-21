package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class AgtContDtlType{
    @JsonProperty("contactNum") 
    public String getContactNum() { 
		 return this.contactNum; } 
    public void setContactNum(String contactNum) { 
		 this.contactNum = contactNum; }
    @Sensitive(MaskType.MOBILE)
    String contactNum;
    @JsonProperty("emailId") 
    public String getEmailId() { 
		 return this.emailId; } 
    public void setEmailId(String emailId) { 
		 this.emailId = emailId; }
    @Sensitive(MaskType.EMAIL)
    String emailId;
    @JsonProperty("agentHomephone") 
    public String getAgentHomephone() { 
		 return this.agentHomephone; } 
    public void setAgentHomephone(String agentHomephone) { 
		 this.agentHomephone = agentHomephone; }
    @Sensitive(MaskType.MOBILE)
    String agentHomephone;
    @JsonProperty("agentMobile1") 
    public String getAgentMobile1() { 
		 return this.agentMobile1; } 
    public void setAgentMobile1(String agentMobile1) { 
		 this.agentMobile1 = agentMobile1; }
    @Sensitive(MaskType.MOBILE)
    String agentMobile1;
    @JsonProperty("agentMobile2") 
    public String getAgentMobile2() { 
		 return this.agentMobile2; } 
    public void setAgentMobile2(String agentMobile2) { 
		 this.agentMobile2 = agentMobile2; }
    @Sensitive(MaskType.MOBILE)
    String agentMobile2;

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtContDtlType{" +
                "contactNum='" + contactNum + '\'' +
                ", emailId='" + emailId + '\'' +
                ", agentHomephone='" + agentHomephone + '\'' +
                ", agentMobile1='" + agentMobile1 + '\'' +
                ", agentMobile2='" + agentMobile2 + '\'' +
                '}';
    }
}
