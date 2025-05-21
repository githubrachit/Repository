package com.mli.mpro.ccms.model;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.sms.models.RequestPayload;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;

public class Data {
    @Sensitive(MaskType.POLICY_NUM)
    private List<Map<String, String>> policyNumber;
    @Sensitive(MaskType.POLICY_NUM)
    private String proposalNumber;
    @Sensitive(MaskType.EMAIL)
    private String proposerEmailId1 = AppConstants.DUMMY_EMAIL_ID;
    @Sensitive(MaskType.MOBILE)
    private String proposerMobileNumber1;

    private String documentType;

    @Sensitive(MaskType.MOBILE)
    private String messageTo;

    @Sensitive(MaskType.NAME)
    private String customerName;

    @Sensitive(MaskType.FIRST_NAME)
    private String firstName;

    @Sensitive(MaskType.LAST_NAME)
    private String lastName;

    private String planName;

    @Sensitive(MaskType.MASK_ALL)
    private String paymentLink;

    @Sensitive(MaskType.BANK_ACC_NUM)
    private String accountNumber;

    private String transactionId;

    @Sensitive(MaskType.MASK_ALL)
    private String docLink;

    @Sensitive(MaskType.MASK_ALL)
    private String enachLink;

    private String stage;

    private String channel;

    private String labAddress;

    @Sensitive(MaskType.MASK_ALL)
    private String encodeUrl;

    private String preferredDateTime;

    @Sensitive(MaskType.MASK_ALL)
    private String otpValue;

    public Data() {
    }

    public Data(Data ccmsPayload){
        this.policyNumber = List.of(Map.of("proposalNumber", ccmsPayload.getProposalNumber()));
        this.documentType = ccmsPayload.getDocumentType();
        this.messageTo = ccmsPayload.getMessageTo();
        this.customerName = ccmsPayload.getCustomerName();
        this.firstName = ccmsPayload.getFirstName();
        this.lastName = ccmsPayload.getLastName();
        this.planName = ccmsPayload.getPlanName();
        this.paymentLink = ccmsPayload.getPaymentLink();
        this.accountNumber = ccmsPayload.getAccountNumber();
        this.transactionId = ccmsPayload.getTransactionId();
        this.docLink = ccmsPayload.getDocLink();
        this.enachLink = ccmsPayload.getEnachLink();
        this.stage = ccmsPayload.getStage();
        this.channel = ccmsPayload.getChannel();
        this.labAddress = ccmsPayload.getLabAddress();
        this.encodeUrl = ccmsPayload.getEncodeUrl();
        this.preferredDateTime = ccmsPayload.getPreferredDateTime();
        this.otpValue = ccmsPayload.getOtpValue();

    }

    public Data(List<Map<String, String>> policyNumber, String documentType, String messageTo, String customerName, String firstName, String lastName, String planName, String paymentLink, String accountNumber, String transactionId, String docLink, String enachLink, String stage, String channel, String labAddress, String encodeUrl, String preferredDateTime, String otpValue) {
        this.policyNumber = policyNumber;
        this.documentType = documentType;
        this.messageTo = messageTo;
        this.customerName = customerName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.planName = planName;
        this.paymentLink = paymentLink;
        this.accountNumber = accountNumber;
        this.transactionId = transactionId;
        this.docLink = docLink;
        this.enachLink = enachLink;
        this.stage = stage;
        this.channel = channel;
        this.labAddress = labAddress;
        this.encodeUrl = encodeUrl;
        this.preferredDateTime = preferredDateTime;
        this.otpValue = otpValue;
    }

    public Data(RequestPayload smsPayload) {
        this.policyNumber = List.of(Map.of("proposalNumber", smsPayload.getPolicyNumber()));
        this.documentType = smsPayload.getType();
        this.messageTo = smsPayload.getMessageTo();
        this.customerName = smsPayload.getCustomerName();
        this.planName = smsPayload.getProductName();
        this.paymentLink = smsPayload.getLink();
        this.accountNumber = smsPayload.getAccountNumber();
        this.transactionId = Objects.nonNull(smsPayload.getTransactionNumber())? String.valueOf(smsPayload.getTransactionNumber())
                : StringUtils.EMPTY;
        this.docLink = smsPayload.getDocLink();
        this.enachLink = smsPayload.getEnachLink();
        this.proposalNumber = smsPayload.getPolicyNumber();
        this.proposerMobileNumber1 = smsPayload.getMessageTo();
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getProposerEmailId1() {
        return proposerEmailId1;
    }

    public void setProposerEmailId1(String proposerEmailId1) {
        this.proposerEmailId1 = proposerEmailId1;
    }

    public String getProposerMobileNumber1() {
        return proposerMobileNumber1;
    }

    public void setProposerMobileNumber1(String proposerMobileNumber1) {
        this.proposerMobileNumber1 = proposerMobileNumber1;
    }

    public List<Map<String,String>> getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(List<Map<String,String>> policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDocLink() {
        return docLink;
    }

    public void setDocLink(String docLink) {
        this.docLink = docLink;
    }

    public String getEnachLink() {
        return enachLink;
    }

    public void setEnachLink(String enachLink) {
        this.enachLink = enachLink;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLabAddress() {
        return labAddress;
    }

    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    public String getEncodeUrl() {
        return encodeUrl;
    }

    public void setEncodeUrl(String encodeUrl) {
        this.encodeUrl = encodeUrl;
    }

    public String getPreferredDateTime() {
        return preferredDateTime;
    }

    public void setPreferredDateTime(String preferredDateTime) {
        this.preferredDateTime = preferredDateTime;
    }

    public String getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(String otpValue) {
        this.otpValue = otpValue;
    }

    @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Data{" +
                "policyNumber='" + policyNumber + '\'' +
                ", proposalNumber='" + proposalNumber + '\'' +
                ", proposerEmailId1='" + proposerEmailId1 + '\'' +
                ", proposerMobileNumber1='" + proposerMobileNumber1 + '\'' +
                ", documentType='" + documentType + '\'' +
                ", messageTo='" + messageTo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", planName='" + planName + '\'' +
                ", paymentLink='" + paymentLink + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", docLink='" + docLink + '\'' +
                ", enachLink='" + enachLink + '\'' +
                ", stage='" + stage + '\'' +
                ", channel='" + channel + '\'' +
                ", labAddress='" + labAddress + '\'' +
                ", encodeUrl='" + encodeUrl + '\'' +
                ", preferredDateTime='" + preferredDateTime + '\'' +
                ", otpValue='" + otpValue + '\'' +
                '}';
    }
}
