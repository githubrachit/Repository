
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "identityProofName", "identityProofNumber", "identityProofExpiryDate", "identityProofIssuingAuthority", "isAddressProofSame",
	"addressProofName", "addressProofNumber", "addressProofIssuingAuthority", "addressProofExpiryDate" })
public class Form60Details {

    @JsonProperty("identityProofName")
    private String identityProofName;
    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("identityProofNumber")
    private String identityProofNumber;
    @JsonProperty("identityProofExpiryDate")
    private String identityProofExpiryDate;
    @JsonProperty("identityProofIssuingAuthority")
    private String identityProofIssuingAuthority;
    @JsonProperty("isAddressProofSame")
    private boolean isAddressProofSame;
    @Sensitive(MaskType.ADDRESS_PROOF_NUM)
    @JsonProperty("addressProofNumber")
    private String addressProofNumber;
    @Sensitive(MaskType.NAME)
    @JsonProperty("addressProofName")
    private String addressProofName;
    @JsonProperty("addressProofIssuingAuthority")
    private String addressProofIssuingAuthority;
    @JsonProperty("addressProofExpiryDate")
    private Date addressProofExpiryDate;

    @JsonProperty("detailsOfDontHavePan")
    private String detailsOfDontHavePan;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panAcknowledgementNo")
    private String panAcknowledgementNo;
    @JsonProperty("panApplicationDate")
    private Date panApplicationDate;
    @JsonProperty("isChargeableIncome")
    private boolean isChargeableIncome;
    @JsonProperty("isIncomeExceedLimit")
    private boolean isIncomeExceedLimit;
    @JsonProperty("isTaxableIncome")
    private boolean isTaxableIncome;
    @JsonProperty("isItOtherIncome")
    private boolean isItOtherIncome;
    @JsonProperty("isNRI")
    private boolean isNRI;
    @JsonProperty("isApplicableIncome")
    private boolean isApplicableIncome;

    //FUL2-17547 Fields added for GLIP Product
    @JsonProperty("secondAnnuitantIdentityProof")
    private String secondAnnuitantIdentityProof;
    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("secondAnnuitantIdentityProofNumber")
    private String secondAnnuitantIdentityProofNumber;
    @JsonProperty("secondAnnuitantIdentityProofExpiryDate")
    private Date secondAnnuitantIdentityProofExpiryDate;
    @JsonProperty("secondAnnuitantIdentityProofIssuingAuthority")
    private String secondAnnuitantIdentityProofIssuingAuthority;
    @JsonProperty("secondAnnuitantAddressProof")
    private String secondAnnuitantAddressProof;
    @Sensitive(MaskType.ADDRESS_PROOF_NUM)
    @JsonProperty("secondAnnuitantAddressProofNumber")
    private String secondAnnuitantAddressProofNumber;
    @JsonProperty("secondAnnuitantAddressProofExpiryDate")
    private Date secondAnnuitantAddressProofExpiryDate;
    @JsonProperty("secondAnnuitantAddressProofIssuingAuthority")
    private String secondAnnuitantAddressProofIssuingAuthority;
    @JsonProperty("secondAnnuitantDetailsOfDontHavePan")
    private String secondAnnuitantDetailsOfDontHavePan;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("secondAnnuitantPanAcknowledgementNo")
    private String secondAnnuitantPanAcknowledgementNo;
    @JsonProperty("secondAnnuitantPanApplicationDate")
    private Date secondAnnuitantPanApplicationDate;
    @JsonProperty("isSecondAnnuitantChargeableIncome")
    private boolean isSecondAnnuitantChargeableIncome;
    @JsonProperty("isSecondAnnuitantIncomeExceedLimit")
    private boolean isSecondAnnuitantIncomeExceedLimit;
    @JsonProperty("isSecondAnnuitantTaxableIncome")
    private boolean isSecondAnnuitantTaxableIncome;
    @JsonProperty("isSecondAnnuitantItOtherIncome")
    private boolean isSecondAnnuitantItOtherIncome;
    @JsonProperty("isSecondAnnuitantNRI")
    private boolean isSecondAnnuitantNRI;
    @JsonProperty("isSecondAnnuitantApplicableIncome")
    private boolean isSecondAnnuitantApplicableIncome;
    @JsonProperty("isSecondAnnuitantAddressProofSame")
    private boolean isSecondAnnuitantAddressProofSame;

    public String getIdentityProofName() {
	return identityProofName;
    }

    public void setIdentityProofName(String identityProofName) {
	this.identityProofName = identityProofName;
    }

    public String getIdentityProofNumber() {
	return identityProofNumber;
    }

    public void setIdentityProofNumber(String identityProofNumber) {
	this.identityProofNumber = identityProofNumber;
    }

    public String getIdentityProofExpiryDate() {
	return identityProofExpiryDate;
    }

    public void setIdentityProofExpiryDate(String identityProofExpiryDate) {
	this.identityProofExpiryDate = identityProofExpiryDate;
    }

    public String getIdentityProofIssuingAuthority() {
	return identityProofIssuingAuthority;
    }

    public void setIdentityProofIssuingAuthority(String identityProofIssuingAuthority) {
	this.identityProofIssuingAuthority = identityProofIssuingAuthority;
    }

    public boolean isAddressProofSame() {
	return isAddressProofSame;
    }

    public void setAddressProofSame(boolean sameAsForm60IdProof) {
	this.isAddressProofSame = sameAsForm60IdProof;
    }

    public String getAddressProofNumber() {
	return addressProofNumber;
    }

    public void setAddressProofNumber(String addressProofNumber) {
	this.addressProofNumber = addressProofNumber;
    }

    public String getAddressProofName() {
	return addressProofName;
    }

    public void setAddressProofName(String addressProofName) {
	this.addressProofName = addressProofName;
    }

    public String getAddressProofIssuingAuthority() {
	return addressProofIssuingAuthority;
    }

    public void setAddressProofIssuingAuthority(String adressProofIssuingAuthority) {
	this.addressProofIssuingAuthority = adressProofIssuingAuthority;
    }

    public Date getAddressProofExpiryDate() {
	return addressProofExpiryDate;
    }

    public void setAddressProofExpiryDate(Date addressProofExpiryDate) {
	this.addressProofExpiryDate = addressProofExpiryDate;
    }

    public String getDetailsOfDontHavePan() {
        return detailsOfDontHavePan;
    }

    public void setDetailsOfDontHavePan(String detailsOfDontHavePan) {
        this.detailsOfDontHavePan = detailsOfDontHavePan;
    }

    public String getPanAcknowledgementNo() {
        return panAcknowledgementNo;
    }

    public void setPanAcknowledgementNo(String panAcknowledgementNo) {
        this.panAcknowledgementNo = panAcknowledgementNo;
    }

    public Date getPanApplicationDate() {
        return panApplicationDate;
    }

    public void setPanApplicationDate(Date panApplicationDate) {
        this.panApplicationDate = panApplicationDate;
    }

    public boolean isChargeableIncome() {
        return isChargeableIncome;
    }

    public void setChargeableIncome(boolean isChargeableIncome) {
        this.isChargeableIncome = isChargeableIncome;
    }

    public boolean isIncomeExceedLimit() {
        return isIncomeExceedLimit;
    }

    public void setIncomeExceedLimit(boolean isIncomeExceedLimit) {
        this.isIncomeExceedLimit = isIncomeExceedLimit;
    }

    public boolean isTaxableIncome() {
        return isTaxableIncome;
    }

    public void setTaxableIncome(boolean isTaxableIncome) {
        this.isTaxableIncome = isTaxableIncome;
    }

    public boolean isItOtherIncome() {
        return isItOtherIncome;
    }

    public void setItOtherIncome(boolean isItOtherIncome) {
        this.isItOtherIncome = isItOtherIncome;
    }

    public boolean isNRI() {
        return isNRI;
    }

    public void setNRI(boolean isNRI) {
        this.isNRI = isNRI;
    }

    public boolean isApplicableIncome() {
        return isApplicableIncome;
    }

    public void setApplicableIncome(boolean isApplicableIncome) {
        this.isApplicableIncome = isApplicableIncome;
    }

    public String getSecondAnnuitantIdentityProof() {
        return secondAnnuitantIdentityProof;
    }

    public void setSecondAnnuitantIdentityProof(String secondAnnuitantIdentityProof) {
        this.secondAnnuitantIdentityProof = secondAnnuitantIdentityProof;
    }

    public String getSecondAnnuitantIdentityProofNumber() {
        return secondAnnuitantIdentityProofNumber;
    }

    public void setSecondAnnuitantIdentityProofNumber(String secondAnnuitantIdentityProofNumber) {
        this.secondAnnuitantIdentityProofNumber = secondAnnuitantIdentityProofNumber;
    }

    public Date getSecondAnnuitantIdentityProofExpiryDate() {
        return secondAnnuitantIdentityProofExpiryDate;
    }

    public void setSecondAnnuitantIdentityProofExpiryDate(Date secondAnnuitantIdentityProofExpiryDate) {
        this.secondAnnuitantIdentityProofExpiryDate = secondAnnuitantIdentityProofExpiryDate;
    }

    public String getSecondAnnuitantIdentityProofIssuingAuthority() {
        return secondAnnuitantIdentityProofIssuingAuthority;
    }

    public void setSecondAnnuitantIdentityProofIssuingAuthority(String secondAnnuitantIdentityProofIssuingAuthority) {
        this.secondAnnuitantIdentityProofIssuingAuthority = secondAnnuitantIdentityProofIssuingAuthority;
    }

    public String getSecondAnnuitantAddressProof() {
        return secondAnnuitantAddressProof;
    }

    public void setSecondAnnuitantAddressProof(String secondAnnuitantAddressProof) {
        this.secondAnnuitantAddressProof = secondAnnuitantAddressProof;
    }

    public String getSecondAnnuitantAddressProofNumber() {
        return secondAnnuitantAddressProofNumber;
    }

    public void setSecondAnnuitantAddressProofNumber(String secondAnnuitantAddressProofNumber) {
        this.secondAnnuitantAddressProofNumber = secondAnnuitantAddressProofNumber;
    }

    public Date getSecondAnnuitantAddressProofExpiryDate() {
        return secondAnnuitantAddressProofExpiryDate;
    }

    public void setSecondAnnuitantAddressProofExpiryDate(Date secondAnnuitantAddressProofExpiryDate) {
        this.secondAnnuitantAddressProofExpiryDate = secondAnnuitantAddressProofExpiryDate;
    }

    public String getSecondAnnuitantAddressProofIssuingAuthority() {
        return secondAnnuitantAddressProofIssuingAuthority;
    }

    public void setSecondAnnuitantAddressProofIssuingAuthority(String secondAnnuitantAddressProofIssuingAuthority) {
        this.secondAnnuitantAddressProofIssuingAuthority = secondAnnuitantAddressProofIssuingAuthority;
    }

    public String getSecondAnnuitantDetailsOfDontHavePan() {
        return secondAnnuitantDetailsOfDontHavePan;
    }

    public void setSecondAnnuitantDetailsOfDontHavePan(String secondAnnuitantDetailsOfDontHavePan) {
        this.secondAnnuitantDetailsOfDontHavePan = secondAnnuitantDetailsOfDontHavePan;
    }

    public String getSecondAnnuitantPanAcknowledgementNo() {
        return secondAnnuitantPanAcknowledgementNo;
    }

    public void setSecondAnnuitantPanAcknowledgementNo(String secondAnnuitantPanAcknowledgementNo) {
        this.secondAnnuitantPanAcknowledgementNo = secondAnnuitantPanAcknowledgementNo;
    }

    public Date getSecondAnnuitantPanApplicationDate() {
        return secondAnnuitantPanApplicationDate;
    }

    public void setSecondAnnuitantPanApplicationDate(Date secondAnnuitantPanApplicationDate) {
        this.secondAnnuitantPanApplicationDate = secondAnnuitantPanApplicationDate;
    }

    public boolean isSecondAnnuitantChargeableIncome() {
        return isSecondAnnuitantChargeableIncome;
    }

    public void setSecondAnnuitantChargeableIncome(boolean secondAnnuitantChargeableIncome) {
        isSecondAnnuitantChargeableIncome = secondAnnuitantChargeableIncome;
    }

    public boolean isSecondAnnuitantIncomeExceedLimit() {
        return isSecondAnnuitantIncomeExceedLimit;
    }

    public void setSecondAnnuitantIncomeExceedLimit(boolean secondAnnuitantIncomeExceedLimit) {
        isSecondAnnuitantIncomeExceedLimit = secondAnnuitantIncomeExceedLimit;
    }

    public boolean isSecondAnnuitantTaxableIncome() {
        return isSecondAnnuitantTaxableIncome;
    }

    public void setSecondAnnuitantTaxableIncome(boolean secondAnnuitantTaxableIncome) {
        isSecondAnnuitantTaxableIncome = secondAnnuitantTaxableIncome;
    }

    public boolean isSecondAnnuitantItOtherIncome() {
        return isSecondAnnuitantItOtherIncome;
    }

    public void setSecondAnnuitantItOtherIncome(boolean secondAnnuitantItOtherIncome) {
        isSecondAnnuitantItOtherIncome = secondAnnuitantItOtherIncome;
    }

    public boolean isSecondAnnuitantNRI() {
        return isSecondAnnuitantNRI;
    }

    public void setSecondAnnuitantNRI(boolean secondAnnuitantNRI) {
        isSecondAnnuitantNRI = secondAnnuitantNRI;
    }

    public boolean isSecondAnnuitantApplicableIncome() {
        return isSecondAnnuitantApplicableIncome;
    }

    public void setSecondAnnuitantApplicableIncome(boolean secondAnnuitantApplicableIncome) {
        isSecondAnnuitantApplicableIncome = secondAnnuitantApplicableIncome;
    }

    public boolean isSecondAnnuitantAddressProofSame() {
        return isSecondAnnuitantAddressProofSame;
    }

    public void setSecondAnnuitantAddressProofSame(boolean secondAnnuitantAddressProofSame) {
        isSecondAnnuitantAddressProofSame = secondAnnuitantAddressProofSame;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "Form60Details [" +
                "identityProofName= "+ identityProofName + 
                ", identityProofNumber= "+ identityProofNumber + 
                ", identityProofExpiryDate= "+ identityProofExpiryDate + 
                ", identityProofIssuingAuthority= "+ identityProofIssuingAuthority + 
                ", isAddressProofSame=" + isAddressProofSame +
                ", addressProofNumber= "+ addressProofNumber + 
                ", addressProofName= "+ addressProofName + 
                ", addressProofIssuingAuthority= "+ addressProofIssuingAuthority + 
                ", addressProofExpiryDate=" + addressProofExpiryDate +
                ", detailsOfDontHavePan= "+ detailsOfDontHavePan + 
                ", panAcknowledgementNo= "+ panAcknowledgementNo + 
                ", panApplicationDate=" + panApplicationDate +
                ", isChargeableIncome=" + isChargeableIncome +
                ", isIncomeExceedLimit=" + isIncomeExceedLimit +
                ", isTaxableIncome=" + isTaxableIncome +
                ", isItOtherIncome=" + isItOtherIncome +
                ", isNRI=" + isNRI +
                ", isApplicableIncome=" + isApplicableIncome +
                ", secondAnnuitantIdentityProof= "+ secondAnnuitantIdentityProof + 
                ", secondAnnuitantIdentityProofNumber= "+ secondAnnuitantIdentityProofNumber + 
                ", secondAnnuitantIdentityProofExpiryDate=" + secondAnnuitantIdentityProofExpiryDate +
                ", secondAnnuitantIdentityProofIssuingAuthority= "+ secondAnnuitantIdentityProofIssuingAuthority + 
                ", secondAnnuitantAddressProof= "+ secondAnnuitantAddressProof + 
                ", secondAnnuitantAddressProofNumber= "+ secondAnnuitantAddressProofNumber + 
                ", secondAnnuitantAddressProofExpiryDate=" + secondAnnuitantAddressProofExpiryDate +
                ", secondAnnuitantAddressProofIssuingAuthority=" + secondAnnuitantAddressProofIssuingAuthority +
                ", secondAnnuitantDetailsOfDontHavePan= "+ secondAnnuitantDetailsOfDontHavePan + 
                ", secondAnnuitantPanAcknowledgementNo= "+ secondAnnuitantPanAcknowledgementNo + 
                ", secondAnnuitantPanApplicationDate=" + secondAnnuitantPanApplicationDate +
                ", isSecondAnnuitantChargeableIncome=" + isSecondAnnuitantChargeableIncome +
                ", isSecondAnnuitantIncomeExceedLimit=" + isSecondAnnuitantIncomeExceedLimit +
                ", isSecondAnnuitantTaxableIncome=" + isSecondAnnuitantTaxableIncome +
                ", isSecondAnnuitantItOtherIncome=" + isSecondAnnuitantItOtherIncome +
                ", isSecondAnnuitantNRI=" + isSecondAnnuitantNRI +
                ", isSecondAnnuitantApplicableIncome=" + isSecondAnnuitantApplicableIncome +
                ", isSecondAnnuitantAddressProofSame=" + isSecondAnnuitantAddressProofSame +
                ']';
    }
}
