package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author priya on 25/06/20
 */
public class BancaDetails implements Serializable {

    private String axisTimeStamp;
    private String axisRefId;
    private String axisLeadId;
    private String axisSourceId;
    private String axisRefferalCode;
    private String axisProductName;
    @Sensitive(MaskType.NAME)
    private String partnerName;
    private String axisUTMSource;
    private String axisUTMMedium;
    private String axisUTMCampaign;
    private String axisSPCode;
    private String axisSolId;
    private String axisProducerCode;
    private String ebccFlag;
    private String axisSchemeCode;
    private String ebccPanModify;
    private String ebccAddModify;
    private String ebccDobModify;
    private String ebccNameModify;
    private String bankJourney;
    private String cKycNo;
    private String axisNRIState;
    @Sensitive(MaskType.NAME)
    private String nomineeName;
    @Sensitive(MaskType.DOB)
    private String nomineeDob;
    private String nomineeRelation;
    @Sensitive(MaskType.BANK_ACC_NUM)
    private String accountNo;
    private String accountType;
    private String ebccGenderModify;
    @JsonAlias("utm_source")
    private String utmSource;
    @JsonAlias("SP_Code")
    private String spCode;
    @JsonAlias("Timestamp")
    private String timestamp;
    private String yblFlagCustType;
    private String yblCustomerType;
    private String yblCustomerId;
    @Sensitive(MaskType.BRANCH_CODE)
    private String yblBranchCode;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    private String yblBranchName;
    private String yblPhoneOff;
    private String yblCustomerClass;
    private String yblLastKyc;
    private String yblSalesRmCode;
    private String yblServiceRmCode;
    private String yblAssetRmCode;
    private String yblSegment;
    private String yblProfession;
    private String yblOccupation;
    private String yblReqRefNum;
    private String yblDisplayNomineeFlag;
    private String yblRelationToAccHolder;
    private String yblRelationToNominee;
    private String customerID;

    public BancaDetails() {
        this.axisTimeStamp = "";
        this.axisRefId = "";
        this.axisLeadId = "";
        this.axisSourceId = "";
        this.axisRefferalCode = "";
        this.axisProductName = "";
        this.partnerName = "";
        this.axisUTMSource = "";
        this.axisUTMMedium = "";
        this.axisUTMCampaign = "";
        this.axisSPCode = "";
        this.axisSolId = "";
        this.axisProducerCode = "";
        this.ebccFlag = "";
        this.axisSchemeCode = "";
        this.ebccPanModify = "";
        this.ebccAddModify = "";
        this.ebccDobModify = "";
        this.ebccNameModify = "";
        this.bankJourney = "";
        this.cKycNo = "";
        this.axisNRIState = "";
        this.nomineeName = "";
        this.nomineeDob = "";
        this.nomineeRelation = "";
        this.accountNo = "";
        this.accountType = "";
        this.ebccGenderModify = "";
        this.utmSource = "";
        this.spCode = "";
        this.timestamp = "";
        this.yblFlagCustType = "";
        this.yblCustomerType = "";
        this.yblFlagCustType = "";
        this.yblCustomerId = "";
        this.yblBranchCode = "";
        this.yblBranchName = "";
        this.yblPhoneOff = "";
        this.yblCustomerClass = "";
        this.yblLastKyc = "";
        this.yblSalesRmCode = "";
        this.yblServiceRmCode = "";
        this.yblAssetRmCode = "";
        this.yblSegment = "";
        this.yblProfession = "";
        this.yblOccupation = "";
        this.yblReqRefNum = "";
        this.yblDisplayNomineeFlag = "";
        this.yblRelationToAccHolder = "";
        this.yblRelationToNominee = "";
        this.customerID = "";
    }

    public String getAxisTimeStamp() {
        return axisTimeStamp;
    }

    public void setAxisTimeStamp(String axisTimeStamp) {
        this.axisTimeStamp = axisTimeStamp;
    }

    public BancaDetails withAxisTimeStamp(String axisTimeStamp) {
        this.axisTimeStamp = axisTimeStamp;
        return this;
    }

    public String getAxisRefId() {
        return axisRefId;
    }

    public void setAxisRefId(String axisRefId) {
        this.axisRefId = axisRefId;
    }

    public BancaDetails withAxisRefId(String axisRefId) {
        this.axisRefId = axisRefId;
        return this;
    }

    public String getAxisLeadId() {
        return axisLeadId;
    }

    public void setAxisLeadId(String axisLeadId) {
        this.axisLeadId = axisLeadId;
    }

    public BancaDetails withAxisLeadId(String axisLeadId) {
        this.axisLeadId = axisLeadId;
        return this;
    }

    public String getAxisSourceId() {
        return axisSourceId;
    }

    public void setAxisSourceId(String axisSourceId) {
        this.axisSourceId = axisSourceId;
    }

    public BancaDetails withAxisSourceId(String axisSourceId) {
        this.axisSourceId = axisSourceId;
        return this;
    }

    public String getAxisRefferalCode() {
        return axisRefferalCode;
    }

    public void setAxisRefferalCode(String axisRefferalCode) {
        this.axisRefferalCode = axisRefferalCode;
    }

    public BancaDetails withAxisRefferalCode(String axisRefferalCode) {
        this.axisRefferalCode = axisRefferalCode;
        return this;
    }

    public String getAxisProductName() {
        return axisProductName;
    }

    public void setAxisProductName(String axisProductName) {
        this.axisProductName = axisProductName;
    }

    public BancaDetails withAxisProductName(String axisProductName) {
        this.axisProductName = axisProductName;
        return this;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public BancaDetails withPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getAxisUTMSource() {
        return axisUTMSource;
    }

    public void setAxisUTMSource(String axisUTMSource) {
        this.axisUTMSource = axisUTMSource;
    }

    public BancaDetails withAxisUTMSource(String axisUTMSource) {
        this.axisUTMSource = axisUTMSource;
        return this;
    }

    public String getAxisUTMMedium() {
        return axisUTMMedium;
    }

    public void setAxisUTMMedium(String axisUTMMedium) {
        this.axisUTMMedium = axisUTMMedium;
    }

    public BancaDetails withAxisUTMMedium(String axisUTMMedium) {
        this.axisUTMMedium = axisUTMMedium;
        return this;
    }

    public String getAxisUTMCampaign() {
        return axisUTMCampaign;
    }

    public void setAxisUTMCampaign(String axisUTMCampaign) {
        this.axisUTMCampaign = axisUTMCampaign;
    }

    public BancaDetails withAxisUTMCampaign(String axisUTMCampaign) {
        this.axisUTMCampaign = axisUTMCampaign;
        return this;
    }

    public String getAxisSPCode() {
        return axisSPCode;
    }

    public void setAxisSPCode(String axisSPCode) {
        this.axisSPCode = axisSPCode;
    }

    public BancaDetails withAxisSPCode(String axisSPCode) {
        this.axisSPCode = axisSPCode;
        return this;
    }

    public String getAxisSolId() {
        return axisSolId;
    }

    public void setAxisSolId(String axisSolId) {
        this.axisSolId = axisSolId;
    }

    public BancaDetails withAxisSolId(String axisSolId) {
        this.axisSolId = axisSolId;
        return this;
    }

    public String getAxisProducerCode() {
        return axisProducerCode;
    }

    public void setAxisProducerCode(String axisProducerCode) {
        this.axisProducerCode = axisProducerCode;
    }

    public BancaDetails withAxisProducerCode(String axisProducerCode) {
        this.axisProducerCode = axisProducerCode;
        return this;
    }

    public String getEbccFlag() {
        return ebccFlag;
    }

    public void setEbccFlag(String ebccFlag) {
        this.ebccFlag = ebccFlag;
    }

    public BancaDetails withEbccFlag(String ebccFlag) {
        this.ebccFlag = ebccFlag;
        return this;
    }

    public String getAxisSchemeCode() {
        return axisSchemeCode;
    }

    public void setAxisSchemeCode(String axisSchemeCode) {
        this.axisSchemeCode = axisSchemeCode;
    }

    public BancaDetails withAxisSchemeCode(String axisSchemeCode) {
        this.axisSchemeCode = axisSchemeCode;
        return this;
    }

    public String getEbccPanModify() {
        return ebccPanModify;
    }

    public void setEbccPanModify(String ebccPanModify) {
        this.ebccPanModify = ebccPanModify;
    }

    public BancaDetails withEbccPanModify(String ebccPanModify) {
        this.ebccPanModify = ebccPanModify;
        return this;
    }

    public String getEbccAddModify() {
        return ebccAddModify;
    }

    public void setEbccAddModify(String ebccAddModify) {
        this.ebccAddModify = ebccAddModify;
    }

    public BancaDetails withEbccAddModify(String ebccAddModify) {
        this.ebccAddModify = ebccAddModify;
        return this;
    }

    public String getEbccDobModify() {
        return ebccDobModify;
    }

    public void setEbccDobModify(String ebccDobModify) {
        this.ebccDobModify = ebccDobModify;
    }

    public BancaDetails withEbccDobModify(String ebccDobModify) {
        this.ebccDobModify = ebccDobModify;
        return this;
    }

    public String getEbccNameModify() {
        return ebccNameModify;
    }

    public void setEbccNameModify(String ebccNameModify) {
        this.ebccNameModify = ebccNameModify;
    }

    public BancaDetails withEbccNameModify(String ebccNameModify) {
        this.ebccNameModify = ebccNameModify;
        return this;
    }

    public String getBankJourney() {
        return bankJourney;
    }

    public void setBankJourney(String bankJourney) {
        this.bankJourney = bankJourney;
    }

    public BancaDetails withBankJourney(String bankJourney) {
        this.bankJourney = bankJourney;
        return this;
    }

    public String getcKycNo() {
        return cKycNo;
    }

    public void setcKycNo(String cKycNo) {
        this.cKycNo = cKycNo;
    }

    public BancaDetails withCkycNumber(String ckycNumber) {
        this.cKycNo = ckycNumber;
        return this;
    }

    public String getAxisNRIState() {
        return axisNRIState;
    }

    public void setAxisNRIState(String axisNRIState) {
        this.axisNRIState = axisNRIState;
    }

    public BancaDetails withAxisNRIState(String axisNRIState) {
        this.axisNRIState = axisNRIState;
        return this;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public BancaDetails withNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
        return this;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public BancaDetails withNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
        return this;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public BancaDetails withNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
        return this;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BancaDetails withAccountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BancaDetails withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public String getEbccGenderModify() {
        return ebccGenderModify;
    }

    public void setEbccGenderModify(String ebccGenderModify) {
        this.ebccGenderModify = ebccGenderModify;
    }

    public BancaDetails withEbccGenderModify(String ebccGenderModify) {
        this.ebccGenderModify = ebccGenderModify;
        return this;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public BancaDetails withUtmSource(String utmSource) {
        this.utmSource = utmSource;
        return this;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public BancaDetails withSpCode(String spCode) {
        this.spCode = spCode;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public BancaDetails withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getYblFlagCustType() {
        return yblFlagCustType;
    }

    public void setYblFlagCustType(String yblFlagCustType) {
        this.yblFlagCustType = yblFlagCustType;
    }

    public BancaDetails withYblFlagCustType(String yblFlagCustType) {
        this.yblFlagCustType = yblFlagCustType;
        return this;
    }

    public String getYblCustomerType() {
        return yblCustomerType;
    }

    public void setYblCustomerType(String yblCustomerType) {
        this.yblCustomerType = yblCustomerType;
    }

    public BancaDetails withYblCustomerType(String yblCustomerType) {
        this.yblCustomerType = yblCustomerType;
        return this;
    }

    public String getYblCustomerId() {
        return yblCustomerId;
    }

    public void setYblCustomerId(String yblCustomerId) {
        this.yblCustomerId = yblCustomerId;
    }

    public BancaDetails withYblCustomerId(String yblCustomerId) {
        this.yblCustomerId = yblCustomerId;
        return this;
    }

    public String getYblBranchCode() {
        return yblBranchCode;
    }

    public void setYblBranchCode(String yblBranchCode) {
        this.yblBranchCode = yblBranchCode;
    }

    public BancaDetails withYblBranchCode(String yblBranchCode) {
        this.yblBranchCode = yblBranchCode;
        return this;
    }

    public String getYblBranchName() {
        return yblBranchName;
    }

    public void setYblBranchName(String yblBranchName) {
        this.yblBranchName = yblBranchName;
    }

    public BancaDetails withYblBranchName(String yblBranchName) {
        this.yblBranchName = yblBranchName;
        return this;
    }

    public String getYblPhoneOff() {
        return yblPhoneOff;
    }

    public void setYblPhoneOff(String yblPhoneOff) {
        this.yblPhoneOff = yblPhoneOff;
    }

    public BancaDetails withYblPhoneOff(String yblPhoneOff) {
        this.yblPhoneOff = yblPhoneOff;
        return this;
    }

    public String getYblCustomerClass() {
        return yblCustomerClass;
    }

    public void setYblCustomerClass(String yblCustomerClass) {
        this.yblCustomerClass = yblCustomerClass;
    }

    public BancaDetails withYblCustomerClass(String yblCustomerClass) {
        this.yblCustomerClass = yblCustomerClass;
        return this;
    }

    public String getYblLastKyc() {
        return yblLastKyc;
    }

    public void setYblLastKyc(String yblLastKyc) {
        this.yblLastKyc = yblLastKyc;
    }

    public BancaDetails withYblLastKyc(String yblLastKyc) {
        this.yblLastKyc = yblLastKyc;
        return this;
    }

    public String getYblSalesRmCode() {
        return yblSalesRmCode;
    }

    public void setYblSalesRmCode(String yblSalesRmCode) {
        this.yblSalesRmCode = yblSalesRmCode;
    }

    public BancaDetails withYblSalesRmCode(String yblSalesRmCode) {
        this.yblSalesRmCode = yblSalesRmCode;
        return this;
    }

    public String getYblServiceRmCode() {
        return yblServiceRmCode;
    }

    public void setYblServiceRmCode(String yblServiceRmCode) {
        this.yblServiceRmCode = yblServiceRmCode;
    }

    public BancaDetails withYblServiceRmCode(String yblServiceRmCode) {
        this.yblServiceRmCode = yblServiceRmCode;
        return this;
    }

    public String getYblAssetRmCode() {
        return yblAssetRmCode;
    }

    public void setYblAssetRmCode(String yblAssetRmCode) {
        this.yblAssetRmCode = yblAssetRmCode;
    }

    public BancaDetails withYblAssetRmCode(String yblAssetRmCode) {
        this.yblAssetRmCode = yblAssetRmCode;
        return this;
    }

    public String getYblSegment() {
        return yblSegment;
    }

    public void setYblSegment(String yblSegment) {
        this.yblSegment = yblSegment;
    }

    public BancaDetails withYblSegment(String yblSegment) {
        this.yblSegment = yblSegment;
        return this;
    }

    public String getYblProfession() {
        return yblProfession;
    }

    public void setYblProfession(String yblProfession) {
        this.yblProfession = yblProfession;
    }

    public BancaDetails withYblProfession(String yblProfession) {
        this.yblProfession = yblProfession;
        return this;
    }

    public String getYblOccupation() {
        return yblOccupation;
    }

    public void setYblOccupation(String yblOccupation) {
        this.yblOccupation = yblOccupation;
    }

    public BancaDetails withYblOccupation(String yblOccupation) {
        this.yblOccupation = yblOccupation;
        return this;
    }

    public String getYblReqRefNum() {
        return yblReqRefNum;
    }

    public void setYblReqRefNum(String yblReqRefNum) {
        this.yblReqRefNum = yblReqRefNum;
    }

    public BancaDetails withYblReqRefNum(String yblReqRefNum) {
        this.yblReqRefNum = yblReqRefNum;
        return this;
    }

    public String getYblDisplayNomineeFlag() {
        return yblDisplayNomineeFlag;
    }

    public void setYblDisplayNomineeFlag(String yblDisplayNomineeFlag) {
        this.yblDisplayNomineeFlag = yblDisplayNomineeFlag;
    }

    public BancaDetails withYblDisplayNomineeFlag(String yblDisplayNomineeFlag) {
        this.yblDisplayNomineeFlag = yblDisplayNomineeFlag;
        return this;
    }

    public String getYblRelationToAccHolder() {
        return yblRelationToAccHolder;
    }

    public void setYblRelationToAccHolder(String yblRelationToAccHolder) {
        this.yblRelationToAccHolder = yblRelationToAccHolder;
    }

    public BancaDetails withYblRelationToAccHolder(String yblRelationToAccHolder) {
        this.yblRelationToAccHolder = yblRelationToAccHolder;
        return this;
    }

    public String getYblRelationToNominee() {
        return yblRelationToNominee;
    }

    public void setYblRelationToNominee(String yblRelationToNominee) {
        this.yblRelationToNominee = yblRelationToNominee;
    }

    public BancaDetails withYblRelationToNominee(String yblRelationToNominee) {
        this.yblRelationToNominee = yblRelationToNominee;
        return this;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", BancaDetails.class.getSimpleName() + "[", "]")
                .add("axisTimeStamp='" + axisTimeStamp + "'")
                .add("axisRefId='" + axisRefId + "'")
                .add("axisLeadId='" + axisLeadId + "'")
                .add("axisSourceId='" + axisSourceId + "'")
                .add("axisRefferalCode='" + axisRefferalCode + "'")
                .add("axisProductName='" + axisProductName + "'")
                .add("partnerName='" + partnerName + "'")
                .add("axisUTMSource='" + axisUTMSource + "'")
                .add("axisUTMMedium='" + axisUTMMedium + "'")
                .add("axisUTMCampaign='" + axisUTMCampaign + "'")
                .add("axisSPCode='" + axisSPCode + "'")
                .add("axisSolId='" + axisSolId + "'")
                .add("axisProducerCode='" + axisProducerCode + "'")
                .add("ebccFlag='" + ebccFlag + "'")
                .add("axisSchemeCode='" + axisSchemeCode + "'")
                .add("ebccPanModify='" + ebccPanModify + "'")
                .add("ebccAddModify='" + ebccAddModify + "'")
                .add("ebccDobModify='" + ebccDobModify + "'")
                .add("ebccNameModify='" + ebccNameModify + "'")
                .add("bankJourney='" + bankJourney + "'")
                .add("cKycNo='" + cKycNo + "'")
                .add("axisNRIState='" + axisNRIState + "'")
                .add("nomineeName='" + nomineeName + "'")
                .add("nomineeDob='" + nomineeDob + "'")
                .add("nomineeRelation='" + nomineeRelation + "'")
                .add("accountNo='" + accountNo + "'")
                .add("accountType='" + accountType + "'")
                .add("ebccGenderModify='" + ebccGenderModify + "'")
                .add("utmSource='" + utmSource + "'")
                .add("spCode='" + spCode + "'")
                .add("timestamp='" + timestamp + "'")
                .add("yblFlagCustType='" + yblFlagCustType + "'")
                .add("yblCustomerType='" + yblCustomerType + "'")
                .add("yblCustomerId='" + yblCustomerId + "'")
                .add("yblBranchCode='" + yblBranchCode + "'")
                .add("yblBranchName='" + yblBranchName + "'")
                .add("yblPhoneOff='" + yblPhoneOff + "'")
                .add("yblCustomerClass='" + yblCustomerClass + "'")
                .add("yblLastKyc='" + yblLastKyc + "'")
                .add("yblSalesRmCode='" + yblSalesRmCode + "'")
                .add("yblServiceRmCode='" + yblServiceRmCode + "'")
                .add("yblAssetRmCode='" + yblAssetRmCode + "'")
                .add("yblSegment='" + yblSegment + "'")
                .add("yblProfession='" + yblProfession + "'")
                .add("yblOccupation='" + yblOccupation + "'")
                .add("yblReqRefNum='" + yblReqRefNum + "'")
                .add("yblDisplayNomineeFlag='" + yblDisplayNomineeFlag + "'")
                .add("yblRelationToAccHolder='" + yblRelationToAccHolder + "'")
                .add("yblRelationToNominee='" + yblRelationToNominee + "'")
                .add("customerID='" + customerID + "'")
                .toString();
    }
}
