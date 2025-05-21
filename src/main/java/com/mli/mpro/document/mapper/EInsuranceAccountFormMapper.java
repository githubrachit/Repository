package com.mli.mpro.document.mapper;

import com.mli.mpro.proposal.models.PanDetails;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.service.impl.DocumentHelper;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.Address;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PersonalIdentification;
import com.mli.mpro.proposal.models.Phone;
import com.mli.mpro.proposal.models.ProposalDetails;

@Service
public class EInsuranceAccountFormMapper {
    private static final Logger logger = LoggerFactory.getLogger(EInsuranceAccountFormMapper.class);

    @Autowired
    private DocumentHelper documentHelper;
    /**
     * Mapping data from Form60 to Context DataMap
     * 
     * @param proposalDetails
     * @return Context
     * @throws UserHandledException
     */
    public Context setDataOfEiaDocument(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START EIA Data population");
	Map<String, Object> dataVariables = new HashMap<>();
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	boolean isNEOOrAggregator = false;
	try {
		//NEORW-173: this will check that incoming request is from NEO or Aggregator
		isNEOOrAggregator = proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
				|| proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR);
	    List<PartyInformation> partyInformationList = proposalDetails.getPartyInformation();
	    HashMap<String, String> permanentAddress = new HashMap<>();
	    HashMap<String, String> currentAddress = new HashMap<>();
	    String mobileNumber1 = "";
	    String mobileNumber2 = "";
	    String landlineNumber = "";
	    String title = "";
	    String firstName = "";
	    String middleName = "";
	    String lastName = "";
	    String fullName = "";
	    String gender = "";
	    String email = "";
	    String motherName = "";
	    String fatherName = "";
	    String dob = "";
	    String nationality = "";
	    String occupation = "";
	    String annualIncome = "";
	    String panNumber = "";
	    String residentialStatus = "";
	    String proposerImageURL=AppConstants.DUMMY_BLANK_IMAGE_PATH;
	    String uidNumber="";
			if (!isNEOOrAggregator) {
				String imageType = Utility.imageType(proposalDetails);
				proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType,
						proposalDetails.getChannelDetails().getChannel(),
						proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
			}

			String eiaType = getEiaType(proposalDetails.getEmploymentDetails().isEIANumberExist(), "OLD", "NEW");

		String repositoryName = "";
	    if (isNEOOrAggregator) {
	    	repositoryName = proposalDetails.getEmploymentDetails().isEIAExist()
					? Utility.getFullRepositoryName(proposalDetails.getEmploymentDetails().getExistingEIANumberRepositoryName())
				    : Utility.getFullRepositoryName(proposalDetails.getEmploymentDetails().getPreferredInsuranceRepositoryName());
		} else {
			repositoryName = getExistingEIANumberRepositoryName(proposalDetails);
		}
	    String repositoryNumber = getExistingEIANumber(proposalDetails);
		String policyNumber = (null != proposalDetails.getApplicationDetails())
				    ? proposalDetails.getApplicationDetails().getPolicyNumber()
				    : "";
		uidNumber = getUidNumber(proposalDetails, uidNumber);
		if (!CollectionUtils.isEmpty(partyInformationList) ) {
			logger.info("EIA: Getting basicDetails");
			BasicDetails basicDetails = null;
			if (Utility.isApplicationIsForm2(proposalDetails) && Utility.isChannelNeoOrAggregator(proposalDetails)) {
				basicDetails = partyInformationList.get(1).getBasicDetails();
			} else {
				basicDetails = partyInformationList.get(0).getBasicDetails();
			}
		title = Utility.getTitle(basicDetails.getGender());
		gender = Utility.getGender(basicDetails.getGender());
		firstName = basicDetails.getFirstName();
		middleName = Utility.nullSafe(basicDetails.getMiddleName());
		lastName = basicDetails.getLastName();
		fullName = String.join(AppConstants.WHITE_SPACE, firstName, middleName, lastName);
		motherName = basicDetails.getMotherName();
		fatherName = basicDetails.getFatherName();
		nationality = basicDetails.getNationalityDetails().getNationality();
		occupation = basicDetails.getOccupation();
		annualIncome = basicDetails.getAnnualIncome();
		residentialStatus = basicDetails.getResidentialStatus();
		dob = Utility.dateFormatter(basicDetails.getDob());

		PersonalIdentification proposerPersonalIdentification = partyInformationList.get(0).getPersonalIdentification();
		List<Phone> proposerPhoneList = proposerPersonalIdentification.getPhone();
		if (!CollectionUtils.isEmpty(proposerPhoneList)) {
		    mobileNumber1 = String.join(AppConstants.BLANK, Utility.emptyIfNull(proposerPhoneList.get(0).getStdIsdCode()), proposerPhoneList.get(0).getPhoneNumber());
		}
		if (!CollectionUtils.isEmpty(proposerPhoneList) && proposerPhoneList.size() >= 2) {
		    mobileNumber2 = String.join(AppConstants.BLANK, Utility.emptyIfNull(proposerPhoneList.get(1).getStdIsdCode()), proposerPhoneList.get(1).getPhoneNumber());
		}
		if (!CollectionUtils.isEmpty(proposerPhoneList) && proposerPhoneList.size() >= 3) {
		    landlineNumber = String.join(AppConstants.BLANK, Utility.emptyIfNull(proposerPhoneList.get(2).getStdIsdCode()), proposerPhoneList.get(2).getPhoneNumber());
		}

		List<Address> proposerAddressList = basicDetails.getAddress();
		// Proposer Current Address
		if (!CollectionUtils.isEmpty(proposerAddressList)) {
		    AddressDetails proposerCurrentAddress = proposerAddressList.get(0).getAddressDetails();

		    currentAddress.put("houseNo", proposerCurrentAddress.getHouseNo());
		    currentAddress.put("area", proposerCurrentAddress.getArea());
		    currentAddress.put("landmark", proposerCurrentAddress.getLandmark());
		    currentAddress.put("city", proposerCurrentAddress.getCity());
		    currentAddress.put("state", proposerCurrentAddress.getState());
		    currentAddress.put("pinCode", proposerCurrentAddress.getPinCode());
		    currentAddress.put("village", proposerCurrentAddress.getVillage());
		    currentAddress.put("country", proposerCurrentAddress.getCountry());
		    currentAddress.put("communicationAddressProofType", proposerAddressList.get(0).getProofType());

		    currentAddress.put("mobileNumber1", mobileNumber1);
		    currentAddress.put("mobileNumber2", mobileNumber2);
		    currentAddress.put("landlineNumber", landlineNumber);
		}
		// Proposer Permanent Address
		if (!CollectionUtils.isEmpty(proposerAddressList) && proposerAddressList.size() >= 2) {
		    AddressDetails proposerPermanentAddress = proposerAddressList.get(1).getAddressDetails();
		    permanentAddress.put("houseNo", proposerPermanentAddress.getHouseNo());
		    permanentAddress.put("area", proposerPermanentAddress.getArea());
		    permanentAddress.put("landmark", proposerPermanentAddress.getLandmark());
		    permanentAddress.put("city", proposerPermanentAddress.getCity());
		    permanentAddress.put("state", proposerPermanentAddress.getState());
		    permanentAddress.put("pinCode", proposerPermanentAddress.getPinCode());
		    permanentAddress.put("village", proposerPermanentAddress.getVillage());
		    permanentAddress.put("country", proposerPermanentAddress.getCountry());
		    permanentAddress.put("permanentAddressProofType", proposerAddressList.get(1).getProofType());

		    permanentAddress.put("mobileNumber1", mobileNumber1);
		    permanentAddress.put("mobileNumber2", mobileNumber2);
		    permanentAddress.put("landlineNumber", landlineNumber);
		}
		email = proposerPersonalIdentification.getEmail();
			if (Utility.isChannelNeoOrAggregator(proposalDetails) && Utility.isApplicationIsForm2(proposalDetails)) {
				panNumber = Optional.ofNullable(partyInformationList.get(1).getPersonalIdentification())
						.map(PersonalIdentification::getPanDetails).map(PanDetails::getPanNumber)
						.orElse(AppConstants.BLANK);
			} else {
				panNumber = proposerPersonalIdentification.getPanDetails().getPanNumber();
			}
	    }

	    logger.info("Setting data in dataMap");
	    // Setting data in DataMap
	    dataVariables.put("title", title);
	    dataVariables.put("firstName", firstName);
	    dataVariables.put("middleName", middleName);
	    dataVariables.put("lastName", lastName);
	    dataVariables.put("fullName", fullName);	 
	    dataVariables.put("gender", gender);
	    dataVariables.put("motherName", motherName);
	    dataVariables.put("fatherName", fatherName);
	    dataVariables.put("dob", dob);
	    dataVariables.put("nationality", nationality);
	    dataVariables.put("occupation", occupation);
	    dataVariables.put("annualIncome", annualIncome);
	    dataVariables.put("panNumber", panNumber);
	    dataVariables.put("uidNumber", uidNumber);
	    dataVariables.put("eiaType", eiaType);
	    dataVariables.put("repositoryName", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(repositoryName), repositoryName, AppConstants.NA));
	    dataVariables.put("repositoryNumber", Utility.evaluateConditionalOperation(StringUtils.isNotBlank(repositoryNumber), repositoryNumber, AppConstants.NA));
	    dataVariables.put("residentialStatus", residentialStatus);
	    dataVariables.put("permanentAddress", permanentAddress);
	    dataVariables.put("currentAddress", currentAddress);
	    dataVariables.put("email", email);
	    dataVariables.put("proposerImageURL", proposerImageURL);
	    if (isNEOOrAggregator
				&& Objects.nonNull(proposalDetails.getApplicationDetails())
				&& Objects.nonNull(proposalDetails.getPaymentDetails())
                && !CollectionUtils.isEmpty(proposalDetails.getPaymentDetails().getReceipt())) {

				String otpConfirmationDate = Utility.getDateOnTheBasisOfRateChange(proposalDetails, false);
			dataVariables.put("date", otpConfirmationDate);
		} else {
	    dataVariables.put("date", LocalDate.now().format(dateFormatter).toString());
		}
	    dataVariables.put("policyNumber",policyNumber);
		dataVariables.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
	} catch (Exception ex) {
	    logger.error("Data addition failed for Proposal Form Document:", ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	Context eiaDetailsCxt = new Context();
	eiaDetailsCxt.setVariables(dataVariables);
	logger.info("END EIA Data population");
	return eiaDetailsCxt;
    }

	private String getUidNumber(ProposalDetails proposalDetails, String uidNumber) {
		if(proposalDetails.getForm60Details()!=null && proposalDetails.getForm60Details().getIdentityProofNumber()!=null) {
			uidNumber = proposalDetails.getForm60Details().getIdentityProofNumber();
		}
		return uidNumber;
	}

	private String getExistingEIANumberRepositoryName(ProposalDetails proposalDetails) {
    	String result = AppConstants.BLANK;
    	if(proposalDetails.getEmploymentDetails() != null){
			return (StringUtils.isNotBlank(proposalDetails.getEmploymentDetails().getExistingEIANumberRepositoryName()))
					? proposalDetails.getEmploymentDetails().getExistingEIANumberRepositoryName()
					: proposalDetails.getEmploymentDetails().getPreferredInsuranceRepositoryName();
		}
		return result;
	}
	private String getExistingEIANumber(ProposalDetails proposalDetails) {
		String result = AppConstants.BLANK;
		if(proposalDetails.getEmploymentDetails() != null){
			return (StringUtils.isNotBlank(proposalDetails.getEmploymentDetails().getExistingEIANumber()))
					? proposalDetails.getEmploymentDetails().getExistingEIANumber()
					: proposalDetails.getEmploymentDetails().getNewEIANumber();
		}
		return result;
	}

    private String getEiaType(boolean eiaNumberExist, String old, String aNew) {
        return eiaNumberExist ? old : aNew;
    }
}
