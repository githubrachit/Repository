
package com.mli.mpro.proposal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "companyName", "firstName", "lastName", "gender", "dob", "fatherName", "motherName", "residentialStatus", "nationalityDetails", "marriageDetails",
	"education", "occupation", "annualIncome", "areBothAddressSame", "address", "name", "qualification", "dobProofType", "nationality",
	"relationshipWithProposer", "relationshipWithProposerWhenOther", "preferredLanguageOfCommunication" })
public class BasicDetails {

    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("firstName")
    private String firstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("middleName")
    private String middleName;
    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("lastName")
    private String lastName;
    @Sensitive(MaskType.GENDER)
    @JsonProperty("gender")
    private String gender;
    @Sensitive(MaskType.DOB)
    @JsonProperty("dob")
    private Date dob;
    @Sensitive(MaskType.NAME)
    @JsonProperty("fatherName")
    private String fatherName;
    @Sensitive(MaskType.NAME)
    @JsonProperty("motherName")
    private String motherName;
    @JsonProperty("residentialStatus")
    private String residentialStatus;
    @JsonProperty("nationalityDetails")
    private NationalityDetails nationalityDetails;
    @JsonProperty("marriageDetails")
    private MarriageDetails marriageDetails;
    @JsonProperty("education")
    private String education;
    @JsonProperty("occupation")
    private String occupation;
    @Sensitive(MaskType.AMOUNT)
    @JsonProperty("annualIncome")
    private String annualIncome;
    @JsonProperty("areBothAddressSame")
    private boolean areBothAddressSame;
    @JsonProperty("address")
    private List<Address> address = null;
    @JsonProperty("qualification")
    private String qualification;
    @JsonProperty("dobProofType")
    private String dobProofType;
    @JsonProperty("relationshipWithProposer")
    private String relationshipWithProposer;
    @JsonProperty("relationshipWithProposerWhenOther")
    private String relationshipWithProposerWhenOther;
    @JsonProperty("preferredLanguageOfCommunication")
    private String preferredLanguageOfCommunication;
    @JsonProperty("personalIdentification")
    private PersonalIdentification personalIdentification;
    //NEORW-:NR-353 adding extar fields
    private String isCorporateCustomer;
    private String isEmployee;
    private String isExistingCustomer;
    private String isExistingCustomerComboSale;
    @Sensitive(MaskType.POLICY_NUM)
    private String existingCustomerPolicyNo;
    @Sensitive(MaskType.POLICY_NUM)
    private String existingCustomerPolicyNoComboSale;
    @JsonProperty("residenceForTax")
    private String residenceForTax;
    //NEORW
    @JsonProperty("smoker")
    private String smoker;
    private String sourceOfFunds;
    @JsonProperty("dateOfIncorporation")
    private Date dateOfIncorporation;
	@JsonProperty("companyName")
	private String companyName;
	@JsonProperty("companyType")
	private String companyType;
	@JsonProperty("organizationType")
	private String organizationType;
	@JsonProperty("relationshipWithCompany")
	private String relationshipWithCompany;
	@JsonProperty("relationshipWithCompanyWhenOther")
	private String relationshipWithCompanyWhenOther;
	@JsonProperty("directorIdentificationNumber")
	private String directorIdentificationNumber;
	@JsonProperty("isNPSCustomer")
	private String isNPSCustomer;
    @JsonProperty("pranNumber")
    private String pranNumber;
    @JsonProperty("payUIncome")
    private String payUIncome;


  /**
     * No args constructor for use in serialization
     * 
     */
    public BasicDetails() {
    }

   

    public BasicDetails(String companyName, String firstName, String middleName, String lastName, String gender, Date dob, String fatherName, String motherName,
	    String residentialStatus, NationalityDetails nationalityDetails, MarriageDetails marriageDetails, String education, String occupation,
	    String annualIncome, boolean areBothAddressSame, List<Address> address, String qualification, String dobProofType, String relationshipWithProposer,
	    String relationshipWithProposerWhenOther, String preferredLanguageOfCommunication, PersonalIdentification personalIdentification,
        String isExistingCustomer, String existingCustomerPolicyNo,String isExistingCustomerComboSale,String existingCustomerPolicyNoComboSale) {
	super();
    this.companyName = companyName;
	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	this.gender = gender;
	this.dob = dob;
	this.fatherName = fatherName;
	this.motherName = motherName;
	this.residentialStatus = residentialStatus;
	this.nationalityDetails = nationalityDetails;
	this.marriageDetails = marriageDetails;
	this.education = education;
	this.occupation = occupation;
	this.annualIncome = annualIncome;
	this.areBothAddressSame = areBothAddressSame;
	this.address = address;
	this.qualification = qualification;
	this.dobProofType = dobProofType;
	this.relationshipWithProposer = relationshipWithProposer;
	this.relationshipWithProposerWhenOther = relationshipWithProposerWhenOther;
	this.preferredLanguageOfCommunication = preferredLanguageOfCommunication;
	this.personalIdentification = personalIdentification;
	this.isExistingCustomer = isExistingCustomer;
    this.isExistingCustomerComboSale = isExistingCustomerComboSale;
	this.existingCustomerPolicyNo = existingCustomerPolicyNo;
    this.existingCustomerPolicyNoComboSale = existingCustomerPolicyNoComboSale;
    }

    //FUL2-156443- Populating incorrectly nameOfEmployer value (declared setter & getter method)
    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

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

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public Date getDob() {
	return dob;
    }

    public void setDob(Date dob) {
	this.dob = dob;
    }

    public String getFatherName() {
	return fatherName;
    }

    public void setFatherName(String fatherName) {
	this.fatherName = fatherName;
    }

    public String getMotherName() {
	return motherName;
    }

    public void setMotherName(String motherName) {
	this.motherName = motherName;
    }

    public String getResidentialStatus() {
	return residentialStatus;
    }

    public void setResidentialStatus(String residentialStatus) {
	this.residentialStatus = residentialStatus;
    }

    public NationalityDetails getNationalityDetails() {
	return nationalityDetails;
    }

    public void setNationalityDetails(NationalityDetails nationalityDetails) {
	this.nationalityDetails = nationalityDetails;
    }

    public MarriageDetails getMarriageDetails() {
	return marriageDetails;
    }

    public void setMarriageDetails(MarriageDetails marriageDetails) {
	this.marriageDetails = marriageDetails;
    }

    public String getEducation() {
	return education;
    }

    public void setEducation(String education) {
	this.education = education;
    }

    public String getOccupation() {
	return occupation;
    }

    public void setOccupation(String occupation) {
	this.occupation = occupation;
    }

    public String getAnnualIncome() {
	return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
	this.annualIncome = annualIncome;
    }

    public boolean isAreBothAddressSame() {
	return areBothAddressSame;
    }

    public void setAreBothAddressSame(boolean areBothAddressSame) {
	this.areBothAddressSame = areBothAddressSame;
    }

    public List<Address> getAddress() {
	return address;
    }

    public void setAddress(List<Address> address) {
	this.address = address;
    }

    public String getQualification() {
	return qualification;
    }

    public void setQualification(String qualification) {
	this.qualification = qualification;
    }

    public String getDobProofType() {
	return dobProofType;
    }

    public void setDobProofType(String dobProofType) {
	this.dobProofType = dobProofType;
    }

    public String getRelationshipWithProposer() {
	return relationshipWithProposer;
    }

    public void setRelationshipWithProposer(String relationshipWithProposer) {
	this.relationshipWithProposer = relationshipWithProposer;
    }

    public String getRelationshipWithProposerWhenOther() {
	return relationshipWithProposerWhenOther;
    }

    public void setRelationshipWithProposerWhenOther(String relationshipWithProposerWhenOther) {
	this.relationshipWithProposerWhenOther = relationshipWithProposerWhenOther;
    }

    public String getPreferredLanguageOfCommunication() {
	return preferredLanguageOfCommunication;
    }

    public void setPreferredLanguageOfCommunication(String preferredLanguageOfCommunication) {
	this.preferredLanguageOfCommunication = preferredLanguageOfCommunication;
    }
    

    public PersonalIdentification getPersonalIdentification() {
        return personalIdentification;
    }

    public void setPersonalIdentification(PersonalIdentification personalIdentification) {
        this.personalIdentification = personalIdentification;
    }

    public String getIsExistingCustomer() { return isExistingCustomer; }

    public void setIsExistingCustomer(String isExistingCustomer) {
        this.isExistingCustomer = isExistingCustomer;
    }

    public String getIsExistingCustomerComboSale() {
        return isExistingCustomerComboSale;
    }

    public void setIsExistingCustomerComboSale(String isExistingCustomerComboSale) {
        this.isExistingCustomerComboSale = isExistingCustomerComboSale;
    }

    public String getExistingCustomerPolicyNo() { return existingCustomerPolicyNo; }

    public String getExistingCustomerPolicyNoComboSale() {
        return existingCustomerPolicyNoComboSale;
    }

    public void setExistingCustomerPolicyNoComboSale(String existingCustomerPolicyNoComboSale) {
        this.existingCustomerPolicyNoComboSale = existingCustomerPolicyNoComboSale;
    }

    public void setExistingCustomerPolicyNo(String existingCustomerPolicyNo) {
        this.existingCustomerPolicyNo = existingCustomerPolicyNo;
    }

    public String getIsCorporateCustomer() {
        return isCorporateCustomer;
    }

    public void setIsCorporateCustomer(String isCorporateCustomer) {
        this.isCorporateCustomer = isCorporateCustomer;
    }

    public String getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(String isEmployee) {
        this.isEmployee = isEmployee;
    }

    public String getResidenceForTax() {
        return residenceForTax;
    }

    public void setResidenceForTax(String residenceForTax) {
        this.residenceForTax = residenceForTax;
    }

    public String getSmoker() { return smoker; }

    public void setSmoker(String smoker) { this.smoker = smoker; }

    public String getSourceOfFunds() {
		return sourceOfFunds;
	}

	public void setSourceOfFunds(String sourceOfFunds) {
		this.sourceOfFunds = sourceOfFunds;
	}

	public Date getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(Date dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}


	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getRelationshipWithCompany() {
		return relationshipWithCompany;
	}

	public void setRelationshipWithCompany(String relationshipWithCompany) {
		this.relationshipWithCompany = relationshipWithCompany;
	}

	public String getRelationshipWithCompanyWhenOther() {
		return relationshipWithCompanyWhenOther;
	}

	public void setRelationshipWithCompanyWhenOther(String relationshipWithCompanyWhenOther) {
		this.relationshipWithCompanyWhenOther = relationshipWithCompanyWhenOther;
	}

	public String getDirectorIdentificationNumber() {
		return directorIdentificationNumber;
	}

	public void setDirectorIdentificationNumber(String directorIdentificationNumber) {
		this.directorIdentificationNumber = directorIdentificationNumber;
	}
	
    public String getIsNPSCustomer() {
		return isNPSCustomer;
	}

	public void setIsNPSCustomer(String isNPSCustomer) {
		this.isNPSCustomer = isNPSCustomer;
	}

    public String getPranNumber() {
        return pranNumber;
    }

    public void setPranNumber(String pranNumber) {
        this.pranNumber = pranNumber;
    }

    public String getPayUIncome() {
        return payUIncome;
    }

    public void setPayUIncome(String payUIncome) {
        this.payUIncome = payUIncome;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return new StringJoiner(", ", BasicDetails.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("middleName='" + middleName + "'")
                .add("lastName='" + lastName + "'")
                .add("gender='" + gender + "'")
                .add("dob=" + dob)
                .add("fatherName='" + fatherName + "'")
                .add("motherName='" + motherName + "'")
                .add("residentialStatus='" + residentialStatus + "'")
                .add("nationalityDetails=" + nationalityDetails)
                .add("marriageDetails=" + marriageDetails)
                .add("education='" + education + "'")
                .add("occupation='" + occupation + "'")
                .add("annualIncome='" + annualIncome + "'")
                .add("areBothAddressSame=" + areBothAddressSame)
                .add("address=" + address)
                .add("qualification='" + qualification + "'")
                .add("dobProofType='" + dobProofType + "'")
                .add("relationshipWithProposer='" + relationshipWithProposer + "'")
                .add("relationshipWithProposerWhenOther='" + relationshipWithProposerWhenOther + "'")
                .add("preferredLanguageOfCommunication='" + preferredLanguageOfCommunication + "'")
                .add("personalIdentification=" + personalIdentification)
                .add("isCorporateCustomer='" + isCorporateCustomer + "'")
                .add("isEmployee='" + isEmployee + "'")
                .add("isExistingCustomer='" + isExistingCustomer + "'")
                .add("isExistingCustomerComboSale='" + isExistingCustomerComboSale + "'")
                .add("existingCustomerPolicyNo='" + existingCustomerPolicyNo + "'")
                .add("existingCustomerPolicyNoComboSale='" + existingCustomerPolicyNoComboSale + "'")
                .add("residenceForTax='" + residenceForTax + "'")
                .add("smoker='" + smoker + "'")
                .add("sourceOfFunds='" + sourceOfFunds + "'")
                .add("dateOfIncorporation='" + dateOfIncorporation + "'")
                .add("companyName='" + companyName + "'")
                .add("organizationType='" + organizationType + "'")
                .add("companyType=" + companyType)
                .add("relationshipWithCompany='" + relationshipWithCompany + "'")
                .add("relationshipWithCompanyWhenOther='" + relationshipWithCompanyWhenOther + "'")
                .add("directorIdentificationNumber='" + directorIdentificationNumber + "'")
                .add("isNPSCustomer='" + isNPSCustomer + "'")
                .toString();
    }
}