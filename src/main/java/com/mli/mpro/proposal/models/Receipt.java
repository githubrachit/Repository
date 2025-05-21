
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "paymentType","paymentReferenceCode", "proposalNumber", "proposerName", "channelName", "premiumMode", "clientIp", "bankId", "currency",
        "transactionReferenceNumber", "bankMerchantId", "bankReferenceNumber", "businessType", "applicationName", "modeOfPayment", "intrumentOfPayment",
        "transactionId", "transactionIdMLI", "transactionDateTimeStamp", "paymentDate", "isSICheck", "receiptId", "amount", "paymentStatus", "instrumentNumber",
        "instrumentDate", "instrumentBank", "instrumentBankBranch", "myMoneyOnlineCheck","yblPaymentDetails" })
public class Receipt {

    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("paymentReferenceCode")
    private String paymentReferenceCode;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("proposalNumber")
    private String proposalNumber;
    @Sensitive(MaskType.NAME)
    @JsonProperty("proposerName")
    private String proposerName;
    @JsonProperty("channelName")
    private String channelName;
    @JsonProperty("premiumMode")
    private String premiumMode;
    @JsonProperty("clientIp")
    private String clientIp;
    @Sensitive(MaskType.BRANCH_CODE)
    @JsonProperty("bankId")
    private String bankId;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("transactionReferenceNumber")
    private String transactionReferenceNumber;
    @JsonProperty("bankMerchantId")
    private String bankMerchantId;
    @JsonProperty("bankReferenceNumber")
    private String bankReferenceNumber;
    @JsonProperty("businessType")
    private String businessType;
    @Sensitive(MaskType.NAME)
    @JsonProperty("applicationName")
    private String applicationName;
    @JsonProperty("intrumentOfPayment")
    private String intrumentOfPayment;
    @JsonProperty("transactionIdMLI")
    private String transactionIdMLI;
    @JsonProperty("transactionDateTimeStamp")
    private String transactionDateTimeStamp;
    @JsonProperty("paymentDate")
    private String paymentDate;
    @JsonProperty("isSICheck")
    private boolean isSICheck;
    @JsonProperty("receiptId")
    private String receiptId;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("paymentStatus")
    private String paymentStatus;
    @JsonProperty("instrumentNumber")
    private String instrumentNumber;
    @JsonProperty("instrumentDate")
    private String instrumentDate;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("instrumentBank")
    private String instrumentBank;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    @JsonProperty("instrumentBankBranch")
    private String instrumentBankBranch;
    @JsonProperty("modeOfPayment")
    private String modeOfPayment;
    @JsonProperty("isTermAccepted")
    private boolean isTermAccepted;
    @JsonProperty("paymentChequeDetails")
    private PaymentChequeDetails paymentChequeDetails;
    @JsonProperty("demandDraftDetails")
    private DemandDraftDetails demandDraftDetails;
    @JsonProperty("myMoneyOnlineStatus")
    private String myMoneyOnlineStatus;
    @JsonProperty("myMoneyOfflineStatus")
    private String myMoneyOfflineStatus;
    @JsonProperty("directPaymentDetails")
    private DirectPaymentDetails directPaymentDetails;
    @JsonProperty("yblPaymentDetails")
    private YBLPaymentDetails yblPaymentDetails;
    @JsonProperty("partnerPaymentDetails")
    private PartnerPaymentDetails partnerPaymentDetails;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("billdeskAuthStatus")
    private String billdeskAuthStatus;
    @JsonProperty("billDeskPaymentStatus")
    private String billDeskPaymentStatus;
    @JsonProperty("billDeskCustomerId")
    private String billDeskCustomerId;
    @JsonProperty("billDeskCustomerIdDummy")
    private String billDeskCustomerIdDummy;
    @JsonProperty("isSIOpted")
    private String isSIOpted ;
    @JsonProperty("cardType")
    private String cardType;
    @JsonProperty("itemCode")
    private String itemCode;
    @JsonProperty("isEmandate")
    private String isEmandate;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("actualAmountPaidByCustomer")
    private String actualAmountPaidByCustomer;


    /**
     * No args constructor for use in serialization
     */
    public Receipt() {
    }

    public Receipt(String paymentType, String paymentReferenceCode, String proposalNumber, String proposerName, String channelName, String premiumMode,
                   String clientIp, String bankId, String currency, String transactionReferenceNumber, String bankMerchantId, String bankReferenceNumber,
                   String businessType, String applicationName, String intrumentOfPayment, String transactionIdMLI, String transactionDateTimeStamp,
                   String paymentDate, boolean isSICheck, String receiptId, String amount, String paymentStatus, String instrumentNumber, String instrumentDate,
                   String instrumentBank, String instrumentBankBranch, String modeOfPayment, boolean isTermAccepted, PaymentChequeDetails paymentChequeDetails,
                   DemandDraftDetails demandDraftDetails, String myMoneyOnlineStatus, String myMoneyOfflineStatus, DirectPaymentDetails directPaymentDetails,
                   YBLPaymentDetails yblPaymentDetails, String customerId, String billdeskAuthStatus, String billDeskPaymentStatus, String billDeskCustomerId,
                   String billDeskCustomerIdDummy, String isSIOpted, String itemCode, String cardType, String isEmandate) {
        super();
        this.paymentType = paymentType;
        this.paymentReferenceCode = paymentReferenceCode;
        this.proposalNumber = proposalNumber;
        this.proposerName = proposerName;
        this.channelName = channelName;
        this.premiumMode = premiumMode;
        this.clientIp = clientIp;
        this.bankId = bankId;
        this.currency = currency;
        this.transactionReferenceNumber = transactionReferenceNumber;
        this.bankMerchantId = bankMerchantId;
        this.bankReferenceNumber = bankReferenceNumber;
        this.businessType = businessType;
        this.applicationName = applicationName;
        this.intrumentOfPayment = intrumentOfPayment;
        this.transactionIdMLI = transactionIdMLI;
        this.transactionDateTimeStamp = transactionDateTimeStamp;
        this.paymentDate = paymentDate;
        this.isSICheck = isSICheck;
        this.receiptId = receiptId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.instrumentNumber = instrumentNumber;
        this.instrumentDate = instrumentDate;
        this.instrumentBank = instrumentBank;
        this.instrumentBankBranch = instrumentBankBranch;
        this.modeOfPayment = modeOfPayment;
        this.isTermAccepted = isTermAccepted;
        this.paymentChequeDetails = paymentChequeDetails;
        this.demandDraftDetails = demandDraftDetails;
        this.myMoneyOnlineStatus = myMoneyOnlineStatus;
        this.myMoneyOfflineStatus = myMoneyOfflineStatus;
        this.directPaymentDetails = directPaymentDetails;
        this.yblPaymentDetails = yblPaymentDetails;
        this.customerId = customerId;
        this.billdeskAuthStatus = billdeskAuthStatus;
        this.billDeskPaymentStatus = billDeskPaymentStatus;
        this.billDeskCustomerId = billDeskCustomerId;
        this.billDeskCustomerIdDummy = billDeskCustomerIdDummy;
        this.isSIOpted = isSIOpted;
        this.itemCode = itemCode;
        this.cardType = cardType;
        this.isEmandate = isEmandate;
        this.actualAmountPaidByCustomer = actualAmountPaidByCustomer;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentReferenceCode() {
        return paymentReferenceCode;
    }

    public void setPaymentReferenceCode(String paymentReferenceCode) {
        this.paymentReferenceCode = paymentReferenceCode;
    }


    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPremiumMode() {
        return premiumMode;
    }

    public void setPremiumMode(String premiumMode) {
        this.premiumMode = premiumMode;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    public String getBankMerchantId() {
        return bankMerchantId;
    }

    public void setBankMerchantId(String bankMerchantId) {
        this.bankMerchantId = bankMerchantId;
    }

    public String getBankReferenceNumber() {
        return bankReferenceNumber;
    }

    public void setBankReferenceNumber(String bankReferenceNumber) {
        this.bankReferenceNumber = bankReferenceNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIntrumentOfPayment() {
        return intrumentOfPayment;
    }

    public void setIntrumentOfPayment(String intrumentOfPayment) {
        this.intrumentOfPayment = intrumentOfPayment;
    }

    public String getTransactionIdMLI() {
        return transactionIdMLI;
    }

    public void setTransactionIdMLI(String transactionIdMLI) {
        this.transactionIdMLI = transactionIdMLI;
    }

    public String getTransactionDateTimeStamp() {
        return transactionDateTimeStamp;
    }

    public void setTransactionDateTimeStamp(String transactionDateTimeStamp) {
        this.transactionDateTimeStamp = transactionDateTimeStamp;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isSICheck() {
        return isSICheck;
    }

    public void setSICheck(boolean isSICheck) {
        this.isSICheck = isSICheck;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInstrumentNumber() {
        return instrumentNumber;
    }

    public void setInstrumentNumber(String instrumentNumber) {
        this.instrumentNumber = instrumentNumber;
    }

    public String getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(String instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public String getInstrumentBank() {
        return instrumentBank;
    }

    public void setInstrumentBank(String instrumentBank) {
        this.instrumentBank = instrumentBank;
    }

    public String getInstrumentBankBranch() {
        return instrumentBankBranch;
    }

    public void setInstrumentBankBranch(String instrumentBankBranch) {
        this.instrumentBankBranch = instrumentBankBranch;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public PaymentChequeDetails getPaymentChequeDetails() {
        return paymentChequeDetails;
    }

    public void setPaymentChequeDetails(PaymentChequeDetails paymentChequeDetails) {
        this.paymentChequeDetails = paymentChequeDetails;
    }

    public DemandDraftDetails getDemandDraftDetails() {
        return demandDraftDetails;
    }

    public void setDemandDraftDetails(DemandDraftDetails demandDraftDetails) {
        this.demandDraftDetails = demandDraftDetails;
    }

    public boolean isTermAccepted() {
        return isTermAccepted;
    }

    public void setTermAccepted(boolean isTermAccepted) {
        this.isTermAccepted = isTermAccepted;
    }

    public String getMyMoneyOnlineStatus() {
        return myMoneyOnlineStatus;
    }

    public void setMyMoneyOnlineStatus(String myMoneyOnlineStatus) {
        this.myMoneyOnlineStatus = myMoneyOnlineStatus;
    }

    public String getMyMoneyOfflineStatus() {
        return myMoneyOfflineStatus;
    }

    public void setMyMoneyOfflineStatus(String myMoneyOfflineStatus) {
        this.myMoneyOfflineStatus = myMoneyOfflineStatus;
    }

    public DirectPaymentDetails getDirectPaymentDetails() {
        return directPaymentDetails;
    }

    public void setDirectPaymentDetails(DirectPaymentDetails directPaymentDetails) {
        this.directPaymentDetails = directPaymentDetails;
    }

    public YBLPaymentDetails getYblPaymentDetails() {
        return yblPaymentDetails;
    }

    public void setYblPaymentDetails(YBLPaymentDetails yblPaymentDetails) {
        this.yblPaymentDetails = yblPaymentDetails;
    }

    public String getBilldeskAuthStatus() {
        return billdeskAuthStatus;
    }

    public void setBilldeskAuthStatus(String billdeskAuthStatus) {
        this.billdeskAuthStatus = billdeskAuthStatus;
    }

    public String getBillDeskPaymentStatus() {
        return billDeskPaymentStatus;
    }

    public void setBillDeskPaymentStatus(String billDeskPaymentStatus) {
        this.billDeskPaymentStatus = billDeskPaymentStatus;
    }

    public String getBillDeskCustomerId() {
        return billDeskCustomerId;
    }

    public void setBillDeskCustomerId(String billDeskCustomerId) {
        this.billDeskCustomerId = billDeskCustomerId;
    }



    public String getBillDeskCustomerIdDummy() {
        return billDeskCustomerIdDummy;
    }

    public void setBillDeskCustomerIdDummy(String billDeskCustomerIdDummy) {
        this.billDeskCustomerIdDummy = billDeskCustomerIdDummy;
    }

    public String getIsSIOpted() {
        return isSIOpted;
    }

    public void setIsSIOpted(String isSIOpted) {
        this.isSIOpted = isSIOpted;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getIsEmandate() { return isEmandate; }

    public void setIsEmandate(String isEmandate) { this.isEmandate = isEmandate; }

    public String getActualAmountPaidByCustomer() {
        return actualAmountPaidByCustomer;
    }

    public void setActualAmountPaidByCustomer(String actualAmountPaidByCustomer) {
        this.actualAmountPaidByCustomer = actualAmountPaidByCustomer;
    }

    public PartnerPaymentDetails getPartnerPaymentDetails() {
        return partnerPaymentDetails;
    }

    public void setPartnerPaymentDetails(PartnerPaymentDetails partnerPaymentDetails) {
        this.partnerPaymentDetails = partnerPaymentDetails;
    }

    @Override
	public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
		return "Receipt [paymentType=" + paymentType + ", paymentReferenceCode=" + paymentReferenceCode
				+ ", proposalNumber=" + proposalNumber + ", proposerNumber=" + proposerName + ", channelName="
				+ channelName + ", premiumMode=" + premiumMode + ", clientIp=" + clientIp + ", bankId=" + bankId
				+ ", currency=" + currency + ", transactionReferenceNumber=" + transactionReferenceNumber
				+ ", bankMerchantId=" + bankMerchantId + ", bankReferenceNumber=" + bankReferenceNumber
				+ ", businessType=" + businessType + ", applicationName=" + applicationName + ", intrumentOfPayment="
				+ intrumentOfPayment + ", transactionIdMLI=" + transactionIdMLI + ", transactionDateTimeStamp="
				+ transactionDateTimeStamp + ", paymentDate=" + paymentDate + ", isSICheck=" + isSICheck
				+ ", receiptId=" + receiptId + ", amount=" + amount + ", paymentStatus=" + paymentStatus
				+ ", instrumentNumber=" + instrumentNumber + ", instrumentDate=" + instrumentDate + ", instrumentBank="
				+ instrumentBank + ", instrumentBankBranch=" + instrumentBankBranch + ", modeOfPayment=" + modeOfPayment
				+ ", isTermAccepted=" + isTermAccepted + ", paymentChequeDetails=" + paymentChequeDetails
				+ ", demandDraftDetails=" + demandDraftDetails + ", myMoneyOnlineStatus=" + myMoneyOnlineStatus
				+ ", myMoneyOfflineStatus=" + myMoneyOfflineStatus + ", directPaymentDetails=" + directPaymentDetails
				+ ", yblPaymentDetails=" + yblPaymentDetails + ", partnerPaymentDetails=" + partnerPaymentDetails +  ", customerId=" + customerId + ", billdeskAuthStatus="
				+ billdeskAuthStatus + ", billDeskPaymentStatus=" + billDeskPaymentStatus + ", billDeskCustomerId="
				+ billDeskCustomerId + ", billDeskCustomerIdDummy=" + billDeskCustomerIdDummy + ", isSIOpted="
				+ isSIOpted + ", cardType=" + cardType + ", itemCode=" + itemCode + ", isEmandate=" + isEmandate + ", actualAmountPaidByCustomer=" + actualAmountPaidByCustomer + "]";
	}
    
    

}