package com.mli.mpro.agentSelfIdentifiedSkip;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.agentSelf.AgentCheck;
import com.mli.mpro.agentSelf.MliEmployeeCheck;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;


public class AgentSelfSoaApiResponse {

    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panMatched")
    private String panMatched;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("phoneNumberMatched")
    private String phoneNumberMatched;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailMatched")
    private String emailMatched;
    @JsonProperty("dbMatchType")
    private String dbMatchType;
    @JsonProperty("agentExistance")
    private String agentExistance;
    @JsonProperty("mliEmployeeExistance")
    private String mliEmployeeExistance;

    @JsonProperty("agentCheck")
    public ArrayList<AgentCheck> agentCheck;

    @JsonProperty("mliEmployeeCheck")
    public ArrayList<MliEmployeeCheck> mliEmployeeCheck;

    public ArrayList<MliEmployeeCheck> getMliEmployeeCheck() {
        return mliEmployeeCheck;
    }

    public void setMliEmployeeCheck(ArrayList<MliEmployeeCheck> mliEmployeeCheck) {
        this.mliEmployeeCheck = mliEmployeeCheck;
    }

    public ArrayList<AgentCheck> getAgentCheck() {
        return agentCheck;
    }
    public String getPanMatched() {
        return panMatched;
    }

    public void setPanMatched(String panMatched) {
        this.panMatched = panMatched;
    }

    public String getPhoneNumberMatched() {
        return phoneNumberMatched;
    }

    public void setPhoneNumberMatched(String phoneNumberMatched) {
        this.phoneNumberMatched = phoneNumberMatched;
    }

    public String getEmailMatched() {
        return emailMatched;
    }

    public void setEmailMatched(String emailMatched) {
        this.emailMatched = emailMatched;
    }

    public String getDbMatchType() {
        return dbMatchType;
    }

    public void setDbMatchType(String dbMatchType) {
        this.dbMatchType = dbMatchType;
    }

    public String getAgentExistance() {
        return agentExistance;
    }

    public void setAgentExistance(String agentExistance) {
        this.agentExistance = agentExistance;
    }

    public String getMliEmployeeExistance() {
        return mliEmployeeExistance;
    }

    public void setMliEmployeeExistance(String mliEmployeeExistance) {
        this.mliEmployeeExistance = mliEmployeeExistance;
    }
    public void setAgentCheck(ArrayList<AgentCheck> agentCheck) {
        this.agentCheck = agentCheck;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgentSelfSoaApiResponse{" +
                "panMatched='" + panMatched + '\'' +
                ", phoneNumberMatched='" + phoneNumberMatched + '\'' +
                ", emailMatched='" + emailMatched + '\'' +
                ", dbMatchType='" + dbMatchType + '\'' +
                ", agentExistance='" + agentExistance + '\'' +
                ", mliEmployeeExistance='" + mliEmployeeExistance + '\'' +
                ", agentCheck=" + agentCheck +
                ", mliEmployeeCheck=" + mliEmployeeCheck +
                '}';
    }
}
