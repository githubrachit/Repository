package com.mli.mpro.nps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.ArrayList;

public class Payload {
@Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pran")
    private String pran;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("pan")
    private String pan;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("aadhar")
    private String aadhar;

    @JsonProperty("title")
    private String title;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("firstName")
    private String firstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("middleName")
    private String middleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("maidenName")
    private String maidenName;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("fathersFirstName")
    private String fathersFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("fathersMiddleName")
    private String fathersMiddleName;

    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("fathersLastLame")
    private String fathersLastLame;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("sex")
    private String sex;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;

    @JsonProperty("dateOfRetirement")
    private String dateOfRetirement;

    @JsonProperty("dateOfResignation")
    private String dateOfResignation;
    @JsonProperty("maritalStatus")
    private String maritalStatus;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrLine1")
    private String corrAddrLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrLine2")
    private String corrAddrLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrLine3")
    private String corrAddrLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrLine4")
    private String corrAddrLine4;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrState")
    private String corrAddrState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrCountry")
    private String corrAddrCountry;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("corrAddrPincode")
    private String corrAddrPincode;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrLine1")
    private String permAddrLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrLine2")
    private String permAddrLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrLine3")
    private String permAddrLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrLine4")
    private String permAddrLine4;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrState")
    private String permAddrState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrCountry")
    private String permAddrCountry;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("permAddrPincode")
    private String permAddrPincode;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("telephoneNumber")
    private String telephoneNumber;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("faxNumber")
    private String faxNumber;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("emailId")
    private String emailId;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("alternatePhoneNumber")
    private String alternatePhoneNumber;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("subBankAccountNumber")
    private String subBankAccountNumber;
    @Sensitive(MaskType.BANK_NAME)
    @JsonProperty("bankName")
    private String bankName;
    @Sensitive(MaskType.BANK_BRANCH_NAME)
    @JsonProperty("bankBranch")
    private String bankBranch;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("bankAddress")
    private String bankAddress;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("bankPin")
    private String bankPin;
    @Sensitive(MaskType.BANK_MICR)
    @JsonProperty("bankMicr")
    private String bankMicr;
    @Sensitive(MaskType.BANK_IFSC)
    @JsonProperty("bankIfsc")
    private String bankIfsc;

    @JsonProperty("subscribersSector")
    private String subscribersSector;

    @JsonProperty("subscribersWithdrawalType")
    private String subscribersWithdrawalType;

    @JsonProperty("wdrReqAuthorizationDate")
    private String wdrReqAuthorizationDate;

    @JsonProperty("wdrAckId")
    private String wdrAckId;

    @JsonProperty("spouseAliveFlag")
    private String spouseAliveFlag;
@Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("spouseFirstName")
    private String spouseFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("spouseMiddleName")
    private String spouseMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("spouseLastName")
    private String spouseLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("spouseDob")
    private String spouseDob;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("spouseGender")
    private String spouseGender;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("spouseAADHAAR")
    private String spouseAADHAAR;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("spousePAN")
    private String spousePAN;

    @JsonProperty("dependentFatherAliveFlag")
    private String dependentFatherAliveFlag;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("dependentFatherFirstName")
    private String dependentFatherFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("dependentFatherMiddleName")
    private String dependentFatherMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("dependentFatherLastName")
    private String dependentFatherLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dependentFatherDob")
    private String dependentFatherDob;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("dependentFatherAADHAAR")
    private String dependentFatherAADHAAR;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("dependentFatherPan")
    private String dependentFatherPan;

    @JsonProperty("dependentMotherAliveFlag")
    private String dependentMotherAliveFlag;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("dependentMotherFirstName")
    private String dependentMotherFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("dependentMotherMiddleName")
    private String dependentMotherMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("dependentMotherLastName")
    private String dependentMotherLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dependentMotherDob")
    private String dependentMotherDob;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("dependentMotherAADHAAR")
    private String dependentMotherAADHAAR;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("dependentMotherPAN")
    private String dependentMotherPAN;

    @JsonProperty("child1AliveFlag")
    private String child1AliveFlag;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("child1FirstName")
    private String child1FirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("child1MiddleName")
    private String child1MiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("child1LastName")
    private String child1LastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("child1Dob")
    private String child1Dob;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("child1AADHAAR")
    private String child1AADHAAR;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("child1PAN")
    private String child1PAN;

    @JsonProperty("child2AliveFlag")
    private String child2AliveFlag;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("child2FirstName")
    private String child2FirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("child2MiddleName")
    private String child2MiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("child2LastName")
    private String child2LastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("child2Dob")
    private String child2Dob;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("child2AADHAAR")
    private String child2AADHAAR;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("child2PAN")
    private String child2PAN;

    @JsonProperty("child3AliveFlag")
    private String child3AliveFlag;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("child3FirstName")
    private String child3FirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("child3MiddleName")
    private String child3MiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("child3LastName")
    private String child3LastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("child3Dob")
    private String child3Dob;
    @Sensitive(MaskType.AADHAAR_NUM)
    @JsonProperty("child3AADHAAR")
    private String child3AADHAAR;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("child3PAN")
    private String child3PAN;
    @Sensitive(MaskType.CKYC_NUM)
    @JsonProperty("cKYC")
    private String cKYC;

    @JsonProperty("politicallyExposedPerson")
    private String politicallyExposedPerson;

    @JsonProperty("relatedPoliticallyExposedPerson")
    private String relatedPoliticallyExposedPerson;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("criminalHistory")
    private String criminalHistory;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("criminalHistoryDetails")
    private String criminalHistoryDetails;

    @JsonProperty("subscriberDeclaration")
    private String subscriberDeclaration;

    @JsonProperty("nodalOfficeDeclaration")
    private String nodalOfficeDeclaration;

    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    @JsonProperty("responseTimestamp")
    private String responseTimestamp;

    @JsonProperty("dataReceived")
    private String dataReceived;

    @JsonProperty("proposerDeclaration")
    private String proposerDeclaration;

    @JsonProperty("nomineeDetails")
    private ArrayList<NomineeDetails> nomineeDetails;

    @JsonProperty("schemeDetails")
    private SchemeDetails schemeDetails;
    @JsonProperty("nomSrNo")
    private String nomSrNo;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("nomFirstName")
    private String nomFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("nomMiddleName")
    private String nomMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("nomLastName")
    private String nomLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("nomDOB")
    private String nomDOB;

    @JsonProperty("nomRelation")
    private String nomRelation;

    @JsonProperty("nomPercentage")
    private String nomPercentage;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine1")
    private String nomAddressLine1;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine2")
    private String nomAddressLine2;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine3")
    private String nomAddressLine3;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomAddressLine4")
    private String nomAddressLine4;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomState")
    private String nomState;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nomCountry")
    private String nomCountry;
    @Sensitive(MaskType.PINCODE)
    @JsonProperty("nomPin")
    private String nomPin;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("nomMobile")
    private String nomMobile;
    @Sensitive(MaskType.MOBILE)
    @JsonProperty("nomAlternateContact")
    private String nomAlternateContact;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("nomEmail")
    private String nomEmail;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("guardianFirstName")
    private String guardianFirstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("guardianMiddleName")
    private String guardianMiddleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("guardianLastName")
    private String guardianLastName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("guardianDOB")
    private String guardianDOB;



    public Payload() {
        //default constructor
    }

    public String getNomSrNo() {
        return nomSrNo;
    }

    public void setNomSrNo(String nomSrNo) {
        this.nomSrNo = nomSrNo;
    }

    public String getNomFirstName() {
        return nomFirstName;
    }

    public void setNomFirstName(String nomFirstName) {
        this.nomFirstName = nomFirstName;
    }

    public String getNomMiddleName() {
        return nomMiddleName;
    }

    public void setNomMiddleName(String nomMiddleName) {
        this.nomMiddleName = nomMiddleName;
    }

    public String getNomLastName() {
        return nomLastName;
    }

    public void setNomLastName(String nomLastName) {
        this.nomLastName = nomLastName;
    }

    public String getNomDOB() {
        return nomDOB;
    }

    public void setNomDOB(String nomDOB) {
        this.nomDOB = nomDOB;
    }

    public String getNomRelation() {
        return nomRelation;
    }

    public void setNomRelation(String nomRelation) {
        this.nomRelation = nomRelation;
    }

    public String getNomPercentage() {
        return nomPercentage;
    }

    public void setNomPercentage(String nomPercentage) {
        this.nomPercentage = nomPercentage;
    }

    public String getNomAddressLine1() {
        return nomAddressLine1;
    }

    public void setNomAddressLine1(String nomAddressLine1) {
        this.nomAddressLine1 = nomAddressLine1;
    }

    public String getNomAddressLine2() {
        return nomAddressLine2;
    }

    public void setNomAddressLine2(String nomAddressLine2) {
        this.nomAddressLine2 = nomAddressLine2;
    }

    public String getNomAddressLine3() {
        return nomAddressLine3;
    }

    public void setNomAddressLine3(String nomAddressLine3) {
        this.nomAddressLine3 = nomAddressLine3;
    }

    public String getNomAddressLine4() {
        return nomAddressLine4;
    }

    public void setNomAddressLine4(String nomAddressLine4) {
        this.nomAddressLine4 = nomAddressLine4;
    }

    public String getNomState() {
        return nomState;
    }

    public void setNomState(String nomState) {
        this.nomState = nomState;
    }

    public String getNomCountry() {
        return nomCountry;
    }

    public void setNomCountry(String nomCountry) {
        this.nomCountry = nomCountry;
    }

    public String getNomPin() {
        return nomPin;
    }

    public void setNomPin(String nomPin) {
        this.nomPin = nomPin;
    }

    public String getNomMobile() {
        return nomMobile;
    }

    public void setNomMobile(String nomMobile) {
        this.nomMobile = nomMobile;
    }

    public String getNomAlternateContact() {
        return nomAlternateContact;
    }

    public void setNomAlternateContact(String nomAlternateContact) {
        this.nomAlternateContact = nomAlternateContact;
    }

    public String getNomEmail() {
        return nomEmail;
    }

    public void setNomEmail(String nomEmail) {
        this.nomEmail = nomEmail;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public void setGuardianFirstName(String guardianFirstName) {
        this.guardianFirstName = guardianFirstName;
    }

    public String getGuardianMiddleName() {
        return guardianMiddleName;
    }

    public void setGuardianMiddleName(String guardianMiddleName) {
        this.guardianMiddleName = guardianMiddleName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public void setGuardianLastName(String guardianLastName) {
        this.guardianLastName = guardianLastName;
    }

    public String getGuardianDOB() {
        return guardianDOB;
    }

    public void setGuardianDOB(String guardianDOB) {
        this.guardianDOB = guardianDOB;
    }

    public String getDataReceived() {
        return dataReceived;
    }

    public void setDataReceived(String dataReceived) {
        this.dataReceived = dataReceived;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @JsonProperty("serviceStatus")
    private String serviceStatus;

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getPran() {
        return pran;
    }

    public void setPran(String pran) {
        this.pran = pran;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getFathersFirstName() {
        return fathersFirstName;
    }

    public void setFathersFirstName(String fathersFirstName) {
        this.fathersFirstName = fathersFirstName;
    }

    public String getFathersMiddleName() {
        return fathersMiddleName;
    }

    public void setFathersMiddleName(String fathersMiddleName) {
        this.fathersMiddleName = fathersMiddleName;
    }

    public String getFathersLastLame() {
        return fathersLastLame;
    }

    public void setFathersLastLame(String fathersLastLame) {
        this.fathersLastLame = fathersLastLame;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfRetirement() {
        return dateOfRetirement;
    }

    public void setDateOfRetirement(String dateOfRetirement) {
        this.dateOfRetirement = dateOfRetirement;
    }

    public String getDateOfResignation() {
        return dateOfResignation;
    }

    public void setDateOfResignation(String dateOfResignation) {
        this.dateOfResignation = dateOfResignation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCorrAddrLine1() {
        return corrAddrLine1;
    }

    public void setCorrAddrLine1(String corrAddrLine1) {
        this.corrAddrLine1 = corrAddrLine1;
    }

    public String getCorrAddrLine2() {
        return corrAddrLine2;
    }

    public void setCorrAddrLine2(String corrAddrLine2) {
        this.corrAddrLine2 = corrAddrLine2;
    }

    public String getCorrAddrLine3() {
        return corrAddrLine3;
    }

    public void setCorrAddrLine3(String corrAddrLine3) {
        this.corrAddrLine3 = corrAddrLine3;
    }

    public String getCorrAddrLine4() {
        return corrAddrLine4;
    }

    public void setCorrAddrLine4(String corrAddrLine4) {
        this.corrAddrLine4 = corrAddrLine4;
    }

    public String getCorrAddrState() {
        return corrAddrState;
    }

    public void setCorrAddrState(String corrAddrState) {
        this.corrAddrState = corrAddrState;
    }

    public String getCorrAddrCountry() {
        return corrAddrCountry;
    }

    public void setCorrAddrCountry(String corrAddrCountry) {
        this.corrAddrCountry = corrAddrCountry;
    }

    public String getCorrAddrPincode() {
        return corrAddrPincode;
    }

    public void setCorrAddrPincode(String corrAddrPincode) {
        this.corrAddrPincode = corrAddrPincode;
    }

    public String getPermAddrLine1() {
        return permAddrLine1;
    }

    public void setPermAddrLine1(String permAddrLine1) {
        this.permAddrLine1 = permAddrLine1;
    }

    public String getPermAddrLine2() {
        return permAddrLine2;
    }

    public void setPermAddrLine2(String permAddrLine2) {
        this.permAddrLine2 = permAddrLine2;
    }

    public String getPermAddrLine3() {
        return permAddrLine3;
    }

    public void setPermAddrLine3(String permAddrLine3) {
        this.permAddrLine3 = permAddrLine3;
    }

    public String getPermAddrLine4() {
        return permAddrLine4;
    }

    public void setPermAddrLine4(String permAddrLine4) {
        this.permAddrLine4 = permAddrLine4;
    }

    public String getPermAddrState() {
        return permAddrState;
    }

    public void setPermAddrState(String permAddrState) {
        this.permAddrState = permAddrState;
    }

    public String getPermAddrCountry() {
        return permAddrCountry;
    }

    public void setPermAddrCountry(String permAddrCountry) {
        this.permAddrCountry = permAddrCountry;
    }

    public String getPermAddrPincode() {
        return permAddrPincode;
    }

    public void setPermAddrPincode(String permAddrPincode) {
        this.permAddrPincode = permAddrPincode;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public String getSubBankAccountNumber() {
        return subBankAccountNumber;
    }

    public void setSubBankAccountNumber(String subBankAccountNumber) {
        this.subBankAccountNumber = subBankAccountNumber;
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

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankPin() {
        return bankPin;
    }

    public void setBankPin(String bankPin) {
        this.bankPin = bankPin;
    }

    public String getBankMicr() {
        return bankMicr;
    }

    public void setBankMicr(String bankMicr) {
        this.bankMicr = bankMicr;
    }

    public String getBankIfsc() {
        return bankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.bankIfsc = bankIfsc;
    }

    public String getSubscribersSector() {
        return subscribersSector;
    }

    public void setSubscribersSector(String subscribersSector) {
        this.subscribersSector = subscribersSector;
    }

    public String getSubscribersWithdrawalType() {
        return subscribersWithdrawalType;
    }

    public void setSubscribersWithdrawalType(String subscribersWithdrawalType) {
        this.subscribersWithdrawalType = subscribersWithdrawalType;
    }

    public String getWdrReqAuthorizationDate() {
        return wdrReqAuthorizationDate;
    }

    public void setWdrReqAuthorizationDate(String wdrReqAuthorizationDate) {
        this.wdrReqAuthorizationDate = wdrReqAuthorizationDate;
    }

    public String getWdrAckId() {
        return wdrAckId;
    }

    public void setWdrAckId(String wdrAckId) {
        this.wdrAckId = wdrAckId;
    }

    public String getSpouseAliveFlag() {
        return spouseAliveFlag;
    }

    public void setSpouseAliveFlag(String spouseAliveFlag) {
        this.spouseAliveFlag = spouseAliveFlag;
    }

    public String getSpouseFirstName() {
        return spouseFirstName;
    }

    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }

    public String getSpouseMiddleName() {
        return spouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        this.spouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public String getSpouseDob() {
        return spouseDob;
    }

    public void setSpouseDob(String spouseDob) {
        this.spouseDob = spouseDob;
    }

    public String getSpouseGender() {
        return spouseGender;
    }

    public void setSpouseGender(String spouseGender) {
        this.spouseGender = spouseGender;
    }

    public String getSpouseAADHAAR() {
        return spouseAADHAAR;
    }

    public void setSpouseAADHAAR(String spouseAADHAAR) {
        this.spouseAADHAAR = spouseAADHAAR;
    }

    public String getSpousePAN() {
        return spousePAN;
    }

    public void setSpousePAN(String spousePAN) {
        this.spousePAN = spousePAN;
    }

    public String getDependentFatherAliveFlag() {
        return dependentFatherAliveFlag;
    }

    public void setDependentFatherAliveFlag(String dependentFatherAliveFlag) {
        this.dependentFatherAliveFlag = dependentFatherAliveFlag;
    }

    public String getDependentFatherFirstName() {
        return dependentFatherFirstName;
    }

    public void setDependentFatherFirstName(String dependentFatherFirstName) {
        this.dependentFatherFirstName = dependentFatherFirstName;
    }

    public String getDependentFatherMiddleName() {
        return dependentFatherMiddleName;
    }

    public void setDependentFatherMiddleName(String dependentFatherMiddleName) {
        this.dependentFatherMiddleName = dependentFatherMiddleName;
    }

    public String getDependentFatherLastName() {
        return dependentFatherLastName;
    }

    public void setDependentFatherLastName(String dependentFatherLastName) {
        this.dependentFatherLastName = dependentFatherLastName;
    }

    public String getDependentFatherDob() {
        return dependentFatherDob;
    }

    public void setDependentFatherDob(String dependentFatherDob) {
        this.dependentFatherDob = dependentFatherDob;
    }

    public String getDependentFatherAADHAAR() {
        return dependentFatherAADHAAR;
    }

    public void setDependentFatherAADHAAR(String dependentFatherAADHAAR) {
        this.dependentFatherAADHAAR = dependentFatherAADHAAR;
    }

    public String getDependentFatherPan() {
        return dependentFatherPan;
    }

    public void setDependentFatherPan(String dependentFatherPan) {
        this.dependentFatherPan = dependentFatherPan;
    }

    public String getDependentMotherAliveFlag() {
        return dependentMotherAliveFlag;
    }

    public void setDependentMotherAliveFlag(String dependentMotherAliveFlag) {
        this.dependentMotherAliveFlag = dependentMotherAliveFlag;
    }

    public String getDependentMotherFirstName() {
        return dependentMotherFirstName;
    }

    public void setDependentMotherFirstName(String dependentMotherFirstName) {
        this.dependentMotherFirstName = dependentMotherFirstName;
    }

    public String getDependentMotherMiddleName() {
        return dependentMotherMiddleName;
    }

    public void setDependentMotherMiddleName(String dependentMotherMiddleName) {
        this.dependentMotherMiddleName = dependentMotherMiddleName;
    }

    public String getDependentMotherLastName() {
        return dependentMotherLastName;
    }

    public void setDependentMotherLastName(String dependentMotherLastName) {
        this.dependentMotherLastName = dependentMotherLastName;
    }

    public String getDependentMotherDob() {
        return dependentMotherDob;
    }

    public void setDependentMotherDob(String dependentMotherDob) {
        this.dependentMotherDob = dependentMotherDob;
    }

    public String getDependentMotherAADHAAR() {
        return dependentMotherAADHAAR;
    }

    public void setDependentMotherAADHAAR(String dependentMotherAADHAAR) {
        this.dependentMotherAADHAAR = dependentMotherAADHAAR;
    }

    public String getDependentMotherPAN() {
        return dependentMotherPAN;
    }

    public void setDependentMotherPAN(String dependentMotherPAN) {
        this.dependentMotherPAN = dependentMotherPAN;
    }

    public String getChild1AliveFlag() {
        return child1AliveFlag;
    }

    public void setChild1AliveFlag(String child1AliveFlag) {
        this.child1AliveFlag = child1AliveFlag;
    }

    public String getChild1FirstName() {
        return child1FirstName;
    }

    public void setChild1FirstName(String child1FirstName) {
        this.child1FirstName = child1FirstName;
    }

    public String getChild1MiddleName() {
        return child1MiddleName;
    }

    public void setChild1MiddleName(String child1MiddleName) {
        this.child1MiddleName = child1MiddleName;
    }

    public String getChild1LastName() {
        return child1LastName;
    }

    public void setChild1LastName(String child1LastName) {
        this.child1LastName = child1LastName;
    }

    public String getChild1Dob() {
        return child1Dob;
    }

    public void setChild1Dob(String child1Dob) {
        this.child1Dob = child1Dob;
    }

    public String getChild1AADHAAR() {
        return child1AADHAAR;
    }

    public void setChild1AADHAAR(String child1AADHAAR) {
        this.child1AADHAAR = child1AADHAAR;
    }

    public String getChild1PAN() {
        return child1PAN;
    }

    public void setChild1PAN(String child1PAN) {
        this.child1PAN = child1PAN;
    }

    public String getChild2AliveFlag() {
        return child2AliveFlag;
    }

    public void setChild2AliveFlag(String child2AliveFlag) {
        this.child2AliveFlag = child2AliveFlag;
    }

    public String getChild2FirstName() {
        return child2FirstName;
    }

    public void setChild2FirstName(String child2FirstName) {
        this.child2FirstName = child2FirstName;
    }

    public String getChild2MiddleName() {
        return child2MiddleName;
    }

    public void setChild2MiddleName(String child2MiddleName) {
        this.child2MiddleName = child2MiddleName;
    }

    public String getChild2LastName() {
        return child2LastName;
    }

    public void setChild2LastName(String child2LastName) {
        this.child2LastName = child2LastName;
    }

    public String getChild2Dob() {
        return child2Dob;
    }

    public void setChild2Dob(String child2Dob) {
        this.child2Dob = child2Dob;
    }

    public String getChild2AADHAAR() {
        return child2AADHAAR;
    }

    public void setChild2AADHAAR(String child2AADHAAR) {
        this.child2AADHAAR = child2AADHAAR;
    }

    public String getChild2PAN() {
        return child2PAN;
    }

    public void setChild2PAN(String child2PAN) {
        this.child2PAN = child2PAN;
    }

    public String getChild3AliveFlag() {
        return child3AliveFlag;
    }

    public void setChild3AliveFlag(String child3AliveFlag) {
        this.child3AliveFlag = child3AliveFlag;
    }

    public String getChild3FirstName() {
        return child3FirstName;
    }

    public void setChild3FirstName(String child3FirstName) {
        this.child3FirstName = child3FirstName;
    }

    public String getChild3MiddleName() {
        return child3MiddleName;
    }

    public void setChild3MiddleName(String child3MiddleName) {
        this.child3MiddleName = child3MiddleName;
    }

    public String getChild3LastName() {
        return child3LastName;
    }

    public void setChild3LastName(String child3LastName) {
        this.child3LastName = child3LastName;
    }

    public String getChild3Dob() {
        return child3Dob;
    }

    public void setChild3Dob(String child3Dob) {
        this.child3Dob = child3Dob;
    }

    public String getChild3AADHAAR() {
        return child3AADHAAR;
    }

    public void setChild3AADHAAR(String child3AADHAAR) {
        this.child3AADHAAR = child3AADHAAR;
    }

    public String getChild3PAN() {
        return child3PAN;
    }

    public void setChild3PAN(String child3PAN) {
        this.child3PAN = child3PAN;
    }

    public String getcKYC() {
        return cKYC;
    }

    public void setcKYC(String cKYC) {
        this.cKYC = cKYC;
    }

    public String getPoliticallyExposedPerson() {
        return politicallyExposedPerson;
    }

    public void setPoliticallyExposedPerson(String politicallyExposedPerson) {
        this.politicallyExposedPerson = politicallyExposedPerson;
    }

    public String getRelatedPoliticallyExposedPerson() {
        return relatedPoliticallyExposedPerson;
    }

    public void setRelatedPoliticallyExposedPerson(String relatedPoliticallyExposedPerson) {
        this.relatedPoliticallyExposedPerson = relatedPoliticallyExposedPerson;
    }

    public String getCriminalHistory() {
        return criminalHistory;
    }

    public void setCriminalHistory(String criminalHistory) {
        this.criminalHistory = criminalHistory;
    }

    public String getCriminalHistoryDetails() {
        return criminalHistoryDetails;
    }

    public void setCriminalHistoryDetails(String criminalHistoryDetails) {
        this.criminalHistoryDetails = criminalHistoryDetails;
    }

    public String getSubscriberDeclaration() {
        return subscriberDeclaration;
    }

    public void setSubscriberDeclaration(String subscriberDeclaration) {
        this.subscriberDeclaration = subscriberDeclaration;
    }

    public String getNodalOfficeDeclaration() {
        return nodalOfficeDeclaration;
    }

    public void setNodalOfficeDeclaration(String nodalOfficeDeclaration) {
        this.nodalOfficeDeclaration = nodalOfficeDeclaration;
    }

    public String getProposerDeclaration() {
        return proposerDeclaration;
    }

    public void setProposerDeclaration(String proposerDeclaration) {
        this.proposerDeclaration = proposerDeclaration;
    }

    public ArrayList<NomineeDetails> getNomineeDetails() {
        return nomineeDetails;
    }

    public void setNomineeDetails(ArrayList<NomineeDetails> nomineeDetails) {
        this.nomineeDetails = nomineeDetails;
    }

    public SchemeDetails getSchemeDetails() {
        return schemeDetails;
    }

    public void setSchemeDetails(SchemeDetails schemeDetails) {
        this.schemeDetails = schemeDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Payload{" +
                "pran='" + pran + '\'' +
                ", pan='" + pan + '\'' +
                ", aadhar='" + aadhar + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", maidenName='" + maidenName + '\'' +
                ", fathersFirstName='" + fathersFirstName + '\'' +
                ", fathersMiddleName='" + fathersMiddleName + '\'' +
                ", fathersLastLame='" + fathersLastLame + '\'' +
                ", sex='" + sex + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", dateOfRetirement='" + dateOfRetirement + '\'' +
                ", dateOfResignation='" + dateOfResignation + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", corrAddrLine1='" + corrAddrLine1 + '\'' +
                ", corrAddrLine2='" + corrAddrLine2 + '\'' +
                ", corrAddrLine3='" + corrAddrLine3 + '\'' +
                ", corrAddrLine4='" + corrAddrLine4 + '\'' +
                ", corrAddrState='" + corrAddrState + '\'' +
                ", corrAddrCountry='" + corrAddrCountry + '\'' +
                ", corrAddrPincode='" + corrAddrPincode + '\'' +
                ", permAddrLine1='" + permAddrLine1 + '\'' +
                ", permAddrLine2='" + permAddrLine2 + '\'' +
                ", permAddrLine3='" + permAddrLine3 + '\'' +
                ", permAddrLine4='" + permAddrLine4 + '\'' +
                ", permAddrState='" + permAddrState + '\'' +
                ", permAddrCountry='" + permAddrCountry + '\'' +
                ", permAddrPincode='" + permAddrPincode + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", alternatePhoneNumber='" + alternatePhoneNumber + '\'' +
                ", subBankAccountNumber='" + subBankAccountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankBranch='" + bankBranch + '\'' +
                ", bankAddress='" + bankAddress + '\'' +
                ", bankPin='" + bankPin + '\'' +
                ", bankMicr='" + bankMicr + '\'' +
                ", bankIfsc='" + bankIfsc + '\'' +
                ", subscribersSector='" + subscribersSector + '\'' +
                ", subscribersWithdrawalType='" + subscribersWithdrawalType + '\'' +
                ", wdrReqAuthorizationDate='" + wdrReqAuthorizationDate + '\'' +
                ", wdrAckId='" + wdrAckId + '\'' +
                ", spouseAliveFlag='" + spouseAliveFlag + '\'' +
                ", spouseFirstName='" + spouseFirstName + '\'' +
                ", spouseMiddleName='" + spouseMiddleName + '\'' +
                ", spouseLastName='" + spouseLastName + '\'' +
                ", spouseDob='" + spouseDob + '\'' +
                ", spouseGender='" + spouseGender + '\'' +
                ", spouseAADHAAR='" + spouseAADHAAR + '\'' +
                ", spousePAN='" + spousePAN + '\'' +
                ", dependentFatherAliveFlag='" + dependentFatherAliveFlag + '\'' +
                ", dependentFatherFirstName='" + dependentFatherFirstName + '\'' +
                ", dependentFatherMiddleName='" + dependentFatherMiddleName + '\'' +
                ", dependentFatherLastName='" + dependentFatherLastName + '\'' +
                ", dependentFatherDob='" + dependentFatherDob + '\'' +
                ", dependentFatherAADHAAR='" + dependentFatherAADHAAR + '\'' +
                ", dependentFatherPan='" + dependentFatherPan + '\'' +
                ", dependentMotherAliveFlag='" + dependentMotherAliveFlag + '\'' +
                ", dependentMotherFirstName='" + dependentMotherFirstName + '\'' +
                ", dependentMotherMiddleName='" + dependentMotherMiddleName + '\'' +
                ", dependentMotherLastName='" + dependentMotherLastName + '\'' +
                ", dependentMotherDob='" + dependentMotherDob + '\'' +
                ", dependentMotherAADHAAR='" + dependentMotherAADHAAR + '\'' +
                ", dependentMotherPAN='" + dependentMotherPAN + '\'' +
                ", child1AliveFlag='" + child1AliveFlag + '\'' +
                ", child1FirstName='" + child1FirstName + '\'' +
                ", child1MiddleName='" + child1MiddleName + '\'' +
                ", child1LastName='" + child1LastName + '\'' +
                ", child1Dob='" + child1Dob + '\'' +
                ", child1AADHAAR='" + child1AADHAAR + '\'' +
                ", child1PAN='" + child1PAN + '\'' +
                ", child2AliveFlag='" + child2AliveFlag + '\'' +
                ", child2FirstName='" + child2FirstName + '\'' +
                ", child2MiddleName='" + child2MiddleName + '\'' +
                ", child2LastName='" + child2LastName + '\'' +
                ", child2Dob='" + child2Dob + '\'' +
                ", child2AADHAAR='" + child2AADHAAR + '\'' +
                ", child2PAN='" + child2PAN + '\'' +
                ", child3AliveFlag='" + child3AliveFlag + '\'' +
                ", child3FirstName='" + child3FirstName + '\'' +
                ", child3MiddleName='" + child3MiddleName + '\'' +
                ", child3LastName='" + child3LastName + '\'' +
                ", child3Dob='" + child3Dob + '\'' +
                ", child3AADHAAR='" + child3AADHAAR + '\'' +
                ", child3PAN='" + child3PAN + '\'' +
                ", cKYC='" + cKYC + '\'' +
                ", politicallyExposedPerson='" + politicallyExposedPerson + '\'' +
                ", relatedPoliticallyExposedPerson='" + relatedPoliticallyExposedPerson + '\'' +
                ", criminalHistory='" + criminalHistory + '\'' +
                ", criminalHistoryDetails='" + criminalHistoryDetails + '\'' +
                ", subscriberDeclaration='" + subscriberDeclaration + '\'' +
                ", nodalOfficeDeclaration='" + nodalOfficeDeclaration + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", responseTimestamp='" + responseTimestamp + '\'' +
                ", dataReceived='" + dataReceived + '\'' +
                ", proposerDeclaration='" + proposerDeclaration + '\'' +
                ", nomineeDetails=" + nomineeDetails +
                ", schemeDetails=" + schemeDetails +
                ", nomSrNo='" + nomSrNo + '\'' +
                ", nomFirstName='" + nomFirstName + '\'' +
                ", nomMiddleName='" + nomMiddleName + '\'' +
                ", nomLastName='" + nomLastName + '\'' +
                ", nomDOB='" + nomDOB + '\'' +
                ", nomRelation='" + nomRelation + '\'' +
                ", nomPercentage='" + nomPercentage + '\'' +
                ", nomAddressLine1='" + nomAddressLine1 + '\'' +
                ", nomAddressLine2='" + nomAddressLine2 + '\'' +
                ", nomAddressLine3='" + nomAddressLine3 + '\'' +
                ", nomAddressLine4='" + nomAddressLine4 + '\'' +
                ", nomState='" + nomState + '\'' +
                ", nomCountry='" + nomCountry + '\'' +
                ", nomPin='" + nomPin + '\'' +
                ", nomMobile='" + nomMobile + '\'' +
                ", nomAlternateContact='" + nomAlternateContact + '\'' +
                ", nomEmail='" + nomEmail + '\'' +
                ", guardianFirstName='" + guardianFirstName + '\'' +
                ", guardianMiddleName='" + guardianMiddleName + '\'' +
                ", guardianLastName='" + guardianLastName + '\'' +
                ", guardianDOB='" + guardianDOB + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                '}';
    }
}



