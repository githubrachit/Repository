
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "mothersFirstName", "mothersLastName", "doYouHaveCKYCNumber", "ckycNumber", "addressProofType", "pleaseSpecify", "addressProofExpiryDate",
        "idProofType", "idProofnumber", "idProofExpiryDate", "nriCityOfBirth" })
public class CkycDetails {

    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("mothersFirstName")
    private String mothersFirstName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("mothersLastName")
    private String mothersLastName;
    @Sensitive(MaskType.ADDRESS_PROOF_NUM)
    @JsonProperty("doYouHaveCKYCNumber")
    private String doYouHaveCKYCNumber;
    @Sensitive(MaskType.ADDRESS_PROOF_NUM)
    @JsonProperty("ckycNumber")
    private String ckycNumber;
    @JsonProperty("addressProofType")
    private String addressProofType;
    @JsonProperty("pleaseSpecify")
    private String pleaseSpecify;
    @JsonProperty("addressProofExpiryDate")
    private String addressProofExpiryDate;
    @JsonProperty("idProofType")
    private String idProofType;
    @Sensitive(MaskType.ID_PROOF_NUM)
    @JsonProperty("idProofnumber")
    private String idProofnumber;
    @JsonProperty("idProofExpiryDate")
    private String idProofExpiryDate;
    @Sensitive(MaskType.ADDRESS)
    @JsonProperty("nriCityOfBirth")
    private String nriCityOfBirth;
    @JsonProperty("ovdFlag")
    private String ovdFlag;
    @JsonProperty("isPanRetry")
    private String isPanRetry;
    @JsonProperty("crifScore")
    private String crifScore;
    @JsonProperty("crifEstimatedIncome")
    private String crifEstimatedIncome;
    @JsonProperty("isDobMatchFromCrif")
    private String isDobMatchFromCrif;
    @JsonProperty("isIncomeProofWaiverFromCrif")
    private String isIncomeProofWaiverFromCrif;
    @JsonProperty("isIncomeProofWaiver")
    private String isIncomeProofWaiver;
    @Sensitive(MaskType.MASK_ALL)
    @JsonProperty("cibilScore")
    private String cibilScore;
    @JsonProperty("cibilEstimatedIncome")
    private String cibilEstimatedIncome;
    @JsonProperty("isDobMatchFromCibil")
    private String isDobMatchFromCibil;
    @JsonProperty("isIncomeProofWaiverFromCibil")
    private String isIncomeProofWaiverFromCibil;
    @JsonProperty("isPraWaivedByCkyc")
    private String isPraWaivedByCkyc;
    @JsonProperty("isCraWaivedByCkyc")
    private String isCraWaivedByCkyc;
    @JsonProperty("praWaiverPinCodeCaseCkyc")
    private String praWaiverPinCodeCaseCkyc;
    @JsonProperty("craWaiverPinCodeCaseCkyc")
    private String craWaiverPinCodeCaseCkyc;
    @JsonProperty("praPinCodeFromCkyc")
    private String praPinCodeFromCkyc;
    @JsonProperty("craPinCodeFromCkyc")
    private String craPinCodeFromCkyc;
    @JsonProperty("isPraWaiver")
    private String isPraWaiver;
    @JsonProperty("praWaiverSource")
    private String praWaiverSource;
    @JsonProperty("isCraWaiver")
    private String isCraWaiver;
    @JsonProperty("craWaiverSource")
    private String craWaiverSource;
    @JsonProperty("isPraWaivedByAml")
    private String isPraWaivedByAml;
    @JsonProperty("isCraWaivedByAml")
    private String isCraWaivedByAml;
    @JsonProperty("isAddressProofUpload")
    private String isAddressProofUpload;
    @JsonProperty("isIncomeProofUpload")
    private String isIncomeProofUpload;
    @JsonProperty("isPanUpload")
    private String isPanUpload;
    @JsonProperty("isDobProofUpload")
    private String isDobProofUpload;
    @JsonProperty("isPayorPanUpload")
    private String isPayorPanUpload;
    @JsonProperty("isPayorDobProofUpload")
    private String isPayorDobProofUpload;
    @JsonProperty("isPhotoUpload")
    private String isPhotoUpload;
    @JsonProperty("isSpouseIncomeProofUpload")
    private String isSpouseIncomeProofUpload;
    @JsonProperty("isSpousePolicyBondUpload")
    private String isSpousePolicyBondUpload;
    @JsonProperty("isMedicalDocUpload")
    private String isMedicalDocUpload;
    @JsonProperty("panWaiverSource")
    private String panWaiverSource;
    @JsonProperty("clientId")
    private String clientId;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("existingPolicyNumber")
    private String existingPolicyNumber;
    @Sensitive(MaskType.POLICY_NUM)
    @JsonProperty("statusOfExistingPolicyNumber")
    private String statusOfExistingPolicyNumber;
    @JsonProperty("isPreviousPolicyOfferedOnModified")
    private String isPreviousPolicyOfferedOnModified;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("msa")
    private String msa;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("totalSumAssured")
    private String totalSumAssured;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("incomeWaiverFSA")
    private String incomeWaiverFSA;
    @JsonProperty("existingCustomerRecheck")
    private String existingCustomerRecheck;
    @JsonProperty("praPinCityFromCkyc")
    private String praPinCityFromCkyc;
    @JsonProperty("craPinCityFromCkyc")
    private String craPinCityFromCkyc;
    @JsonProperty("incomeWaiverSource")
    private String incomeWaiverSource;
    @JsonProperty("isPanValidationForJL")
    private boolean isPanValidationForJL;
    @JsonProperty("isJLDOBwaiverFromPANValidation")
    private boolean isJLDOBwaiverFromPANValidation;
    @JsonProperty("isPanAdhaarLinked")
    private String isPanAdhaarLinked;

    /**
     * No args constructor for use in serialization
     *
     */
    public CkycDetails() {
    }

    /**
     *
     * @param ckycNumber
     * @param pleaseSpecify
     * @param idProofType
     * @param nriCityOfBirth
     * @param idProofExpiryDate
     * @param mothersFirstName
     * @param mothersLastName
     * @param addressProofExpiryDate
     * @param doYouHaveCKYCNumber
     * @param idProofnumber
     * @param addressProofType
     */
    public CkycDetails(String mothersFirstName, String mothersLastName, String doYouHaveCKYCNumber, String ckycNumber, String addressProofType,
                       String pleaseSpecify, String addressProofExpiryDate, String idProofType, String idProofnumber, String idProofExpiryDate, String nriCityOfBirth) {
        super();
        this.mothersFirstName = mothersFirstName;
        this.mothersLastName = mothersLastName;
        this.doYouHaveCKYCNumber = doYouHaveCKYCNumber;
        this.ckycNumber = ckycNumber;
        this.addressProofType = addressProofType;
        this.pleaseSpecify = pleaseSpecify;
        this.addressProofExpiryDate = addressProofExpiryDate;
        this.idProofType = idProofType;
        this.idProofnumber = idProofnumber;
        this.idProofExpiryDate = idProofExpiryDate;
        this.nriCityOfBirth = nriCityOfBirth;
    }

    @JsonProperty("mothersFirstName")
    public String getMothersFirstName() {
        return mothersFirstName;
    }

    @JsonProperty("mothersFirstName")
    public void setMothersFirstName(String mothersFirstName) {
        this.mothersFirstName = mothersFirstName;
    }

    @JsonProperty("mothersLastName")
    public String getMothersLastName() {
        return mothersLastName;
    }

    @JsonProperty("mothersLastName")
    public void setMothersLastName(String mothersLastName) {
        this.mothersLastName = mothersLastName;
    }

    @JsonProperty("doYouHaveCKYCNumber")
    public String getDoYouHaveCKYCNumber() {
        return doYouHaveCKYCNumber;
    }

    @JsonProperty("doYouHaveCKYCNumber")
    public void setDoYouHaveCKYCNumber(String doYouHaveCKYCNumber) {
        this.doYouHaveCKYCNumber = doYouHaveCKYCNumber;
    }

    @JsonProperty("ckycNumber")
    public String getCkycNumber() {
        return ckycNumber;
    }

    @JsonProperty("ckycNumber")
    public void setCkycNumber(String ckycNumber) {
        this.ckycNumber = ckycNumber;
    }

    @JsonProperty("addressProofType")
    public String getAddressProofType() {
        return addressProofType;
    }

    @JsonProperty("addressProofType")
    public void setAddressProofType(String addressProofType) {
        this.addressProofType = addressProofType;
    }

    @JsonProperty("pleaseSpecify")
    public String getPleaseSpecify() {
        return pleaseSpecify;
    }

    @JsonProperty("pleaseSpecify")
    public void setPleaseSpecify(String pleaseSpecify) {
        this.pleaseSpecify = pleaseSpecify;
    }

    @JsonProperty("addressProofExpiryDate")
    public String getAddressProofExpiryDate() {
        return addressProofExpiryDate;
    }

    @JsonProperty("addressProofExpiryDate")
    public void setAddressProofExpiryDate(String addressProofExpiryDate) {
        this.addressProofExpiryDate = addressProofExpiryDate;
    }

    @JsonProperty("idProofType")
    public String getIdProofType() {
        return idProofType;
    }

    @JsonProperty("idProofType")
    public void setIdProofType(String idProofType) {
        this.idProofType = idProofType;
    }

    @JsonProperty("idProofnumber")
    public String getIdProofnumber() {
        return idProofnumber;
    }

    @JsonProperty("idProofnumber")
    public void setIdProofnumber(String idProofnumber) {
        this.idProofnumber = idProofnumber;
    }

    @JsonProperty("idProofExpiryDate")
    public String getIdProofExpiryDate() {
        return idProofExpiryDate;
    }

    @JsonProperty("idProofExpiryDate")
    public void setIdProofExpiryDate(String idProofExpiryDate) {
        this.idProofExpiryDate = idProofExpiryDate;
    }

    @JsonProperty("nriCityOfBirth")
    public String getNriCityOfBirth() {
        return nriCityOfBirth;
    }

    @JsonProperty("nriCityOfBirth")
    public void setNriCityOfBirth(String nriCityOfBirth) {
        this.nriCityOfBirth = nriCityOfBirth;
    }

    @JsonProperty("ovdFlag")
    public String getOvdFlag() { return ovdFlag; }

    @JsonProperty("ovdFlag")
    public CkycDetails setOvdFlag(String ovdFlag) {
        this.ovdFlag = ovdFlag;
        return this;
    }

    public String getIsPanRetry() {
        return isPanRetry;
    }

    public CkycDetails setIsPanRetry(String isPanRetry) {
        this.isPanRetry = isPanRetry;
        return this;
    }

    public String getCrifScore() {
        return crifScore;
    }

    public CkycDetails setCrifScore(String crifScore) {
        this.crifScore = crifScore;
        return this;
    }

    public String getCrifEstimatedIncome() {
        return crifEstimatedIncome;
    }

    public CkycDetails setCrifEstimatedIncome(String crifEstimatedIncome) {
        this.crifEstimatedIncome = crifEstimatedIncome;
        return this;
    }

    public String getIsDobMatchFromCrif() {
        return isDobMatchFromCrif;
    }

    public CkycDetails setIsDobMatchFromCrif(String isDobMatchFromCrif) {
        this.isDobMatchFromCrif = isDobMatchFromCrif;
        return this;
    }

    public String getIsIncomeProofWaiverFromCrif() {
        return isIncomeProofWaiverFromCrif;
    }

    public CkycDetails setIsIncomeProofWaiverFromCrif(String isIncomeProofWaiverFromCrif) {
        this.isIncomeProofWaiverFromCrif = isIncomeProofWaiverFromCrif;
        return this;
    }

    public String getIsIncomeProofWaiver() {
        return isIncomeProofWaiver;
    }

    public CkycDetails setIsIncomeProofWaiver(String isIncomeProofWaiver) {
        this.isIncomeProofWaiver = isIncomeProofWaiver;
        return this;
    }

    public String getCibilScore() {
        return cibilScore;
    }

    public CkycDetails setCibilScore(String cibilScore) {
        this.cibilScore = cibilScore;
        return this;
    }

    public String getCibilEstimatedIncome() {
        return cibilEstimatedIncome;
    }

    public CkycDetails setCibilEstimatedIncome(String cibilEstimatedIncome) {
        this.cibilEstimatedIncome = cibilEstimatedIncome;
        return this;
    }

    public String getIsDobMatchFromCibil() {
        return isDobMatchFromCibil;
    }

    public CkycDetails setIsDobMatchFromCibil(String isDobMatchFromCibil) {
        this.isDobMatchFromCibil = isDobMatchFromCibil;
        return this;
    }

    public String getIsIncomeProofWaiverFromCibil() {
        return isIncomeProofWaiverFromCibil;
    }

    public CkycDetails setIsIncomeProofWaiverFromCibil(String isIncomeProofWaiverFromCibil) {
        this.isIncomeProofWaiverFromCibil = isIncomeProofWaiverFromCibil;
        return this;
    }

    public String getIsPraWaivedByCkyc() {
        return isPraWaivedByCkyc;
    }

    public CkycDetails setIsPraWaivedByCkyc(String isPraWaivedByCkyc) {
        this.isPraWaivedByCkyc = isPraWaivedByCkyc;
        return this;
    }

    public String getIsCraWaivedByCkyc() {
        return isCraWaivedByCkyc;
    }

    public CkycDetails setIsCraWaivedByCkyc(String isCraWaivedByCkyc) {
        this.isCraWaivedByCkyc = isCraWaivedByCkyc;
        return this;
    }

    public String getPraWaiverPinCodeCaseCkyc() {
        return praWaiverPinCodeCaseCkyc;
    }

    public CkycDetails setPraWaiverPinCodeCaseCkyc(String praWaiverPinCodeCaseCkyc) {
        this.praWaiverPinCodeCaseCkyc = praWaiverPinCodeCaseCkyc;
        return this;
    }

    public String getCraWaiverPinCodeCaseCkyc() {
        return craWaiverPinCodeCaseCkyc;
    }

    public CkycDetails setCraWaiverPinCodeCaseCkyc(String craWaiverPinCodeCaseCkyc) {
        this.craWaiverPinCodeCaseCkyc = craWaiverPinCodeCaseCkyc;
        return this;
    }

    public String getPraPinCodeFromCkyc() {
        return praPinCodeFromCkyc;
    }

    public CkycDetails setPraPinCodeFromCkyc(String praPinCodeFromCkyc) {
        this.praPinCodeFromCkyc = praPinCodeFromCkyc;
        return this;
    }

    public String getCraPinCodeFromCkyc() {
        return craPinCodeFromCkyc;
    }

    public CkycDetails setCraPinCodeFromCkyc(String craPinCodeFromCkyc) {
        this.craPinCodeFromCkyc = craPinCodeFromCkyc;
        return this;
    }

    public String getIsPraWaiver() {
        return isPraWaiver;
    }

    public CkycDetails setIsPraWaiver(String isPraWaiver) {
        this.isPraWaiver = isPraWaiver;
        return this;
    }

    public String getPraWaiverSource() {
        return praWaiverSource;
    }

    public CkycDetails setPraWaiverSource(String praWaiverSource) {
        this.praWaiverSource = praWaiverSource;
        return this;
    }

    public String getIsCraWaiver() {
        return isCraWaiver;
    }

    public CkycDetails setIsCraWaiver(String isCraWaiver) {
        this.isCraWaiver = isCraWaiver;
        return this;
    }

    public String getCraWaiverSource() {
        return craWaiverSource;
    }

    public CkycDetails setCraWaiverSource(String craWaiverSource) {
        this.craWaiverSource = craWaiverSource;
        return this;
    }

    public String getIsPraWaivedByAml() {
        return isPraWaivedByAml;
    }

    public CkycDetails setIsPraWaivedByAml(String isPraWaivedByAml) {
        this.isPraWaivedByAml = isPraWaivedByAml;
        return this;
    }

    public String getIsCraWaivedByAml() {
        return isCraWaivedByAml;
    }

    public CkycDetails setIsCraWaivedByAml(String isCraWaivedByAml) {
        this.isCraWaivedByAml = isCraWaivedByAml;
        return this;
    }

    public String getIsAddressProofUpload() {
        return isAddressProofUpload;
    }

    public CkycDetails setIsAddressProofUpload(String isAddressProofUpload) {
        this.isAddressProofUpload = isAddressProofUpload;
        return this;
    }

    public String getIsIncomeProofUpload() {
        return isIncomeProofUpload;
    }

    public CkycDetails setIsIncomeProofUpload(String isIncomeProofUpload) {
        this.isIncomeProofUpload = isIncomeProofUpload;
        return this;
    }

    public String getIsPanUpload() {
        return isPanUpload;
    }

    public CkycDetails setIsPanUpload(String isPanUpload) {
        this.isPanUpload = isPanUpload;
        return this;
    }

    public String getIsDobProofUpload() {
        return isDobProofUpload;
    }

    public CkycDetails setIsDobProofUpload(String isDobProofUpload) {
        this.isDobProofUpload = isDobProofUpload;
        return this;
    }

    public String getIsPayorPanUpload() {
        return isPayorPanUpload;
    }

    public CkycDetails setIsPayorPanUpload(String isPayorPanUpload) {
        this.isPayorPanUpload = isPayorPanUpload;
        return this;
    }

    public String getIsPayorDobProofUpload() {
        return isPayorDobProofUpload;
    }

    public CkycDetails setIsPayorDobProofUpload(String isPayorDobProofUpload) {
        this.isPayorDobProofUpload = isPayorDobProofUpload;
        return this;
    }

    public String getIsPhotoUpload() {
        return isPhotoUpload;
    }

    public CkycDetails setIsPhotoUpload(String isPhotoUpload) {
        this.isPhotoUpload = isPhotoUpload;
        return this;
    }

    public String getIsSpouseIncomeProofUpload() {
        return isSpouseIncomeProofUpload;
    }

    public CkycDetails setIsSpouseIncomeProofUpload(String isSpouseIncomeProofUpload) {
        this.isSpouseIncomeProofUpload = isSpouseIncomeProofUpload;
        return this;
    }

    public String getIsSpousePolicyBondUpload() {
        return isSpousePolicyBondUpload;
    }

    public CkycDetails setIsSpousePolicyBondUpload(String isSpousePolicyBondUpload) {
        this.isSpousePolicyBondUpload = isSpousePolicyBondUpload;
        return this;
    }

    public String getIsMedicalDocUpload() {
        return isMedicalDocUpload;
    }

    public CkycDetails setIsMedicalDocUpload(String isMedicalDocUpload) {
        this.isMedicalDocUpload = isMedicalDocUpload;
        return this;
    }

    public String getPanWaiverSource() {
        return panWaiverSource;
    }

    public CkycDetails setPanWaiverSource(String panWaiverSource) {
        this.panWaiverSource = panWaiverSource;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public CkycDetails setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getExistingPolicyNumber() {
        return existingPolicyNumber;
    }

    public CkycDetails setExistingPolicyNumber(String existingPolicyNumber) {
        this.existingPolicyNumber = existingPolicyNumber;
        return this;
    }

    public String getStatusOfExistingPolicyNumber() {
        return statusOfExistingPolicyNumber;
    }

    public CkycDetails setStatusOfExistingPolicyNumber(String statusOfExistingPolicyNumber) {
        this.statusOfExistingPolicyNumber = statusOfExistingPolicyNumber;
        return this;
    }

    public String getIsPreviousPolicyOfferedOnModified() {
        return isPreviousPolicyOfferedOnModified;
    }

    public CkycDetails setIsPreviousPolicyOfferedOnModified(String isPreviousPolicyOfferedOnModified) {
        this.isPreviousPolicyOfferedOnModified = isPreviousPolicyOfferedOnModified;
        return this;
    }

    public String getMsa() {
        return msa;
    }

    public CkycDetails setMsa(String msa) {
        this.msa = msa;
        return this;
    }

    public String getTotalSumAssured() {
        return totalSumAssured;
    }

    public CkycDetails setTotalSumAssured(String totalSumAssured) {
        this.totalSumAssured = totalSumAssured;
        return this;
    }

    public String getIncomeWaiverFSA() {
        return incomeWaiverFSA;
    }

    public CkycDetails setIncomeWaiverFSA(String incomeWaiverFSA) {
        this.incomeWaiverFSA = incomeWaiverFSA;
        return this;
    }

    public String getExistingCustomerRecheck() {
        return existingCustomerRecheck;
    }

    public CkycDetails setExistingCustomerRecheck(String existingCustomerRecheck) {
        this.existingCustomerRecheck = existingCustomerRecheck;
        return this;
    }

    public String getPraPinCityFromCkyc() {
        return praPinCityFromCkyc;
    }

    public CkycDetails setPraPinCityFromCkyc(String praPinCityFromCkyc) {
        this.praPinCityFromCkyc = praPinCityFromCkyc;
        return this;
    }

    public String getCraPinCityFromCkyc() {
        return craPinCityFromCkyc;
    }

    public CkycDetails setCraPinCityFromCkyc(String craPinCityFromCkyc) {
        this.craPinCityFromCkyc = craPinCityFromCkyc;
        return this;
    }

    public String getIncomeWaiverSource() {
        return incomeWaiverSource;
    }

    public CkycDetails setIncomeWaiverSource(String incomeWaiverSource) {
        this.incomeWaiverSource = incomeWaiverSource;
        return this;
    }

    public boolean isPanValidationForJL() {
        return isPanValidationForJL;
    }

    public void setPanValidationForJL(boolean panValidationForJL) {
        isPanValidationForJL = panValidationForJL;
    }

    public boolean isJLDOBwaiverFromPANValidation() {
        return isJLDOBwaiverFromPANValidation;
    }

    public void setJLDOBwaiverFromPANValidation(boolean JLDOBwaiverFromPANValidation) {
        isJLDOBwaiverFromPANValidation = JLDOBwaiverFromPANValidation;
    }

    public String getIsPanAdhaarLinked() {
        return isPanAdhaarLinked;
    }

    public void setIsPanAdhaarLinked(String isPanAdhaarLinked) {
        this.isPanAdhaarLinked = isPanAdhaarLinked;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", CkycDetails.class.getSimpleName() + "[", "]")
                .add("mothersFirstName='" + mothersFirstName + "'")
                .add("mothersLastName='" + mothersLastName + "'")
                .add("doYouHaveCKYCNumber='" + doYouHaveCKYCNumber + "'")
                .add("ckycNumber='" + ckycNumber + "'")
                .add("addressProofType='" + addressProofType + "'")
                .add("pleaseSpecify='" + pleaseSpecify + "'")
                .add("addressProofExpiryDate='" + addressProofExpiryDate + "'")
                .add("idProofType='" + idProofType + "'")
                .add("idProofnumber='" + idProofnumber + "'")
                .add("idProofExpiryDate='" + idProofExpiryDate + "'")
                .add("nriCityOfBirth='" + nriCityOfBirth + "'")
                .add("ovdFlag='" + ovdFlag + "'")
                .add("isPanRetry='" + isPanRetry + "'")
                .add("crifScore='" + crifScore + "'")
                .add("crifEstimatedIncome='" + crifEstimatedIncome + "'")
                .add("isDobMatchFromCrif='" + isDobMatchFromCrif + "'")
                .add("isIncomeProofWaiverFromCrif='" + isIncomeProofWaiverFromCrif + "'")
                .add("isIncomeProofWaiver='" + isIncomeProofWaiver + "'")
                .add("cibilScore='" + cibilScore + "'")
                .add("cibilEstimatedIncome='" + cibilEstimatedIncome + "'")
                .add("isDobMatchFromCibil='" + isDobMatchFromCibil + "'")
                .add("isIncomeProofWaiverFromCibil='" + isIncomeProofWaiverFromCibil + "'")
                .add("isPraWaivedByCkyc='" + isPraWaivedByCkyc + "'")
                .add("isCraWaivedByCkyc='" + isCraWaivedByCkyc + "'")
                .add("praWaiverPinCodeCaseCkyc='" + praWaiverPinCodeCaseCkyc + "'")
                .add("craWaiverPinCodeCaseCkyc='" + craWaiverPinCodeCaseCkyc + "'")
                .add("praPinCodeFromCkyc='" + praPinCodeFromCkyc + "'")
                .add("craPinCodeFromCkyc='" + craPinCodeFromCkyc + "'")
                .add("isPraWaiver='" + isPraWaiver + "'")
                .add("praWaiverSource='" + praWaiverSource + "'")
                .add("isCraWaiver='" + isCraWaiver + "'")
                .add("craWaiverSource='" + craWaiverSource + "'")
                .add("isPraWaivedByAml='" + isPraWaivedByAml + "'")
                .add("isCraWaivedByAml='" + isCraWaivedByAml + "'")
                .add("isAddressProofUpload='" + isAddressProofUpload + "'")
                .add("isIncomeProofUpload='" + isIncomeProofUpload + "'")
                .add("isPanUpload='" + isPanUpload + "'")
                .add("isDobProofUpload='" + isDobProofUpload + "'")
                .add("isPayorPanUpload='" + isPayorPanUpload + "'")
                .add("isPayorDobProofUpload='" + isPayorDobProofUpload + "'")
                .add("isPhotoUpload='" + isPhotoUpload + "'")
                .add("isSpouseIncomeProofUpload='" + isSpouseIncomeProofUpload + "'")
                .add("isSpousePolicyBondUpload='" + isSpousePolicyBondUpload + "'")
                .add("isMedicalDocUpload='" + isMedicalDocUpload + "'")
                .add("panWaiverSource='" + panWaiverSource + "'")
                .add("clientId='" + clientId + "'")
                .add("existingPolicyNumber='" + existingPolicyNumber + "'")
                .add("statusOfExistingPolicyNumber='" + statusOfExistingPolicyNumber + "'")
                .add("isPreviousPolicyOfferedOnModified='" + isPreviousPolicyOfferedOnModified + "'")
                .add("msa='" + msa + "'")
                .add("totalSumAssured='" + totalSumAssured + "'")
                .add("incomeWaiverFSA='" + incomeWaiverFSA + "'")
                .add("existingCustomerRecheck='" + existingCustomerRecheck + "'")
                .add("praPinCityFromCkyc='" + praPinCityFromCkyc + "'")
                .add("craPinCityFromCkyc='" + craPinCityFromCkyc + "'")
                .add("incomeWaiverSource='" + incomeWaiverSource + "'")
                .add("isPanValidationForJL='" + isPanValidationForJL + "'")
                .add("isJLDOBwaiverFromPANValidation='" + isJLDOBwaiverFromPANValidation + "'")
                .add("isPanAdhaarLinked='" + isPanAdhaarLinked + "'")
                .toString();
    }
}
