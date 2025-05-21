package com.mli.mpro.document.service.impl;

import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.YES;
import static com.mli.mpro.productRestriction.util.AppConstants.NO;
import static com.mli.mpro.productRestriction.util.AppConstants.NA;

/**
 * @author akshom4375
 */
@Service
public class ULIPLifeInsuredMapper {


    private static final Logger logger = LoggerFactory.getLogger(ULIPLifeInsuredMapper.class);

    /**
     * Setting data in Context for LifeInsured section of ULIP Proposal Form
     *
     * @param proposalDetails
     * @return
     */
    public Context setDataOfLifeInsured(ProposalDetails proposalDetails) {
        Map<String, Object> dataVariables = new HashMap<>();

        logger.info("ULIP Proposal Form - setting Life Insured details");

        String insuredGender = StringUtils.EMPTY;
        String proposerGender = StringUtils.EMPTY;
        String spouseOccupation = AppConstants.NA;
        String spouseAnnualIncome = AppConstants.NA;
        String spouseCover = AppConstants.NA;
        String isProposerPregnant = AppConstants.NA;
        String pregnantSince = AppConstants.NA;
        String isPregnancyComplicated = AppConstants.NA;
        String pregnancyComplications = AppConstants.NA;
        String insuredspouseOccupation = AppConstants.NA;
        String insuredspouseAnnualIncome = AppConstants.NA;
        String insuredspouseCover = AppConstants.NA;
        String isInsuredPregnant = AppConstants.NA;
        String insuredPregnantSince = AppConstants.NA;
        String isinsuredPregnancyComplicated = AppConstants.NA;
        String insuredPregnancyComplications = AppConstants.NA;
        String familyCover = StringUtils.EMPTY;
        String familyAnnualIncome = StringUtils.EMPTY;
        String isLIIssue = StringUtils.EMPTY;
        String isLiRejected = StringUtils.EMPTY;
        String doLIExist = StringUtils.EMPTY;
        int lifeInsuredAge = 0;
        LifeStyleDetails proposerLifeStyleDetails = null;
        LifeStyleDetails insuredLifeStyleDetails = null;
        BasicDetails proposerDetails = new BasicDetails();
        BasicDetails insuredDetails = new BasicDetails();

        String formType = proposalDetails.getApplicationDetails().getFormType();
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        boolean isForm2 = AppConstants.DEPENDENT.equalsIgnoreCase(formType);
        boolean isForm2orForm3 = (AppConstants.DEPENDENT.equalsIgnoreCase(formType)
                || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType)));
        boolean isWopOrPb = !Utility.isProposerAndInsuredSame(proposalDetails) && Utility.isPayorBenefitRiderPresent(proposalDetails);

        List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
        List<PartyInformation> partyInformationList = proposalDetails.getPartyInformation();

        // Proposer and Insured lifestyle based values
        if (!CollectionUtils.isEmpty(lifeStyleDetailsList) && lifeStyleDetailsList.size() >= 2) {
            proposerLifeStyleDetails = lifeStyleDetailsList.get(0);
            insuredLifeStyleDetails = lifeStyleDetailsList.get(1);
            if (!isForm2) {
                familyCover = proposerLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
                familyAnnualIncome = proposerLifeStyleDetails.getParentsDetails().getAnnualIncome();
                isLIIssue = Utility.evaluateConditionalOperation(
                        proposerLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist(), AppConstants.YES, AppConstants.NO);

                isLiRejected = Utility.evaluateConditionalOperation(
                        proposerLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected(), AppConstants.YES, AppConstants.NO);

                doLIExist = Utility.evaluateConditionalOperation(
                        Utility.orTwoExpressions(
                                proposerLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected(), proposerLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist())
                        , AppConstants.YES
                        , AppConstants.NO);
            } else {
                familyCover = insuredLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
                familyAnnualIncome = insuredLifeStyleDetails.getParentsDetails().getAnnualIncome();
                isLIIssue = (Utility.evaluateConditionalOperation(
                        insuredLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist(), AppConstants.YES, AppConstants.NO));

                isLiRejected = (Utility.evaluateConditionalOperation(
                        insuredLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected(), AppConstants.YES, AppConstants.NO));

                doLIExist = (Utility.evaluateConditionalOperation(
                        Utility.orTwoExpressions(
                                insuredLifeStyleDetails.getInsuranceDetails().isLIPolicyRejected(), insuredLifeStyleDetails.getInsuranceDetails().isLICIPolicyExist())
                        , AppConstants.YES
                        , AppConstants.NO));
            }
        }

        //Set Proposer Lifestyle details && Insured Lifestyle details
        dataVariables = setProposerInsuredLifestyleDetails(proposalDetails, familyCover,
                familyAnnualIncome, dataVariables);

        if (!CollectionUtils.isEmpty(partyInformationList)) {
            proposerDetails = partyInformationList.get(0).getBasicDetails();
            proposerGender = proposerDetails.getGender();
        }
        if (!CollectionUtils.isEmpty(partyInformationList) && partyInformationList.size() >= 2) {
            insuredDetails = partyInformationList.get(1).getBasicDetails();
            insuredGender = insuredDetails.getGender();

            Date lifeInsuredDob = insuredDetails.getDob();
            lifeInsuredAge = getLifeInsuredAge(lifeInsuredDob);
        }

        boolean isLiMinor = Utility.andTwoExpressions(lifeInsuredAge != -1, lifeInsuredAge < 18);
        String liMinorStatus = Utility.evaluateConditionalOperation(
                isLiMinor, AppConstants.YES, AppConstants.NO);

        boolean isProposerFemale = StringUtils.equalsIgnoreCase(proposerGender, "F");
        boolean isInsuredFemale = StringUtils.equalsIgnoreCase(insuredGender, "F");
        boolean isFemale = Utility.orTwoExpressions(isProposerFemale, isInsuredFemale);
        String isFemaleInsured = Utility.evaluateConditionalOperation(
                isFemale, AppConstants.YES, AppConstants.NO);

        boolean isPregnant = Objects.nonNull(proposerDetails.getMarriageDetails()) && Objects.nonNull(insuredDetails.getMarriageDetails())
                && Utility.orTwoExpressions(proposerDetails.getMarriageDetails().isPregnant(), insuredDetails.getMarriageDetails().isPregnant());
        // proposer marriage details
        //NEORW: add null check for marriage details object
        if (Objects.nonNull(proposerDetails.getMarriageDetails()) && Utility.andTwoExpressions(null != proposerDetails.getMarriageDetails(), isProposerFemale)) {
            isPregnant = proposerDetails.getMarriageDetails().isPregnant();
            spouseOccupation = proposerDetails.getMarriageDetails().getSpouseOccupation();
            spouseAnnualIncome = proposerDetails.getMarriageDetails().getSpouseAnnualIncome();
            spouseCover = proposerDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse();
            isProposerPregnant =
                    Utility.evaluateConditionalOperation(
                            proposerDetails.getMarriageDetails().isPregnant(), AppConstants.YES, AppConstants.NO);
            if (proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                    || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
                pregnantSince = isProposerFemale ? (!StringUtils.isEmpty(proposerDetails.getMarriageDetails().getPregnantSince())
                        ? Utility.getPregnancyMonths(proposerDetails.getMarriageDetails().getPregnantSince())
                        : AppConstants.NA) : AppConstants.NA;
            } else {
                pregnantSince = isProposerFemale ? (!StringUtils.isEmpty(proposerDetails.getMarriageDetails().getPregnantSince())
                        ? proposerDetails.getMarriageDetails().getPregnantSince().concat(" months")
                        : AppConstants.NA) : AppConstants.NA;
            }

            isPregnancyComplicated = Utility.evaluateConditionalOperation(
                    proposerDetails.getMarriageDetails().isAnyComplicationToPregnancy(),
                    "YES", "NO");

            pregnancyComplications = Utility.evaluateConditionalOperation(
                    StringUtils.equalsIgnoreCase(isPregnancyComplicated, AppConstants.YES) &&
                            StringUtils.isNotEmpty(proposerDetails.getMarriageDetails().getPregnancyComplicationDetails())
                    , proposerDetails.getMarriageDetails().getPregnancyComplicationDetails()
                    , AppConstants.NA);
        }


        // insured marriage details
        if (null != insuredDetails.getMarriageDetails() && ((AppConstants.DEPENDENT.equalsIgnoreCase(formType)
                || (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))))) {
            insuredspouseOccupation = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(isInsuredFemale, isForm2orForm3)
                    , insuredDetails.getMarriageDetails().getSpouseOccupation()
                    , AppConstants.NA);
            insuredspouseAnnualIncome = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(isInsuredFemale, isForm2orForm3)
                    , insuredDetails.getMarriageDetails().getSpouseAnnualIncome()
                    , AppConstants.NA);
            insuredspouseCover = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(isInsuredFemale, isForm2orForm3)
                    , insuredDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse()
                    , AppConstants.NA);
            isInsuredPregnant = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(isInsuredFemale, isForm2orForm3)
                    , Utility.evaluateConditionalOperation(
                            insuredDetails.getMarriageDetails().isPregnant(), AppConstants.YES, AppConstants.NO)
                    , AppConstants.NA);
            insuredPregnantSince = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(isInsuredFemale, isForm2orForm3),
                    !StringUtils.isEmpty(insuredDetails.getMarriageDetails().getPregnantSince())
                            ? insuredDetails.getMarriageDetails().getPregnantSince().concat(" months")
                            : AppConstants.NA
                    , AppConstants.NA);
            isinsuredPregnancyComplicated = Utility.evaluateConditionalOperation(
                    Utility.andTwoExpressions(isInsuredFemale, isForm2orForm3)
                    , Utility.evaluateConditionalOperation(
                            insuredDetails.getMarriageDetails().isAnyComplicationToPregnancy()
                            , AppConstants.YES, AppConstants.NO)
                    , AppConstants.NA);

            insuredPregnancyComplications = Utility.evaluateConditionalOperation(
                    Utility.andThreeExpressions(
                            isInsuredFemale, isForm2orForm3, StringUtils.equalsIgnoreCase(isinsuredPregnancyComplicated, AppConstants.YES))
                    , Utility.evaluateConditionalOperation(
                            StringUtils.isNotEmpty(insuredDetails.getMarriageDetails().getPregnancyComplicationDetails())
                            , insuredDetails.getMarriageDetails().getPregnancyComplicationDetails()
                            , AppConstants.NA)
                    , AppConstants.NA);

        }
        // set data for insurance history for NEO
        setInsuranceHistoryDataForNeo(dataVariables, proposalDetails);

        // Data setting in dataMap
        dataVariables.put("healthQuesSet", isWopOrPb);
        dataVariables.put("anyPregnancyComplications", pregnancyComplications);
        //FUL2-144677 start
        dataVariables.put("ciPolicy", Utility.evaluateConditionalOperation(
                (!(AppConstants.FORM_TYPE_SELF.equalsIgnoreCase(formType)
                        || (AppConstants.FORM3.equalsIgnoreCase(formType) && Utility.schemeBCase(schemeType)))), doLIExist, AppConstants.NA));
        //FUL2-144677 end
        dataVariables.put("fullName", AppConstants.NA);
        dataVariables.put("insuredspouseOccupation", insuredspouseOccupation);
        dataVariables.put("insuredspouseAnnualIncome", insuredspouseAnnualIncome);
        dataVariables.put("insuredspouseInsurance", insuredspouseCover);
        dataVariables.put("insuredfullName", AppConstants.NA);
        dataVariables.put("insuredPregnant", isInsuredPregnant);
        dataVariables.put("insuredPregnantSince", insuredPregnantSince);
        dataVariables.put("isinsuredPregnancyComplicated", isinsuredPregnancyComplicated);
        dataVariables.put("insuredPregnancyComplications", insuredPregnancyComplications);
        dataVariables.put("isFemale", isFemale);
        dataVariables.put("isFemaleInsured", isFemaleInsured);
        dataVariables.put("isLIExist", StringUtils.isNotBlank(doLIExist) ? doLIExist : AppConstants.NO);
        dataVariables.put("isLiMinor", isLiMinor);
        dataVariables.put("isLiIssued", isLIIssue);
        dataVariables.put("isLiRejected", StringUtils.isNotBlank(isLiRejected) ? isLiRejected : AppConstants.NO);
        dataVariables.put("isPregnancyComplicated", isPregnancyComplicated);
        dataVariables.put("isPregnant", isPregnant);
        dataVariables.put("liMinorStatus", liMinorStatus);
        dataVariables.put("proposerFormFlag", Utility.isProposerAndInsuredSame(proposalDetails));
        dataVariables.put("proposerPregnant", isProposerPregnant);
        dataVariables.put("pregnantSince", pregnantSince);
        dataVariables.put("pregnancyComplications", pregnancyComplications);
        dataVariables.put("spouseAnnualIncome", Utility.evaluateConditionalOperation(
                StringUtils.isNotBlank(spouseAnnualIncome), spouseAnnualIncome, AppConstants.NA));
        dataVariables.put("spouseInsurance", Utility.evaluateConditionalOperation(
                StringUtils.isNotBlank(spouseCover), spouseCover, AppConstants.NA));
        dataVariables.put("spouseOccupation", Utility.evaluateConditionalOperation(
                StringUtils.isNotBlank(spouseOccupation), spouseOccupation, AppConstants.NA));
        //FUL2-144677 start
        dataVariables.put("proposerLiExist",Utility.evaluateConditionalOperation(
                (!(AppConstants.FORM_TYPE_SELF.equalsIgnoreCase(formType)
                        || (AppConstants.FORM3.equalsIgnoreCase(formType) && Utility.schemeBCase(schemeType)))), AppConstants.NA,doLIExist));
        Utility.iibDetailsAdder(proposalDetails, dataVariables);
        //FUL2-144677 end
        // WOP Rider Specific Mapping
        if(isWopOrPb) setProposerLifestyleDetails(dataVariables, proposalDetails);

        Context context = new Context();
        context.setVariables(dataVariables);
        logger.info("Successfully set data for Life Insured section of ULIP Proposal Form!");
        return context;
    }

    private void setProposerLifestyleDetails(Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
		LifeStyleDetails proposerLifeStyleDetails = proposalDetails.getLifeStyleDetails().get(0);
		InsuranceDetails proposerInsuranceDetails = proposerLifeStyleDetails.getInsuranceDetails();
		String proposerLiExist = (proposerInsuranceDetails.isLICIPolicyExist() || proposerInsuranceDetails.isLIPolicyRejected()) ? YES : NO; 
		String proposerLiIssued = proposerInsuranceDetails.isLICIPolicyExist() ? YES : NO;
		String proposerLiOffered = proposerInsuranceDetails.isLIPolicyRejected() ? YES : NO;
		String proposerLiPolicySA = proposerInsuranceDetails.getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
		String proposerFamilyCover = proposerLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
		String proposerCiPolicySA = proposerInsuranceDetails.getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
		String proposerTravelAbroad = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure()) ? proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad() : NA;
		String proposerCountryVisited = Optional.ofNullable(proposerLifeStyleDetails).map(o->o.getTravelAndAdventure()).map(o->o.getTravelOrResideAbroadDetails()).map(o->o.getCICountryTobeVisited()).map(o-> StringUtils.join(o, ", ")).orElse(NA);
		String proposerHazardousActivity = Optional.ofNullable(proposerLifeStyleDetails).map(o->o.getTravelAndAdventure()).map(o->o.getDoYouParticipateInHazardousActivities()).orElse(NA);
		String proposerHazardousActivityDetails = Optional.ofNullable(proposerLifeStyleDetails).map(o->o.getTravelAndAdventure()).map(o->o.getHazardousActivitiesDetails()).map(o->o.getHazardousActivityExtent()).orElse(NA);
		String proposerCriminalCharges = Optional.ofNullable(proposerLifeStyleDetails).map(o->o.getFamilyOrCriminalHistory()).map(o->o.getAnyCriminalCharges()).orElse(NA);
		String proposerCriminalDetails = Optional.ofNullable(proposerLifeStyleDetails).map(o->o.getFamilyOrCriminalHistory()).map(o->o.getSpecifyDetails()).orElse(NA);
		
		dataVariables.put("proposerLiExist", proposerLiExist.toUpperCase());
		dataVariables.put("proposerLiIssued", proposerLiIssued.toUpperCase());
		dataVariables.put("proposerLiOffered", proposerLiOffered.toUpperCase());
		dataVariables.put("proposerLiPolicySA", proposerLiPolicySA.toUpperCase());
		dataVariables.put("proposerFamilyCover", proposerFamilyCover.toUpperCase());
		dataVariables.put("proposerCiPolicySA", proposerCiPolicySA.toUpperCase());
		dataVariables.put("proposerTravelAbroad", proposerTravelAbroad.toUpperCase());
		dataVariables.put("proposerCountryVisited", proposerCountryVisited.toUpperCase());
		dataVariables.put("proposerHazardousActivity", proposerHazardousActivity.toUpperCase());
		dataVariables.put("proposerHazardousActivityDetails", proposerHazardousActivityDetails.toUpperCase());
		dataVariables.put("proposerCriminalCharges", proposerCriminalCharges.toUpperCase());
		dataVariables.put("proposerCriminalDetails", proposerCriminalDetails.toUpperCase());
		if(proposerLiExist.equalsIgnoreCase(YES)) dataVariables.put("isLIExist", YES);
		
	}

	private int getLifeInsuredAge(Date lifeInsuredDob) {
        int lifeInsuredAge = -1;
        ZoneId defaultZoneId = ZoneId.of(AppConstants.APP_TIMEZONE);
        if (null != lifeInsuredDob) {
            Instant lifeInsuredDobInstant = lifeInsuredDob.toInstant();
            LocalDate lifeInsuredDobLocalDate = lifeInsuredDobInstant.atZone(defaultZoneId).toLocalDate();
            lifeInsuredAge = Utility.calculateAge(lifeInsuredDobLocalDate, LocalDate.now());
        }
        return lifeInsuredAge;
    }


    private Map<String, Object> setProposerInsuredLifestyleDetails(ProposalDetails proposalDetails, String familyCover,
                                                                   String familyAnnualIncome, Map<String, Object> dataVariables) {
        String formType = proposalDetails.getApplicationDetails().getFormType();
        String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
        List<LifeStyleDetails> lifeStyleDetailsList = proposalDetails.getLifeStyleDetails();
        LifeStyleDetails proposerLifeStyleDetails = null;
        LifeStyleDetails insuredLifeStyleDetails = null;
        String hazardousActivites = StringUtils.EMPTY;
        String proposerCriminalCharges = StringUtils.EMPTY;
        String sumAssuredLI = StringUtils.EMPTY;
        String criticalIllnessLI = StringUtils.EMPTY;
        String proposerTravelAbroad = StringUtils.EMPTY;
        List<String> countryVisited = null;
        String countryToBevisited = StringUtils.EMPTY;
        String insuredTravelAbroad = StringUtils.EMPTY;
        String insuredhazardousActivity = StringUtils.EMPTY;
        //FUL2-98622 hazardous activity details
        String insuredhazardousActivityDetails = StringUtils.EMPTY;

        String insuredCriminalCharges = StringUtils.EMPTY;
        //FUL2-103414 Criminal Charges Details
        String insuredCriminalChargesDetails = StringUtils.EMPTY;

        List<String> insuredCountryVisited = null;
        String insuredCountryToBevisited = StringUtils.EMPTY;
        HazardousActivitiesDetails hazardousActivitesDetails = new HazardousActivitiesDetails();
        String hazardousActivitesD = "";
        String proposerCriminalDetails = StringUtils.EMPTY;
        String durationOfStay = StringUtils.EMPTY;
        String purpose = StringUtils.EMPTY;
        String cities = StringUtils.EMPTY;

        // Proposer Lifestyle details
        if (!CollectionUtils.isEmpty(lifeStyleDetailsList)) {
            proposerLifeStyleDetails = lifeStyleDetailsList.get(0);

            if (!AppConstants.DEPENDENT.equalsIgnoreCase(formType) && !(AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))) {
                //NEORW-173: this will check that incoming request is from NEO or Aggregator
                if (proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_NEO)
                        || proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
                    if (Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure())) {
                        proposerTravelAbroad = Utility.convertToYesOrNo(
                                proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad());
                        hazardousActivites = Utility.convertToYesOrNo(
                                proposerLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities());
                        BeanUtils.copyProperties(proposerLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails(), hazardousActivitesDetails);
                        hazardousActivitesD = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure()) ?
                                proposerLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails().getHazardousActivityExtent() : AppConstants.BLANK;
                        countryVisited = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails()) ?
                                proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited() : Collections.emptyList();
                        countryToBevisited = StringUtils.join(countryVisited, ",");
                        durationOfStay = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails()) ?
                                proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getDurationOfStay() : AppConstants.NA;
                        purpose = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails()) ?
                                proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurpose() : AppConstants.NA;
                        cities = Objects.nonNull(proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails()) ?
                                proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCity() : AppConstants.NA;
                    }

                    proposerCriminalCharges = Objects.nonNull(proposerLifeStyleDetails.getFamilyOrCriminalHistory())
                            ? Utility.convertToYesOrNo(
                            proposerLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges()) : AppConstants.NO;
                    proposerCriminalDetails = Objects.nonNull(proposerLifeStyleDetails.getFamilyOrCriminalHistory()) ?
                            proposerLifeStyleDetails.getFamilyOrCriminalHistory().getSpecifyDetails() : AppConstants.BLANK;
                    familyCover = Objects.nonNull(proposerLifeStyleDetails.getParentsDetails()) ? proposerLifeStyleDetails.getParentsDetails().getTotalInsuranceCover() : "";

                    if (Objects.nonNull(proposerLifeStyleDetails.getInsuranceDetails()) &&
                            Objects.nonNull(proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails()) &&
                            !proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().isEmpty()) {
                        criticalIllnessLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
                        sumAssuredLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
                    }
                    dataVariables.put("declarationVersionDate", Utility.getUlipDeclarationVersionDate());

                } else {
                    proposerTravelAbroad = proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad();

                    hazardousActivites = proposerLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities();
                    //FUL2-98622 hazardous activity details
                    BeanUtils.copyProperties(proposerLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails(), hazardousActivitesDetails);

                    proposerCriminalCharges = proposerLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges();
                    //FUL2-103414 Criminal Charges Details
                    proposerCriminalDetails = Objects.nonNull(proposerLifeStyleDetails.getFamilyOrCriminalHistory()) ?
                            proposerLifeStyleDetails.getFamilyOrCriminalHistory().getSpecifyDetails() : AppConstants.BLANK;

                    familyCover = proposerLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
                    familyAnnualIncome = proposerLifeStyleDetails.getParentsDetails().getAnnualIncome();
                    sumAssuredLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
                    criticalIllnessLI = proposerLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
                    countryVisited = proposerLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited();
                    countryToBevisited = StringUtils.join(countryVisited, ",");
                }
            }
            if(Utility.isChannelNeoOrAggregator(proposalDetails)){
                dataVariables.put("travelAbroad", Utility.evaluateConditionalOperation(
                        StringUtils.isNotBlank(proposerTravelAbroad), StringUtils.upperCase(proposerTravelAbroad), AppConstants.NA));
                dataVariables.put("hazardousActivity", Utility.evaluateConditionalOperation(
                        StringUtils.isNotBlank(hazardousActivites), StringUtils.upperCase(hazardousActivites), AppConstants.NA));
                dataVariables.put("criminalCharges", Utility.evaluateConditionalOperation(
                        StringUtils.isNotBlank(proposerCriminalCharges), StringUtils.upperCase(proposerCriminalCharges), AppConstants.NA));
                dataVariables.put("visitedCountries", countryToBevisited);
                dataVariables.put("hazardousActivitiesDetails", hazardousActivitesDetails);
                //FUL2-98622 Hazardous Activity Details
                dataVariables.put("uliphazardousActivitesDetails", hazardousActivitesDetails.getHazardousActivityExtent());
                dataVariables.put("criminalChargesDetails", proposerCriminalDetails);
                dataVariables.put(AppConstants.DURATION_OF_STAY, durationOfStay);
                dataVariables.put(AppConstants.TRAVEL_CITIES, cities);
                dataVariables.put(AppConstants.TRAVEL_PURPOSE, purpose);
                dataVariables.put("hazardousActivityDetails", hazardousActivitesD);
            } else {
                dataVariables.put("proposerTravelAbroad", Utility.evaluateConditionalOperation(
                        StringUtils.isNotBlank(proposerTravelAbroad), StringUtils.upperCase(proposerTravelAbroad), AppConstants.NA));
                dataVariables.put("proposerHazardousActivity", Utility.evaluateConditionalOperation(
                        StringUtils.isNotBlank(hazardousActivites), StringUtils.upperCase(hazardousActivites), AppConstants.NA));
                dataVariables.put("proposerCriminalCharges", Utility.evaluateConditionalOperation(
                        StringUtils.isNotBlank(proposerCriminalCharges), StringUtils.upperCase(proposerCriminalCharges), AppConstants.NA));
                dataVariables.put("visitedCountries", countryToBevisited);
                dataVariables.put("hazardousActivitiesDetails", hazardousActivitesDetails);
                //FUL2-98622 Hazardous Activity Details
                dataVariables.put("proposerHazardousActivityDetails", hazardousActivitesDetails.getHazardousActivityExtent());
                dataVariables.put("proposerCriminalDetails", proposerCriminalDetails);
                dataVariables.put(AppConstants.DURATION_OF_STAY, durationOfStay);
                dataVariables.put(AppConstants.TRAVEL_CITIES, cities);
                dataVariables.put(AppConstants.TRAVEL_PURPOSE, purpose);
                dataVariables.put("hazardousActivityDetails", hazardousActivitesD);
            }
        }
        // Insured Lifestyle details
        if (!CollectionUtils.isEmpty(lifeStyleDetailsList) && lifeStyleDetailsList.size() >= 2) {
            insuredLifeStyleDetails = lifeStyleDetailsList.get(1);

            if (AppConstants.DEPENDENT.equalsIgnoreCase(formType) ||
                    (AppConstants.FORM3.equalsIgnoreCase(formType) && !Utility.schemeBCase(schemeType))) {
                insuredTravelAbroad = insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad();
                insuredhazardousActivity = insuredLifeStyleDetails.getTravelAndAdventure().getDoYouParticipateInHazardousActivities();
                //FUL2-98622 hazardous activity details
                insuredhazardousActivityDetails = insuredLifeStyleDetails.getTravelAndAdventure().getHazardousActivitiesDetails().getHazardousActivityExtent();

                insuredCriminalCharges = insuredLifeStyleDetails.getFamilyOrCriminalHistory().getAnyCriminalCharges();
                //FUL2-103414 Criminal Charges Details
                insuredCriminalChargesDetails = insuredLifeStyleDetails.getFamilyOrCriminalHistory().getSpecifyDetails();

                String insuredSumAssuredLI = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForLI();
                String insuredCriticalIllnessLI = insuredLifeStyleDetails.getInsuranceDetails().getLiciPolicyDetails().get(0).getTotalSumAssuredForCI();
                String insuredFamilyCover = insuredLifeStyleDetails.getParentsDetails().getTotalInsuranceCover();
                String insuredFamilyAnnualIncome = insuredLifeStyleDetails.getParentsDetails().getAnnualIncome();
                familyCover = Utility.evaluateConditionalOperation(
                        StringUtils.isBlank(insuredFamilyCover), AppConstants.NA, familyCover);
                familyAnnualIncome = Utility.evaluateConditionalOperation(
                        StringUtils.isBlank(insuredFamilyAnnualIncome), AppConstants.NA, familyAnnualIncome);
                sumAssuredLI = Utility.evaluateConditionalOperation(
                        StringUtils.isBlank(insuredSumAssuredLI), AppConstants.NA, insuredSumAssuredLI);
                criticalIllnessLI = Utility.evaluateConditionalOperation(
                        StringUtils.isBlank(insuredCriticalIllnessLI), AppConstants.NA, insuredCriticalIllnessLI);
                insuredCountryVisited = insuredLifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited();
                insuredCountryToBevisited = StringUtils.join(insuredCountryVisited, ",");
            }
            dataVariables.put("insuredtravelAbroad",
                    Utility.evaluateConditionalOperation(
                            StringUtils.isNotBlank(insuredTravelAbroad), StringUtils.upperCase(insuredTravelAbroad), AppConstants.NA));
            dataVariables.put("insuredhazardousActivity", Utility.evaluateConditionalOperation(
                    StringUtils.isNotBlank(insuredhazardousActivity), StringUtils.upperCase(insuredhazardousActivity), AppConstants.NA));
            //FUL2-98622 hazardous activity details
            dataVariables.put("insuredhazardousActivityDetails",StringUtils.isNotEmpty(insuredhazardousActivityDetails)?insuredhazardousActivityDetails.toUpperCase():AppConstants.NA);

            dataVariables.put("insuredcriminalCharges", Utility.evaluateConditionalOperation(
                    StringUtils.isNotBlank(insuredCriminalCharges), StringUtils.upperCase(insuredCriminalCharges), AppConstants.NA));
            dataVariables.put("insuredvisitedCountries", insuredCountryToBevisited);
            //FUL2-103414 Criminal Charges Details
            dataVariables.put("insuredCriminalChargesDetails",StringUtils.isNotEmpty(insuredCriminalChargesDetails)?insuredCriminalChargesDetails:AppConstants.NA);


        }

        dataVariables.put("familyCover", Utility.evaluateConditionalOperation(
                StringUtils.isNotBlank(familyCover), familyCover, AppConstants.NA));
        dataVariables.put("familyAnnualIncome", Utility.evaluateConditionalOperation(
                StringUtils.isNotBlank(familyAnnualIncome), familyAnnualIncome, AppConstants.NA));
        dataVariables.put("totalSumAssured", Utility.evaluateConditionalOperation(
                StringUtils.isNotEmpty(sumAssuredLI), sumAssuredLI, AppConstants.NA));
        dataVariables.put("totalSumAssuredCI", Utility.evaluateConditionalOperation(
                StringUtils.isNotEmpty(criticalIllnessLI), criticalIllnessLI, AppConstants.NA));
        return dataVariables;
    }

    public void setInsuranceHistoryDataForNeoForLI(Map<String, Object> dataVariables,
        ProposalDetails proposalDetails) {
        String insuredEverLIIssuedPendingLapsed = "";
        String insuredEverLIRejectedPostponed = "";
        String insuredTotalSumAssuredLI = "";
        String insuredTotalSumAssuredCI = "";

        if (proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_NEO)
            || proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)
            && Objects.nonNull(proposalDetails.getLifeStyleDetails().get(1))) {
            InsuranceDetails insuranceDetails = proposalDetails.getLifeStyleDetails().get(1)
                .getInsuranceDetails();
            insuredEverLIIssuedPendingLapsed = Utility
                .convertToYesOrNo(insuranceDetails.getEverLIIssuedPendingLapsed());
            insuredEverLIRejectedPostponed = Utility
                .convertToYesOrNo(insuranceDetails.getEverLIRejectedPostponed());
            insuredTotalSumAssuredLI =
                !StringUtils.isEmpty(insuranceDetails.getTotalLifeSumAssured()) ? insuranceDetails
                    .getTotalLifeSumAssured() : AppConstants.BLANK;
            insuredTotalSumAssuredCI =
                !StringUtils.isEmpty(insuranceDetails.getTotalCISumAssured()) ? insuranceDetails
                    .getTotalCISumAssured() : AppConstants.BLANK;
        }
        dataVariables.put("insuredEverLIIssuedPendingLapsed",
            Utility.convertToYesOrNo(insuredEverLIIssuedPendingLapsed));
        dataVariables
            .put("insuredEverLIRejectedPostponed", Utility.convertToYesOrNo(insuredEverLIRejectedPostponed));
        dataVariables.put("insuredTotalSumAssuredLI", insuredTotalSumAssuredLI);
        dataVariables.put("neoInsuredTotalSumAssuredCI", insuredTotalSumAssuredCI);
        dataVariables.put("insuredLIIssuedPendingOrRejectedPostponed",
            checkForLIIssuedPendingOrRejectedPostPoned(insuredEverLIIssuedPendingLapsed, insuredEverLIRejectedPostponed));
    }

    public void setInsuranceHistoryDataForNeo(Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
        String everLIIssuedPendingLapsed = "";
        String everLIRejectedPostponed = "";
        //NEORW-173: this will check that incoming request is from NEO or Aggregator
        if (checkMandatoryDetailsForNeoAndAggregator(proposalDetails)) {
            InsuranceDetails insuranceDetails = proposalDetails.getLifeStyleDetails().get(0).getInsuranceDetails();
            everLIIssuedPendingLapsed = Utility.convertToYesOrNo(insuranceDetails.getEverLIIssuedPendingLapsed());
            everLIRejectedPostponed = Utility.convertToYesOrNo(insuranceDetails.getEverLIRejectedPostponed());
            setSumAssuredForLIandCI(dataVariables, proposalDetails, insuranceDetails);
            setEverLIIssuedPendingLapsedDetailsList(dataVariables, everLIIssuedPendingLapsed, insuranceDetails);
            everLIRejectedPostponedDetailsList(dataVariables, everLIRejectedPostponed, insuranceDetails);
        }
        dataVariables.put(AppConstants.EVER_LI_ISSUED_PENDING_LAPSED, Utility.convertToYesOrNo(everLIIssuedPendingLapsed));
        dataVariables.put(AppConstants.EVER_LI_REJECTED_PENDING, Utility.convertToYesOrNo(everLIRejectedPostponed));
        dataVariables.put(AppConstants.EVER_LI_ISSUEDPENDING_OR_REJECTEDPOSTPONED,
            checkForLIIssuedPendingOrRejectedPostPoned(everLIIssuedPendingLapsed, everLIRejectedPostponed));
    }

    private void setSumAssuredForLIandCI(Map<String, Object> dataVariables, ProposalDetails proposalDetails,
        InsuranceDetails insuranceDetails) {
        String totalSumAssuredCI;
        String totalSumAssuredLI;
        if (isAggregator(proposalDetails)) {
                totalSumAssuredLI = setTotalSumAssuredForAggregator(insuranceDetails, "Life");
                totalSumAssuredCI = setTotalSumAssuredForAggregator(insuranceDetails, "CI/DD");
        }else {
            totalSumAssuredLI = !StringUtils.isEmpty(insuranceDetails.getTotalLifeSumAssured()) ? insuranceDetails
                .getTotalLifeSumAssured() : AppConstants.BLANK;
            totalSumAssuredCI = !StringUtils.isEmpty(insuranceDetails.getTotalCISumAssured()) ? insuranceDetails
                .getTotalCISumAssured() : AppConstants.BLANK;
        }
        dataVariables.put(AppConstants.TOTAL_SUM_ASSURED_LI, totalSumAssuredLI);
        dataVariables.put(AppConstants.TOTAL_SUM_ASSURED_CI, totalSumAssuredCI);
    }

    private void everLIRejectedPostponedDetailsList(Map<String, Object> dataVariables,
        String everLIRejectedPostponed, InsuranceDetails insuranceDetails) {
        List<LICIPolicyDetails> everLIRejectedPostponedDetailsList = null;
        if (AppConstants.YES.equalsIgnoreCase(everLIRejectedPostponed)
                && Objects.nonNull(insuranceDetails.getEverLIRejectedPostponedDetails())
                && !insuranceDetails.getEverLIRejectedPostponedDetails().isEmpty()) {
            everLIRejectedPostponedDetailsList = insuranceDetails.getEverLIRejectedPostponedDetails();
        }
        dataVariables.put(AppConstants.EVER_LI_REJECTED_PENDING_LIST, Objects.nonNull(everLIRejectedPostponedDetailsList)
            ? everLIRejectedPostponedDetailsList : Collections.emptyList());
    }

    private void setEverLIIssuedPendingLapsedDetailsList(Map<String, Object> dataVariables, String everLIIssuedPendingLapsed,
        InsuranceDetails insuranceDetails) {
        List<LICIPolicyDetails> everLIIssuedPendingLapsedDetailsList = null;
        if (AppConstants.YES.equalsIgnoreCase(everLIIssuedPendingLapsed)
                && Objects.nonNull(insuranceDetails.getEverLIIssuedPendingLapsedDetails())
                && !insuranceDetails.getEverLIIssuedPendingLapsedDetails().isEmpty()) {
            everLIIssuedPendingLapsedDetailsList = insuranceDetails.getEverLIIssuedPendingLapsedDetails();
        }
        dataVariables.put(AppConstants.EVER_LI_ISSUED_PENDING_LAPSED_LIST, Objects.nonNull(everLIIssuedPendingLapsedDetailsList)
            ? everLIIssuedPendingLapsedDetailsList : Collections.emptyList());
    }

    private boolean checkMandatoryDetailsForNeoAndAggregator(ProposalDetails proposalDetails) {
        return proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_NEO)
            || proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)
            && Objects.nonNull(proposalDetails.getLifeStyleDetails())
            && !proposalDetails.getLifeStyleDetails().isEmpty()
            && Objects.nonNull(proposalDetails.getLifeStyleDetails().get(0).getInsuranceDetails());
    }

    private boolean isAggregator(ProposalDetails proposalDetails) {
        return Objects.nonNull(proposalDetails.getChannelDetails())
            && AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel());
    }

    private String setTotalSumAssuredForAggregator(InsuranceDetails insuranceDetails, String sumAssuredOfPolicyType) {
        try {
            List<LICIPolicyDetails> everLIIssuedPendingLapsedDetails =Optional.ofNullable(insuranceDetails)
                .map(InsuranceDetails::getEverLIIssuedPendingLapsedDetails)
                .orElse(Collections.emptyList());
            if(!CollectionUtils.isEmpty(everLIIssuedPendingLapsedDetails)) {
                if ("Life".equals(sumAssuredOfPolicyType)) {
                    return getSumAssuredForLife(everLIIssuedPendingLapsedDetails);
                }
                return getSummAssuredForCI(everLIIssuedPendingLapsedDetails);
            }
            return AppConstants.BLANK;
        }catch (Exception e){
            logger.error("exception occured while calculation sumassured for the policy", e);
            return AppConstants.BLANK;
        }
    }

    private String getSummAssuredForCI(List<LICIPolicyDetails> everLIIssuedPendingLapsedDetails) {
        return String.valueOf(everLIIssuedPendingLapsedDetails.stream()
            .filter(
                liciPolicyDetails -> !"Life".equalsIgnoreCase(liciPolicyDetails.getTypeOfPolicy()))
            .mapToInt(liciPolicyDetails -> Integer.parseInt(liciPolicyDetails.getTotalSumAssured()))
            .sum());
    }

    private String getSumAssuredForLife(List<LICIPolicyDetails> everLIIssuedPendingLapsedDetails) {
        return String.valueOf(everLIIssuedPendingLapsedDetails.stream()
            .filter(
                liciPolicyDetails -> liciPolicyDetails.getTypeOfPolicy().equalsIgnoreCase("Life"))
            .mapToInt(liciPolicyDetails -> Integer.parseInt(liciPolicyDetails.getTotalSumAssured()))
            .sum());
    }

    private String checkForLIIssuedPendingOrRejectedPostPoned(String everLIIssuedPendingLapsed,
        String everLIRejectedPostponed) {
        if (everLIIssuedPendingLapsed.equalsIgnoreCase(AppConstants.YES) || everLIRejectedPostponed.equalsIgnoreCase(AppConstants.YES)) {
            return AppConstants.YES;
        }
        return AppConstants.NO;
    }
}
