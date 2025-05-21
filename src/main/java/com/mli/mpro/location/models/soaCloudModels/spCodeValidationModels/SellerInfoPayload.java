package com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.StringJoiner;

public class SellerInfoPayload {

  private String id;
  private String spNo;
  private String channel;
  private String branchCode;
  private String type;
  private String transTrackingId;
  private String raId;

  public SellerInfoPayload() {
  }

  public SellerInfoPayload(String id, String spNo, String channel, String branchCode, String type, String transTrackingId, String raId) {
    this.id = id;
    this.spNo = spNo;
    this.channel = channel;
    this.branchCode = branchCode;
    this.type = type;
    this.transTrackingId = transTrackingId;
    this.raId = raId;
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

  public String getTransTrackingId() {
    return transTrackingId;
  }

  public void setTransTrackingId(String transTrackingId) {
    this.transTrackingId = transTrackingId;
  }

  public String getRaId() {
    return raId;
  }

  public void setRaId(String raId) {
    this.raId = raId;
  }

  @Override
  public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
    return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
        .add("id = " + id)
        .add("spNo = " + spNo)
        .add("channel = " + channel)
        .add("branchCode = " + branchCode)
        .add("type = " + type)
        .add("transTrackingId = " + transTrackingId)
        .add("raId = " + raId)
        .toString();
  }
}
