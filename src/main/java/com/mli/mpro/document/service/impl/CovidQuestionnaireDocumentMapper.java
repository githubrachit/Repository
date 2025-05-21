package com.mli.mpro.document.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.PosvQuestion;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import static com.mli.mpro.utils.Utility.convertYesNoAndNA;

@Service
public class CovidQuestionnaireDocumentMapper {
	private static final Logger logger = LoggerFactory.getLogger(CovidQuestionnaireDocumentMapper.class);

	public Context setDataForCovidQuestionnaire(ProposalDetails proposalDetails) throws UserHandledException {
		logger.info("Starting COVID questionnaire document data population for transactionId {}",
				proposalDetails.getTransactionId());
		Map<String, Object> dataVariables = new HashMap<>();
		try {
			Map<String, String> questionMap = new HashMap<>();
			List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
			for (PosvQuestion posvQuestion : posvQuestions) {
				if(StringUtils.isEmpty(questionMap.get(posvQuestion.getQuestionId()))) {
					questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
				}
			}
				setCovidExposure(dataVariables, questionMap, proposalDetails);

		} catch (Exception e) {
			logger.info("Data addition failed for COVID Questionnaire document for transactionId {} : {} ",
					proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("Data addition failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Ending COVID questionnaire document data population for transactionId {}",
				proposalDetails.getTransactionId());
		Context context = new Context();
		context.setVariables(dataVariables);
		return context;
	}

	private void setCovidExposure(Map<String, Object> dataVariables, Map<String, String> questionMap,
			ProposalDetails proposalDetails) {
		
		String proposalName = null;
		String formType = proposalDetails.getApplicationDetails().getFormType();
		Boolean isNRI = checkNRIstatus(formType, proposalDetails);
		BasicDetails proposalBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
		proposalName = proposalBasicDetails.getFirstName() + " " + proposalBasicDetails.getMiddleName() + " "
				+ proposalBasicDetails.getLastName();
		Date otpConfirmation = proposalDetails.getPosvDetails().getPosvStatus().getGeneratedOTPDate();
		SimpleDateFormat format = new SimpleDateFormat(AppConstants.DD_MM_YYYY_SLASH);
		format.setTimeZone(TimeZone.getTimeZone(AppConstants.APP_TIMEZONE));
		String otpConfirmationDate = format.format(otpConfirmation);
		String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
		//FUL2-46310 
		policyNumber = Utility.getSecondaryPolicyNumber(proposalDetails, policyNumber);
		policyNumber = Utility.getPrimaryPolicyNumber(proposalDetails, policyNumber);
		
		dataVariables.put("applicationNumber", policyNumber);
		dataVariables.put("lifeInsuredName", Utility.getInsuredName(formType, proposalDetails));
		dataVariables.put("proposedPolicyHolder", proposalName);

		dataVariables.put("covidExposureSection", validateSection(questionMap.get("H16"), isNRI));

		dataVariables.put("travelAbroad", convertYesNoAndNA(questionMap.get("H16A")));
		dataVariables.put("travelAbroadCountry", questionMap.get("H16A1"));
		dataVariables.put("travelAbroadReturnDate", dateFormat(questionMap.get("H16A2")));

		dataVariables.put("intendTravelAbroad", convertYesNoAndNA(questionMap.get("H16B")));
		dataVariables.put("intendTravelAbroadCountry", questionMap.get("H16B1"));
		dataVariables.put("intendTravelAbroadIntendDate", dateFormat(questionMap.get("H16B2")));
		dataVariables.put("intendTravelAbroadReturnDate", questionMap.get("H16B3"));

		dataVariables.put("lastThreeMonthHealthCheck", convertYesNoAndNA(questionMap.get("H16C")));
		dataVariables.put("lastThreeMonthHealthCheckSelf", convertYesNoAndNA(questionMap.get("H16C1")));
		dataVariables.put("lastThreeMonthHealthCheckFamily", convertYesNoAndNA(questionMap.get("H16C2")));
		dataVariables.put("lastThreeMonthHealthCheckDiagnosis", questionMap.get("H16C3"));
		dataVariables.put("lastThreeMonthHealthCheckDiagnosisDate", dateFormat(questionMap.get("H16C4")));
		dataVariables.put("lastThreeMonthHealthCheckRecovery", convertYesNoAndNA(questionMap.get("H16C5")));
		dataVariables.put("lastThreeMonthHealthCheckRecoveryDate", dateFormat(questionMap.get("H16C6")));

		dataVariables.put("covidCheck", convertYesNoAndNA(questionMap.get("H16D")));
		dataVariables.put("covidCheckDateOfDiagnosis", questionMap.get("H16D1"));
		dataVariables.put("covidCheckNameOfTest", questionMap.get("H16D2"));
		dataVariables.put("covidCheckSubsequentTest", questionMap.get("H16D3"));
		dataVariables.put("covidCheckAdmitted", convertYesNoAndNA(questionMap.get("H16D4")));
		dataVariables.put("covidCheckHDU", convertYesNoAndNA(questionMap.get("H16D4i")));
		dataVariables.put("covidCheckICU", convertYesNoAndNA(questionMap.get("H16D4ii")));
		dataVariables.put("covidCheckITU", convertYesNoAndNA(questionMap.get("H16D4iii")));
		dataVariables.put("covidCheckCCU", convertYesNoAndNA(questionMap.get("H16D4iv")));
		dataVariables.put("covidCheckOther", questionMap.get("H16D4v"));

		dataVariables.put("covidCheckVentilator", convertYesNoAndNA(questionMap.get("H16D5")));
		dataVariables.put("covidCheckRecovery", convertYesNoAndNA(questionMap.get("H16D6")));
		dataVariables.put("covidCheckRecoveryDate", dateFormat(questionMap.get("H16D7")));

		dataVariables.put("quarantine", convertYesNoAndNA(questionMap.get("H16E")));
		dataVariables.put("quarantineLocation", questionMap.get("H16E1"));
		dataVariables.put("quarantinePeriodFrom", dateFormat(questionMap.get("H16E2")));
		dataVariables.put("quarantinePeriodTo", dateFormat(questionMap.get("H16E3")));

		dataVariables.put("areYouCovidWarrior", validateSection(questionMap.get("H17"), isNRI));
		dataVariables.put("occupationOfHealthWorker", questionMap.get("H17A"));
		dataVariables.put("medicalSpecialty", (questionMap.get("H17B")));
		dataVariables.put("natureOfDuty", (questionMap.get("H17C")));
		dataVariables.put("addressOfHealthcareFacility", (questionMap.get("H17D")));
		dataVariables.put("healthAuthorityName", (questionMap.get("H17E")));
		dataVariables.put("healthcareFacilityHavePPE", convertYesNoAndNA(questionMap.get("H17F")));
		dataVariables.put("closeContactWithQuarantined", convertYesNoAndNA(questionMap.get("H17G")));
		dataVariables.put("natureOfWorkForPatients", questionMap.get("H17G1"));
		dataVariables.put("voluntaryOrCompulsoryLeave", convertYesNoAndNA(questionMap.get("H17H")));
		dataVariables.put("dateOfLeave", questionMap.get("H17H1"));
		dataVariables.put("detailLeaveInformation", questionMap.get("H17H2"));
		dataVariables.put("currentlyGoodHealth", convertYesNoAndNA(questionMap.get("H17I")));
		dataVariables.put("healthDetails", questionMap.get("H17I1"));
		dataVariables.put("date", otpConfirmationDate);
	}

	private boolean validateSection(String value, boolean isNRI) {
		if (isNRI) {
			return true;
		} else if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("Y")) {
			return true;
		} else if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("N")) {
			return false;
		}
		return false;
	}

	private String dateFormat(String date) {
		String formattedDate = null;
		if(StringUtils.isNotEmpty(date)){
		DateFormat parser = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date2=null;
		try {
			date2 = (Date) parser.parse(date);
		} catch (ParseException e) {
			logger.info("Error in date formating {}",date);
		}

		DateFormat formatter = new SimpleDateFormat(AppConstants.DD_MM_YYYY_SLASH); 
		formattedDate = formatter.format(date2);
		}
	        return formattedDate;
	}

	private boolean checkNRIstatus(String formType, ProposalDetails proposalDetails) {
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		boolean isNRI = false;
		if ((formType.equalsIgnoreCase(AppConstants.DEPENDENT) || (formType.equalsIgnoreCase(AppConstants.FORM3) && !Utility.schemeBCase(schemeType)))
				&& StringUtils.isNotEmpty(proposalDetails.getPartyInformation().get(1).getBasicDetails()
						.getNationalityDetails().getNationality())
				&& proposalDetails.getPartyInformation().get(1).getBasicDetails().getNationalityDetails()
						.getNationality().equalsIgnoreCase("nri")) {
			isNRI = true;
		} else if ((formType.equalsIgnoreCase(AppConstants.SELF) || Utility.schemeBCase(formType, schemeType))
				&& StringUtils.isNotEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails()
						.getNationalityDetails().getNationality())
				&& proposalDetails.getPartyInformation().get(0).getBasicDetails().getNationalityDetails()
						.getNationality().equalsIgnoreCase("nri")) {
			isNRI = true;
		} else {
			isNRI = false;
		}
		return isNRI;
	}
	
	public boolean isCovidFormRequiredToGenerate(ProposalDetails proposalDetails) {
		boolean isCovidFormRequired = false;
		Map<String, String> questionMap = new HashMap<>();
		List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
		for (PosvQuestion posvQuestion : posvQuestions) {
			if(StringUtils.isEmpty(questionMap.get(posvQuestion.getQuestionId()))) {
				questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
			}
		}
		String formType = proposalDetails.getApplicationDetails().getFormType();
		boolean isNRI = checkNRIstatus(formType, proposalDetails);
		logger.info("Covid document generation {} {} {}", isNRI, questionMap.get("H16"), questionMap.get("H17") );
		if(isCheckForShowCovidQuesOnPosv(proposalDetails, isNRI, questionMap)) {
			isCovidFormRequired = false;
		} else if (AppConstants.Y.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getShowCovidQuesOnPosv())
				&& !isNRI && (StringUtils.isBlank(questionMap.get("H16")) || questionMap.get("H16").equalsIgnoreCase("N"))
				&& (StringUtils.isBlank(questionMap.get("H17")) || questionMap.get("H17").equalsIgnoreCase("N"))) {
			isCovidFormRequired = false;
		} else {
			isCovidFormRequired = true;
		}
		if(isWOPorSWPJoint(proposalDetails)) {
			isCovidFormRequired = false;
		}
		return isCovidFormRequired;
	}

	private boolean isCheckForShowCovidQuesOnPosv(ProposalDetails proposalDetails, boolean isNRI, Map<String, String> questionMap) {
		return (AppConstants.N.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getShowCovidQuesOnPosv())
				&& !isNRI && StringUtils.isBlank(questionMap.get("HC16")) && StringUtils.isBlank(questionMap.get("HC17"))
				&& StringUtils.isBlank(questionMap.get("HC40"))	&& StringUtils.isBlank(questionMap.get("HC41")));
	}

	/** Checks if <b>WOP</b> or <b>Payor Benefit</b> Rider is present OR if it is <b>SWP with Whole Life Variant</b> */
	public boolean isWOPorSWPJoint(ProposalDetails proposalDetails) {
		boolean isWOPorSWPJoint;
		boolean isWOPRiderCase = isWOPorPayorBenefitRiderCase(proposalDetails.getProductDetails().get(0).getProductInfo()
				.getRiderDetails());
		boolean isSwpWLIVariant = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId().equals(AppConstants.SWP_PRODUCTCODE) && proposalDetails.getProductDetails().get(0).getProductInfo().getVariant().equals(AppConstants.WHOLE_LIFE_INCOME);
		if(isWOPRiderCase || isSwpWLIVariant) {
			isWOPorSWPJoint = true;
		}else {
			isWOPorSWPJoint = false;
		}
		return isWOPorSWPJoint;
	}
	
	private  boolean isWOPorPayorBenefitRiderCase(List<com.mli.mpro.proposal.models.RiderDetails> riderDetailsList){
		try {
			for (com.mli.mpro.proposal.models.RiderDetails riderDetails : riderDetailsList) {
				if ((AppConstants.WOP.equalsIgnoreCase(riderDetails.getRiderInfo())
						|| AppConstants.AXIS_WOP.equalsIgnoreCase(riderDetails.getRiderInfo())
						|| (AppConstants.RIDERVAR_PB.equalsIgnoreCase(riderDetails.getRiderInfo())))
					&& riderDetails.isRiderRequired()) {
					return true;
				}
			}
		}catch (Exception e){
			logger.error("error occurred while checking wop rider case: {} ",Utility.getExceptionAsString(e));
		}
		return false;
	}
	public boolean isCovidWOPorSWPJointFormRequiredToGenerate(ProposalDetails proposalDetails) {
		boolean isCovidFormRequired = false;
		Map<String, String> questionMap = new HashMap<>();
		List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
		for (PosvQuestion posvQuestion : posvQuestions) {
			if(StringUtils.isEmpty(questionMap.get(posvQuestion.getQuestionId()))) {
				questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
			}
		}
		boolean isProposalNRI = checkNRIstatus(AppConstants.SELF, proposalDetails);
		boolean isInsuredNRI = checkNRIstatus(AppConstants.DEPENDENT, proposalDetails);
		logger.info("Covid WOPorSWPJoint document generation {} {} ", isProposalNRI, isInsuredNRI );
		if (!(isProposalNRI || isInsuredNRI) && isCheckForIsCovidWOPorSWPJointForm(questionMap)) {
			isCovidFormRequired = false;
		} else {
			isCovidFormRequired = true;
		}
		return isCovidFormRequired;
	}

	private boolean isCheckForIsCovidWOPorSWPJointForm(Map<String, String> questionMap) {
		return !(StringUtils.isBlank(questionMap.get("H16")) || questionMap.get("H16").equalsIgnoreCase("N"))
				&& (StringUtils.isBlank(questionMap.get("H17")) || questionMap.get("H17").equalsIgnoreCase("N"))
				&& (StringUtils.isBlank(questionMap.get("H40")) || questionMap.get("H40").equalsIgnoreCase("N"))
				&& (StringUtils.isBlank(questionMap.get("H41")) || questionMap.get("H41").equalsIgnoreCase("N"))
				&& (StringUtils.isBlank(questionMap.get(AppConstants.HC16)))
				&& (StringUtils.isBlank(questionMap.get(AppConstants.HC17)))
				&& (StringUtils.isBlank(questionMap.get(AppConstants.HC40)))
				&& (StringUtils.isBlank(questionMap.get(AppConstants.HC41)));
	}

	public Context setDataForCovidWOPorSWPQuestionnaire(ProposalDetails proposalDetails) throws UserHandledException {
		logger.info("Starting COVID WOP/SWP questionnaire document data population for transactionId {}",
				proposalDetails.getTransactionId());
		Map<String, Object> dataVariables = new HashMap<>();
		try {
			Map<String, String> questionMap = new HashMap<>();
			List<PosvQuestion> posvQuestions = proposalDetails.getPosvDetails().getPosvQuestions();
			for (PosvQuestion posvQuestion : posvQuestions) {
				if(StringUtils.isEmpty(questionMap.get(posvQuestion.getQuestionId()))) {
					questionMap.put(posvQuestion.getQuestionId(), posvQuestion.getAnswer());
				}
			}
				setCovidExposure(dataVariables, questionMap, proposalDetails);
				setCovidExposureInsured(dataVariables, questionMap, proposalDetails);

		} catch (Exception e) {
			logger.info("Data addition failed for COVID WOP/SWP Questionnaire document for transactionId {} :{} ",
					proposalDetails.getTransactionId(), Utility.getExceptionAsString(e));
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("Data addition failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Ending COVID WOP/SWP questionnaire document data population for transactionId {}",
				proposalDetails.getTransactionId());
		Context context = new Context();
		context.setVariables(dataVariables);
		return context;
	}
	
	private void setCovidExposureInsured(Map<String, Object> dataVariablesInsure, Map<String, String> questionMap, ProposalDetails proposalDetails) {
		
		boolean proposalNRIStatus = checkNRIstatus(AppConstants.SELF, proposalDetails);
		boolean insuredNRIStatus = checkNRIstatus(AppConstants.DEPENDENT, proposalDetails);

		dataVariablesInsure.put("travelAbroadInsured", convertYesNoAndNA(questionMap.get("H40A")));
		dataVariablesInsure.put("travelAbroadCountryInsured", questionMap.get("H40A1"));
		dataVariablesInsure.put("travelAbroadReturnDateInsured", dateFormat(questionMap.get("H40A2")));

		dataVariablesInsure.put("intendTravelAbroadInsured", convertYesNoAndNA(questionMap.get("H40B")));
		dataVariablesInsure.put("intendTravelAbroadCountryInsured", questionMap.get("H40B1"));
		dataVariablesInsure.put("intendTravelAbroadIntendDateInsured", dateFormat(questionMap.get("H40B2")));
		dataVariablesInsure.put("intendTravelAbroadReturnDateInsured", questionMap.get("H40B3"));

		dataVariablesInsure.put("lastThreeMonthHealthCheckInsured", convertYesNoAndNA(questionMap.get("H40C")));
		dataVariablesInsure.put("lastThreeMonthHealthCheckSelfInsured", convertYesNoAndNA(questionMap.get("H40C1")));
		dataVariablesInsure.put("lastThreeMonthHealthCheckFamilyInsured", convertYesNoAndNA(questionMap.get("H40C2")));
		dataVariablesInsure.put("lastThreeMonthHealthCheckDiagnosisInsured", questionMap.get("H40C3"));
		dataVariablesInsure.put("lastThreeMonthHealthCheckDiagnosisDateInsured", dateFormat(questionMap.get("H40C4")));
		dataVariablesInsure.put("lastThreeMonthHealthCheckRecoveryInsured", convertYesNoAndNA(questionMap.get("H40C5")));
		dataVariablesInsure.put("lastThreeMonthHealthCheckRecoveryDateInsured", dateFormat(questionMap.get("H40C6")));

		dataVariablesInsure.put("covidCheckInsured", convertYesNoAndNA(questionMap.get("H40D")));
		dataVariablesInsure.put("covidCheckDateOfDiagnosisInsured", questionMap.get("H40D1"));
		dataVariablesInsure.put("covidCheckNameOfTestInsured", questionMap.get("H40D2"));
		dataVariablesInsure.put("covidCheckSubsequentTestInsured", questionMap.get("H40D3"));
		dataVariablesInsure.put("covidCheckAdmittedInsured", convertYesNoAndNA(questionMap.get("H40D4")));
		dataVariablesInsure.put("covidCheckHDUInsured", convertYesNoAndNA(questionMap.get("H40D4i")));
		dataVariablesInsure.put("covidCheckICUInsured", convertYesNoAndNA(questionMap.get("H40D4ii")));
		dataVariablesInsure.put("covidCheckITUInsured", convertYesNoAndNA(questionMap.get("H40D4iii")));
		dataVariablesInsure.put("covidCheckCCUInsured", convertYesNoAndNA(questionMap.get("H40D4iv")));
		dataVariablesInsure.put("covidCheckOtherInsured", questionMap.get("H40D4v"));

		dataVariablesInsure.put("covidCheckVentilatorInsured", convertYesNoAndNA(questionMap.get("H40D5")));
		dataVariablesInsure.put("covidCheckRecoveryInsured", convertYesNoAndNA(questionMap.get("H40D6")));
		dataVariablesInsure.put("covidCheckRecoveryDateInsured", dateFormat(questionMap.get("H40D7")));

		dataVariablesInsure.put("quarantineInsured", convertYesNoAndNA(questionMap.get("H40E")));
		dataVariablesInsure.put("quarantineLocationInsured", questionMap.get("H40E1"));
		dataVariablesInsure.put("quarantinePeriodFromInsured", dateFormat(questionMap.get("H40E2")));
		dataVariablesInsure.put("quarantinePeriodToInsured", dateFormat(questionMap.get("H40E3")));

		dataVariablesInsure.put("occupationOfHealthWorkerInsured", StringUtils.isNotEmpty(questionMap.get("H41A"))? questionMap.get("H41A") :"NA");
		dataVariablesInsure.put("medicalSpecialtyInsured", StringUtils.isNotEmpty(questionMap.get("H41B"))? questionMap.get("H41B") :"NA");
		dataVariablesInsure.put("natureOfDutyInsured", StringUtils.isNotEmpty(questionMap.get("H41C"))? questionMap.get("H41C") :"NA");
		dataVariablesInsure.put("addressOfHealthcareFacilityInsured", StringUtils.isNotEmpty(questionMap.get("H41D"))? questionMap.get("H41D") :"NA");
		dataVariablesInsure.put("healthAuthorityNameInsured", StringUtils.isNotEmpty(questionMap.get("H41E"))? questionMap.get("H41E") :"NA");
		dataVariablesInsure.put("healthcareFacilityHavePPEInsured", convertYesNoAndNA(questionMap.get("H41F")));
		dataVariablesInsure.put("closeContactWithQuarantinedInsured", convertYesNoAndNA(questionMap.get("H41G")));
		dataVariablesInsure.put("natureOfWorkForPatientsInsured", questionMap.get("H41G1"));
		dataVariablesInsure.put("voluntaryOrCompulsoryLeaveInsured", convertYesNoAndNA(questionMap.get("H41H")));
		dataVariablesInsure.put("dateOfLeaveInsured", questionMap.get("H41H1"));
		dataVariablesInsure.put("detailLeaveInformationInsured", questionMap.get("H41H2"));
		dataVariablesInsure.put("currentlyGoodHealthInsured", convertYesNoAndNA(questionMap.get("H41I")));
		dataVariablesInsure.put("healthDetailsInsured", questionMap.get("H41I1"));
		
		dataVariablesInsure.put("covidWopExposureSection", validateSectionWopSwp(questionMap.get("H16"), questionMap.get("H40"), proposalNRIStatus, insuredNRIStatus));
		dataVariablesInsure.put("areYouWopCovidWarrior", validateSectionWopSwp(questionMap.get("H17"), questionMap.get("H41"), proposalNRIStatus, insuredNRIStatus));
	}
	private boolean validateSectionWopSwp(String proposal, String insure, boolean isNRIProposal, boolean isNRIInsured) {
		if (isNRIProposal || isNRIInsured) {
			return true;
		} else if (StringUtils.isNotEmpty(proposal) && proposal.equalsIgnoreCase("Y") || StringUtils.isNotEmpty(insure) && insure.equalsIgnoreCase("Y") ) {
			return true;
		} else if (StringUtils.isNotEmpty(proposal) && proposal.equalsIgnoreCase("N") && StringUtils.isNotEmpty(insure) && insure.equalsIgnoreCase("N")) {
			return false;
		}
		return false;
	}
}
