
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CKYCRelatedPerson")
public class CkycRelatedPerson
{

    @JsonProperty("ckycRpSequence")
    private String ckycRpSequence;
    @JsonProperty("ckycRpRelation")
    private String ckycRpRelation;
    @Sensitive(MaskType.CKYC_NUM)
    @JsonProperty("ckycRpCKYCNumber")
    private String ckycRpCKYCNumber;
    @JsonProperty("ckycRpNamePrefix")
    private String ckycRpNamePrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycRpFirstName")
    private String ckycRpFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycRpMiddleName")
    private String ckycRpMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycRpLastName")
    private String ckycRpLastName;
    @JsonProperty("ckycRpMaidenPrefix")
    private String ckycRpMaidenPrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycRpMaidenFirstName")
    private String ckycRpMaidenFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycRpMaidenMiddleName")
    private String ckycRpMaidenMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycRpMaidenLastName")
    private String ckycRpMaidenLastName;
    @JsonProperty("ckycRpFatherOrSpouseFlag")
    private String ckycRpFatherOrSpouseFlag;
    @JsonProperty("ckycRpFatherPrefix")
    private String ckycRpFatherPrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycRpFatherFirstName")
    private String ckycRpFatherFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycRpFatherMiddleName")
    private String ckycRpFatherMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycRpFatherLastName")
    private String ckycRpFatherLastName;
    @JsonProperty("ckycRpMotherNamePrefix")
    private String ckycRpMotherNamePrefix;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("ckycRpMotherFirstName")
    private String ckycRpMotherFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("ckycRpMotherMiddleName")
    private String ckycRpMotherMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("ckycRpMotherLastName")
    private String ckycRpMotherLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("ckycRpDateOfBirth")
    private String ckycRpDateOfBirth;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("ckycRpGender")
    private String ckycRpGender;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpPerAdd1")
    private List<String> ckycRpPerAdd1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpPerAdd3")
    private String ckycRpPerAdd3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpPerAddCity")
    private String ckycRpPerAddCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpPerAddDistrict")
    private String ckycRpPerAddDistrict;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpPerAddState")
    private String ckycRpPerAddState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpPerAddCountry")
    private String ckycRpPerAddCountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("ckycRpPerAddPIN")
    private String ckycRpPerAddPIN;
    @JsonProperty("ckycRpPerAddPOA")
    private String ckycRpPerAddPOA;
    @JsonProperty("ckycRpPerAddSameasCorAdd")
    private String ckycRpPerAddSameasCorAdd;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAdd1")
    private String ckycRpCorAdd1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAdd2")
    private String ckycRpCorAdd2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAdd3")
    private String ckycRpCorAdd3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAddCity")
    private String ckycRpCorAddCity;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAddDistrict")
    private String ckycRpCorAddDistrict;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAddState")
    private String ckycRpCorAddState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("ckycRpCorAddCountry")
    private String ckycRpCorAddCountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("ckycRpCorAddPIN")
    private String ckycRpCorAddPIN;
    @JsonProperty("ckycRpCorAddPOA")
    private String ckycRpCorAddPOA;
    @JsonProperty("ckycRpResSTDCode")
    private String ckycRpResSTDCode;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("ckycRpResTelNum")
    private String ckycRpResTelNum;
    @JsonProperty("ckycRpOffSTDCode")
    private String ckycRpOffSTDCode;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("ckycRpOffTelNum")
    private String ckycRpOffTelNum;
    @JsonProperty("ckycRpMobCode")
    private String ckycRpMobCode;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("ckycRpMobNum")
    private String ckycRpMobNum;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("ckycRpEmail")
    private String ckycRpEmail;
    @JsonProperty("ckycRpRemarks")
    private String ckycRpRemarks;
    @JsonProperty("ckycRpPhotoType")
    private String ckycRpPhotoType;
    @JsonProperty("ckycRpPhotoData")
    private String ckycRpPhotoData;
    @JsonProperty("ckycRpPerPOAType")
    private String ckycRpPerPOAType;
    @JsonProperty("ckycRpPerPOAData")
    private String ckycRpPerPOAData;
    @JsonProperty("ckycRpCorPOAType")
    private String ckycRpCorPOAType;
    @JsonProperty("ckycRpCorPOAData")
    private String ckycRpCorPOAData;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("ckycRpPAN")
    private String ckycRpPAN;
    @JsonProperty("ckycRpFormSixty")
    private String ckycRpFormSixty;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("ckycRpUID")
    private String ckycRpUID;
    @JsonProperty("ckycRpVoterId")
    private String ckycRpVoterId;
    @JsonProperty("ckycRpNREGA")
    private String ckycRpNREGA;
    @Sensitive(MaskType.PASSPORT)
    @JsonProperty("ckycRpPassportNumber")
    private String ckycRpPassportNumber;
    @JsonProperty("ckycRpDrivingLicenceNumber")
    private String ckycRpDrivingLicenceNumber;
    @JsonProperty("ckycRpNPRLetter")
    private String ckycRpNPRLetter;
    @JsonProperty("ckycRpOfflineVerficationAadhaar")
    private String ckycRpOfflineVerficationAadhaar;
    @JsonProperty("ckycRpeKYCAuthentication")
    private String ckycRpeKYCAuthentication;
    @JsonProperty("ckycRpDateofDeclaration")
    private String ckycRpDateofDeclaration;
    @JsonProperty("ckycRpPlaceofDeclaration")
    private String ckycRpPlaceofDeclaration;
    @JsonProperty("ckycRpKYCVerificationDate")
    private String ckycRpKYCVerificationDate;
    @JsonProperty("ckycRpTypeofDocSubmitted")
    private String ckycRpTypeofDocSubmitted;
    @Sensitive(MaskType.NAME)
    @JsonProperty("ckycRpKYCVerificationName")
    private String ckycRpKYCVerificationName;
    @JsonProperty("ckycRpKYCVerificationDesg")
    private String ckycRpKYCVerificationDesg;
    @JsonProperty("ckycRpKYCVerificationBranch")
    private String ckycRpKYCVerificationBranch;
    @JsonProperty("ckycRpKYCVerificationEmpcode")
    private String ckycRpKYCVerificationEmpcode;

    private final static long serialVersionUID = -6265638914210222807L;

    @JsonProperty("ckycRpSequence")
    public String getCkycRpSequence() {
        return ckycRpSequence;
    }

    @JsonProperty("ckycRpSequence")
    @XmlElement(name = "CKYCRPSequence")
    public void setCkycRpSequence(String ckycRpSequence) {
        this.ckycRpSequence = ckycRpSequence;
    }

    @JsonProperty("ckycRpRelation")
    public String getCkycRpRelation() {
        return ckycRpRelation;
    }

    @JsonProperty("ckycRpRelation")
    @XmlElement(name = "CKYCRPRelation")
    public void setCkycRpRelation(String ckycRpRelation) {
        this.ckycRpRelation = ckycRpRelation;
    }

    @JsonProperty("ckycRpCKYCNumber")
    public String getCkycRpCKYCNumber() {
        return ckycRpCKYCNumber;
    }

    @JsonProperty("ckycRpCKYCNumber")
    @XmlElement(name = "CKYCRPCKYCNumber")
    public void setCkycRpCKYCNumber(String ckycRpCKYCNumber) {
        this.ckycRpCKYCNumber = ckycRpCKYCNumber;
    }

    @JsonProperty("ckycRpNamePrefix")
    public String getCkycRpNamePrefix() {
        return ckycRpNamePrefix;
    }

    @JsonProperty("ckycRpNamePrefix")
    @XmlElement(name = "CKYCRPNamePrefix")
    public void setCkycRpNamePrefix(String ckycRpNamePrefix) {
        this.ckycRpNamePrefix = ckycRpNamePrefix;
    }

    @JsonProperty("ckycRpFirstName")
    public String getCkycRpFirstName() {
        return ckycRpFirstName;
    }

    @JsonProperty("ckycRpFirstName")
    @XmlElement(name = "CKYCRPFirstName")
    public void setCkycRpFirstName(String ckycRpFirstName) {
        this.ckycRpFirstName = ckycRpFirstName;
    }

    @JsonProperty("ckycRpMiddleName")
    public String getCkycRpMiddleName() {
        return ckycRpMiddleName;
    }

    @JsonProperty("ckycRpMiddleName")
    @XmlElement(name = "CKYCRPMiddleName")
    public void setCkycRpMiddleName(String ckycRpMiddleName) {
        this.ckycRpMiddleName = ckycRpMiddleName;
    }

    @JsonProperty("ckycRpLastName")
    public String getCkycRpLastName() {
        return ckycRpLastName;
    }

    @JsonProperty("ckycRpLastName")
    @XmlElement(name = "CKYCRPLastName")
    public void setCkycRpLastName(String ckycRpLastName) {
        this.ckycRpLastName = ckycRpLastName;
    }

    @JsonProperty("ckycRpMaidenPrefix")
    public String getCkycRpMaidenPrefix() {
        return ckycRpMaidenPrefix;
    }

    @JsonProperty("ckycRpMaidenPrefix")
    @XmlElement(name = "CKYCRPMaidenPrefix")
    public void setCkycRpMaidenPrefix(String ckycRpMaidenPrefix) {
        this.ckycRpMaidenPrefix = ckycRpMaidenPrefix;
    }

    @JsonProperty("ckycRpMaidenFirstName")
    public String getCkycRpMaidenFirstName() {
        return ckycRpMaidenFirstName;
    }

    @JsonProperty("ckycRpMaidenFirstName")
    @XmlElement(name = "CKYCRPMaidenFirstName")
    public void setCkycRpMaidenFirstName(String ckycRpMaidenFirstName) {
        this.ckycRpMaidenFirstName = ckycRpMaidenFirstName;
    }

    @JsonProperty("ckycRpMaidenMiddleName")
    public String getCkycRpMaidenMiddleName() {
        return ckycRpMaidenMiddleName;
    }

    @JsonProperty("ckycRpMaidenMiddleName")
    @XmlElement(name = "CKYCRPMaidenMiddleName")
    public void setCkycRpMaidenMiddleName(String ckycRpMaidenMiddleName) {
        this.ckycRpMaidenMiddleName = ckycRpMaidenMiddleName;
    }

    @JsonProperty("ckycRpMaidenLastName")
    public String getCkycRpMaidenLastName() {
        return ckycRpMaidenLastName;
    }

    @JsonProperty("ckycRpMaidenLastName")
    @XmlElement(name = "CKYCRPMaidenLastName")
    public void setCkycRpMaidenLastName(String ckycRpMaidenLastName) {
        this.ckycRpMaidenLastName = ckycRpMaidenLastName;
    }

    @JsonProperty("ckycRpFatherOrSpouseFlag")
    public String getCkycRpFatherOrSpouseFlag() {
        return ckycRpFatherOrSpouseFlag;
    }

    @JsonProperty("ckycRpFatherOrSpouseFlag")
    @XmlElement(name = "CKYCRPFatherOrSpouseFlag")
    public void setCkycRpFatherOrSpouseFlag(String ckycRpFatherOrSpouseFlag) {
        this.ckycRpFatherOrSpouseFlag = ckycRpFatherOrSpouseFlag;
    }

    @JsonProperty("ckycRpFatherPrefix")
    public String getCkycRpFatherPrefix() {
        return ckycRpFatherPrefix;
    }

    @JsonProperty("ckycRpFatherPrefix")
    @XmlElement(name = "CKYCRPFatherPrefix")
    public void setCkycRpFatherPrefix(String ckycRpFatherPrefix) {
        this.ckycRpFatherPrefix = ckycRpFatherPrefix;
    }

    @JsonProperty("ckycRpFatherFirstName")
    public String getCkycRpFatherFirstName() {
        return ckycRpFatherFirstName;
    }

    @JsonProperty("ckycRpFatherFirstName")
    @XmlElement(name = "CKYCRPFatherFirstName")
    public void setCkycRpFatherFirstName(String ckycRpFatherFirstName) {
        this.ckycRpFatherFirstName = ckycRpFatherFirstName;
    }

    @JsonProperty("ckycRpFatherMiddleName")
    public String getCkycRpFatherMiddleName() {
        return ckycRpFatherMiddleName;
    }

    @JsonProperty("ckycRpFatherMiddleName")
    @XmlElement(name = "CKYCRPFatherMiddleName")
    public void setCkycRpFatherMiddleName(String ckycRpFatherMiddleName) {
        this.ckycRpFatherMiddleName = ckycRpFatherMiddleName;
    }

    @JsonProperty("ckycRpFatherLastName")
    public String getCkycRpFatherLastName() {
        return ckycRpFatherLastName;
    }

    @JsonProperty("ckycRpFatherLastName")
    @XmlElement(name = "CKYCRPFatherLastName")
    public void setCkycRpFatherLastName(String ckycRpFatherLastName) {
        this.ckycRpFatherLastName = ckycRpFatherLastName;
    }

    @JsonProperty("ckycRpMotherNamePrefix")
    public String getCkycRpMotherNamePrefix() {
        return ckycRpMotherNamePrefix;
    }

    @JsonProperty("ckycRpMotherNamePrefix")
    @XmlElement(name = "CKYCRPMotherNamePrefix")
    public void setCkycRpMotherNamePrefix(String ckycRpMotherNamePrefix) {
        this.ckycRpMotherNamePrefix = ckycRpMotherNamePrefix;
    }

    @JsonProperty("ckycRpMotherFirstName")
    public String getCkycRpMotherFirstName() {
        return ckycRpMotherFirstName;
    }

    @JsonProperty("ckycRpMotherFirstName")
    @XmlElement(name = "CKYCRPMotherFirstName")
    public void setCkycRpMotherFirstName(String ckycRpMotherFirstName) {
        this.ckycRpMotherFirstName = ckycRpMotherFirstName;
    }

    @JsonProperty("ckycRpMotherMiddleName")
    public String getCkycRpMotherMiddleName() {
        return ckycRpMotherMiddleName;
    }

    @JsonProperty("ckycRpMotherMiddleName")
    @XmlElement(name = "CKYCRPMotherMiddleName")
    public void setCkycRpMotherMiddleName(String ckycRpMotherMiddleName) {
        this.ckycRpMotherMiddleName = ckycRpMotherMiddleName;
    }

    @JsonProperty("ckycRpMotherLastName")
    public String getCkycRpMotherLastName() {
        return ckycRpMotherLastName;
    }

    @JsonProperty("ckycRpMotherLastName")
    @XmlElement(name = "CKYCRPMotherLastName")
    public void setCkycRpMotherLastName(String ckycRpMotherLastName) {
        this.ckycRpMotherLastName = ckycRpMotherLastName;
    }

    @JsonProperty("ckycRpDateOfBirth")
    public String getCkycRpDateOfBirth() {
        return ckycRpDateOfBirth;
    }

    @JsonProperty("ckycRpDateOfBirth")
    @XmlElement(name = "CKYCRPDateOfBirth")
    public void setCkycRpDateOfBirth(String ckycRpDateOfBirth) {
        this.ckycRpDateOfBirth = ckycRpDateOfBirth;
    }

    @JsonProperty("ckycRpGender")
    public String getCkycRpGender() {
        return ckycRpGender;
    }

    @JsonProperty("ckycRpGender")
    @XmlElement(name = "CKYCRPGender")
    public void setCkycRpGender(String ckycRpGender) {
        this.ckycRpGender = ckycRpGender;
    }

    @JsonProperty("ckycRpPerAdd1")
    public List<String> getCkycRpPerAdd1() {
        return ckycRpPerAdd1;
    }

    @JsonProperty("ckycRpPerAdd1")
    @XmlElement(name = "CKYCRPPerAdd1")
    public void setCkycRpPerAdd1(List<String> ckycRpPerAdd1) {
        this.ckycRpPerAdd1 = ckycRpPerAdd1;
    }

    @JsonProperty("ckycRpPerAdd3")
    public String getCkycRpPerAdd3() {
        return ckycRpPerAdd3;
    }

    @JsonProperty("ckycRpPerAdd3")
    @XmlElement(name = "CKYCRPPerAdd3")
    public void setCkycRpPerAdd3(String ckycRpPerAdd3) {
        this.ckycRpPerAdd3 = ckycRpPerAdd3;
    }

    @JsonProperty("ckycRpPerAddCity")
    public String getCkycRpPerAddCity() {
        return ckycRpPerAddCity;
    }

    @JsonProperty("ckycRpPerAddCity")
    @XmlElement(name = "CKYCRPPerAddCity")
    public void setCkycRpPerAddCity(String ckycRpPerAddCity) {
        this.ckycRpPerAddCity = ckycRpPerAddCity;
    }

    @JsonProperty("ckycRpPerAddDistrict")
    public String getCkycRpPerAddDistrict() {
        return ckycRpPerAddDistrict;
    }

    @JsonProperty("ckycRpPerAddDistrict")
    @XmlElement(name = "CKYCRPPerAddDistrict")
    public void setCkycRpPerAddDistrict(String ckycRpPerAddDistrict) {
        this.ckycRpPerAddDistrict = ckycRpPerAddDistrict;
    }

    @JsonProperty("ckycRpPerAddState")
    public String getCkycRpPerAddState() {
        return ckycRpPerAddState;
    }

    @JsonProperty("ckycRpPerAddState")
    @XmlElement(name = "CKYCRPPerAddState")
    public void setCkycRpPerAddState(String ckycRpPerAddState) {
        this.ckycRpPerAddState = ckycRpPerAddState;
    }

    @JsonProperty("ckycRpPerAddCountry")
    public String getCkycRpPerAddCountry() {
        return ckycRpPerAddCountry;
    }

    @JsonProperty("ckycRpPerAddCountry")
    @XmlElement(name = "CKYCRPPerAddCountry")
    public void setCkycRpPerAddCountry(String ckycRpPerAddCountry) {
        this.ckycRpPerAddCountry = ckycRpPerAddCountry;
    }

    @JsonProperty("ckycRpPerAddPIN")
    public String getCkycRpPerAddPIN() {
        return ckycRpPerAddPIN;
    }

    @JsonProperty("ckycRpPerAddPIN")
    @XmlElement(name = "CKYCRPPerAddPIN")
    public void setCkycRpPerAddPIN(String ckycRpPerAddPIN) {
        this.ckycRpPerAddPIN = ckycRpPerAddPIN;
    }

    @JsonProperty("ckycRpPerAddPOA")
    public String getCkycRpPerAddPOA() {
        return ckycRpPerAddPOA;
    }

    @JsonProperty("ckycRpPerAddPOA")
    @XmlElement(name = "CKYCRPPerAddPOA")
    public void setCkycRpPerAddPOA(String ckycRpPerAddPOA) {
        this.ckycRpPerAddPOA = ckycRpPerAddPOA;
    }

    @JsonProperty("ckycRpPerAddSameasCorAdd")
    public String getCkycRpPerAddSameasCorAdd() {
        return ckycRpPerAddSameasCorAdd;
    }

    @JsonProperty("ckycRpPerAddSameasCorAdd")
    @XmlElement(name = "CKYCRPPerAddSameasCorAdd")
    public void setCkycRpPerAddSameasCorAdd(String ckycRpPerAddSameasCorAdd) {
        this.ckycRpPerAddSameasCorAdd = ckycRpPerAddSameasCorAdd;
    }

    @JsonProperty("ckycRpCorAdd1")
    public String getCkycRpCorAdd1() {
        return ckycRpCorAdd1;
    }

    @JsonProperty("ckycRpCorAdd1")
    @XmlElement(name = "CKYCRPCorAdd1")
    public void setCkycRpCorAdd1(String ckycRpCorAdd1) {
        this.ckycRpCorAdd1 = ckycRpCorAdd1;
    }

    @JsonProperty("ckycRpCorAdd2")
    public String getCkycRpCorAdd2() {
        return ckycRpCorAdd2;
    }

    @JsonProperty("ckycRpCorAdd2")
    @XmlElement(name = "CKYCRPCorAdd2")
    public void setCkycRpCorAdd2(String ckycRpCorAdd2) {
        this.ckycRpCorAdd2 = ckycRpCorAdd2;
    }

    @JsonProperty("ckycRpCorAdd3")
    public String getCkycRpCorAdd3() {
        return ckycRpCorAdd3;
    }

    @JsonProperty("ckycRpCorAdd3")
    @XmlElement(name = "CKYCRPCorAdd3")
    public void setCkycRpCorAdd3(String ckycRpCorAdd3) {
        this.ckycRpCorAdd3 = ckycRpCorAdd3;
    }

    @JsonProperty("ckycRpCorAddCity")
    public String getCkycRpCorAddCity() {
        return ckycRpCorAddCity;
    }

    @JsonProperty("ckycRpCorAddCity")
    @XmlElement(name = "CKYCRPCorAddCity")
    public void setCkycRpCorAddCity(String ckycRpCorAddCity) {
        this.ckycRpCorAddCity = ckycRpCorAddCity;
    }

    @JsonProperty("ckycRpCorAddDistrict")
    public String getCkycRpCorAddDistrict() {
        return ckycRpCorAddDistrict;
    }

    @JsonProperty("ckycRpCorAddDistrict")
    @XmlElement(name = "CKYCRPCorAddDistrict")
    public void setCkycRpCorAddDistrict(String ckycRpCorAddDistrict) {
        this.ckycRpCorAddDistrict = ckycRpCorAddDistrict;
    }

    @JsonProperty("ckycRpCorAddState")
    public String getCkycRpCorAddState() {
        return ckycRpCorAddState;
    }

    @JsonProperty("ckycRpCorAddState")
    @XmlElement(name = "CKYCRPCorAddState")
    public void setCkycRpCorAddState(String ckycRpCorAddState) {
        this.ckycRpCorAddState = ckycRpCorAddState;
    }

    @JsonProperty("ckycRpCorAddCountry")
    public String getCkycRpCorAddCountry() {
        return ckycRpCorAddCountry;
    }

    @JsonProperty("ckycRpCorAddCountry")
    @XmlElement(name = "CKYCRPCorAddCountry")
    public void setCkycRpCorAddCountry(String ckycRpCorAddCountry) {
        this.ckycRpCorAddCountry = ckycRpCorAddCountry;
    }

    @JsonProperty("ckycRpCorAddPIN")
    public String getCkycRpCorAddPIN() {
        return ckycRpCorAddPIN;
    }

    @JsonProperty("ckycRpCorAddPIN")
    @XmlElement(name = "CKYCRPCorAddPIN")
    public void setCkycRpCorAddPIN(String ckycRpCorAddPIN) {
        this.ckycRpCorAddPIN = ckycRpCorAddPIN;
    }

    @JsonProperty("ckycRpCorAddPOA")
    public String getCkycRpCorAddPOA() {
        return ckycRpCorAddPOA;
    }

    @JsonProperty("ckycRpCorAddPOA")
    @XmlElement(name = "CKYCRPCorAddPOA")
    public void setCkycRpCorAddPOA(String ckycRpCorAddPOA) {
        this.ckycRpCorAddPOA = ckycRpCorAddPOA;
    }

    @JsonProperty("ckycRpResSTDCode")
    public String getCkycRpResSTDCode() {
        return ckycRpResSTDCode;
    }

    @JsonProperty("ckycRpResSTDCode")
    @XmlElement(name = "CKYCRPResSTDCode")
    public void setCkycRpResSTDCode(String ckycRpResSTDCode) {
        this.ckycRpResSTDCode = ckycRpResSTDCode;
    }

    @JsonProperty("ckycRpResTelNum")
    public String getCkycRpResTelNum() {
        return ckycRpResTelNum;
    }

    @JsonProperty("ckycRpResTelNum")
    @XmlElement(name = "CKYCRPResTelNum")
    public void setCkycRpResTelNum(String ckycRpResTelNum) {
        this.ckycRpResTelNum = ckycRpResTelNum;
    }

    @JsonProperty("ckycRpOffSTDCode")
    public String getCkycRpOffSTDCode() {
        return ckycRpOffSTDCode;
    }

    @JsonProperty("ckycRpOffSTDCode")
    @XmlElement(name = "CKYCRPOffSTDCode")
    public void setCkycRpOffSTDCode(String ckycRpOffSTDCode) {
        this.ckycRpOffSTDCode = ckycRpOffSTDCode;
    }

    @JsonProperty("ckycRpOffTelNum")
    public String getCkycRpOffTelNum() {
        return ckycRpOffTelNum;
    }

    @JsonProperty("ckycRpOffTelNum")
    @XmlElement(name = "CKYCRPOffTelNum")
    public void setCkycRpOffTelNum(String ckycRpOffTelNum) {
        this.ckycRpOffTelNum = ckycRpOffTelNum;
    }

    @JsonProperty("ckycRpMobCode")
    public String getCkycRpMobCode() {
        return ckycRpMobCode;
    }

    @JsonProperty("ckycRpMobCode")
    @XmlElement(name = "CKYCRPMobCode")
    public void setCkycRpMobCode(String ckycRpMobCode) {
        this.ckycRpMobCode = ckycRpMobCode;
    }

    @JsonProperty("ckycRpMobNum")
    public String getCkycRpMobNum() {
        return ckycRpMobNum;
    }

    @JsonProperty("ckycRpMobNum")
    @XmlElement(name = "CKYCRPMobNum")
    public void setCkycRpMobNum(String ckycRpMobNum) {
        this.ckycRpMobNum = ckycRpMobNum;
    }

    @JsonProperty("ckycRpEmail")
    public String getCkycRpEmail() {
        return ckycRpEmail;
    }

    @JsonProperty("ckycRpEmail")
    @XmlElement(name = "CKYCRPEmail")
    public void setCkycRpEmail(String ckycRpEmail) {
        this.ckycRpEmail = ckycRpEmail;
    }

    @JsonProperty("ckycRpRemarks")
    public String getCkycRpRemarks() {
        return ckycRpRemarks;
    }

    @JsonProperty("ckycRpRemarks")
    @XmlElement(name = "CKYCRPRemarks")
    public void setCkycRpRemarks(String ckycRpRemarks) {
        this.ckycRpRemarks = ckycRpRemarks;
    }

    @JsonProperty("ckycRpPhotoType")
    public String getCkycRpPhotoType() {
        return ckycRpPhotoType;
    }

    @JsonProperty("ckycRpPhotoType")
    @XmlElement(name = "CKYCRPPhotoType")
    public void setCkycRpPhotoType(String ckycRpPhotoType) {
        this.ckycRpPhotoType = ckycRpPhotoType;
    }

    @JsonProperty("ckycRpPhotoData")
    public String getCkycRpPhotoData() {
        return ckycRpPhotoData;
    }

    @JsonProperty("ckycRpPhotoData")
    @XmlElement(name = "CKYCRPPhotoData")
    public void setCkycRpPhotoData(String ckycRpPhotoData) {
        this.ckycRpPhotoData = ckycRpPhotoData;
    }

    @JsonProperty("ckycRpPerPOAType")
    public String getCkycRpPerPOAType() {
        return ckycRpPerPOAType;
    }

    @JsonProperty("ckycRpPerPOAType")
    @XmlElement(name = "CKYCRPPerPOAType")
    public void setCkycRpPerPOAType(String ckycRpPerPOAType) {
        this.ckycRpPerPOAType = ckycRpPerPOAType;
    }

    @JsonProperty("ckycRpPerPOAData")
    public String getCkycRpPerPOAData() {
        return ckycRpPerPOAData;
    }

    @JsonProperty("ckycRpPerPOAData")
    @XmlElement(name = "CKYCRPPerPOAData")
    public void setCkycRpPerPOAData(String ckycRpPerPOAData) {
        this.ckycRpPerPOAData = ckycRpPerPOAData;
    }

    @JsonProperty("ckycRpCorPOAType")
    public String getCkycRpCorPOAType() {
        return ckycRpCorPOAType;
    }

    @JsonProperty("ckycRpCorPOAType")
    @XmlElement(name = "CKYCRPCorPOAType")
    public void setCkycRpCorPOAType(String ckycRpCorPOAType) {
        this.ckycRpCorPOAType = ckycRpCorPOAType;
    }

    @JsonProperty("ckycRpCorPOAData")
    public String getCkycRpCorPOAData() {
        return ckycRpCorPOAData;
    }

    @JsonProperty("ckycRpCorPOAData")
    @XmlElement(name = "CKYCRPCorPOAData")
    public void setCkycRpCorPOAData(String ckycRpCorPOAData) {
        this.ckycRpCorPOAData = ckycRpCorPOAData;
    }

    @JsonProperty("ckycRpPAN")
    public String getCkycRpPAN() {
        return ckycRpPAN;
    }

    @JsonProperty("ckycRpPAN")
    @XmlElement(name = "CKYCRPPAN")
    public void setCkycRpPAN(String ckycRpPAN) {
        this.ckycRpPAN = ckycRpPAN;
    }

    @JsonProperty("ckycRpFormSixty")
    public String getCkycRpFormSixty() {
        return ckycRpFormSixty;
    }

    @JsonProperty("ckycRpFormSixty")
    @XmlElement(name = "CKYCRPFormSixty")
    public void setCkycRpFormSixty(String ckycRpFormSixty) {
        this.ckycRpFormSixty = ckycRpFormSixty;
    }

    @JsonProperty("ckycRpUID")
    public String getCkycRpUID() {
        return ckycRpUID;
    }

    @JsonProperty("ckycRpUID")
    @XmlElement(name = "CKYCRPUID")
    public void setCkycRpUID(String ckycRpUID) {
        this.ckycRpUID = ckycRpUID;
    }

    @JsonProperty("ckycRpVoterId")
    public String getCkycRpVoterId() {
        return ckycRpVoterId;
    }

    @JsonProperty("ckycRpVoterId")
    @XmlElement(name = "CKYCRPVoterId")
    public void setCkycRpVoterId(String ckycRpVoterId) {
        this.ckycRpVoterId = ckycRpVoterId;
    }

    @JsonProperty("ckycRpNREGA")
    public String getCkycRpNREGA() {
        return ckycRpNREGA;
    }

    @JsonProperty("ckycRpNREGA")
    @XmlElement(name = "CKYCRPNREGA")
    public void setCkycRpNREGA(String ckycRpNREGA) {
        this.ckycRpNREGA = ckycRpNREGA;
    }

    @JsonProperty("ckycRpPassportNumber")
    public String getCkycRpPassportNumber() {
        return ckycRpPassportNumber;
    }

    @JsonProperty("ckycRpPassportNumber")
    @XmlElement(name = "CKYCRPPassportNumber")
    public void setCkycRpPassportNumber(String ckycRpPassportNumber) {
        this.ckycRpPassportNumber = ckycRpPassportNumber;
    }

    @JsonProperty("ckycRpDrivingLicenceNumber")
    public String getCkycRpDrivingLicenceNumber() {
        return ckycRpDrivingLicenceNumber;
    }

    @JsonProperty("ckycRpDrivingLicenceNumber")
    @XmlElement(name = "CKYCRPDrivingLicenceNumber")
    public void setCkycRpDrivingLicenceNumber(String ckycRpDrivingLicenceNumber) {
        this.ckycRpDrivingLicenceNumber = ckycRpDrivingLicenceNumber;
    }

    @JsonProperty("ckycRpNPRLetter")
    public String getCkycRpNPRLetter() {
        return ckycRpNPRLetter;
    }

    @JsonProperty("ckycRpNPRLetter")
    @XmlElement(name = "CKYCRPNPRLetter")
    public void setCkycRpNPRLetter(String ckycRpNPRLetter) {
        this.ckycRpNPRLetter = ckycRpNPRLetter;
    }

    @JsonProperty("ckycRpOfflineVerficationAadhaar")
    public String getCkycRpOfflineVerficationAadhaar() {
        return ckycRpOfflineVerficationAadhaar;
    }

    @JsonProperty("ckycRpOfflineVerficationAadhaar")
    @XmlElement(name = "CKYCRPOfflineVerficationAadhaar")
    public void setCkycRpOfflineVerficationAadhaar(String ckycRpOfflineVerficationAadhaar) {
        this.ckycRpOfflineVerficationAadhaar = ckycRpOfflineVerficationAadhaar;
    }

    @JsonProperty("ckycRpeKYCAuthentication")
    public String getCkycRpeKYCAuthentication() {
        return ckycRpeKYCAuthentication;
    }

    @JsonProperty("ckycRpeKYCAuthentication")
    @XmlElement(name = "CKYCRPeKYCAuthentication")
    public void setCkycRpeKYCAuthentication(String ckycRpeKYCAuthentication) {
        this.ckycRpeKYCAuthentication = ckycRpeKYCAuthentication;
    }

    @JsonProperty("ckycRpDateofDeclaration")
    public String getCkycRpDateofDeclaration() {
        return ckycRpDateofDeclaration;
    }

    @JsonProperty("ckycRpDateofDeclaration")
    @XmlElement(name = "CKYCRPDateofDeclaration")
    public void setCkycRpDateofDeclaration(String ckycRpDateofDeclaration) {
        this.ckycRpDateofDeclaration = ckycRpDateofDeclaration;
    }

    @JsonProperty("ckycRpPlaceofDeclaration")
    public String getCkycRpPlaceofDeclaration() {
        return ckycRpPlaceofDeclaration;
    }

    @JsonProperty("ckycRpPlaceofDeclaration")
    @XmlElement(name = "CKYCRPPlaceofDeclaration")
    public void setCkycRpPlaceofDeclaration(String ckycRpPlaceofDeclaration) {
        this.ckycRpPlaceofDeclaration = ckycRpPlaceofDeclaration;
    }

    @JsonProperty("ckycRpKYCVerificationDate")
    public String getCkycRpKYCVerificationDate() {
        return ckycRpKYCVerificationDate;
    }

    @JsonProperty("ckycRpKYCVerificationDate")
    @XmlElement(name = "CKYCRPKYCVerificationDate")
    public void setCkycRpKYCVerificationDate(String ckycRpKYCVerificationDate) {
        this.ckycRpKYCVerificationDate = ckycRpKYCVerificationDate;
    }

    @JsonProperty("ckycRpTypeofDocSubmitted")
    public String getCkycRpTypeofDocSubmitted() {
        return ckycRpTypeofDocSubmitted;
    }

    @JsonProperty("ckycRpTypeofDocSubmitted")
    @XmlElement(name = "CKYCRPTypeofDocSubmitted")
    public void setCkycRpTypeofDocSubmitted(String ckycRpTypeofDocSubmitted) {
        this.ckycRpTypeofDocSubmitted = ckycRpTypeofDocSubmitted;
    }

    @JsonProperty("ckycRpKYCVerificationName")
    public String getCkycRpKYCVerificationName() {
        return ckycRpKYCVerificationName;
    }

    @JsonProperty("ckycRpKYCVerificationName")
    @XmlElement(name = "CKYCRPKYCVerificationName")
    public void setCkycRpKYCVerificationName(String ckycRpKYCVerificationName) {
        this.ckycRpKYCVerificationName = ckycRpKYCVerificationName;
    }

    @JsonProperty("ckycRpKYCVerificationDesg")
    public String getCkycRpKYCVerificationDesg() {
        return ckycRpKYCVerificationDesg;
    }

    @JsonProperty("ckycRpKYCVerificationDesg")
    @XmlElement(name = "CKYCRPKYCVerificationDesg")
    public void setCkycRpKYCVerificationDesg(String ckycRpKYCVerificationDesg) {
        this.ckycRpKYCVerificationDesg = ckycRpKYCVerificationDesg;
    }

    @JsonProperty("ckycRpKYCVerificationBranch")
    public String getCkycRpKYCVerificationBranch() {
        return ckycRpKYCVerificationBranch;
    }

    @JsonProperty("ckycRpKYCVerificationBranch")
    @XmlElement(name = "CKYCRPKYCVerificationBranch")
    public void setCkycRpKYCVerificationBranch(String ckycRpKYCVerificationBranch) {
        this.ckycRpKYCVerificationBranch = ckycRpKYCVerificationBranch;
    }

    @JsonProperty("ckycRpKYCVerificationEmpcode")
    public String getCkycRpKYCVerificationEmpcode() {
        return ckycRpKYCVerificationEmpcode;
    }

    @JsonProperty("ckycRpKYCVerificationEmpcode")
    @XmlElement(name = "CKYCRPKYCVerificationEmpcode")
    public void setCkycRpKYCVerificationEmpcode(String ckycRpKYCVerificationEmpcode) {
        this.ckycRpKYCVerificationEmpcode = ckycRpKYCVerificationEmpcode;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        final StringBuilder sb = new StringBuilder("CkycRelatedPerson{");
        sb.append("ckycRpSequence='").append(ckycRpSequence).append('\'');
        sb.append(", ckycRpRelation='").append(ckycRpRelation).append('\'');
        sb.append(", ckycRpCKYCNumber=").append(ckycRpCKYCNumber);
        sb.append(", ckycRpNamePrefix='").append(ckycRpNamePrefix).append('\'');
        sb.append(", ckycRpFirstName='").append(ckycRpFirstName).append('\'');
        sb.append(", ckycRpMiddleName=").append(ckycRpMiddleName);
        sb.append(", ckycRpLastName='").append(ckycRpLastName).append('\'');
        sb.append(", ckycRpMaidenPrefix='").append(ckycRpMaidenPrefix).append('\'');
        sb.append(", ckycRpMaidenFirstName='").append(ckycRpMaidenFirstName).append('\'');
        sb.append(", ckycRpMaidenMiddleName='").append(ckycRpMaidenMiddleName).append('\'');
        sb.append(", ckycRpMaidenLastName='").append(ckycRpMaidenLastName).append('\'');
        sb.append(", ckycRpFatherOrSpouseFlag='").append(ckycRpFatherOrSpouseFlag).append('\'');
        sb.append(", ckycRpFatherPrefix='").append(ckycRpFatherPrefix).append('\'');
        sb.append(", ckycRpFatherFirstName='").append(ckycRpFatherFirstName).append('\'');
        sb.append(", ckycRpFatherMiddleName='").append(ckycRpFatherMiddleName).append('\'');
        sb.append(", ckycRpFatherLastName='").append(ckycRpFatherLastName).append('\'');
        sb.append(", ckycRpMotherNamePrefix='").append(ckycRpMotherNamePrefix).append('\'');
        sb.append(", ckycRpMotherFirstName='").append(ckycRpMotherFirstName).append('\'');
        sb.append(", ckycRpMotherMiddleName='").append(ckycRpMotherMiddleName).append('\'');
        sb.append(", ckycRpMotherLastName='").append(ckycRpMotherLastName).append('\'');
        sb.append(", ckycRpDateOfBirth=").append(ckycRpDateOfBirth);
        sb.append(", ckycRpGender='").append(ckycRpGender).append('\'');
        sb.append(", ckycRpPerAdd1=").append(ckycRpPerAdd1);
        sb.append(", ckycRpPerAdd3='").append(ckycRpPerAdd3).append('\'');
        sb.append(", ckycRpPerAddCity='").append(ckycRpPerAddCity).append('\'');
        sb.append(", ckycRpPerAddDistrict='").append(ckycRpPerAddDistrict).append('\'');
        sb.append(", ckycRpPerAddState='").append(ckycRpPerAddState).append('\'');
        sb.append(", ckycRpPerAddCountry='").append(ckycRpPerAddCountry).append('\'');
        sb.append(", ckycRpPerAddPIN='").append(ckycRpPerAddPIN).append('\'');
        sb.append(", ckycRpPerAddPOA='").append(ckycRpPerAddPOA).append('\'');
        sb.append(", ckycRpPerAddSameasCorAdd='").append(ckycRpPerAddSameasCorAdd).append('\'');
        sb.append(", ckycRpCorAdd1='").append(ckycRpCorAdd1).append('\'');
        sb.append(", ckycRpCorAdd2='").append(ckycRpCorAdd2).append('\'');
        sb.append(", ckycRpCorAdd3='").append(ckycRpCorAdd3).append('\'');
        sb.append(", ckycRpCorAddCity='").append(ckycRpCorAddCity).append('\'');
        sb.append(", ckycRpCorAddDistrict='").append(ckycRpCorAddDistrict).append('\'');
        sb.append(", ckycRpCorAddState='").append(ckycRpCorAddState).append('\'');
        sb.append(", ckycRpCorAddCountry='").append(ckycRpCorAddCountry).append('\'');
        sb.append(", ckycRpCorAddPIN='").append(ckycRpCorAddPIN).append('\'');
        sb.append(", ckycRpCorAddPOA='").append(ckycRpCorAddPOA).append('\'');
        sb.append(", ckycRpResSTDCode=").append(ckycRpResSTDCode);
        sb.append(", ckycRpResTelNum=").append(ckycRpResTelNum);
        sb.append(", ckycRpOffSTDCode='").append(ckycRpOffSTDCode).append('\'');
        sb.append(", ckycRpOffTelNum='").append(ckycRpOffTelNum).append('\'');
        sb.append(", ckycRpMobCode='").append(ckycRpMobCode).append('\'');
        sb.append(", ckycRpMobNum='").append(ckycRpMobNum).append('\'');
        sb.append(", ckycRpEmail='").append(ckycRpEmail).append('\'');
        sb.append(", ckycRpRemarks=").append(ckycRpRemarks);
        sb.append(", ckycRpPhotoType='").append(ckycRpPhotoType).append('\'');
        sb.append(", ckycRpPhotoData='").append(ckycRpPhotoData).append('\'');
        sb.append(", ckycRpPerPOAType='").append(ckycRpPerPOAType).append('\'');
        sb.append(", ckycRpPerPOAData='").append(ckycRpPerPOAData).append('\'');
        sb.append(", ckycRpCorPOAType='").append(ckycRpCorPOAType).append('\'');
        sb.append(", ckycRpCorPOAData='").append(ckycRpCorPOAData).append('\'');
        sb.append(", ckycRpPAN=").append(ckycRpPAN);
        sb.append(", ckycRpFormSixty=").append(ckycRpFormSixty);
        sb.append(", ckycRpUID='").append(ckycRpUID).append('\'');
        sb.append(", ckycRpVoterId='").append(ckycRpVoterId).append('\'');
        sb.append(", ckycRpNREGA=").append(ckycRpNREGA);
        sb.append(", ckycRpPassportNumber=").append(ckycRpPassportNumber);
        sb.append(", ckycRpDrivingLicenceNumber=").append(ckycRpDrivingLicenceNumber);
        sb.append(", ckycRpNPRLetter='").append(ckycRpNPRLetter).append('\'');
        sb.append(", ckycRpOfflineVerficationAadhaar='").append(ckycRpOfflineVerficationAadhaar).append('\'');
        sb.append(", ckycRpeKYCAuthentication=").append(ckycRpeKYCAuthentication);
        sb.append(", ckycRpDateofDeclaration='").append(ckycRpDateofDeclaration).append('\'');
        sb.append(", ckycRpPlaceofDeclaration='").append(ckycRpPlaceofDeclaration).append('\'');
        sb.append(", ckycRpKYCVerificationDate='").append(ckycRpKYCVerificationDate).append('\'');
        sb.append(", ckycRpTypeofDocSubmitted='").append(ckycRpTypeofDocSubmitted).append('\'');
        sb.append(", ckycRpKYCVerificationName='").append(ckycRpKYCVerificationName).append('\'');
        sb.append(", ckycRpKYCVerificationDesg='").append(ckycRpKYCVerificationDesg).append('\'');
        sb.append(", ckycRpKYCVerificationBranch='").append(ckycRpKYCVerificationBranch).append('\'');
        sb.append(", ckycRpKYCVerificationEmpcode='").append(ckycRpKYCVerificationEmpcode).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
