
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CKYCPersonalDetail")
public class CkycPersonalDetail
{

    @JsonProperty("recordIdentifier")
    private String recordIdentifier;
    @JsonProperty("applicationFormNo")
    private String applicationFormNo;
    @Sensitive(MaskType.BRANCH_CODE)
    @JsonProperty("branchCode")
    private String branchCode;
    @JsonProperty("sourceSystem")
    private String sourceSystem;
    @JsonProperty("sourceSystemSegment")
    private String sourceSystemSegment;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("ckycConstiType")
    private String ckycConstiType;
    @JsonProperty("ckycAccType")
    private String ckycAccType;
    @Sensitive(MaskType.CKYC_NUM)
    @JsonProperty("ckycNumber")
    private String ckycNumber;
    @JsonProperty("ckycNamePrefix")
    private String ckycNamePrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycFirstName")
    private String ckycFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycMiddleName")
    private String ckycMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycLastName")
    private String ckycLastName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("ckycFullName")
    private String ckycFullName;
    @JsonProperty("ckycMaidenNamePrefix")
    private String ckycMaidenNamePrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycMaidenFirstName")
    private String ckycMaidenFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycMaidenMiddleName")
    private String ckycMaidenMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycMaidenLastName")
    private String ckycMaidenLastName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("ckycMaidenFullName")
    private String ckycMaidenFullName;
    @JsonProperty("ckycFatherNamePrefix")
    private String ckycFatherNamePrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycFatherFirstName")
    private String ckycFatherFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycFatherMiddleName")
    private String ckycFatherMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycFatherLastName")
    private String ckycFatherLastName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("ckycFatherFullName")
    private String ckycFatherFullName;
    @JsonProperty("ckycMotherNamePrefix")
    private String ckycMotherNamePrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycMotherFirstName")
    private String ckycMotherFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycMotherMiddletName")
    private String ckycMotherMiddletName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycMotherLastName")
    private String ckycMotherLastName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("ckycMotherFullName")
    private String ckycMotherFullName;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("ckycGender")
    private String ckycGender;
    @Sensitive(MaskType.DOB)
    @JsonProperty("ckycDOB")
    private String ckycDOB;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("ckycPAN")
    private String ckycPAN;
    @JsonProperty("ckycFormSixty")
    private String ckycFormSixty;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAdd1")
    private String ckycPerAdd1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAdd2")
    private String ckycPerAdd2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAdd3")
    private String ckycPerAdd3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAddCity")
    private String ckycPerAddCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAddDistrict")
    private String ckycPerAddDistrict;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAddState")
    private String ckycPerAddState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycPerAddCountry")
    private String ckycPerAddCountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("ckycPerAddPin")
    private String ckycPerAddPin;
    @JsonProperty("ckycPerAddPOA")
    private String ckycPerAddPOA;
    @JsonProperty("ckycPerAddSameasCorAdd")
    private String ckycPerAddSameasCorAdd;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAdd1")
    private String ckycCorAdd1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAdd2")
    private String ckycCorAdd2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAdd3")
    private String ckycCorAdd3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAddCity")
    private String ckycCorAddCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAddDistrict")
    private String ckycCorAddDistrict;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAddState")
    private String ckycCorAddState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycCorAddCountry")
    private String ckycCorAddCountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("ckycCorAddPin")
    private String ckycCorAddPin;
    @JsonProperty("ckycCorAddPOA")
    private String ckycCorAddPOA;
    @JsonProperty("ckycResTelSTD")
    private String ckycResTelSTD;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("ckycResTelNumber")
    private String ckycResTelNumber;
    @JsonProperty("ckycOffTelSTD")
    private String ckycOffTelSTD;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("ckycOffTelNumber")
    private String ckycOffTelNumber;
    @JsonProperty("ckycMobileISD")
    private String ckycMobileISD;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("ckycMobileNumber")
    private String ckycMobileNumber;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("ckycEmailAdd")
    private String ckycEmailAdd;
    @JsonProperty("ckycRemarks")
    private String ckycRemarks;
    @JsonProperty("ckycDateofDeclaration")
    private String ckycDateofDeclaration;
    @JsonProperty("ckycPlaceofDeclaration")
    private String ckycPlaceofDeclaration;
    @JsonProperty("ckycKYCVerificationDate")
    private String ckycKYCVerificationDate;
    @JsonProperty("ckycTypeofDocSubmitted")
    private String ckycTypeofDocSubmitted;
    @JsonProperty("ckycKYCVerificationName")
    private String ckycKYCVerificationName;
    @JsonProperty("ckycKYCVerificationDesg")
    private String ckycKYCVerificationDesg;
    @JsonProperty("ckycKYCVerificationBranch")
    private String ckycKYCVerificationBranch;
    @JsonProperty("ckycKYCVerificationEmpcode")
    private String ckycKYCVerificationEmpcode;
    @JsonProperty("ckycNumberofIds")
    private String ckycNumberofIds;
    @JsonProperty("ckycNumberofRelatedPersons")
    private String ckycNumberofRelatedPersons;
    @JsonProperty("ckycNumberofImages")
    private String ckycNumberofImages;

    private final static long serialVersionUID = -671865880628931541L;

    @JsonProperty("recordIdentifier")
    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    @JsonProperty("recordIdentifier")
    @XmlElement(name = "RecordIdentifier")
    public void setRecordIdentifier(String recordIdentifier) {
        this.recordIdentifier = recordIdentifier;
    }

    @JsonProperty("applicationFormNo")
    public String getApplicationFormNo() {
        return applicationFormNo;
    }

    @JsonProperty("applicationFormNo")
    @XmlElement(name = "ApplicationFormNo")
    public void setApplicationFormNo(String applicationFormNo) {
        this.applicationFormNo = applicationFormNo;
    }

    @JsonProperty("branchCode")
    public String getBranchCode() {
        return branchCode;
    }

    @JsonProperty("branchCode")
    @XmlElement(name = "BranchCode")
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @JsonProperty("sourceSystem")
    public String getSourceSystem() {
        return sourceSystem;
    }

    @JsonProperty("sourceSystem")
    @XmlElement(name = "SourceSystem")
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    @JsonProperty("sourceSystemSegment")
    public String getSourceSystemSegment() {
        return sourceSystemSegment;
    }

    @JsonProperty("sourceSystemSegment")
    @XmlElement(name = "SourceSystemSegment")
    public void setSourceSystemSegment(String sourceSystemSegment) {
        this.sourceSystemSegment = sourceSystemSegment;
    }

    @JsonProperty("remarks")
    public String getRemarks() {
        return remarks;
    }

    @JsonProperty("remarks")
    @XmlElement(name = "Remarks")
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("ckycConstiType")
    public String getCkycConstiType() {
        return ckycConstiType;
    }

    @JsonProperty("ckycConstiType")
    @XmlElement(name = "CKYCConstiType")
    public void setCkycConstiType(String ckycConstiType) {
        this.ckycConstiType = ckycConstiType;
    }

    @JsonProperty("ckycAccType")
    public String getCkycAccType() {
        return ckycAccType;
    }

    @JsonProperty("ckycAccType")
    @XmlElement(name = "CKYCAccType")
    public void setCkycAccType(String ckycAccType) {
        this.ckycAccType = ckycAccType;
    }

    @JsonProperty("ckycNumber")
    public String getCkycNumber() {
        return ckycNumber;
    }

    @JsonProperty("ckycNumber")
    @XmlElement(name = "CKYCNumber")
    public void setCkycNumber(String ckycNumber) {
        this.ckycNumber = ckycNumber;
    }

    @JsonProperty("ckycNamePrefix")
    public String getCkycNamePrefix() {
        return ckycNamePrefix;
    }

    @JsonProperty("ckycNamePrefix")
    @XmlElement(name = "CKYCNamePrefix")
    public void setCkycNamePrefix(String ckycNamePrefix) {
        this.ckycNamePrefix = ckycNamePrefix;
    }

    @JsonProperty("ckycFirstName")
    public String getCkycFirstName() {
        return ckycFirstName;
    }

    @JsonProperty("ckycFirstName")
    @XmlElement(name = "CKYCFirstName")
    public void setCkycFirstName(String ckycFirstName) {
        this.ckycFirstName = ckycFirstName;
    }

    @JsonProperty("ckycMiddleName")
    public String getCkycMiddleName() {
        return ckycMiddleName;
    }

    @JsonProperty("ckycMiddleName")
    @XmlElement(name = "CKYCMiddleName")
    public void setCkycMiddleName(String ckycMiddleName) {
        this.ckycMiddleName = ckycMiddleName;
    }

    @JsonProperty("ckycLastName")
    public String getCkycLastName() {
        return ckycLastName;
    }

    @JsonProperty("ckycLastName")
    @XmlElement(name = "CKYCLastName")
    public void setCkycLastName(String ckycLastName) {
        this.ckycLastName = ckycLastName;
    }

    @JsonProperty("ckycFullName")
    public String getCkycFullName() {
        return ckycFullName;
    }

    @JsonProperty("ckycFullName")
    @XmlElement(name = "CKYCFullName")
    public void setCkycFullName(String ckycFullName) {
        this.ckycFullName = ckycFullName;
    }

    @JsonProperty("ckycMaidenNamePrefix")
    public String getCkycMaidenNamePrefix() {
        return ckycMaidenNamePrefix;
    }

    @JsonProperty("ckycMaidenNamePrefix")
    @XmlElement(name = "CKYCMaidenNamePrefix")
    public void setCkycMaidenNamePrefix(String ckycMaidenNamePrefix) {
        this.ckycMaidenNamePrefix = ckycMaidenNamePrefix;
    }

    @JsonProperty("ckycMaidenFirstName")
    public String getCkycMaidenFirstName() {
        return ckycMaidenFirstName;
    }

    @JsonProperty("ckycMaidenFirstName")
    @XmlElement(name = "CKYCMaidenFirstName")
    public void setCkycMaidenFirstName(String ckycMaidenFirstName) {
        this.ckycMaidenFirstName = ckycMaidenFirstName;
    }

    @JsonProperty("ckycMaidenMiddleName")
    public String getCkycMaidenMiddleName() {
        return ckycMaidenMiddleName;
    }

    @JsonProperty("ckycMaidenMiddleName")
    @XmlElement(name = "CKYCMaidenMiddleName")
    public void setCkycMaidenMiddleName(String ckycMaidenMiddleName) {
        this.ckycMaidenMiddleName = ckycMaidenMiddleName;
    }

    @JsonProperty("ckycMaidenLastName")
    public String getCkycMaidenLastName() {
        return ckycMaidenLastName;
    }

    @JsonProperty("ckycMaidenLastName")
    @XmlElement(name = "CKYCMaidenLastName")
    public void setCkycMaidenLastName(String ckycMaidenLastName) {
        this.ckycMaidenLastName = ckycMaidenLastName;
    }

    @JsonProperty("ckycMaidenFullName")
    public String getCkycMaidenFullName() {
        return ckycMaidenFullName;
    }

    @JsonProperty("ckycMaidenFullName")
    @XmlElement(name = "CKYCMaidenFullName")
    public void setCkycMaidenFullName(String ckycMaidenFullName) {
        this.ckycMaidenFullName = ckycMaidenFullName;
    }

    @JsonProperty("ckycFatherNamePrefix")
    public String getCkycFatherNamePrefix() {
        return ckycFatherNamePrefix;
    }

    @JsonProperty("ckycFatherNamePrefix")
    @XmlElement(name = "CKYCFatherNamePrefix")
    public void setCkycFatherNamePrefix(String ckycFatherNamePrefix) {
        this.ckycFatherNamePrefix = ckycFatherNamePrefix;
    }

    @JsonProperty("ckycFatherFirstName")
    public String getCkycFatherFirstName() {
        return ckycFatherFirstName;
    }

    @JsonProperty("ckycFatherFirstName")
    @XmlElement(name = "CKYCFatherFirstName")
    public void setCkycFatherFirstName(String ckycFatherFirstName) {
        this.ckycFatherFirstName = ckycFatherFirstName;
    }

    @JsonProperty("ckycFatherMiddleName")
    public String getCkycFatherMiddleName() {
        return ckycFatherMiddleName;
    }

    @JsonProperty("ckycFatherMiddleName")
    @XmlElement(name = "CKYCFatherMiddleName")
    public void setCkycFatherMiddleName(String ckycFatherMiddleName) {
        this.ckycFatherMiddleName = ckycFatherMiddleName;
    }

    @JsonProperty("ckycFatherLastName")
    public String getCkycFatherLastName() {
        return ckycFatherLastName;
    }

    @JsonProperty("ckycFatherLastName")
    @XmlElement(name = "CKYCFatherLastName")
    public void setCkycFatherLastName(String ckycFatherLastName) {
        this.ckycFatherLastName = ckycFatherLastName;
    }

    @JsonProperty("ckycFatherFullName")
    public String getCkycFatherFullName() {
        return ckycFatherFullName;
    }

    @JsonProperty("ckycFatherFullName")
    @XmlElement(name = "CKYCFatherFullName")
    public void setCkycFatherFullName(String ckycFatherFullName) {
        this.ckycFatherFullName = ckycFatherFullName;
    }

    @JsonProperty("ckycMotherNamePrefix")
    public String getCkycMotherNamePrefix() {
        return ckycMotherNamePrefix;
    }

    @JsonProperty("ckycMotherNamePrefix")
    @XmlElement(name = "CKYCMotherNamePrefix")
    public void setCkycMotherNamePrefix(String ckycMotherNamePrefix) {
        this.ckycMotherNamePrefix = ckycMotherNamePrefix;
    }

    @JsonProperty("ckycMotherFirstName")
    public String getCkycMotherFirstName() {
        return ckycMotherFirstName;
    }

    @JsonProperty("ckycMotherFirstName")
    @XmlElement(name = "CKYCMotherFirstName")
    public void setCkycMotherFirstName(String ckycMotherFirstName) {
        this.ckycMotherFirstName = ckycMotherFirstName;
    }

    @JsonProperty("ckycMotherMiddletName")
    public String getCkycMotherMiddletName() {
        return ckycMotherMiddletName;
    }

    @JsonProperty("ckycMotherMiddletName")
    @XmlElement(name = "CKYCMotherMiddletName")
    public void setCkycMotherMiddletName(String ckycMotherMiddletName) {
        this.ckycMotherMiddletName = ckycMotherMiddletName;
    }

    @JsonProperty("ckycMotherLastName")
    public String getCkycMotherLastName() {
        return ckycMotherLastName;
    }

    @JsonProperty("ckycMotherLastName")
    @XmlElement(name = "CKYCMotherLastName")
    public void setCkycMotherLastName(String ckycMotherLastName) {
        this.ckycMotherLastName = ckycMotherLastName;
    }

    @JsonProperty("ckycMotherFullName")
    public String getCkycMotherFullName() {
        return ckycMotherFullName;
    }

    @JsonProperty("ckycMotherFullName")
    @XmlElement(name = "CKYCMotherFullName")
    public void setCkycMotherFullName(String ckycMotherFullName) {
        this.ckycMotherFullName = ckycMotherFullName;
    }

    @JsonProperty("ckycGender")
    public String getCkycGender() {
        return ckycGender;
    }

    @JsonProperty("ckycGender")
    @XmlElement(name = "CKYCGender")
    public void setCkycGender(String ckycGender) {
        this.ckycGender = ckycGender;
    }

    @JsonProperty("ckycDOB")
    public String getCkycDOB() {
        return ckycDOB;
    }

    @JsonProperty("ckycDOB")
    @XmlElement(name = "CKYCDOB")
    public void setCkycDOB(String ckycDOB) {
        this.ckycDOB = ckycDOB;
    }

    @JsonProperty("ckycPAN")
    public String getCkycPAN() {
        return ckycPAN;
    }

    @JsonProperty("ckycPAN")
    @XmlElement(name = "CKYCPAN")
    public void setCkycPAN(String ckycPAN) {
        this.ckycPAN = ckycPAN;
    }

    @JsonProperty("ckycFormSixty")
    public String getCkycFormSixty() {
        return ckycFormSixty;
    }

    @JsonProperty("ckycFormSixty")
    @XmlElement(name = "CKYCFormSixty")
    public void setCkycFormSixty(String ckycFormSixty) {
        this.ckycFormSixty = ckycFormSixty;
    }

    @JsonProperty("ckycPerAdd1")
    public String getCkycPerAdd1() {
        return ckycPerAdd1;
    }

    @JsonProperty("ckycPerAdd1")
    @XmlElement(name = "CKYCPerAdd1")
    public void setCkycPerAdd1(String ckycPerAdd1) {
        this.ckycPerAdd1 = ckycPerAdd1;
    }

    @JsonProperty("ckycPerAdd2")
    public String getCkycPerAdd2() {
        return ckycPerAdd2;
    }

    @JsonProperty("ckycPerAdd2")
    @XmlElement(name = "CKYCPerAdd2")
    public void setCkycPerAdd2(String ckycPerAdd2) {
        this.ckycPerAdd2 = ckycPerAdd2;
    }

    @JsonProperty("ckycPerAdd3")
    public String getCkycPerAdd3() {
        return ckycPerAdd3;
    }

    @JsonProperty("ckycPerAdd3")
    @XmlElement(name = "CKYCPerAdd3")
    public void setCkycPerAdd3(String ckycPerAdd3) {
        this.ckycPerAdd3 = ckycPerAdd3;
    }

    @JsonProperty("ckycPerAddCity")
    public String getCkycPerAddCity() {
        return ckycPerAddCity;
    }

    @JsonProperty("ckycPerAddCity")
    @XmlElement(name = "CKYCPerAddCity")
    public void setCkycPerAddCity(String ckycPerAddCity) {
        this.ckycPerAddCity = ckycPerAddCity;
    }

    @JsonProperty("ckycPerAddDistrict")
    public String getCkycPerAddDistrict() {
        return ckycPerAddDistrict;
    }

    @JsonProperty("ckycPerAddDistrict")
    @XmlElement(name = "CKYCPerAddDistrict")
    public void setCkycPerAddDistrict(String ckycPerAddDistrict) {
        this.ckycPerAddDistrict = ckycPerAddDistrict;
    }

    @JsonProperty("ckycPerAddState")
    public String getCkycPerAddState() {
        return ckycPerAddState;
    }

    @JsonProperty("ckycPerAddState")
    @XmlElement(name = "CKYCPerAddState")
    public void setCkycPerAddState(String ckycPerAddState) {
        this.ckycPerAddState = ckycPerAddState;
    }

    @JsonProperty("ckycPerAddCountry")
    public String getCkycPerAddCountry() {
        return ckycPerAddCountry;
    }

    @JsonProperty("ckycPerAddCountry")
    @XmlElement(name = "CKYCPerAddCountry")
    public void setCkycPerAddCountry(String ckycPerAddCountry) {
        this.ckycPerAddCountry = ckycPerAddCountry;
    }

    @JsonProperty("ckycPerAddPin")
    public String getCkycPerAddPin() {
        return ckycPerAddPin;
    }

    @JsonProperty("ckycPerAddPin")
    @XmlElement(name = "CKYCPerAddPin")
    public void setCkycPerAddPin(String ckycPerAddPin) {
        this.ckycPerAddPin = ckycPerAddPin;
    }

    @JsonProperty("ckycPerAddPOA")
    public String getCkycPerAddPOA() {
        return ckycPerAddPOA;
    }

    @JsonProperty("ckycPerAddPOA")
    @XmlElement(name = "CKYCPerAddPOA")
    public void setCkycPerAddPOA(String ckycPerAddPOA) {
        this.ckycPerAddPOA = ckycPerAddPOA;
    }

    @JsonProperty("ckycPerAddSameasCorAdd")
    public String getCkycPerAddSameasCorAdd() {
        return ckycPerAddSameasCorAdd;
    }

    @JsonProperty("ckycPerAddSameasCorAdd")
    @XmlElement(name = "CKYCPerAddSameasCorAdd")
    public void setCkycPerAddSameasCorAdd(String ckycPerAddSameasCorAdd) {
        this.ckycPerAddSameasCorAdd = ckycPerAddSameasCorAdd;
    }

    @JsonProperty("ckycCorAdd1")
    public String getCkycCorAdd1() {
        return ckycCorAdd1;
    }

    @JsonProperty("ckycCorAdd1")
    @XmlElement(name = "CKYCCorAdd1")
    public void setCkycCorAdd1(String ckycCorAdd1) {
        this.ckycCorAdd1 = ckycCorAdd1;
    }

    @JsonProperty("ckycCorAdd2")
    public String getCkycCorAdd2() {
        return ckycCorAdd2;
    }

    @JsonProperty("ckycCorAdd2")
    @XmlElement(name = "CKYCCorAdd2")
    public void setCkycCorAdd2(String ckycCorAdd2) {
        this.ckycCorAdd2 = ckycCorAdd2;
    }

    @JsonProperty("ckycCorAdd3")
    public String getCkycCorAdd3() {
        return ckycCorAdd3;
    }

    @JsonProperty("ckycCorAdd3")
    @XmlElement(name = "CKYCCorAdd3")
    public void setCkycCorAdd3(String ckycCorAdd3) {
        this.ckycCorAdd3 = ckycCorAdd3;
    }

    @JsonProperty("ckycCorAddCity")
    public String getCkycCorAddCity() {
        return ckycCorAddCity;
    }

    @JsonProperty("ckycCorAddCity")
    @XmlElement(name = "CKYCCorAddCity")
    public void setCkycCorAddCity(String ckycCorAddCity) {
        this.ckycCorAddCity = ckycCorAddCity;
    }

    @JsonProperty("ckycCorAddDistrict")
    public String getCkycCorAddDistrict() {
        return ckycCorAddDistrict;
    }

    @JsonProperty("ckycCorAddDistrict")
    @XmlElement(name = "CKYCCorAddDistrict")
    public void setCkycCorAddDistrict(String ckycCorAddDistrict) {
        this.ckycCorAddDistrict = ckycCorAddDistrict;
    }

    @JsonProperty("ckycCorAddState")
    public String getCkycCorAddState() {
        return ckycCorAddState;
    }

    @JsonProperty("ckycCorAddState")
    @XmlElement(name = "CKYCCorAddState")
    public void setCkycCorAddState(String ckycCorAddState) {
        this.ckycCorAddState = ckycCorAddState;
    }

    @JsonProperty("ckycCorAddCountry")
    public String getCkycCorAddCountry() {
        return ckycCorAddCountry;
    }

    @JsonProperty("ckycCorAddCountry")
    @XmlElement(name = "CKYCCorAddCountry")
    public void setCkycCorAddCountry(String ckycCorAddCountry) {
        this.ckycCorAddCountry = ckycCorAddCountry;
    }

    @JsonProperty("ckycCorAddPin")
    public String getCkycCorAddPin() {
        return ckycCorAddPin;
    }

    @JsonProperty("ckycCorAddPin")
    @XmlElement(name = "CKYCCorAddPin")
    public void setCkycCorAddPin(String ckycCorAddPin) {
        this.ckycCorAddPin = ckycCorAddPin;
    }

    @JsonProperty("ckycCorAddPOA")
    public String getCkycCorAddPOA() {
        return ckycCorAddPOA;
    }

    @JsonProperty("ckycCorAddPOA")
    @XmlElement(name = "CKYCCorAddPOA")
    public void setCkycCorAddPOA(String ckycCorAddPOA) {
        this.ckycCorAddPOA = ckycCorAddPOA;
    }

    @JsonProperty("ckycResTelSTD")
    public String getCkycResTelSTD() {
        return ckycResTelSTD;
    }

    @JsonProperty("ckycResTelSTD")
    @XmlElement(name = "CKYCResTelSTD")
    public void setCkycResTelSTD(String ckycResTelSTD) {
        this.ckycResTelSTD = ckycResTelSTD;
    }

    @JsonProperty("ckycResTelNumber")
    public String getCkycResTelNumber() {
        return ckycResTelNumber;
    }

    @JsonProperty("ckycResTelNumber")
    @XmlElement(name = "CKYCResTelNumber")
    public void setCkycResTelNumber(String ckycResTelNumber) {
        this.ckycResTelNumber = ckycResTelNumber;
    }

    @JsonProperty("ckycOffTelSTD")
    public String getCkycOffTelSTD() {
        return ckycOffTelSTD;
    }

    @JsonProperty("ckycOffTelSTD")
    @XmlElement(name = "CKYCOffTelSTD")
    public void setCkycOffTelSTD(String ckycOffTelSTD) {
        this.ckycOffTelSTD = ckycOffTelSTD;
    }

    @JsonProperty("ckycOffTelNumber")
    public String getCkycOffTelNumber() {
        return ckycOffTelNumber;
    }

    @JsonProperty("ckycOffTelNumber")
    @XmlElement(name = "CKYCOffTelNumber")
    public void setCkycOffTelNumber(String ckycOffTelNumber) {
        this.ckycOffTelNumber = ckycOffTelNumber;
    }

    @JsonProperty("ckycMobileISD")
    public String getCkycMobileISD() {
        return ckycMobileISD;
    }

    @JsonProperty("ckycMobileISD")
    @XmlElement(name = "CKYCMobileISD")
    public void setCkycMobileISD(String ckycMobileISD) {
        this.ckycMobileISD = ckycMobileISD;
    }

    @JsonProperty("ckycMobileNumber")
    public String getCkycMobileNumber() {
        return ckycMobileNumber;
    }

    @JsonProperty("ckycMobileNumber")
    @XmlElement(name = "CKYCMobileNumber")
    public void setCkycMobileNumber(String ckycMobileNumber) {
        this.ckycMobileNumber = ckycMobileNumber;
    }

    @JsonProperty("ckycEmailAdd")
    public String getCkycEmailAdd() {
        return ckycEmailAdd;
    }

    @JsonProperty("ckycEmailAdd")
    @XmlElement(name = "CKYCEmailAdd")
    public void setCkycEmailAdd(String ckycEmailAdd) {
        this.ckycEmailAdd = ckycEmailAdd;
    }

    @JsonProperty("ckycRemarks")
    public String getCkycRemarks() {
        return ckycRemarks;
    }

    @JsonProperty("ckycRemarks")
    @XmlElement(name = "CKYCRemarks")
    public void setCkycRemarks(String ckycRemarks) {
        this.ckycRemarks = ckycRemarks;
    }

    @JsonProperty("ckycDateofDeclaration")
    public String getCkycDateofDeclaration() {
        return ckycDateofDeclaration;
    }

    @JsonProperty("ckycDateofDeclaration")
    @XmlElement(name = "CKYCDateofDeclaration")
    public void setCkycDateofDeclaration(String ckycDateofDeclaration) {
        this.ckycDateofDeclaration = ckycDateofDeclaration;
    }

    @JsonProperty("ckycPlaceofDeclaration")
    public String getCkycPlaceofDeclaration() {
        return ckycPlaceofDeclaration;
    }

    @JsonProperty("ckycPlaceofDeclaration")
    @XmlElement(name = "CKYCPlaceofDeclaration")
    public void setCkycPlaceofDeclaration(String ckycPlaceofDeclaration) {
        this.ckycPlaceofDeclaration = ckycPlaceofDeclaration;
    }

    @JsonProperty("ckycKYCVerificationDate")
    public String getCkycKYCVerificationDate() {
        return ckycKYCVerificationDate;
    }

    @JsonProperty("ckycKYCVerificationDate")
    @XmlElement(name = "CKYCKYCVerificationDate")
    public void setCkycKYCVerificationDate(String ckycKYCVerificationDate) {
        this.ckycKYCVerificationDate = ckycKYCVerificationDate;
    }

    @JsonProperty("ckycTypeofDocSubmitted")
    public String getCkycTypeofDocSubmitted() {
        return ckycTypeofDocSubmitted;
    }

    @JsonProperty("ckycTypeofDocSubmitted")
    @XmlElement(name = "CKYCTypeofDocSubmitted")
    public void setCkycTypeofDocSubmitted(String ckycTypeofDocSubmitted) {
        this.ckycTypeofDocSubmitted = ckycTypeofDocSubmitted;
    }

    @JsonProperty("ckycKYCVerificationName")
    public String getCkycKYCVerificationName() {
        return ckycKYCVerificationName;
    }

    @JsonProperty("ckycKYCVerificationName")
    @XmlElement(name = "CKYCKYCVerificationName")
    public void setCkycKYCVerificationName(String ckycKYCVerificationName) {
        this.ckycKYCVerificationName = ckycKYCVerificationName;
    }

    @JsonProperty("ckycKYCVerificationDesg")
    public String getCkycKYCVerificationDesg() {
        return ckycKYCVerificationDesg;
    }

    @JsonProperty("ckycKYCVerificationDesg")
    @XmlElement(name = "CKYCKYCVerificationDesg")
    public void setCkycKYCVerificationDesg(String ckycKYCVerificationDesg) {
        this.ckycKYCVerificationDesg = ckycKYCVerificationDesg;
    }

    @JsonProperty("ckycKYCVerificationBranch")
    public String getCkycKYCVerificationBranch() {
        return ckycKYCVerificationBranch;
    }

    @JsonProperty("ckycKYCVerificationBranch")
    @XmlElement(name = "CKYCKYCVerificationBranch")
    public void setCkycKYCVerificationBranch(String ckycKYCVerificationBranch) {
        this.ckycKYCVerificationBranch = ckycKYCVerificationBranch;
    }

    @JsonProperty("ckycKYCVerificationEmpcode")
    public String getCkycKYCVerificationEmpcode() {
        return ckycKYCVerificationEmpcode;
    }

    @JsonProperty("ckycKYCVerificationEmpcode")
    @XmlElement(name = "CKYCKYCVerificationEmpcode")
    public void setCkycKYCVerificationEmpcode(String ckycKYCVerificationEmpcode) {
        this.ckycKYCVerificationEmpcode = ckycKYCVerificationEmpcode;
    }

    @JsonProperty("ckycNumberofIds")
    public String getCkycNumberofIds() {
        return ckycNumberofIds;
    }

    @JsonProperty("ckycNumberofIds")
    @XmlElement(name = "CKYCNumberofIds")
    public void setCkycNumberofIds(String ckycNumberofIds) {
        this.ckycNumberofIds = ckycNumberofIds;
    }

    @JsonProperty("ckycNumberofRelatedPersons")
    public String getCkycNumberofRelatedPersons() {
        return ckycNumberofRelatedPersons;
    }

    @JsonProperty("ckycNumberofRelatedPersons")
    @XmlElement(name = "CKYCNumberofRelatedPersons")
    public void setCkycNumberofRelatedPersons(String ckycNumberofRelatedPersons) {
        this.ckycNumberofRelatedPersons = ckycNumberofRelatedPersons;
    }

    @JsonProperty("ckycNumberofImages")
    public String getCkycNumberofImages() {
        return ckycNumberofImages;
    }

    @JsonProperty("ckycNumberofImages")
    @XmlElement(name = "CKYCNumberofImages")
    public void setCkycNumberofImages(String ckycNumberofImages) {
        this.ckycNumberofImages = ckycNumberofImages;
    }


    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycPersonalDetail{");
        sb.append("recordIdentifier='").append(recordIdentifier).append('\'');
        sb.append(", applicationFormNo='").append(applicationFormNo).append('\'');
        sb.append(", branchCode='").append(branchCode).append('\'');
        sb.append(", sourceSystem='").append(sourceSystem).append('\'');
        sb.append(", sourceSystemSegment=").append(sourceSystemSegment);
        sb.append(", remarks=").append(remarks);
        sb.append(", ckycConstiType='").append(ckycConstiType).append('\'');
        sb.append(", ckycAccType='").append(ckycAccType).append('\'');
        sb.append(", ckycNumber='").append(ckycNumber).append('\'');
        sb.append(", ckycNamePrefix='").append(ckycNamePrefix).append('\'');
        sb.append(", ckycFirstName='").append(ckycFirstName).append('\'');
        sb.append(", ckycMiddleName=").append(ckycMiddleName);
        sb.append(", ckycLastName='").append(ckycLastName).append('\'');
        sb.append(", ckycFullName='").append(ckycFullName).append('\'');
        sb.append(", ckycMaidenNamePrefix=").append(ckycMaidenNamePrefix);
        sb.append(", ckycMaidenFirstName=").append(ckycMaidenFirstName);
        sb.append(", ckycMaidenMiddleName=").append(ckycMaidenMiddleName);
        sb.append(", ckycMaidenLastName=").append(ckycMaidenLastName);
        sb.append(", ckycMaidenFullName=").append(ckycMaidenFullName);
        sb.append(", ckycFatherNamePrefix='").append(ckycFatherNamePrefix).append('\'');
        sb.append(", ckycFatherFirstName='").append(ckycFatherFirstName).append('\'');
        sb.append(", ckycFatherMiddleName=").append(ckycFatherMiddleName);
        sb.append(", ckycFatherLastName='").append(ckycFatherLastName).append('\'');
        sb.append(", ckycFatherFullName='").append(ckycFatherFullName).append('\'');
        sb.append(", ckycMotherNamePrefix='").append(ckycMotherNamePrefix).append('\'');
        sb.append(", ckycMotherFirstName='").append(ckycMotherFirstName).append('\'');
        sb.append(", ckycMotherMiddletName=").append(ckycMotherMiddletName);
        sb.append(", ckycMotherLastName='").append(ckycMotherLastName).append('\'');
        sb.append(", ckycMotherFullName='").append(ckycMotherFullName).append('\'');
        sb.append(", ckycGender='").append(ckycGender).append('\'');
        sb.append(", ckycDOB='").append(ckycDOB).append('\'');
        sb.append(", ckycPAN='").append(ckycPAN).append('\'');
        sb.append(", ckycFormSixty='").append(ckycFormSixty).append('\'');
        sb.append(", ckycPerAdd1='").append(ckycPerAdd1).append('\'');
        sb.append(", ckycPerAdd2='").append(ckycPerAdd2).append('\'');
        sb.append(", ckycPerAdd3='").append(ckycPerAdd3).append('\'');
        sb.append(", ckycPerAddCity='").append(ckycPerAddCity).append('\'');
        sb.append(", ckycPerAddDistrict='").append(ckycPerAddDistrict).append('\'');
        sb.append(", ckycPerAddState='").append(ckycPerAddState).append('\'');
        sb.append(", ckycPerAddCountry='").append(ckycPerAddCountry).append('\'');
        sb.append(", ckycPerAddPin='").append(ckycPerAddPin).append('\'');
        sb.append(", ckycPerAddPOA='").append(ckycPerAddPOA).append('\'');
        sb.append(", ckycPerAddSameasCorAdd='").append(ckycPerAddSameasCorAdd).append('\'');
        sb.append(", ckycCorAdd1='").append(ckycCorAdd1).append('\'');
        sb.append(", ckycCorAdd2='").append(ckycCorAdd2).append('\'');
        sb.append(", ckycCorAdd3='").append(ckycCorAdd3).append('\'');
        sb.append(", ckycCorAddCity='").append(ckycCorAddCity).append('\'');
        sb.append(", ckycCorAddDistrict='").append(ckycCorAddDistrict).append('\'');
        sb.append(", ckycCorAddState='").append(ckycCorAddState).append('\'');
        sb.append(", ckycCorAddCountry='").append(ckycCorAddCountry).append('\'');
        sb.append(", ckycCorAddPin='").append(ckycCorAddPin).append('\'');
        sb.append(", ckycCorAddPOA='").append(ckycCorAddPOA).append('\'');
        sb.append(", ckycResTelSTD=").append(ckycResTelSTD);
        sb.append(", ckycResTelNumber=").append(ckycResTelNumber);
        sb.append(", ckycOffTelSTD=").append(ckycOffTelSTD);
        sb.append(", ckycOffTelNumber=").append(ckycOffTelNumber);
        sb.append(", ckycMobileISD=").append(ckycMobileISD);
        sb.append(", ckycMobileNumber=").append(ckycMobileNumber);
        sb.append(", ckycEmailAdd=").append(ckycEmailAdd);
        sb.append(", ckycRemarks=").append(ckycRemarks);
        sb.append(", ckycDateofDeclaration='").append(ckycDateofDeclaration).append('\'');
        sb.append(", ckycPlaceofDeclaration='").append(ckycPlaceofDeclaration).append('\'');
        sb.append(", ckycKYCVerificationDate='").append(ckycKYCVerificationDate).append('\'');
        sb.append(", ckycTypeofDocSubmitted='").append(ckycTypeofDocSubmitted).append('\'');
        sb.append(", ckycKYCVerificationName='").append(ckycKYCVerificationName).append('\'');
        sb.append(", ckycKYCVerificationDesg='").append(ckycKYCVerificationDesg).append('\'');
        sb.append(", ckycKYCVerificationBranch='").append(ckycKYCVerificationBranch).append('\'');
        sb.append(", ckycKYCVerificationEmpcode='").append(ckycKYCVerificationEmpcode).append('\'');
        sb.append(", ckycNumberofIds='").append(ckycNumberofIds).append('\'');
        sb.append(", ckycNumberofRelatedPersons='").append(ckycNumberofRelatedPersons).append('\'');
        sb.append(", ckycNumberofImages='").append(ckycNumberofImages).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
