
package com.mli.mpro.proposal.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "relationshipWithProposer", "relationshipOthers", "salutation", "firstName", "middleName", "lastName", "fatherOrHusbandName","accountForNominee", "dob",
        "gender", "annualIncome", " organisationType", "otherOccupationDetails", "industryDetails", "nameOfEmployer", "natureOfDuties", "placeOfPosting",
        "rank", "branchOfArmedForces", "areYouInvolvedinhazardousActivities", "panOrForm60", "panNumber", "section139ACheck", "appointeeDetails",
        "percentageShare" })
public class PartyDetails {

    @JsonProperty("relationshipWithProposer")
    private String relationshipWithProposer;
    @JsonProperty("relationshipOthers")
    private String relationshipOthers;
    @JsonProperty("salutation")
    private String salutation;
    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("firstName")
    private String firstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("middleName")
    private String middleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("lastName")
    private String lastName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("fatherOrHusbandName")
    private String fatherOrHusbandName;
    @Sensitive(MaskType.BANK_ACC_NUM)
    @JsonProperty("accountForNominee")
    private String accountForNominee;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private Date dob;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualIncome")
    private String annualIncome;
    @JsonProperty("organisationType")
    private String organisationType;
    @JsonProperty("otherOccupationDetails")
    private String otherOccupationDetails;
    @JsonProperty("industryDetails")
    private IndustryDetails industryDetails;
    @Sensitive(MaskType.NAME)
    @JsonProperty("nameOfEmployer")
    private String nameOfEmployer;
    @JsonProperty("jobTitle")
    private String jobTitle;
    @JsonProperty("natureOfDuties")
    private String natureOfDuties;
    @JsonProperty("placeOfPosting")
    private String placeOfPosting;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("branchOfArmedForces")
    private String branchOfArmedForces;
    @JsonProperty("areYouInvolvedinhazardousActivities")
    private String areYouInvolvedinhazardousActivities;
    @JsonProperty("hazardousActivitiesDetails")
    private String hazardousActivitiesDetails;
    @JsonProperty("panOrForm60")
    private String panOrForm60;
    @Sensitive(MaskType.PAN_NUM)
    @JsonProperty("panNumber")
    private String panNumber;
    @JsonProperty("section139ACheck")
    private String section139ACheck;
    @JsonProperty("reasonForNomination")
    private String reasonForNomination;
    @JsonProperty("appointeeDetails")
    private AppointeeDetails appointeeDetails;
    @JsonProperty("percentageShare")
    private int percentageShare;
    @Sensitive(MaskType.NAME)
    @JsonProperty("nomineeChildName")
    private String nomineeChildName;
    @Sensitive(MaskType.DOB)
    @JsonProperty("nomineeChildDob")
    private Date nomineeChildDob;
    @JsonProperty("nomineeNationality")
    private NationalityDetails nomineeNationality;
    @JsonProperty("nomineeAddress")
    private List<Address> nomineeAddress;
    @JsonProperty("nomineePhoneDetails")
    private List<Phone> nomineePhoneDetails;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("nomineeEmail")
    private String nomineeEmail;
    @JsonProperty("nomineeNameFlag")
    private String nomineeNameFlag;
    @JsonProperty("nomineeRegistrationNumber")
    private String nomineeRegistrationNumber;
    @JsonProperty("nomineeBankDetails")
    private Bank nomineeBankDetails;
    @JsonProperty("relationshipWithAccHolder")
    private String relationshipWithAccHolder;
    @JsonProperty("nomineePanDetails")
    private PanDetails nomineePanDetails;
    @JsonProperty("nomineeIdentityDetails")
    private Form60Details nomineeIdentityDetails;
    @JsonProperty("nomineeType")
    private String nomineeType;
    @JsonProperty("jobType")
    private String jobType;
    @Sensitive(MaskType.EMAIL)
    @JsonProperty("officialEmail")
    private String officialEmail;
    @JsonProperty("tenureOfJob")
    private String tenureOfJob;
    @JsonProperty("specifyDutiesType")
    private String specifyDutiesType;
    @JsonProperty("specifyDutiesTypeOthers")
    private String specifyDutiesTypeOthers;
    @JsonProperty("objectiveOfInsurance")
    private String objectiveOfInsurance;


    /**
     * No args constructor for use in serialization
     *
     */
    public PartyDetails() {
    }

   

    public String getObjectiveOfInsurance() {
        return objectiveOfInsurance;
    }

    public void setObjectiveOfInsurance(String objectiveOfInsurance) {
        this.objectiveOfInsurance = objectiveOfInsurance;
    }

    public PartyDetails(String relationshipWithProposer, String relationshipOthers, String salutation, String firstName, String middleName, String lastName,
	    String fatherOrHusbandName, String accountForNominee, Date dob, String gender, String annualIncome, String organisationType,
	    String otherOccupationDetails, IndustryDetails industryDetails, String nameOfEmployer, String jobTitle, String natureOfDuties,
	    String placeOfPosting, String rank, String branchOfArmedForces, String areYouInvolvedinhazardousActivities, String hazardousActivitiesDetails,
	    String panOrForm60, String panNumber, String section139aCheck, String reasonForNomination, AppointeeDetails appointeeDetails, int percentageShare,
	    String nomineeChildName, Date nomineeChildDob, NationalityDetails nomineeNationality, List<Address> nomineeAddress, List<Phone> nomineePhoneDetails,
	    String nomineeEmail, String nomineeNameFlag, String nomineeRegistrationNumber, Bank nomineeBankDetails, String relationshipWithAccHolder,
	    PanDetails nomineePanDetails, Form60Details nomineeIdentityDetails, String nomineeType, String jobType, String officialEmail, String tenureOfJob, String specifyDutiesType, String specifyDutiesTypeOthers) {
	super();
	this.relationshipWithProposer = relationshipWithProposer;
	this.relationshipOthers = relationshipOthers;
	this.salutation = salutation;
	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	this.fatherOrHusbandName = fatherOrHusbandName;
	this.accountForNominee = accountForNominee;
	this.dob = dob;
	this.gender = gender;
	this.annualIncome = annualIncome;
	this.organisationType = organisationType;
	this.otherOccupationDetails = otherOccupationDetails;
	this.industryDetails = industryDetails;
	this.nameOfEmployer = nameOfEmployer;
	this.jobTitle = jobTitle;
	this.natureOfDuties = natureOfDuties;
	this.placeOfPosting = placeOfPosting;
	this.rank = rank;
	this.branchOfArmedForces = branchOfArmedForces;
	this.areYouInvolvedinhazardousActivities = areYouInvolvedinhazardousActivities;
	this.hazardousActivitiesDetails = hazardousActivitiesDetails;
	this.panOrForm60 = panOrForm60;
	this.panNumber = panNumber;
	section139ACheck = section139aCheck;
	this.reasonForNomination = reasonForNomination;
	this.appointeeDetails = appointeeDetails;
	this.percentageShare = percentageShare;
	this.nomineeChildName = nomineeChildName;
	this.nomineeChildDob = nomineeChildDob;
	this.nomineeNationality = nomineeNationality;
	this.nomineeAddress = nomineeAddress;
	this.nomineePhoneDetails = nomineePhoneDetails;
	this.nomineeEmail = nomineeEmail;
	this.nomineeNameFlag = nomineeNameFlag;
	this.nomineeRegistrationNumber = nomineeRegistrationNumber;
	this.nomineeBankDetails = nomineeBankDetails;
	this.relationshipWithAccHolder = relationshipWithAccHolder;
	this.nomineePanDetails = nomineePanDetails;
	this.nomineeIdentityDetails = nomineeIdentityDetails;
	this.nomineeType = nomineeType;
	this.jobType = jobType;
	this.officialEmail = officialEmail;
	this.tenureOfJob = tenureOfJob;
	this.specifyDutiesType = specifyDutiesType;
	this.specifyDutiesTypeOthers = specifyDutiesTypeOthers;
    }








    @JsonProperty("relationshipWithProposer")
    public String getRelationshipWithProposer() {
        return relationshipWithProposer;
    }

    @JsonProperty("relationshipWithProposer")
    public void setRelationshipWithProposer(String relationshipWithProposer) {
        this.relationshipWithProposer = relationshipWithProposer;
    }

    @JsonProperty("relationshipOthers")
    public String getRelationshipOthers() {
        return relationshipOthers;
    }

    @JsonProperty("relationshipOthers")
    public void setRelationshipOthers(String relationshipOthers) {
        this.relationshipOthers = relationshipOthers;
    }

    @JsonProperty("salutation")
    public String getSalutation() {
        return salutation;
    }

    @JsonProperty("salutation")
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("middleName")
    public String getMiddleName() {
        return middleName;
    }

    @JsonProperty("middleName")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("fatherOrHusbandName")
    public String getFatherOrHusbandName() {
        return fatherOrHusbandName;
    }

    @JsonProperty("fatherOrHusbandName")
    public void setFatherOrHusbandName(String fatherOrHusbandName) {
        this.fatherOrHusbandName = fatherOrHusbandName;
    }
    
    @JsonProperty("accountForNominee")
    public String getAccountForNominee() {
        return accountForNominee;
    }

    @JsonProperty("accountForNominee")
    public void setAccountForNominee(String accountForNominee) {
        this.accountForNominee = accountForNominee;
    }   

    @JsonProperty("dob")
    public Date getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(Date dob) {
        this.dob = dob;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("appointeeDetails")
    public AppointeeDetails getAppointeeDetails() {
        return appointeeDetails;
    }

    @JsonProperty("appointeeDetails")
    public void setAppointeeDetails(AppointeeDetails appointeeDetails) {
        this.appointeeDetails = appointeeDetails;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getOrganisationType() {
        return organisationType;
    }

    public void setOrganisationType(String organisationType) {
        this.organisationType = organisationType;
    }

    public String getOtherOccupationDetails() {
        return otherOccupationDetails;
    }

    public void setOtherOccupationDetails(String occupationOthers) {
        this.otherOccupationDetails = occupationOthers;
    }

    public IndustryDetails getIndustryDetails() {
        return industryDetails;
    }

    public void setIndustryDetails(IndustryDetails industryDetails) {
        this.industryDetails = industryDetails;
    }

    public String getNameOfEmployer() {
        return nameOfEmployer;
    }

    public void setNameOfEmployer(String nameOfEmployer) {
        this.nameOfEmployer = nameOfEmployer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getNatureOfDuties() {
        return natureOfDuties;
    }

    public void setNatureOfDuties(String natureOfDuties) {
        this.natureOfDuties = natureOfDuties;
    }

    public String getPlaceOfPosting() {
        return placeOfPosting;
    }

    public void setPlaceOfPosting(String placeOfPosting) {
        this.placeOfPosting = placeOfPosting;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBranchOfArmedForces() {
        return branchOfArmedForces;
    }

    public void setBranchOfArmedForces(String branchOfArmedForces) {
        this.branchOfArmedForces = branchOfArmedForces;
    }

    public String getAreYouInvolvedinhazardousActivities() {
        return areYouInvolvedinhazardousActivities;
    }

    public void setAreYouInvolvedinhazardousActivities(String areYouInvolvedinhazardousActivities) {
        this.areYouInvolvedinhazardousActivities = areYouInvolvedinhazardousActivities;
    }

    public String getHazardousActivitiesDetails() {
        return hazardousActivitiesDetails;
    }

    public void setHazardousActivitiesDetails(String hazardousActivitiesDetails) {
        this.hazardousActivitiesDetails = hazardousActivitiesDetails;
    }

    public String getPanOrForm60() {
        return panOrForm60;
    }

    public void setPanOrForm60(String panOrForm60) {
        this.panOrForm60 = panOrForm60;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getSection139ACheck() {
        return section139ACheck;
    }

    public void setSection139ACheck(String section139aCheck) {
        section139ACheck = section139aCheck;
    }

    public String getReasonForNomination() {
        return reasonForNomination;
    }

    public void setReasonForNomination(String reasonForNomination) {
        this.reasonForNomination = reasonForNomination;
    }

    @JsonProperty("percentageShare")
    public int getPercentageShare() {
        return percentageShare;
    }

    @JsonProperty("percentageShare")
    public void setPercentageShare(int percentageShare) {
        this.percentageShare = percentageShare;
    }

    public String getNomineeChildName() {
        return nomineeChildName;
    }

    public void setNomineeChildName(String nomineeChildName) {
        this.nomineeChildName = nomineeChildName;
    }

    public Date getNomineeChildDob() {
        return nomineeChildDob;
    }

    public void setNomineeChildDob(Date nomineeChildDob) {
        this.nomineeChildDob = nomineeChildDob;
    }

    public NationalityDetails getNomineeNationality() {
        return nomineeNationality;
    }

    public void setNomineeNationality(NationalityDetails nomineeNationality) {
        this.nomineeNationality = nomineeNationality;
    }

    public List<Address> getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(List<Address> nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public List<Phone> getNomineePhoneDetails() {
        return nomineePhoneDetails;
    }

    public void setNomineePhoneDetails(List<Phone> nomineePhoneDetails) {
        this.nomineePhoneDetails = nomineePhoneDetails;
    }

    public String getNomineeEmail() {
        return nomineeEmail;
    }

    public void setNomineeEmail(String nomineeEmail) {
        this.nomineeEmail = nomineeEmail;
    }

    public Bank getNomineeBankDetails() {
        return nomineeBankDetails;
    }

    public void setNomineeBankDetails(Bank nomineeBankDetails) {
        this.nomineeBankDetails = nomineeBankDetails;
    }

    public PanDetails getNomineePanDetails() {
        return nomineePanDetails;
    }

    public void setNomineePanDetails(PanDetails nomineePanDetails) {
        this.nomineePanDetails = nomineePanDetails;
    }

    public Form60Details getNomineeIdentityDetails() {
        return nomineeIdentityDetails;
    }

    public void setNomineeIdentityDetails(Form60Details nomineeIdentityDetails) {
        this.nomineeIdentityDetails = nomineeIdentityDetails;
    }
    

    public String getNomineeNameFlag() {
        return nomineeNameFlag;
    }



    public void setNomineeNameFlag(String nomineeNameFlag) {
        this.nomineeNameFlag = nomineeNameFlag;
    }



    public String getNomineeRegistrationNumber() {
        return nomineeRegistrationNumber;
    }



    public void setNomineeRegistrationNumber(String nomineeRegistrationNumber) {
        this.nomineeRegistrationNumber = nomineeRegistrationNumber;
    }



    public String getRelationshipWithAccHolder() {
        return relationshipWithAccHolder;
    }



    public void setRelationshipWithAccHolder(String relationshipWithAccHolder) {
        this.relationshipWithAccHolder = relationshipWithAccHolder;
    }
    



    public String getNomineeType() {
        return nomineeType;
    }



    public void setNomineeType(String nomineeType) {
        this.nomineeType = nomineeType;
    }

    public String getJobType() { return jobType; }

    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getOfficialEmail() { return officialEmail; }

    public void setOfficialEmail(String officialEmail) { this.officialEmail = officialEmail;}

    public String getTenureOfJob() { return tenureOfJob; }

    public void setTenureOfJob(String tenureOfJob) { this.tenureOfJob = tenureOfJob;}

    public String getSpecifyDutiesType() {
        return specifyDutiesType;
    }

    public void setSpecifyDutiesType(String specifyDutiesType) {
        this.specifyDutiesType = specifyDutiesType;
    }

    public String getSpecifyDutiesTypeOthers() {
        return specifyDutiesTypeOthers;
    }

    public void setSpecifyDutiesTypeOthers(String specifyDutiesTypeOthers) {
        this.specifyDutiesTypeOthers = specifyDutiesTypeOthers;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
	return "PartyDetails [relationshipWithProposer=" + relationshipWithProposer + ", relationshipOthers=" + relationshipOthers + ", salutation="
		+ salutation + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", fatherOrHusbandName="
		+ fatherOrHusbandName + ", accountForNominee=" + accountForNominee + ", dob=" + dob + ", gender=" + gender + ", annualIncome=" + annualIncome
		+ ", organisationType=" + organisationType + ", otherOccupationDetails=" + otherOccupationDetails + ", industryDetails=" + industryDetails
		+ ", nameOfEmployer=" + nameOfEmployer + ", jobTitle=" + jobTitle + ", natureOfDuties=" + natureOfDuties + ", placeOfPosting=" + placeOfPosting
		+ ", rank=" + rank + ", branchOfArmedForces=" + branchOfArmedForces + ", areYouInvolvedinhazardousActivities="
		+ areYouInvolvedinhazardousActivities + ", hazardousActivitiesDetails=" + hazardousActivitiesDetails + ", panOrForm60=" + panOrForm60
		+ ", panNumber=" + panNumber + ", section139ACheck=" + section139ACheck + ", reasonForNomination=" + reasonForNomination + ", appointeeDetails="
		+ appointeeDetails + ", percentageShare=" + percentageShare + ", nomineeChildName=" + nomineeChildName + ", nomineeChildDob=" + nomineeChildDob
		+ ", nomineeNationality=" + nomineeNationality + ", nomineeAddress=" + nomineeAddress + ", nomineePhoneDetails=" + nomineePhoneDetails
		+ ", nomineeEmail=" + nomineeEmail + ", nomineeNameFlag=" + nomineeNameFlag + ", nomineeRegistrationNumber=" + nomineeRegistrationNumber
		+ ", nomineeBankDetails=" + nomineeBankDetails + ", relationshipWithAccHolder=" + relationshipWithAccHolder + ", nomineePanDetails="
		+ nomineePanDetails + ", nomineeIdentityDetails=" + nomineeIdentityDetails + ", nomineeType=" + nomineeType + ", jobType="
        + jobType + ", officialEmail=" + officialEmail + ", tenureOfJob=" + tenureOfJob + ", specifyDutiesType=" + specifyDutiesType + ", , specifyDutiesTypeOthers=" + specifyDutiesTypeOthers + "]";
    }



   



    
}
