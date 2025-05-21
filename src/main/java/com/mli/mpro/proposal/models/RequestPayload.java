
package com.mli.mpro.proposal.models;

import com.mli.mpro.document.models.SellerConsentDetails;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "proposalDetails" })
public class RequestPayload {

    @JsonProperty("version")
    private String version;
    @JsonProperty("proposalDetails")
    private ProposalDetails proposalDetails;
    @JsonProperty("agentId")
    private String agentId;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("policyNumber")
    private String policyNumber;
    @JsonProperty("posvStatus")
    private PosvStatus posvStatus;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("posvQuestions")
    private List<PosvQuestion> posvQuestions;
    @JsonProperty("documentDetails")
    private DocumentDetails documentDetails;
    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("myMoneyStatus")
    private String myMoneyStatus;
    @JsonProperty("transactionId")
    private long transactionId;
    @JsonProperty("policyNumbers")
    private List<String> policyNumbers;
    @JsonProperty("fromDate")
    private Date fromDate;
    @JsonProperty("toDate")
    private Date toDate;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("retryCategory")
    private String retryCategory;
    private String documentType;

    @JsonProperty("sellerConsentDetails")
    private SellerConsentDetails sellerConsentDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestPayload() {
    }

    /**
     * 
     * @param proposalDetails
     */
    public RequestPayload(ProposalDetails proposalDetails) {
	super();
	this.proposalDetails = proposalDetails;
    }

    public RequestPayload(String agentId, String policyNumber, PosvStatus posvStatus, List<PosvQuestion> posvQuestions) {
	super();
	this.agentId = agentId;
	this.policyNumber = policyNumber;
	this.posvStatus = posvStatus;
	this.posvQuestions = posvQuestions;
    }

    public RequestPayload(ProposalDetails proposalDetails, DocumentDetails documentDetails) {
	super();
	this.proposalDetails = proposalDetails;
	this.documentDetails = documentDetails;
    }

    public RequestPayload(String policyNumber, String paymentType, String myMoneyStatus, ProposalDetails proposalDetails) {
	super();
	this.policyNumber = policyNumber;
	this.paymentType = paymentType;
	this.myMoneyStatus = myMoneyStatus;
	this.proposalDetails = proposalDetails;
    }

    public RequestPayload(Date fromDate, Date toDate, String channel) {
	super();
	this.fromDate = fromDate;
	this.toDate = toDate;
	this.channel = channel;
    }

    @JsonProperty("proposalDetails")
    public ProposalDetails getProposalDetails() {
	return proposalDetails;
    }

    @JsonProperty("proposalDetails")
    public void setProposalDetails(ProposalDetails proposalDetails) {
	this.proposalDetails = proposalDetails;
    }

    @JsonProperty("agentId")
    public String getAgentId() {
	return agentId;
    }

    @JsonProperty("agentId")
    public void setAgentId(String agentId) {
	this.agentId = agentId;
    }

    @JsonProperty("policyNumber")
    public String getPolicyNumber() {
	return policyNumber;
    }

    @JsonProperty("policyNumber")
    public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
    }

    @JsonProperty("posvStatus")
    public PosvStatus getPosvStatus() {
	return posvStatus;
    }

    @JsonProperty("posvStatus")
    public void setPosvStatus(PosvStatus posvStatus) {
	this.posvStatus = posvStatus;
    }

    @JsonProperty("posvQuestions")
    public List<PosvQuestion> getPosvQuestions() {
	return posvQuestions;
    }

    @JsonProperty("posvQuestions")
    public void setPosvQuestions(List<PosvQuestion> posvQuestions) {
	this.posvQuestions = posvQuestions;
    }

    public DocumentDetails getDocumentDetails() {
	return documentDetails;
    }

    public void setDocumentDetails(DocumentDetails documentDetails) {
	this.documentDetails = documentDetails;
    }

    @JsonProperty("paymentType")
    public String getPaymentType() {
	return paymentType;
    }

    @JsonProperty("paymentType")
    public void setPaymentType(String paymentType) {
	this.paymentType = paymentType;
    }

    @JsonProperty("myMoneyStatus")
    public String getMyMoneyStatus() {
	return myMoneyStatus;
    }

    @JsonProperty("myMoneyStatus")
    public void setMyMoneyStatus(String myMoneyStatus) {
	this.myMoneyStatus = myMoneyStatus;
    }


    public Date getFromDate() {
	return fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    public Date getToDate() {
	return toDate;
    }

    public void setToDate(Date toDate) {
	this.toDate = toDate;
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }
    public long getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(long transactionId) {
	this.transactionId = transactionId;
    }

    public String getRetryCategory() {
        return retryCategory;
    }

    public void setRetryCategory(String retryCategory) {
        this.retryCategory = retryCategory;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getPolicyNumbers() { return policyNumbers;}

    public void setPolicyNumbers(List<String> policyNumbers) { this.policyNumbers = policyNumbers; }

    @JsonProperty("sellerConsentDetails")
   public SellerConsentDetails getSellerConsentDetails() { return sellerConsentDetails; }
   @JsonProperty("sellerConsentDetails")
   public void setSellerConsentDetails(SellerConsentDetails sellerConsentDetails) { this.sellerConsentDetails = sellerConsentDetails; }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "RequestPayload [version=" + version + ", proposalDetails=" + proposalDetails + ", agentId=" + agentId + ", policyNumber=" + policyNumber + ", posvStatus=" + posvStatus
		+ ", posvQuestions=" + posvQuestions + ", documentDetails=" + documentDetails + ", paymentType=" + paymentType + ", myMoneyStatus="
		+ myMoneyStatus + ", transactionId=" + transactionId + ", fromDate=" + fromDate + ", toDate=" + toDate + ", channel=" + channel
		+ ", retryCategory=" + retryCategory + ", documentType=" + documentType + ", uuid=" + uuid + ", sellerConsentDetails="+ sellerConsentDetails
      +" ]";
    }


}
