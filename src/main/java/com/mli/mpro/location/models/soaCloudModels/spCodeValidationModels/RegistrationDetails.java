package com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class RegistrationDetails {

  @Sensitive(MaskType.NAME)
  private String agentName;
  private String registrationNo;
  private String registrationDate;
  private String expiryDate;
  private String agentJoinDate;
  @Sensitive(MaskType.PAN_NUM)
  private String agentPanNo;
  @Sensitive(MaskType.AADHAAR_NUM)
  private String agentAadhaarNo;
  @Sensitive(MaskType.MOBILE)
  private String agentContactNo;
  @Sensitive(MaskType.EMAIL)
  private String agentEmailId;
  @Sensitive(MaskType.DOB)
  private String agentDOB;
  private String agentID;

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public String getRegistrationNo() {
    return registrationNo;
  }

  public void setRegistrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getAgentJoinDate() {
    return agentJoinDate;
  }

  public void setAgentJoinDate(String agentJoinDate) {
    this.agentJoinDate = agentJoinDate;
  }

  public String getAgentPanNo() {
    return agentPanNo;
  }

  public void setAgentPanNo(String agentPanNo) {
    this.agentPanNo = agentPanNo;
  }

  public String getAgentAadhaarNo() {
    return agentAadhaarNo;
  }

  public void setAgentAadhaarNo(String agentAadhaarNo) {
    this.agentAadhaarNo = agentAadhaarNo;
  }

  public String getAgentContactNo() {
    return agentContactNo;
  }

  public void setAgentContactNo(String agentContactNo) {
    this.agentContactNo = agentContactNo;
  }

  public String getAgentEmailId() {
    return agentEmailId;
  }

  public void setAgentEmailId(String agentEmailId) {
    this.agentEmailId = agentEmailId;
  }

  public String getAgentDOB() {
    return agentDOB;
  }

  public void setAgentDOB(String agentDOB) {
    this.agentDOB = agentDOB;
  }

  public String getAgentID() {
    return agentID;
  }

  public void setAgentID(String agentID) {
    this.agentID = agentID;
  }
@Override
  public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
    return "RegistrationDetails[" +
            "agentName='" + agentName +
            ", registrationNo='" + registrationNo +
            ", registrationDate='" + registrationDate +
            ", expiryDate='" + expiryDate +
            ", agentJoinDate='" + agentJoinDate +
            ", agentPanNo='" + agentPanNo +
            ", agentAadhaarNo='" + agentAadhaarNo +
            ", agentContactNo='" + agentContactNo +
            ", agentEmailId='" + agentEmailId +
            ", agentDOB='" + agentDOB +
            ", agentID='" + agentID +
            ']';
  }
}
