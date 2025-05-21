package com.mli.mpro.document.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
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
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.InsuranceDetails;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
@Service
public class HSALifeInsuredDetailsMapper {

	private static final Logger logger = LoggerFactory.getLogger(HSALifeInsuredDetailsMapper.class);

	private static final String LI_OFFERED = "LI_OFFERED";
	private static final String LI_ISSUED = "LI_ISSUED";
	private static final String LI_REJECTED = "LI_REJECTED";

	public Context mapDataForLifeInsureDetails(ProposalDetails proposalDetails) throws UserHandledException {
		long transactionId = proposalDetails.getTransactionId();
		logger.info("START mapDataForLifeInsureDetails for transactionId {}", transactionId);
		Map<String, Object> dataVariables = new HashMap<>();
		try {
			// 1. existing policy details
			setLiPolicyDetails(proposalDetails, dataVariables);
			// 2. set visits abroad
			setTravelDetails(proposalDetails, dataVariables);
			// 3. set hazardous activities
			setHazardousActivityDetails(proposalDetails, dataVariables);
			// 4. set criminal charges
			setCriminalChargesDetails(proposalDetails, dataVariables);
			// 5. set female insured details
			setFemaleLiDetails(proposalDetails, dataVariables);
			// 6. IIB Details Mapper
			Utility.iibDetailsAdder(proposalDetails, dataVariables);
		} catch (Exception ex) {
			int lineNumber = ex.getStackTrace()[0].getLineNumber();
			logger.error(
					"mapDataForLifeInsureDetails failed for transactionId {} at line {} with exception {}",
					transactionId, lineNumber, ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("%M Data Mapping Failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("mapDataForLifeInsureDetails completed successfully for transactionId {}",
				transactionId);
		Context lifeInsuredDetailsContext = new Context();
		lifeInsuredDetailsContext.setVariables(dataVariables);
		logger.info("END mapDataForLifeInsureDetails");
		return lifeInsuredDetailsContext;
	}

	private void setLiPolicyDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		//
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		boolean isProposer = (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType, schemeType));
		String insuredCiSumAssured = AppConstants.NA;
		String insuredLiOffered = AppConstants.NA;
		String insuredLiSumAssured = AppConstants.NA;
		String isInsuredLiIssued = AppConstants.NA;
		String isInsuredLiRejected = AppConstants.NA;
		String isProposerLiIssued = AppConstants.NA;
		String isProposerLiRejected = AppConstants.NA;
		String proposerCiSumAssured = AppConstants.NA;
		String proposerLiOffered = AppConstants.NA;
		String proposerLiSumAssured = AppConstants.NA;

		List<LifeStyleDetails> lifestyleDetailsList = proposalDetails.getLifeStyleDetails();
		if (!CollectionUtils.isEmpty(lifestyleDetailsList)) {
			LifeStyleDetails proposerLifeStyleDetails = proposalDetails.getLifeStyleDetails().get(0);
			logger.info("Setting Lifestyle details for SELF...");
			proposerLiOffered = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_OFFERED,
					proposerLifeStyleDetails, null, proposalDetails, true);
			isProposerLiIssued = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_ISSUED,
					proposerLifeStyleDetails, null, proposalDetails, true);
			isProposerLiRejected = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_REJECTED,
					proposerLifeStyleDetails, null, proposalDetails, true);
			proposerLiSumAssured = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0)
					.getTotalSumAssuredForLI();
			proposerCiSumAssured = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0)
					.getTotalSumAssuredForCI();
		}

		if (!CollectionUtils.isEmpty(lifestyleDetailsList) && !isProposer && (lifestyleDetailsList.size() > 1)) {
			LifeStyleDetails insuredLifeStyleDetails = proposalDetails.getLifeStyleDetails().get(1);

			insuredLiOffered = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_OFFERED,
					null, insuredLifeStyleDetails, proposalDetails, false);
			isInsuredLiIssued = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_ISSUED,
					null, insuredLifeStyleDetails, proposalDetails, false);
			isInsuredLiRejected = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_REJECTED,
					null, insuredLifeStyleDetails, proposalDetails, false);
			insuredLiSumAssured = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0)
					.getTotalSumAssuredForLI();
			insuredCiSumAssured = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails()
					.get(0).getTotalSumAssuredForCI();
		}

		if (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)) {
			proposerLiOffered = AppConstants.NA;
			isProposerLiIssued = AppConstants.NA;
			isProposerLiRejected = AppConstants.NA;
		}
		if (Utility.schemeBCase(formType, schemeType) || (AppConstants.SELF.equalsIgnoreCase(formType)
				&& (AppConstants.CEIP.equalsIgnoreCase(productDetails.getObjectiveOfInsurance())
						|| AppConstants.EMPLOYER_EMPLOYEE.equalsIgnoreCase(productDetails.getObjectiveOfInsurance()))))
			isInsuredLiIssued = AppConstants.NA;

		dataVariables.put("proposerLiOffered", proposerLiOffered);
		dataVariables.put("isProposerLiIssued", isProposerLiIssued);
		dataVariables.put("isProposerLiRejected", isProposerLiRejected);
		dataVariables.put("proposerLiSumAssured", proposerLiSumAssured);
		dataVariables.put("proposerCiSumAssured", proposerCiSumAssured);
		dataVariables.put("insuredLiOffered", insuredLiOffered);
		dataVariables.put("isInsuredLiIssued", isInsuredLiIssued);
		dataVariables.put("isInsuredLiRejected", isInsuredLiRejected);
		dataVariables.put("insuredLiSumAssured", insuredLiSumAssured);
		dataVariables.put("insuredCiSumAssured", insuredCiSumAssured);
	}

	private void setTravelDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		//
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		boolean isProposer = (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType, schemeType));
		String proposerTravelAbroad = AppConstants.NA;
		String insuredtravelAbroad = AppConstants.NA;
		String proposerVisitedCountries = AppConstants.NA;
		String insuredVisitedCountries = AppConstants.NA;

		List<LifeStyleDetails> lifestyleDetailsList = proposalDetails.getLifeStyleDetails();
		if(!CollectionUtils.isEmpty(lifestyleDetailsList) && !lifestyleDetailsList.isEmpty()) {
			//
			LifeStyleDetails proposerLifeStyleDetails = lifestyleDetailsList.get(0);
			proposerTravelAbroad = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure()) ?
					Utility.ifEmptyThenNA(proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad()) : AppConstants.NO;
			List<String> proposerCiCountryTobeVisited = proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited();
			proposerVisitedCountries = StringUtils.join(proposerCiCountryTobeVisited, ", ");
		}

		if(!CollectionUtils.isEmpty(lifestyleDetailsList) && !isProposer && lifestyleDetailsList.size()>1) {
			//
			LifeStyleDetails insuredLifeStyleDetails = lifestyleDetailsList.get(1);
			insuredtravelAbroad = insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad();
			List<String> insuredCiCountryTobeVisited = insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited();
			insuredVisitedCountries = StringUtils.join(insuredCiCountryTobeVisited, ", ");
		}
		dataVariables.put("proposerTravelAbroad", proposerTravelAbroad);
		dataVariables.put("insuredTravelAbroad", insuredtravelAbroad);
		dataVariables.put("proposerVisitedCountries", proposerVisitedCountries);
		dataVariables.put("insuredVisitedCountries", insuredVisitedCountries);
	}

	private void setHazardousActivityDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		//
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		boolean isProposer = (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType, schemeType));

		String proposerHazardousActivity = AppConstants.NA;
		String insuredHazardousActivity = AppConstants.NA;
		String proposerHazardousActivityDetails = AppConstants.NA;
		String insuredHazardousActivityDetails = AppConstants.NA;

		List<LifeStyleDetails> lifestyleDetailsList = proposalDetails.getLifeStyleDetails();

		if(!CollectionUtils.isEmpty(lifestyleDetailsList) && isProposer && !lifestyleDetailsList.isEmpty()) {
			//
			LifeStyleDetails proposerLifeStyleDetails = lifestyleDetailsList.get(0);
			proposerHazardousActivity = proposerLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities();
			proposerHazardousActivityDetails = proposerLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails().getHazardousActivityExtent();
		}
		if(!CollectionUtils.isEmpty(lifestyleDetailsList) && !isProposer && lifestyleDetailsList.size()>1) {
			//
			LifeStyleDetails insuredLifeStyleDetails = lifestyleDetailsList.get(1);
			insuredHazardousActivity = insuredLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities();
			insuredHazardousActivityDetails = insuredLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails().getHazardousActivityExtent();
		}
		dataVariables.put("proposerHazardousActivity", proposerHazardousActivity);
		dataVariables.put("insuredHazardousActivity", insuredHazardousActivity);
		dataVariables.put("proposerHazardousActivityDetails", proposerHazardousActivityDetails);
		dataVariables.put("insuredHazardousActivityDetails", insuredHazardousActivityDetails);
	}

	private void setCriminalChargesDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		//
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		boolean isProposer = (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType, schemeType));

		String proposerCriminalCharges = AppConstants.NA;
		String insuredCriminalCharges = AppConstants.NA;
		String proposerCriminalChargesDetails = AppConstants.NA;
		String insuredCriminalChargesDetails = AppConstants.NA;

		List<LifeStyleDetails> lifestyleDetailsList = proposalDetails.getLifeStyleDetails();

		if(!CollectionUtils.isEmpty(lifestyleDetailsList) && isProposer && !lifestyleDetailsList.isEmpty()) {
			//
			LifeStyleDetails proposerLifeStyleDetails = lifestyleDetailsList.get(0);
			proposerCriminalCharges = proposerLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges();
			proposerCriminalChargesDetails = proposerLifeStyleDetails.getFamilyOrCriminalHistory().getSpecifyDetails();
		}
		if(!CollectionUtils.isEmpty(lifestyleDetailsList) && !isProposer && lifestyleDetailsList.size()>1) {
			//
			LifeStyleDetails insuredLifeStyleDetails = lifestyleDetailsList.get(1);
			insuredCriminalCharges = insuredLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges();
			insuredCriminalChargesDetails = insuredLifeStyleDetails.getFamilyOrCriminalHistory().getSpecifyDetails();
		}

		dataVariables.put("proposerCriminalCharges", proposerCriminalCharges);
		dataVariables.put("insuredCriminalCharges", insuredCriminalCharges);
		dataVariables.put("proposerCriminalChargesDetails", proposerCriminalChargesDetails);
		dataVariables.put("insuredCriminalChargesDetails", insuredCriminalChargesDetails);
	}

	private void setFemaleLiDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		//
		String formType = proposalDetails.getApplicationDetails().getFormType();
		String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
		boolean isProposer = Utility.orTwoExpressions(AppConstants.SELF.equalsIgnoreCase(formType), Utility.schemeBCase(formType, schemeType));
		proposalDetails.getProductDetails();
		String isLiPregnancyComplicated = AppConstants.NA;
		String isLiPregnant = AppConstants.NA;
		String liPregnantSince = AppConstants.NA;
		String liSpouseAnnualIncome = AppConstants.NA;
		String liSpouseInsurance = AppConstants.NA;
		String liSpouseOccupation = AppConstants.NA;
		String liPregnancyComplication = AppConstants.NA;
		String familyCover = AppConstants.NA;
		String minorLI = AppConstants.NO;
		BasicDetails lifeInsuredDetails = (BasicDetails) Utility.evaluateConditionalOperation(isProposer, proposalDetails.getPartyInformation().get(0).getBasicDetails(), proposalDetails.getPartyInformation().get(1).getBasicDetails());
		LifeStyleDetails lifeStyleDetails = (isProposer) ? proposalDetails.getLifeStyleDetails().get(0): proposalDetails.getLifeStyleDetails().get(1);
		String lifeInsuredGender = (Objects.nonNull(lifeInsuredDetails)) ? lifeInsuredDetails.getGender() : AppConstants.NA;
		boolean isFemale = filter(lifeInsuredGender, (String str) -> str.equalsIgnoreCase("F"));
		boolean isInsuredFemale = false;
		isInsuredFemale = filter(lifeInsuredGender, (String str) -> str.equalsIgnoreCase("F"));
		if (isInsuredFemale && Objects.nonNull(lifeInsuredDetails)) {
			liSpouseOccupation = Utility.ifEmptyThenNA(lifeInsuredDetails.getMarriageDetails().getSpouseOccupation());
			liSpouseAnnualIncome = Utility.ifEmptyThenNA(lifeInsuredDetails.getMarriageDetails().getSpouseAnnualIncome());
			liSpouseInsurance = Utility.ifEmptyThenNA(lifeInsuredDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse());
			if (lifeInsuredDetails.getMarriageDetails().isPregnant()) {
				isLiPregnant = AppConstants.YES;
				liPregnantSince = getPregnantSinceMonths(lifeInsuredDetails.getMarriageDetails().getPregnantSince());
				isLiPregnancyComplicated = Utility.convertToYesOrNo(lifeInsuredDetails.getMarriageDetails().isAnyComplicationToPregnancy());
				liPregnancyComplication = (isLiPregnancyComplicated.equalsIgnoreCase(AppConstants.YES)) ? lifeInsuredDetails.getMarriageDetails().getPregnancyComplicationDetails() : AppConstants.NA;
			} else {
				isLiPregnant = AppConstants.NO;
			}
		}
		if(Objects.nonNull(lifeInsuredDetails)) {
			String insuredAge = Utility.getAge(lifeInsuredDetails.getDob());
			minorLI = checkIfInsuredIsMinor(proposalDetails, insuredAge);
			if (minorLI.equalsIgnoreCase(AppConstants.YES)) {
				familyCover = lifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
			}
		}

		dataVariables.put("isFemale", isFemale);
		dataVariables.put("isInsuredFemale", isInsuredFemale);
		dataVariables.put("liSpouseOccupation", liSpouseOccupation);
		dataVariables.put("liSpouseAnnualIncome", liSpouseAnnualIncome);
		dataVariables.put("liSpouseInsurance", liSpouseInsurance);
		dataVariables.put("isLiPregnant", isLiPregnant);
		dataVariables.put("liPregnantSince", liPregnantSince);
		dataVariables.put("isLiPregnancyComplicated", isLiPregnancyComplicated);
		dataVariables.put("liPregnancyComplication", liPregnancyComplication);
		dataVariables.put("minorLI", minorLI);
		dataVariables.put("familyCover", familyCover);
	}

	public static <T> boolean filter(T str, Predicate<T> p) {
		return p.test(str);
	}

	public static String ifStringNullThenNA(String value) {
		if(StringUtils.isEmpty(value)) {
			return AppConstants.NA;
		} else {
			return value;
		}
	}

	private String getPregnantSinceMonths(String pregnantSince) {
		return (pregnantSince + " Months");
	}

	private String getLICPolicyStatus(String formType, String type, LifeStyleDetails proposerLifeStyleDetails,
			LifeStyleDetails insuredLifeStyleDetails, ProposalDetails proposalDetails, boolean isProposerDataRequested) {
		if (proposalDetails.getProductDetails() == null) {
			return StringUtils.EMPTY;
		}

		InsuranceDetails insuranceDetails = null;
		if ((AppConstants.SELF.equals(formType)
				|| Utility.schemeBCase(formType, proposalDetails.getApplicationDetails().getSchemeType()))
				&& isProposerDataRequested) {
			insuranceDetails = proposerLifeStyleDetails.getInsuranceDetails();
			return getLifeInsuredDeatils(insuranceDetails, type);
		} else if (AppConstants.DEPENDENT.equals(formType)
				|| (AppConstants.FORM3.equalsIgnoreCase(formType)
						&& !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType()))
				|| AppConstants.FORM2.equals(formType)) {
			if (isProposerDataRequested) {
				insuranceDetails = proposerLifeStyleDetails.getInsuranceDetails();
			} else {
				insuranceDetails = insuredLifeStyleDetails.getInsuranceDetails();
			}
			return getLifeInsuredDeatils(insuranceDetails, type);
		}
		return StringUtils.EMPTY;
	}

	private String getLifeInsuredDeatils(InsuranceDetails insuranceDetails, String type) {
		switch (type) {
		case LI_OFFERED:
			return insuranceDetails.isLIPolicyRejected() || insuranceDetails.isLICIPolicyExist() ? AppConstants.YES : AppConstants.NO;
		case LI_ISSUED:
			return insuranceDetails.isLICIPolicyExist() ? AppConstants.YES : AppConstants.NO;
		case LI_REJECTED:
			return insuranceDetails.isLIPolicyRejected() ? AppConstants.YES : AppConstants.NO;
		default:
			return StringUtils.EMPTY;
		}
	}

	private String checkIfInsuredIsMinor(ProposalDetails proposalDetails, String insuredAge) throws NullPointerException{
		try {

			if(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId().equalsIgnoreCase("108")){
				return AppConstants.NA;
			}
			else if(StringUtils.isNotBlank(insuredAge) && !StringUtils.equalsIgnoreCase(insuredAge, "0")){
				if(Float.valueOf(insuredAge) < 18){
					return AppConstants.YES;
				}
				else{
					return AppConstants.NO;
				}
			}
			else{
				return  AppConstants.NO;
			}
		} catch (Exception ex) {
			logger.error("Error checking age for Minor in life insured section:",ex);
		}
		return AppConstants.NO;
	}

}
