package com.mli.mpro.document.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import com.google.common.base.Strings;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.ProposalNomineeDetails;
import com.mli.mpro.document.utils.DateTimeUtils;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.Address;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.AppointeeDetails;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.EmploymentDetails;
import com.mli.mpro.proposal.models.IndustryDetails;
import com.mli.mpro.proposal.models.IndustryInfo;
import com.mli.mpro.proposal.models.PartyDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.Phone;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import com.mli.mpro.utils.Utility.NAME_PARTS;

/**
 * Mapper class for Personal Details section in Proposal Form Document for HSA Products
 * @author SayanGhosh
 */

@Service
public class HSAPersonalDetailsMapper {
	private static final Logger logger = LoggerFactory.getLogger(HSAPersonalDetailsMapper.class);

	// string constants
	private static final String TRANSGENDER = "Transgender";
	public static final String DD_MM_YYYY_CONST = "dd/MM/yyyy";
	private static final String OTHERS = "Others";
	private static Map<String,String> insuranceRepositoryMap = new HashMap<>();
	static {
		insuranceRepositoryMap.put("1","NSDL Database Management Limited");
		insuranceRepositoryMap.put("2","Central Insurance Repository Limited");
		insuranceRepositoryMap.put("3","SHCIL Projects Limited");
		insuranceRepositoryMap.put("4","Karvy Insurance Repository Limited");
		insuranceRepositoryMap.put("5","CAMS Repository Services Limited");
	}

	public Context mapDataForPersonalDetails(ProposalDetails proposalDetails) throws UserHandledException {
		long transactionId = proposalDetails.getTransactionId();
		logger.info("START mapDataForPersonalDetails for transactionId {}", transactionId);
		Map<String, Object> dataVariables = new HashMap<>();
		// List of variables used in the template
		Date insuredDob = null;
		Date proposerDob = null;
		String formType = StringUtils.EMPTY;
		String formattedInsuredDob = AppConstants.NA;
		String insuredAnnualIncome = AppConstants.NA;
		String insuredCountryByTaxLaw = AppConstants.NA;
		String insuredEducation = AppConstants.NA;
		String insuredEmployerName = AppConstants.NA;
		String insuredFatherFirstName = AppConstants.NA;
		String insuredFatherLastName = AppConstants.NA;
		String insuredFatherMiddleName = AppConstants.NA;
		String insuredFatherName = AppConstants.NA;
		String insuredFirstName = AppConstants.NA;
		String insuredGender = AppConstants.NA;
		String insuredLastName = AppConstants.NA;
		String insuredMaritialStatus = AppConstants.NA;
		String insuredMiddleName = AppConstants.NA;
		String insuredNationality = AppConstants.NA;
		String insuredNatureOfDuties = AppConstants.NA;
		String insuredOccupation = AppConstants.NA;
		String insuredOrganisationType = AppConstants.NA;
		String insuredRelationshipWithProposer = AppConstants.NA;
		String insuredResidingCountry = AppConstants.NA;
		String insuredTitle = AppConstants.NA;
		String proposerAnnualIncome = AppConstants.NA;
		String proposerCountryByTaxLaw = AppConstants.NA;
		String proposerEducation = AppConstants.NA;
		String proposerEmployerName = AppConstants.NA;
		String proposerFatherFirstName = AppConstants.NA;
		String proposerFatherLastName = AppConstants.NA;
		String proposerFirstName = AppConstants.NA;
		String proposerGender = AppConstants.NA;
		String proposerLastName = AppConstants.NA;
		String proposerMaritialStatus = AppConstants.NA;
		String proposerMiddleName = AppConstants.NA;
		String proposerNationality = AppConstants.NA;
		String proposerNatureOfDuties = AppConstants.NA;
		String proposerOccupation = AppConstants.NA;
		String proposerOrganisationType = AppConstants.NA;
		String proposerRelationshipWithProposer = AppConstants.NA;
		String proposerResidingCountry = AppConstants.NA;
		String proposerTitle = AppConstants.NA;
		try {
			formType = proposalDetails.getApplicationDetails().getFormType();
			String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
			BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
			// set pr name
			proposerTitle = Utility.getTitle(proposerBasicDetails.getGender());
			String proposerFullName = proposerBasicDetails.getFirstName()+" "+proposerBasicDetails.getMiddleName()+" "+proposerBasicDetails.getLastName();
			proposerFirstName = Utility.getNamePart(proposerFullName, NAME_PARTS.FIRST);
			proposerMiddleName = Utility.getNamePart(proposerFullName, NAME_PARTS.MIDDLE);
			proposerLastName = Utility.getNamePart(proposerFullName, NAME_PARTS.LAST);
			dataVariables.put("formType", formType.toUpperCase());
			dataVariables.put("proposerTitle", proposerTitle);
			dataVariables.put("proposerFirstName", proposerFirstName);
			dataVariables.put("proposerMiddleName", proposerMiddleName);
			dataVariables.put("proposerLastName", proposerLastName);
			// set pr father's name
			proposerFatherFirstName = Utility.getNamePart(proposerBasicDetails.getFatherName(), NAME_PARTS.FIRST);
			proposerFatherLastName = Utility.getNamePart(proposerBasicDetails.getFatherName(), NAME_PARTS.LAST);
			dataVariables.put("proposerFatherFirstName", proposerFatherFirstName);
			dataVariables.put("proposerFatherLastName", proposerFatherLastName);
			// set pr dob
			proposerDob = proposerBasicDetails.getDob();
			SimpleDateFormat dobFormatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);
			dobFormatter.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
			String formattedProposerDob = dobFormatter.format(proposerDob);
			dataVariables.put("proposerDob", formattedProposerDob);
			// set pr gender
			proposerGender = Utility.getGender(proposerBasicDetails.getGender());
			dataVariables.put("proposerGender", proposerGender);
			// set pr nationality
			proposerNationality = proposerBasicDetails.getNationalityDetails().getNationality();
			if(!proposerNationality.equalsIgnoreCase(AppConstants.INDIAN_NATIONALITY)) {
				proposerResidingCountry = proposerBasicDetails.getNationalityDetails().getNriDetails().getCurrentCountryOfResidence();
				proposerCountryByTaxLaw = proposerBasicDetails.getNationalityDetails().getNriDetails().getCountryOfResidenceAsPerTaxLaw();
			}

			dataVariables.put("proposerNationality", proposerNationality);
			dataVariables.put("proposerResidingCountry", proposerResidingCountry);
			dataVariables.put("proposerCountryByTaxLaw", proposerCountryByTaxLaw);
			// set pr marital status
			proposerMaritialStatus = proposerBasicDetails.getMarriageDetails().getMaritalStatus();
			dataVariables.put("proposerMaritialStatus", proposerMaritialStatus);
			// set pr eduction
			proposerEducation = proposerBasicDetails.getEducation();
			dataVariables.put("proposerEducation", proposerEducation);
			// set pr relationship with proposer
			proposerRelationshipWithProposer = AppConstants.SELF.toUpperCase();
			dataVariables.put("proposerRelationshipWithProposer", proposerRelationshipWithProposer);
			// set pr industry type
			setIndustryData(AppConstants.PROPOSER, dataVariables, proposalDetails);
			dataVariables.put("insuredIndustryType",AppConstants.NA);

			PartyInformation proposerEmploymentInformation = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0);
			// set pr organization type
			proposerOrganisationType = proposerEmploymentInformation.getPartyDetails().getOrganisationType();
			dataVariables.put("proposerOrganisationType", proposerOrganisationType);
			// set pr occupation
			proposerOccupation = proposerBasicDetails.getOccupation();
			dataVariables.put("proposerOccupation", proposerOccupation);
			// set pr employer name
			proposerEmployerName = proposerEmploymentInformation.getPartyDetails().getNameOfEmployer();
			dataVariables.put("proposerEmployerName", proposerEmployerName);
			// pr nature of business
			proposerNatureOfDuties = Utility.ifEmptyThenNA(proposerEmploymentInformation.getPartyDetails().getSpecifyDutiesType());
			dataVariables.put("proposerNatureOfDuties", proposerNatureOfDuties);
			// set pr annual income
			proposerAnnualIncome = proposerBasicDetails.getAnnualIncome();
			dataVariables.put("proposerAnnualIncome", proposerAnnualIncome);
			String customerReminderConsent= Utility.customerReminderConsent(proposalDetails);
			dataVariables.put("customerReminderConsent",customerReminderConsent);
			// For form2 case
			BasicDetails insuredBasicDetails = (formType.equalsIgnoreCase(AppConstants.DEPENDENT)
					&& proposalDetails.getPartyInformation().size() > 1)
							? proposalDetails.getPartyInformation().get(1).getBasicDetails()
							: null;
			if (insuredBasicDetails!=null && (formType.equalsIgnoreCase(AppConstants.DEPENDENT)
					|| (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)))) {
				// set name
				insuredTitle = Utility.getTitle(insuredBasicDetails.getGender());
				insuredFirstName = insuredBasicDetails.getFirstName();
				insuredMiddleName = insuredBasicDetails.getMiddleName();
				insuredLastName = insuredBasicDetails.getLastName();

				// father's name
				insuredFatherName = insuredBasicDetails.getFatherName();
				insuredFatherFirstName = Utility.getNamePart(insuredFatherName, NAME_PARTS.FIRST);
				insuredFatherMiddleName = Utility.getNamePart(insuredFatherName, NAME_PARTS.MIDDLE);
				insuredFatherLastName = Utility.getNamePart(insuredFatherName, NAME_PARTS.LAST);

				// set dob
				insuredDob = insuredBasicDetails.getDob();
				formattedInsuredDob = dobFormatter.format(insuredDob);

				// gender
				insuredGender = Utility.getGender(insuredBasicDetails.getGender());

				// nationality
				insuredNationality = insuredBasicDetails.getNationalityDetails().getNationality();

				if(!insuredNationality.equalsIgnoreCase(AppConstants.INDIAN_NATIONALITY)) {
					insuredResidingCountry = insuredBasicDetails.getNationalityDetails().getNriDetails().getCurrentCountryOfResidence();
					insuredCountryByTaxLaw = insuredBasicDetails.getNationalityDetails().getNriDetails().getCountryOfResidenceAsPerTaxLaw();
				}

				// marital status
				insuredMaritialStatus = insuredBasicDetails.getMarriageDetails().getMaritalStatus();

				// education
				insuredEducation = insuredBasicDetails.getEducation();

				// relation with proposer
				insuredRelationshipWithProposer = Optional.ofNullable(insuredBasicDetails.getRelationshipWithProposer()).map(rel -> rel.equalsIgnoreCase(AppConstants.OTHER) ? insuredBasicDetails.getRelationshipWithProposerWhenOther() : rel).orElse(AppConstants.NA);

				// in industry type
				setIndustryData(AppConstants.INSURED, dataVariables, proposalDetails);
				// set pr organization type
				insuredOrganisationType = proposalDetails.getEmploymentDetails().getPartiesInformation().get(1).getPartyDetails().getOrganisationType();

				PartyInformation insuredEmploymentInformation = proposalDetails.getEmploymentDetails().getPartiesInformation().get(1);
				// in occupation
				insuredOccupation = insuredBasicDetails.getOccupation();

				// in employer name
				insuredEmployerName = insuredEmploymentInformation.getPartyDetails().getNameOfEmployer();

				// in nature of business
				insuredNatureOfDuties = Utility.ifEmptyThenNA(insuredEmploymentInformation.getPartyDetails().getSpecifyDutiesType());

				// in annual income
				insuredAnnualIncome = insuredEmploymentInformation.getPartyDetails().getAnnualIncome();
				Utility.setInsuredContactDetails(proposalDetails, dataVariables);

			}
			dataVariables.put("insuredTitle", insuredTitle);
			dataVariables.put("insuredFirstName", insuredFirstName);
			dataVariables.put("insuredMiddleName", insuredMiddleName);
			dataVariables.put("insuredLastName", insuredLastName);
			dataVariables.put("insuredFatherFirstName", insuredFatherFirstName);
			dataVariables.put("insuredFatherMiddleName", insuredFatherMiddleName);
			dataVariables.put("insuredFatherLastName", insuredFatherLastName);
			dataVariables.put("insuredDob", formattedInsuredDob);
			dataVariables.put("insuredGender", insuredGender);
			dataVariables.put("insuredNationality", insuredNationality);
			dataVariables.put("insuredResidingCountry", insuredResidingCountry);
			dataVariables.put("insuredCountryByTaxLaw", insuredCountryByTaxLaw);
			dataVariables.put("insuredMaritialStatus", insuredMaritialStatus);
			dataVariables.put("insuredEducation", insuredEducation);
			dataVariables.put("insuredRelationshipWithProposer", insuredRelationshipWithProposer);
			dataVariables.put("insuredOrganisationType", insuredOrganisationType);
			dataVariables.put("insuredOccupation", insuredOccupation);
			dataVariables.put("insuredEmployerName", insuredEmployerName);
			dataVariables.put("insuredNatureOfDuties", insuredNatureOfDuties);
			dataVariables.put("insuredAnnualIncome", insuredAnnualIncome);

			// set PEP Details
			Utility.addPepDetails(proposalDetails, dataVariables);
			// set nominee details
			setNomineeDetails(proposalDetails, dataVariables);
			// set address
			setAddressDetails(proposalDetails, dataVariables);
			// set phn number
			setPhoneNumberDetails(proposalDetails, dataVariables);
			// set eia details
			setEiaDetails(proposalDetails, dataVariables);

		} catch (Exception ex) {
			int lineNumber = ex.getStackTrace()[0].getLineNumber();
			logger.error(
					"mapDataForPersonalDetails failed for transactionId {} at line {} with exception {}",
					transactionId, lineNumber, ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("mapDataForPersonalDetails Data Mapping Failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("mapDataForPersonalDetails completed successfully for transactionId {}",
				transactionId);
		Context personalDetailsContext = new Context();
		personalDetailsContext.setVariables(dataVariables);
		logger.info("END mapDataForPersonalDetails");
		return personalDetailsContext;
	}

	private void setIndustryData(String partyType, Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
		partyType = partyType.toLowerCase();
		EmploymentDetails employmentDetails = proposalDetails.getEmploymentDetails();
		PartyDetails partyDetails = (PartyDetails) Utility.evaluateConditionalOperation(
				(partyType.equalsIgnoreCase(AppConstants.PROPOSER)),
				employmentDetails.getPartiesInformation().get(0).getPartyDetails(),
				employmentDetails.getPartiesInformation().get(1).getPartyDetails());
		IndustryDetails industryDetails = partyDetails.getIndustryDetails();
		IndustryInfo industryInfo = industryDetails.getIndustryInfo();

		String industryType = industryDetails.getIndustryType();
		dataVariables.put(partyType + "IndustryType", industryType);
		switch (industryType) {
		case AppConstants.DEFENCE:
			dataVariables.put(partyType + "DefenceReflexive1",
					industryInfo.isPostedOnDefenceLocation() ? AppConstants.YES : AppConstants.NO);
			dataVariables.put(partyType + "DefenceReflexive2", industryInfo.getNatureOfRole());
			dataVariables.put(partyType + "Defence", true);
			break;
		case AppConstants.DIVING:
			dataVariables.put(partyType + "DivingReflexive1",
					industryInfo.isProfessionalDiver() ? AppConstants.YES : AppConstants.NO);
			dataVariables.put(partyType + "DivingReflexive2", industryInfo.getDiveLocation());
			dataVariables.put(partyType + "Diving", true);
			break;
		case AppConstants.MINING:
			dataVariables.put(partyType + "MiningReflexive1",
					industryInfo.isWorkingInsideMine() ? AppConstants.YES : AppConstants.NO);
			dataVariables.put(partyType + "MiningReflexive2",
					industryInfo.isAnyIllnessRelatedToOccupation() ? AppConstants.YES : AppConstants.NO);
			dataVariables.put(partyType + "Mining", true);
			break;
		case AppConstants.AIR_FORCE:
			dataVariables.put(partyType + "AirforceReflexive1", industryInfo.isFlying() ? "YES" : "NO");
			dataVariables.put(partyType + "AirforceReflexive2", industryInfo.getTypeOfAirCraft());
			dataVariables.put(partyType + "Airforce", true);
			break;
		case AppConstants.OIL:
			dataVariables.put(partyType + "OilReflexive1",
					industryInfo.isBasedAtOffshore() ? AppConstants.YES : AppConstants.NO);
			dataVariables.put(partyType + "Oil", true);
			break;
		case AppConstants.NAVY:
			dataVariables.put(partyType + "NavyReflexive1", industryInfo.getNavyAreaWorking());
			dataVariables.put(partyType + "Navy", true);
			break;
		case AppConstants.OTHERS_INDUSTRY:
			break;
		default:
			logger.info("No Industry type Found");
		}
	}

	public void setPEPDetails(ProposalDetails proposalDetails) {
		//
	}

	public void setNomineeDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		List<ProposalNomineeDetails> proposalNomineeDetails = new ArrayList<>();
		List<PartyDetails> nomineeDetails = new ArrayList<>();
		if (Objects.nonNull(proposalDetails.getNomineeDetails())) {
			nomineeDetails.addAll(proposalDetails.getNomineeDetails().getPartyDetails());
		}
		ProposalNomineeDetails details = null;
		for (int i = 0; i < nomineeDetails.size(); i++) {
			details = new ProposalNomineeDetails();
			PartyDetails nominee = nomineeDetails.get(i);
			if (StringUtils.isAllEmpty(nominee.getFirstName(), nominee.getMiddleName(), nominee.getLastName())) {
				details = new ProposalNomineeDetails("NA");
				proposalNomineeDetails.add(details);
				continue;
			}
			String nomineeDob = Utility.dateFormatter(nominee.getDob());
			details.setDob(nomineeDob);
			details.setFirstName(nominee.getFirstName());
			details.setLastName(nominee.getLastName());
			details.setMiddleName(nominee.getMiddleName());
			details.setFatherFirstName(Utility.getNamePart(nominee.getFatherOrHusbandName(), NAME_PARTS.FIRST));
			details.setFatherLastName(Utility.getNamePart(nominee.getFatherOrHusbandName(), NAME_PARTS.LAST));
			details.setPercentageShare((nomineeDetails.size() == 1) ? 100 : nominee.getPercentageShare());
			details.setGender(getGenderInFullForm(nominee.getGender()));
			details.setTitle(Optional.ofNullable(nominee.getGender()).map(gender -> Utility.getTitle(nominee.getGender())).orElse(AppConstants.NA));
			details.setRelationshipWithProposer(nominee.getRelationshipWithProposer());
			details.setGuardianGender(getGenderInFullForm(nominee.getAppointeeDetails().getAppointeeGender()));
			if (OTHERS.equalsIgnoreCase(nominee.getRelationshipWithProposer())) {
				details.setNomineeRealtionshipOther(AppConstants.YES);
				details.setSpecifyRelationship(nominee.getRelationshipOthers());
				details.setReasonForNomination(nominee.getReasonForNomination());
			}
			setNomineeMinorDetails(details, nominee);
			proposalNomineeDetails.add(details);
		}
		dataVariables.put("nomineeDetailsCount", proposalNomineeDetails);
	}

	private void setNomineeMinorDetails(ProposalNomineeDetails details, PartyDetails nominee) {
		// if nominee is minor
		if (nominee.getDob() != null && Float.valueOf(Utility.getAge(nominee.getDob())) < 18) {
			details.setNomineeMinor(AppConstants.YES);
			AppointeeDetails appointeDetails = nominee.getAppointeeDetails() != null ? nominee.getAppointeeDetails()
					: new AppointeeDetails();
			details.setGuardianName(appointeDetails.getGuardianNameOfNominee());

			/** changes for FUL- 14812 */
			String guardianName = details.getGuardianName();
			details.setGuardianFirstName(Utility.getNamePart(guardianName, NAME_PARTS.FIRST));
			details.setGuardianLastName(Utility.getNamePart(guardianName, NAME_PARTS.LAST));

			details.setGuardianRelation(appointeDetails.getRelationwithNominee());
			if (!StringUtils.isEmpty(appointeDetails.getRelationwithNominee())
					&& appointeDetails.getRelationwithNominee().equalsIgnoreCase(OTHERS)) {
				details.setGuardianRelationshipOther(AppConstants.YES);
				details.setGuardianSpecifyRelation(appointeDetails.getRelationwithNomineeOthers());
			}
		} else {
			details.setNomineeMinor(AppConstants.NA);
		}
		if (Objects.nonNull(nominee.getAppointeeDetails())
				&& Objects.nonNull(nominee.getAppointeeDetails().getAppointeeDOB())) {
			details.setAppointeeDOB(
					DateTimeUtils.format(nominee.getAppointeeDetails().getAppointeeDOB(), DD_MM_YYYY_CONST));
		}
		if (Objects.nonNull(nominee.getAppointeeDetails())
				&& Objects.nonNull(nominee.getAppointeeDetails().getAppointeeGender())
				&& !Strings.isNullOrEmpty(nominee.getAppointeeDetails().getAppointeeGender())) {
			details.setAppointeeGender(Utility.getGender(nominee.getAppointeeDetails().getAppointeeGender()));
		}

	}

	public void setAddressDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		String formType = proposalDetails.getApplicationDetails().getFormType();
		List<Address> proposerAddressList = proposalDetails.getPartyInformation().get(0).getBasicDetails().getAddress();
		boolean isPrPermAddSameAsCommAdd = proposalDetails.getPartyInformation().get(0).getBasicDetails().isAreBothAddressSame();
		if(!(formType.equals(AppConstants.FORM3)) && !CollectionUtils.isEmpty(proposerAddressList)) {
			AddressDetails commAddressDetails = proposerAddressList.get(0).getAddressDetails();
			String prCommHouseNo = commAddressDetails.getHouseNo();
			String prCommArea = commAddressDetails.getArea();
			String prCommVillage = commAddressDetails.getVillage();
			String prCommLandmark = commAddressDetails.getLandmark();
			String prCommCity = commAddressDetails.getCity();
			String prCommPincode = commAddressDetails.getPinCode();
			String prCommState = commAddressDetails.getState();
			String prCommCountry = commAddressDetails.getCountry();
			dataVariables.put("commHouseNo", prCommHouseNo);
			dataVariables.put("commArea", prCommArea);
			dataVariables.put("commVillage", prCommVillage);
			dataVariables.put("commLandmark", prCommLandmark);
			dataVariables.put("commCity", prCommCity);
			dataVariables.put("commPincode", prCommPincode);
			dataVariables.put("commState", prCommState);
			dataVariables.put("commCountry", prCommCountry);
		}

		if(!CollectionUtils.isEmpty(proposerAddressList) && proposerAddressList.size()>1 && !isPrPermAddSameAsCommAdd) {
			AddressDetails permAddressDetails = proposerAddressList.get(1).getAddressDetails();
			String prPermHouseNo = permAddressDetails.getHouseNo();
			String prPermArea = permAddressDetails.getArea();
			String prPermVillage = permAddressDetails.getVillage();
			String prPermLandmark = permAddressDetails.getLandmark();
			String prPermCity = permAddressDetails.getCity();
			String prPermPincode = permAddressDetails.getPinCode();
			String prPermState = permAddressDetails.getState();
			String prPermCountry = permAddressDetails.getCountry();
			dataVariables.put("permHouseNo", prPermHouseNo);
			dataVariables.put("permArea", prPermArea);
			dataVariables.put("permVillage", prPermVillage);
			dataVariables.put("permLandmark", prPermLandmark);
			dataVariables.put("permCity", prPermCity);
			dataVariables.put("permPincode", prPermPincode);
			dataVariables.put("permState", prPermState);
			dataVariables.put("permCountry", prPermCountry);
		}
		String email = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getEmail();
		dataVariables.put("email", email);
	}

	private void setPhoneNumberDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		String mobileNumber = AppConstants.NA;
		String alternateMobileNo = AppConstants.NA;
		String stdIsdCode = StringUtils.EMPTY;
		String landlineNo = StringUtils.EMPTY;
		List<Phone> phoneNoList = proposalDetails.getPartyInformation().get(0).getPersonalIdentification().getPhone();
		if(!CollectionUtils.isEmpty(phoneNoList)) {
			mobileNumber = phoneNoList.get(0).getPhoneNumber();
			alternateMobileNo = phoneNoList.get(1).getPhoneNumber();
			stdIsdCode = phoneNoList.get(2).getStdIsdCode();
			landlineNo = phoneNoList.get(2).getPhoneNumber();
		}
		dataVariables.put("mobileNumber",mobileNumber);
		dataVariables.put("alternateMobileNo",alternateMobileNo);
		dataVariables.put("stdIsdCode",stdIsdCode);
		dataVariables.put("landlineNo",landlineNo);
	 }

	private void setEiaDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		EmploymentDetails employmentDetails = proposalDetails.getEmploymentDetails();
		String prNationality = proposalDetails.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality();
		String isEIA = Utility.orTwoExpressions(employmentDetails.isEIAExist(), prNationality.equalsIgnoreCase(AppConstants.INDIAN_NATIONALITY)) ? AppConstants.YES : AppConstants.NO;
		String eiaNumberExist = !StringUtils.isEmpty(employmentDetails.getExistingEIANumber()) ? AppConstants.YES : AppConstants.NO;
		String existingEIANumber = (eiaNumberExist.equalsIgnoreCase(AppConstants.YES)) ? employmentDetails.getExistingEIANumber() : AppConstants.NA;
		String existingRepoName = "";
		if (!StringUtils.isEmpty(existingEIANumber)) {
			if (existingEIANumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_1)) {
				existingRepoName = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_1);
			} else if (existingEIANumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_2)) {
				existingRepoName = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_2);
			} else if (existingEIANumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_3)) {
				existingRepoName = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_3);
			} else if (existingEIANumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_4)) {
				existingRepoName = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_4);
			} else if (existingEIANumber.startsWith(AppConstants.E_INC_ACC_NO_START_WITH_5)) {
				existingRepoName = insuranceRepositoryMap.get(AppConstants.E_INC_ACC_NO_START_WITH_5);
			} else {
				 existingRepoName = Utility.ifEmptyThenNA(employmentDetails.getExistingEIANumberRepositoryName());
			}
		}
		String preferredInsuranceRepositoryName = employmentDetails.getPreferredInsuranceRepositoryName();
		dataVariables.put("isEIA", isEIA);
	    dataVariables.put("existingEIANumber", existingEIANumber);
	    dataVariables.put("EIANumberExist", eiaNumberExist);
	    dataVariables.put("existingRepoName", existingRepoName);
	    dataVariables.put("preferredRepoName",preferredInsuranceRepositoryName);
	}

	private String getGenderInFullForm(String gender) {
		if(StringUtils.isEmpty(gender)) return AppConstants.BLANK;
		String genderFullForm = "Male";
		if ("F".equals(gender)) {
			genderFullForm = "Female";
		} else if ("O".equalsIgnoreCase(gender) || "Other".equalsIgnoreCase(gender) || OTHERS.equalsIgnoreCase(gender)
				|| "Mx".equalsIgnoreCase(gender)) {
			genderFullForm = TRANSGENDER;
		}
		return genderFullForm;
	}

}
