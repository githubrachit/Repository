package com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.List;

public class SellerResponsePayload {

  private List<RegistrationDetails> registrationDetails;
  private List<RaDetails> raDetails;
  private String id;
  private String spNo;
  private String channel;
  private String branchCode;
  private String type;

  public List<RegistrationDetails> getRegistrationDetails() {
    return registrationDetails;
  }

  public void setRegistrationDetails(List<RegistrationDetails> registrationDetails) {
    this.registrationDetails = registrationDetails;
  }

  public List<RaDetails> getRaDetails() {
    return raDetails;
  }

  public void setRaDetails(List<RaDetails> raDetails) {
    this.raDetails = raDetails;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSpNo() {
    return spNo;
  }

  public void setSpNo(String spNo) {
    this.spNo = spNo;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getBranchCode() {
    return branchCode;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
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
    return "ResponsePayload{" +
        "registrationDetails=" + registrationDetails +
        ", raDetails=" + raDetails +
        ", id='" + id + '\'' +
        ", spNo='" + spNo + '\'' +
        ", channel='" + channel + '\'' +
        ", branchCode='" + branchCode + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
