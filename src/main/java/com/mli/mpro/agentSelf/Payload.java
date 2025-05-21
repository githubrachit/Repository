package com.mli.mpro.agentSelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import java.util.ArrayList;
public class Payload {
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
    private ArrayList<AgentCheck> agentCheck;
    @JsonProperty("mliEmployeeCheck")
    private MliEmployeeCheck mliEmployeeCheck;

    public void setAgentCheck(ArrayList<AgentCheck> agentCheck) {
        this.agentCheck = agentCheck;
    }


    public ArrayList<AgentCheck> getAgentCheck() {
        return agentCheck;
    }
    public MliEmployeeCheck getMliEmployeeCheck() {
        return mliEmployeeCheck;
    }

    public void setMliEmployeeCheck(MliEmployeeCheck mliEmployeeCheck) {
        this.mliEmployeeCheck = mliEmployeeCheck;
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

    @Override
    public String toString() {
        return "Payload{" +
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
