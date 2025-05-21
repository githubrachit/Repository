package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.enums.FamilyType;
import com.mli.mpro.document.models.MedicalQuestions;
import com.mli.mpro.document.models.NeoAggregatorMedicalQuestions;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

/**
 * @author manish on 23/03/20
 */
@Service
public class ProposalFormAnnexureMapper {

    private static final Logger logger = LoggerFactory.getLogger(ProposalFormAnnexureMapper.class);

    @Value("${swiss.applicable.date}")
    private String swissApplicableDate;

    @Autowired
    private ProposalMedicalDetailsMapper medicalDetailsMapper;

public Context setDataForProposalFormAnnexure(ProposalDetails proposalDetails) throws UserHandledException {
        Map<String, Object> dataVariables = new HashMap<>();
        Context context = new Context();
        try {
            logger.info("Starting Proposal Form Annexure data population for transactionId {}", proposalDetails.getTransactionId());
            setDataForMedicalAndTravelQuestion(dataVariables, proposalDetails,
                proposalDetails.getLifeStyleDetails().get(0),proposalDetails.getEmploymentDetails().getPartiesInformation().get(0));
            if (Utility.isApplicationIsForm2(proposalDetails) || Utility.isProductSWPJL(proposalDetails)
                    || Utility.isSSPJLProduct(proposalDetails) && Objects.nonNull(proposalDetails.getLifeStyleDetails().get(1))) {
                Map<String, Object> dataDocumentForLI = new HashMap<>();
                setDataForMedicalAndTravelQuestion(dataDocumentForLI, proposalDetails,
                    proposalDetails.getLifeStyleDetails().get(1),
                    proposalDetails.getEmploymentDetails().getPartiesInformation().get(1));
                    setDataForInsuranceDetails(dataDocumentForLI, Collections.singletonList(proposalDetails.getLifeStyleDetails().get(1)));
                dataVariables.put("lIDataMap", dataDocumentForLI);
            }
                setDataForInsuranceDetails(dataVariables, proposalDetails.getLifeStyleDetails());
                setSwissReGeneralQuestion(dataVariables, proposalDetails.getLifeStyleDetails());
                if(Utility.isSSPJLProduct(proposalDetails)){
                setSwissReGeneralQuestionSSPJL(dataVariables, proposalDetails.getLifeStyleDetails());
                }
            boolean isPaymentDate = setPaymentDate(proposalDetails.getPaymentDetails());
            dataVariables.put("isPaymentDate", isPaymentDate);

            convertNullValuesToBlank(dataVariables);
            context.setVariables(dataVariables);
        } catch (Exception ex) {
            logger.info("Data addition failed for Proposal form Annexure for transactionId {} : ", proposalDetails.getTransactionId(), ex);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Ending Proposal Form Annexure Data Population for transactionId {}", proposalDetails.getTransactionId());
        return context;
    }

    /**
     * Main function to data populate for Medical and Travel Question for STP
     * @param dataVariables
     * @param proposalDetails
     */
    private void setDataForMedicalAndTravelQuestion(Map<String, Object> dataVariables, ProposalDetails proposalDetails ,LifeStyleDetails lifeStyleDetails ,PartyInformation partyInformation) {
        String countryToBevisited = "";
        String travelOrResideAbroad = "";
        String city = "";
        String duration = "";
        String purpose = "";
        // set industry details
        if (Objects.nonNull(partyInformation) &&
            Objects.nonNull(partyInformation.getPartyDetails())) {
            setDataForIndustry(dataVariables, partyInformation.getPartyDetails());
        }

        // set PEP details
        if(Objects.nonNull(proposalDetails.getEmploymentDetails())) {
            setDataForPEP(dataVariables, proposalDetails, lifeStyleDetails.getPartyType());
            logger.info("isPep : {}", dataVariables.get(AppConstants.POLITICALLY_EXPOSED));
        }

        // set medical and travel details
        if (Objects.nonNull(proposalDetails.getLifeStyleDetails())
                && !proposalDetails.getLifeStyleDetails().isEmpty()
                && Objects.nonNull(lifeStyleDetails.getTravelAndAdventure())) {

            travelOrResideAbroad = Utility.convertToYesOrNo(lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroad());
            if (Objects.nonNull(lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails())
                    && Objects.nonNull(lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited())) {
                List<String> countryVisited = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCICountryTobeVisited();
                if (countryVisited.isEmpty()) {
                    countryVisited = Collections.emptyList();
                }
                countryToBevisited = org.apache.commons.lang3.StringUtils.join(countryVisited, ",");
             }
            if(Objects.nonNull(lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails())) {
                city = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getCity();
                duration = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getDurationOfStay();
                String purposeForTravel = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurpose();
                if (Utility.isChannelAggregator(proposalDetails)) {
                    if (OTHERS.equalsIgnoreCase(purposeForTravel) || "07".equalsIgnoreCase(purposeForTravel)) {
                        purpose = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurposeOthers();
                    } else {
                        purpose = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurpose();
                    }
                } else {
                    if (Objects.nonNull(lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurposeOthers()) && !(lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurposeOthers().isEmpty())) {
                        purpose = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurposeOthers();
                    } else {
                        purpose = lifeStyleDetails.getTravelAndAdventure().getTravelOrResideAbroadDetails().getPurpose();
                    }
                }

            }
            setDataForMedicationQuestion(dataVariables, proposalDetails,lifeStyleDetails);
        }
        dataVariables.put(AppConstants.COUNTRIES_TO_BE_VISITED, countryToBevisited);
        dataVariables.put(AppConstants.TRAVEL_OR_RESIDE_ABROAD, travelOrResideAbroad);
        dataVariables.put(AppConstants.DECLARATION_VERSION_DATE, Utility.getStpDeclarationVersionDate());
        dataVariables.put("city",Utility.nullSafe(city));
        dataVariables.put("durationInWeek",Utility.nullSafe(duration));
        dataVariables.put("purpose",Utility.nullSafe(purpose));
    }

    public void setDataForInsuranceDetails(Map<String, Object> dataVariables,
        List<LifeStyleDetails> lifeStyleDetails) {
        String everLIIssuedPendingLapsed = "";
        String everLIRejectedPostponed = "";
        String activeLIPolicySA = "";
        InsuranceDetails insuranceDetails = null;
        List<IibDetails> iibDetailsList;
        if (!CollectionUtils.isEmpty(lifeStyleDetails)) {
            insuranceDetails = lifeStyleDetails.get(0).getInsuranceDetails();
            everLIIssuedPendingLapsed = Utility
                .convertToYesOrNo(insuranceDetails.getEverLIIssuedPendingLapsed());
            everLIRejectedPostponed = Utility
                .convertToYesOrNo(insuranceDetails.getEverLIRejectedPostponed());
            activeLIPolicySA = Utility
                    .nullSafe(insuranceDetails.getActiveLIPolicySA());
            if (Objects.nonNull(insuranceDetails.getIibDetails()) && !insuranceDetails.getIibDetails().isEmpty()) {
                iibDetailsList = insuranceDetails.getIibDetails();
                dataVariables.put("iibList", Objects.nonNull(iibDetailsList)
                        ? iibDetailsList : Collections.emptyList());
            }

        }
        dataVariables.put("everLIIssuedPendingLapsed", everLIIssuedPendingLapsed);
        dataVariables.put("everLIRejectedPostponed", everLIRejectedPostponed);
        dataVariables.put("insuranceDetails", insuranceDetails);
        dataVariables.put("activeLIPolicySA", activeLIPolicySA);
    }

    public void setSwissReGeneralQuestion(Map<String, Object> dataVariables,
          List<LifeStyleDetails> lifeStyleDetails) {
        String isWeightChanged = "";
        String weightChangeReason = "";
        HeightAndWeight heightAndWeight = null;
        if (!CollectionUtils.isEmpty(lifeStyleDetails)) {
           heightAndWeight = lifeStyleDetails.get(0).getHeightAndWeight();
           isWeightChanged = Utility.convertToYesOrNo(heightAndWeight.getIsWeightChanged());
           weightChangeReason = Utility.nullSafe(heightAndWeight.getWeightChangeReason());
        }
        dataVariables.put("isWeightChanged", isWeightChanged);
        dataVariables.put("weightChangeReason", weightChangeReason);
    }

    public void setProposerWeight(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables) {
        dataVariables.put(AppConstants.PROPOSER_WEIGHT, Objects.nonNull(lifeStyleDetails.getHeightAndWeight())
                ? Utility.nullSafe(lifeStyleDetails.getHeightAndWeight().getWeight())
                : AppConstants.BLANK);
    }

    public void setProposerHeight(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables) {
        String heightInCM = "";
        String heightInFt = "";
        String heightInInch = "";

        if (Objects.nonNull(lifeStyleDetails.getHeightAndWeight())) {
            if ("01".equalsIgnoreCase(lifeStyleDetails.getHeightAndWeight().getHeightMetric())
                    && !StringUtils.isEmpty(lifeStyleDetails.getHeightAndWeight().getHeight())) {
                String[] strArr = lifeStyleDetails.getHeightAndWeight().getHeight().split("\\.");
                heightInFt = strArr.length >= 1 ? strArr[0] : AppConstants.BLANK;
                heightInInch = strArr.length >= 2 ? strArr[1] : AppConstants.BLANK;
            } else {
                heightInCM = Utility.nullSafe(lifeStyleDetails.getHeightAndWeight().getHeight());
            }
        }
        dataVariables.put(AppConstants.PROPOSER_HEIGHT_CM, heightInCM);
        dataVariables.put(AppConstants.PROPOSER_HEIGHT_FT, heightInFt);
        dataVariables.put(AppConstants.PROPOSER_HEIGHT_IN, heightInInch);
    }

    private void setDataForIndustry(Map<String, Object> dataVariables, PartyDetails partyDetails) {

        String industryType = "";
        String natureOfDuties = "";
        String industryTypeOthers = "";
        String jobType = "";
        String officialEmail = "";
        String tenureOfJob = "";
        String specifyDutiesType = "";
        String specifyDutiesTypeOthers = "";

        if (Objects.nonNull(partyDetails)) {
            industryType = Objects.nonNull(partyDetails.getIndustryDetails())
                    ? Utility.nullSafe(partyDetails.getIndustryDetails().getIndustryType()) : "";
            natureOfDuties = Utility.nullSafe(partyDetails.getNatureOfDuties());
            industryTypeOthers = Objects.nonNull(partyDetails.getIndustryDetails())
                    ? Utility.nullSafe(partyDetails.getIndustryDetails().getIndustryTypeOthers()) : AppConstants.BLANK;

            if (Objects.nonNull(partyDetails.getIndustryDetails()) && Objects.nonNull(partyDetails.getIndustryDetails().getIndustryInfo())) {
                setDataForIndustryReflexive(partyDetails, industryType, dataVariables);
                if (AppConstants.NAVY.equalsIgnoreCase(partyDetails.getIndustryDetails().getIndustryType())) {
                    dataVariables.put(AppConstants.NAVY_REFLEXIVE_1, Utility.nullSafe(partyDetails.getRank()));
                }
            }

            jobType = Utility.nullSafe(partyDetails.getJobType());
            officialEmail = Utility.nullSafe(partyDetails.getOfficialEmail());
            tenureOfJob = Utility.nullSafe(partyDetails.getTenureOfJob());
            specifyDutiesType = Utility.nullSafe(partyDetails.getSpecifyDutiesType());
            specifyDutiesTypeOthers = Utility.nullSafe(partyDetails.getSpecifyDutiesTypeOthers());
        }
        dataVariables.put(AppConstants.INDUSTRY_TYPE, industryType);
        dataVariables.put(AppConstants.NATURE_OF_DUTIES, natureOfDuties);
        dataVariables.put(AppConstants.INDUSTRY_TYPE_OTHER, industryTypeOthers);
        dataVariables.put(AppConstants.JOB_TYPE, jobType);
        dataVariables.put(AppConstants.OFFICIAL_EMAIL, officialEmail);
        dataVariables.put(AppConstants.TENURE_OF_JOB, tenureOfJob);
        dataVariables.put("specifyDutiesType", specifyDutiesType);
        dataVariables.put("specifyDutiesTypeOthers", specifyDutiesTypeOthers);
        logger.info("industry type : {}, reflexive question : {}", dataVariables.get(AppConstants.INDUSTRY_TYPE), dataVariables.get(AppConstants.DEFENCE_REFLEXIVE_1));
    }

    private void setDataForIndustryReflexive(PartyDetails partyDetails, String industryType, Map<String, Object> dataVariables) {

        String defenceReflexive1 = "";
        String defenceReflexive2 = "";
        String divingReflexive1 = "";
        String divingReflexive2 = "";
        String miningReflexive1 = "";
        String miningReflexive2 = "";
        String airforceReflexive1 = "";
        String airforceReflexive2 = "";
        String oilReflexive1 = "";

        IndustryInfo industryInfo = partyDetails.getIndustryDetails().getIndustryInfo();
        try {
            switch (industryType) {
                case AppConstants.DEFENCE:
                    defenceReflexive1 = industryInfo.isPostedOnDefenceLocation() ? AppConstants.YES : AppConstants.NO;
                    defenceReflexive2 = Utility.nullSafe(industryInfo.getNatureOfRole());
                    break;
                case AppConstants.DIVING:
                    divingReflexive1 = industryInfo.isProfessionalDiver() ? AppConstants.YES : AppConstants.NO;
                    divingReflexive2 = Utility.nullSafe(industryInfo.getDiveLocation());
                    break;
                case AppConstants.MINING:
                    miningReflexive1 = industryInfo.isWorkingInsideMine() ? AppConstants.YES : AppConstants.NO;
                    miningReflexive2 = industryInfo.isAnyIllnessRelatedToOccupation() ? AppConstants.YES : AppConstants.NO;
                    break;
                case AppConstants.AIR_FORCE:
                    airforceReflexive1 = industryInfo.isFlying() ? AppConstants.YES : AppConstants.NO;
                    airforceReflexive2 = Utility.nullSafe(industryInfo.getTypeOfAirCraft());
                    break;
                case AppConstants.OIL:
                    oilReflexive1 = industryInfo.isBasedAtOffshore() ? AppConstants.YES : AppConstants.NO;
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            logger.error("Error setDataForIndustry : ", ex);
        }

        dataVariables.put(AppConstants.DEFENCE_REFLEXIVE_1, defenceReflexive1);
        dataVariables.put(AppConstants.DEFENCE_REFLEXIVE_2, defenceReflexive2);
        dataVariables.put(AppConstants.AVIATION_REFLEXIVE_1, airforceReflexive1);
        dataVariables.put(AppConstants.AVIATION_REFLEXIVE_2, airforceReflexive2);
        dataVariables.put(AppConstants.MINING_REFLEXIVE_1, miningReflexive1);
        dataVariables.put(AppConstants.MINING_REFLEXIVE_2, miningReflexive2);
        dataVariables.put(AppConstants.DIVING_REFLEXIVE_1, divingReflexive1);
        dataVariables.put(AppConstants.DIVING_REFLEXIVE_2, divingReflexive2);
        dataVariables.put(AppConstants.OIL_REFLEXIVE_1, oilReflexive1);
    }

    private void setDataForPEP(Map<String, Object> dataVariables, ProposalDetails proposalDetails,
        String partyType) {
        boolean politicallyExposed = false;
        String pepPerson = "";
        String specifyFamilyMembers = "";
        String politicalExperience = "";
        String partyAffilations = "";
        String pepPortfolioHandled = "";
        String roleInParty = "";
        String roleInPartyOthers = "";
        String partyInPower = "";
        String pepPostedInForeignOffice = "";
        String pepPostedForeignOfficeDetails = "";
        String pepIncomeSources = "";
        String pepEverConvicted = "";
        String pepConvictedDetails = "";
        boolean isLIPep = false;
        boolean isFamilyPep = false;
        boolean isAggregator = proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(CHANNEL_AGGREGATOR);

        EmploymentDetails employmentDetails = proposalDetails.getEmploymentDetails();
        PEPDetails pepDetails = null;
        if (Objects.nonNull(employmentDetails) && "LifeInsured".equalsIgnoreCase(partyType)) {
            pepDetails = employmentDetails.getPepDetails();
        }
        if (!CollectionUtils.isEmpty(proposalDetails.getSecondaryLifeStyleDetails()) && "Proposer".equalsIgnoreCase(partyType)) {
            pepDetails =
                !CollectionUtils.isEmpty(proposalDetails.getSecondaryPEPDetails()) ? proposalDetails
                    .getSecondaryPEPDetails().get(0) : null;
        }
        if (Objects.nonNull(employmentDetails) && Objects.nonNull(pepDetails)) {
            politicallyExposed = setPoliticallyExposed(isAggregator, employmentDetails);
                isLIPep = pepDetails.isLIPEP();
                isFamilyPep = pepDetails.isFamilyMemberPEP();
                specifyFamilyMembers = Utility.nullSafe(pepDetails.getSpecifyFamilyMembers());
                politicalExperience = Utility.nullSafe(pepDetails.getPoliticalExperience());
                partyAffilations = Utility.nullSafe(pepDetails.getAffiliationsToPoliticalparty());
                pepPortfolioHandled = Utility.nullSafe(pepDetails.getPortfolioHandled());
                partyInPower = Utility.nullSafe(pepDetails.getPartyInPower());
                pepPostedInForeignOffice = Utility.nullSafe(pepDetails.getPepEverPostedInForeignOffice());
                pepPostedForeignOfficeDetails = Utility.nullSafe(pepDetails.getForeignOfficeDetails());
                pepIncomeSources = Utility.nullSafe(pepDetails.getIncomeSources());
                pepEverConvicted = Utility.nullSafe(pepDetails.getPepConvicted());
                pepConvictedDetails = Utility.nullSafe(pepDetails.getConvictionDetails());
                roleInParty = Utility.nullSafe(pepDetails.getRoleInPoliticalParty());
                roleInPartyOthers = Utility.nullSafe(pepDetails.getRoleOthers());
        }
        dataVariables.put(AppConstants.POLITICALLY_EXPOSED, politicallyExposed);
        dataVariables.put(AppConstants.PEP_PERSON, pepPerson);
        dataVariables.put(AppConstants.IS_LIFE_INSURED_PEP, isLIPep);
        dataVariables.put(AppConstants.IS_FAMILY_MEMBER_PEP, isFamilyPep);
        dataVariables.put(AppConstants.SPECIFY_FAMILY_MEMBERS, specifyFamilyMembers);
        dataVariables.put(AppConstants.POLITICAL_EXPERIENCE, politicalExperience);
        dataVariables.put(AppConstants.PARTY_AFFILATIONS, partyAffilations);
        dataVariables.put(AppConstants.PEP_PORTFOLIO_HANDLED, pepPortfolioHandled);
        dataVariables.put(AppConstants.ROLE_IN_PARTY, Utility.convertToPartyRole(roleInParty));
        dataVariables.put(AppConstants.ROLE_IN_PARTY_OTHERS,roleInPartyOthers);
        dataVariables.put(AppConstants.PARTY_IN_POWER, Utility.convertToYesOrNo(partyInPower));
        dataVariables.put(AppConstants.PEP_POSTED_IN_FOREIGN_OFFICE, Utility.convertToYesOrNo(pepPostedInForeignOffice));
        dataVariables.put(AppConstants.PEP_POSTED_FOREIGN_OFFICE_DETAILS, pepPostedForeignOfficeDetails);
        dataVariables.put(AppConstants.PEP_INCOME_SOURCES, pepIncomeSources);
        dataVariables.put(AppConstants.PEP_EVER_CONVICTED, Utility.convertToYesOrNo(pepEverConvicted));
        dataVariables.put(AppConstants.PEP_CONVICTED_DETAILS, pepConvictedDetails);
    }

    private boolean setPoliticallyExposed(boolean isAggregator,
        EmploymentDetails employmentDetails) {
        boolean politicallyExposed;
        //we have to pick politicalexposed  from isLIpep in case of aggregator
        if (isAggregator) {
            politicallyExposed = employmentDetails.getPepDetails().isLIPEP() || employmentDetails.getPepDetails().isFamilyMemberPEP();
        } else {
            politicallyExposed = employmentDetails.isPoliticallyExposed();
        }
        return politicallyExposed;
    }

    private void setDataForMedicationQuestion(Map<String, Object> dataVariables,
        ProposalDetails proposalDetails,LifeStyleDetails lifeStyleDetails) {
        String channelName = proposalDetails.getChannelDetails().getChannel();
        String neverBeenDiagnosed = "";
        String isNewFamilyHisApplicable = "";
        String familyDiagnosedWithDiseases60 = "";
        String consumptionsDetails = "";
        String newDiabeticQuestion="";
        String newHypertensionQuestion="";
        String newRespiratoryQuestion="";
        String isHivCancerHistoryApplicable = "";
        String  hivCancerTumorHistory = "";
        List<FamilyDiagnosedWithDiseasesDetail> familyDiagnosedWithDiseasesDetails = Collections.emptyList();
        MedicalQuestions medicalQuestions = new MedicalQuestions();
        NeoAggregatorMedicalQuestions neoAggregatorMedicalQuestions = new NeoAggregatorMedicalQuestions();

        if (Objects.nonNull(lifeStyleDetails)) {
            neverBeenDiagnosed = Objects.nonNull(lifeStyleDetails.getHealth())
                    ? Utility.nullSafe(lifeStyleDetails.getHealth().getNeverBeenDiagnosedOrTreated()) : AppConstants.NO;
            newDiabeticQuestion=Objects.nonNull(lifeStyleDetails.getNewDiabeticQuestion()) ? Utility.nullSafe(lifeStyleDetails.getNewDiabeticQuestion()) : NO;
            newRespiratoryQuestion=Objects.nonNull(lifeStyleDetails.getNewRespiratoryQuestion()) ? Utility.nullSafe(lifeStyleDetails.getNewRespiratoryQuestion()) : NO;
            newHypertensionQuestion=Objects.nonNull(lifeStyleDetails.getNewHypertensionQuestion()) ? Utility.nullSafe(lifeStyleDetails.getNewHypertensionQuestion()) : NO;
            familyDiagnosedWithDiseases60 = Objects.nonNull(lifeStyleDetails.getFamilyOrCriminalHistory())
                    ? Utility.nullSafe(lifeStyleDetails.getFamilyOrCriminalHistory().getFamilyDiagnosedWithDiseasesBefore60()) : AppConstants.NO;
            familyDiagnosedWithDiseasesDetails = getFamilyDiagnosedWithDiseasesDetails(lifeStyleDetails,
                familyDiagnosedWithDiseasesDetails);
            isNewFamilyHisApplicable = Objects.nonNull(lifeStyleDetails.getFamilyOrCriminalHistory())
                    ? Utility.nullSafe(lifeStyleDetails.getFamilyOrCriminalHistory().getIsNewFamilyHisApplicable()) : AppConstants.NO;
            isHivCancerHistoryApplicable = Utility.convertToYesOrNo(Objects.nonNull(lifeStyleDetails.getIsHivCancerHistoryApplicable()) ? Utility.nullSafe(lifeStyleDetails.getIsHivCancerHistoryApplicable()) : NO);
            hivCancerTumorHistory = Utility.convertToYesOrNo(Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getBloodAndCellular().getHivCancerTumorHistory()) ? Utility.nullSafe(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getBloodAndCellular().getHivCancerTumorHistory()) : NO);
            Map<Predicate<LifeStyleDetails>, Consumer<LifeStyleDetails>> conditionActionMap = new LinkedHashMap<>();
            conditionActionMap.put(
                    lsd -> Objects.nonNull(lsd.getHealth().getDiagnosedOrTreatedDetails().getHormonal().getHighBloodSugarAndDiabetesDetails()) && AppConstants.NEO_YES.equals(lsd.getNewDiabeticQuestion()),
                    lsd -> setDaibeticQuestionnaireDetails(dataVariables, lsd)
            );
            conditionActionMap.put(
                    lsd -> Objects.nonNull(lsd.getHealth().getDiagnosedOrTreatedDetails().getCardio().getHighBloodPressureDetails()) && AppConstants.NEO_YES.equals(lsd.getNewHypertensionQuestion()),
                    lsd -> setHyperTensionQuestionnaireDetails(dataVariables, lsd)
            );
            conditionActionMap.put(
                    lsd -> Objects.nonNull(lsd.getHealth().getDiagnosedOrTreatedDetails().getRespiratory().getAsthmaDetails()) && AppConstants.NEO_YES.equals(lsd.getNewRespiratoryQuestion()),
                    lsd -> setRespiratoryQuestionnaireDetails(dataVariables, lsd)
            );
            conditionActionMap.entrySet().stream()
                    .filter(entry -> entry.getKey().test(lifeStyleDetails))
                    .forEach(entry -> entry.getValue().accept(lifeStyleDetails));

            if (Objects.nonNull(lifeStyleDetails.getHealth()) && Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails())) {
                DiagnosedOrTreatedDetails diagnosedOrTreatedDetails = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails();
                medicalDetailsMapper.setMedicalQuestionsAnswer(medicalQuestions, diagnosedOrTreatedDetails, channelName);

                setQuestion1Choices(medicalQuestions, diagnosedOrTreatedDetails.getHormonal());
                setQuestion2And3Choices(medicalQuestions, diagnosedOrTreatedDetails.getCardio());
                setQuestion4Choices(medicalQuestions, diagnosedOrTreatedDetails.getRespiratory());
                setQuestion5Choices(medicalQuestions, diagnosedOrTreatedDetails.getDigestiveAndRegulatory(), proposalDetails, dataVariables);
                setQuestion6Choices(medicalQuestions, diagnosedOrTreatedDetails.getBloodAndCellular(),proposalDetails);
                setQuestion7Choices(medicalQuestions, diagnosedOrTreatedDetails.getKidneyDisorder(), proposalDetails, dataVariables);
                setQuestion8Choices(medicalQuestions, diagnosedOrTreatedDetails.getMentalAndPsychiatric());
                setQuestion9Choices(medicalQuestions, diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular());
                setQuestion10Choices(medicalQuestions, diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork());
                setQuestion11Choices(medicalQuestions, diagnosedOrTreatedDetails.getEverAdvisedForSurgeryEcg());
                setQuestion12Choices(medicalQuestions, diagnosedOrTreatedDetails.getExternalInternalAnomaly());
                setQuestion13Choices(medicalQuestions, diagnosedOrTreatedDetails.getGeneticTesting());
                consumptionsDetails = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                        diagnosedOrTreatedDetails.getSpecifyHabit().getSpecifyConsDetails() : AppConstants.BLANK;
                setTobaccoAlcholQuestion(lifeStyleDetails, diagnosedOrTreatedDetails, dataVariables, proposalDetails);
                medicalDetailsMapper.setAggregatorMedicalQuestion(neoAggregatorMedicalQuestions,
                    diagnosedOrTreatedDetails);
                setSwissReTobaccoAlcoholQuestion(diagnosedOrTreatedDetails, dataVariables, channelName);
            }

            setDataForTobaccoConsumed(dataVariables, lifeStyleDetails.getHabit());
            setDataForAlcoholConsumed(dataVariables, lifeStyleDetails.getHabit());
            setDataForDrugsConsumed(dataVariables, lifeStyleDetails.getHabit());

        }
        dataVariables.put(AppConstants.NEVER_BEEN_DIAGNOSED, Utility.convertToYesOrNo(neverBeenDiagnosed));
        dataVariables.put(NEW_DIABETIC_QUESTION, Utility.convertToYesOrNo(newDiabeticQuestion));
        dataVariables.put(NEW_HYPERTENSION_QUESTION, Utility.convertToYesOrNo(newHypertensionQuestion));
        dataVariables.put(NEW_RESPIRATORY_QUESTION, Utility.convertToYesOrNo(newRespiratoryQuestion));
        dataVariables.put(AppConstants.CONSUMPTIONS_DETAILS, consumptionsDetails);
        dataVariables.put(AppConstants.MEDICAL_QUESTIONS, medicalQuestions);
        dataVariables.put(AppConstants.AGGREGATOR_MEDICAL_QUESTIONS, neoAggregatorMedicalQuestions);
        dataVariables.put(AppConstants.FAMILY_DIAGNOSED_WITH_DISEASES, Utility.convertToYesOrNo(familyDiagnosedWithDiseases60));
        dataVariables.put(AppConstants.FAMILY_DIAGNOSE_WITH_DISEASE_DETAILS, familyDiagnosedWithDiseasesDetails);
        dataVariables.put(AppConstants.ISNEWFAMILYHISAPPLICABLE, Utility.convertToYesOrNo(isNewFamilyHisApplicable));
        dataVariables.put(AppConstants.HIV_CANCER_HISTORY, isHivCancerHistoryApplicable);
        dataVariables.put(AppConstants.HIV_CANCER_TUMOR_HISTORY, hivCancerTumorHistory);
    }

    private List<FamilyDiagnosedWithDiseasesDetail> getFamilyDiagnosedWithDiseasesDetails(
        LifeStyleDetails lifeStyleDetails,
        List<FamilyDiagnosedWithDiseasesDetail> familyDiagnosedWithDiseasesDetails) {
        if (!CollectionUtils.isEmpty(lifeStyleDetails.getFamilyOrCriminalHistory()
            .getFamilyDiagnosedWithDiseasesDetails())) {
            familyDiagnosedWithDiseasesDetails = lifeStyleDetails.getFamilyOrCriminalHistory()
                .getFamilyDiagnosedWithDiseasesDetails();
            for (FamilyDiagnosedWithDiseasesDetail familyDiagnosedWithDiseasesDetail:familyDiagnosedWithDiseasesDetails) {
                if(Objects.nonNull(familyDiagnosedWithDiseasesDetail.getOthersMedicalProblem())
                        && Strings.isNotBlank(familyDiagnosedWithDiseasesDetail.getOthersMedicalProblem())){
                    familyDiagnosedWithDiseasesDetail.setMedicalProblem(familyDiagnosedWithDiseasesDetail.getOthersMedicalProblem());
                }
            }

        }
        return familyDiagnosedWithDiseasesDetails;
    }

    private void setDaibeticQuestionnaireDetails(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails) {
        Optional.ofNullable(lifeStyleDetails)
                .map(LifeStyleDetails::getHealth)
                .map(Health::getDiagnosedOrTreatedDetails)
                .map(DiagnosedOrTreatedDetails::getHormonal)
                .map(Hormonal::getHighBloodSugarAndDiabetesDetails)
                .ifPresent(details -> {
                    dataVariables.put("typeOfDiabetes", Optional.ofNullable(details.getTypeOfDiabetes()).orElse(""));
                    dataVariables.put("diabeticComplications", Optional.ofNullable(details.getDiabeticComplications()).map(Object::toString).orElse(""));
                    dataVariables.put("diabetesDiagnosedDate", Optional.ofNullable(details.getDiabetesDiagnosedDate()).orElse(""));
                });
    }

    private void setHyperTensionQuestionnaireDetails(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails) {
        Optional.ofNullable(lifeStyleDetails)
                .map(LifeStyleDetails::getHealth)
                .map(Health::getDiagnosedOrTreatedDetails)
                .map(DiagnosedOrTreatedDetails::getCardio)
                .map(Cardio::getHighBloodPressureDetails)
                .ifPresent(details -> {
                    dataVariables.put("diagnosedDate", Optional.ofNullable(details.getDiagnosedDate()).orElse(""));
                    dataVariables.put("areYouOnTreatment", Utility.convertToYesOrNo(Optional.ofNullable(String.valueOf(details.getAreYouOnTreatment())).orElse("")));
                    dataVariables.put("medicalTestApplicable", Utility.convertToYesOrNo(Optional.ofNullable(details.getMedicalTestApplicable()).orElse("")));
                    dataVariables.put("medicalTestDocConsultationDate", Optional.ofNullable(details.getMedicalTestDocConsultationDate()).orElse(""));
                    dataVariables.put("highBloodPressureComplications", Optional.ofNullable(details.getHighBloodPressureComplications()).filter(highBloodPressureComplications -> !highBloodPressureComplications.isEmpty()).orElse(null));
                    dataVariables.put("medicalTestDetails", Optional.ofNullable(details.getMedicalTestDetails()).filter(medicalTestDetails -> !medicalTestDetails.isEmpty()).orElse(null));
                });
    }

    private void setRespiratoryQuestionnaireDetails(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails) {
        Optional.ofNullable(lifeStyleDetails)
                .map(LifeStyleDetails::getHealth)
                .map(Health::getDiagnosedOrTreatedDetails)
                .map(DiagnosedOrTreatedDetails::getRespiratory)
                .map(Respiratory::getAsthmaDetails)
                .ifPresent(details -> {
                    dataVariables.put("frequencyAndSeverity", Optional.ofNullable(details.getFrequencyAndSeverity()).orElse(""));
                    dataVariables.put("typeOfTreatment", Optional.ofNullable(details.getTypeOfTreatment()).map(Object::toString).orElse(""));
                    dataVariables.put("everAdmittedToHospital", Utility.convertToYesOrNo(Optional.ofNullable(details.getEverAdmittedToHospital()).orElse("")));
                    dataVariables.put("everSmokedCigOrTobacco", Utility.convertToYesOrNo(Optional.ofNullable(details.getEverSmokedCigOrTobacco()).orElse("")));
                });
    }

    private void setTobaccoAlcholQuestion(LifeStyleDetails lifeStyleDetails, DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,
                         Map<String, Object> dataVariables, ProposalDetails proposalDetails) {
        String isTobaccoAlcoholDrugsConsumed = "";
        isTobaccoAlcoholDrugsConsumed = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit().getEverConsumeType()) : AppConstants.NO;
        if (proposalDetails.getChannelDetails().getChannel()
                .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
            Optional<Habit> habit = Optional.ofNullable(lifeStyleDetails.getHabit());
            if (habit.isPresent() && (!CollectionUtils.isEmpty(habit.get().getDrugs().getConsumptionDetails())
                    || !CollectionUtils
                    .isEmpty(habit.get().getLiquor().getConsumptionDetails())
                    || !CollectionUtils
                    .isEmpty(habit.get().getTobaccoNicotine().getConsumptionDetails()))) {
                isTobaccoAlcoholDrugsConsumed = YES;
            }
        }
        dataVariables.put(AppConstants.IS_TOBACCO_ALCOHOL_DRUGS_CONSUMED, isTobaccoAlcoholDrugsConsumed);
    }

    private void setSwissReTobaccoAlcoholQuestion(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, Map<String, Object> dataVariables, String channelName) {
        String swissReTobaccoAlchoholDrugsConsumed = "";
        String consumeTobacco = "";
        String cigaretteQuantity = "";
        String cigaretteFrequency = "";
        String gutkaPanQuantity = "";
        String gutkaPanFrequency = "";
        String specifyConsDetails = "";


        if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelName) || AppConstants.CHANNEL_NEO.equalsIgnoreCase(channelName)) {
            swissReTobaccoAlchoholDrugsConsumed = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit().getEverConsumeType()) : AppConstants.NO;
            consumeTobacco = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit().getConsumeTobaccoCurrentlyOrOcc()) : AppConstants.NO;
            cigaretteQuantity = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getCigaretteQuantity()) : AppConstants.NO;
            cigaretteFrequency = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getCigaretteFrequency()) : AppConstants.NO;
            gutkaPanQuantity = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getSachetGutkaPanQuantity()) : AppConstants.NO;
            gutkaPanFrequency = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getSachetGutkaPanFrequency()) : AppConstants.NO;
            specifyConsDetails = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getSpecifyConsDetails()) : AppConstants.NO;


            settingWineBeerAndHardLiquor(diagnosedOrTreatedDetails, dataVariables);

        }
        dataVariables.put("swissReTobaccoAlchoholDrugsConsumed", swissReTobaccoAlchoholDrugsConsumed);
        dataVariables.put("consumeTobacco", consumeTobacco);
        dataVariables.put("cigaretteQuantity", cigaretteQuantity);
        dataVariables.put("cigaretteFrequency", cigaretteFrequency);
        dataVariables.put("gutkaPanQuantity", gutkaPanQuantity);
        dataVariables.put("gutkaPanFrequency", gutkaPanFrequency);
        dataVariables.put("specifyConsDetails", specifyConsDetails);

    }

    private void settingWineBeerAndHardLiquor(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, Map<String, Object> dataVariables) {

        String wineQuantity = "";
        String wineFrequency = "";
        String beerQuantity = "";
        String beerFrequency = "";
        String hardLiquorQuantity = "";
        String hardLiquorFrequency = "";
        String consumedAlcohol = "";

        consumedAlcohol = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit().getEverConsumedAlcohol()) : AppConstants.NO;
        wineQuantity = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getWineQuantity()) : AppConstants.NO;
        wineFrequency = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getWineFrequency()) : AppConstants.NO;
        beerQuantity = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getBeerQuantity()) : AppConstants.NO;
        beerFrequency = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getBeerFrequency()) : AppConstants.NO;
        hardLiquorQuantity = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getHardLiquorQuantity()) : AppConstants.NO;
        hardLiquorFrequency = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getHardLiquorFrequency()) : AppConstants.NO;

        dataVariables.put("consumedAlcohol", consumedAlcohol);
        dataVariables.put("wineQuantity", wineQuantity);
        dataVariables.put("wineFrequency", wineFrequency);
        dataVariables.put("beerQuantity", beerQuantity);
        dataVariables.put("beerFrequency", beerFrequency);
        dataVariables.put("hardLiquorQuantity", hardLiquorQuantity);
        dataVariables.put("hardLiquorFrequency", hardLiquorFrequency);
    }

    private void setQuestion13Choices(MedicalQuestions medicalQuestions, EverHadGeneticTesting geneticTesting) {
        if (Objects.nonNull(geneticTesting)) {
            medicalQuestions.setQuestion10Answer(Utility.convertToYesOrNo(geneticTesting.getEverHadGenetictesting()));
            medicalQuestions.setQuestion10AChoice(Utility.nullSafe(geneticTesting.getSpecifyGeneticDetails()));
        }
    }

    private void setQuestion12Choices(MedicalQuestions medicalQuestions, ExternalInternalAnomaly externalInternalAnomaly) {
        if (Objects.nonNull(externalInternalAnomaly)) {
            medicalQuestions.setQuestion16Answer(Utility.convertToYesOrNo(externalInternalAnomaly.getIsExternalInternalAnomaly()));
            medicalQuestions.setQuestion16AChoice(Utility.nullSafe(externalInternalAnomaly.getSpecifyInternalDetails()));
        }
    }

    private void setQuestion1Choices(MedicalQuestions medicalQuestions, Hormonal hormonal) {
        if (Objects.nonNull(hormonal) && Objects.nonNull(hormonal.getHighBloodSugarAndDiabetesDetails())) {
            medicalQuestions.setQuestion2AChoice(Utility.convertToYesOrNo(hormonal.getHighBloodSugarAndDiabetesDetails().getManagingDiabThrough()));
            medicalQuestions.setQuestion2BChoice(Utility.nullSafe(hormonal.getHighBloodSugarAndDiabetesDetails().getDiabPeriod()));
            medicalQuestions.setQuestion2CChoice(Utility.nullSafe(hormonal.getHighBloodSugarAndDiabetesDetails().getMedicationDetails()));
            medicalQuestions.setQuestion2DChoice(Utility.nullSafe(hormonal.getSpecifyDetails()));
        }
    }

    private void setQuestion2And3Choices(MedicalQuestions medicalQuestions, Cardio cardio) {
        if (Objects.nonNull(cardio) && Objects.nonNull(cardio.getHighBloodPressureDetails())) {
            medicalQuestions.setQuestion1AChoice(Utility.convertToYesOrNo(cardio.getChestPain()));
            medicalQuestions.setQuestion1BChoice(Utility.nullSafe(cardio.getSpecifyDetails()));
            medicalQuestions.setQuestion3AChoice(Utility.convertToYesOrNo(cardio.getHighBloodPressureDetails().getWhenWasItDiagnosed()));
            medicalQuestions.setQuestion3BChoice(Utility.convertToYesOrNo(cardio.getHighBloodPressureDetails().getMedicationDetails()));
            medicalQuestions.setQuestion3CChoice(Utility.convertToYesOrNo(cardio.getHighBloodPressureDetails().getFollowupAfterLastConsultation()));
            medicalQuestions.setQuestion3DChoice(Utility.nullSafe(cardio.getHighBloodPressureDetails().getLastReading()));
        }
    }

    private void setQuestion4Choices(MedicalQuestions medicalQuestions, Respiratory respiratory) {
        if (Objects.nonNull(respiratory) && Objects.nonNull(respiratory.getAsthmaDetails())) {
            medicalQuestions.setQuestion4AChoice(Utility.convertToYesOrNo(respiratory.getAsthmaDetails().getWhenWasItDiagnosed()));
            medicalQuestions.setQuestion4BChoice(Utility.convertToYesOrNo(respiratory.getAsthmaDetails().getHaveYouTakenSteriods()));
            medicalQuestions.setQuestion4CChoice(Utility.convertToYesOrNo(respiratory.getAsthmaDetails().getMedicationDetails()));
            medicalQuestions.setQuestion4DChoice(Utility.nullSafe(respiratory.getAsthmaDetails().getSymptomsDetails()));
        }
    }

    private void setQuestion5Choices(MedicalQuestions medicalQuestions, DigestiveAndRegulatory digestiveAndRegulatory,
                                     ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        if (Objects.nonNull(digestiveAndRegulatory)) {
            String productId= Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
                     ? proposalDetails.getProductDetails().get(0).getProductInfo().getProductId() : BLANK;
            String productName = FamilyType.getHtmlFormByProductId(productId);
            medicalQuestions.setQuestion6AChoice(Utility.convertToYesOrNo(digestiveAndRegulatory.getIsJaundHistory()));
            medicalQuestions.setQuestion6BChoice(Utility.convertToYesOrNo(digestiveAndRegulatory.getStomachOrintestinalDisorder()));
            medicalQuestions.setQuestion6CChoice(Utility.convertToYesOrNo(digestiveAndRegulatory.getAnyKidneyDisorders()));
            medicalQuestions.setQuestion6DChoice(Utility.convertToYesOrNo(digestiveAndRegulatory.getIsStoneHistory()));
            medicalQuestions.setQuestion6EChoice(Utility.nullSafe(digestiveAndRegulatory.getSpecifyDetails()));

            if ((STEP_CIS.equalsIgnoreCase(productName) || SSP_CIS.equalsIgnoreCase(productName) || NEO_STPP_PRODUCT_TYPE.equalsIgnoreCase(productName))
                && StringUtils.hasLength(digestiveAndRegulatory.getChronicLiverDisease())) {
                dataVariables.put("chronicLiverDisease", Utility.convertToYesOrNo(digestiveAndRegulatory.getChronicLiverDisease()));
                dataVariables.put("chronicLiverDiseaseYESORNO", digestiveAndRegulatory.getChronicLiverDisease());
            } else {
                dataVariables.put("nullOrEmptyStringCLDString", NEO_YES);
            }
        }
    }

private void setQuestion6Choices(MedicalQuestions medicalQuestions, BloodAndCellular bloodAndCellular,ProposalDetails proposalDetails) {
        if (Objects.nonNull(bloodAndCellular)) {
            medicalQuestions.setQuestion5AChoice(Utility.convertToYesOrNo(bloodAndCellular.getAnyBloodDisorder()));
            medicalQuestions.setQuestion5BChoice(Utility.convertToYesOrNo(bloodAndCellular.getTumorAndMalignantGrowth()));
            medicalQuestions.setQuestion5CChoice(Utility.nullSafe(bloodAndCellular.getSpecifyDetails()));
            medicalQuestions.setQuestion5WChoice(Utility.getPosvQuestionValueForm(proposalDetails, H6D));
        }
    }

    private void setQuestion7Choices(MedicalQuestions medicalQuestions, KidneyDisorder kidneyDisorder,
                                     ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
        if (Objects.nonNull(kidneyDisorder)) {
            String productId= Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
                    ? proposalDetails.getProductDetails().get(0).getProductInfo().getProductId() : BLANK;
            String productName = FamilyType.getHtmlFormByProductId(productId);
            medicalQuestions.setQuestion9AChoice(Utility.convertToYesOrNo(kidneyDisorder.getIsUtiHistory()));
            medicalQuestions.setQuestion9BChoice(Utility.convertToYesOrNo(kidneyDisorder.getIsKidneySurgHis()));
            medicalQuestions.setQuestion9CChoice(Utility.convertToYesOrNo(kidneyDisorder.getIsKidStoneHis()));
            medicalQuestions.setQuestion9DChoice(Utility.nullSafe(kidneyDisorder.getSpecifyKidneyDetails()));

            if ((STEP_CIS.equalsIgnoreCase(productName) || SSP_CIS.equalsIgnoreCase(productName) || NEO_STPP_PRODUCT_TYPE.equalsIgnoreCase(productName))
                && StringUtils.hasLength(kidneyDisorder.getChronicKidneyDisease())) {
                dataVariables.put("chronicKidneyDisease", Utility.convertToYesOrNo(kidneyDisorder.getChronicKidneyDisease()));
                dataVariables.put("chronicKidneyDiseaseYESORNO", kidneyDisorder.getChronicKidneyDisease().toLowerCase());
            } else {
                dataVariables.put("nullOrEmptyCKDString", NEO_YES);
            }
        }
    }

    private void setQuestion8Choices(MedicalQuestions medicalQuestions, MentalAndPsychiatric mentalAndPsychiatric) {
        if (Objects.nonNull(mentalAndPsychiatric)) {
            medicalQuestions.setQuestion7AChoice(Utility.nullSafe(mentalAndPsychiatric.getSpecifyDetails()));
        }
    }

    private void setQuestion9Choices(MedicalQuestions medicalQuestions, NeuralOrSkeletalOrMuscular neuralOrSkeletalOrMuscular) {
        if (Objects.nonNull(neuralOrSkeletalOrMuscular)) {
            medicalQuestions.setQuestion12AChoice(Utility.convertToYesOrNo(neuralOrSkeletalOrMuscular.getAnyDisorderOfSpine()));
            medicalQuestions.setQuestion12BChoice(Utility.convertToYesOrNo(neuralOrSkeletalOrMuscular.getIsBackPainSprainExcercise()));
            medicalQuestions.setQuestion12CChoice(Utility.convertToYesOrNo(neuralOrSkeletalOrMuscular.getAnyDisorderOfMuscle()));
            medicalQuestions.setQuestion12DChoice(Utility.convertToYesOrNo(neuralOrSkeletalOrMuscular.getAnyDisorderOfENT()));
            medicalQuestions.setQuestion12EChoice(Utility.convertToYesOrNo(neuralOrSkeletalOrMuscular.getIsFractureHistory()));
            medicalQuestions.setQuestion12FChoice(Utility.nullSafe(neuralOrSkeletalOrMuscular.getSpecifyDetails()));
        }
    }

    private void setQuestion10Choices(MedicalQuestions medicalQuestions, HospitalizationAndAbsenceFromWork hospitalizationAndAbsenceFromWork) {
        if (Objects.nonNull(hospitalizationAndAbsenceFromWork)) {
            medicalQuestions.setQuestion15Answer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getEverBeenhospitalized()));
            medicalQuestions.setQuestion15AAnswer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getIsFeverHosptalization()));
            medicalQuestions.setQuestion15BAnswer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getIsFoodPoisonHospt()));
            medicalQuestions.setQuestion15CAnswer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getIsAccidentHospt()));
            medicalQuestions.setQuestion15DAnswer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getIsStonePileHospt()));
            medicalQuestions.setQuestion15EAnswer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getIsTyphDengHospt()));
            medicalQuestions.setQuestion15FAnswer(Utility.convertToYesOrNo(hospitalizationAndAbsenceFromWork.getIsSinusColdHospt()));
            medicalQuestions.setQuestion15GAnswer(Utility.nullSafe(hospitalizationAndAbsenceFromWork.getEverBeenhospitalizedDetails()));
        }
    }

    private void setQuestion11Choices(MedicalQuestions medicalQuestions, EverAdvisedForSurgeryEcg everAdvisedForSurgeryEcg) {
        if (Objects.nonNull(everAdvisedForSurgeryEcg)) {
            medicalQuestions.setQuestion13AAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsAccidentSurg()));
            medicalQuestions.setQuestion13BAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsAppendHist()));
            medicalQuestions.setQuestion13CAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsPileSurgHist()));
            medicalQuestions.setQuestion13DAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsGallStoneHist()));
            medicalQuestions.setQuestion13EAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsInsertnSurgery()));
            medicalQuestions.setQuestion13FAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsLasikCorrection()));
            medicalQuestions.setQuestion13GAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsCataSurgHist()));
            medicalQuestions.setQuestion13HAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsDnsSurgery()));
            medicalQuestions.setQuestion13IAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsMriScanHist()));
            medicalQuestions.setQuestion13JAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsAnnualTestDone()));
            medicalQuestions.setQuestion13KAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsFluBloodDone()));
            medicalQuestions.setQuestion13LAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsPregBloodDone()));
            medicalQuestions.setQuestion13MAnswer(Utility.convertToYesOrNo(everAdvisedForSurgeryEcg.getIsBloodDonatHist()));
            medicalQuestions.setQuestion13NAnswer(Utility.nullSafe(everAdvisedForSurgeryEcg.getAnyOtherSurgeryDetails()));
        }
    }

    private void setDataForDrugsConsumed(Map<String, Object> dataVariables, Habit habit) {

        String drugsConsumed = "";
        List<ConsumptionDetail> drugsConsumptionDetailsList = new ArrayList<>();

        if (Objects.nonNull(habit) && Objects.nonNull(habit.getDrugs())) {
            drugsConsumed = Utility.nullSafe(habit.getDrugs().getConsumeSubstance());

            if (Objects.nonNull(habit.getDrugs().getConsumptionDetails()) && !habit.getDrugs().getConsumptionDetails().isEmpty()) {
                habit.getDrugs().getConsumptionDetails().forEach(sourceConsumptionDetail -> {
                    ConsumptionDetail targetConsumptionDetail = new ConsumptionDetail();
                    targetConsumptionDetail.setType(convertToDrugType(sourceConsumptionDetail.getType()));
                    targetConsumptionDetail.setFrequency(convertToFrequencyType(sourceConsumptionDetail.getFrequency()));
                    targetConsumptionDetail.setQuantity(Utility.nullSafe(sourceConsumptionDetail.getQuantity()));
                    targetConsumptionDetail.setNumberOfYears(Utility.nullSafe(sourceConsumptionDetail.getNumberOfYears()));
                    drugsConsumptionDetailsList.add(targetConsumptionDetail);
                });
            }
        }

        dataVariables.put(AppConstants.DRUGS_CONSUMED, Utility.convertToYesOrNo(drugsConsumed));
        dataVariables.put(AppConstants.DRUGS_CONSUMED_DETAILS_LIST, drugsConsumptionDetailsList);
    }

    public void setDataForAlcoholConsumed(Map<String, Object> dataVariables, Habit habit) {

        String alcoholConsumed = "";
        List<ConsumptionDetail> alcoholConsumptionDetailsList = new ArrayList<>();

        if (Objects.nonNull(habit) && Objects.nonNull(habit.getLiquor())) {
            alcoholConsumed = Utility.nullSafe(habit.getLiquor().getConsumeSubstance());

            if (Objects.nonNull(habit.getLiquor().getConsumptionDetails()) && !habit.getLiquor().getConsumptionDetails().isEmpty()) {
                habit.getLiquor().getConsumptionDetails().forEach(sourceConsumptionDetail -> {
                    ConsumptionDetail targetConsumptionDetail = new ConsumptionDetail();
                    targetConsumptionDetail.setType(convertToAlcoholType(sourceConsumptionDetail.getType()));
                    targetConsumptionDetail.setFrequency(convertToFrequencyType(sourceConsumptionDetail.getFrequency()));
                    targetConsumptionDetail.setQuantity(Utility.nullSafe(sourceConsumptionDetail.getQuantity()));
                    targetConsumptionDetail.setNumberOfYears(Utility.nullSafe(sourceConsumptionDetail.getNumberOfYears()));
                    alcoholConsumptionDetailsList.add(targetConsumptionDetail);
                });
            }
        }

        dataVariables.put(AppConstants.ALCOHOL_CONSUMED, Utility.convertToYesOrNo(alcoholConsumed));
        dataVariables.put(AppConstants.ALCOHOL_CONSUMED_DETAILS_LIST, alcoholConsumptionDetailsList);
    }

     public void setDataForTobaccoConsumed(Map<String, Object> dataVariables, Habit habit) {

        String tobaccoConsumed = "";
        List<ConsumptionDetail> tobaccoConsumptionDetailsList = new ArrayList<>();

        if (Objects.nonNull(habit) && Objects.nonNull(habit.getTobaccoNicotine())) {
            tobaccoConsumed = Utility.nullSafe(habit.getTobaccoNicotine().getConsumeSubstance());

            if (Objects.nonNull(habit.getTobaccoNicotine().getConsumptionDetails()) && !habit.getTobaccoNicotine().getConsumptionDetails().isEmpty()) {
                habit.getTobaccoNicotine().getConsumptionDetails().forEach(sourceConsumptionDetail -> {
                    ConsumptionDetail targetConsumptionDetail = new ConsumptionDetail();
                    targetConsumptionDetail.setType(convertToTobaccoType(sourceConsumptionDetail.getType()));
                    targetConsumptionDetail.setFrequency(convertToFrequencyType(sourceConsumptionDetail.getFrequency()));
                    targetConsumptionDetail.setQuantity(Utility.nullSafe(sourceConsumptionDetail.getQuantity()));
                    targetConsumptionDetail.setNumberOfYears(Utility.nullSafe(sourceConsumptionDetail.getNumberOfYears()));
                    tobaccoConsumptionDetailsList.add(targetConsumptionDetail);
                });
            }
        }
        dataVariables.put(AppConstants.TOBACCO_CONSUMED, Utility.convertToYesOrNo(tobaccoConsumed));
        dataVariables.put(AppConstants.TOBACCO_CONSUMED_DETAILS_LIST, tobaccoConsumptionDetailsList);
    }

    private void convertNullValuesToBlank(Map<String, Object> dataMap) {
        Set<String> keys = dataMap.keySet();
        for (String key : keys) {
            if (Objects.isNull(dataMap.get(key))) {
                dataMap.put(key, "");
            }
        }
    }

private String convertToAlcoholType(String type) {
        if ("01".equalsIgnoreCase(type)) {
            return "Beer";
        } else if ("02".equalsIgnoreCase(type)) {
            return "Wine";
        } else if ("03".equalsIgnoreCase(type)) {
            return "Hard Liquor";
        } else {
            return type;
        }
    }

    private String convertToDrugType(String type) {
        if ("01".equalsIgnoreCase(type)) {
            return "Cannabis";
        } else if ("02".equalsIgnoreCase(type)) {
            return "Marijuana";
        } else if ("03".equalsIgnoreCase(type)) {
            return "Ecstasy";
        } else if ("04".equalsIgnoreCase(type)) {
            return "Heroin";
        } else if ("05".equalsIgnoreCase(type)) {
            return "LSD";
        } else if ("06".equalsIgnoreCase(type)) {
            return "Amphetamines";
        } else {
            return type;
        }
    }

    private String convertToFrequencyType(String frequency) {
        if ("01".equalsIgnoreCase(frequency)) {
            return "Per Day";
        } else if ("02".equalsIgnoreCase(frequency)) {
            return "Per Week";
        } else if ("03".equalsIgnoreCase(frequency)) {
            return "Per Month";
        } else if ("04".equalsIgnoreCase(frequency)) {
            return "Occasionally";
        } else {
            return frequency;
        }
    }

    private String convertToTobaccoType(String type) {
        if ("01".equalsIgnoreCase(type)) {
            return "Cigarette";
        } else if ("02".equalsIgnoreCase(type)) {
            return "Beedis";
        } else if ("03".equalsIgnoreCase(type)) {
            return "Cigar";
        } else if ("04".equalsIgnoreCase(type)) {
            return "Gutka";
        } else if ("05".equalsIgnoreCase(type)) {
            return "Flavoured Pan Masala";
        } else if ("06".equalsIgnoreCase(type)) {
            return "Khaini";
        } else {
            return type;
        }
    }

    private boolean setPaymentDate(PaymentDetails paymentDetails) {
        if(Objects.nonNull(paymentDetails) && Objects.nonNull(paymentDetails.getReceipt()) && Objects.nonNull(paymentDetails.getReceipt().get(0).getPaymentDate()) ) {
            String paymentDate = paymentDetails.getReceipt().get(0).getPaymentDate();
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date1 = formatter.parse(paymentDate);
                Date date2 = formatter.parse(swissApplicableDate);
                return date1.after(date2);
            } catch (ParseException p) {
                logger.error("error parsing the date {}", paymentDate);
            }
        }
        return false;
    }
    public void setSwissReGeneralQuestionSSPJL(Map<String, Object> dataVariables,
                                          List<LifeStyleDetails> lifeStyleDetails) {
        String isWeightChangedSSPJL = "";
        String weightChangeReasonSSPJL = "";
        HeightAndWeight heightAndWeight = null;
        if (!CollectionUtils.isEmpty(lifeStyleDetails) && Objects.nonNull(lifeStyleDetails.get(1))) {
            heightAndWeight = lifeStyleDetails.get(1).getHeightAndWeight();
            isWeightChangedSSPJL = Utility.convertToYesOrNo(heightAndWeight.getIsWeightChanged());
            weightChangeReasonSSPJL = Utility.nullSafe(heightAndWeight.getWeightChangeReason());
        }
        dataVariables.put("isWeightChangedSSPJL", isWeightChangedSSPJL);
        dataVariables.put("weightChangeReasonSSPJL", weightChangeReasonSSPJL);
    }

}
