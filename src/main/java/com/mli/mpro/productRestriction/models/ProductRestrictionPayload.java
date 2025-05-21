package com.mli.mpro.productRestriction.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.underwriting.clientPolicyDetailsResponseModels.PolicyDetails;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;

public class ProductRestrictionPayload {

    private long transactionId;
    private String productId;
    private String agentId;
    private String agentRole;
    private String customerCode;
    private String industryType;
    @Sensitive(MaskType.PINCODE)
    private String communicationPinCode;
    @Sensitive(MaskType.ADDRESS)
    private String communicationCountry;
    @Sensitive(MaskType.ADDRESS)
    private String permanentCountry;
    @Sensitive(MaskType.ADDRESS)
    private String communicationCity;
    /* FUL2-9472 WLS Product Restriction */
    @Sensitive(MaskType.ADDRESS)
    private String communicationState;
    private String education;
    private String insurerEducation;
    @Sensitive(MaskType.AMOUNT)
    private String income;
    @Sensitive(MaskType.GENDER)
    private String gender;
    @Sensitive(MaskType.AMOUNT)
    private String insurerAnnualIncome;
    @Sensitive(MaskType.PAN_NUM)
    private String panNumber;
    private String occupation;
    private String insurerOccupation;
    private String formType;
    private String schemeType;
    @Sensitive(MaskType.AMOUNT)
    private double sumAssured;
    private String channel;
    private String customerClassification;
    private String nationality;
    @Sensitive(MaskType.DOB)
    private Date dateOfBirth;
    @Sensitive(MaskType.DOB)
    private Date insurerDateOfBirth;
    private boolean isCIRider;
    private boolean isPartnerCareRider;
    private boolean isTermPlusRider;
    private boolean isAccidentalDeathAndDismembermentRider;
    private boolean isADBRider;
    private boolean isWOPPlusRider;
    private boolean isACORider;
    private boolean isACIRider;
    private String termPlusAddAmount;
    private String accidentalDeathAddAmount;
    private boolean isSmoker;
    private boolean isDiabetic;
    // FUL2-5522 OTP and SmTP Pr private String schemeType;oduct Restriction - CAT and SPARC
    private String isDedupeValidated;
    // FUL2-8762 OTP and SmTP Stop Rules- phase 2| Housewife
    private double spouseAnnualIncome;
    private double spouseTotalInsuranceCover;
    private boolean isPosSeller;
    private String currentScreen;
    //FUL2-40915 SSP - Negative & Conditional Pincode : Existing Customer Logic Change
    private String dedupeFlag;
    private String residentialStatus;

    //FUL2-48288 Restriction of FATF countries
    private Boolean fatfCountryFlag;

    private String companyCountry;

    // rider is selected or not "TRUE" or "FALSE"
    private String isCIRiderSelected;

    // CI rider sum Assured
    private String currentCIRiderSumAssured;
    // ACI rider sum Assured
    private String currentACIRiderSumAssured;
    // ACO rider sum Assured
    private String currentACORiderSumAssured;

    private List<PolicyDetails> clientPolicyProposerDetails;


    //FUL2-53692 Identification of the Smart Secure Easy Solution 
    private String isSSESProduct;
    @JsonProperty("SSESSolveOption")
    private String SSESSolveOption;

    private String goCode;

    private String isPasaEligible;
    private String pasaQ1;
    private String pasaQ2;
    private String pasaQ3;
    private String pasaQ4;
    @JsonProperty("isSspSwissReCase")
    private boolean isSspSwissReCase;
    
    private String isJointLife;
    private String insurerNationality;
    @JsonProperty("objectiveOfInsurance")
    private String objectiveOfInsurance;
    @JsonProperty("premiumType")
    private String premiumType;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("policyTerm")
    private String policyTerm;
    @JsonProperty("premiumPaymentTerm")
    private String premiumPaymentTerm;
    @JsonProperty("variant")
    private String variant;
    @JsonProperty("limitedTerm")
    private String limitedTerm;
    private String isPCB;
    private String premiumCommitment;
    private String source;
    @JsonProperty("annuityOption")
    private String annuityOption;

    @JsonProperty("annuityType")
    private String annuityType;

    @JsonProperty("annuityPurchasedFrom")
    private String annuityPurchasedFrom;
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPremiumCommitment() {
        return premiumCommitment;
    }

    public void setPremiumCommitment(String premiumCommitment) {
        this.premiumCommitment = premiumCommitment;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public boolean isTermPlusRider() {
        return isTermPlusRider;
    }

    public void setTermPlusRider(boolean termPlusRider) {
        isTermPlusRider = termPlusRider;
    }

    public String getAccidentalDeathAddAmount() {
        return accidentalDeathAddAmount;
    }

    public String getAnnuityOption() {
        return annuityOption;
    }

    public void setAnnuityOption(String annuityOption) {
        this.annuityOption = annuityOption;
    }

    public String getAnnuityType() {
        return annuityType;
    }

    public void setAnnuityType(String annuityType) {
        this.annuityType = annuityType;
    }

    public String getAnnuityPurchasedFrom() {
        return annuityPurchasedFrom;
    }

    public void setAnnuityPurchasedFrom(String annuityPurchasedFrom) {
        this.annuityPurchasedFrom = annuityPurchasedFrom;
    }

    public boolean isAccidentalDeathAndDismembermentRider() {
        return isAccidentalDeathAndDismembermentRider;
    }

    public void setAccidentalDeathAndDismembermentRider(boolean accidentalDeathAndDismembermentRider) {
        isAccidentalDeathAndDismembermentRider = accidentalDeathAndDismembermentRider;
    }

    public String getTermPlusAddAmount() {
        return termPlusAddAmount;
    }

    public void setTermPlusAddAmount(String termPlusAddAmount) {
        this.termPlusAddAmount = termPlusAddAmount;
    }

    public String isAccidentalDeathAddAmount() {
        return accidentalDeathAddAmount;
    }

    public void setAccidentalDeathAddAmount(String accidentalDeathAddAmount) {
        this.accidentalDeathAddAmount = accidentalDeathAddAmount;
    }

    public String getIsPCB() {
        return isPCB;
    }

    public void setIsPCB(String isPCB) {
        this.isPCB = isPCB;
    }

    public String getLimitedTerm() {
        return limitedTerm;
    }

    public void setLimitedTerm(String limitedTerm) {
        this.limitedTerm = limitedTerm;
    }

    public String getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(String policyTerm) {
        this.policyTerm = policyTerm;
    }

    public String getPremiumPaymentTerm() {
        return premiumPaymentTerm;
    }

    public void setPremiumPaymentTerm(String premiumPaymentTerm) {
        this.premiumPaymentTerm = premiumPaymentTerm;
    }
    public boolean isSspSwissReCase() {
        return isSspSwissReCase;
    }

    public void setSspSwissReCase(boolean sspSwissReCase) {
        isSspSwissReCase = sspSwissReCase;
    }

    public String getDedupeFlag() { return dedupeFlag; }
    public void setDedupeFlag(String dedupeFlag) { this.dedupeFlag = dedupeFlag; }

    public boolean isPosSeller() {
		return isPosSeller;
	}
	public void setisPosSeller(boolean isPosSeller) {
		this.isPosSeller = isPosSeller;
	}
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentRole() {
        return agentRole;
    }

    public void setAgentRole(String agentRole) {
        this.agentRole = agentRole;
    }

    public String getCommunicationPinCode() {
        return communicationPinCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }
    public void setCommunicationPinCode(String communicationPinCode) {
        this.communicationPinCode = communicationPinCode;
    }

    public String getCommunicationCountry() {
        return communicationCountry;
    }

    public void setCommunicationCountry(String communicationCountry) {
        this.communicationCountry = communicationCountry;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public String getCommunicationCity() {
        return communicationCity;
    }

    public void setCommunicationCity(String communicationCity) {
        this.communicationCity = communicationCity;
    }

    public String getCommunicationState() {
        return communicationState;
    }

    public void setCommunicationState(String communicationState) {
        this.communicationState = communicationState;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setCIRider(boolean CIRider) {
        isCIRider = CIRider;
    }

    public void setADBRider(boolean ADBRider) {
        isADBRider = ADBRider;
    }

    public void setWOPPlusRider(boolean WOPPlusRider) {
        isWOPPlusRider = WOPPlusRider;
    }

    public void setACORider(boolean ACORider) {
        isACORider = ACORider;
    }

    public void setACIRider(boolean ACIRider) {
        isACIRider = ACIRider;
    }

    public void setSmoker(boolean smoker) {
        isSmoker = smoker;
    }

    public void setDiabetic(boolean diabetic) {
        isDiabetic = diabetic;
    }

    public String getCurrentACIRiderSumAssured() {
        return currentACIRiderSumAssured;
    }

    public void setCurrentACIRiderSumAssured(String currentACIRiderSumAssured) {
        this.currentACIRiderSumAssured = currentACIRiderSumAssured;
    }

    public String getCurrentACORiderSumAssured() {
        return currentACORiderSumAssured;
    }

    public void setCurrentACORiderSumAssured(String currentACORiderSumAssured) {
        this.currentACORiderSumAssured = currentACORiderSumAssured;
    }

    public String getInsurerEducation() {
        return insurerEducation;
    }

    public void setInsurerEducation(String insurerEducation) {
        this.insurerEducation = insurerEducation;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getInsurerAnnualIncome() {
        return insurerAnnualIncome;
    }

    public void setInsurerAnnualIncome(String insurerAnnualIncome) {
        this.insurerAnnualIncome = insurerAnnualIncome;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getInsurerOccupation() {
        return insurerOccupation;
    }

    public void setInsurerOccupation(String insurerOccupation) {
        this.insurerOccupation = insurerOccupation;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustomerClassification() {
        return customerClassification;
    }

    public void setCustomerClassification(String customerClassification) {
        this.customerClassification = customerClassification;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isCIRider() {
        return isCIRider;
    }

    public void setIsCIRider(boolean CIRider) {
        isCIRider = CIRider;
    }

    public boolean isADBRider() {
        return isADBRider;
    }

    public boolean isPartnerCareRider() {
        return isPartnerCareRider;
    }

    public void setPartnerCareRider(boolean partnerCareRider) {
        isPartnerCareRider = partnerCareRider;
    }

    public void setIsADBRider(boolean ADBRider) {
        isADBRider = ADBRider;
    }

    public boolean isWOPPlusRider() {
        return isWOPPlusRider;
    }

    public void setIsWOPPlusRider(boolean IsWOPPlusRider) {
        isWOPPlusRider = IsWOPPlusRider;
    }

    public boolean isACORider() {
        return isACORider;
    }

    public void setIsACORider(boolean ACORider) {
        isACORider = ACORider;
    }

    public boolean isACIRider() {
        return isACIRider;
    }

    public void setIsACIRider(boolean ACIRider) {
        isACIRider = ACIRider;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(boolean smoker) {
        isSmoker = smoker;
    }
    
    public String getIsDedupeValidated() {
        return isDedupeValidated;
    }

    public void setIsDedupeValidated(String isDedupeValidated) {
        this.isDedupeValidated = isDedupeValidated;
    }


    public boolean isDiabetic() { return isDiabetic; }

    public void setIsDiabetic(boolean diabetic) { isDiabetic = diabetic; }
   
    public double getSpouseAnnualIncome() {
		return spouseAnnualIncome;
	}

	public void setSpouseAnnualIncome(double spouseAnnualIncome) {
		this.spouseAnnualIncome = spouseAnnualIncome;
	}

	public double getSpouseTotalInsuranceCover() {
		return spouseTotalInsuranceCover;
	}

	public void setSpouseTotalInsuranceCover(double spouseTotalInsuranceCover) {
		this.spouseTotalInsuranceCover = spouseTotalInsuranceCover;
	}
	
	public Date getInsurerDateOfBirth() {
		return insurerDateOfBirth;
	}

	public void setInsurerDateOfBirth(Date insurerDateOfBirth) {
		this.insurerDateOfBirth = insurerDateOfBirth;
	}

	public String getCurrentScreen() {
		return currentScreen;
	}
	public void setCurrentScreen(String currentScreen) {
		this.currentScreen = currentScreen;
	}

    public String getResidentialStatus() {
        return residentialStatus;
    }

    public void setResidentialStatus(String residentialStatus) {
        this.residentialStatus = residentialStatus;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public Boolean getFatfCountryFlag() {
        return fatfCountryFlag;
    }

    public void setFatfCountryFlag(Boolean fatfCountryFlag) {
        this.fatfCountryFlag = fatfCountryFlag;
    }

    public String getIsSSESProduct() {
		return isSSESProduct;
	}
	public void setIsSSESProduct(String isSSESProduct) {
		this.isSSESProduct = isSSESProduct;
	}
	public String getSSESSolveOption() {
		return SSESSolveOption;
	}
	public void setSSESSolveOption(String sSESSolveOption) {
		SSESSolveOption = sSESSolveOption;
	}
    public String getGoCode() {
        return goCode;
    }
    public void setGoCode(String goCode) {
        this.goCode = goCode;
    }

    public String getIsCIRiderSelected() { return isCIRiderSelected; }

    public void setIsCIRiderSelected(String isCIRiderSelected) { this.isCIRiderSelected = isCIRiderSelected; }

    public String getCurrentCIRiderSumAssured() { return currentCIRiderSumAssured; }

    public void setCurrentCIRiderSumAssured(String currentCIRiderSumAssured) { this.currentCIRiderSumAssured = currentCIRiderSumAssured; }

    public List<PolicyDetails> getClientPolicyProposerDetails() { return clientPolicyProposerDetails; }

    public void setClientPolicyProposerDetails(List<PolicyDetails> clientPolicyProposerDetails) { this.clientPolicyProposerDetails = clientPolicyProposerDetails; }
    public String getIsPasaEligible() {
        return isPasaEligible;
    }

    public void setIsPasaEligible(String isPasaEligible) {
        this.isPasaEligible = isPasaEligible;
    }

    public String getPasaQ1() {
        return pasaQ1;
    }

    public void setPasaQ1(String pasaQ1) {
        this.pasaQ1 = pasaQ1;
    }

    public String getPasaQ2() {
        return pasaQ2;
    }

    public void setPasaQ2(String pasaQ2) {
        this.pasaQ2 = pasaQ2;
    }

    public String getPasaQ3() {
        return pasaQ3;
    }

    public void setPasaQ3(String pasaQ3) {
        this.pasaQ3 = pasaQ3;
    }

    public String getPasaQ4() {
        return pasaQ4;
    }

    public void setPasaQ4(String pasaQ4) {
        this.pasaQ4 = pasaQ4;
    }
    
    public String getIsJointLife() {
		return isJointLife;
	}

	public void setIsJointLife(String isJointLife) {
		this.isJointLife = isJointLife;
	}

	public String getInsurerNationality() {
		return insurerNationality;
	}

	public void setInsurerNationality(String insurerNationality) {
		this.insurerNationality = insurerNationality;
	}

    @JsonProperty("objectiveOfInsurance")
    public String getObjectiveOfInsurance() {
        return objectiveOfInsurance;
    }

    @JsonProperty("objectiveOfInsurance")
    public void setObjectiveOfInsurance(String objectiveOfInsurance) {
        this.objectiveOfInsurance = objectiveOfInsurance;
    }

    @JsonProperty("productType")
    public String getProductType() {
        return productType;
    }

    @JsonProperty("productType")
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }
    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "ProductRestrictionPayload{" +
                "transactionId=" + transactionId +
                ", productId='" + productId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", agentRole='" + agentRole + '\'' +
                ", communicationPinCode='" + communicationPinCode + '\'' +
                ", communicationCountry='" + communicationCountry + '\'' +
                ", permanentCountry='" + permanentCountry + '\'' +
                ", communicationCity='" + communicationCity + '\'' +
                ", communicationState='" + communicationState + '\'' +
                ", education='" + education + '\'' +
                ", insurerEducation='" + insurerEducation + '\'' +
                ", income='" + income + '\'' +
                ", insurerAnnualIncome='" + insurerAnnualIncome + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", occupation='" + occupation + '\'' +
                ", insurerOccupation='" + insurerOccupation + '\'' +
                ", formType='" + formType + '\'' +
                ", schemeType='" + schemeType + '\'' +
                ", sumAssured=" + sumAssured +
                ", channel='" + channel + '\'' +
                ", customerClassification='" + customerClassification + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ",insurerDateOfBirth=" + insurerDateOfBirth +
                ", isCIRider=" + isCIRider +
                ", isADBRider=" + isADBRider +
                ", isWOPPlusRider=" + isWOPPlusRider +
                ", isSmoker=" + isSmoker +
                ", isDiabetic=" + isDiabetic +
                ", isDedupeValidated=" + isDedupeValidated + 
                ", spouseAnnualIncome=" + spouseAnnualIncome + 
                ", isPosSeller=" + isPosSeller +
                ", currentScreen=" + currentScreen +
                ", dedupeFlag=" + dedupeFlag +
                ", spouseTotalInsuranceCover=" + spouseTotalInsuranceCover +
                ", residentialStatus=" + residentialStatus +
                ", companyCountry=" + companyCountry +
                ", fatfCountryFlag=" + fatfCountryFlag +
                ", isSSESProduct=" + isSSESProduct +
                ", SSESSolveOption=" + SSESSolveOption + 
                ", goCode=" + goCode +
                ", isCIRiderSelected=" + isCIRiderSelected +
                ", currentCIRiderSumAssured=" + currentCIRiderSumAssured +
                ", clientPolicyProposerDetails=" + clientPolicyProposerDetails +
                ", isPasaEligible='" + isPasaEligible + '\'' +
                ", pasaQ1='" + pasaQ1 + '\'' +
                ", pasaQ2='" + pasaQ2 + '\'' +
                ", pasaQ3='" + pasaQ3 + '\'' +
                ", pasaQ4='" + pasaQ4 + '\'' +
                ", isJointLife=" + isJointLife +
                ", insurerNationality=" + insurerNationality +
                '}';
    }
}
