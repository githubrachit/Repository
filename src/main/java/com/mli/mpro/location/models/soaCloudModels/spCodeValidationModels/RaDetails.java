package com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class RaDetails {

  private String raId;
  private String raName;
  private String raStatus;
  private String roleCode;
  private String roleName;

  private String raPhoneNumber;
  private String raEmail;

  public String getRaId() {
    return raId;
  }

  public void setRaId(String raId) {
    this.raId = raId;
  }

  public String getRaName() {
    return raName;
  }

  public void setRaName(String raName) {
    this.raName = raName;
  }

  public String getRaStatus() {
    return raStatus;
  }

  public void setRaStatus(String raStatus) {
    this.raStatus = raStatus;
  }

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRaPhoneNumber() {
    return raPhoneNumber;
  }

  public void setRaPhoneNumber(String raPhoneNumber) {
    this.raPhoneNumber = raPhoneNumber;
  }

  public String getRaEmail() {
    return raEmail;
  }

  public void setRaEmail(String raEmail) {
    this.raEmail = raEmail;
  }

  @Override
  public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
    return "RaDetails{" +
            "raId='" + raId + '\'' +
            ", raName='" + raName + '\'' +
            ", raStatus='" + raStatus + '\'' +
            ", roleCode='" + roleCode + '\'' +
            ", roleName='" + roleName + '\'' +
            ", raEmail='" + raEmail + '\'' +
            ", raPhoneNumber='" + raPhoneNumber + '\'' +
            '}';
  }
}

