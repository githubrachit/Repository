package com.mli.mpro.location.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sarthi_master")
public class SarthiMaster {

    private String agentId;
    @Sensitive(MaskType.NAME)
    private String agentName;
    private String officeCode;
    @Sensitive(MaskType.NAME)
    private String officeName;
    @Sensitive(MaskType.NAME)
    private String sarthiName;
    @Sensitive(MaskType.MOBILE)
    private String sarthiContactNumber;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getSarthiName() {
        return sarthiName;
    }

    public void setSarthiName(String sarthiName) {
        this.sarthiName = sarthiName;
    }

    public String getSarthiContactNumber() {
        return sarthiContactNumber;
    }

    public void setSarthiContactNumber(String sarthiContactNumber) {
        this.sarthiContactNumber = sarthiContactNumber;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "SarthiMaster{" +
                "agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", officeCode='" + officeCode + '\'' +
                ", officeName='" + officeName + '\'' +
                ", sarthiName='" + sarthiName + '\'' +
                ", sarthiContactNumber='" + sarthiContactNumber + '\'' +
                '}';
    }
}
