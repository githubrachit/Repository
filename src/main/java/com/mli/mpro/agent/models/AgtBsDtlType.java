package com.mli.mpro.agent.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgtBsDtlType{
    @JsonProperty("agentId")
    public String getAgentId() { 
		 return this.agentId; } 
    public void setAgentId(String agentId) { 
		 this.agentId = agentId; } 
    String agentId;

    //FUL2-196166 Persistency Tag mapping
    @JsonProperty("uwAgentJoiningDt")
    public String uwAgentJoiningDt;

    @JsonProperty("title") 
    public String getTitle() { 
		 return this.title; } 
    public void setTitle(String title) { 
		 this.title = title; } 
    String title;
    @JsonProperty("agentName")
    public String getAgentName() { 
		 return this.agentName; } 
    public void setAgentName(String agentName) { 
		 this.agentName = agentName; }
    @Sensitive(MaskType.NAME)
    String agentName;
    @JsonProperty("designation") 
    public String getDesignation() { 
		 return this.designation; } 
    public void setDesignation(String designation) { 
		 this.designation = designation; } 
    String designation;
    @JsonProperty("branchAddr") 
    public String getBranchAddr() { 
		 return this.branchAddr; } 
    public void setBranchAddr(String branchAddr) { 
		 this.branchAddr = branchAddr; }
    @Sensitive(MaskType.ADDRESS)
    String branchAddr;
    @JsonProperty("isEmp") 
    public String getIsEmp() { 
		 return this.isEmp; } 
    public void setIsEmp(String isEmp) { 
		 this.isEmp = isEmp; } 
    String isEmp;
    @JsonProperty("goLocality") 
    public String getGoLocality() { 
		 return this.goLocality; } 
    public void setGoLocality(String goLocality) { 
		 this.goLocality = goLocality; } 
    String goLocality;
    @JsonProperty("agencyName") 
    public String getAgencyName() { 
		 return this.agencyName; } 
    public void setAgencyName(String agencyName) { 
		 this.agencyName = agencyName; } 
    String agencyName;
    @JsonProperty("dob") 
    public String getDob() { 
		 return this.dob; } 
    public void setDob(String dob) { 
		 this.dob = dob; }
   @Sensitive(MaskType.DOB)
    String dob;
    @JsonProperty("gender") 
    public String getGender() { 
		 return this.gender; } 
    public void setGender(String gender) { 
		 this.gender = gender; }
    @Sensitive(MaskType.GENDER)
    String gender;
    @JsonProperty("categoryCd") 
    public String getCategoryCd() { 
		 return this.categoryCd; } 
    public void setCategoryCd(String categoryCd) { 
		 this.categoryCd = categoryCd; } 
    String categoryCd;
    @JsonProperty("categoryDesc") 
    public String getCategoryDesc() { 
		 return this.categoryDesc; } 
    public void setCategoryDesc(String categoryDesc) { 
		 this.categoryDesc = categoryDesc; } 
    String categoryDesc;
    @JsonProperty("staffCode") 
    public String getStaffCode() { 
		 return this.staffCode; } 
    public void setStaffCode(String staffCode) { 
		 this.staffCode = staffCode; } 
    String staffCode;
    @JsonProperty("eduQufication") 
    public String getEduQufication() { 
		 return this.eduQufication; } 
    public void setEduQufication(String eduQufication) { 
		 this.eduQufication = eduQufication; } 
    String eduQufication;
    @JsonProperty("panNum") 
    public String getPanNum() { 
		 return this.panNum; } 
    public void setPanNum(String panNum) { 
		 this.panNum = panNum; }
    @Sensitive(MaskType.PAN_NUM)
    String panNum;
    @JsonProperty("agentSegmentCd") 
    public String getAgentSegmentCd() { 
		 return this.agentSegmentCd; } 
    public void setAgentSegmentCd(String agentSegmentCd) { 
		 this.agentSegmentCd = agentSegmentCd; } 
    String agentSegmentCd;
    @JsonProperty("agentSegmentDesc") 
    public String getAgentSegmentDesc() { 
		 return this.agentSegmentDesc; } 
    public void setAgentSegmentDesc(String agentSegmentDesc) { 
		 this.agentSegmentDesc = agentSegmentDesc; } 
    String agentSegmentDesc;
    @JsonProperty("agentClientID") 
    public String getAgentClientID() { 
		 return this.agentClientID; } 
    public void setAgentClientID(String agentClientID) { 
		 this.agentClientID = agentClientID; } 
    String agentClientID;
    @JsonProperty("maritalStatus") 
    public String getMaritalStatus() { 
		 return this.maritalStatus; } 
    public void setMaritalStatus(String maritalStatus) { 
		 this.maritalStatus = maritalStatus; } 
    String maritalStatus;
    @JsonProperty("fhName") 
    public String getFhName() { 
		 return this.fhName; } 
    public void setFhName(String fhName) { 
		 this.fhName = fhName; }
    @Sensitive(MaskType.NAME)
    String fhName;
    @JsonProperty("nomineeName") 
    public String getNomineeName() { 
		 return this.nomineeName; } 
    public void setNomineeName(String nomineeName) { 
		 this.nomineeName = nomineeName; }
    @Sensitive(MaskType.NAME)
    String nomineeName;
    @JsonProperty("perShrOfNominee") 
    public String getPerShrOfNominee() { 
		 return this.perShrOfNominee; } 
    public void setPerShrOfNominee(String perShrOfNominee) { 
		 this.perShrOfNominee = perShrOfNominee; } 
    String perShrOfNominee;
    @JsonProperty("nomineeAgentRelation") 
    public String getNomineeAgentRelation() { 
		 return this.nomineeAgentRelation; } 
    public void setNomineeAgentRelation(String nomineeAgentRelation) { 
		 this.nomineeAgentRelation = nomineeAgentRelation; } 
    String nomineeAgentRelation;
    @JsonProperty("channelType") 
    public String getChannelType() { 
		 return this.channelType; } 
    public void setChannelType(String channelType) { 
		 this.channelType = channelType; } 
    String channelType;
    @JsonProperty("panUpdateDt") 
    public String getPanUpdateDt() { 
		 return this.panUpdateDt; } 
    public void setPanUpdateDt(String panUpdateDt) { 
		 this.panUpdateDt = panUpdateDt; } 
    String panUpdateDt;
    @JsonProperty("eligyForMaxOne") 
    public String getEligyForMaxOne() { 
		 return this.eligyForMaxOne; } 
    public void setEligyForMaxOne(String eligyForMaxOne) { 
		 this.eligyForMaxOne = eligyForMaxOne; } 
    String eligyForMaxOne;
    @JsonProperty("supensionReason") 
    public String getSupensionReason() { 
		 return this.supensionReason; } 
    public void setSupensionReason(String supensionReason) { 
		 this.supensionReason = supensionReason; } 
    String supensionReason;
    @JsonProperty("recruitedBy") 
    public String getRecruitedBy() { 
		 return this.recruitedBy; } 
    public void setRecruitedBy(String recruitedBy) { 
		 this.recruitedBy = recruitedBy; } 
    String recruitedBy;
    @JsonProperty("recruitedByName") 
    public String getRecruitedByName() { 
		 return this.recruitedByName; } 
    public void setRecruitedByName(String recruitedByName) { 
		 this.recruitedByName = recruitedByName; } 
    String recruitedByName;
    @JsonProperty("reportingTo") 
    public String getReportingTo() { 
		 return this.reportingTo; } 
    public void setReportingTo(String reportingTo) { 
		 this.reportingTo = reportingTo; } 
    String reportingTo;
    @JsonProperty("reportingToName") 
    public String getReportingToName() { 
		 return this.reportingToName; } 
    public void setReportingToName(String reportingToName) { 
		 this.reportingToName = reportingToName; } 
    String reportingToName;
    @JsonProperty("rdcBonusReduction") 
    public String getRdcBonusReduction() { 
		 return this.rdcBonusReduction; } 
    public void setRdcBonusReduction(String rdcBonusReduction) { 
		 this.rdcBonusReduction = rdcBonusReduction; } 
    String rdcBonusReduction;
    @JsonProperty("referringAgentCode") 
    public String getReferringAgentCode() { 
		 return this.referringAgentCode; } 
    public void setReferringAgentCode(String referringAgentCode) { 
		 this.referringAgentCode = referringAgentCode; } 
    String referringAgentCode;
    @JsonProperty("referringAgentName") 
    public String getReferringAgentName() { 
		 return this.referringAgentName; } 
    public void setReferringAgentName(String referringAgentName) { 
		 this.referringAgentName = referringAgentName; } 
    String referringAgentName;
    @JsonProperty("managingBranch") 
    public String getManagingBranch() { 
		 return this.managingBranch; } 
    public void setManagingBranch(String managingBranch) { 
		 this.managingBranch = managingBranch; } 
    String managingBranch;
    @JsonProperty("ssnCodes") 
    public String getSsnCodes() { 
		 return this.ssnCodes; } 
    public void setSsnCodes(String ssnCodes) { 
		 this.ssnCodes = ssnCodes; }
    String ssnCodes;
    @JsonProperty("agentStatus") 
    public String getAgentStatus() { 
		 return this.agentStatus; } 
    public void setAgentStatus(String agentStatus) { 
		 this.agentStatus = agentStatus; } 
    String agentStatus;
    @JsonProperty("servBrId") 
    public String getServBrId() { 
		 return this.servBrId; } 
    public void setServBrId(String servBrId) { 
		 this.servBrId = servBrId; } 
    String servBrId;
    @JsonProperty("agentSegamentationCd") 
    public String getAgentSegamentationCd() { 
		 return this.agentSegamentationCd; } 
    public void setAgentSegamentationCd(String agentSegamentationCd) { 
		 this.agentSegamentationCd = agentSegamentationCd; } 
    String agentSegamentationCd;
    @JsonProperty("agentSegamentationDesc") 
    public String getAgentSegamentationDesc() { 
		 return this.agentSegamentationDesc; } 
    public void setAgentSegamentationDesc(String agentSegamentationDesc) { 
		 this.agentSegamentationDesc = agentSegamentationDesc; } 
    String agentSegamentationDesc;
    @JsonProperty("areaMgr") 
    public String getAreaMgr() { 
		 return this.areaMgr; } 
    public void setAreaMgr(String areaMgr) { 
		 this.areaMgr = areaMgr; } 
    String areaMgr;
    @JsonProperty("centreMgr") 
    public String getCentreMgr() { 
		 return this.centreMgr; } 
    public void setCentreMgr(String centreMgr) { 
		 this.centreMgr = centreMgr; } 
    String centreMgr;
    @JsonProperty("role") 
    public String getRole() { 
		 return this.role; } 
    public void setRole(String role) { 
		 this.role = role; } 
    String role;
    @JsonProperty("agentActiveCode") 
    public String getAgentActiveCode() { 
		 return this.agentActiveCode; } 
    public void setAgentActiveCode(String agentActiveCode) { 
		 this.agentActiveCode = agentActiveCode; } 
    String agentActiveCode;
    @JsonProperty("commissionableFlag") 
    public String getCommissionableFlag() { 
		 return this.commissionableFlag; } 
    public void setCommissionableFlag(String commissionableFlag) { 
		 this.commissionableFlag = commissionableFlag; } 
    String commissionableFlag;
    @JsonProperty("channelName") 
    public String getChannelName() { 
		 return this.channelName; } 
    public void setChannelName(String channelName) { 
		 this.channelName = channelName; } 
    String channelName;
    @JsonProperty("branchName") 
    public String getBranchName() { 
		 return this.branchName; } 
    public void setBranchName(String branchName) { 
		 this.branchName = branchName; } 
    String branchName;
    @JsonProperty("reason") 
    public String getReason() { 
		 return this.reason; } 
    public void setReason(String reason) { 
		 this.reason = reason; } 
    String reason;
    @JsonProperty("agentPerst") 
    public String getAgentPerst() { 
		 return this.agentPerst; } 
    public void setAgentPerst(String agentPerst) { 
		 this.agentPerst = agentPerst; } 
    String agentPerst;
    @JsonProperty("isPlatinum") 
    public String getIsPlatinum() { 
		 return this.isPlatinum; } 
    public void setIsPlatinum(String isPlatinum) { 
		 this.isPlatinum = isPlatinum; } 
    String isPlatinum;
    @JsonProperty("solId") 
    public String getSolId() { 
		 return this.solId; } 
    public void setSolId(String solId) { 
		 this.solId = solId; } 
    String solId;
    @JsonProperty("applicationId") 
    public String getApplicationId() { 
		 return this.applicationId; } 
    public void setApplicationId(String applicationId) { 
		 this.applicationId = applicationId; } 
    String applicationId;
    @JsonProperty("customerId") 
    public String getCustomerId() { 
		 return this.customerId; } 
    public void setCustomerId(String customerId) { 
		 this.customerId = customerId; } 
    String customerId;
    @JsonProperty("customerClassification") 
    public String getCustomerClassification() { 
		 return this.customerClassification; } 
    public void setCustomerClassification(String customerClassification) { 
		 this.customerClassification = customerClassification; } 
    String customerClassification;
    @JsonProperty("customerSegment") 
    public String getCustomerSegment() { 
		 return this.customerSegment; } 
    public void setCustomerSegment(String customerSegment) { 
		 this.customerSegment = customerSegment; } 
    String customerSegment;
    @JsonProperty("raId") 
    public String getRaId() { 
		 return this.raId; } 
    public void setRaId(String raId) { 
		 this.raId = raId; } 
    String raId;
    @JsonProperty("secondaryBranchCodeAll") 
    public ArrayList<Object> getSecondaryBranchCodeAll() {
		 return this.secondaryBranchCodeAll; } 
    public void setSecondaryBranchCodeAll(ArrayList<Object> secondaryBranchCodeAll) { 
		 this.secondaryBranchCodeAll = secondaryBranchCodeAll; } 
    ArrayList<Object> secondaryBranchCodeAll;
    @JsonProperty("agentActiveStatuscode") 
    public String getAgentActiveStatuscode() { 
		 return this.agentActiveStatuscode; } 
    public void setAgentActiveStatuscode(String agentActiveStatuscode) { 
		 this.agentActiveStatuscode = agentActiveStatuscode; } 
    String agentActiveStatuscode;
    @JsonProperty("agentAgencyCode") 
    public String getAgentAgencyCode() { 
		 return this.agentAgencyCode; } 
    public void setAgentAgencyCode(String agentAgencyCode) { 
		 this.agentAgencyCode = agentAgencyCode; } 
    String agentAgencyCode;
    @JsonProperty("agentAgencyName") 
    public String getAgentAgencyName() { 
		 return this.agentAgencyName; } 
    public void setAgentAgencyName(String agentAgencyName) { 
		 this.agentAgencyName = agentAgencyName; } 
    String agentAgencyName;
    @JsonProperty("suspensionDt") 
    public String getSuspensionDt() { 
		 return this.suspensionDt; } 
    public void setSuspensionDt(String suspensionDt) { 
		 this.suspensionDt = suspensionDt; } 
    String suspensionDt;
    @JsonProperty("suspensionRemovalDt") 
    public String getSuspensionRemovalDt() { 
		 return this.suspensionRemovalDt; } 
    public void setSuspensionRemovalDt(String suspensionRemovalDt) { 
		 this.suspensionRemovalDt = suspensionRemovalDt; } 
    String suspensionRemovalDt;
    @JsonProperty("strAdhaarNo") 
    public String getStrAdhaarNo() { 
		 return this.strAdhaarNo; } 
    public void setStrAdhaarNo(String strAdhaarNo) { 
		 this.strAdhaarNo = strAdhaarNo; }
    @Sensitive(MaskType.AADHAAR_NUM)
    String strAdhaarNo;
    @JsonProperty("strSumAssured") 
    public String getStrSumAssured() { 
		 return this.strSumAssured; } 
    public void setStrSumAssured(String strSumAssured) { 
		 this.strSumAssured = strSumAssured; } 
    String strSumAssured;
    @JsonProperty("strBranchCity") 
    public String getStrBranchCity() { 
		 return this.strBranchCity; } 
    public void setStrBranchCity(String strBranchCity) { 
		 this.strBranchCity = strBranchCity; } 
    String strBranchCity;
    @JsonProperty("strBranchState") 
    public String getStrBranchState() { 
		 return this.strBranchState; } 
    public void setStrBranchState(String strBranchState) { 
		 this.strBranchState = strBranchState; } 
    String strBranchState;
    @JsonProperty("strBranchPin") 
    public String getStrBranchPin() { 
		 return this.strBranchPin; } 
    public void setStrBranchPin(String strBranchPin) { 
		 this.strBranchPin = strBranchPin; }
    @Sensitive(MaskType.PINCODE)
    String strBranchPin;

    @JsonProperty("secondaryBranchNameAll")
    List<String> secondaryBranchNameAll;
    public List<String> getSecondaryBranchNameAll() {
        return secondaryBranchNameAll;
    }

    public AgtBsDtlType setSecondaryBranchNameAll(List<String> secondaryBranchNameAll) {
        this.secondaryBranchNameAll = secondaryBranchNameAll;
        return this;
    }

    public String getUwAgentJoiningDt() {
        return uwAgentJoiningDt;
    }

    public void setUwAgentJoiningDt(String uwAgentJoiningDt) {
        this.uwAgentJoiningDt = uwAgentJoiningDt;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "AgtBsDtlType{" +
                "agentId='" + agentId + '\'' +
                ", title='" + title + '\'' +
                ", agentName='" + agentName + '\'' +
                ", designation='" + designation + '\'' +
                ", branchAddr='" + branchAddr + '\'' +
                ", isEmp='" + isEmp + '\'' +
                ", goLocality='" + goLocality + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", categoryCd='" + categoryCd + '\'' +
                ", categoryDesc='" + categoryDesc + '\'' +
                ", staffCode='" + staffCode + '\'' +
                ", eduQufication='" + eduQufication + '\'' +
                ", panNum='" + panNum + '\'' +
                ", agentSegmentCd='" + agentSegmentCd + '\'' +
                ", agentSegmentDesc='" + agentSegmentDesc + '\'' +
                ", agentClientID='" + agentClientID + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", fhName='" + fhName + '\'' +
                ", nomineeName='" + nomineeName + '\'' +
                ", perShrOfNominee='" + perShrOfNominee + '\'' +
                ", nomineeAgentRelation='" + nomineeAgentRelation + '\'' +
                ", channelType='" + channelType + '\'' +
                ", panUpdateDt='" + panUpdateDt + '\'' +
                ", eligyForMaxOne='" + eligyForMaxOne + '\'' +
                ", supensionReason='" + supensionReason + '\'' +
                ", recruitedBy='" + recruitedBy + '\'' +
                ", recruitedByName='" + recruitedByName + '\'' +
                ", reportingTo='" + reportingTo + '\'' +
                ", reportingToName='" + reportingToName + '\'' +
                ", rdcBonusReduction='" + rdcBonusReduction + '\'' +
                ", referringAgentCode='" + referringAgentCode + '\'' +
                ", referringAgentName='" + referringAgentName + '\'' +
                ", managingBranch='" + managingBranch + '\'' +
                ", ssnCodes='" + ssnCodes + '\'' +
                ", agentStatus='" + agentStatus + '\'' +
                ", servBrId='" + servBrId + '\'' +
                ", agentSegamentationCd='" + agentSegamentationCd + '\'' +
                ", agentSegamentationDesc='" + agentSegamentationDesc + '\'' +
                ", areaMgr='" + areaMgr + '\'' +
                ", centreMgr='" + centreMgr + '\'' +
                ", role='" + role + '\'' +
                ", agentActiveCode='" + agentActiveCode + '\'' +
                ", commissionableFlag='" + commissionableFlag + '\'' +
                ", channelName='" + channelName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", reason='" + reason + '\'' +
                ", agentPerst='" + agentPerst + '\'' +
                ", isPlatinum='" + isPlatinum + '\'' +
                ", solId='" + solId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerClassification='" + customerClassification + '\'' +
                ", customerSegment='" + customerSegment + '\'' +
                ", raId='" + raId + '\'' +
                ", secondaryBranchCodeAll=" + secondaryBranchCodeAll +
                ", agentActiveStatuscode='" + agentActiveStatuscode + '\'' +
                ", agentAgencyCode='" + agentAgencyCode + '\'' +
                ", agentAgencyName='" + agentAgencyName + '\'' +
                ", suspensionDt='" + suspensionDt + '\'' +
                ", suspensionRemovalDt='" + suspensionRemovalDt + '\'' +
                ", strAdhaarNo='" + strAdhaarNo + '\'' +
                ", strSumAssured='" + strSumAssured + '\'' +
                ", strBranchCity='" + strBranchCity + '\'' +
                ", strBranchState='" + strBranchState + '\'' +
                ", strBranchPin='" + strBranchPin + '\'' +
                ", uwAgentJoiningDt='" + uwAgentJoiningDt + '\'' +
                '}';
    }
}
