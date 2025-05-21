package com.mli.mpro.document.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.document.utils.SellerConsentStatus;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "seller-consent-details")
public class SellerConsentDetails {

  private String uniqueId;
  private String channel;
  @Sensitive(MaskType.POLICY_NUM)
  private String policyNumber;
  private String referralCode;
  private String spCode;
  @Sensitive(MaskType.EMAIL)
  private String emailId;
  @Sensitive(MaskType.MOBILE)
  private String mobileNumber;
  private String agentId;
  private String agentPlace;
  private String solId;
  private SellerConsentStatus sellerConsentStatus;
  private String errorMessage;
  private Date consentInitiationTime;
  private Date createdDate;
  private Date lastModifiedDate;
  @Sensitive(MaskType.NAME)
  private String sellerName;
  @Sensitive(MaskType.FIRST_NAME)
  private String customerFirstName;
  @Sensitive(MaskType.MIDDLE_NAME)
  private String customerMiddleName;
  @Sensitive(MaskType.LAST_NAME)
  private String customerLastName;
  private String productId;
  private String productName;
  private String productType;
  private boolean sellerDisclosure;
  private int period;
  private String periodUnit;
  private List<SellerConsentQuestionnaire> sellerConsentQuestionnaire;

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getPolicyNumber() {
    return policyNumber;
  }

  public void setPolicyNumber(String policyNumber) {
    this.policyNumber = policyNumber;
  }

  public String getReferralCode() {
    return referralCode;
  }

  public void setReferralCode(String referralCode) {
    this.referralCode = referralCode;
  }

  public String getSpCode() {
    return spCode;
  }

  public void setSpCode(String spCode) {
    this.spCode = spCode;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getAgentId() {
    return agentId;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }

  public String getAgentPlace() {
    return agentPlace;
  }

  public void setAgentPlace(String agentPlace) {
    this.agentPlace = agentPlace;
  }

  public String getSolId() {
    return solId;
  }

  public void setSolId(String solId) {
    this.solId = solId;
  }

  public SellerConsentStatus getSellerConsentStatus() {
    return sellerConsentStatus;
  }

  public void setSellerConsentStatus(SellerConsentStatus sellerConsentStatus) {
    this.sellerConsentStatus = sellerConsentStatus;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Date getConsentInitiationTime() {
    return consentInitiationTime;
  }

  public void setConsentInitiationTime(Date consentInitiationTime) {
    this.consentInitiationTime = consentInitiationTime;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerMiddleName() {
    return customerMiddleName;
  }

  public void setCustomerMiddleName(String customerMiddleName) {
    this.customerMiddleName = customerMiddleName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public boolean getSellerDisclosure() { return sellerDisclosure; }

  public void setSellerDisclosure(boolean sellerDisclosure) { this.sellerDisclosure = sellerDisclosure; }

  public int getPeriod() { return period; }

  public void setPeriod(int period) { this.period = period; }

  public String getPeriodUnit() { return periodUnit; }

  public void setPeriodUnit(String periodUnit) { this.periodUnit = periodUnit; }

  public List<SellerConsentQuestionnaire> getSellerConsentQuestionnaire() { return sellerConsentQuestionnaire; }

  public void setSellerConsentQuestionnaire(List<SellerConsentQuestionnaire> sellerConsentQuestionnaire) {
    this.sellerConsentQuestionnaire = sellerConsentQuestionnaire; }

  @Override
  public String toString() {
    if(Utility.isCalledFromLogs(Thread.currentThread())){
      return Utility.toString(this);
    }
    return "SellerConsentDetails{" +
        "uniqueId='" + uniqueId + '\'' +
        ", channel='" + channel + '\'' +
        ", policyNumber='" + policyNumber + '\'' +
        ", referralCode='" + referralCode + '\'' +
        ", spCode='" + spCode + '\'' +
        ", emailId='" + emailId + '\'' +
        ", mobileNumber='" + mobileNumber + '\'' +
        ", agentId='" + agentId + '\'' +
        ", agentPlace='" + agentPlace + '\'' +
        ", solId='" + solId + '\'' +
        ", sellerConsentStatus=" + sellerConsentStatus +
        ", errorMessage='" + errorMessage + '\'' +
        ", consentInitiationTime=" + consentInitiationTime +
        ", createdDate=" + createdDate +
        ", lastModifiedDate=" + lastModifiedDate +
        ", sellerName='" + sellerName + '\'' +
        ", customerFirstName='" + customerFirstName + '\'' +
        ", customerMiddleName='" + customerMiddleName + '\'' +
        ", customerLastName='" + customerLastName + '\'' +
        ", productId='" + productId + '\'' +
        ", productName='" + productName + '\'' +
        ", productType='" + productType + '\'' +
        ", sellerDisclosure=" + sellerDisclosure +
        ", period=" + period +
        ", periodUnit='" + periodUnit + '\'' +
        ", sellerConsentQuestionnaire=" + sellerConsentQuestionnaire +
        '}';
  }
}

