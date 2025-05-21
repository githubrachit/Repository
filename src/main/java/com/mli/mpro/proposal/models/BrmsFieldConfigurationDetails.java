package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class BrmsFieldConfigurationDetails {
    @JsonProperty("productRestricted")
    private String productRestricted;
    @JsonProperty("productRestrictMessage")
    private String productRestrictMessage;
    @JsonProperty("campaignID")
    private String campaignID;
    @JsonProperty("campaignLocation")
    private String campaignLocation;
    @JsonProperty("custIdDataType")
    private String custIdDataType;
    @JsonProperty("custIdLength")
    private String custIdLength;
    @JsonProperty("customerID")
    private String customerID;
    @JsonProperty("form1")
    private String form1;
    @JsonProperty("form2")
    private String form2;
    @JsonProperty("formC")
    private String formC;
    @JsonProperty("digitalJourney")
    private String digitalJourney;
    @JsonProperty("isBRMSApiApplicable")
    private String isBRMSApiApplicable;
    @JsonProperty("pOSPJourney")
    private String pOSPJourney;
    @JsonProperty("indianNationaility")
    private String indianNationaility;
    @JsonProperty("nRInationality")
    private String nRInationality;
    @JsonProperty("lEChannelCode")
    private String lEChannelCode;
    @JsonProperty("lEChannelName")
    private String lEChannelName;
    @JsonProperty("productAnnualMode")
    private String productAnnualMode;
    @JsonProperty("productMinPrem")
    private String productMinPrem;
    @JsonProperty("productModeCode")
    private String productModeCode;
    @JsonProperty("productMonthlyMode")
    private String productMonthlyMode;
    @JsonProperty("productQuarterlyMode")
    private String productQuarterlyMode;
    @JsonProperty("productSemiMode")
    private String productSemiMode;
    @JsonProperty("cashOption")
    private String cashOption;
    @JsonProperty("chequeOption")
    private String chequeOption;
    @JsonProperty("dDOption")
    private String dDOption;
    @JsonProperty("directDebitMannualOption")
    private String directDebitMannualOption;
    @JsonProperty("onlineOption")
    private String onlineOption;
    @JsonProperty("renewalDirectDebit")
    private String renewalDirectDebit;
    @JsonProperty("renewalEnach")
    private String renewalEnach;
    @JsonProperty("renewalCheque")
    private String renewalCheque;
    @JsonProperty("renewalECS")
    private String renewalECS;
    @JsonProperty("splitPayment")
    private String splitPayment;
    @JsonProperty("medicalScheduling")
    private String medicalScheduling;
    @JsonProperty("dNDOption")
    private String dNDOption;
    @JsonProperty("sellerDeclarationonMob")
    private String sellerDeclarationonMob;
    @JsonProperty("auditRequestId")
    private String auditRequestId;
    @JsonProperty("serviceStatus")
    private String serviceStatus;
    @JsonProperty("campaignIDDataType")
    private String campaignIDDataType;
    @JsonProperty("campaignIDLength")
    private String campaignIDLength;
    @JsonProperty("campaignLocationDataType")
    private String campaignLocationDataType;
    @JsonProperty("campaignLocationLength")
    private String campaignLocationLength;
    @JsonProperty("jvBranding")
    private String jvBranding;


    @JsonProperty("productMinPremAnnual")
    private String productMinPremAnnual;

    @JsonProperty("productMinPremSemi")
    private String productMinPremSemi;

    @JsonProperty("productMinPremQuarterly")
    private String productMinPremQuarterly;

    @JsonProperty("productMinPremMonthly")
    private String productMinPremMonthly;

    @JsonProperty("refIdDataType")
    private String refIdDataType;
    @JsonProperty("refIdLength")
    private String refIdLength;
    @JsonProperty("referenceId")
    private String referenceId;

    public String getProductMinPremAnnual() {
        return productMinPremAnnual;
    }

    public void setProductMinPremAnnual(String productMinPremAnnual) {
        this.productMinPremAnnual = productMinPremAnnual;
    }

    public String getProductMinPremSemi() {
        return productMinPremSemi;
    }

    public void setProductMinPremSemi(String productMinPremSemi) {
        this.productMinPremSemi = productMinPremSemi;
    }

    public String getProductMinPremQuarterly() {
        return productMinPremQuarterly;
    }

    public void setProductMinPremQuarterly(String productMinPremQuarterly) {
        this.productMinPremQuarterly = productMinPremQuarterly;
    }

    public String getProductMinPremMonthly() {
        return productMinPremMonthly;
    }

    public void setProductMinPremMonthly(String productMinPremMonthly) {
        this.productMinPremMonthly = productMinPremMonthly;
    }


    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getAuditRequestId() {
        return auditRequestId;
    }

    public void setAuditRequestId(String auditRequestId) {
        this.auditRequestId = auditRequestId;
    }

    public String getProductRestricted() {
        return productRestricted;
    }

    public void setProductRestricted(String productRestricted) {
        this.productRestricted = productRestricted;
    }

    public String getProductRestrictMessage() {
        return productRestrictMessage;
    }

    public void setProductRestrictMessage(String productRestrictMessage) {
        this.productRestrictMessage = productRestrictMessage;
    }

    public String getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(String campaignID) {
        this.campaignID = campaignID;
    }

    public String getCampaignLocation() {
        return campaignLocation;
    }

    public void setCampaignLocation(String campaignLocation) {
        this.campaignLocation = campaignLocation;
    }

    public String getCustIdDataType() {
        return custIdDataType;
    }

    public void setCustIdDataType(String custIdDataType) {
        this.custIdDataType = custIdDataType;
    }

    public String getCustIdLength() {
        return custIdLength;
    }

    public void setCustIdLength(String custIdLength) {
        this.custIdLength = custIdLength;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getForm1() {
        return form1;
    }

    public void setForm1(String form1) {
        this.form1 = form1;
    }

    public String getForm2() {
        return form2;
    }

    public void setForm2(String form2) {
        this.form2 = form2;
    }

    public String getFormC() {
        return formC;
    }

    public void setFormC(String formC) {
        this.formC = formC;
    }

    public String getDigitalJourney() {
        return digitalJourney;
    }

    public void setDigitalJourney(String digitalJourney) {
        this.digitalJourney = digitalJourney;
    }

    public String getIsBRMSApiApplicable() {
        return isBRMSApiApplicable;
    }

    public void setIsBRMSApiApplicable(String isBRMSApiApplicable) {
        this.isBRMSApiApplicable = isBRMSApiApplicable;
    }

    public String getpOSPJourney() {
        return pOSPJourney;
    }

    public void setpOSPJourney(String pOSPJourney) {
        this.pOSPJourney = pOSPJourney;
    }

    public String getIndianNationaility() {
        return indianNationaility;
    }

    public void setIndianNationaility(String indianNationaility) {
        this.indianNationaility = indianNationaility;
    }

    public String getnRInationality() {
        return nRInationality;
    }

    public void setnRInationality(String nRInationality) {
        this.nRInationality = nRInationality;
    }

    public String getlEChannelCode() {
        return lEChannelCode;
    }

    public void setlEChannelCode(String lEChannelCode) {
        this.lEChannelCode = lEChannelCode;
    }

    public String getlEChannelName() {
        return lEChannelName;
    }

    public void setlEChannelName(String lEChannelName) {
        this.lEChannelName = lEChannelName;
    }

    public String getProductAnnualMode() {
        return productAnnualMode;
    }

    public void setProductAnnualMode(String productAnnualMode) {
        this.productAnnualMode = productAnnualMode;
    }

    public String getProductMinPrem() {
        return productMinPrem;
    }

    public void setProductMinPrem(String productMinPrem) {
        this.productMinPrem = productMinPrem;
    }

    public String getProductModeCode() {
        return productModeCode;
    }

    public void setProductModeCode(String productModeCode) {
        this.productModeCode = productModeCode;
    }

    public String getProductMonthlyMode() {
        return productMonthlyMode;
    }

    public void setProductMonthlyMode(String productMonthlyMode) {
        this.productMonthlyMode = productMonthlyMode;
    }

    public String getProductQuarterlyMode() {
        return productQuarterlyMode;
    }

    public void setProductQuarterlyMode(String productQuarterlyMode) {
        this.productQuarterlyMode = productQuarterlyMode;
    }

    public String getProductSemiMode() {
        return productSemiMode;
    }

    public void setProductSemiMode(String productSemiMode) {
        this.productSemiMode = productSemiMode;
    }

    public String getCashOption() {
        return cashOption;
    }

    public void setCashOption(String cashOption) {
        this.cashOption = cashOption;
    }

    public String getChequeOption() {
        return chequeOption;
    }

    public void setChequeOption(String chequeOption) {
        this.chequeOption = chequeOption;
    }

    public String getdDOption() {
        return dDOption;
    }

    public void setdDOption(String dDOption) {
        this.dDOption = dDOption;
    }

    public String getDirectDebitMannualOption() {
        return directDebitMannualOption;
    }

    public void setDirectDebitMannualOption(String directDebitMannualOption) {
        this.directDebitMannualOption = directDebitMannualOption;
    }

    public String getOnlineOption() {
        return onlineOption;
    }

    public void setOnlineOption(String onlineOption) {
        this.onlineOption = onlineOption;
    }

    public String getRenewalDirectDebit() {
        return renewalDirectDebit;
    }

    public void setRenewalDirectDebit(String renewalDirectDebit) {
        this.renewalDirectDebit = renewalDirectDebit;
    }

    public String getRenewalEnach() {
        return renewalEnach;
    }

    public void setRenewalEnach(String renewalEnach) {
        this.renewalEnach = renewalEnach;
    }

    public String getRenewalCheque() {
        return renewalCheque;
    }

    public void setRenewalCheque(String renewalCheque) {
        this.renewalCheque = renewalCheque;
    }

    public String getRenewalECS() {
        return renewalECS;
    }

    public void setRenewalECS(String renewalECS) {
        this.renewalECS = renewalECS;
    }

    public String getSplitPayment() {
        return splitPayment;
    }

    public void setSplitPayment(String splitPayment) {
        this.splitPayment = splitPayment;
    }

    public String getMedicalScheduling() {
        return medicalScheduling;
    }

    public void setMedicalScheduling(String medicalScheduling) {
        this.medicalScheduling = medicalScheduling;
    }

    public String getdNDOption() {
        return dNDOption;
    }

    public void setdNDOption(String dNDOption) {
        this.dNDOption = dNDOption;
    }

    public String getSellerDeclarationonMob() {
        return sellerDeclarationonMob;
    }

    public void setSellerDeclarationonMob(String sellerDeclarationonMob) {
        this.sellerDeclarationonMob = sellerDeclarationonMob;
    }

    public String getCampaignIDDataType() {
        return campaignIDDataType;
    }

    public void setCampaignIDDataType(String campaignIDDataType) {
        this.campaignIDDataType = campaignIDDataType;
    }

    public String getCampaignIDLength() {
        return campaignIDLength;
    }

    public void setCampaignIDLength(String campaignIDLength) {
        this.campaignIDLength = campaignIDLength;
    }

    public String getCampaignLocationDataType() {
        return campaignLocationDataType;
    }

    public void setCampaignLocationDataType(String campaignLocationDataType) {
        this.campaignLocationDataType = campaignLocationDataType;
    }

    public String getCampaignLocationLength() {
        return campaignLocationLength;
    }

    public void setCampaignLocationLength(String campaignLocationLength) {
        this.campaignLocationLength = campaignLocationLength;
    }

    public String getJvBranding() {
        return jvBranding;
    }

    public void setJvBranding(String jvBranding) {
        this.jvBranding = jvBranding;
    }

    public String getRefIdDataType() {
        return refIdDataType;
    }

    public void setRefIdDataType(String refIdDataType) {
        this.refIdDataType = refIdDataType;
    }

    public String getRefIdLength() {
        return refIdLength;
    }

    public void setRefIdLength(String refIdLength) {
        this.refIdLength = refIdLength;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "BrmsFieldConfigurationDetails{" +
                "productRestricted='" + productRestricted + '\'' +
                ", productRestrictMessage='" + productRestrictMessage + '\'' +
                ", campaignID='" + campaignID + '\'' +
                ", campaignLocation='" + campaignLocation + '\'' +
                ", custIdDataType='" + custIdDataType + '\'' +
                ", custIdLength='" + custIdLength + '\'' +
                ", customerID='" + customerID + '\'' +
                ", form1='" + form1 + '\'' +
                ", form2='" + form2 + '\'' +
                ", formC='" + formC + '\'' +
                ", digitalJourney='" + digitalJourney + '\'' +
                ", isBRMSApiApplicable='" + isBRMSApiApplicable + '\'' +
                ", pOSPJourney='" + pOSPJourney + '\'' +
                ", indianNationaility='" + indianNationaility + '\'' +
                ", nRInationality='" + nRInationality + '\'' +
                ", lEChannelCode='" + lEChannelCode + '\'' +
                ", lEChannelName='" + lEChannelName + '\'' +
                ", productAnnualMode='" + productAnnualMode + '\'' +
                ", productMinPrem='" + productMinPrem + '\'' +
                ", productModeCode='" + productModeCode + '\'' +
                ", productMonthlyMode='" + productMonthlyMode + '\'' +
                ", productQuarterlyMode='" + productQuarterlyMode + '\'' +
                ", productSemiMode='" + productSemiMode + '\'' +
                ", cashOption='" + cashOption + '\'' +
                ", chequeOption='" + chequeOption + '\'' +
                ", dDOption='" + dDOption + '\'' +
                ", directDebitMannualOption='" + directDebitMannualOption + '\'' +
                ", onlineOption='" + onlineOption + '\'' +
                ", renewalDirectDebit='" + renewalDirectDebit + '\'' +
                ", renewalEnach='" + renewalEnach + '\'' +
                ", renewalCheque='" + renewalCheque + '\'' +
                ", renewalECS='" + renewalECS + '\'' +
                ", splitPayment='" + splitPayment + '\'' +
                ", medicalScheduling='" + medicalScheduling + '\'' +
                ", dNDOption='" + dNDOption + '\'' +
                ", sellerDeclarationonMob='" + sellerDeclarationonMob + '\'' +
                ", auditRequestId='" + auditRequestId + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", campaignIDDataType='" + campaignIDDataType + '\'' +
                ", campaignIDLength='" + campaignIDLength + '\'' +
                ", campaignLocationDataType='" + campaignLocationDataType + '\'' +
                ", campaignLocationLength='" + campaignLocationLength + '\'' +
                ", jvBranding='" + jvBranding + '\'' +
                ", productMinPremAnnual='" + productMinPremAnnual + '\'' +
                ", productMinPremSemi='" + productMinPremSemi + '\'' +
                ", productMinPremQuarterly='" + productMinPremQuarterly + '\'' +
                ", productMinPremMonthly='" + productMinPremMonthly + '\'' +
                ", refIdDataType='" + refIdDataType + '\'' +
                ", refIdLength='" + refIdLength + '\'' +
                ", referenceId='" + referenceId + '\''+
                '}';
    }
}

