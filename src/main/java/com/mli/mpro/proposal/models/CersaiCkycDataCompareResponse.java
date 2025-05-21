package com.mli.mpro.proposal.models;

import com.mli.mpro.utils.Utility;

public class CersaiCkycDataCompareResponse {

  private String communicationAddressModified;
  private String permAddressModified;
  private String applicantNameModified;
  private String fatherNameModified;
  private String motherNameModified;
  private String dateOfBirthModified;
  private String genderModified;
  private String maritalStatusModified;
  private String citizenshipModified;
  private String occupationModified;
  private String residentialStatusModified;
  private String mobileNumberModified;
  private String telNumberModified;
  private String residenceNumberModified;
  private String faxNumberModified;
  private String emailIdModified;
  private String docsPassedInProofOfId;
  private String docsPassedInProofOfAddress;
  private String docsPassedInRecentPhoto;
  private String idModificationStatus;


  public String getCommunicationAddressModified() {
    return communicationAddressModified;
  }

  public void setCommunicationAddressModified(String communicationAddressModified) {
    this.communicationAddressModified = communicationAddressModified;
  }

  public String getDocsPassedInProofOfId() {
    return docsPassedInProofOfId;
  }

  public void setDocsPassedInProofOfId(String docsPassedInProofOfId) {
    this.docsPassedInProofOfId = docsPassedInProofOfId;
  }

  public String getDocsPassedInProofOfAddress() {
    return docsPassedInProofOfAddress;
  }

  public void setDocsPassedInProofOfAddress(String docsPassedInProofOfAddress) {
    this.docsPassedInProofOfAddress = docsPassedInProofOfAddress;
  }

  public String getDocsPassedInRecentPhoto() {
    return docsPassedInRecentPhoto;
  }

  public void setDocsPassedInRecentPhoto(String docsPassedInRecentPhoto) {
    this.docsPassedInRecentPhoto = docsPassedInRecentPhoto;
  }

  public String getIdModificationStatus() {
    return idModificationStatus;
  }

  public void setIdModificationStatus(String idModificationStatus) {
    this.idModificationStatus = idModificationStatus;
  }


  public String getPermAddressModified() {
    return permAddressModified;
  }

  public void setPermAddressModified(String permAddressModified) {
    this.permAddressModified = permAddressModified;
  }

  public String getApplicantNameModified() {
    return applicantNameModified;
  }

  public void setApplicantNameModified(String applicantNameModified) {
    this.applicantNameModified = applicantNameModified;
  }

  public String getFatherNameModified() {
    return fatherNameModified;
  }

  public void setFatherNameModified(String fatherNameModified) {
    this.fatherNameModified = fatherNameModified;
  }

  public String getMotherNameModified() {
    return motherNameModified;
  }

  public void setMotherNameModified(String motherNameModified) {
    this.motherNameModified = motherNameModified;
  }

  public String getDateOfBirthModified() {
    return dateOfBirthModified;
  }

  public void setDateOfBirthModified(String dateOfBirthModified) {
    this.dateOfBirthModified = dateOfBirthModified;
  }

  public String getGenderModified() {
    return genderModified;
  }

  public void setGenderModified(String genderModified) {
    this.genderModified = genderModified;
  }

  public String getMaritalStatusModified() {
    return maritalStatusModified;
  }

  public void setMaritalStatusModified(String maritalStatusModified) {
    this.maritalStatusModified = maritalStatusModified;
  }

  public String getCitizenshipModified() {
    return citizenshipModified;
  }

  public void setCitizenshipModified(String citizenshipModified) {
    this.citizenshipModified = citizenshipModified;
  }

  public String getOccupationModified() {
    return occupationModified;
  }

  public void setOccupationModified(String occupationModified) {
    this.occupationModified = occupationModified;
  }

  public String getResidentialStatusModified() {
    return residentialStatusModified;
  }

  public void setResidentialStatusModified(String residentialStatusModified) {
    this.residentialStatusModified = residentialStatusModified;
  }

  public String getMobileNumberModified() {
    return mobileNumberModified;
  }

  public void setMobileNumberModified(String mobileNumberModified) {
    this.mobileNumberModified = mobileNumberModified;
  }

  public String getTelNumberModified() {
    return telNumberModified;
  }

  public void setTelNumberModified(String telNumberModified) {
    this.telNumberModified = telNumberModified;
  }

  public String getResidenceNumberModified() {
    return residenceNumberModified;
  }

  public void setResidenceNumberModified(String residenceNumberModified) {
    this.residenceNumberModified = residenceNumberModified;
  }

  public String getFaxNumberModified() {
    return faxNumberModified;
  }

  public void setFaxNumberModified(String faxNumberModified) {
    this.faxNumberModified = faxNumberModified;
  }

  public String getEmailIdModified() {
    return emailIdModified;
  }

  public void setEmailIdModified(String emailIdModified) {
    this.emailIdModified = emailIdModified;
  }

  @Override
  public String toString() {
    if(Utility.isCalledFromLogs(Thread.currentThread())){
      return Utility.toString(this);
    }
    return "CersaiCkycDataCompareResponse{" +
            "communicationAddressModified='" + communicationAddressModified + '\'' +
            ", permAddressModified='" + permAddressModified + '\'' +
            ", applicantNameModified='" + applicantNameModified + '\'' +
            ", fatherNameModified='" + fatherNameModified + '\'' +
            ", motherNameModified='" + motherNameModified + '\'' +
            ", dateOfBirthModified='" + dateOfBirthModified + '\'' +
            ", genderModified='" + genderModified + '\'' +
            ", maritalStatusModified='" + maritalStatusModified + '\'' +
            ", citizenshipModified='" + citizenshipModified + '\'' +
            ", occupationModified='" + occupationModified + '\'' +
            ", residentialStatusModified='" + residentialStatusModified + '\'' +
            ", mobileNumberModified='" + mobileNumberModified + '\'' +
            ", telNumberModified='" + telNumberModified + '\'' +
            ", residenceNumberModified='" + residenceNumberModified + '\'' +
            ", faxNumberModified='" + faxNumberModified + '\'' +
            ", emailIdModified='" + emailIdModified + '\'' +
            ", docsPassedInProofOfId='" + docsPassedInProofOfId + '\'' +
            ", docsPassedInProofOfAddress='" + docsPassedInProofOfAddress + '\'' +
            ", docsPassedInRecentPhoto='" + docsPassedInRecentPhoto + '\'' +
            ", idModificationStatus='" + idModificationStatus + '\'' +
            '}';
  }
}
