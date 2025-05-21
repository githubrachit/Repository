package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.models.IdentityProofDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CkycMapper {

    private static final Logger logger = LoggerFactory.getLogger(CkycMapper.class);

    /**
     * Mapping data from CKYC to Context DataMap
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setDataOfCkycDocument(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START CKYC Data population for transactionId {}",proposalDetails.getTransactionId());
	Map<String, Object> dataVariables = new HashMap<>();

	HashMap<String, Object> communicationAddress = new HashMap<>();
	String proposalNumber = "";
	String title = "";
	String firstName = "";
	String middleName = "";
	String lastName = "";
	String motherName = "";
	String fatherName = "";
	String dob = "";
	String formattedGender = "";
	String nationality = "";
	String residentialStatus = "";
	String occupation = "";
	String maritalStatus = "";
	String ckycNumber = "";
	String applicationType = "";
	String annualIncome = "";
	IdentityProofDetails identityProofDetails = null;
	try {
	    String formType = Utility.getFormType(proposalDetails);
		logger.info("Initiating processing proposalDetails for CKYC Document for transactionId {} formType {} ",proposalDetails.getTransactionId(),formType);
	    boolean proposerFormFlag = StringUtils.equalsIgnoreCase(formType, AppConstants.SELF)  || Utility.schemeBCase(formType, proposalDetails.getApplicationDetails().getSchemeType()) ? true : false;
	    proposalNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
	    List<PartyInformation> partyInfoList = proposalDetails.getPartyInformation();
	    if (!CollectionUtils.isEmpty(partyInfoList)) {
			PartyInformation partyInformation  = Utility.getPartyInfoWrtFormType(proposalDetails);
		BasicDetails basicDetails = partyInformation.getBasicDetails();
		List<Address> addressList = basicDetails.getAddress();
		PersonalIdentification proposerPersonalIdentification = partyInformation.getPersonalIdentification();
		List<Phone> phoneList = proposerPersonalIdentification.getPhone();

		// Setting Communication Address
		if (!CollectionUtils.isEmpty(addressList)) {
		    AddressDetails proposerCurrentAddressDetails = addressList.get(0).getAddressDetails();

		    communicationAddress.put("houseNo", proposerCurrentAddressDetails.getHouseNo());
		    communicationAddress.put("area", proposerCurrentAddressDetails.getArea());
		    communicationAddress.put("landmark", proposerCurrentAddressDetails.getLandmark());
		    communicationAddress.put("city", proposerCurrentAddressDetails.getCity());
		    communicationAddress.put("state", proposerCurrentAddressDetails.getState());
		    communicationAddress.put("pinCode", proposerCurrentAddressDetails.getPinCode());
		    communicationAddress.put("village", proposerCurrentAddressDetails.getVillage());
		    communicationAddress.put("country", proposerCurrentAddressDetails.getCountry());
		    communicationAddress.put("email", proposerPersonalIdentification.getEmail());
		    communicationAddress.put("adressProofNumber", AppConstants.AADHAAR.equalsIgnoreCase(partyInformation.getBasicDetails().getAddress().get(0).getProofType())
			    ? Utility.maskAadhaarNumber(partyInformation.getPersonalIdentification().getAadhaarDetails().getAadhaarNumber()) : addressList.get(0).getProofNumber());
		    communicationAddress.put("addressProofExpiryDate", Utility.dateFormatterWithTimeZone(addressList.get(0)
					.getProofExpiryDate(), AppConstants.YYYY_MM_DD_HH_MM_SS_HYPHEN_Z, AppConstants.DD_MM_YYYY_SLASH));
		    if (!CollectionUtils.isEmpty(phoneList)) {
			communicationAddress.put("mobileNumber1", proposerPersonalIdentification.getPhone().get(0).getPhoneNumber());
		    }
		    communicationAddress.put("communicationAddressProofType", addressList.get(0).getProofType());

		} else {
		    logger.info("Communication Address data not found!");
		}

		title = Utility.getTitle(basicDetails.getGender());
		firstName = basicDetails.getFirstName();
		middleName = basicDetails.getMiddleName();
		lastName = basicDetails.getLastName();
		motherName = basicDetails.getMotherName();
		fatherName = basicDetails.getFatherName();
		nationality = basicDetails.getNationalityDetails().getNationality();
		residentialStatus = basicDetails.getResidentialStatus();
		occupation = basicDetails.getOccupation();
		maritalStatus = basicDetails.getMarriageDetails().getMaritalStatus();
		annualIncome = basicDetails.getAnnualIncome();
		dob = Utility.dateFormatter(basicDetails.getDob());
		formattedGender = Utility.getGender(basicDetails.getGender());
		identityProofDetails = getIdentityProof(partyInformation);
	    }

		//FUL2-116448-Updating ckyc and application type
		if (setApplicationType(proposalDetails)) {
			ckycNumber = proposalDetails.getCkycDetails().getCkycNumber();
			applicationType = "Old";
		} else
			applicationType = "NEW";

	    // Putting data in DataMap
	    dataVariables.put(AppConstants.PANDOB_PROPOSALNUMBER, StringUtils.isNotBlank(proposalNumber) ? proposalNumber : AppConstants.BLANK);
	    dataVariables.put("formType", formType);
	    dataVariables.put("proposerFormFlag", proposerFormFlag);
	    dataVariables.put("title", title);
	    dataVariables.put("firstName", firstName);
	    dataVariables.put("middleName", middleName);
	    dataVariables.put("lastName", lastName);
	    dataVariables.put("motherName", motherName);
	    dataVariables.put("fatherName", fatherName);
	    dataVariables.put("dob", dob);
	    dataVariables.put("gender", formattedGender);
	    dataVariables.put("nationality", nationality);
	    dataVariables.put("residentialStatus", residentialStatus);
	    dataVariables.put("occupation", occupation);
	    dataVariables.put("maritalStatus", maritalStatus);
	    dataVariables.put("annualIncome", annualIncome);
	    dataVariables.put("communicationAddress", communicationAddress);
	    dataVariables.put("identityProofDetails", identityProofDetails);
		dataVariables.put("applicationType", applicationType);
		dataVariables.put("ckycNumber", ckycNumber);
		if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.PHYSICAL_JOURNEY_FEATURE_FLAG) && 
				AppConstants.YES.equalsIgnoreCase(proposalDetails.getApplicationDetails().getPhysicalJourneyEnabled())) {
			dataVariables.put("date", Utility.dateFormatter(LocalDate.now()));
		} else {
			dataVariables.put("date", Utility.dateFormatter(proposalDetails.getPosvDetails().getPosvStatus().getSubmittedOTPDate()));
		}

	} catch (Exception ex) {
	    logger.error("Data addition failed for CKYC Document:", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	Context ckycDetailsCxt = new Context();
	ckycDetailsCxt.setVariables(dataVariables);
	logger.info("END CKYC Data population");
	return ckycDetailsCxt;
    }

    /**
     * Function to get proof of Identity
     * 
     * @return
     */
    private static IdentityProofDetails getIdentityProof(PartyInformation partyInformation) {
	logger.info("Fetching Identity Proof and setting the extracted data in IdentityProofDetails");
	IdentityProofDetails proofDetails = new IdentityProofDetails();

	/*FUL2-11558 Changes of cKYC for individual */
	Address craDetails = partyInformation.getBasicDetails().getAddress().get(0);
	String country = craDetails.getAddressDetails().getCountry();
	if (AppConstants.INDIA_COUNTRY.equalsIgnoreCase(country)) {
	    proofDetails.setProofName(partyInformation.getBasicDetails().getAddress().get(0).getProofType());
	    proofDetails.setProofNumber(
		    AppConstants.AADHAAR.equalsIgnoreCase(partyInformation.getBasicDetails().getAddress().get(0).getProofType())
			    ? Utility.maskAadhaarNumber(partyInformation.getPersonalIdentification().getAadhaarDetails().getAadhaarNumber()) : craDetails.getProofNumber());
	    proofDetails.setProofExpiryDate(Utility.dateFormatterWithTimeZone(craDetails.getProofExpiryDate(), AppConstants.YYYY_MM_DD_HH_MM_SS_HYPHEN_Z, AppConstants.DD_MM_YYYY_SLASH));
	} else {
		NRIDetails nriDetails = partyInformation.getBasicDetails().getNationalityDetails().getNriDetails();
	    proofDetails.setProofName("PASSPORT");
	    proofDetails.setProofNumber(nriDetails.getPassportNumber());
	    proofDetails.setProofExpiryDate(Utility.dateFormatter(nriDetails.getPassportExpiryDate()));

	}
	return proofDetails;
    }
	public boolean setApplicationType(ProposalDetails proposalDetails) {
		return !ObjectUtils.isEmpty(proposalDetails.getCkycDetails()) && !StringUtils.isEmpty(proposalDetails.getCkycDetails().getCkycNumber());
	}
}
