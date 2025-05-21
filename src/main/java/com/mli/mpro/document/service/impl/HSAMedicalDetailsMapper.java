package com.mli.mpro.document.service.impl;

import static com.mli.mpro.productRestriction.util.AppConstants.NA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.context.Context;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.MedicalQuestions;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.PosvQuestion;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

@Service
public class HSAMedicalDetailsMapper {
	@Autowired
	private BaseMapper baseMapper;
	private static final Logger logger = LoggerFactory.getLogger(HSAMedicalDetailsMapper.class);

	public Context mapDataForMedicalDetails(ProposalDetails proposalDetails) throws UserHandledException {
		long transactionId = proposalDetails.getTransactionId();
		logger.info("START mapDataForMedicalDetails for transactionId {}", transactionId);
		Map<String, Object> dataVariables = new HashMap<>();
		MedicalQuestions medicalQuestions = null;
		boolean isSspSwissReCase = false;
		String proposerAbhaNumber = AppConstants.BLANK;
		String insurerAbhaNumber = AppConstants.BLANK;
		try {
			//
			String formType = proposalDetails.getApplicationDetails().getFormType();
			String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
			boolean proposerFormFlag = Utility.orTwoExpressions(Utility.schemeBCase(formType, schemeType), Utility.orTwoExpressions(StringUtils.equalsIgnoreCase(formType, "SELF"), StringUtils.equalsIgnoreCase(formType, "form1")));
			boolean isForm2 = filter(formType, (String s) -> s.equalsIgnoreCase(AppConstants.DEPENDENT));
			// FUL2-98619
			boolean isForm3 = Utility.andTwoExpressions(filter(formType, (String s) -> s.equalsIgnoreCase(AppConstants.FORM3)), !Utility.schemeBCase(schemeType));
			isSspSwissReCase = Utility.andThreeExpressions(proposalDetails.getAdditionalFlags() != null
					, proposalDetails.getAdditionalFlags().getIsSspSwissReCase() != null
					, "Y".equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsSspSwissReCase()));

			setHeightWeightDisease60(proposalDetails, dataVariables);


			logger.info("Setting Question Answers for medical section of Proposal Form");
			String qDefault = Utility.evaluateConditionalOperation(proposalDetails.getPosvDetails() != null, NA, AppConstants.WHITE_SPACE);
			List<PosvQuestion> posvQuestionsList = (List<PosvQuestion>) Utility.evaluateConditionalOperation(
					proposalDetails.getPosvDetails() != null, proposalDetails.getPosvDetails(), "posvQuestions",
					new ArrayList<>());
			Map<String, List<PosvQuestion>> posvQAMap = posvQuestionsList.stream()
					.collect(Collectors.groupingBy(PosvQuestion::getParentId));
			Map<String, List<PosvQuestion>> posvQA12Map = posvQuestionsList.stream()
					.collect(Collectors.groupingBy(PosvQuestion::getQuestionId));
			/** Setting POSV Medical Questions response */
			medicalQuestions = setPosvGeneralMedicalQuestions(baseMapper, posvQAMap, posvQA12Map,
					isSspSwissReCase);
			// FUL2-98619
			dataVariables.put("isForm3", isForm3);
			dataVariables.put("isForm2", isForm2);

			// FUL2-195747 PF form changes for ABHA Id
			if (Utility.isJointLifeCase(proposalDetails)) {
				proposerAbhaNumber = Utility.setSecondInsurerJointLifeAbhaNumberInPfForm(proposalDetails);
				insurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
			} else {
				insurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
			}
			dataVariables.put("proposerAbhaNumber",proposerAbhaNumber);
			dataVariables.put("insurerAbhaNumber",insurerAbhaNumber);


			dataVariables.put("proposerFormFlag", proposerFormFlag);
			dataVariables.put("qDefault", qDefault);

			dataVariables.put("medicalQuestions", medicalQuestions);
			dataVariables.put("isThanos",
					AppConstants.THANOS_CHANNEL.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()));

			// insured pregnancy ques
			setPregnancyDetails(proposalDetails, dataVariables, isForm2);

			if (!CollectionUtils.isEmpty(proposalDetails.getProductDetails())
					&& !ObjectUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo())) {
				// DRUGS CONSUMPTION ques for life insured
				boolean isTobaccoConsumed = AppConstants.YES.equals(getMedicalQuestionIdAnswer(posvQA12Map, "H13fi"));
				boolean isAlcoholConsumed = AppConstants.YES.equals(getMedicalQuestionIdAnswer(posvQA12Map, "H13Fii"));
				dataVariables.put("isTobaccoConsumed", isTobaccoConsumed);
				dataVariables.put("isAlcoholConsumed", isAlcoholConsumed);

				String tobaccoQtPerDay = NA;
				String tobaccoConsumptionYrs = NA;
				if (AppConstants.YES.equals(getMedicalQuestionIdAnswer(posvQA12Map, "H13fi"))) {
					tobaccoQtPerDay = getMedicalQuestionIdAnswer(posvQA12Map, "H13Fib");
					tobaccoConsumptionYrs = getMedicalQuestionIdAnswer(posvQA12Map, "H13Fid");
				}
				dataVariables.put("tobaccoQtPerDay", tobaccoQtPerDay);
				dataVariables.put("tobaccoConsumptionYrs", tobaccoConsumptionYrs);

				String alcoholQtPerDay = NA;
				String alcoholConsumptionYrs = NA;
				if (AppConstants.YES.equals(getMedicalQuestionIdAnswer(posvQA12Map, "H13Fii"))) {
					alcoholQtPerDay = getMedicalQuestionIdAnswer(posvQA12Map, "H13Fiib");
					alcoholConsumptionYrs = getMedicalQuestionIdAnswer(posvQA12Map, "H13Fiid");
				}
				dataVariables.put("alcoholQtPerDay", alcoholQtPerDay);
				dataVariables.put("alcoholConsumptionYrs", alcoholConsumptionYrs);
				dataVariables.put("drugConsumed", getMedicalQuestionIdAnswer(posvQA12Map, "H13F"));
			}
		} catch (Exception ex) {
			int lineNumber = ex.getStackTrace()[0].getLineNumber();
			logger.error(
					"mapDataForMedicalDetails failed for transactionId {} at line {} with exception {}",
					transactionId, lineNumber, ex);
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("FAIL mapDataForMedicalDetails Data Mapping Failed");
			throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("mapDataForMedicalDetails completed successfully for transactionId {}",
				transactionId);
		Context medicalDetailsContext = new Context();
		medicalDetailsContext.setVariables(dataVariables);
		logger.info("END mapDataForMedicalDetails");
		return medicalDetailsContext;
	}

	private static void setPregnancyDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables, boolean isForm2) {
		//
		String isInsuredPregnant = "";
		BasicDetails insuredDetails = proposalDetails.getPartyInformation().size() > 1
				? proposalDetails.getPartyInformation().get(1).getBasicDetails()
				: proposalDetails.getPartyInformation().get(0).getBasicDetails();
		String insuredGender = "";
		insuredGender = (Objects.nonNull(insuredDetails) && !StringUtils.isEmpty(insuredDetails.getGender()))
				? insuredDetails.getGender()
				: AppConstants.BLANK;
		boolean isInsuredFemale = filter(insuredGender, (String str) -> str.equalsIgnoreCase("F"));
		if(isInsuredFemale && isForm2) {
			isInsuredPregnant = AppConstants.NA;
		} else {
			isInsuredPregnant = (Objects.nonNull(insuredDetails) && insuredDetails.getMarriageDetails().isPregnant() ) ? AppConstants.YES : AppConstants.NO;
		}
		dataVariables.put("isInsuredPregnant", isInsuredPregnant);
		if (AppConstants.YES.equals(isInsuredPregnant)) {
			String pregnantSince = Objects.nonNull(insuredDetails) && !StringUtils.isEmpty(insuredDetails.getMarriageDetails().getPregnantSince())
					? getPregnantSinceMonths(insuredDetails.getMarriageDetails().getPregnantSince())
							.concat(AppConstants.MONTHS)
					: NA;
			dataVariables.put("pregnantSince", pregnantSince);
		}
	}

	private static void setHeightWeightDisease60(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
		List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
		String proposerHeight = NA;
		String proposerWeight = NA;
		String insuredHeight = NA;
		String insuredWeight = NA;
		String familyDiagnosedWithDiseases60 = NA;
		String insuredFamilyDiagnosedWithDiseases60 = NA;
		if (!CollectionUtils.isEmpty(lifeStyleDetailsList)) {
			proposerHeight = Utility.evaluateConditionalOperation(
					Utility.orTwoExpressions(
							StringUtils.isEmpty(lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight()),
							StringUtils.equalsIgnoreCase(lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight(),
									"0")),
					NA, String.join(AppConstants.WHITE_SPACE,
							lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight(), AppConstants.HEIGHT_UNIT));

			proposerWeight = Utility.evaluateConditionalOperation(
					Utility.orTwoExpressions(
							StringUtils.isEmpty(lifeStyleDetailsList.get(0).getHeightAndWeight().getWeight()),
							StringUtils.equalsIgnoreCase(lifeStyleDetailsList.get(0).getHeightAndWeight().getWeight(),
									"0")),
					NA, String.join(AppConstants.WHITE_SPACE,
							lifeStyleDetailsList.get(0).getHeightAndWeight().getWeight(), AppConstants.WEIGHT_UNIT));

			familyDiagnosedWithDiseases60 = (String) Utility.evaluateConditionalOperation(
					Utility.andTwoExpressions(null != lifeStyleDetailsList.get(0).getFamilyOrCriminalHistory(),
							StringUtils.isNotBlank(lifeStyleDetailsList.get(0).getFamilyOrCriminalHistory()
									.getFamilyDiagnosedWithDiseasesBefore60())),
					lifeStyleDetailsList.get(0).getFamilyOrCriminalHistory(), "familyDiagnosedWithDiseasesBefore60",
					NA);

			if (lifeStyleDetailsList.size() > 1) {
				insuredHeight = Utility.evaluateConditionalOperation(
						Utility.orTwoExpressions(
								StringUtils.isEmpty(lifeStyleDetailsList.get(1).getHeightAndWeight().getHeight()),
								StringUtils.equalsIgnoreCase(
										lifeStyleDetailsList.get(1).getHeightAndWeight().getHeight(), "0")),
						NA,
						String.join(AppConstants.WHITE_SPACE,
								lifeStyleDetailsList.get(1).getHeightAndWeight().getHeight(),
								AppConstants.HEIGHT_UNIT));

				insuredWeight = Utility.evaluateConditionalOperation(
						Utility.orTwoExpressions(
								StringUtils.isEmpty(lifeStyleDetailsList.get(1).getHeightAndWeight().getWeight()),
								StringUtils.equalsIgnoreCase(
										lifeStyleDetailsList.get(1).getHeightAndWeight().getWeight(), "0")),
						NA,
						String.join(AppConstants.WHITE_SPACE,
								lifeStyleDetailsList.get(1).getHeightAndWeight().getWeight(),
								AppConstants.WEIGHT_UNIT));

				insuredFamilyDiagnosedWithDiseases60 = (String) Utility.evaluateConditionalOperation(
						(null != lifeStyleDetailsList.get(1).getFamilyOrCriminalHistory()
								&& StringUtils.isNotBlank(lifeStyleDetailsList.get(1).getFamilyOrCriminalHistory()
										.getFamilyDiagnosedWithDiseasesBefore60())),
						lifeStyleDetailsList.get(1).getFamilyOrCriminalHistory(), "familyDiagnosedWithDiseasesBefore60",
						NA);
			}

		}
		dataVariables.put("proposerHeight", proposerHeight);
		dataVariables.put("proposerWeight", proposerWeight);
		dataVariables.put("insuredHeight", insuredHeight);
		dataVariables.put("insuredWeight", insuredWeight);
		dataVariables.put("familyDiagnosedWithDiseases60", familyDiagnosedWithDiseases60);
		dataVariables.put("insuredFamilyDiagnosedWithDiseases60", insuredFamilyDiagnosedWithDiseases60);
	}

	public static <T> boolean filter(T str, Predicate<T> p) {
		return p.test(str);
	}

	private MedicalQuestions setPosvGeneralMedicalQuestions(BaseMapper baseMapper, Map<String, List<PosvQuestion>> posvQAMap, Map<String, List<PosvQuestion>> posvQA12Map, boolean isSspSwissReCase) {
		MedicalQuestions medicalQuestions = new MedicalQuestions();
		medicalQuestions.setQuestion1Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H3"));
		medicalQuestions.setQuestion1AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H3", "H3A"));
		medicalQuestions.setQuestion1BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H3", "H3B"));
		medicalQuestions.setQuestion2Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H2"));
		medicalQuestions.setQuestion2AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2", "H2A"));
		//Medical DHU changes
		medicalQuestions.setQuestion2EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2A", "H2A1"));
		medicalQuestions.setQuestion2FChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2A", "H2A2"));
		medicalQuestions.setQuestion2GChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2A", "H2A3"));
		medicalQuestions.setQuestion2HChoice(Utility.getArrayAns("H2A3","H2A4",baseMapper,posvQAMap));
		medicalQuestions.setQuestion2IChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2A3", "H2A5"));
		medicalQuestions.setQuestion2JChoice(Utility.getArrayAns("H2A","H2A6",baseMapper,posvQAMap));
		medicalQuestions.setQuestion2KChoice(Utility.getArrayAnsYesOrNoResponse(medicalQuestions.getQuestion2JChoice()));
		medicalQuestions.setQuestion2BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2", "H2B"));
		medicalQuestions.setQuestion2CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2", "H2C"));
		medicalQuestions.setQuestion2DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H2", "H2D"));
		medicalQuestions.setQuestion3Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H1"));
		medicalQuestions.setQuestion3AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H1", "H1A"));
		medicalQuestions.setQuestion3BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H1", "H1B"));
		medicalQuestions.setQuestion3CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H1", "H1C"));
		medicalQuestions.setQuestion3DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H1", "H1D"));
		//Medical DHU changes
		medicalQuestions.setQuestion3EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H1", "H1E"));
		medicalQuestions.setQuestion3FChoice(Utility.getArrayAns("H1","H1F",baseMapper,posvQAMap));
		medicalQuestions.setQuestion3GChoice(Utility.getArrayAnsYesOrNoResponse( medicalQuestions.getQuestion3FChoice()));
		medicalQuestions.setQuestion4Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H4"));
		medicalQuestions.setQuestion4AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4", "H4A"));
		medicalQuestions.setQuestion4BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4", "H4B"));
		medicalQuestions.setQuestion4CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4", "H4C"));
		//Medical DHU changes
		medicalQuestions.setQuestion4C1Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4C", "H4C1"));
		medicalQuestions.setQuestion4C2Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4C", "H4C2"));
		medicalQuestions.setQuestion4C3Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4C", "H4C3"));
		medicalQuestions.setQuestion4C4Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4C", "H4C4"));
		medicalQuestions.setQuestion4DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H4", "H4D"));

		medicalQuestions.setQuestion5Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H6"));
		medicalQuestions.setQuestion5AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6A"));
		medicalQuestions.setQuestion5BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6B"));
		medicalQuestions.setQuestion5CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6C"));
		medicalQuestions.setQuestion5WChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6D"));

		medicalQuestions.setQuestion6Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H5"));
		medicalQuestions.setQuestion6AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H5", "H5A"));
		medicalQuestions.setQuestion6BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H5", "H5B"));
		medicalQuestions.setQuestion6CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H5", "H5C"));
		medicalQuestions.setQuestion6DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H5", "H5D"));
		medicalQuestions.setQuestion6EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H5", "H5E"));

		medicalQuestions.setQuestion7Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H8"));
		medicalQuestions.setQuestion7AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H8", "H8A"));

		medicalQuestions.setQuestion8Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H6"));
		medicalQuestions.setQuestion8AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6A"));
		medicalQuestions.setQuestion8BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6B"));
		medicalQuestions.setQuestion8CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6C"));

		medicalQuestions.setQuestion9Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H7"));
		medicalQuestions.setQuestion9AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H7", "H7A"));
		medicalQuestions.setQuestion9BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H7", "H7B"));
		medicalQuestions.setQuestion9CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H7", "H7C"));
		medicalQuestions.setQuestion9DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H7", "H7D"));

		medicalQuestions.setQuestion10Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H8"));
		medicalQuestions.setQuestion10AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H8", "H8A"));

		medicalQuestions.setQuestion12Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H9"));
		medicalQuestions.setQuestion12AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H9", "H9A"));
		medicalQuestions.setQuestion12BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H9", "H9B"));
		medicalQuestions.setQuestion12CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H9", "H9C"));
		medicalQuestions.setQuestion12DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H9", "H9D"));
		medicalQuestions.setQuestion12EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H9", "H9E"));
		medicalQuestions.setQuestion12FChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H9", "H9F"));

		medicalQuestions.setQuestion13Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H11"));
		medicalQuestions.setQuestion13AAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11A"));
		medicalQuestions.setQuestion13BAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11B"));
		medicalQuestions.setQuestion13CAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11C"));
		medicalQuestions.setQuestion13DAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11D"));
		medicalQuestions.setQuestion13EAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11E"));
		medicalQuestions.setQuestion13FAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11F"));
		medicalQuestions.setQuestion13GAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11G"));
		medicalQuestions.setQuestion13HAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11H"));
		medicalQuestions.setQuestion13IAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11I"));
		medicalQuestions.setQuestion13JAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11J"));
		medicalQuestions.setQuestion13KAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11K"));
		medicalQuestions.setQuestion13LAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11L"));
		medicalQuestions.setQuestion13MAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11M"));
		medicalQuestions.setQuestion13NAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H11", "H11N"));

		medicalQuestions.setQuestion14Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H6"));
		medicalQuestions.setQuestion14AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6A"));
		medicalQuestions.setQuestion14BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6B"));
		medicalQuestions.setQuestion14CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H6", "H6C"));

		medicalQuestions.setQuestion15Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H10"));
		medicalQuestions.setQuestion15AAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10A"));
		medicalQuestions.setQuestion15BAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10B"));
		medicalQuestions.setQuestion15CAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10C"));
		medicalQuestions.setQuestion15DAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10D"));
		medicalQuestions.setQuestion15EAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10E"));
		medicalQuestions.setQuestion15FAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10F"));
		medicalQuestions.setQuestion15GAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H10", "H10G"));
		medicalQuestions.setQuestion16Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H14"));
		medicalQuestions.setQuestion16AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H14", "H14A"));
		medicalQuestions.setQuestion17Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H15"));
		medicalQuestions.setQuestion17AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H15", "H15A"));

		if(!isSspSwissReCase){
			medicalQuestions.setQuestionTobaccoAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19"));
			medicalQuestions.setQuestionTobaccoSmokingAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19i"));
			medicalQuestions.setQuestionTobaccoSmokingQuantityAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19i1"));
			medicalQuestions.setQuestionTobaccoSmokingFrequencyAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19i2"));
			medicalQuestions.setQuestionTobaccoEatingAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19ii"));
			medicalQuestions.setQuestionTobaccoEatingQuantityAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19ii1"));
			medicalQuestions.setQuestionTobaccoEatingFrequencyAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H19ii2"));
			medicalQuestions.setQuestionAlcoholAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20"));
			medicalQuestions.setQuestionAlcoholBeerAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20i"));
			medicalQuestions.setQuestionAlcoholBeerQuantityAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20i1"));
			medicalQuestions.setQuestionAlcoholBeerFrequencyAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20i2"));
			medicalQuestions.setQuestionAlcoholWineAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20ii"));
			medicalQuestions.setQuestionAlcoholWineQuantityAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20ii1"));
			medicalQuestions.setQuestionAlcoholWineFrequencyAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20ii2"));
			medicalQuestions.setQuestionAlcoholHardLiquorAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20iii"));
			medicalQuestions.setQuestionAlcoholHardLiquorQuantityAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20iii1"));
			medicalQuestions.setQuestionAlcoholHardLiquorFrequencyAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H20iii2"));
			medicalQuestions.setQuestionDrugsAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H12C"));
			medicalQuestions.setQuestionDrugsDetailsAnswer(getMedicalQuestionIdAnswer(posvQA12Map, "H12Ci"));
			if (medicalQuestions.getQuestionTobaccoAnswer().equalsIgnoreCase("NO")
					&& medicalQuestions.getQuestionAlcoholAnswer().equalsIgnoreCase("NO")
					&& medicalQuestions.getQuestionDrugsAnswer().equalsIgnoreCase("NO")) {
				medicalQuestions.setQuestionTobaccoAlcoholDrugConsumption("NO");
			} else {
				medicalQuestions.setQuestionTobaccoAlcoholDrugConsumption("YES");
			}

		} else {
			medicalQuestions.setQuestionTobaccoAlcoholDrugConsumption(AppConstants.NA);
			medicalQuestions.setQuestionTobaccoSmokingAnswer(AppConstants.NA);
			medicalQuestions.setQuestionTobaccoSmokingQuantityAnswer(AppConstants.NA);
			medicalQuestions.setQuestionTobaccoSmokingFrequencyAnswer(AppConstants.NA);
			medicalQuestions.setQuestionTobaccoEatingAnswer(AppConstants.NA);
			medicalQuestions.setQuestionTobaccoEatingQuantityAnswer(AppConstants.NA);
			medicalQuestions.setQuestionTobaccoEatingFrequencyAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholBeerAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholBeerQuantityAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholBeerFrequencyAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholWineAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholWineQuantityAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholWineFrequencyAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholHardLiquorAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholHardLiquorQuantityAnswer(AppConstants.NA);
			medicalQuestions.setQuestionAlcoholHardLiquorFrequencyAnswer(AppConstants.NA);
			medicalQuestions.setQuestionDrugsAnswer(AppConstants.NA);
			medicalQuestions.setQuestionDrugsDetailsAnswer(AppConstants.NA);
		}

		return medicalQuestions;
	}

	private String getMedicalQuestionIdAnswer(Map<String, List<PosvQuestion>> posvQAMap, String questionId) {
		logger.info("getMedicalQuestionIdAnswer questionId: {} ", questionId);
		List<PosvQuestion> posvQAList = posvQAMap.get(questionId);

		if (!CollectionUtils.isEmpty(posvQAList)) {

			String answer = posvQAList.get(0).getAnswer();

			if (StringUtils.equalsIgnoreCase(answer, "Y")) {
				return "YES";
			} else if (StringUtils.equalsIgnoreCase(answer, "N")) {
				return "NO";
			} else if (StringUtils.equalsIgnoreCase(answer, AppConstants.NA) || answer == null) {
				return AppConstants.NA;
			} else {
				return answer;
			}
		}
		return "NA";
	}

	private static String getPregnantSinceMonths(String pregnantSince) {
		return pregnantSince;
	}

}
