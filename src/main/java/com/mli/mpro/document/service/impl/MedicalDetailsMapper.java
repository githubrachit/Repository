/**
 *
 */
package com.mli.mpro.document.service.impl;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.BooleanUtils;
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

/**
 * @author akshom4375 Mapper class for Medical Section of Proposal Form document
 */
@Service
public class MedicalDetailsMapper {
	@Autowired
	private BaseMapper baseMapper;
    private static final Logger logger = LoggerFactory.getLogger(MedicalDetailsMapper.class);

    /**
     * Populate Relevant data for Proposal Document generation in Context
     *
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    public Context setMedicalData(ProposalDetails proposalDetails) throws UserHandledException {
	logger.info("START {}", "%m");
	Context context = new Context();
	Map<String, Object> dataForDocument = new HashMap<>();
	MedicalQuestions medicalQuestions = null;
	String qDefault = null;
	boolean swpFlag = false;
	String wopRiderInfo = "";
	boolean isRiderRequired = false;
	boolean isWopFlag = false;
	boolean healthQuesSet = false;
	boolean sspJointFlag=false;
	boolean isSspSwissReCase = Utility.isSSPSwissReCase(proposalDetails);
	logger.info("Mapping medical details of proposal form document for transactionId {}", proposalDetails.getTransactionId());
	try {
	    List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
	    String formType = proposalDetails.getApplicationDetails().getFormType();
	    String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        boolean proposerFormFlag = (StringUtils.equalsIgnoreCase(formType, "SELF") ||
                StringUtils.equalsIgnoreCase(formType, "form1")
				|| Utility.schemeBCase(formType, schemeType));

        String proposerHeight =AppConstants.NA;
        String proposerWeight =AppConstants.NA;
        String insuredHeight=AppConstants.NA;
        String insuredWeight =AppConstants.NA;
        String familyDiagnosedWithDiseases60 =AppConstants.NA;
        String insuredFamilyDiagnosedWithDiseases60=AppConstants.NA;
		String isInsuredPregnant = "";
		String proposerAbhaNumber = AppConstants.BLANK;
		String insurerAbhaNumber = AppConstants.BLANK;
		boolean isForm2 = filter(formType,(String s)-> s.equalsIgnoreCase(AppConstants.DEPENDENT));
		//FUL2-98619
		boolean isForm3 = filter(formType,(String s)-> s.equalsIgnoreCase(AppConstants.FORM3)) && !Utility.schemeBCase(schemeType);

		if(!CollectionUtils.isEmpty(lifeStyleDetailsList)){
            proposerHeight = Utility.evaluateConditionalOperation(
                    Utility.orTwoExpressions(StringUtils.isEmpty(lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight())
                            , StringUtils.equalsIgnoreCase(lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight(), "0"))
                    , AppConstants.NA
                    , String.join(AppConstants.WHITE_SPACE, lifeStyleDetailsList.get(0).getHeightAndWeight().getHeight(),
                            AppConstants.HEIGHT_UNIT));


            proposerWeight =  Utility.evaluateConditionalOperation(Utility.orTwoExpressions(StringUtils.isEmpty(lifeStyleDetailsList.get(0).getHeightAndWeight().getWeight())
                    , StringUtils.equalsIgnoreCase(lifeStyleDetailsList.get(0).getHeightAndWeight().getWeight(), "0"))
                    , AppConstants.NA
                    , String.join(AppConstants.WHITE_SPACE, lifeStyleDetailsList.get(0).getHeightAndWeight().getWeight(),
                            AppConstants.WEIGHT_UNIT) );

            familyDiagnosedWithDiseases60 = (String) Utility.evaluateConditionalOperation((null != lifeStyleDetailsList.get(0).getFamilyOrCriminalHistory()
                            && StringUtils.isNotBlank(lifeStyleDetailsList.get(0).getFamilyOrCriminalHistory().getFamilyDiagnosedWithDiseasesBefore60()))
                    , lifeStyleDetailsList.get(0).getFamilyOrCriminalHistory(),"familyDiagnosedWithDiseasesBefore60"
                    , AppConstants.NA);

            if( lifeStyleDetailsList.size() > 1){
                insuredHeight =Utility.evaluateConditionalOperation(Utility.orTwoExpressions  (StringUtils.isEmpty(lifeStyleDetailsList.get(1).getHeightAndWeight().getHeight())
                        , StringUtils.equalsIgnoreCase(lifeStyleDetailsList.get(1).getHeightAndWeight().getHeight(), "0"))
                        , AppConstants.NA
                        , String.join(AppConstants.WHITE_SPACE, lifeStyleDetailsList.get(1).getHeightAndWeight().getHeight(),
                                AppConstants.HEIGHT_UNIT) );

                insuredWeight =   Utility.evaluateConditionalOperation(Utility.orTwoExpressions (StringUtils.isEmpty(lifeStyleDetailsList.get(1).getHeightAndWeight().getWeight())
                        , StringUtils.equalsIgnoreCase(lifeStyleDetailsList.get(1).getHeightAndWeight().getWeight(), "0"))
                        , AppConstants.NA
                        , String.join(AppConstants.WHITE_SPACE, lifeStyleDetailsList.get(1).getHeightAndWeight().getWeight(),
                                AppConstants.WEIGHT_UNIT) );

                insuredFamilyDiagnosedWithDiseases60 = (String)Utility.evaluateConditionalOperation ((null != lifeStyleDetailsList.get(1).getFamilyOrCriminalHistory()
                                && StringUtils.isNotBlank(lifeStyleDetailsList.get(1).getFamilyOrCriminalHistory().getFamilyDiagnosedWithDiseasesBefore60()))
                        , lifeStyleDetailsList.get(1).getFamilyOrCriminalHistory(),"familyDiagnosedWithDiseasesBefore60"
                        , AppConstants.NA);
            }

        }


		if (!CollectionUtils.isEmpty(proposalDetails.getProductDetails()) &&
				null != proposalDetails.getProductDetails().get(0).getProductInfo()
				&& !CollectionUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails())
				&& proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2) {

			if(AppConstants.SWP_PRODUCTCODE.equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId()) && AppConstants.WHOLE_LIFE_INCOME.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getVariant())){
				swpFlag = true;
			}
			//FUL2-120213-SSP_JointLife_Changes
			if (proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
					&& Utility.isSSPJointLife(proposalDetails.getProductDetails().get(0))) {
				sspJointFlag = true;
				proposerFormFlag = false;
			}
		}
		if (!CollectionUtils.isEmpty(proposalDetails.getProductDetails()) &&
				!ObjectUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo())
				&& !CollectionUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails())) {
			isRiderRequired = Utility.isWOPPresent(proposalDetails) || Utility.isPayorBenefitRiderPresent(proposalDetails);
			if (isRiderRequired) {
				wopRiderInfo = AppConstants.AXIS_WOP;
			}
		}
			//Check Riderinfo values
		isWopFlag = (isRiderRequired && !proposerFormFlag);

			if(swpFlag || isWopFlag || sspJointFlag) {
				healthQuesSet = true;
			}
			logger.info("wop flag is -- {}",isWopFlag);

        logger.info("Setting Question Answers for medical section of Proposal Form");
        qDefault =Utility.evaluateConditionalOperation( proposalDetails.getPosvDetails() != null , AppConstants.NA , AppConstants.WHITE_SPACE);
        List<PosvQuestion> posvQuestionsList =(List<PosvQuestion>) Utility.evaluateConditionalOperation(proposalDetails.getPosvDetails() != null ,proposalDetails.getPosvDetails(),"posvQuestions",
                new ArrayList<>());
        Map<String, List<PosvQuestion>> posvQAMap = posvQuestionsList.stream().collect(Collectors.groupingBy(PosvQuestion::getParentId));
        Map<String, List<PosvQuestion>> posvQA12Map = posvQuestionsList.stream().collect(Collectors.groupingBy(PosvQuestion::getQuestionId));


        /** Setting POSV Medical Questions response */
		medicalQuestions = new MedicalQuestions();
		medicalQuestions = setPosvGeneralMedicalQuestions(medicalQuestions, baseMapper, posvQAMap, posvQA12Map, isSspSwissReCase);

		/**SWP Question Answers*/
		medicalQuestions = setPosvSWPMedicalQuestions(medicalQuestions, baseMapper, posvQAMap, posvQA12Map,sspJointFlag);

	    logger.info("Setting Question Answers for medical section of Proposal Form");
	    qDefault = proposalDetails.getPosvDetails() != null ? AppConstants.NA : AppConstants.WHITE_SPACE;

		// FUL2-195747 PF form changes for ABHA Id
		if (Utility.isJointLifeCase(proposalDetails)) {
			proposerAbhaNumber = Utility.setSecondInsurerJointLifeAbhaNumberInPfForm(proposalDetails);
			insurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
		} else {
			insurerAbhaNumber = Utility.setInsurerAbhaNumberInPfForm(proposalDetails);
		}

           //FUL2-98619
		dataForDocument.put("isForm3", isForm3);
		dataForDocument.put("isForm2", isForm2);

	    dataForDocument.put("proposerHeight", proposerHeight);
	    dataForDocument.put("proposerWeight", proposerWeight);
	    dataForDocument.put("proposerFormFlag", proposerFormFlag);
	    dataForDocument.put("qDefault", qDefault);
	    dataForDocument.put("familyDiagnosedWithDiseases60", familyDiagnosedWithDiseases60);
	    dataForDocument.put("insuredFamilyDiagnosedWithDiseases60", insuredFamilyDiagnosedWithDiseases60);
		dataForDocument.put("wopRiderInfo", wopRiderInfo);
	    dataForDocument.put("isRiderRequired", isRiderRequired);
		dataForDocument.put("swpFlag", swpFlag);
		dataForDocument.put("sspJointFlag",sspJointFlag);
	    dataForDocument.put("medicalQuestions", medicalQuestions);
	    dataForDocument.put("healthQuesSet",healthQuesSet);
		dataForDocument.put("isThanos", AppConstants.THANOS_CHANNEL
				.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()));
		dataForDocument.put("proposerAbhaNumber",proposerAbhaNumber);
		dataForDocument.put("insurerAbhaNumber",insurerAbhaNumber);

		//FUL2- 14812 changes POS PF
	    //FUL2-36693 adding short and long term variants in swp pos seller
		if (!CollectionUtils.isEmpty(proposalDetails.getProductDetails())) {
			if (!ObjectUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo())) {

				ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
				String productId = productDetails.getProductInfo().getProductId();
				String variant = productDetails.getProductInfo().getVariant();
				boolean isPosSeller = proposalDetails.getSourcingDetails() != null && proposalDetails.getSourcingDetails().isPosSeller();
				boolean isSwpVariantEligible = (AppConstants.SWP_PRODUCTCODE.equals(productId)
						&& (AppConstants.LUMP_SUM.equalsIgnoreCase(variant)
						|| AppConstants.SHORTTERM_INCOME.equalsIgnoreCase(variant)
						|| AppConstants.LONGTERM_INCOME.equalsIgnoreCase(variant)));
				boolean isSwagVariantEligible = (AppConstants.SWAG.equals(productId) 
						&& (AppConstants.EARLY_WEALTH.contentEquals(variant) 
						|| AppConstants.WEALTH_FOR_MILESTONES.equalsIgnoreCase(variant) 
						|| AppConstants.REGULAR_WEALTH.equalsIgnoreCase(variant)));
				if(isPosSeller && (isSwpVariantEligible || isSwagVariantEligible)){
					dataForDocument.put("posmed1",getMedicalQuestion12Answer(posvQA12Map,"POSMED1"));
					dataForDocument.put("posmed2",getMedicalQuestion12Answer(posvQA12Map,"POSMED2"));

					//insured pregnancy ques
					BasicDetails insuredDetails = proposalDetails.getPartyInformation().size() > 1
							? proposalDetails.getPartyInformation().get(1).getBasicDetails() : proposalDetails.getPartyInformation().get(0).getBasicDetails();
					String insuredGender = "";
					insuredGender = (Objects.nonNull(insuredDetails) && !StringUtils.isEmpty(insuredDetails.getGender())) ? insuredDetails.getGender() : AppConstants.BLANK ;
					boolean isInsuredFemale = filter(insuredGender,(String str)-> str.equalsIgnoreCase("F"));
					isInsuredPregnant = (isInsuredFemale && (isForm2 || isForm3  )) ? (insuredDetails.getMarriageDetails().isPregnant()
							? AppConstants.YES : AppConstants.NO) : AppConstants.NA;
					dataForDocument.put("isInsuredPregnant",isInsuredPregnant);
					if(AppConstants.YES.equals(isInsuredPregnant)){
						String pregnantSince = !StringUtils.isEmpty(insuredDetails.getMarriageDetails().getPregnantSince())
								? getPregnantSinceMonths(insuredDetails.getMarriageDetails().getPregnantSince()).concat(AppConstants.MONTHS)
								: AppConstants.NA;
						dataForDocument.put("pregnantSince",pregnantSince);
					}

					//DRUGS CONSUMPTION ques for life insured
					boolean isTobaccoConsumed = AppConstants.YES.equals(getMedicalQuestion12Answer(posvQA12Map,"H13fi"));
					boolean isAlcoholConsumed = AppConstants.YES.equals(getMedicalQuestion12Answer(posvQA12Map,"H13Fii"));
					dataForDocument.put("isTobaccoConsumed", isTobaccoConsumed);
					dataForDocument.put("isAlcoholConsumed", isAlcoholConsumed);
					
					String tobaccoQtPerDay=AppConstants.NA;
					String tobaccoConsumptionYrs=AppConstants.NA;
					if(AppConstants.YES.equals(getMedicalQuestion12Answer(posvQA12Map,"H13fi"))){
						tobaccoQtPerDay=getMedicalQuestion12Answer(posvQA12Map,"H13Fib");
						tobaccoConsumptionYrs=getMedicalQuestion12Answer(posvQA12Map,"H13Fid");
					}
					dataForDocument.put("tobaccoQtPerDay",tobaccoQtPerDay);
					dataForDocument.put("tobaccoConsumptionYrs",tobaccoConsumptionYrs);

					String alcoholQtPerDay=AppConstants.NA;
					String alcoholConsumptionYrs=AppConstants.NA;
					if(AppConstants.YES.equals(getMedicalQuestion12Answer(posvQA12Map,"H13Fii"))){
						alcoholQtPerDay=getMedicalQuestion12Answer(posvQA12Map,"H13Fiib");
						alcoholConsumptionYrs=getMedicalQuestion12Answer(posvQA12Map,"H13Fiid");
					}
					dataForDocument.put("alcoholQtPerDay",alcoholQtPerDay);
					dataForDocument.put("alcoholConsumptionYrs",alcoholConsumptionYrs);
					dataForDocument.put("drugConsumed",getMedicalQuestion12Answer(posvQA12Map,"H13F"));

					if((AppConstants.NA.equals(insuredHeight) || StringUtils.isEmpty(insuredHeight)) && (AppConstants.NA.equals(insuredWeight) || StringUtils.isEmpty(insuredWeight)))
					{
						insuredHeight = proposerHeight;
						insuredWeight = proposerWeight;
					}
				}
			}
		}

		dataForDocument.put("insuredHeight", insuredHeight);
		dataForDocument.put("insuredWeight", insuredWeight);
	    context.setVariables(dataForDocument);

	} catch (Exception ex) {
	    logger.error("Data addition failed for Proposal Form  Document for transactionId {} : ", proposalDetails.getTransactionId(), ex);
	    List<String> errorMessages = new ArrayList<>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	logger.info("Mapping medical details of proposal form document is completed successfully for transactionId {}", proposalDetails.getTransactionId());
	return context;
    }

    // FUL2-114196
	private String getPregnantSinceMonths(String pregnantSince) {
		return pregnantSince;
	}
	public static <T> boolean filter(T str, Predicate<T> p){
		return p.test(str);
	}
	private MedicalQuestions setPosvSWPMedicalQuestions(MedicalQuestions medicalQuestions, BaseMapper baseMapper, Map<String, List<PosvQuestion>> posvQAMap, Map<String, List<PosvQuestion>> posvQA12Map , boolean sspJointFlag) {
		medicalQuestions.setQuestion18Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H27"));
		medicalQuestions.setQuestion18AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H27", "H27A"));
		medicalQuestions.setQuestion18BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H27", "H27B"));
		medicalQuestions.setQuestion19Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H28"));
		medicalQuestions.setQuestion19AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28", "H28A"));
		medicalQuestions.setQuestion19BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28", "H28B"));
		medicalQuestions.setQuestion19CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28", "H28C"));
		//Medical DHU changes
		medicalQuestions.setQuestion19C1Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28C", "H28C1"));
		medicalQuestions.setQuestion19C2Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28C", "H28C2"));
		medicalQuestions.setQuestion19C3Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28C", "H28C3"));
		medicalQuestions.setQuestion19C4Choice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28C", "H28C4"));
		medicalQuestions.setQuestion19DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H28", "H28D"));
		medicalQuestions.setQuestion20Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H29"));
		medicalQuestions.setQuestion20AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H29", "H29A"));
		medicalQuestions.setQuestion20BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H29", "H29B"));
		medicalQuestions.setQuestion20CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H29", "H29C"));
		medicalQuestions.setQuestion20DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H29", "H29D"));
		medicalQuestions.setQuestion20EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H29", "H29E"));
		medicalQuestions.setQuestion21Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H30"));
		medicalQuestions.setQuestion21AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H30", "H30A"));
		medicalQuestions.setQuestion21BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H30", "H30B"));
		medicalQuestions.setQuestion21CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H30", "H30C"));
		medicalQuestions.setQuestion21WChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H30", "H30D"));
		medicalQuestions.setQuestion22Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H31"));
		medicalQuestions.setQuestion22AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H31", "H31A"));
		medicalQuestions.setQuestion22BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H31", "H31B"));
		medicalQuestions.setQuestion22CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H31", "H31C"));
		medicalQuestions.setQuestion22DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H31", "H31D"));
		medicalQuestions.setQuestion23Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H32"));
		medicalQuestions.setQuestion23AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H32", "H32A"));
		medicalQuestions.setQuestion24Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H33"));
		medicalQuestions.setQuestion24AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H33", "H33A"));
		medicalQuestions.setQuestion24BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H33", "H33B"));
		medicalQuestions.setQuestion24CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H33", "H33C"));
		medicalQuestions.setQuestion24DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H33", "H33D"));
		medicalQuestions.setQuestion24EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H33", "H33E"));
		medicalQuestions.setQuestion24FChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H33", "H33F"));
		medicalQuestions.setQuestion25Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H34"));
		medicalQuestions.setQuestion25AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34A"));
		medicalQuestions.setQuestion25BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34B"));
		medicalQuestions.setQuestion25CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34C"));
		medicalQuestions.setQuestion25DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34D"));
		medicalQuestions.setQuestion25EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34E"));
		medicalQuestions.setQuestion25FChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34F"));
		medicalQuestions.setQuestion25GChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H34", "H34G"));
		medicalQuestions.setQuestion26Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H35"));
		medicalQuestions.setQuestion26AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35A"));
		medicalQuestions.setQuestion26BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35B"));
		medicalQuestions.setQuestion26CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35C"));
		medicalQuestions.setQuestion26DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35D"));
		medicalQuestions.setQuestion26EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35E"));
		medicalQuestions.setQuestion26FChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35F"));
		medicalQuestions.setQuestion26GChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35G"));
		medicalQuestions.setQuestion26HChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35H"));
		medicalQuestions.setQuestion26IChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35I"));
		medicalQuestions.setQuestion26JChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35J"));
		medicalQuestions.setQuestion26KChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35K"));
		medicalQuestions.setQuestion26LChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35L"));
		medicalQuestions.setQuestion26MChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35M"));
		medicalQuestions.setQuestion26NChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H35", "H35N"));
		medicalQuestions.setQuestion29Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H25"));
		medicalQuestions.setQuestion29AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H25", "H25A"));
		medicalQuestions.setQuestion29BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H25", "H25B"));
		medicalQuestions.setQuestion29CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H25", "H25C"));
		medicalQuestions.setQuestion29DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H25", "H25D"));
		//Medical DHU changes
		medicalQuestions.setQuestion29EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H25", "H25E"));
		medicalQuestions.setQuestion29FChoice(Utility.getArrayAns( "H25", "H25F",baseMapper,posvQAMap));
		medicalQuestions.setQuestion29GChoice(Utility.getArrayAnsYesOrNoResponse( medicalQuestions.getQuestion29FChoice()));
		medicalQuestions.setQuestion30Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H26"));
		medicalQuestions.setQuestion30AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26", "H26A"));
		//Medical DHU changes
		medicalQuestions.setQuestion30EChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26A", "H26A1"));
		medicalQuestions.setQuestion30FChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26A", "H26A2"));
		medicalQuestions.setQuestion30GChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26A", "H26A3"));
		medicalQuestions.setQuestion30HChoice(Utility.getArrayAns("H26A3","H26A4",baseMapper,posvQAMap));
		medicalQuestions.setQuestion30IChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26A3", "H26A5"));
		medicalQuestions.setQuestion30JChoice(Utility.getArrayAns("H26A","H26A6",baseMapper,posvQAMap));
		medicalQuestions.setQuestion30KChoice(Utility.getArrayAnsYesOrNoResponse(medicalQuestions.getQuestion30JChoice()));
		medicalQuestions.setQuestion30BChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26", "H26B"));
		medicalQuestions.setQuestion30CChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26", "H26C"));
		medicalQuestions.setQuestion30DChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H26", "H26D"));

		medicalQuestions.setQuestionWopTobaccoAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36A"));
		medicalQuestions.setQuestionWopSmokingAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36Ai"));
		medicalQuestions.setQuestionWopAlcoholAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36B"));
		medicalQuestions.setQuestionWopAlcohol1Answer(getMedicalQuestion12Answer(posvQA12Map, "H36Bi"));
		medicalQuestions.setQuestionWopAlcohol2Answer(getMedicalQuestion12Answer(posvQA12Map, "H36Bii"));
		medicalQuestions.setQuestionWopDrugsAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36C"));
		medicalQuestions.setQuestionWopDrugsDetailsAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36Ci"));
		
		medicalQuestions.setQuestionSwpDrugsAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36C"));
		medicalQuestions.setQuestionSwpDrugsDetail2Answer(getMedicalQuestion12Answer(posvQA12Map, "H36Ci"));

		if(!sspJointFlag){

			medicalQuestions.setQuestion27Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H38"));
			medicalQuestions.setQuestion27AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H38", "H38A"));
			medicalQuestions.setQuestion28Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H39"));
			medicalQuestions.setQuestion28AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H39", "H39A"));
			medicalQuestions.setQuestionSwpTobaccoAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36A"));
			medicalQuestions.setQuestionSwpSmokingAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36Ai"));
			medicalQuestions.setQuestionSwpAlcoholAnswer(getMedicalQuestion12Answer(posvQA12Map, "H36B"));
			medicalQuestions.setQuestionSwpAlcohol1Answer(getMedicalQuestion12Answer(posvQA12Map, "H36Bi"));
			medicalQuestions.setQuestionSwpAlcohol2Answer(getMedicalQuestion12Answer(posvQA12Map, "H36Bii"));


		}
		//FUL2-136867 Set "NA" for some health questions (JointLife)
		else {
			medicalQuestions.setQuestion27Answer("NA");
			medicalQuestions.setQuestion27AChoice("NA");
			medicalQuestions.setQuestion28Answer("NA");
			medicalQuestions.setQuestion28AChoice("NA");
			medicalQuestions.setQuestionSwpTobaccoAnswer("NA");
			medicalQuestions.setQuestionSwpSmokingAnswer("NA");
			medicalQuestions.setQuestionSwpAlcoholAnswer("NA");
			medicalQuestions.setQuestionSwpAlcohol1Answer("NA");
			medicalQuestions.setQuestionSwpAlcohol2Answer("NA");
		}

		return medicalQuestions;
	}

	private MedicalQuestions setPosvGeneralMedicalQuestions(MedicalQuestions medicalQuestions, BaseMapper baseMapper, Map<String, List<PosvQuestion>> posvQAMap, Map<String, List<PosvQuestion>> posvQA12Map, boolean isSspSwissReCase) {
		medicalQuestions = new MedicalQuestions();
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
		medicalQuestions.setQuestionDrugsAnswer(getMedicalQuestion12Answer(posvQA12Map, "H12C"));
		medicalQuestions.setQuestionDrugsDetailsAnswer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H12C", "H12Ci"));
		medicalQuestions.setQuestionSwpDrugsDetailAnswer(getMedicalQuestion12Answer(posvQA12Map, "H12Ci"));

		if(!isSspSwissReCase){
			medicalQuestions.setQuestion16Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H14"));
			medicalQuestions.setQuestion16AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H14", "H14A"));
			medicalQuestions.setQuestion17Answer(baseMapper.getMedicalQuestionAnswer(posvQAMap, "NA", "H15"));
			medicalQuestions.setQuestion17AChoice(baseMapper.getMedicalQuestionAnswer(posvQAMap, "H15", "H15A"));
			medicalQuestions.setQuestionTobacco(getMedicalQuestion12Answer(posvQA12Map, "H12"));
			medicalQuestions.setQuestionTobaccoAnswer(getMedicalQuestion12Answer(posvQA12Map, "H12A"));
			medicalQuestions.setQuestionAlcoholAnswer(getMedicalQuestion12Answer(posvQA12Map, "H12B"));
			medicalQuestions.setQuestionAlcohol1Answer(getMedicalQuestion12Answer(posvQA12Map, "H12Bi"));
			medicalQuestions.setQuestionAlcohol2Answer(getMedicalQuestion12Answer(posvQA12Map, "H12Bii"));
			medicalQuestions.setQuestionSmokingAnswer(getMedicalQuestion12Answer(posvQA12Map, "H12Ai"));
		} else {
			medicalQuestions.setQuestion16Answer("NA");
			medicalQuestions.setQuestion16AChoice("NA");
			medicalQuestions.setQuestion17Answer("NA");
			medicalQuestions.setQuestion17AChoice("NA");
			medicalQuestions.setQuestionTobacco("NA");
			medicalQuestions.setQuestionTobaccoAnswer("NA");
			medicalQuestions.setQuestionAlcoholAnswer("NA");
			medicalQuestions.setQuestionAlcohol1Answer("NA");
			medicalQuestions.setQuestionAlcohol2Answer("NA");
			medicalQuestions.setQuestionSmokingAnswer("NA");

		}

		return medicalQuestions;
	}

	private String getMedicalQuestion12Answer(Map<String, List<PosvQuestion>> posvQAMap, String questionId) {
		logger.info("getMedicalQuestion12Answer questionId: {} " , questionId);
		List<PosvQuestion> posvQAList = posvQAMap.get(questionId);

		if (!CollectionUtils.isEmpty(posvQAList)) {

			String answer = posvQAList.get(0).getAnswer();

			if (StringUtils.equalsIgnoreCase(answer, "Y")) {
				return "YES";
			} else if (StringUtils.equalsIgnoreCase(answer, "N")) {
				return "NO";
			} else if (StringUtils.equalsIgnoreCase(answer, "NA") || answer == null) {
				return "NA";
			} else {
				return answer;
			}
		}
		return "NA";
	}
}
