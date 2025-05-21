package com.mli.mpro.productRestriction.models.proposalFormRequestModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class Payload {

    @JsonInclude(JsonInclude.Include.NON_NULL)

    @JsonProperty("objectiveOfInsurance")
    private String objectiveOfInsurance;
    @JsonProperty("age")
    private String age;
    @JsonProperty("nomineeRelationship")
    private String nomineeRelationship;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("countryName")
    private String countryName;
    @JsonProperty("insuredMaritalStatus")
    private String insuredMaritalStatus;
    @JsonProperty("education")
    private String education;
    @JsonProperty("airForceRelatedQues1")
    private String airForceRelatedQues1;
    @JsonProperty("airForceRelatedQues2")
    private String airForceRelatedQues2;
    @JsonProperty("airForceRelatedQues3")
    private String airForceRelatedQues3;
    @JsonProperty("airForceRelatedQues4")
    private String airForceRelatedQues4;
    @JsonProperty("airForceRelatedQues5")
    private String airForceRelatedQues5;
    @JsonProperty("armedForceRelatedQues1")
    private String armedForceRelatedQues1;
    @JsonProperty("armedForceRelatedQues2")
    private String armedForceRelatedQues2;
    @JsonProperty("armedForceRelatedQues3")
    private String armedForceRelatedQues3;
    @JsonProperty("armedForceRelatedQues4")
    private String armedForceRelatedQues4;
    @JsonProperty("divingRelatedQues1")
    private String divingRelatedQues1;
    @JsonProperty("divingRelatedQues2")
    private String divingRelatedQues2;
    @JsonProperty("divingRelatedQues3")
    private String divingRelatedQues3;
    @JsonProperty("divingRelatedQues4")
    private String divingRelatedQues4;
    @JsonProperty("divingRelatedQues5")
    private String divingRelatedQues5;
    @JsonProperty("navyAndMerchaentRelatedQues1")
    private String navyAndMerchaentRelatedQues1;
    @JsonProperty("navyAndMerchaentRelatedQues2")
    private String navyAndMerchaentRelatedQues2;
    @JsonProperty("navyAndMerchaentRelatedQues3")
    private String navyAndMerchaentRelatedQues3;
    @JsonProperty("navyAndMerchaentRelatedQues4")
    private String navyAndMerchaentRelatedQues4;
    @JsonProperty("navyAndMerchaentRelatedQues5")
    private String navyAndMerchaentRelatedQues5;
    @JsonProperty("miningRelatedQues1")
    private String miningRelatedQues1;
    @JsonProperty("miningRelatedQues2")
    private String miningRelatedQues2;
    @JsonProperty("oilAndNaturalGasRelatedQues1")
    private String oilAndNaturalGasRelatedQues1;
    @JsonProperty("insuredIndustry")
    private String insuredIndustry;
    @JsonProperty("fsa")
    private String fsa;
    @JsonProperty("insuredOccupation")
    private String insuredOccupation;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("companyType")
    private String companyType;
    @JsonProperty("isPep")
    private String isPep;
    @JsonProperty("payorOrNot")
    private String payorOrNot;
    @JsonProperty("relationshipWithPayor")
    private String relationshipWithPayor;
    @JsonProperty("existingInsurance")
    private String existingInsurance;
    @JsonProperty("offeredAtModifiedTerms")
    private String offeredAtModifiedTerms;
    @JsonProperty("hobbyAsPartOfJob")
    private String hobbyAsPartOfJob;
    @JsonProperty("hobbyAsPassion")
    private String hobbyAsPassion;
    @JsonProperty("hobbyAsHolidayActivity")
    private String hobbyAsHolidayActivity;
    @JsonProperty("avocation")
    private String avocation;
    @JsonProperty("convicted")
    private String convicted;
    @JsonProperty("familyHistoryAdverse")
    private String familyHistoryAdverse;
    @JsonProperty("insuredHeight")
    private String insuredHeight;
    @JsonProperty("insuredWeight")
    private String insuredWeight;
    @JsonProperty("pregnancyComplicationDetails")
    private String pregnancyComplicationDetails;
    @JsonProperty("isPregnant")
    private String isPregnant;
    @JsonProperty("monthsOfPregnancy")
    private String monthsOfPregnancy;
    @JsonProperty("pregnancyComplications")
    private String pregnancyComplications;
    @JsonProperty("medicalQuestion19")
    private String medicalQuestion19;
    @JsonProperty("medicalQuestion20")
    private String medicalQuestion20;
    @JsonProperty("medicalQuestion20A")
    private String medicalQuestion20A;
    @JsonProperty("medicalQuestion20B")
    private String medicalQuestion20B;
    @JsonProperty("medicalQuestion20C")
    private String medicalQuestion20C;
    @JsonProperty("medicalQuestion20D")
    private String medicalQuestion20D;
    @JsonProperty("medicalQuestion21")
    private String medicalQuestion21;
    @JsonProperty("medicalQuestion21A")
    private String medicalQuestion21A;
    @JsonProperty("medicalQuestion21B")
    private String medicalQuestion21B;
    @JsonProperty("medicalQuestion22")
    private String medicalQuestion22;
    @JsonProperty("medicalQuestion22A")
    private String medicalQuestion22A;
    @JsonProperty("medicalQuestion22B")
    private String medicalQuestion22B;
    @JsonProperty("medicalQuestion22C")
    private String medicalQuestion22C;
    @JsonProperty("medicalQuestion22D")
    private String medicalQuestion22D;
    @JsonProperty("medicalQuestion23")
    private String medicalQuestion23;
    @JsonProperty("medicalQuestion23A")
    private String medicalQuestion23A;
    @JsonProperty("medicalQuestion23B")
    private String medicalQuestion23B;
    @JsonProperty("medicalQuestion23C")
    private String medicalQuestion23C;
    @JsonProperty("medicalQuestion23D")
    private String medicalQuestion23D;
    @JsonProperty("medicalQuestion23E")
    private String medicalQuestion23E;
    @JsonProperty("medicalQuestion24")
    private String medicalQuestion24;
    @JsonProperty("medicalQuestion24A")
    private String medicalQuestion24A;
    @JsonProperty("medicalQuestion24B")
    private String medicalQuestion24B;
    @JsonProperty("medicalQuestion24C")
    private String medicalQuestion24C;
    @JsonProperty("medicalQuestion25")
    private String medicalQuestion25;
    @JsonProperty("medicalQuestion25A")
    private String medicalQuestion25A;
    @JsonProperty("medicalQuestion25B")
    private String medicalQuestion25B;
    @JsonProperty("medicalQuestion25C")
    private String medicalQuestion25C;
    @JsonProperty("medicalQuestion25D")
    private String medicalQuestion25D;
    @JsonProperty("medicalQuestion26")
    private String medicalQuestion26;
    @JsonProperty("medicalQuestion26A")
    private String medicalQuestion26A;
    @JsonProperty("medicalQuestion27")
    private String medicalQuestion27;
    @JsonProperty("medicalQuestion27A")
    private String medicalQuestion27A;
    @JsonProperty("medicalQuestion27B")
    private String medicalQuestion27B;
    @JsonProperty("medicalQuestion27C")
    private String medicalQuestion27C;
    @JsonProperty("medicalQuestion27D")
    private String medicalQuestion27D;
    @JsonProperty("medicalQuestion27E")
    private String medicalQuestion27E;
    @JsonProperty("medicalQuestion27F")
    private String medicalQuestion27F;
    @JsonProperty("medicalQuestion28")
    private String medicalQuestion28;
    @JsonProperty("medicalQuestion28A")
    private String medicalQuestion28A;
    @JsonProperty("medicalQuestion28B")
    private String medicalQuestion28B;
    @JsonProperty("medicalQuestion28C")
    private String medicalQuestion28C;
    @JsonProperty("medicalQuestion28D")
    private String medicalQuestion28D;
    @JsonProperty("medicalQuestion28E")
    private String medicalQuestion28E;
    @JsonProperty("medicalQuestion28F")
    private String medicalQuestion28F;
    @JsonProperty("medicalQuestion28G")
    private String medicalQuestion28G;
    @JsonProperty("medicalQuestion29")
    private String medicalQuestion29;
    @JsonProperty("medicalQuestion29A")
    private String medicalQuestion29A;
    @JsonProperty("medicalQuestion29B")
    private String medicalQuestion29B;
    @JsonProperty("medicalQuestion29C")
    private String medicalQuestion29C;
    @JsonProperty("medicalQuestion29D")
    private String medicalQuestion29D;
    @JsonProperty("medicalQuestion29E")
    private String medicalQuestion29E;
    @JsonProperty("medicalQuestion29F")
    private String medicalQuestion29F;
    @JsonProperty("medicalQuestion29G")
    private String medicalQuestion29G;
    @JsonProperty("medicalQuestion29H")
    private String medicalQuestion29H;
    @JsonProperty("medicalQuestion29I")
    private String medicalQuestion29I;
    @JsonProperty("medicalQuestion29J")
    private String medicalQuestion29J;
    @JsonProperty("medicalQuestion29K")
    private String medicalQuestion29K;
    @JsonProperty("medicalQuestion29L")
    private String medicalQuestion29L;
    @JsonProperty("medicalQuestion29M")
    private String medicalQuestion29M;
    @JsonProperty("medicalQuestion29N")
    private String medicalQuestion29N;
    @JsonProperty("medicalQuestion30")
    private String medicalQuestion30;
    @JsonProperty("medicalQuestion30A")
    private String medicalQuestion30A;
    @JsonProperty("medicalQuestion30B")
    private String medicalQuestion30B;
    @JsonProperty("medicalQuestion30C")
    private String medicalQuestion30C;
    @JsonProperty("medicalQuestion30D")
    private String medicalQuestion30D;
    @JsonProperty("medicalQuestion30E")
    private String medicalQuestion30E;
    @JsonProperty("medicalQuestion30F")
    private String medicalQuestion30F;
    @JsonProperty("medicalQuestion30G")
    private String medicalQuestion30G;
    @JsonProperty("alcoholfreqQuestion37B")
    private String alcoholfreqQuestion37B;
    @JsonProperty("alcoholQtyQuestion37C")
    private String alcoholQtyQuestion37C;
    @JsonProperty("alcoholType37A")
    private String alcoholType37A;
    @JsonProperty("canceCareQuestion31")
    private String canceCareQuestion31;
    @JsonProperty("canceCareQuestion31A")
    private String canceCareQuestion31A;
    @JsonProperty("canceCareQuestion32")
    private String canceCareQuestion32;
    @JsonProperty("canceCareQuestion32A")
    private String canceCareQuestion32A;
    @JsonProperty("canceCareQuestion33")
    private String canceCareQuestion33;
    @JsonProperty("canceCareQuestion33A")
    private String canceCareQuestion33A;
    @JsonProperty("canceCareQuestion34")
    private String canceCareQuestion34;
    @JsonProperty("canceCareQuestion34A")
    private String canceCareQuestion34A;
    @JsonProperty("canceCareQuestion35")
    private String canceCareQuestion35;
    @JsonProperty("canceCareQuestion35A")
    private String canceCareQuestion35A;
    @JsonProperty("posQuestion35")
    private String posQuestion35;
    @JsonProperty("posQuestion35A")
    private String posQuestion35A;
    @JsonProperty("posQuestion36")
    private String posQuestion36;
    @JsonProperty("posQuestion36A")
    private String posQuestion36A;
    @JsonProperty("posQuestion37")
    private String posQuestion37;
    @JsonProperty("posQuestion37A")
    private String posQuestion37A;
    @JsonProperty("posQuestion38")
    private String posQuestion38;
    @JsonProperty("posQuestion38A")
    private String posQuestion38A;
    @JsonProperty("posQuestion39")
    private String posQuestion39;
    @JsonProperty("tobaccoFreqQuestion36B")
    private String tobaccoFreqQuestion36B;
    @JsonProperty("tobaccoQtyQuestion36C")
    private String tobaccoQtyQuestion36C;
    @JsonProperty("tobaccoTypeQuestion36A")
    private String tobaccoTypeQuestion36A;
    @JsonProperty("posAlcoholFreq")
    private String posAlcoholFreq;
    @JsonProperty("posAlcoholQty")
    private String posAlcoholQty;
    @JsonProperty("posAlcoholType")
    private String posAlcoholType;
    @JsonProperty("posDrug")
    private String posDrug;
    @JsonProperty("posDrugDetails")
    private String posDrugDetails;
    @JsonProperty("posTobaccoFreq")
    private String posTobaccoFreq;
    @JsonProperty("posTobaccoQty")
    private String posTobaccoQty;
    @JsonProperty("posTobaccoType")
    private String posTobaccoType;
    @JsonProperty("posTravelDetails")
    private String posTravelDetails;
    @JsonProperty("posTravelQuestion")
    private String posTravelQuestion;
    @JsonProperty("acrQuestion")
    private String acrQuestion;
    //FUL2-32223
    @JsonProperty("covidQuest")
    private String covidQuest;

    @JsonProperty("ciRiderSa")
    private String ciRiderSa;
    private String policyTerm;
    private String premiumPaymentTerm;

    //new properties
    private String praCountry;
    private String nriQuestResidingCountry;
    @Sensitive(MaskType.GENDER)
    private String proposerGender;
    private String productCode;
    @JsonProperty("initiativeType")
    private String initiativeType;
    
    @JsonProperty("isVaccinated")
    private String isVaccinated;
    private String agentCode;
    private String proposerIncome;
    private String insuredsuc;
    private String formType;
    private String tag1;
    private String tag2;
    private String tag3;
    private String tag4;
    private String tag5;
    private String cibilIncome;
    private String cibilScore;
    private String customerClasificationCode;
    private String agentLevel;
    private String craCity;
    private String appSignDate;
    private String craPinCode;
    private String channelGoCode;
    private String msa;
    private String sumAssured;
    private String termPlusRiderSumAssured;
    private String birthDate;
    private String birthMonth;
    private String natureOfJob;
    private String insuredIncome;
    private String smokingQuantity;
    private String tobaccoQuantity;
    private String wineQuantity;
    private String beerQuantity;
    private String liquorQuantity;
    private String prevPolDecPost;
    private String weightChange;
    private String smokingFrequency;
    private String tobaccoFrequency;
    private String wineFrequency;
    private String beerFrequency;
    private String liquorFrequency;
    private String proposerAge;
    private String proposerNationality;
    private String praCity;
    private String praPincode;
    private String existingCustomer;
    private String proposerOccupation;
    private String proposerEducation;
    private String policyAfyp;
    private String clientAfyp;
    private String policyCiDdRiderSA;
    private String clientCiDdRiderSA;
    private String policyAcoRiderSA;
    private String clientAcoRiderSA;
    private String policyAciRiderSa;
    private String clientAciRiderSa;
    private String policyWopRiderSA;
    private String bse500;
    private String parentsInsurance;
    private String spouseInsurance;
    private String smokerCode;
    private String bankRelationSince;
    private String proposerOrganisationType;
    private String spouseIncome;
    private String parentsIncome;
    private String riskScore;
    private String pfMedicalQTag;
    private String isDiabetic;
    @Sensitive(MaskType.PAN_NUM)
    private String panNumber;

    private String videoPosv;

    private String pcb;

    public String getPcb() {
        return pcb;
    }

    public void setPcb(String pcb) {
        this.pcb = pcb;
    }

    public String getPolicyAciRiderSa() {
        return policyAciRiderSa;
    }

    public void setPolicyAciRiderSa(String policyAciRiderSa) {
        this.policyAciRiderSa = policyAciRiderSa;
    }

    public String getClientAciRiderSa() {
        return clientAciRiderSa;
    }

    public void setClientAciRiderSa(String clientAciRiderSa) {
        this.clientAciRiderSa = clientAciRiderSa;
    }

    public String getVideoPosv() {
        return videoPosv;
    }

    public void setVideoPosv(String videoPosv) {
        this.videoPosv = videoPosv;
    }
    public String getObjectiveOfInsurance() {
	return objectiveOfInsurance;
    }

    public void setObjectiveOfInsurance(String objectiveOfInsurance) {
	this.objectiveOfInsurance = objectiveOfInsurance;
    }

    public String getAge() {
	return age;
    }

    public void setAge(String age) {
	this.age = age;
    }

    public String getNomineeRelationship() {
	return nomineeRelationship;
    }

    public void setNomineeRelationship(String nomineeRelationship) {
	this.nomineeRelationship = nomineeRelationship;
    }

    public String getNationality() {
	return nationality;
    }

    public void setNationality(String nationality) {
	this.nationality = nationality;
    }

    public String getCountryName() {
	return countryName;
    }

    public void setCountryName(String countryName) {
	this.countryName = countryName;
    }

    public String getInsuredMaritalStatus() {
	return insuredMaritalStatus;
    }

    public void setInsuredMaritalStatus(String insuredMaritalStatus) {
	this.insuredMaritalStatus = insuredMaritalStatus;
    }

    public String getEducation() {
	return education;
    }

    public void setEducation(String education) {
	this.education = education;
    }

    public String getAirForceRelatedQues1() {
	return airForceRelatedQues1;
    }

    public void setAirForceRelatedQues1(String airForceRelatedQues1) {
	this.airForceRelatedQues1 = airForceRelatedQues1;
    }

    public String getAirForceRelatedQues2() {
	return airForceRelatedQues2;
    }

    public void setAirForceRelatedQues2(String airForceRelatedQues2) {
	this.airForceRelatedQues2 = airForceRelatedQues2;
    }

    public String getAirForceRelatedQues3() {
	return airForceRelatedQues3;
    }

    public void setAirForceRelatedQues3(String airForceRelatedQues3) {
	this.airForceRelatedQues3 = airForceRelatedQues3;
    }

    public String getAirForceRelatedQues4() {
	return airForceRelatedQues4;
    }

    public void setAirForceRelatedQues4(String airForceRelatedQues4) {
	this.airForceRelatedQues4 = airForceRelatedQues4;
    }

    public String getAirForceRelatedQues5() {
	return airForceRelatedQues5;
    }

    public void setAirForceRelatedQues5(String airForceRelatedQues5) {
	this.airForceRelatedQues5 = airForceRelatedQues5;
    }

    public String getArmedForceRelatedQues1() {
	return armedForceRelatedQues1;
    }

    public void setArmedForceRelatedQues1(String armedForceRelatedQues1) {
	this.armedForceRelatedQues1 = armedForceRelatedQues1;
    }

    public String getArmedForceRelatedQues2() {
	return armedForceRelatedQues2;
    }

    public void setArmedForceRelatedQues2(String armedForceRelatedQues2) {
	this.armedForceRelatedQues2 = armedForceRelatedQues2;
    }

    public String getArmedForceRelatedQues3() {
	return armedForceRelatedQues3;
    }

    public void setArmedForceRelatedQues3(String armedForceRelatedQues3) {
	this.armedForceRelatedQues3 = armedForceRelatedQues3;
    }

    public String getArmedForceRelatedQues4() {
	return armedForceRelatedQues4;
    }

    public void setArmedForceRelatedQues4(String armedForceRelatedQues4) {
	this.armedForceRelatedQues4 = armedForceRelatedQues4;
    }

    public String getDivingRelatedQues1() {
	return divingRelatedQues1;
    }

    public void setDivingRelatedQues1(String divingRelatedQues1) {
	this.divingRelatedQues1 = divingRelatedQues1;
    }

    public String getDivingRelatedQues2() {
	return divingRelatedQues2;
    }

    public void setDivingRelatedQues2(String divingRelatedQues2) {
	this.divingRelatedQues2 = divingRelatedQues2;
    }

    public String getDivingRelatedQues3() {
	return divingRelatedQues3;
    }

    public void setDivingRelatedQues3(String divingRelatedQues3) {
	this.divingRelatedQues3 = divingRelatedQues3;
    }

    public String getDivingRelatedQues4() {
	return divingRelatedQues4;
    }

    public void setDivingRelatedQues4(String divingRelatedQues4) {
	this.divingRelatedQues4 = divingRelatedQues4;
    }

    public String getDivingRelatedQues5() {
	return divingRelatedQues5;
    }

    public void setDivingRelatedQues5(String divingRelatedQues5) {
	this.divingRelatedQues5 = divingRelatedQues5;
    }

    public String getNavyAndMerchaentRelatedQues1() {
	return navyAndMerchaentRelatedQues1;
    }

    public void setNavyAndMerchaentRelatedQues1(String navyAndMerchaentRelatedQues1) {
	this.navyAndMerchaentRelatedQues1 = navyAndMerchaentRelatedQues1;
    }

    public String getNavyAndMerchaentRelatedQues2() {
	return navyAndMerchaentRelatedQues2;
    }

    public void setNavyAndMerchaentRelatedQues2(String navyAndMerchaentRelatedQues2) {
	this.navyAndMerchaentRelatedQues2 = navyAndMerchaentRelatedQues2;
    }

    public String getNavyAndMerchaentRelatedQues3() {
	return navyAndMerchaentRelatedQues3;
    }

    public void setNavyAndMerchaentRelatedQues3(String navyAndMerchaentRelatedQues3) {
	this.navyAndMerchaentRelatedQues3 = navyAndMerchaentRelatedQues3;
    }

    public String getNavyAndMerchaentRelatedQues4() {
	return navyAndMerchaentRelatedQues4;
    }

    public void setNavyAndMerchaentRelatedQues4(String navyAndMerchaentRelatedQues4) {
	this.navyAndMerchaentRelatedQues4 = navyAndMerchaentRelatedQues4;
    }

    public String getNavyAndMerchaentRelatedQues5() {
	return navyAndMerchaentRelatedQues5;
    }

    public void setNavyAndMerchaentRelatedQues5(String navyAndMerchaentRelatedQues5) {
	this.navyAndMerchaentRelatedQues5 = navyAndMerchaentRelatedQues5;
    }

    public String getMiningRelatedQues1() {
	return miningRelatedQues1;
    }

    public void setMiningRelatedQues1(String miningRelatedQues1) {
	this.miningRelatedQues1 = miningRelatedQues1;
    }

    public String getMiningRelatedQues2() {
	return miningRelatedQues2;
    }

    public void setMiningRelatedQues2(String miningRelatedQues2) {
	this.miningRelatedQues2 = miningRelatedQues2;
    }

    public String getOilAndNaturalGasRelatedQues1() {
	return oilAndNaturalGasRelatedQues1;
    }

    public void setOilAndNaturalGasRelatedQues1(String oilAndNaturalGasRelatedQues1) {
	this.oilAndNaturalGasRelatedQues1 = oilAndNaturalGasRelatedQues1;
    }

    public String getInsuredIndustry() {
	return insuredIndustry;
    }

    public void setInsuredIndustry(String insuredIndustry) {
	this.insuredIndustry = insuredIndustry;
    }

    public String getFsa() {
	return fsa;
    }

    public void setFsa(String fsa) {
	this.fsa = fsa;
    }

    public String getInsuredOccupation() {
	return insuredOccupation;
    }

    public void setInsuredOccupation(String insuredOccupation) {
	this.insuredOccupation = insuredOccupation;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public String getCompanyType() {
	return companyType;
    }

    public void setCompanyType(String companyType) {
	this.companyType = companyType;
    }

    public String getIsPep() {
	return isPep;
    }

    public void setIsPep(String isPep) {
	this.isPep = isPep;
    }

    public String getPayorOrNot() {
	return payorOrNot;
    }

    public void setPayorOrNot(String payorOrNot) {
	this.payorOrNot = payorOrNot;
    }

    public String getRelationshipWithPayor() {
	return relationshipWithPayor;
    }

    public void setRelationshipWithPayor(String relationshipWithPayor) {
	this.relationshipWithPayor = relationshipWithPayor;
    }

    public String getExistingInsurance() {
	return existingInsurance;
    }

    public void setExistingInsurance(String existingInsurance) {
	this.existingInsurance = existingInsurance;
    }

    public String getOfferedAtModifiedTerms() {
	return offeredAtModifiedTerms;
    }

    public void setOfferedAtModifiedTerms(String offeredAtModifiedTerms) {
	this.offeredAtModifiedTerms = offeredAtModifiedTerms;
    }

    public String getHobbyAsPartOfJob() {
	return hobbyAsPartOfJob;
    }

    public void setHobbyAsPartOfJob(String hobbyAsPartOfJob) {
	this.hobbyAsPartOfJob = hobbyAsPartOfJob;
    }

    public String getHobbyAsPassion() {
	return hobbyAsPassion;
    }

    public void setHobbyAsPassion(String hobbyAsPassion) {
	this.hobbyAsPassion = hobbyAsPassion;
    }

    public String getHobbyAsHolidayActivity() {
	return hobbyAsHolidayActivity;
    }

    public void setHobbyAsHolidayActivity(String hobbyAsHolidayActivity) {
	this.hobbyAsHolidayActivity = hobbyAsHolidayActivity;
    }

    public String getAvocation() {
	return avocation;
    }

    public void setAvocation(String avocation) {
	this.avocation = avocation;
    }

    public String getConvicted() {
	return convicted;
    }

    public void setConvicted(String convicted) {
	this.convicted = convicted;
    }

    public String getFamilyHistoryAdverse() {
	return familyHistoryAdverse;
    }

    public void setFamilyHistoryAdverse(String familyHistoryAdverse) {
	this.familyHistoryAdverse = familyHistoryAdverse;
    }

    public String getInsuredHeight() {
	return insuredHeight;
    }

    public void setInsuredHeight(String insuredHeight) {
	this.insuredHeight = insuredHeight;
    }

    public String getInsuredWeight() {
	return insuredWeight;
    }

    public void setInsuredWeight(String insuredWeight) {
	this.insuredWeight = insuredWeight;
    }

    public String getPregnancyComplicationDetails() {
	return pregnancyComplicationDetails;
    }

    public void setPregnancyComplicationDetails(String pregnancyComplicationDetails) {
	this.pregnancyComplicationDetails = pregnancyComplicationDetails;
    }

    public String getIsPregnant() {
	return isPregnant;
    }

    public void setIsPregnant(String isPregnant) {
	this.isPregnant = isPregnant;
    }

    public String getMonthsOfPregnancy() {
	return monthsOfPregnancy;
    }

    public void setMonthsOfPregnancy(String monthsOfPregnancy) {
	this.monthsOfPregnancy = monthsOfPregnancy;
    }

    public String getPregnancyComplications() {
	return pregnancyComplications;
    }

    public void setPregnancyComplications(String pregnancyComplications) {
	this.pregnancyComplications = pregnancyComplications;
    }

    public String getMedicalQuestion19() {
	return medicalQuestion19;
    }

    public void setMedicalQuestion19(String medicalQuestion19) {
	this.medicalQuestion19 = medicalQuestion19;
    }

    public String getMedicalQuestion20() {
	return medicalQuestion20;
    }

    public void setMedicalQuestion20(String medicalQuestion20) {
	this.medicalQuestion20 = medicalQuestion20;
    }

    public String getMedicalQuestion20A() {
	return medicalQuestion20A;
    }

    public void setMedicalQuestion20A(String medicalQuestion20A) {
	this.medicalQuestion20A = medicalQuestion20A;
    }

    public String getMedicalQuestion20B() {
	return medicalQuestion20B;
    }

    public void setMedicalQuestion20B(String medicalQuestion20B) {
	this.medicalQuestion20B = medicalQuestion20B;
    }

    public String getMedicalQuestion20C() {
	return medicalQuestion20C;
    }

    public void setMedicalQuestion20C(String medicalQuestion20C) {
	this.medicalQuestion20C = medicalQuestion20C;
    }

    public String getMedicalQuestion20D() {
	return medicalQuestion20D;
    }

    public void setMedicalQuestion20D(String medicalQuestion20D) {
	this.medicalQuestion20D = medicalQuestion20D;
    }

    public String getMedicalQuestion21() {
	return medicalQuestion21;
    }

    public void setMedicalQuestion21(String medicalQuestion21) {
	this.medicalQuestion21 = medicalQuestion21;
    }

    public String getMedicalQuestion21A() {
	return medicalQuestion21A;
    }

    public void setMedicalQuestion21A(String medicalQuestion21A) {
	this.medicalQuestion21A = medicalQuestion21A;
    }

    public String getMedicalQuestion21B() {
	return medicalQuestion21B;
    }

    public void setMedicalQuestion21B(String medicalQuestion21B) {
	this.medicalQuestion21B = medicalQuestion21B;
    }

    public String getMedicalQuestion22() {
	return medicalQuestion22;
    }

    public void setMedicalQuestion22(String medicalQuestion22) {
	this.medicalQuestion22 = medicalQuestion22;
    }

    public String getMedicalQuestion22A() {
	return medicalQuestion22A;
    }

    public void setMedicalQuestion22A(String medicalQuestion22A) {
	this.medicalQuestion22A = medicalQuestion22A;
    }

    public String getMedicalQuestion22B() {
	return medicalQuestion22B;
    }

    public void setMedicalQuestion22B(String medicalQuestion22B) {
	this.medicalQuestion22B = medicalQuestion22B;
    }

    public String getMedicalQuestion22C() {
	return medicalQuestion22C;
    }

    public void setMedicalQuestion22C(String medicalQuestion22C) {
	this.medicalQuestion22C = medicalQuestion22C;
    }

    public String getMedicalQuestion22D() {
	return medicalQuestion22D;
    }

    public void setMedicalQuestion22D(String medicalQuestion22D) {
	this.medicalQuestion22D = medicalQuestion22D;
    }

    public String getMedicalQuestion23() {
	return medicalQuestion23;
    }

    public void setMedicalQuestion23(String medicalQuestion23) {
	this.medicalQuestion23 = medicalQuestion23;
    }

    public String getMedicalQuestion23A() {
	return medicalQuestion23A;
    }

    public void setMedicalQuestion23A(String medicalQuestion23A) {
	this.medicalQuestion23A = medicalQuestion23A;
    }

    public String getMedicalQuestion23B() {
	return medicalQuestion23B;
    }

    public void setMedicalQuestion23B(String medicalQuestion23B) {
	this.medicalQuestion23B = medicalQuestion23B;
    }

    public String getMedicalQuestion23C() {
	return medicalQuestion23C;
    }

    public void setMedicalQuestion23C(String medicalQuestion23C) {
	this.medicalQuestion23C = medicalQuestion23C;
    }

    public String getMedicalQuestion23D() {
	return medicalQuestion23D;
    }

    public void setMedicalQuestion23D(String medicalQuestion23D) {
	this.medicalQuestion23D = medicalQuestion23D;
    }

    public String getMedicalQuestion23E() {
	return medicalQuestion23E;
    }

    public void setMedicalQuestion23E(String medicalQuestion23E) {
	this.medicalQuestion23E = medicalQuestion23E;
    }

    public String getMedicalQuestion24() {
	return medicalQuestion24;
    }

    public void setMedicalQuestion24(String medicalQuestion24) {
	this.medicalQuestion24 = medicalQuestion24;
    }

    public String getMedicalQuestion24A() {
	return medicalQuestion24A;
    }

    public void setMedicalQuestion24A(String medicalQuestion24A) {
	this.medicalQuestion24A = medicalQuestion24A;
    }

    public String getMedicalQuestion24B() {
	return medicalQuestion24B;
    }

    public void setMedicalQuestion24B(String medicalQuestion24B) {
	this.medicalQuestion24B = medicalQuestion24B;
    }

    public String getMedicalQuestion24C() {
	return medicalQuestion24C;
    }

    public void setMedicalQuestion24C(String medicalQuestion24C) {
	this.medicalQuestion24C = medicalQuestion24C;
    }

    public String getMedicalQuestion25() {
	return medicalQuestion25;
    }

    public void setMedicalQuestion25(String medicalQuestion25) {
	this.medicalQuestion25 = medicalQuestion25;
    }

    public String getMedicalQuestion25A() {
	return medicalQuestion25A;
    }

    public void setMedicalQuestion25A(String medicalQuestion25A) {
	this.medicalQuestion25A = medicalQuestion25A;
    }

    public String getMedicalQuestion25B() {
	return medicalQuestion25B;
    }

    public void setMedicalQuestion25B(String medicalQuestion25B) {
	this.medicalQuestion25B = medicalQuestion25B;
    }

    public String getMedicalQuestion25C() {
	return medicalQuestion25C;
    }

    public void setMedicalQuestion25C(String medicalQuestion25C) {
	this.medicalQuestion25C = medicalQuestion25C;
    }

    public String getMedicalQuestion25D() {
	return medicalQuestion25D;
    }

    public void setMedicalQuestion25D(String medicalQuestion25D) {
	this.medicalQuestion25D = medicalQuestion25D;
    }

    public String getMedicalQuestion26() {
	return medicalQuestion26;
    }

    public void setMedicalQuestion26(String medicalQuestion26) {
	this.medicalQuestion26 = medicalQuestion26;
    }

    public String getMedicalQuestion26A() {
	return medicalQuestion26A;
    }

    public void setMedicalQuestion26A(String medicalQuestion26A) {
	this.medicalQuestion26A = medicalQuestion26A;
    }

    public String getMedicalQuestion27() {
	return medicalQuestion27;
    }

    public void setMedicalQuestion27(String medicalQuestion27) {
	this.medicalQuestion27 = medicalQuestion27;
    }

    public String getMedicalQuestion27A() {
	return medicalQuestion27A;
    }

    public void setMedicalQuestion27A(String medicalQuestion27A) {
	this.medicalQuestion27A = medicalQuestion27A;
    }

    public String getMedicalQuestion27B() {
	return medicalQuestion27B;
    }

    public void setMedicalQuestion27B(String medicalQuestion27B) {
	this.medicalQuestion27B = medicalQuestion27B;
    }

    public String getMedicalQuestion27C() {
	return medicalQuestion27C;
    }

    public void setMedicalQuestion27C(String medicalQuestion27C) {
	this.medicalQuestion27C = medicalQuestion27C;
    }

    public String getMedicalQuestion27D() {
	return medicalQuestion27D;
    }

    public void setMedicalQuestion27D(String medicalQuestion27D) {
	this.medicalQuestion27D = medicalQuestion27D;
    }

    public String getMedicalQuestion27E() {
	return medicalQuestion27E;
    }

    public void setMedicalQuestion27E(String medicalQuestion27E) {
	this.medicalQuestion27E = medicalQuestion27E;
    }

    public String getMedicalQuestion27F() {
	return medicalQuestion27F;
    }

    public void setMedicalQuestion27F(String medicalQuestion27F) {
	this.medicalQuestion27F = medicalQuestion27F;
    }

    public String getMedicalQuestion28() {
	return medicalQuestion28;
    }

    public void setMedicalQuestion28(String medicalQuestion28) {
	this.medicalQuestion28 = medicalQuestion28;
    }

    public String getMedicalQuestion28A() {
	return medicalQuestion28A;
    }

    public void setMedicalQuestion28A(String medicalQuestion28A) {
	this.medicalQuestion28A = medicalQuestion28A;
    }

    public String getMedicalQuestion28B() {
	return medicalQuestion28B;
    }

    public void setMedicalQuestion28B(String medicalQuestion28B) {
	this.medicalQuestion28B = medicalQuestion28B;
    }

    public String getMedicalQuestion28C() {
	return medicalQuestion28C;
    }

    public void setMedicalQuestion28C(String medicalQuestion28C) {
	this.medicalQuestion28C = medicalQuestion28C;
    }

    public String getMedicalQuestion28D() {
	return medicalQuestion28D;
    }

    public void setMedicalQuestion28D(String medicalQuestion28D) {
	this.medicalQuestion28D = medicalQuestion28D;
    }

    public String getMedicalQuestion28E() {
	return medicalQuestion28E;
    }

    public void setMedicalQuestion28E(String medicalQuestion28E) {
	this.medicalQuestion28E = medicalQuestion28E;
    }

    public String getMedicalQuestion28F() {
	return medicalQuestion28F;
    }

    public void setMedicalQuestion28F(String medicalQuestion28F) {
	this.medicalQuestion28F = medicalQuestion28F;
    }

    public String getMedicalQuestion28G() {
	return medicalQuestion28G;
    }

    public void setMedicalQuestion28G(String medicalQuestion28G) {
	this.medicalQuestion28G = medicalQuestion28G;
    }

    public String getMedicalQuestion29() {
	return medicalQuestion29;
    }

    public void setMedicalQuestion29(String medicalQuestion29) {
	this.medicalQuestion29 = medicalQuestion29;
    }

    public String getMedicalQuestion29A() {
	return medicalQuestion29A;
    }

    public void setMedicalQuestion29A(String medicalQuestion29A) {
	this.medicalQuestion29A = medicalQuestion29A;
    }

    public String getMedicalQuestion29B() {
	return medicalQuestion29B;
    }

    public void setMedicalQuestion29B(String medicalQuestion29B) {
	this.medicalQuestion29B = medicalQuestion29B;
    }

    public String getMedicalQuestion29C() {
	return medicalQuestion29C;
    }

    public void setMedicalQuestion29C(String medicalQuestion29C) {
	this.medicalQuestion29C = medicalQuestion29C;
    }

    public String getMedicalQuestion29D() {
	return medicalQuestion29D;
    }

    public void setMedicalQuestion29D(String medicalQuestion29D) {
	this.medicalQuestion29D = medicalQuestion29D;
    }

    public String getMedicalQuestion29E() {
	return medicalQuestion29E;
    }

    public void setMedicalQuestion29E(String medicalQuestion29E) {
	this.medicalQuestion29E = medicalQuestion29E;
    }

    public String getMedicalQuestion29F() {
	return medicalQuestion29F;
    }

    public void setMedicalQuestion29F(String medicalQuestion29F) {
	this.medicalQuestion29F = medicalQuestion29F;
    }

    public String getMedicalQuestion29G() {
	return medicalQuestion29G;
    }

    public void setMedicalQuestion29G(String medicalQuestion29G) {
	this.medicalQuestion29G = medicalQuestion29G;
    }

    public String getMedicalQuestion29H() {
	return medicalQuestion29H;
    }

    public void setMedicalQuestion29H(String medicalQuestion29H) {
	this.medicalQuestion29H = medicalQuestion29H;
    }

    public String getMedicalQuestion29I() {
	return medicalQuestion29I;
    }

    public void setMedicalQuestion29I(String medicalQuestion29I) {
	this.medicalQuestion29I = medicalQuestion29I;
    }

    public String getMedicalQuestion29J() {
	return medicalQuestion29J;
    }

    public void setMedicalQuestion29J(String medicalQuestion29J) {
	this.medicalQuestion29J = medicalQuestion29J;
    }

    public String getMedicalQuestion29K() {
	return medicalQuestion29K;
    }

    public void setMedicalQuestion29K(String medicalQuestion29K) {
	this.medicalQuestion29K = medicalQuestion29K;
    }

    public String getMedicalQuestion29L() {
	return medicalQuestion29L;
    }

    public void setMedicalQuestion29L(String medicalQuestion29L) {
	this.medicalQuestion29L = medicalQuestion29L;
    }

    public String getMedicalQuestion29M() {
	return medicalQuestion29M;
    }

    public void setMedicalQuestion29M(String medicalQuestion29M) {
	this.medicalQuestion29M = medicalQuestion29M;
    }

    public String getMedicalQuestion29N() {
	return medicalQuestion29N;
    }

    public void setMedicalQuestion29N(String medicalQuestion29N) {
	this.medicalQuestion29N = medicalQuestion29N;
    }

    public String getMedicalQuestion30() {
	return medicalQuestion30;
    }

    public void setMedicalQuestion30(String medicalQuestion30) {
	this.medicalQuestion30 = medicalQuestion30;
    }

    public String getMedicalQuestion30A() {
	return medicalQuestion30A;
    }

    public void setMedicalQuestion30A(String medicalQuestion30A) {
	this.medicalQuestion30A = medicalQuestion30A;
    }

    public String getMedicalQuestion30B() {
	return medicalQuestion30B;
    }

    public void setMedicalQuestion30B(String medicalQuestion30B) {
	this.medicalQuestion30B = medicalQuestion30B;
    }

    public String getMedicalQuestion30C() {
	return medicalQuestion30C;
    }

    public void setMedicalQuestion30C(String medicalQuestion30C) {
	this.medicalQuestion30C = medicalQuestion30C;
    }

    public String getMedicalQuestion30D() {
	return medicalQuestion30D;
    }

    public void setMedicalQuestion30D(String medicalQuestion30D) {
	this.medicalQuestion30D = medicalQuestion30D;
    }

    public String getMedicalQuestion30E() {
	return medicalQuestion30E;
    }

    public void setMedicalQuestion30E(String medicalQuestion30E) {
	this.medicalQuestion30E = medicalQuestion30E;
    }

    public String getMedicalQuestion30F() {
	return medicalQuestion30F;
    }

    public void setMedicalQuestion30F(String medicalQuestion30F) {
	this.medicalQuestion30F = medicalQuestion30F;
    }

    public String getMedicalQuestion30G() {
	return medicalQuestion30G;
    }

    public void setMedicalQuestion30G(String medicalQuestion30G) {
	this.medicalQuestion30G = medicalQuestion30G;
    }

    public String getAlcoholfreqQuestion37B() {
	return alcoholfreqQuestion37B;
    }

    public void setAlcoholfreqQuestion37B(String alcoholfreqQuestion37B) {
	this.alcoholfreqQuestion37B = alcoholfreqQuestion37B;
    }

    public String getAlcoholQtyQuestion37C() {
	return alcoholQtyQuestion37C;
    }

    public void setAlcoholQtyQuestion37C(String alcoholQtyQuestion37C) {
	this.alcoholQtyQuestion37C = alcoholQtyQuestion37C;
    }

    public String getAlcoholType37A() {
	return alcoholType37A;
    }

    public void setAlcoholType37A(String alcoholType37A) {
	this.alcoholType37A = alcoholType37A;
    }

    public String getCanceCareQuestion31() {
	return canceCareQuestion31;
    }

    public void setCanceCareQuestion31(String canceCareQuestion31) {
	this.canceCareQuestion31 = canceCareQuestion31;
    }

    public String getCanceCareQuestion31A() {
	return canceCareQuestion31A;
    }

    public void setCanceCareQuestion31A(String canceCareQuestion31A) {
	this.canceCareQuestion31A = canceCareQuestion31A;
    }

    public String getCanceCareQuestion32() {
	return canceCareQuestion32;
    }

    public void setCanceCareQuestion32(String canceCareQuestion32) {
	this.canceCareQuestion32 = canceCareQuestion32;
    }

    public String getCanceCareQuestion32A() {
	return canceCareQuestion32A;
    }

    public void setCanceCareQuestion32A(String canceCareQuestion32A) {
	this.canceCareQuestion32A = canceCareQuestion32A;
    }

    public String getCanceCareQuestion33() {
	return canceCareQuestion33;
    }

    public void setCanceCareQuestion33(String canceCareQuestion33) {
	this.canceCareQuestion33 = canceCareQuestion33;
    }

    public String getCanceCareQuestion33A() {
	return canceCareQuestion33A;
    }

    public void setCanceCareQuestion33A(String canceCareQuestion33A) {
	this.canceCareQuestion33A = canceCareQuestion33A;
    }

    public String getCanceCareQuestion34() {
	return canceCareQuestion34;
    }

    public void setCanceCareQuestion34(String canceCareQuestion34) {
	this.canceCareQuestion34 = canceCareQuestion34;
    }

    public String getCanceCareQuestion34A() {
	return canceCareQuestion34A;
    }

    public void setCanceCareQuestion34A(String canceCareQuestion34A) {
	this.canceCareQuestion34A = canceCareQuestion34A;
    }

    public String getCanceCareQuestion35() {
	return canceCareQuestion35;
    }

    public void setCanceCareQuestion35(String canceCareQuestion35) {
	this.canceCareQuestion35 = canceCareQuestion35;
    }

    public String getCanceCareQuestion35A() {
	return canceCareQuestion35A;
    }

    public void setCanceCareQuestion35A(String canceCareQuestion35A) {
	this.canceCareQuestion35A = canceCareQuestion35A;
    }

    public String getPosQuestion35() {
	return posQuestion35;
    }

    public void setPosQuestion35(String posQuestion35) {
	this.posQuestion35 = posQuestion35;
    }

    public String getPosQuestion35A() {
	return posQuestion35A;
    }

    public void setPosQuestion35A(String posQuestion35A) {
	this.posQuestion35A = posQuestion35A;
    }

    public String getPosQuestion36() {
	return posQuestion36;
    }

    public void setPosQuestion36(String posQuestion36) {
	this.posQuestion36 = posQuestion36;
    }

    public String getPosQuestion36A() {
	return posQuestion36A;
    }

    public void setPosQuestion36A(String posQuestion36A) {
	this.posQuestion36A = posQuestion36A;
    }

    public String getPosQuestion37() {
	return posQuestion37;
    }

    public void setPosQuestion37(String posQuestion37) {
	this.posQuestion37 = posQuestion37;
    }

    public String getPosQuestion37A() {
	return posQuestion37A;
    }

    public void setPosQuestion37A(String posQuestion37A) {
	this.posQuestion37A = posQuestion37A;
    }

    public String getPosQuestion38() {
	return posQuestion38;
    }

    public void setPosQuestion38(String posQuestion38) {
	this.posQuestion38 = posQuestion38;
    }

    public String getPosQuestion38A() {
	return posQuestion38A;
    }

    public void setPosQuestion38A(String posQuestion38A) {
	this.posQuestion38A = posQuestion38A;
    }

    public String getPosQuestion39() {
	return posQuestion39;
    }

    public void setPosQuestion39(String posQuestion39) {
	this.posQuestion39 = posQuestion39;
    }

    public String getTobaccoFreqQuestion36B() {
	return tobaccoFreqQuestion36B;
    }

    public void setTobaccoFreqQuestion36B(String tobaccoFreqQuestion36B) {
	this.tobaccoFreqQuestion36B = tobaccoFreqQuestion36B;
    }

    public String getTobaccoQtyQuestion36C() {
	return tobaccoQtyQuestion36C;
    }

    public void setTobaccoQtyQuestion36C(String tobaccoQtyQuestion36C) {
	this.tobaccoQtyQuestion36C = tobaccoQtyQuestion36C;
    }

    public String getTobaccoTypeQuestion36A() {
	return tobaccoTypeQuestion36A;
    }

    public void setTobaccoTypeQuestion36A(String tobaccoTypeQuestion36A) {
	this.tobaccoTypeQuestion36A = tobaccoTypeQuestion36A;
    }

    public String getPosAlcoholFreq() {
	return posAlcoholFreq;
    }

    public void setPosAlcoholFreq(String posAlcoholFreq) {
	this.posAlcoholFreq = posAlcoholFreq;
    }

    public String getPosAlcoholQty() {
	return posAlcoholQty;
    }

    public void setPosAlcoholQty(String posAlcoholQty) {
	this.posAlcoholQty = posAlcoholQty;
    }

    public String getPosAlcoholType() {
	return posAlcoholType;
    }

    public void setPosAlcoholType(String posAlcoholType) {
	this.posAlcoholType = posAlcoholType;
    }

    public String getPosDrug() {
	return posDrug;
    }

    public void setPosDrug(String posDrug) {
	this.posDrug = posDrug;
    }

    public String getPosDrugDetails() {
	return posDrugDetails;
    }

    public void setPosDrugDetails(String posDrugDetails) {
	this.posDrugDetails = posDrugDetails;
    }

    public String getPosTobaccoFreq() {
	return posTobaccoFreq;
    }

    public void setPosTobaccoFreq(String posTobaccoFreq) {
	this.posTobaccoFreq = posTobaccoFreq;
    }

    public String getPosTobaccoQty() {
	return posTobaccoQty;
    }

    public void setPosTobaccoQty(String posTobaccoQty) {
	this.posTobaccoQty = posTobaccoQty;
    }

    public String getPosTobaccoType() {
	return posTobaccoType;
    }

    public void setPosTobaccoType(String posTobaccoType) {
	this.posTobaccoType = posTobaccoType;
    }

    public String getPosTravelDetails() {
	return posTravelDetails;
    }

    public void setPosTravelDetails(String posTravelDetails) {
	this.posTravelDetails = posTravelDetails;
    }

    public String getPosTravelQuestion() {
	return posTravelQuestion;
    }

    public void setPosTravelQuestion(String posTravelQuestion) {
	this.posTravelQuestion = posTravelQuestion;
    }

    public String getAcrQuestion() {
        return acrQuestion;
    }

    public void setAcrQuestion(String acrQuestion) {
        this.acrQuestion = acrQuestion;
    }

    public String getPraCountry() {
        return praCountry;
    }

    public void setPraCountry(String praCountry) {
        this.praCountry = praCountry;
    }

    public String getNriQuestResidingCountry() {
        return nriQuestResidingCountry;
    }

    public void setNriQuestResidingCountry(String nriQuestResidingCountry) {
        this.nriQuestResidingCountry = nriQuestResidingCountry;
    }

    public String getProposerGender() {
        return proposerGender;
    }

    public void setProposerGender(String proposerGender) {
        this.proposerGender = proposerGender;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getCovidQuest() {
		return covidQuest;
	}

	public void setCovidQuest(String covidQuest) {
		this.covidQuest = covidQuest;
	}

    public String getInitiativeType() {
        return initiativeType;
    }

    public void setInitiativeType(String initiativeType) {
        this.initiativeType = initiativeType;
    }

    public String getIsVaccinated() {
		return isVaccinated;
	}

	public void setIsVaccinated(String isVaccinated) {
		this.isVaccinated = isVaccinated;
	}

    public String getCiRiderSa() { return ciRiderSa; }

    public void setCiRiderSa(String ciRiderSa) { this.ciRiderSa = ciRiderSa; }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getProposerIncome() {
        return proposerIncome;
    }

    public void setProposerIncome(String proposerIncome) {
        this.proposerIncome = proposerIncome;
    }

    public String getInsuredsuc() {
        return insuredsuc;
    }

    public void setInsuredsuc(String insuredsuc) {
        this.insuredsuc = insuredsuc;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4() {
        return tag4;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5() {
        return tag5;
    }

    public void setTag5(String tag5) {
        this.tag5 = tag5;
    }

    public String getCibilIncome() {
        return cibilIncome;
    }

    public void setCibilIncome(String cibilIncome) {
        this.cibilIncome = cibilIncome;
    }

    public String getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(String cibilScore) {
        this.cibilScore = cibilScore;
    }

    public String getCustomerClasificationCode() {
        return customerClasificationCode;
    }

    public void setCustomerClasificationCode(String customerClasificationCode) {
        this.customerClasificationCode = customerClasificationCode;
    }

    public String getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(String agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getCraCity() {
        return craCity;
    }

    public void setCraCity(String craCity) {
        this.craCity = craCity;
    }

    public String getAppSignDate() {
        return appSignDate;
    }

    public void setAppSignDate(String appSignDate) {
        this.appSignDate = appSignDate;
    }

    public String getCraPinCode() {
        return craPinCode;
    }

    public void setCraPinCode(String craPinCode) {
        this.craPinCode = craPinCode;
    }

    public String getChannelGoCode() {
        return channelGoCode;
    }

    public void setChannelGoCode(String channelGoCode) {
        this.channelGoCode = channelGoCode;
    }

    public String getMsa() {
        return msa;
    }

    public void setMsa(String msa) {
        this.msa = msa;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public String getTermPlusRiderSumAssured() {
        return termPlusRiderSumAssured;
    }

    public void setTermPlusRiderSumAssured(String termPlusRiderSumAssured) {
        this.termPlusRiderSumAssured = termPlusRiderSumAssured;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getNatureOfJob() {
        return natureOfJob;
    }

    public void setNatureOfJob(String natureOfJob) {
        this.natureOfJob = natureOfJob;
    }

    public String getInsuredIncome() {
        return insuredIncome;
    }

    public void setInsuredIncome(String insuredIncome) {
        this.insuredIncome = insuredIncome;
    }

    public String getSmokingQuantity() {
        return smokingQuantity;
    }

    public void setSmokingQuantity(String smokingQuantity) {
        this.smokingQuantity = smokingQuantity;
    }

    public String getTobaccoQuantity() {
        return tobaccoQuantity;
    }

    public void setTobaccoQuantity(String tobaccoQuantity) {
        this.tobaccoQuantity = tobaccoQuantity;
    }

    public String getWineQuantity() {
        return wineQuantity;
    }

    public void setWineQuantity(String wineQuantity) {
        this.wineQuantity = wineQuantity;
    }

    public String getBeerQuantity() {
        return beerQuantity;
    }

    public void setBeerQuantity(String beerQuantity) {
        this.beerQuantity = beerQuantity;
    }

    public String getLiquorQuantity() {
        return liquorQuantity;
    }

    public void setLiquorQuantity(String liquorQuantity) {
        this.liquorQuantity = liquorQuantity;
    }

    public String getPrevPolDecPost() {
        return prevPolDecPost;
    }

    public void setPrevPolDecPost(String prevPolDecPost) {
        this.prevPolDecPost = prevPolDecPost;
    }

    public String getWeightChange() {
        return weightChange;
    }

    public void setWeightChange(String weightChange) {
        this.weightChange = weightChange;
    }

    public String getSmokingFrequency() {
        return smokingFrequency;
    }

    public void setSmokingFrequency(String smokingFrequency) {
        this.smokingFrequency = smokingFrequency;
    }

    public String getTobaccoFrequency() {
        return tobaccoFrequency;
    }

    public void setTobaccoFrequency(String tobaccoFrequency) {
        this.tobaccoFrequency = tobaccoFrequency;
    }

    public String getWineFrequency() {
        return wineFrequency;
    }

    public void setWineFrequency(String wineFrequency) {
        this.wineFrequency = wineFrequency;
    }

    public String getBeerFrequency() {
        return beerFrequency;
    }

    public void setBeerFrequency(String beerFrequency) {
        this.beerFrequency = beerFrequency;
    }

    public String getLiquorFrequency() {
        return liquorFrequency;
    }

    public void setLiquorFrequency(String liquorFrequency) {
        this.liquorFrequency = liquorFrequency;
    }

    public String getProposerAge() {
        return proposerAge;
    }

    public void setProposerAge(String proposerAge) {
        this.proposerAge = proposerAge;
    }

    public String getProposerNationality() {
        return proposerNationality;
    }

    public void setProposerNationality(String proposerNationality) {
        this.proposerNationality = proposerNationality;
    }

    public String getPraCity() {
        return praCity;
    }

    public void setPraCity(String praCity) {
        this.praCity = praCity;
    }

    public String getPraPincode() {
        return praPincode;
    }

    public void setPraPincode(String praPincode) {
        this.praPincode = praPincode;
    }

    public String getExistingCustomer() {
        return existingCustomer;
    }

    public void setExistingCustomer(String existingCustomer) {
        this.existingCustomer = existingCustomer;
    }

    public String getProposerOccupation() {
        return proposerOccupation;
    }

    public void setProposerOccupation(String proposerOccupation) {
        this.proposerOccupation = proposerOccupation;
    }

    public String getProposerEducation() {
        return proposerEducation;
    }

    public void setProposerEducation(String proposerEducation) {
        this.proposerEducation = proposerEducation;
    }

    public String getPolicyAfyp() {
        return policyAfyp;
    }

    public void setPolicyAfyp(String policyAfyp) {
        this.policyAfyp = policyAfyp;
    }

    public String getClientAfyp() {
        return clientAfyp;
    }

    public void setClientAfyp(String clientAfyp) {
        this.clientAfyp = clientAfyp;
    }

    public String getPolicyCiDdRiderSA() {
        return policyCiDdRiderSA;
    }

    public void setPolicyCiDdRiderSA(String policyCiDdRiderSA) {
        this.policyCiDdRiderSA = policyCiDdRiderSA;
    }

    public String getClientCiDdRiderSA() {
        return clientCiDdRiderSA;
    }

    public void setClientCiDdRiderSA(String clientCiDdRiderSA) {
        this.clientCiDdRiderSA = clientCiDdRiderSA;
    }

    public String getPolicyAcoRiderSA() {
        return policyAcoRiderSA;
    }

    public void setPolicyAcoRiderSA(String policyAcoRiderSA) {
        this.policyAcoRiderSA = policyAcoRiderSA;
    }

    public String getClientAcoRiderSA() {
        return clientAcoRiderSA;
    }

    public void setClientAcoRiderSA(String clientAcoRiderSA) {
        this.clientAcoRiderSA = clientAcoRiderSA;
    }

    public String getPolicyWopRiderSA() {
        return policyWopRiderSA;
    }

    public void setPolicyWopRiderSA(String policyWopRiderSA) {
        this.policyWopRiderSA = policyWopRiderSA;
    }

    public String getBse500() {
        return bse500;
    }

    public void setBse500(String bse500) {
        this.bse500 = bse500;
    }

    public String getParentsInsurance() {
        return parentsInsurance;
    }

    public void setParentsInsurance(String parentsInsurance) {
        this.parentsInsurance = parentsInsurance;
    }

    public String getSpouseInsurance() {
        return spouseInsurance;
    }

    public void setSpouseInsurance(String spouseInsurance) {
        this.spouseInsurance = spouseInsurance;
    }

    public String getSmokerCode() {
        return smokerCode;
    }

    public void setSmokerCode(String smokerCode) {
        this.smokerCode = smokerCode;
    }

    public String getBankRelationSince() {
        return bankRelationSince;
    }

    public void setBankRelationSince(String bankRelationSince) {
        this.bankRelationSince = bankRelationSince;
    }

    public String getProposerOrganisationType() {
        return proposerOrganisationType;
    }

    public void setProposerOrganisationType(String proposerOrganisationType) {
        this.proposerOrganisationType = proposerOrganisationType;
    }

    public String getSpouseIncome() {
        return spouseIncome;
    }

    public void setSpouseIncome(String spouseIncome) {
        this.spouseIncome = spouseIncome;
    }

    public String getParentsIncome() {
        return parentsIncome;
    }

    public void setParentsIncome(String parentsIncome) {
        this.parentsIncome = parentsIncome;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(String riskScore) {
        this.riskScore = riskScore;
    }

    public String getPfMedicalQTag() {
        return pfMedicalQTag;
    }

    public void setPfMedicalQTag(String pfMedicalQTag) {
        this.pfMedicalQTag = pfMedicalQTag;
    }

    public String getIsDiabetic() {
        return isDiabetic;
    }

    public void setIsDiabetic(String isDiabetic) {
        this.isDiabetic = isDiabetic;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
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


    @Override
    public String toString() {

           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }


	return "Payload [objectiveOfInsurance=" + objectiveOfInsurance + ", age=" + age + ", nomineeRelationship=" + nomineeRelationship + ", nationality="
		+ nationality + ", countryName=" + countryName + ", insuredMaritalStatus=" + insuredMaritalStatus + ", education=" + education
		+ ", airForceRelatedQues1=" + airForceRelatedQues1 + ", airForceRelatedQues2=" + airForceRelatedQues2 + ", airForceRelatedQues3="
		+ airForceRelatedQues3 + ", airForceRelatedQues4=" + airForceRelatedQues4 + ", airForceRelatedQues5=" + airForceRelatedQues5
		+ ", armedForceRelatedQues1=" + armedForceRelatedQues1 + ", armedForceRelatedQues2=" + armedForceRelatedQues2 + ", armedForceRelatedQues3="
		+ armedForceRelatedQues3 + ", armedForceRelatedQues4=" + armedForceRelatedQues4 + ", divingRelatedQues1=" + divingRelatedQues1
		+ ", divingRelatedQues2=" + divingRelatedQues2 + ", divingRelatedQues3=" + divingRelatedQues3 + ", divingRelatedQues4=" + divingRelatedQues4
		+ ", divingRelatedQues5=" + divingRelatedQues5 + ", navyAndMerchaentRelatedQues1=" + navyAndMerchaentRelatedQues1
		+ ", navyAndMerchaentRelatedQues2=" + navyAndMerchaentRelatedQues2 + ", navyAndMerchaentRelatedQues3=" + navyAndMerchaentRelatedQues3
		+ ", navyAndMerchaentRelatedQues4=" + navyAndMerchaentRelatedQues4 + ", navyAndMerchaentRelatedQues5=" + navyAndMerchaentRelatedQues5
		+ ", miningRelatedQues1=" + miningRelatedQues1 + ", miningRelatedQues2=" + miningRelatedQues2 + ", oilAndNaturalGasRelatedQues1="
		+ oilAndNaturalGasRelatedQues1 + ", insuredIndustry=" + insuredIndustry + ", fsa=" + fsa + ", insuredOccupation=" + insuredOccupation
		+ ", gender=" + gender + ", companyType=" + companyType + ", isPep=" + isPep + ", payorOrNot=" + payorOrNot + ", relationshipWithPayor="
		+ relationshipWithPayor + ", existingInsurance=" + existingInsurance + ", offeredAtModifiedTerms=" + offeredAtModifiedTerms
		+ ", hobbyAsPartOfJob=" + hobbyAsPartOfJob + ", hobbyAsPassion=" + hobbyAsPassion + ", hobbyAsHolidayActivity=" + hobbyAsHolidayActivity
		+ ", avocation=" + avocation + ", convicted=" + convicted + ", familyHistoryAdverse=" + familyHistoryAdverse + ", insuredHeight="
		+ insuredHeight + ", insuredWeight=" + insuredWeight + ", pregnancyComplicationDetails=" + pregnancyComplicationDetails + ", isPregnant="
		+ isPregnant + ", monthsOfPregnancy=" + monthsOfPregnancy + ", pregnancyComplications=" + pregnancyComplications + ", medicalQuestion19="
		+ medicalQuestion19 + ", medicalQuestion20=" + medicalQuestion20 + ", medicalQuestion20A=" + medicalQuestion20A + ", medicalQuestion20B="
		+ medicalQuestion20B + ", medicalQuestion20C=" + medicalQuestion20C + ", medicalQuestion20D=" + medicalQuestion20D + ", medicalQuestion21="
		+ medicalQuestion21 + ", medicalQuestion21A=" + medicalQuestion21A + ", medicalQuestion21B=" + medicalQuestion21B + ", medicalQuestion22="
		+ medicalQuestion22 + ", medicalQuestion22A=" + medicalQuestion22A + ", medicalQuestion22B=" + medicalQuestion22B + ", medicalQuestion22C="
		+ medicalQuestion22C + ", medicalQuestion22D=" + medicalQuestion22D + ", medicalQuestion23=" + medicalQuestion23 + ", medicalQuestion23A="
		+ medicalQuestion23A + ", medicalQuestion23B=" + medicalQuestion23B + ", medicalQuestion23C=" + medicalQuestion23C + ", medicalQuestion23D="
		+ medicalQuestion23D + ", medicalQuestion23E=" + medicalQuestion23E + ", medicalQuestion24=" + medicalQuestion24 + ", medicalQuestion24A="
		+ medicalQuestion24A + ", medicalQuestion24B=" + medicalQuestion24B + ", medicalQuestion24C=" + medicalQuestion24C + ", medicalQuestion25="
		+ medicalQuestion25 + ", medicalQuestion25A=" + medicalQuestion25A + ", medicalQuestion25B=" + medicalQuestion25B + ", medicalQuestion25C="
		+ medicalQuestion25C + ", medicalQuestion25D=" + medicalQuestion25D + ", medicalQuestion26=" + medicalQuestion26 + ", medicalQuestion26A="
		+ medicalQuestion26A + ", medicalQuestion27=" + medicalQuestion27 + ", medicalQuestion27A=" + medicalQuestion27A + ", medicalQuestion27B="
		+ medicalQuestion27B + ", medicalQuestion27C=" + medicalQuestion27C + ", medicalQuestion27D=" + medicalQuestion27D + ", medicalQuestion27E="
		+ medicalQuestion27E + ", medicalQuestion27F=" + medicalQuestion27F + ", medicalQuestion28=" + medicalQuestion28 + ", medicalQuestion28A="
		+ medicalQuestion28A + ", medicalQuestion28B=" + medicalQuestion28B + ", medicalQuestion28C=" + medicalQuestion28C + ", medicalQuestion28D="
		+ medicalQuestion28D + ", medicalQuestion28E=" + medicalQuestion28E + ", medicalQuestion28F=" + medicalQuestion28F + ", medicalQuestion28G="
		+ medicalQuestion28G + ", medicalQuestion29=" + medicalQuestion29 + ", medicalQuestion29A=" + medicalQuestion29A + ", medicalQuestion29B="
		+ medicalQuestion29B + ", medicalQuestion29C=" + medicalQuestion29C + ", medicalQuestion29D=" + medicalQuestion29D + ", medicalQuestion29E="
		+ medicalQuestion29E + ", medicalQuestion29F=" + medicalQuestion29F + ", medicalQuestion29G=" + medicalQuestion29G + ", medicalQuestion29H="
		+ medicalQuestion29H + ", medicalQuestion29I=" + medicalQuestion29I + ", medicalQuestion29J=" + medicalQuestion29J + ", medicalQuestion29K="
		+ medicalQuestion29K + ", medicalQuestion29L=" + medicalQuestion29L + ", medicalQuestion29M=" + medicalQuestion29M + ", medicalQuestion29N="
		+ medicalQuestion29N + ", medicalQuestion30=" + medicalQuestion30 + ", medicalQuestion30A=" + medicalQuestion30A + ", medicalQuestion30B="
		+ medicalQuestion30B + ", medicalQuestion30C=" + medicalQuestion30C + ", medicalQuestion30D=" + medicalQuestion30D + ", medicalQuestion30E="
		+ medicalQuestion30E + ", medicalQuestion30F=" + medicalQuestion30F + ", medicalQuestion30G=" + medicalQuestion30G + ", alcoholfreqQuestion37B="
		+ alcoholfreqQuestion37B + ", alcoholQtyQuestion37C=" + alcoholQtyQuestion37C + ", alcoholType37A=" + alcoholType37A + ", canceCareQuestion31="
		+ canceCareQuestion31 + ", canceCareQuestion31A=" + canceCareQuestion31A + ", canceCareQuestion32=" + canceCareQuestion32
		+ ", canceCareQuestion32A=" + canceCareQuestion32A + ", canceCareQuestion33=" + canceCareQuestion33 + ", canceCareQuestion33A="
		+ canceCareQuestion33A + ", canceCareQuestion34=" + canceCareQuestion34 + ", canceCareQuestion34A=" + canceCareQuestion34A
		+ ", canceCareQuestion35=" + canceCareQuestion35 + ", canceCareQuestion35A=" + canceCareQuestion35A + ", posQuestion35=" + posQuestion35
		+ ", posQuestion35A=" + posQuestion35A + ", posQuestion36=" + posQuestion36 + ", posQuestion36A=" + posQuestion36A + ", posQuestion37="
		+ posQuestion37 + ", posQuestion37A=" + posQuestion37A + ", posQuestion38=" + posQuestion38 + ", posQuestion38A=" + posQuestion38A
		+ ", posQuestion39=" + posQuestion39 + ", tobaccoFreqQuestion36B=" + tobaccoFreqQuestion36B + ", tobaccoQtyQuestion36C=" + tobaccoQtyQuestion36C
		+ ", tobaccoTypeQuestion36A=" + tobaccoTypeQuestion36A + ", posAlcoholFreq=" + posAlcoholFreq + ", posAlcoholQty=" + posAlcoholQty
		+ ", posAlcoholType=" + posAlcoholType + ", posDrug=" + posDrug + ", posDrugDetails=" + posDrugDetails + ", posTobaccoFreq=" + posTobaccoFreq
		+ ", posTobaccoQty=" + posTobaccoQty + ", posTobaccoType=" + posTobaccoType + ", posTravelDetails=" + posTravelDetails + ", posTravelQuestion="
		+ posTravelQuestion + ", acrQuestion=" + acrQuestion+ ", praCountry=" + praCountry+ ", nriQuestResidingCountry="
        + nriQuestResidingCountry+ ", proposerGender=" + proposerGender+ ", productCode=" + productCode+ ", covidQuest=" + covidQuest+", initiativeType=" + initiativeType
        +", isVaccinated=" + isVaccinated +", ciRiderSa=" + ciRiderSa +", agentCode=" + agentCode +", proposerIncome=" + proposerIncome +", insuredsuc=" + insuredsuc +", formType=" + formType
            +", tag1=" + tag1 +", tag2=" + tag2+", tag3=" + tag3 +", tag4=" + tag4 +", tag5=" + tag5 +", cibilIncome=" + cibilIncome +", cibilScore=" + cibilScore +", customerClasificationCode=" + customerClasificationCode
            +", agentLevel=" + agentLevel +", craCity=" + craCity +", appSignDate=" + appSignDate +", craPinCode=" + craPinCode +", channelGoCode=" + channelGoCode
            +", msa=" + msa +", sumAssured=" + sumAssured +", birthDate=" + birthDate +", birthMonth=" + birthMonth +", natureOfJob=" + natureOfJob
            +", insuredIncome=" + insuredIncome +", videoPosv=" + videoPosv +"]";
    }

}
