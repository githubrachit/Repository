    
package com.mli.mpro.proposal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sameAsBankDetails", "bankAccountNumber", "accountHolderName", "micr", "ifsc", "bankName", "bankBranch", "typeOfAccount", "bankInfoType","bankAccOpeningDate" })
public class BankDetails {

    @JsonProperty("bankInfoType")
    private String bankInfoType;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("bankAccountNumber")
    private String bankAccountNumber;
    @Sensitive(MaskType.NAME)
    @JsonProperty("accountHolderName")
    private String accountHolderName;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("micr")
    private String micr;
    @Sensitive(MaskType.BANK_IFSC)
    @JsonProperty("ifsc")
    private String ifsc;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("bankName")
    private String bankName;
    @Sensitive(MaskType.BANK_BRANCH_NAME )
    @JsonProperty("bankBranch")
    private String bankBranch;
    @JsonProperty("typeOfAccount")
    private String typeOfAccount;
    @JsonProperty("bankAccOpeningDate")
    private Date bankAccOpeningDate;

    //FUL2-16866 Fields added for GLIP Product
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("secondAnnuitantbankAccountNo")
    private String secondAnnuitantbankAccountNo;
    @Sensitive(MaskType.NAME)
    @JsonProperty("secondAnnuitantbankAccountHolderName")
    private String secondAnnuitantbankAccountHolderName;
    @Sensitive(MaskType.BANK_IFSC)
    @JsonProperty("secondAnnuitantbankAccountIFSC")
    private String secondAnnuitantbankAccountIFSC;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("secondAnnuitantbankAccountMICR")
    private String secondAnnuitantbankAccountMICR;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("secondAnnuitantbankName")
    private String secondAnnuitantbankName;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    @JsonProperty("secondAnnuitantbankBranch")
    private String secondAnnuitantbankBranch;
    @JsonProperty("secondAnnuitanttypeofAccount")
    private String secondAnnuitanttypeofAccount;
    @JsonProperty("secondAnnuitantbankAccOpeningDate")
    private Date secondAnnuitantbankAccOpeningDate;
    @JsonProperty("isPennyDropVerified")
    private String isPennyDropVerified;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BankDetails() {
    }

    /**
     * 
     * @param micr
     * @param accountholderName
     * @param bankName
     * @param bankAccountNumber
     * @param ifsc
     * @param rewalPaymentBy
     * @param typeOfAccount
     * @param bankBranch
     */

    public String getBankAccountNumber() {
	return bankAccountNumber;
    }

    public BankDetails(String bankInfoType, String bankAccountNumber, String accountHolderName, String micr,
                       String ifsc, String bankName, String bankBranch, String typeOfAccount, Date bankAccOpeningDate
            , String secondAnnuitantbankAccountNo, String secondAnnuitantbankAccountHolderName,
                       String secondAnnuitantbankAccountIFSC, String secondAnnuitantbankAccountMICR,
                       String secondAnnuitantbankName, String secondAnnuitantbankBranch,
                       String secondAnnuitanttypeofAccount, Date secondAnnuitantbankAccOpeningDate) {
        super();
        this.bankInfoType = bankInfoType;
        this.bankAccountNumber = bankAccountNumber;
        this.accountHolderName = accountHolderName;
        this.micr = micr;
        this.ifsc = ifsc;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.typeOfAccount = typeOfAccount;
        this.bankAccOpeningDate = bankAccOpeningDate;
        this.secondAnnuitantbankAccountNo = secondAnnuitantbankAccountNo;
        this.secondAnnuitantbankAccountHolderName = secondAnnuitantbankAccountHolderName;
        this.secondAnnuitantbankAccountIFSC = secondAnnuitantbankAccountIFSC;
        this.secondAnnuitantbankAccountMICR = secondAnnuitantbankAccountMICR;
        this.secondAnnuitantbankName = secondAnnuitantbankName;
        this.secondAnnuitantbankBranch = secondAnnuitantbankBranch;
        this.secondAnnuitanttypeofAccount = secondAnnuitanttypeofAccount;
        this.secondAnnuitantbankAccOpeningDate = secondAnnuitantbankAccOpeningDate;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
	this.bankAccountNumber = bankAccountNumber;
    }

    public String getAccountHolderName() {
	return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
	this.accountHolderName = accountHolderName;
    }

    public String getMicr() {
	return micr;
    }

    public void setMicr(String micr) {
	this.micr = micr;
    }

    public String getIfsc() {
	return ifsc;
    }

    public void setIfsc(String ifsc) {
	this.ifsc = ifsc;
    }

    public String getBankName() {
	return bankName;
    }

    public void setBankName(String bankName) {
	this.bankName = bankName;
    }

    public String getBankBranch() {
	return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
	this.bankBranch = bankBranch;
    }

    public String getTypeOfAccount() {
	return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
	this.typeOfAccount = typeOfAccount;
    }

    public String getBankInfoType() {
	return bankInfoType;
    }

    public void setBankInfoType(String bankInfoType) {
	this.bankInfoType = bankInfoType;
    }

    public Date getBankAccOpeningDate() {
        return bankAccOpeningDate;
    }

    public void setBankAccOpeningDate(Date bankAccOpeningDate) {
        this.bankAccOpeningDate = bankAccOpeningDate;
    }

    public String getSecondAnnuitantbankAccountNo() {
        return secondAnnuitantbankAccountNo;
    }

    public void setSecondAnnuitantbankAccountNo(String secondAnnuitantbankAccountNo) {
        this.secondAnnuitantbankAccountNo = secondAnnuitantbankAccountNo;
    }

    public String getSecondAnnuitantbankAccountHolderName() {
        return secondAnnuitantbankAccountHolderName;
    }

    public void setSecondAnnuitantbankAccountHolderName(String secondAnnuitantbankAccountHolderName) {
        this.secondAnnuitantbankAccountHolderName = secondAnnuitantbankAccountHolderName;
    }

    public String getSecondAnnuitantbankAccountIFSC() {
        return secondAnnuitantbankAccountIFSC;
    }

    public void setSecondAnnuitantbankAccountIFSC(String secondAnnuitantbankAccountIFSC) {
        this.secondAnnuitantbankAccountIFSC = secondAnnuitantbankAccountIFSC;
    }

    public String getSecondAnnuitantbankAccountMICR() {
        return secondAnnuitantbankAccountMICR;
    }

    public void setSecondAnnuitantbankAccountMICR(String secondAnnuitantbankAccountMICR) {
        this.secondAnnuitantbankAccountMICR = secondAnnuitantbankAccountMICR;
    }

    public String getSecondAnnuitantbankName() {
        return secondAnnuitantbankName;
    }

    public void setSecondAnnuitantbankName(String secondAnnuitantbankName) {
        this.secondAnnuitantbankName = secondAnnuitantbankName;
    }

    public String getSecondAnnuitantbankBranch() {
        return secondAnnuitantbankBranch;
    }

    public void setSecondAnnuitantbankBranch(String secondAnnuitantbankBranch) {
        this.secondAnnuitantbankBranch = secondAnnuitantbankBranch;
    }

    public String getSecondAnnuitanttypeofAccount() {
        return secondAnnuitanttypeofAccount;
    }

    public void setSecondAnnuitanttypeofAccount(String secondAnnuitanttypeofAccount) {
        this.secondAnnuitanttypeofAccount = secondAnnuitanttypeofAccount;
    }

    public Date getSecondAnnuitantbankAccOpeningDate() {
        return secondAnnuitantbankAccOpeningDate;
    }

    public void setSecondAnnuitantbankAccOpeningDate(Date secondAnnuitantbankAccOpeningDate) {
        this.secondAnnuitantbankAccOpeningDate = secondAnnuitantbankAccOpeningDate;
    }

    public String getIsPennyDropVerified() {
        return isPennyDropVerified;
    }

    public void setIsPennyDropVerified(String isPennyDropVerified) {
        this.isPennyDropVerified = isPennyDropVerified;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "BankDetails [" +
                "bankInfoType= " + bankInfoType +
                ", bankAccountNumber= " + bankAccountNumber +
                ", accountHolderName= " + accountHolderName +
                ", micr= " + micr +
                ", ifsc= " + ifsc +
                ", bankName= " + bankName +
                ", bankBranch= " + bankBranch +
                ", typeOfAccount= " + typeOfAccount +
                ", bankAccOpeningDate=" + bankAccOpeningDate +
                ", secondAnnuitantbankAccountNo= " + secondAnnuitantbankAccountNo +
                ", secondAnnuitantbankAccountHolderName= " + secondAnnuitantbankAccountHolderName +
                ", secondAnnuitantbankAccountIFSC= " + secondAnnuitantbankAccountIFSC +
                ", secondAnnuitantbankAccountMICR= " + secondAnnuitantbankAccountMICR +
                ", secondAnnuitantbankName= " + secondAnnuitantbankName +
                ", secondAnnuitantbankBranch= " + secondAnnuitantbankBranch +
                ", secondAnnuitanttypeofAccount= " + secondAnnuitanttypeofAccount +
                ", secondAnnuitantbankAccOpeningDate=" + secondAnnuitantbankAccOpeningDate +
                ", isPennyDropVerified=" + isPennyDropVerified +
                ']';
    }
}