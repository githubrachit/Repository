package com.mli.mpro.document.mapper.term;

import com.mli.mpro.proposal.models.FamilyOrCriminalHistory;
import com.mli.mpro.proposal.models.HazardousActivitiesDetails;
import com.mli.mpro.proposal.models.TravelAndAdventure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

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
import com.mli.mpro.document.service.impl.ULIPLifeInsuredMapper;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.InsuranceDetails;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;

@Service
public class TermLifeInsuredDetailsMapper {

    private static final Logger logger = LoggerFactory.getLogger(TermLifeInsuredDetailsMapper.class);
    @Autowired
	private ULIPLifeInsuredMapper ulipLifeInsuredMapper;
	//Constants
	private static final String LI_EXIST = "LI_EXIST";
	private static final String LI_OFFERED = "LI_OFFERED";
	private static final String LI_ISSUED = "LI_REJECTED";
	private static final String LIC_POLICYDETAILS_SUMASSURED = "sumAssuredLI";
	private static final String LIC_POLICYDETAILS_FAMILYCOVER = "familyCover";
	private static final String LIC_POLICYDETAILS_CRITICALILLNESS_LI = "criticalIllnessLI";
	private static final String INSURED_LIFETIMEINSURANCE_TSA = "insuredLifeTimeInsuranceTSA";
	private static final String INSURED_CRITICALILLNESS_TSA =  "insuredCriticalIllnessTSA";

	private static final String HAZARDOUS_ACTIVITY =  "hazardousActivity";
	private static final String CRIMINAL_CHARGES =  "criminalCharges";
	private static final String INSURED_TRAVELABROAD =  "insuredTravelAbroad";
	private static final String OCCUPATION = "occupation";
	private static final String PREGNANT = "pregnant";
	private static final String PREGNANT_SINCE = "pregnantSince";
	private static final String SPOUSECOVER = "spouseCover";
	private static final String ANNUAL_INCOME = "annualIncome";
	private static final String IS_PREGNANCY_COMPLICATED = "isPregnancyComplicated";

	private static final String TRAVEL_ABROAD = "travelAbroad";
	private static final String IS_FEMALE = "isFemale";
	private static final String AGE = "age";
	private static final String WOPRIDER_INFO = "wopRiderInfo";
	private static final String LIFESTYLEDETAILS_SIZEANDNULLCHECK = "lifeStyleDetailsSizeAndNullCheck";

    public Context setDataForLifeInsured(ProposalDetails proposalDetails) throws UserHandledException {

	Map<String, Object> dataVariables = new HashMap<>();
	boolean isNeoOrAggregator = false;
	String formType = proposalDetails.getApplicationDetails().getFormType();
	String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
	logger.info("Mapping life insured details of proposal form document for transactionId {}", proposalDetails.getTransactionId());

	try {
	    boolean isRiderRequired = false;
	    String proposerLIExist = AppConstants.NO;
	    String proposerLiIssued = "";
	    String proposerLiOffered = "";
	    String proposerLifeTimeInsuranceTSA = "";
	    String proposerCriticalIllnessTSA = "";
	    String insuredCriticalIllnessTSA = "";
	    String insuredLifeTimeInsuranceTSA = "";
	    String insuredLIExist = "";
	    String insuredLiIssued = "";
	    String insuredLiOffered = "";
	    String hazardousActivites = "";
	    String hazardousActivitesDetails = "";
	    String travelAbroad = "";
	    String insuredTravelAbroad = "";
	    String countryToBevisited = "";
	    String insuredCountryToBevisited = "";
	    List<String> countryVisited = null;
	    String criminalCharges = "";
	    String criminalChargesDetails = "";
	    String familyCover = "";
	    String sumAssuredLI = "";
	    String insuredTotalSumAssured = "";
	    String criticalIllnessLI = "";
	    String insuredTotalSumAssuredCI = "";
	    String insuredAge = "";
	    boolean doesLIExistFlag = false;
	    String LIExist = AppConstants.NO;
	    String isLIIssue = AppConstants.NO;
	    String insuredIssued = AppConstants.NO;
	    String isRejected = AppConstants.NO;
	    String minorLI = AppConstants.NO;
	    boolean isSJB = false;
	    boolean isProposer = false;
	    String proposerRejected="";
	    String insuredRejected="";
	    String proposerSumAssuredLI="";
	    String insuredSumAssuredLI="";
	    String proposerCriticalIllnessLI="";
	    String insuredCriticalIllnessLI="";
	    boolean healthQuesSet = false;
	    if(proposalDetails.getProductDetails()!=null && proposalDetails.getProductDetails().stream().anyMatch(pd->pd.getProductInfo()!=null && AppConstants.SJB_PRODUCT_ID.equals(pd.getProductInfo().getProductId()))){
	    	isSJB = true;
	    }

		//NEORW-173: this will check that incoming request is from NEO or Aggregator//NEORW-80:
		isNeoOrAggregator = AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				|| AppConstants.CHANNEL_NEO.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
				? true : false;
	    //NEORW-80: added form type flag ("form1") for NEO
		boolean proposerFormFlag = filter(formType,(String s)-> s.equalsIgnoreCase(AppConstants.SELF)) || Utility.schemeBCase(formType,schemeType);
		boolean isForm2 = filter(formType,(String s)-> s.equalsIgnoreCase(AppConstants.DEPENDENT));
		ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		boolean isForm2orForm3 = (AppConstants.DEPENDENT.equalsIgnoreCase(formType)
				|| (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)));
		List<LifeStyleDetails> lifestyleDetailsList = proposalDetails.getLifeStyleDetails();
	    BasicDetails proposerDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
	    //NEORW-173: handle null pointer exception
	    BasicDetails insuredDetails = proposalDetails.getPartyInformation().size() > 1
				? proposalDetails.getPartyInformation().get(1).getBasicDetails() : null;
		String isJointLife= proposalDetails.getProductDetails().get(0).getProductInfo().getIsJointLife();
	    
	    if (Boolean.valueOf(monitorGeneralChecks(lifestyleDetailsList,LIFESTYLEDETAILS_SIZEANDNULLCHECK))) {
		LifeStyleDetails proposerLifeStyleDetails = proposalDetails.getLifeStyleDetails().get(0);

		proposerLifeTimeInsuranceTSA = getProposerLifeTimeInsuranceTSA(proposerLifeStyleDetails);
				
		proposerCriticalIllnessTSA = getProposerCriticalIllnessTSA(proposerLifeStyleDetails);
		
		proposerLIExist = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_EXIST,proposerLifeStyleDetails,null,proposalDetails,true);
		proposerLiIssued = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_ISSUED,proposerLifeStyleDetails,null,proposalDetails,true);
		proposerLiOffered = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_OFFERED,proposerLifeStyleDetails,null,proposalDetails,true);

		doesLIExistFlag = setLIExistFlag(proposerFormFlag,proposerLifeStyleDetails,null);

		if (!AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
		    logger.info("Setting Lifestyle details for SELF...");
		    // handle NULL pointer exception here
		    if (Objects.nonNull(proposerLifeStyleDetails.getInsuranceDetails()) && Objects.nonNull(proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails()) &&
				!proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().isEmpty()) {
				sumAssuredLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
				criticalIllnessLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
			}
		    familyCover = Objects.nonNull(proposerLifeStyleDetails.getParentsDetails()) ? proposerLifeStyleDetails.getParentsDetails().getTotalInsuranceCover() : "";
		}
		hazardousActivites = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure()) ?
				proposerLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities() : AppConstants.NA;
		hazardousActivitesDetails = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure()) ?
				proposerLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails().getHazardousActivityExtent() : AppConstants.BLANK;
		criminalCharges = Objects.nonNull(proposerLifeStyleDetails.getFamilyOrCriminalHistory()) ?
				proposerLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges() : AppConstants.NO;
		criminalChargesDetails = Objects.nonNull(proposerLifeStyleDetails.getFamilyOrCriminalHistory()) ?
				proposerLifeStyleDetails.getFamilyOrCriminalHistory().getSpecifyDetails() : AppConstants.BLANK;
		travelAbroad = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure()) ?
				proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad() : AppConstants.NO;
		countryVisited = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure())
				&& Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails())
				? proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited() : null;
		if (Objects.nonNull(countryVisited) && !countryVisited.isEmpty()) {
			countryToBevisited = StringUtils.join(countryVisited, ", ");
		}
		if(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType) && StringUtils.isEmpty(countryToBevisited))
			countryToBevisited = AppConstants.NA;
			
				if (isNeoOrAggregator && (Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails)
						|| Utility.isSSPJLProduct(proposalDetails))) {
					String neoInsuredcriminalCharges =
							Objects.nonNull(proposalDetails.getLifeStyleDetails().get(1).getFamilyOrCriminalHistory()) ?
									proposalDetails.getLifeStyleDetails().get(1).getFamilyOrCriminalHistory().getAnyCriminalCharges()
									: AppConstants.NO;
					String neoInsuredtravelAbroad =
							Objects.nonNull(proposalDetails.getLifeStyleDetails().get(1).getTravelAndAdventure())
									? proposalDetails.getLifeStyleDetails().get(1).getTravelAndAdventure().getTravelOrResideAbroad()
									: AppConstants.NO;
					String neoInsuredhazardousActivity =
							Objects.nonNull(proposalDetails.getLifeStyleDetails().get(1).getTravelAndAdventure())
									? proposalDetails.getLifeStyleDetails().get(1).getTravelAndAdventure().getDoYouParticipateInHazardousActivities() : AppConstants.NA;
					String neoInsuredhazardousActivitesDetails = "";
					String neoInsuredcriminalChargesDetails = "";
					if(Utility.isSSPJLProduct(proposalDetails)){
						neoInsuredhazardousActivitesDetails = proposalDetails.getLifeStyleDetails().stream()
								.filter(lifeStyleDetails -> AppConstants.PROPOSER.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
								.findFirst()
								.map(LifeStyleDetails::getTravelAndAdventure)
								.map(TravelAndAdventure::getHazardousActivitiesDetails)
								.map(HazardousActivitiesDetails::getHazardousActivityExtent).orElse(AppConstants.BLANK);
						neoInsuredcriminalChargesDetails = proposalDetails.getLifeStyleDetails().stream()
								.filter(lifeStyleDetails -> AppConstants.PROPOSER.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
								.findFirst()
								.map(LifeStyleDetails::getFamilyOrCriminalHistory)
								.map(FamilyOrCriminalHistory::getSpecifyDetails).orElse(AppConstants.BLANK);

					}else{
						neoInsuredhazardousActivitesDetails = proposalDetails.getLifeStyleDetails().stream()
								.filter(lifeStyleDetails -> AppConstants.LIFE_INSURED.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
								.findFirst()
								.map(LifeStyleDetails::getTravelAndAdventure)
								.map(TravelAndAdventure::getHazardousActivitiesDetails)
								.map(HazardousActivitiesDetails::getHazardousActivityExtent).orElse(AppConstants.BLANK);
						neoInsuredcriminalChargesDetails = proposalDetails.getLifeStyleDetails().stream()
								.filter(lifeStyleDetails -> AppConstants.LIFE_INSURED.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
								.findFirst()
								.map(LifeStyleDetails::getFamilyOrCriminalHistory)
								.map(FamilyOrCriminalHistory::getSpecifyDetails).orElse(AppConstants.BLANK);
					}
					dataVariables.put("neoInsuredtravelAbroad", Utility.convertToYesOrNo(neoInsuredtravelAbroad));
					dataVariables.put("neoInsuredcriminalCharges", Utility.convertToYesOrNo(neoInsuredcriminalCharges));
					dataVariables.put("neoInsuredhazardousActivity", Utility.convertToYesOrNo(neoInsuredhazardousActivity));
					dataVariables.put("neoInsuredhazardousActivitesDetails", neoInsuredhazardousActivitesDetails);
					dataVariables.put("neoInsuredcriminalChargesDetails", neoInsuredcriminalChargesDetails);
				}
		//NEORW-173: this will check that incoming request is from NEO or Aggregator
		if (isNeoOrAggregator) {
			dataVariables.put(TRAVEL_ABROAD, Utility.convertToYesOrNo(travelAbroad));
			dataVariables.put("visitedCountries", countryToBevisited);
			dataVariables.put(HAZARDOUS_ACTIVITY, Utility.convertToYesOrNo(hazardousActivites));
			dataVariables.put(CRIMINAL_CHARGES, Utility.convertToYesOrNo(criminalCharges));
			dataVariables.put("criminalChargesDetails", criminalChargesDetails);
			dataVariables.put("hazardousActivitesDetails", hazardousActivitesDetails);
		} else {
			dataVariables.put(TRAVEL_ABROAD, StringUtils.isNotEmpty(travelAbroad) ? StringUtils.upperCase(travelAbroad) : AppConstants.NA);
			dataVariables.put("visitedCountries", countryToBevisited);
			dataVariables.put(HAZARDOUS_ACTIVITY,  StringUtils.isNotEmpty(hazardousActivites) ? StringUtils.upperCase(hazardousActivites) : AppConstants.NA);
			//FUL2-98622 hazardous activity extent
			dataVariables.put("hazardousActivitesDetails",StringUtils.isNotEmpty(hazardousActivitesDetails) ?hazardousActivitesDetails:AppConstants.NA);

			dataVariables.put(CRIMINAL_CHARGES,  StringUtils.isNotEmpty(criminalCharges) ? StringUtils.upperCase(criminalCharges) : AppConstants.NA);
			//FUL2-103414 Criminal Charges Details
			dataVariables.put("criminalChargesDetails",StringUtils.isNotEmpty(criminalChargesDetails) ?criminalChargesDetails:AppConstants.NA);

		}
	    }

	    if (null != lifestyleDetailsList && !CollectionUtils.isEmpty(lifestyleDetailsList) && lifestyleDetailsList.size() >= 2) {
		LifeStyleDetails proposerLifeStyleDetails = proposalDetails.getLifeStyleDetails().get(0);
		LifeStyleDetails insuredLifeStyleDetails = proposalDetails.getLifeStyleDetails().get(1);

		insuredLifeTimeInsuranceTSA =  setInsuredLifeTimeInsuranceTSA(INSURED_LIFETIMEINSURANCE_TSA, insuredLifeStyleDetails,proposerLifeStyleDetails) ;

		insuredCriticalIllnessTSA = setInsuredLifeTimeInsuranceTSA(INSURED_CRITICALILLNESS_TSA,insuredLifeStyleDetails,proposerLifeStyleDetails);
		insuredLiIssued = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_ISSUED,proposerLifeStyleDetails ,insuredLifeStyleDetails,proposalDetails,false);
		insuredLiOffered = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_OFFERED,proposerLifeStyleDetails , insuredLifeStyleDetails,proposalDetails,false);

			proposerLIExist = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_EXIST,proposerLifeStyleDetails,insuredLifeStyleDetails,proposalDetails,true);
			proposerLiIssued = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_ISSUED,proposerLifeStyleDetails ,insuredLifeStyleDetails,proposalDetails,true);
			proposerRejected = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_OFFERED,proposerLifeStyleDetails , insuredLifeStyleDetails,proposalDetails,true);

		doesLIExistFlag = setLIExistFlag(proposerFormFlag,proposerLifeStyleDetails,insuredLifeStyleDetails);

		doesLIExistFlag = BooleanUtils.isTrue(proposerFormFlag)
			? proposerLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist()
				|| proposerLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected()
			: insuredLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist()
				|| insuredLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected();
				if ((AppConstants.DEPENDENT.equalsIgnoreCase(formType) || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)))
						&& Objects.nonNull(
						proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
			sumAssuredLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
			criticalIllnessLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
			insuredTotalSumAssured = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
			insuredTotalSumAssuredCI = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
		    familyCover = insuredLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
		}
				// FUL2-120213_To map the LIC details for SSP joint life
				if(Utility.isSSPJointLife(productDetails) && Objects.nonNull(
						insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails()) ){
					insuredTotalSumAssured = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
					insuredTotalSumAssuredCI = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
				}
		logger.info("Setting Lifestyle details for DEPENDENT...");
		hazardousActivites = isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife) ? insuredLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities() : AppConstants.NA;
			//FUL2-98622 hazardous activity extent
			hazardousActivitesDetails = isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife)?
					("yes".equalsIgnoreCase(hazardousActivites) ? insuredLifeStyleDetails.getTravelAndAdventure()
							.getHazardousActivitiesDetails().getHazardousActivityExtent() : AppConstants.NA)
					:AppConstants.NA;

		criminalCharges = isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife) ? insuredLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges() : AppConstants.NA;
			//FUL2-103414 Criminal Charges Details
			criminalChargesDetails = isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife)?
					("yes".equalsIgnoreCase(criminalCharges) ? insuredLifeStyleDetails.getFamilyOrCriminalHistory()
							.getSpecifyDetails(): AppConstants.NA)
					:AppConstants.NA;

		insuredTravelAbroad = isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife) ? insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad() : AppConstants.NA;
		countryVisited = isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife) ? insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited() : new ArrayList<>();
		insuredCountryToBevisited = StringUtils.join(countryVisited, ", ");
		dataVariables.put("insuredtravelAbroad",
			StringUtils.isNotEmpty(insuredTravelAbroad) ? StringUtils.upperCase(insuredTravelAbroad) : AppConstants.NA);
		dataVariables.put("insuredvisitedCountries", StringUtils.isNotEmpty(insuredCountryToBevisited) ? StringUtils.upperCase(insuredCountryToBevisited) : AppConstants.NA);
		dataVariables.put("insuredhazardousActivity", StringUtils.isNotEmpty(hazardousActivites) ? StringUtils.upperCase(hazardousActivites) : AppConstants.NA);
			//FUL2-98622 hazardous activity extent
			dataVariables.put("insuredhazardousActivitesDetails", StringUtils.isNotEmpty(hazardousActivitesDetails) ? hazardousActivitesDetails: AppConstants.NA);

		dataVariables.put("insuredcriminalCharges", StringUtils.isNotEmpty(criminalCharges) ? StringUtils.upperCase(criminalCharges) : AppConstants.NA);
			//FUL2-103414 Criminal Charges Details
			dataVariables.put("insuredCriminalChargesDetails", StringUtils.isNotEmpty(criminalChargesDetails) ? criminalChargesDetails : AppConstants.NA);

		if (Objects.nonNull(insuredDetails) && null != insuredDetails.getDob()) {
		    insuredAge = Utility.getAge(insuredDetails.getDob());
		}
			familyCover =  setLICPolicyDetails(formType,LIC_POLICYDETAILS_FAMILYCOVER,proposerLifeStyleDetails,insuredLifeStyleDetails);
			LIExist = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_EXIST,proposerLifeStyleDetails,insuredLifeStyleDetails,proposalDetails,true);
			insuredLIExist = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(), LI_EXIST, proposerLifeStyleDetails, insuredLifeStyleDetails, proposalDetails, false);
			if(AppConstants.SELF.equalsIgnoreCase(formType)
					|| Utility.schemeBCase(formType,schemeType)){
				isProposer =true;
			} else if(AppConstants.DEPENDENT.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()) && AppConstants.SWP_PRODUCT_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())){
				isProposer = true;
			}
			isLIIssue = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_ISSUED,proposerLifeStyleDetails,insuredLifeStyleDetails,proposalDetails,isProposer);
			insuredIssued = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_ISSUED,proposerLifeStyleDetails,insuredLifeStyleDetails,proposalDetails,false);
			isRejected = getLICPolicyStatus(proposalDetails.getApplicationDetails().getFormType(),LI_OFFERED,proposerLifeStyleDetails,insuredLifeStyleDetails,proposalDetails,isProposer);
		}

	    String proposerGender = Objects.nonNull(proposerDetails) ? proposerDetails.getGender() : AppConstants.BLANK;
	    String insuredGender = "";
	    // handle NULL pointer exception for insuredGender
	    if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
			!proposalDetails.getPartyInformation().isEmpty() && proposalDetails.getPartyInformation().size() > 1 &&
			Objects.nonNull(proposalDetails.getPartyInformation().get(1).getBasicDetails())) {
	    	insuredGender = !StringUtils.isEmpty(proposalDetails.getPartyInformation().get(1).getBasicDetails().getGender()) ? proposalDetails.getPartyInformation().get(1).getBasicDetails().getGender() : AppConstants.BLANK;
		}
	    boolean isProposerFemale = filter(proposerGender,(String str)-> str.equalsIgnoreCase("F"));
	    boolean isInsuredFemale = filter(insuredGender,(String str)-> str.equalsIgnoreCase("F"));
	    boolean isFemale = filter(proposerGender,(String str)-> str.equalsIgnoreCase("F")) || filter(insuredGender,(String str)-> str.equalsIgnoreCase("F")) ;
	    String femaleFlag = monitorGeneralChecks(isFemale,IS_FEMALE);

		String spouseOccupation = "";
		String spouseAnnualIncome = "";
		String spouseCover = "";
		String isProposerPregnant = "";
		String pregnantSince = "";
		String isPregnancyComplicated = "";
		String anyComplicancy = "";
		String insuredspouseOccupation = "";
		String insuredspouseAnnualIncome = "";
		String insuredspouseCover = "";
		String isInsuredPregnant = "";
		String insuredPregnantSince = "";
		String insuredanyComplicancy = "";
		String isinsuredPregnancyComplicated = "";
		boolean isPregnant = false;
	
		// handle NULL pointer check here for MarriageDetails Object
	    if (Objects.nonNull(proposerDetails.getMarriageDetails())) {
	    	if(!isNeoOrAggregator) {
	    		isPregnant = proposerDetails.getMarriageDetails().isPregnant() || insuredDetails.getMarriageDetails().isPregnant() ? true : false; 
	    	}
	    		spouseOccupation = femaleProposerRelatedInfo(isProposerFemale ,  OCCUPATION ,proposerDetails);
				spouseAnnualIncome = femaleProposerRelatedInfo(isProposerFemale ,ANNUAL_INCOME ,proposerDetails);

				if(AppConstants.YES.equalsIgnoreCase(isJointLife)){
				spouseOccupation= isProposerFemale? insuredDetails.getOccupation().toLowerCase():AppConstants.NA;
				spouseAnnualIncome= isProposerFemale? insuredDetails.getAnnualIncome().toLowerCase():AppConstants.NA;
				}
			spouseCover = femaleProposerRelatedInfo(isProposerFemale , SPOUSECOVER ,proposerDetails);
			isProposerPregnant = femaleProposerRelatedInfo(isProposerFemale ,PREGNANT ,proposerDetails);

				if (isNeoOrAggregator && (Utility.isProductSWPJL(proposalDetails)
						|| Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails))) {
					BasicDetails insuredBasicDetails = proposalDetails.getPartyInformation().get(1)
							.getBasicDetails();
					String neoInsuredSpouseOccupation = femaleProposerRelatedInfo(isInsuredFemale, OCCUPATION,
							insuredBasicDetails);
					String neoInsuredspouseAnnualIncome = femaleProposerRelatedInfo(isInsuredFemale,
							ANNUAL_INCOME, insuredBasicDetails);
					String neoInsureSpouseInsurance = femaleProposerRelatedInfo(isInsuredFemale, SPOUSECOVER,
							insuredBasicDetails);
					String neoInsuredPregnant = femaleProposerRelatedInfo(isInsuredFemale, PREGNANT,
							insuredBasicDetails);
					String neoInsuredPregnantMonths = !StringUtils.isEmpty(insuredBasicDetails.getMarriageDetails().getPregnantSince())
							? insuredBasicDetails.getMarriageDetails().getPregnantSince().concat(AppConstants.MONTHS)
							: AppConstants.NA;
					String neoInsuredPregnancyComplicated = femaleProposerRelatedInfo(isInsuredFemale,
							IS_PREGNANCY_COMPLICATED, insuredBasicDetails);
					String neoInsuredAnyComplicancyDetails = getPregnancyComplication(isInsuredFemale, null,
							neoInsuredPregnancyComplicated, insuredBasicDetails);

					dataVariables.put("neoInsuredSpouseOccupation",
							monitorGeneralChecks(neoInsuredSpouseOccupation, OCCUPATION));
					dataVariables.put("neoInsuredspouseAnnualIncome",
							StringUtils.isNotBlank(neoInsuredspouseAnnualIncome) ? neoInsuredspouseAnnualIncome
									: AppConstants.NA);
					dataVariables.put("neoInsureSpouseInsurance",
							monitorGeneralChecks(neoInsureSpouseInsurance, SPOUSECOVER));
					dataVariables.put("neoInsuredPregnant", neoInsuredPregnant);
					dataVariables.put("neoInsuredPregnantMonths", neoInsuredPregnantMonths);
					dataVariables.put("neoInsuredPregnancyComplicated", neoInsuredPregnancyComplicated);
					dataVariables.put("neoInsuredAnyComplicancyDetails", neoInsuredAnyComplicancyDetails);
				}
			if (isNeoOrAggregator && isProposerPregnant.equalsIgnoreCase(AppConstants.YES)) {
				pregnantSince = !StringUtils.isEmpty(proposerDetails.getMarriageDetails().getPregnantSince())
						? proposerDetails.getMarriageDetails().getPregnantSince().concat(AppConstants.MONTHS)
						: AppConstants.NA;
			} else {
				pregnantSince = (isProposerPregnant.equalsIgnoreCase(AppConstants.YES)) ? femaleProposerRelatedInfo(isProposerFemale ,PREGNANT_SINCE ,proposerDetails) : AppConstants.NA;
			}
			isPregnancyComplicated = (isProposerPregnant.equalsIgnoreCase(AppConstants.YES)) ? femaleProposerRelatedInfo(isProposerFemale ,IS_PREGNANCY_COMPLICATED,proposerDetails) : AppConstants.NA;

			anyComplicancy = getPregnancyComplication(isProposerFemale,null,isPregnancyComplicated,proposerDetails);
			// insured details
			if (Objects.nonNull(insuredDetails) && Objects.nonNull(insuredDetails.getMarriageDetails())) {
				insuredspouseOccupation = (isInsuredFemale && isForm2orForm3) ? insuredDetails.getMarriageDetails().getSpouseOccupation() : AppConstants.NA;
				insuredspouseAnnualIncome = (isInsuredFemale && isForm2orForm3)? insuredDetails.getMarriageDetails().getSpouseAnnualIncome() : AppConstants.NA;
				insuredspouseCover = (isInsuredFemale && (isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife))) ? insuredDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse() : AppConstants.NA;
				isInsuredPregnant = (isInsuredFemale && (isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife))) ? (insuredDetails.getMarriageDetails().isPregnant()
						? AppConstants.YES : AppConstants.NO) : AppConstants.NA;

				insuredPregnantSince = (isInsuredFemale && (isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife)) && isInsuredPregnant.equalsIgnoreCase(AppConstants.YES)) ? (!StringUtils.isEmpty(insuredDetails.getMarriageDetails().getPregnantSince())
						? insuredDetails.getMarriageDetails().getPregnantSince().concat(AppConstants.MONTHS)
						: AppConstants.NA) : AppConstants.NA;
						
				insuredanyComplicancy = (isInsuredFemale && (isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife))&& isInsuredPregnant.equalsIgnoreCase(AppConstants.YES))
						? (insuredDetails.getMarriageDetails().isAnyComplicationToPregnancy() ? AppConstants.YES : AppConstants.NO)
						: AppConstants.NA;

				isinsuredPregnancyComplicated = (isInsuredFemale && (isForm2orForm3 || AppConstants.YES.equalsIgnoreCase(isJointLife))&& StringUtils.equalsIgnoreCase(insuredanyComplicancy, AppConstants.YES))
						? (StringUtils.isNotEmpty(insuredDetails.getMarriageDetails().getPregnancyComplicationDetails())
						? insuredDetails.getMarriageDetails().getPregnancyComplicationDetails()
						: AppConstants.NA)
						: AppConstants.NA;
				if(AppConstants.YES.equalsIgnoreCase(isJointLife) && isInsuredFemale){
					spouseOccupation =  proposerDetails.getOccupation().toLowerCase();
					spouseAnnualIncome =  proposerDetails.getAnnualIncome().toLowerCase();
					pregnantSince= insuredDetails.getMarriageDetails().getPregnantSince().concat(AppConstants.MONTHS);
					anyComplicancy =insuredDetails.getMarriageDetails().getPregnancyComplicationDetails();
				}
			}
		}

	    try {
			minorLI = checkIfInsuredIsMinor(proposalDetails, insuredAge);
			if ((AppConstants.SELF.equalsIgnoreCase(formType)
					&& (AppConstants.CEIP.equalsIgnoreCase(productDetails.getObjectiveOfInsurance())
							|| AppConstants.EMPLOYER_EMPLOYEE.equalsIgnoreCase(productDetails.getObjectiveOfInsurance())))
					||AppConstants.FORM3.equalsIgnoreCase(formType))
				minorLI = AppConstants.NO;
			
	    } catch (Exception ex) {
			logger.error("Error checking age for Minor in life insured section :",ex);
	    }
		    
	    
	    if (null != proposalDetails.getProductDetails() && !CollectionUtils.isEmpty(proposalDetails.getProductDetails())
		    && proposalDetails.getProductDetails().size() >= 1) {
			//NEORW: handle NULL pointer exception for riderDetails object
			if (Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo()) &&
				Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails()) &&
				!proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() &&
					proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails().size() > 2) {
				isRiderRequired = BooleanUtils
						.isTrue(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).isRiderRequired()) ? true : false;
			}
	    }
		if (Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails) || Utility.isSSPJLProduct(proposalDetails)) {
			ulipLifeInsuredMapper.setInsuranceHistoryDataForNeoForLI(dataVariables, proposalDetails);
		}
		ulipLifeInsuredMapper.setInsuranceHistoryDataForNeo(dataVariables, proposalDetails);


		if (!CollectionUtils.isEmpty(proposalDetails.getProductDetails()) &&
				!ObjectUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo())
				&& !CollectionUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails())) {
			isRiderRequired = Utility.isWOPPresent(proposalDetails);

		    }

		boolean isWopFlag = (isRiderRequired && !proposerFormFlag);

		if(isSJB || isWopFlag) {
			proposerSumAssuredLI = sumAssuredLI;
			proposerCriticalIllnessLI = criticalIllnessLI;
		}

		if (AppConstants.SELF.equalsIgnoreCase(formType) || Utility.schemeBCase(formType,schemeType)) {
			if (AppConstants.NO.equalsIgnoreCase(insuredLIExist)) {
				insuredLIExist = AppConstants.NO;
			} else if (StringUtils.EMPTY.endsWith(insuredLIExist)) {
				insuredLIExist = AppConstants.NA;
			}
		}

		if(isSJB && AppConstants.DEPENDENT.equalsIgnoreCase(formType)){
			insuredRejected = StringUtils.isEmpty(insuredLiOffered) ? "NA" : insuredLiOffered;
			insuredSumAssuredLI=StringUtils.isEmpty(sumAssuredLI) ? "NA" : sumAssuredLI;
			insuredCriticalIllnessLI=StringUtils.isEmpty(criticalIllnessLI) ? "NA" : criticalIllnessLI;
		}else if(isWopFlag && AppConstants.DEPENDENT.equalsIgnoreCase(formType)) {
			insuredRejected = StringUtils.isEmpty(insuredLiOffered) ? "NA" : insuredLiOffered;
			insuredSumAssuredLI=StringUtils.isEmpty(insuredTotalSumAssured) ? "NA" : insuredTotalSumAssured;
			insuredCriticalIllnessLI=StringUtils.isEmpty(insuredTotalSumAssuredCI) ? "NA" : insuredTotalSumAssuredCI;
		}

		if(isSJB || isWopFlag) {
			healthQuesSet = true;
		}

		if(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)){
			LIExist =  AppConstants.NA;
			isLIIssue = AppConstants.NA;
			isRejected = AppConstants.NA;
		}
		if((Utility.schemeBCase(formType,schemeType) && !Utility.isSSPJointLife(productDetails))|| (AppConstants.SELF.equalsIgnoreCase(formType)
					&& (AppConstants.CEIP.equalsIgnoreCase(productDetails.getObjectiveOfInsurance())
							|| AppConstants.EMPLOYER_EMPLOYEE.equalsIgnoreCase(productDetails.getObjectiveOfInsurance()))))
			insuredIssued = AppConstants.NA;

		setDataForPrevPregnancy(proposerDetails,insuredDetails,dataVariables,isForm2orForm3);
		
	    //Data Values
		dataVariables.put("isSJB", isSJB);
	    dataVariables.put("proposerRejected",proposerRejected);
		dataVariables.put("insuredRejected",StringUtils.isEmpty(insuredRejected) ? "NA" :insuredRejected);
	  	dataVariables.put("proposerSumAssuredLI",StringUtils.isEmpty(proposerSumAssuredLI) ? "NA" : proposerSumAssuredLI);
		dataVariables.put("insuredSumAssuredLI",StringUtils.isEmpty(insuredSumAssuredLI) ? "NA" :insuredSumAssuredLI);
	  	dataVariables.put("proposerCriticalIllnessLI",StringUtils.isEmpty(proposerCriticalIllnessLI) ? "NA" : proposerCriticalIllnessLI);
	    dataVariables.put("insuredCriticalIllnessLI",StringUtils.isEmpty(insuredCriticalIllnessLI) ? "NA" :insuredCriticalIllnessLI);
	    dataVariables.put("proposerFormFlag", proposerFormFlag);
	    dataVariables.put("proposerLIExist", proposerLIExist);
	    dataVariables.put("proposerLiIssued", proposerLiIssued);
	    dataVariables.put("proposerLiOffered", proposerLiOffered);
	    dataVariables.put("proposerLifeTimeInsuranceTSA", proposerLifeTimeInsuranceTSA);
	    dataVariables.put("proposerCriticalIllnessTSA", proposerCriticalIllnessTSA);
	    dataVariables.put("insuredLIExist", insuredLIExist);
	    dataVariables.put("insuredLiIssued", StringUtils.isEmpty(insuredLiIssued) ? "NA" : insuredLiIssued);
	    dataVariables.put("insuredLiOffered", StringUtils.isEmpty(insuredLiOffered) ? "NA" : insuredLiOffered);
	    dataVariables.put(INSURED_LIFETIMEINSURANCE_TSA, insuredLifeTimeInsuranceTSA);
	    dataVariables.put(INSURED_CRITICALILLNESS_TSA, insuredCriticalIllnessTSA);
	    dataVariables.put("doesLIExistFlag", doesLIExistFlag);
		dataVariables.put("totalSumAssured", StringUtils.isEmpty(sumAssuredLI) ? "NA" : sumAssuredLI);
		dataVariables.put("insuredTotalSumAssured", StringUtils.isEmpty(insuredTotalSumAssured) ? "NA" : insuredTotalSumAssured);
		dataVariables.put("totalSumAssuredCI", StringUtils.isEmpty(criticalIllnessLI) ? "NA" : criticalIllnessLI);
		dataVariables.put("insuredTotalSumAssuredCI", StringUtils.isEmpty(insuredTotalSumAssuredCI) ? "NA" : insuredTotalSumAssuredCI);
	    dataVariables.put("femaleInsured", femaleFlag);
	    dataVariables.put("spouseOccupation", monitorGeneralChecks(spouseOccupation,OCCUPATION));
	    dataVariables.put("insuredspouseOccupation",monitorGeneralChecks(insuredspouseOccupation,OCCUPATION));
	    dataVariables.put("spouseAnnualIncome", StringUtils.isNotBlank(spouseAnnualIncome) ? spouseAnnualIncome : AppConstants.NA);
	    dataVariables.put("insuredspouseAnnualIncome",StringUtils.isNotBlank(insuredspouseAnnualIncome) ? insuredspouseAnnualIncome : AppConstants.NA);
	    dataVariables.put("spouseInsurance", monitorGeneralChecks(spouseCover,SPOUSECOVER));
	    dataVariables.put("insuredspouseInsurance", monitorGeneralChecks(insuredspouseCover,SPOUSECOVER));
	    dataVariables.put("fullName", "NA");
	    dataVariables.put("insuredfullName", "NA");
	    dataVariables.put("proposerPregnant", isProposerPregnant);
	    dataVariables.put("insuredPregnant", isInsuredPregnant);
	    dataVariables.put("months", pregnantSince);
	    dataVariables.put("insuredmonths", insuredPregnantSince);
	    dataVariables.put("anyComplications", isPregnancyComplicated);
	    dataVariables.put("insuredanyComplications", insuredanyComplicancy);
	    dataVariables.put(IS_FEMALE, isFemale);
	    dataVariables.put("complicationDetails", anyComplicancy);
	    dataVariables.put("insuredcomplicationDetails", isinsuredPregnancyComplicated);
	    dataVariables.put("minorInsured", minorLI);
	    dataVariables.put("isPregnant", isPregnant);
	    dataVariables.put(LIC_POLICYDETAILS_FAMILYCOVER, familyCover);
	    dataVariables.put("isWopFlag", isWopFlag);
	    dataVariables.put("ciPolicy", LIExist);
	    dataVariables.put("insurerciPolicy", insuredLIExist);
	    dataVariables.put("isLIExist", LIExist);
	    dataVariables.put("issued", isLIIssue);
	    dataVariables.put("insuredIssued", insuredIssued);
	    dataVariables.put("rejected", isRejected);
	    dataVariables.put("isLIIssued", "");
	    dataVariables.put("healthQuesSet",healthQuesSet);
		dataVariables.put("formType",formType);
		Utility.iibDetailsAdder(proposalDetails, dataVariables);
		setLifeStyleQuesDetailsData(proposalDetails, dataVariables);
		refreshDataVariablesForSJB(dataVariables, isForm2 ? 2 : 1, isSJB);
	} catch (Exception ex) {
	    logger.error("Data addition failed for Proposal Form Document for transactionId {} : ", proposalDetails.getTransactionId(), ex);
		List<String> errorMessages = new ArrayList<String>();
	    errorMessages.add("Data addition failed");
	    throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	logger.info("Mapping life insured details of proposal form document is completed successfully for transactionId {}",
		proposalDetails.getTransactionId());
	Context lifeInsuredcontext = new Context();
	lifeInsuredcontext.setVariables(dataVariables);
	return lifeInsuredcontext;
    }

    private void refreshDataVariablesForSJB(Map<String, Object> dataVariables,int formType,boolean isSJB) {
    	if(!isSJB) {
    		return;
    	}
    	if(formType==2){//dependent
    	dataVariables.put("proposerLIExist",AppConstants.NO);
    	dataVariables.put("proposerLiIssued",AppConstants.NO);
    	dataVariables.put("proposerRejected",AppConstants.NO);
    	dataVariables.put("proposerSumAssuredLI",AppConstants.BLANK);
	    dataVariables.put("proposerCriticalIllnessLI",AppConstants.BLANK);
        }
	    if(formType==1) {//self
	    dataVariables.put("insuredLIExist",AppConstants.NA);
	    dataVariables.put("insuredLiIssued",AppConstants.NA);
	    dataVariables.put("insuredRejected",AppConstants.NA);
	    dataVariables.put("insuredSumAssuredLI",AppConstants.NA);
	    dataVariables.put("insuredCriticalIllnessLI",AppConstants.NA);
	    }
    }
	private String getPregnantSinceMonths(String pregnantSince) {
    	if ("2".equalsIgnoreCase(pregnantSince)) {
    		return "1";
		} else if ("3".equalsIgnoreCase(pregnantSince)) {
    		return "2";
		} else if ("4".equalsIgnoreCase(pregnantSince)) {
			return "3";
		} else if ("5".equalsIgnoreCase(pregnantSince)) {
			return "4";
		} else if ("6".equalsIgnoreCase(pregnantSince)) {
			return "5";
		} else if ("7".equalsIgnoreCase(pregnantSince)) {
			return "6";
		} else if ("8".equalsIgnoreCase(pregnantSince)) {
			return "7";
		} else if ("9".equalsIgnoreCase(pregnantSince)) {
			return "8";
		} else if ("10".equalsIgnoreCase(pregnantSince)) {
			return "9";
		} else {
    		return pregnantSince;
		}
	}
	public static <T> boolean filter(T str, Predicate<T> p){
		return p.test(str);
	}

	private String getProposerLifeTimeInsuranceTSA(LifeStyleDetails proposerLifeStyleDetails) throws NullPointerException{
		if(null != proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails()
				&& !CollectionUtils.isEmpty(proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())){
			return proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
		}
		return AppConstants.NA;
	}

	private String getProposerCriticalIllnessTSA(LifeStyleDetails proposerLifeStyleDetails){
		if(null != proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails()
				&& !CollectionUtils.isEmpty(proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
			return proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
		}
		return AppConstants.NA;
	}

	private String getLICPolicyStatus(String formType, String type, LifeStyleDetails proposerLifeStyleDetails, LifeStyleDetails insuredLifeStyleDetails,ProposalDetails proposalDetails,boolean isProposer){
		//self should also consider for SJB and proposerLifeStyleDetails payload is giving flag for it

		if(proposalDetails.getProductDetails()==null) {
			return StringUtils.EMPTY;
		}
		InsuranceDetails insuranceDetails = null;
		if ((AppConstants.SELF.equals(formType) || Utility.schemeBCase(formType, proposalDetails.getApplicationDetails().getSchemeType()))
				&& isProposer) {
			insuranceDetails = proposerLifeStyleDetails.getInsuranceDetails();
			return getLifeInsuredDeatils(insuranceDetails, type);
		} else if (AppConstants.DEPENDENT.equals(formType)
				|| (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(proposalDetails.getApplicationDetails().getSchemeType()))
				|| AppConstants.FORM2.equals(formType)
		        || Utility.isSSPJointLife(proposalDetails.getProductDetails().get(0))) {
			if (isProposer) {
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
			case LI_EXIST:
				return insuranceDetails.isLIPolicyRejected()
						|| insuranceDetails.isLICIPolicyExist() ? AppConstants.YES : AppConstants.NO;
			case LI_ISSUED:
				return insuranceDetails.isLICIPolicyExist() ? AppConstants.YES : AppConstants.NO;
			case LI_OFFERED:
				return insuranceDetails.isLIPolicyRejected() ? AppConstants.YES : AppConstants.NO;
			default:
				return StringUtils.EMPTY;
		}
	}

	private String setLICPolicyDetails(String formType, String type , LifeStyleDetails proposerLifeStyleDetails, LifeStyleDetails insuredLifeStyleDetails) throws NullPointerException{

		if(!AppConstants.DEPENDENT.equalsIgnoreCase(formType) && insuredLifeStyleDetails==null){

			switch (type) {

				case LIC_POLICYDETAILS_SUMASSURED: {
					return proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
				}

				case LIC_POLICYDETAILS_FAMILYCOVER :
					return proposerLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();

				case LIC_POLICYDETAILS_CRITICALILLNESS_LI :
					return proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();

				default:
					return StringUtils.EMPTY;
			}
		} else if (formType.equalsIgnoreCase(AppConstants.DEPENDENT) && insuredLifeStyleDetails != null
				&& Objects.nonNull(insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
			switch (type) {

				case LIC_POLICYDETAILS_SUMASSURED: {
					return insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
				}

				case LIC_POLICYDETAILS_FAMILYCOVER :
					return insuredLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();

				case LIC_POLICYDETAILS_CRITICALILLNESS_LI :
					return insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();

				default:
					return StringUtils.EMPTY;
			}
		}
		return StringUtils.EMPTY;

	}

	private boolean setLIExistFlag(boolean proposerFormFlag, LifeStyleDetails proposerLifeStyleDetails, LifeStyleDetails insuredLifeStyleDetails) throws NullPointerException{
		if(insuredLifeStyleDetails==null) {
			return BooleanUtils.isTrue(proposerFormFlag)
					? proposerLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist()
					|| proposerLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected()
					: false;
		}

		else {
			return BooleanUtils.isTrue(proposerFormFlag)
					? proposerLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist()
					|| proposerLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected()
					: insuredLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist()
					|| insuredLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected();
		}
	}


	private String setInsuredLifeTimeInsuranceTSA(String type, LifeStyleDetails insuredLifeStyleDetails, LifeStyleDetails proposerLifeStyleDetails) throws NullPointerException {

		switch (type) {

			case INSURED_LIFETIMEINSURANCE_TSA: {
				if (insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails() != null
						&& !CollectionUtils.isEmpty(insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
					return insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
				} else if (proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails() != null
						&& !CollectionUtils.isEmpty(proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
					return proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
				} else {
					return AppConstants.NA;
				}
			}

			case INSURED_CRITICALILLNESS_TSA: {

				if (insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails() != null
						&& !CollectionUtils.isEmpty(insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
					return insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
				} else if (proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails() != null
						&& !CollectionUtils.isEmpty(proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails())) {
					return proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
				} else {
					return AppConstants.NA;
				}
			}

			default:
				return AppConstants.NA;

		}


	}

	private String settingLifeStyleDetails(String formType, String type , LifeStyleDetails insuredLifeStyleDetails) throws NullPointerException{

		switch(type){

			case HAZARDOUS_ACTIVITY :
				return formType.equalsIgnoreCase(AppConstants.DEPENDENT) ? insuredLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities() : AppConstants.NA;
			case CRIMINAL_CHARGES :
				return formType.equalsIgnoreCase(AppConstants.DEPENDENT)  ? insuredLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges() : AppConstants.NA;
			case INSURED_TRAVELABROAD :
				return  formType.equalsIgnoreCase(AppConstants.DEPENDENT)? insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad() : AppConstants.NA;
			default:
				return AppConstants.NA;
		}

	}

	private String femaleProposerRelatedInfo(boolean isProposerFemale, String type, BasicDetails proposerDetailsOrInsuredDetails) throws NullPointerException {

		if (isProposerFemale) {
			switch (type) {

				case OCCUPATION:
					return proposerDetailsOrInsuredDetails.getMarriageDetails().getSpouseOccupation();
				case ANNUAL_INCOME:
					return proposerDetailsOrInsuredDetails.getMarriageDetails().getSpouseAnnualIncome();
				case SPOUSECOVER:
					return proposerDetailsOrInsuredDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse();
				case PREGNANT:
					return proposerDetailsOrInsuredDetails.getMarriageDetails().isPregnant() ? "YES" : "NO";
				case PREGNANT_SINCE:
					return !StringUtils.isEmpty(proposerDetailsOrInsuredDetails.getMarriageDetails().getPregnantSince())
							? proposerDetailsOrInsuredDetails.getMarriageDetails().getPregnantSince().concat(AppConstants.MONTHS)
							: "NA";
				case IS_PREGNANCY_COMPLICATED:
					return proposerDetailsOrInsuredDetails.getMarriageDetails().isAnyComplicationToPregnancy() ? "YES" : "NO";
				default:
					return AppConstants.NA;
			}
		} else {
			return AppConstants.NA;
		}
	}

	private String femaleProposerRelatedInfoForm2(boolean isProposerFemale, String formType, String type, BasicDetails InsuredDetails) throws NullPointerException {
		String female = StringUtils.EMPTY;
		if(isProposerFemale){
			female = "femaleProposer";
		}

		String expression = female+type+"_"+formType;
		switch (expression) {

			case OCCUPATION:
				return  InsuredDetails.getMarriageDetails().getSpouseOccupation();
			case ANNUAL_INCOME:
				return InsuredDetails.getMarriageDetails().getSpouseAnnualIncome();
			case SPOUSECOVER:
				return InsuredDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse();
			case PREGNANT:
				return (InsuredDetails.getMarriageDetails().isPregnant() ? "YES" : "NO");
			case PREGNANT_SINCE:
				return  !StringUtils.isEmpty(InsuredDetails.getMarriageDetails().getPregnantSince())
						? InsuredDetails.getMarriageDetails().getPregnantSince().concat(AppConstants.MONTHS)
						: "NA";
			case IS_PREGNANCY_COMPLICATED:
				return InsuredDetails.getMarriageDetails().isAnyComplicationToPregnancy() ? "YES" : "NO";
			default:
				return AppConstants.NA;
		}


	}

	private String getPregnancyComplication(boolean isProposerFemale ,String isForm2, String isComplicatedPregnancy , BasicDetails basicDetails) throws NullPointerException{

		if(isProposerFemale && StringUtils.equalsIgnoreCase(isComplicatedPregnancy, AppConstants.YES) && (isForm2==null || isForm2.equalsIgnoreCase("true"))){
			return StringUtils.isNotEmpty(basicDetails.getMarriageDetails().getPregnancyComplicationDetails())
					? basicDetails.getMarriageDetails().getPregnancyComplicationDetails()
					: AppConstants.NA ;
		}
		else {
			return AppConstants.NA;
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

	private String monitorGeneralChecks(Object obj, String type) throws NullPointerException{

		switch(type){
			case TRAVEL_ABROAD : {
				String travelAbroad = (String) obj;
				return StringUtils.isNotEmpty(travelAbroad) ? StringUtils.upperCase(travelAbroad) : AppConstants.NA;
			}
			case IS_FEMALE :
				return ((boolean)obj) ? AppConstants.YES: AppConstants.NO;
			case AGE : {
				BasicDetails basicDetails = (BasicDetails) obj;
				if (null != basicDetails.getDob()) {
					return Utility.getAge(basicDetails.getDob());
				} else {
					return StringUtils.EMPTY;
				}
			}
			case SPOUSECOVER : {
				String spouseCover = (String) obj;
				return StringUtils.isNotBlank(spouseCover) ? spouseCover : AppConstants.NA;
			}
			case WOPRIDER_INFO :{
				ProposalDetails proposalDetails = (ProposalDetails)obj;
				return String.valueOf(null != proposalDetails.getProductDetails() && !CollectionUtils.isEmpty(proposalDetails.getProductDetails())
						&& proposalDetails.getProductDetails().size() >= 1 && null != proposalDetails.getProductDetails().get(0).getProductInfo()
						&& !CollectionUtils.isEmpty(proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails())
						&& proposalDetails.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2) ;
			}
			case LIFESTYLEDETAILS_SIZEANDNULLCHECK :{
				List<LifeStyleDetails> lifeStyleDetailsList = (List<LifeStyleDetails>) obj;
				return String.valueOf(lifeStyleDetailsList!=null && !CollectionUtils.isEmpty(lifeStyleDetailsList) && lifeStyleDetailsList.size() >= 1);
			}

			case OCCUPATION: {
				String spouseOccupation = (String) obj;
				return StringUtils.isNotBlank(spouseOccupation) ? spouseOccupation : AppConstants.NA;
			}
			default:
				return AppConstants.NA;
		}

	}
	
	private void setLifeStyleQuesDetailsData(ProposalDetails proposalDetails, Map<String, Object> dataForDocument){
        String isProposerWeightChange = AppConstants.NA;
        String proposerWeightChangeReason =AppConstants.NA;
        String isInsuredWeightChange=AppConstants.NA;
        String insuredWeightChangeReason =AppConstants.NA;
        String sumAssuredForSpouseAndChildProposer = AppConstants.NA;
        String sumAssuredForSpouseAndChildInsured = AppConstants.NA;
        List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
        if(!CollectionUtils.isEmpty(lifeStyleDetailsList) ){
            if(Objects.nonNull(lifeStyleDetailsList.get(0).getHeightAndWeight())){
                isProposerWeightChange = getIsWeightChangedAnswer(lifeStyleDetailsList.get(0).getHeightAndWeight().getIsWeightChanged());
                proposerWeightChangeReason = lifeStyleDetailsList.get(0).getHeightAndWeight().getWeightChangeReason();
            }

            if(Objects.nonNull(lifeStyleDetailsList.get(0).getInsuranceSumAssuredDetails())){
                sumAssuredForSpouseAndChildProposer = lifeStyleDetailsList.get(0).getInsuranceSumAssuredDetails().getSumAssuredForSpouseAndChild();
            }

            if( lifeStyleDetailsList.size() > 1){ // insured condition
                if(Objects.nonNull(lifeStyleDetailsList.get(1).getHeightAndWeight())){
                    isInsuredWeightChange = getIsWeightChangedAnswer(lifeStyleDetailsList.get(1).getHeightAndWeight().getIsWeightChanged());
                    insuredWeightChangeReason = lifeStyleDetailsList.get(1).getHeightAndWeight().getWeightChangeReason();
                }
                if(Objects.nonNull(lifeStyleDetailsList.get(1).getInsuranceSumAssuredDetails())){
                    sumAssuredForSpouseAndChildInsured = lifeStyleDetailsList.get(1).getInsuranceSumAssuredDetails().getSumAssuredForSpouseAndChild();
                }
            }
        }
        dataForDocument.put("isProposerWeightChange", isProposerWeightChange);
        dataForDocument.put("proposerWeightChangeReason", proposerWeightChangeReason);
        dataForDocument.put("isInsuredWeightChange", isInsuredWeightChange);
        dataForDocument.put("insuredWeightChangeReason", insuredWeightChangeReason);
        dataForDocument.put("sumAssuredForSpouseAndChildProposer", sumAssuredForSpouseAndChildProposer);
        dataForDocument.put("sumAssuredForSpouseAndChildInsured", sumAssuredForSpouseAndChildInsured);
    }
	
	private String getIsWeightChangedAnswer(String answer){
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
	private void setDataForPrevPregnancy(BasicDetails proposerDetails, BasicDetails insuredDetails,
										 Map<String, Object> dataVariables, boolean isForm2orForm3) {
		String prevAnyComplicationToPregnancy = AppConstants.NA;
		String prevPregnancyComplicationDetails = AppConstants.NA;

		if (isForm2orForm3) {
			if (insuredDetails != null && insuredDetails.getMarriageDetails() != null) {
				prevAnyComplicationToPregnancy =
						insuredDetails.getMarriageDetails().getPrevAnyComplicationToPregnancy() != null ?
								insuredDetails.getMarriageDetails().getPrevAnyComplicationToPregnancy() : prevAnyComplicationToPregnancy;

				prevPregnancyComplicationDetails =
						insuredDetails.getMarriageDetails().getPrevPregnancyComplicationDetails() != null ?
								insuredDetails.getMarriageDetails().getPrevPregnancyComplicationDetails() : prevPregnancyComplicationDetails;
			}
		} else {
			if (proposerDetails != null && proposerDetails.getMarriageDetails() != null) {
				prevAnyComplicationToPregnancy =
						proposerDetails.getMarriageDetails().getPrevAnyComplicationToPregnancy() != null ?
								proposerDetails.getMarriageDetails().getPrevAnyComplicationToPregnancy() : prevAnyComplicationToPregnancy;

				prevPregnancyComplicationDetails =
						proposerDetails.getMarriageDetails().getPrevPregnancyComplicationDetails() != null ?
								proposerDetails.getMarriageDetails().getPrevPregnancyComplicationDetails() : prevPregnancyComplicationDetails;
			}
		}

		dataVariables.put("prevAnyComplicationToPregnancy", prevAnyComplicationToPregnancy);
		dataVariables.put("prevPregnancyComplicationDetails", prevPregnancyComplicationDetails);
	}

}
