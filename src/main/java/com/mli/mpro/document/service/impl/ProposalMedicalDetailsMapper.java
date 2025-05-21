package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.MedicalQuestions;
import com.mli.mpro.document.models.NeoAggregatorMedicalQuestions;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.mli.mpro.productRestriction.util.AppConstants.BLANK;
import static com.mli.mpro.productRestriction.util.AppConstants.YES;

/**
 * @author manish on 16/04/20
 */
@Service
public class ProposalMedicalDetailsMapper {

    private static final Logger logger = LoggerFactory.getLogger(ProposalMedicalDetailsMapper.class);

    @Value("${swiss.applicable.date}")
    private String swissApplicableDate;

    @Autowired
    ProposalFormAnnexureMapper proposalFormAnnexureMapper;

    /**
     * This function will populate data for Medical Information Section in Proposal Form (NEO).
     * @param proposalDetails
     * @return
     */
    public Context setMedicalData(ProposalDetails proposalDetails) throws UserHandledException {
        Map<String, Object> dataVariables = new HashMap<>();
        Context context = new Context();
try {
    if (Objects.nonNull(proposalDetails)) {
        logger.info("Mapping medical information of proposal form document for transactionId {}",
                proposalDetails.getTransactionId());
        medicalAndTravel(proposalDetails.getPartyInformation().get(0), dataVariables,
                proposalDetails.getLifeStyleDetails().get(0), proposalDetails);
        if (Utility.isProductSWPJL(proposalDetails) || Utility.isApplicationIsForm2(proposalDetails)
                || Utility.isSSPJLProduct(proposalDetails)
                && Objects.nonNull(proposalDetails.getPartyInformation().get(1))) {
            Map<String, Object> dataDocumentForLI = new HashMap<>();
            medicalAndTravel(proposalDetails.getPartyInformation().get(1), dataDocumentForLI,
                    proposalDetails.getLifeStyleDetails().get(1), proposalDetails);
            setDataForMedicationQuestion(dataDocumentForLI, proposalDetails, proposalDetails.getLifeStyleDetails().get(1));
            setSwissReGeneralQuestionSSPJL(dataVariables, proposalDetails.getLifeStyleDetails());
            dataVariables.put("lIDataMap", dataDocumentForLI);
        }
            setSwissReGeneralQuestion(dataVariables, proposalDetails.getLifeStyleDetails());
            setDataForMedicationQuestion(dataVariables, proposalDetails, proposalDetails.getLifeStyleDetails().get(0));
        boolean isPaymentDate = setPaymentDate(proposalDetails.getPaymentDetails());
        dataVariables.put("isPaymentDate", isPaymentDate);

        convertNullValuesToBlank(dataVariables);
        context.setVariables(dataVariables);
    }
}
        catch(Exception e){
        logger.info("Data addition failed for Proposal medical details mapper for transactionId {} : ", proposalDetails.getTransactionId(), e);
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("Data addition failed");
        throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
        logger.info("Ending Proposal medical details Data Population for transactionId {}", proposalDetails.getTransactionId());
        return context;
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

    private void setDataForMedicationQuestion(Map<String, Object> dataVariables,
                                              ProposalDetails proposalDetails,LifeStyleDetails lifeStyleDetails) {
        String channelName = proposalDetails.getChannelDetails().getChannel();
        DiagnosedOrTreatedDetails diagnosedOrTreatedDetails = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails();
        if (Objects.nonNull(lifeStyleDetails)) {
            setSwissReTobaccoAlcoholQuestion(diagnosedOrTreatedDetails, dataVariables, channelName);
        }
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
        String consumedAlcohol = "";
        String wineQuantity = "";
        String wineFrequency = "";
        String beerQuantity = "";
        String beerFrequency = "";
        String hardLiquorQuantity = "";
        String hardLiquorFrequency = "";
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

    private void medicalAndTravel(PartyInformation partyInformation,
        Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails,
        ProposalDetails proposalDetails) {
        if (Objects.nonNull(lifeStyleDetails)) {
            String channelName = proposalDetails.getChannelDetails().getChannel();
            setDataForMedicalAndTravelQuestions(dataVariables,
                lifeStyleDetails, proposalDetails
                    .getBankJourney(),
                channelName);
        }

        if (AppConstants.YBL.equalsIgnoreCase(proposalDetails.getBankJourney())) {
            setSmokerField(dataVariables, partyInformation);
        }
    }

    private void setSmokerField(Map<String, Object> dataVariables, PartyInformation partyInformation) {
        if (Objects.nonNull(partyInformation)){
            dataVariables.put("isSmoker", Utility.convertToYesOrNo(partyInformation.getBasicDetails().getSmoker()));
        }
    }

    private void setDataForMedicalAndTravelQuestions(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails,
                                                     String bankJourney, String channelName) {
        String familyDiagnosedWithDiseases60 = "";
        String insuredHeight = "";
        String insuredWeight = "";

        if (Objects.nonNull(lifeStyleDetails)) {
            if (AppConstants.YBL.equalsIgnoreCase(bankJourney)) {
                setMedicalInformationQuestionsYBL(dataVariables, lifeStyleDetails);
            } else {
            setDataForMedicalQuestions(dataVariables, lifeStyleDetails, channelName);
            }

            familyDiagnosedWithDiseases60 = Objects.nonNull(lifeStyleDetails.getFamilyOrCriminalHistory())
                    ? Utility.convertToYesOrNo(lifeStyleDetails.getFamilyOrCriminalHistory().getFamilyDiagnosedWithDiseasesBefore60())
                    : AppConstants.NO;
            proposalFormAnnexureMapper.setProposerHeight(lifeStyleDetails, dataVariables);
            proposalFormAnnexureMapper.setProposerWeight(lifeStyleDetails, dataVariables);
        }
        dataVariables.put(AppConstants.INSURED_HEIGHT, insuredHeight);
        dataVariables.put(AppConstants.INSURED_WEIGHT, insuredWeight);
        dataVariables.put(AppConstants.FAMILY_DIAGNOSED_WITH_DISEASES,familyDiagnosedWithDiseases60);
    }

    private void setMedicalInformationQuestionsYBL(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails) {
        String everBeenHospitalized = "";
        String isUltrasound = "";
        String isMriScan = "";
        String isTobaccoAlcoholDrugsConsumed = "";

        if (Objects.nonNull(lifeStyleDetails.getHealth())
                && Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails())) {
            everBeenHospitalized = Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getHospitalizationAndAbsenceFromWork())
                    ? lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getHospitalizationAndAbsenceFromWork().getEverBeenhospitalized()
                    : AppConstants.NO;

            if (Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getEverAdvisedForSurgeryEcg())) {
                isUltrasound = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getEverAdvisedForSurgeryEcg().getIsEverAdvisedSurg();
                isMriScan = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getEverAdvisedForSurgeryEcg().getIsMriScanHist();
            }

            isTobaccoAlcoholDrugsConsumed = Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getSpecifyHabit())
                    ? lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails().getSpecifyHabit().getEverConsumeType()
                    : AppConstants.NO;
        }


        proposalFormAnnexureMapper.setDataForAlcoholConsumed(dataVariables, lifeStyleDetails.getHabit());
        proposalFormAnnexureMapper.setDataForTobaccoConsumed(dataVariables, lifeStyleDetails.getHabit());
        dataVariables.put(AppConstants.EVER_BEEN_HOSPITALIZED, Utility.convertToYesOrNo(everBeenHospitalized));
        dataVariables.put(AppConstants.IS_ULTRASOUND, Utility.convertToYesOrNo(isUltrasound));
        dataVariables.put(AppConstants.IS_MRI_SCAN, Utility.convertToYesOrNo(isMriScan));
        dataVariables.put(AppConstants.IS_TOBACCO_ALCOHOL_DRUGS_CONSUMED, Utility.convertToYesOrNo(isTobaccoAlcoholDrugsConsumed));

    }

    private void setDataForMedicalQuestions(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails,
                                            String channelName) {

        String neverBeenDiagnosedOrTreated = "";
        String questionAlcohol1Answer = "";
        String questionAlcohol2Answer = "";
        String questionTobaccoAnswer = "";
        String questionSmokingAnswer = "";
        String questionDrugsAnswer = "";
        MedicalQuestions medicalQuestions = new MedicalQuestions();

        if (Objects.nonNull(lifeStyleDetails)) {
            neverBeenDiagnosedOrTreated = Objects.nonNull(lifeStyleDetails.getHealth())
                        ? Utility.nullSafe(lifeStyleDetails.getHealth().getNeverBeenDiagnosedOrTreated()) : AppConstants.NO;

            if (Objects.nonNull(lifeStyleDetails.getHealth()) && Objects.nonNull(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails())) {
                DiagnosedOrTreatedDetails diagnosedOrTreatedDetails = lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails();

                setMedicalQuestionsAnswer(medicalQuestions, diagnosedOrTreatedDetails, channelName);

                if (Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit())) {
                    setTobaccoAlcholQuestion(lifeStyleDetails, dataVariables, channelName);
                    questionAlcohol1Answer = setQuestionAlcohol1Answer(lifeStyleDetails, channelName, diagnosedOrTreatedDetails);
                    questionAlcohol2Answer = Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getEverAdvisedToQuit());
                    questionTobaccoAnswer = setQuestionTobaccoAnswer(lifeStyleDetails, channelName, diagnosedOrTreatedDetails);
                    questionSmokingAnswer = Utility.nullSafe(diagnosedOrTreatedDetails.getSpecifyHabit().getMoreThanTwentyOrChewTen());
                    questionDrugsAnswer = setQuestionDrugsAnswer(lifeStyleDetails, channelName, diagnosedOrTreatedDetails);
                }
            }
            setLiquorTobacooBasedQuestion(dataVariables,lifeStyleDetails);
            setSwissReTobaccoAlcoholDrugAnswer(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails(), channelName, dataVariables);
        }

        medicalQuestions.setQuestionAlcohol1Answer(Utility.convertToYesOrNo(questionAlcohol1Answer));
        medicalQuestions.setQuestionAlcohol2Answer(Utility.convertToYesOrNo(questionAlcohol2Answer));
        medicalQuestions.setQuestionTobaccoAnswer(Utility.convertToYesOrNo(questionTobaccoAnswer));
        medicalQuestions.setQuestionSmokingAnswer(Utility.convertToYesOrNo(questionSmokingAnswer));
        medicalQuestions.setQuestionDrugsAnswer(Utility.convertToYesOrNo(questionDrugsAnswer));


        dataVariables.put(AppConstants.NEVER_BEEN_DIAGNOSED, Utility.convertToYesOrNo(neverBeenDiagnosedOrTreated));
        dataVariables.put(AppConstants.MEDICAL_QUESTIONS, medicalQuestions);
    }

    private void setLiquorTobacooBasedQuestion(Map<String, Object> dataVariables,LifeStyleDetails lifeStyleDetails) {
        if(Objects.nonNull(lifeStyleDetails.getHabit())){
            checkAndSetValueForTobaccoAndNicoTine(dataVariables, lifeStyleDetails);
            checkAndSetValueForLiquor(dataVariables, lifeStyleDetails);
        }
    }

    private void checkAndSetValueForLiquor(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails) {
        if(Objects.nonNull(lifeStyleDetails.getHabit().getLiquor())
                && Objects.nonNull(lifeStyleDetails.getHabit().getLiquor().getConsumptionDetails())
                && !lifeStyleDetails.getHabit().getLiquor().getConsumptionDetails().isEmpty()){
            setLiquorBasedQuestion(dataVariables, lifeStyleDetails.getHabit().getLiquor());
        }else {
            dataVariables.put("questionWineIsTrue", BLANK);
            dataVariables.put("questionBeerIsTrue", BLANK);
            dataVariables.put("questionLiquorIsTrue", BLANK);
            dataVariables.put("answerWineCapacity", BLANK);
            dataVariables.put("answerBeerCapacity", BLANK);
            dataVariables.put("answerLiquorCapacity", BLANK);
        }
    }

    private void checkAndSetValueForTobaccoAndNicoTine(Map<String, Object> dataVariables, LifeStyleDetails lifeStyleDetails) {
        if(Objects.nonNull(lifeStyleDetails.getHabit().getTobaccoNicotine())
                && Objects.nonNull(lifeStyleDetails.getHabit().getTobaccoNicotine().getConsumptionDetails())
                && !lifeStyleDetails.getHabit().getTobaccoNicotine().getConsumptionDetails().isEmpty()){
            setTobacooQuestionCapacity(dataVariables, lifeStyleDetails.getHabit().getTobaccoNicotine());
        } else {
            dataVariables.put("smokingAnswer", BLANK);
            dataVariables.put("tobaccoAnswerHowManyGutka", BLANK);
        }
    }

    private void setLiquorBasedQuestion(Map<String, Object> dataVariables, Liquor liquor) {
        String questionWineIsTrue = "";
        String questionBeerIsTrue = "";
        String questionLiquorIsTrue = "";
        String answerWineCapacity = "";
        String answerBeerCapacity = "";
        String answerLiquorCapacity = "";
        List<ConsumptionDetail> consumptionDetailsLiquorList = liquor.getConsumptionDetails();
        for(int i=0;i < consumptionDetailsLiquorList.size();i++){
            ConsumptionDetail consumptionDetailLiquor = consumptionDetailsLiquorList.get(i);
            String type = consumptionDetailLiquor.getType();
            if("01".equals(type)){
                questionWineIsTrue = "01";
                answerWineCapacity = Utility.isNotNullOrEmpty(consumptionDetailLiquor.getQuantity()) ? consumptionDetailLiquor.getQuantity() : "";
            }
            else if("02".equals(type)){
                questionBeerIsTrue = "01";
                answerBeerCapacity = Utility.isNotNullOrEmpty(consumptionDetailLiquor.getQuantity()) ? consumptionDetailLiquor.getQuantity() : "";
            }
            else if("03".equals(type)){
                questionLiquorIsTrue = "01";
                answerLiquorCapacity = Utility.isNotNullOrEmpty(consumptionDetailLiquor.getQuantity()) ? consumptionDetailLiquor.getQuantity() : "";
            }
        }
        dataVariables.put("questionWineIsTrue",questionWineIsTrue);
        dataVariables.put("questionBeerIsTrue",questionBeerIsTrue);
        dataVariables.put("questionLiquorIsTrue",questionLiquorIsTrue);
        dataVariables.put("answerWineCapacity",answerWineCapacity);
        dataVariables.put("answerBeerCapacity",answerBeerCapacity);
        dataVariables.put("answerLiquorCapacity",answerLiquorCapacity);
    }

    private void setTobacooQuestionCapacity(Map<String, Object> dataVariables, TobaccoNicotine tobaccoNicotine) {
        String questionSmokingHowManycigarette = "";
        String questionTobaccoHowManyGutka = "";
        List<ConsumptionDetail> consumptionDetailsTobaccoList = tobaccoNicotine.getConsumptionDetails();
        for(int i=0;i < consumptionDetailsTobaccoList.size();i++){
            ConsumptionDetail consumptionDetailTobacoo = consumptionDetailsTobaccoList.get(i);
            String type = consumptionDetailTobacoo.getType();
            if("01".equals(type)){
                questionSmokingHowManycigarette = Utility.isNotNullOrEmpty(consumptionDetailTobacoo.getQuantity()) ? consumptionDetailTobacoo.getQuantity() : "";
            }
            else if("02".equals(type)){
                questionTobaccoHowManyGutka = Utility.isNotNullOrEmpty(consumptionDetailTobacoo.getQuantity()) ? consumptionDetailTobacoo.getQuantity() : "";
            }
        }
        dataVariables.put("smokingAnswer",questionSmokingHowManycigarette);
        dataVariables.put("tobaccoAnswerHowManyGutka",questionTobaccoHowManyGutka);

    }


    private String setQuestionAlcohol1Answer(LifeStyleDetails lifeStyleDetails, String channelName,
        DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
        String questionAlcohol1Answer;
        if (channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
            questionAlcohol1Answer = Utility.nullSafe(
                lifeStyleDetails.getHabit().getLiquor().getConsumeSubstance());
        } else {
            questionAlcohol1Answer = Utility.nullSafe(
                diagnosedOrTreatedDetails.getSpecifyHabit()
                    .getDrinkLiquorThreeDaysOrMore());
        }
        return questionAlcohol1Answer;
    }

    private String setQuestionTobaccoAnswer(LifeStyleDetails lifeStyleDetails,
        String channelName,
        DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
        String questionTobaccoAnswer;
        if (channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
            questionTobaccoAnswer = Utility.nullSafe(
                lifeStyleDetails.getHabit().getTobaccoNicotine().getConsumeSubstance());

        } else {
            questionTobaccoAnswer = Utility.nullSafe(
                diagnosedOrTreatedDetails.getSpecifyHabit()
                    .getConsumeTobaccoCurrentlyOrOcc());
        }
        return questionTobaccoAnswer;
    }

    private void setSwissReTobaccoAlcoholDrugAnswer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, String channelName, Map<String, Object> dataVariables) {
        String everConsumeType = "";
        String swissReTobaccoAlchoholDrugsConsumed = "";
        String consumeTobaccoCurrentlyOrOcc = "";
        String moreThanTwentyOrChewTen = "";
        String drinkLiquorThreeDaysOrMore = "";
        String everAdvisedToQuit = "";
        String isConsumeAnyIllegalDrugs = "";

        if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelName) || AppConstants.CHANNEL_NEO.equalsIgnoreCase(channelName)) {
            swissReTobaccoAlchoholDrugsConsumed = Objects.nonNull(diagnosedOrTreatedDetails.getSpecifyHabit()) ?
                    Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit().getEverConsumeType()) : AppConstants.NO;
            everConsumeType = Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit()
                                .getEverConsumeType());
            consumeTobaccoCurrentlyOrOcc = Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit()
                                            .getConsumeTobaccoCurrentlyOrOcc());
            moreThanTwentyOrChewTen = Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit()
                                        .getMoreThanTwentyOrChewTen());
            drinkLiquorThreeDaysOrMore = Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit()
                                            .getDrinkLiquorThreeDaysOrMore());
            everAdvisedToQuit = Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit()
                                    .getEverAdvisedToQuit());
            isConsumeAnyIllegalDrugs = Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getSpecifyHabit()
                                            .getIsConsumeAnyIllegalDrugs());
        }
        dataVariables.put("swissReTobaccoAlchoholDrugsConsumed", swissReTobaccoAlchoholDrugsConsumed);
        dataVariables.put("everConsumeType", everConsumeType);
        dataVariables.put("consumeTobaccoCurrentlyOrOcc",consumeTobaccoCurrentlyOrOcc);
        dataVariables.put("moreThanTwentyOrChewTen",moreThanTwentyOrChewTen);
        dataVariables.put("drinkLiquorThreeDaysOrMore",drinkLiquorThreeDaysOrMore);
        dataVariables.put("everAdvisedToQuit",everAdvisedToQuit);
        dataVariables.put("isConsumeAnyIllegalDrugs",isConsumeAnyIllegalDrugs);
    }

    private String setQuestionDrugsAnswer(LifeStyleDetails lifeStyleDetails, String channelName,
        DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
        String questionDrugsAnswer;
        if (channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
            questionDrugsAnswer = Utility
                .nullSafe(lifeStyleDetails.getHabit().getDrugs().getConsumeSubstance());
        } else {
            questionDrugsAnswer = Utility.nullSafe(
                diagnosedOrTreatedDetails.getSpecifyHabit()
                    .getIsConsumeAnyIllegalDrugs());
        }
        return questionDrugsAnswer;
    }

    private void setTobaccoAlcholQuestion(LifeStyleDetails lifeStyleDetails, Map<String, Object> dataVariables, String channelName) {
        String isTobaccoAlcoholDrugsConsumed = "";
        if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelName)) {
            Optional<Habit> habit = Optional.ofNullable(lifeStyleDetails.getHabit());
            if (habit.isPresent() && (!CollectionUtils.isEmpty(habit.get().getDrugs().getConsumptionDetails())
                    || !CollectionUtils
                    .isEmpty(habit.get().getLiquor().getConsumptionDetails())
                    || !CollectionUtils
                    .isEmpty(habit.get().getTobaccoNicotine().getConsumptionDetails()))) {
                isTobaccoAlcoholDrugsConsumed = YES;
            }
        } else {
            isTobaccoAlcoholDrugsConsumed = Utility.nullSafe(lifeStyleDetails.getHealth().getDiagnosedOrTreatedDetails()
                    .getSpecifyHabit().getEverConsumeType());
        }
        dataVariables.put(AppConstants.IS_TOBACCO_ALCOHOL_DRUGS_CONSUMED, Utility.convertToYesOrNo(isTobaccoAlcoholDrugsConsumed));
    }

    public void setMedicalQuestionsAnswer(MedicalQuestions medicalQuestions, DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,
                                          String channelName) {
        setQuestion1Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion2Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion3Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion4Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion5Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion6Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion7Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion8Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion9Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion10Answer(diagnosedOrTreatedDetails, medicalQuestions);
        setQuestion11Answer(diagnosedOrTreatedDetails, medicalQuestions, channelName);
        setQuestion12Answer(diagnosedOrTreatedDetails, medicalQuestions);
        setQuestion13Answer(diagnosedOrTreatedDetails, medicalQuestions);
        }

    private void setQuestion1Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions,
                                    String channelName) {
        String question1Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails)) {
            if (Objects.nonNull(diagnosedOrTreatedDetails.getHormonal())) {
                if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelName) && AppConstants.INFLUENCER_CHANNEL_38.equalsIgnoreCase(channelName)) {
                    if("01".equals(diagnosedOrTreatedDetails.getHormonal().getDiabetes())
                            ||"01".equals(diagnosedOrTreatedDetails.getHormonal().getHighBloodSugar())){
                        question1Answer = "01";
                    }
                } else {
                    question1Answer = Utility.nullSafe(diagnosedOrTreatedDetails.getHormonal().getHighBloodSugar());
                }
            } else {
                question1Answer = AppConstants.NO;
            }
        }
        medicalQuestions.setQuestion1Answer(Utility.convertToYesOrNo(question1Answer));

    }
    private void setQuestion2Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions,String channelName) {
        String question2Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails.getCardio())) {
            if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelName) && AppConstants.INFLUENCER_CHANNEL_38.equalsIgnoreCase(channelName)) {
                question2Answer = setQuestion2Foraggregator(diagnosedOrTreatedDetails);
                if("01".equals(diagnosedOrTreatedDetails.getCardio().getHighBloodPressure())
                        ||"Y".equals(diagnosedOrTreatedDetails.getCardio().getHighBloodPressure())){
                    question2Answer = "YES";
                }
            }else {
                question2Answer = Objects.nonNull(diagnosedOrTreatedDetails.getCardio())
                        ? Utility.nullSafe(diagnosedOrTreatedDetails.getCardio().getHighBloodPressure()) : AppConstants.NO;
            }
        }
        medicalQuestions.setQuestion2Answer(Utility.convertToYesOrNo(question2Answer));

    }
    private String setQuestion2Foraggregator(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails){
        if (Objects.nonNull(diagnosedOrTreatedDetails.getHormonal())) {
            if("01".equals(diagnosedOrTreatedDetails.getHormonal().getThyroid())){
                return "YES";
            }
        }
        return "NO";
    }
    private void setQuestion4Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions,String channel) {
        String question4Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails) && Objects.nonNull(diagnosedOrTreatedDetails.getRespiratory())) {
            if(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel) && AppConstants.INFLUENCER_CHANNEL_38.equalsIgnoreCase(channel)){
                if("01".equals(diagnosedOrTreatedDetails.getRespiratory().getAsthma())
                   ||"01".equals(diagnosedOrTreatedDetails.getRespiratory().getTuberculosis())
                        ||"01".equals(diagnosedOrTreatedDetails.getRespiratory().getAnyOtherRespiratoryDisorder())){
                    question4Answer = "YES";
                }
            }
            else {
                question4Answer = Objects.nonNull(diagnosedOrTreatedDetails.getRespiratory())
                        ? Utility.nullSafe(diagnosedOrTreatedDetails.getRespiratory().getAsthma()) : AppConstants.NO;
            }
        }
        medicalQuestions.setQuestion4Answer(Utility.convertToYesOrNo(question4Answer));

    }

    private void setQuestion3Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions, String channel) {
        String question3Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails)) {
            if(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel) && AppConstants.INFLUENCER_CHANNEL_38.equalsIgnoreCase(channel)){
                question3Answer = checkMandatoryFieldsAndSetValue(diagnosedOrTreatedDetails);
            }else {
                question3Answer = Objects.nonNull(diagnosedOrTreatedDetails.getCardio())
                    ? Utility.nullSafe(diagnosedOrTreatedDetails.getCardio().getHeartAttack()) : AppConstants.NO;
            }
        }
        medicalQuestions.setQuestion3Answer(Utility.convertToYesOrNo(question3Answer));
    }

    private String checkMandatoryFieldsAndSetValue(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
       Cardio cardio = Optional.ofNullable(diagnosedOrTreatedDetails)
            .map(DiagnosedOrTreatedDetails::getCardio).orElse(null);
        List<String> answerList = new ArrayList<>();
        if(Objects.nonNull(cardio)){
            answerList.add(Utility.convertToYesOrNo(cardio.getChestPain()));
            answerList.add(Utility.convertToYesOrNo(cardio.getHeartAttack()));
            answerList.add(Utility.convertToYesOrNo(cardio.getStroke()));
            answerList.add(Utility.convertToYesOrNo(cardio.getAnyOtherHeartCondition()));
            answerList.add(!StringUtils.isEmpty(cardio.getSpecifyDetails()) ? AppConstants.YES: AppConstants.BLANK);
            if(answerList.contains(AppConstants.YES)){
                return AppConstants.YES;
            }
        }
        return AppConstants.NO;
    }

    private void setQuestion5Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions,String channel) {
        String question5Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails) && Objects.nonNull(diagnosedOrTreatedDetails.getDigestiveAndRegulatory())) {
            if(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel)){
                question5Answer = setQuestion5HepatitisBC(diagnosedOrTreatedDetails);
                if("01".equals(diagnosedOrTreatedDetails.getDigestiveAndRegulatory().getJaundiceOrliverDisorder())){
                    question5Answer = "YES";
                }
            }
            else {
                question5Answer = Objects.nonNull(diagnosedOrTreatedDetails.getDigestiveAndRegulatory())
                        ? Utility.nullSafe(diagnosedOrTreatedDetails.getDigestiveAndRegulatory().getJaundiceOrliverDisorder()) : AppConstants.NO;
            }
        }
        medicalQuestions.setQuestion5Answer(Utility.convertToYesOrNo(question5Answer));

    }
    private String setQuestion5HepatitisBC(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails){
        if (Objects.nonNull(diagnosedOrTreatedDetails.getInfectiousOrContagious())) {
            if ("01".equals(diagnosedOrTreatedDetails.getInfectiousOrContagious().getHepatitisB())
                    || "01".equals(diagnosedOrTreatedDetails.getInfectiousOrContagious().getHepatitisC())) {
                return "YES";
            }
        }
        return "NO";
    }
    private void setQuestion6Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions , String channelName) {
        String question6Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails) && Objects.nonNull(diagnosedOrTreatedDetails.getBloodAndCellular())) {
            if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channelName) && AppConstants.INFLUENCER_CHANNEL_38.equalsIgnoreCase(channelName)) {
                    question6Answer = setQuestoin6(diagnosedOrTreatedDetails);
                if("01".equals(diagnosedOrTreatedDetails.getBloodAndCellular().getTumorAndMalignantGrowth())
                        ||"01".equals(diagnosedOrTreatedDetails.getBloodAndCellular().getCancer())
                        ||"01".equals(diagnosedOrTreatedDetails.getBloodAndCellular().getAnyBloodDisorder())
                        ||"01".equals(diagnosedOrTreatedDetails.getBloodAndCellular().getLeukemia())){
                       question6Answer = "YES";
                }
            }else {
                question6Answer = Objects.nonNull(diagnosedOrTreatedDetails.getBloodAndCellular())
                        ? Utility.nullSafe(diagnosedOrTreatedDetails.getBloodAndCellular().getCancer()) : AppConstants.NO;
            }
        }
        medicalQuestions.setQuestion6Answer(Utility.convertToYesOrNo(question6Answer));
    }

    private String setQuestoin6(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails){
        if (Objects.nonNull(diagnosedOrTreatedDetails) && Objects.nonNull(diagnosedOrTreatedDetails.getInfectiousOrContagious())) {
            if( "01".equals(diagnosedOrTreatedDetails.getInfectiousOrContagious().getAidsRelated())
                    ||"01".equals(diagnosedOrTreatedDetails.getInfectiousOrContagious().getAnySTDDisease())
                    ||"01".equals(diagnosedOrTreatedDetails.getInfectiousOrContagious().getHivInfection())){
                return "YES";
            }
            else return "NO";
        }
        return "NO";
    }

    private void setQuestion7Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,
        MedicalQuestions medicalQuestions, String channelName) {
        String question7Answer = "";
        if (channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && channelName.equalsIgnoreCase(AppConstants.INFLUENCER_CHANNEL_38)) {
            question7Answer = Utility.nullSafe(diagnosedOrTreatedDetails.getDigestiveAndRegulatory()
                .getAnyKidneyDisorders());
        } else {
            question7Answer = Utility.nullSafe(diagnosedOrTreatedDetails.getKidneyDisorder()
                .getIsKidneyRenalDisorder());
        }
        medicalQuestions.setQuestion7Answer(Utility.convertToYesOrNo(question7Answer));
    }
    private void setQuestion8Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,
                                    MedicalQuestions medicalQuestions, String channelName) {
        String question8Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails) && Objects.nonNull(diagnosedOrTreatedDetails.getMentalAndPsychiatric())) {
                if(channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && channelName.equalsIgnoreCase(AppConstants.INFLUENCER_CHANNEL_38)) {
                    if("01".equals(diagnosedOrTreatedDetails.getMentalAndPsychiatric().getMentalOrPsychiatricAilment())
                        || "01".equals(diagnosedOrTreatedDetails.getMentalAndPsychiatric().getNervousSystemDisease())) {

                        question8Answer = "01";
                    }
                } else {

                    question8Answer = Objects.nonNull(diagnosedOrTreatedDetails.getMentalAndPsychiatric())
                            ? Utility.nullSafe(diagnosedOrTreatedDetails.getMentalAndPsychiatric().getMentalOrPsychiatricAilment()) : AppConstants.NO;
                }
        }
        medicalQuestions.setQuestion8Answer(Utility.convertToYesOrNo(question8Answer));

    }
    private void setQuestion9Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,
                                    MedicalQuestions medicalQuestions, String channelName) {
        String question9Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails) && Objects.nonNull(diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular())) {
                        if(channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && channelName.equalsIgnoreCase(AppConstants.INFLUENCER_CHANNEL_38)) {
                             if("01".equals(diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular().getAnyAilmentOfBonesOrjoints())
                                 || "01".equals(diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular().getAnyDisorderOfMuscle())
                                 || "01".equals(diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular().getAnyDisorderOfSpine())) {

                        question9Answer = "01";
                    }

                } else {
                    question9Answer = Objects.nonNull(diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular())
                            ? Utility.nullSafe(diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular().getAnyAilmentOfBonesOrjoints()) : AppConstants.NO;
                }
            }
        medicalQuestions.setQuestion9Answer(Utility.convertToYesOrNo(question9Answer));

    }
    private void setQuestion10Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions) {
        String question10Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails)) {
            question10Answer = Objects.nonNull(diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork())
                    ? Utility.nullSafe(diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork().getEverBeenhospitalized()) : AppConstants.NO;
        }
        medicalQuestions.setQuestion10Answer(Utility.convertToYesOrNo(question10Answer));

    }

    private void setQuestion11Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,
        MedicalQuestions medicalQuestions, String channelName) {
        String question11Answer = "";
        if (channelName.equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && channelName.equalsIgnoreCase(AppConstants.INFLUENCER_CHANNEL_38)) {
            question11Answer = Utility.nullSafe(
                diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork()
                    .getEverBeenhospitalized());
        } else {
            question11Answer =
                Objects.nonNull(diagnosedOrTreatedDetails.getEverAdvisedForSurgeryEcg())
                    ? Utility.nullSafe(
                    diagnosedOrTreatedDetails.getEverAdvisedForSurgeryEcg().getIsEverAdvisedSurg())
                    : AppConstants.NO;
        }
        medicalQuestions.setQuestion11Answer(Utility.convertToYesOrNo(question11Answer));
    }
    private void setQuestion12Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions) {
        String question12Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails)) {
            question12Answer = Objects.nonNull(diagnosedOrTreatedDetails.getGeneticTesting()) &&
                !StringUtils.isEmpty(diagnosedOrTreatedDetails.getGeneticTesting().getEverHadGenetictesting())
                ? diagnosedOrTreatedDetails.getGeneticTesting().getEverHadGenetictesting() : AppConstants.NO;
        }
        medicalQuestions.setQuestion12Answer(Utility.convertToYesOrNo(question12Answer));

    }
    private void setQuestion13Answer(DiagnosedOrTreatedDetails diagnosedOrTreatedDetails, MedicalQuestions medicalQuestions) {
        String question13Answer="";
        if (Objects.nonNull(diagnosedOrTreatedDetails)) {
            question13Answer = Objects.nonNull(diagnosedOrTreatedDetails.getExternalInternalAnomaly())
                    ? Utility.nullSafe(diagnosedOrTreatedDetails.getExternalInternalAnomaly()
                    .getIsExternalInternalAnomaly()) : AppConstants.NO;
        }
        medicalQuestions.setQuestion13Answer(Utility.convertToYesOrNo(question13Answer));

    }

    public void setAggregatorMedicalQuestion(NeoAggregatorMedicalQuestions medicalQuestions,
        DiagnosedOrTreatedDetails diagnosedOrTreatedDetails) {
        String question1A = diagnosedOrTreatedDetails.getCardio().getChestPain();
        String question1B = diagnosedOrTreatedDetails.getCardio().getHeartAttack();
        String question1C = diagnosedOrTreatedDetails.getCardio().getStroke();
        String question1D = diagnosedOrTreatedDetails.getCardio().getAnyOtherHeartCondition();
        String question1Answer = diagnosedOrTreatedDetails.getCardio().getSpecifyDetails();
        String question2A = diagnosedOrTreatedDetails.getHormonal().getHighBloodSugar();
        String question2B = diagnosedOrTreatedDetails.getHormonal().getDiabetes();
        String question3A = diagnosedOrTreatedDetails.getRespiratory().getAsthma();
        String question3B = diagnosedOrTreatedDetails.getRespiratory().getTuberculosis();
        String question3C = diagnosedOrTreatedDetails.getRespiratory()
            .getAnyOtherRespiratoryDisorder();
        String question3Answer = diagnosedOrTreatedDetails.getRespiratory().getSpecifyDetails();
        String question4A = diagnosedOrTreatedDetails.getBloodAndCellular().getCancer();
        String question4B = diagnosedOrTreatedDetails.getBloodAndCellular()
            .getTumorAndMalignantGrowth();
        String question4C = diagnosedOrTreatedDetails.getBloodAndCellular().getLeukemia();
        String question4D = diagnosedOrTreatedDetails.getInfectiousOrContagious().getHivInfection();
        String question4E = diagnosedOrTreatedDetails.getInfectiousOrContagious().getAidsRelated();
        String question4F = diagnosedOrTreatedDetails.getInfectiousOrContagious()
            .getAnySTDDisease();
        String question4G = diagnosedOrTreatedDetails.getBloodAndCellular().getAnyBloodDisorder();
        String question4Answer = diagnosedOrTreatedDetails.getBloodAndCellular()
            .getSpecifyDetails();
        String question5A = diagnosedOrTreatedDetails.getDigestiveAndRegulatory()
            .getJaundiceOrliverDisorder();
        String question5B = diagnosedOrTreatedDetails.getDigestiveAndRegulatory()
            .getJaundiceOrliverDisorder();
        String question5C = diagnosedOrTreatedDetails.getInfectiousOrContagious().getHepatitisB();
        String question5D = diagnosedOrTreatedDetails.getInfectiousOrContagious().getHepatitisC();
        String question5Answer = diagnosedOrTreatedDetails.getDigestiveAndRegulatory()
            .getSpecifyDetails();
        String question7B = diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular()
            .getAnyDisorderOfSpine();
        String question6A = diagnosedOrTreatedDetails.getMentalAndPsychiatric()
            .getMentalOrPsychiatricAilment();
        String question6B = diagnosedOrTreatedDetails.getMentalAndPsychiatric()
            .getNervousSystemDisease();
        String question7C = diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular()
            .getAnyDisorderOfMuscle();
        String question6Answer = diagnosedOrTreatedDetails.getMentalAndPsychiatric()
            .getSpecifyDetails();
        String question7A = diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular()
            .getAnyAilmentOfBonesOrjoints();
        String question7D = diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular()
            .getSpecifyDetails();
        String question8A = diagnosedOrTreatedDetails.getDigestiveAndRegulatory()
            .getAnyKidneyDisorders();
        String question8Answer = diagnosedOrTreatedDetails.getInfectiousOrContagious().getSpecifyDetails();
        String question9A = diagnosedOrTreatedDetails.getCardio().getHighBloodPressure();
        String question9B = diagnosedOrTreatedDetails.getHormonal().getThyroid();
        String question9Answer = diagnosedOrTreatedDetails.getHormonal().getSpecifyDetails();
        String question10A = diagnosedOrTreatedDetails.getInfectiousOrContagious()
            .getAnyGynaecologicalDisorder();
        String question10B = diagnosedOrTreatedDetails.getNeuralOrSkeletalOrMuscular()
            .getAnyDisorderOfENT();
        String question10C = diagnosedOrTreatedDetails.getHormonal().getAnyOtherHormonalDisorder();
        String question10Answer = diagnosedOrTreatedDetails.getHormonal().getSpecifyDetails();
        String question11A = diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork()
            .getEverBeenhospitalized();
        String question11Answer = diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork()
            .getEverBeenhospitalizedDetails();
        String question12A = diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork()
            .getEverBeenAbsentFromWork();
        String question12Answer = diagnosedOrTreatedDetails.getHospitalizationAndAbsenceFromWork()
            .getEverBeenAbsentFromWorkDetails();

        String question13Answer = !StringUtils.isEmpty(diagnosedOrTreatedDetails.getGeneticTesting().getEverHadGenetictesting())
            ? Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getGeneticTesting().getEverHadGenetictesting()) : AppConstants.BLANK;

        String question13AChoice = Objects.nonNull(diagnosedOrTreatedDetails.getGeneticTesting().getSpecifyGeneticDetails())
            ? diagnosedOrTreatedDetails.getGeneticTesting().getSpecifyGeneticDetails() : AppConstants.BLANK;

        String question14Answer = !StringUtils.isEmpty(diagnosedOrTreatedDetails.getExternalInternalAnomaly().getIsExternalInternalAnomaly())
            ? Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getExternalInternalAnomaly().getIsExternalInternalAnomaly()) : AppConstants.BLANK;

        String question14AChoice = Objects.nonNull(diagnosedOrTreatedDetails.getExternalInternalAnomaly().getSpecifyInternalDetails())
            ? diagnosedOrTreatedDetails.getExternalInternalAnomaly().getSpecifyInternalDetails() : AppConstants.BLANK;

        setQuestion1Answer(medicalQuestions, question1A, question1B, question1C, question1D,
            question1Answer);
        setQuestion2Answer(medicalQuestions, question2A, question2B);
        setQuestion3Answer(medicalQuestions, question3A, question3B, question3C, question3Answer);
        setQuestion4Answer(medicalQuestions, question4A, question4B, question4C);
        setQuestion4AnswerSubPart(medicalQuestions, question4D, question4E, question4F, question4G,
            question4Answer);
        setQuestion5Answer(medicalQuestions, question5A, question5B, question5C, question5D,
            question5Answer);
        setQuestion6Answer(medicalQuestions, question6A, question6B, question6Answer);
        setQuestion7Answer(medicalQuestions, question7B, question7C, question7A, question7D);
        setQuestion8Answer(medicalQuestions, question8A, question8Answer);
        setQuestion9Answer(medicalQuestions, question9A, question9B, question9Answer);
        setQuestion10Answer(medicalQuestions, question10A, question10B, question10C, question10Answer);
        setQuestion11Answer(medicalQuestions, question11A, question11Answer);
        setQuestion12Answer(medicalQuestions, question12A, question12Answer);
        setQuestion13And14Answer(medicalQuestions, question13Answer, question13AChoice, question14Answer, question14AChoice);
    }

    private void setQuestion13And14Answer(NeoAggregatorMedicalQuestions medicalQuestions,
        String question13Answer, String question13AChoice, String question14Answer,
        String question14AChoice) {
        medicalQuestions.setQuestion14Answer(question14Answer);
        medicalQuestions.setQuestion14AChoice(question14AChoice);
        medicalQuestions.setQuestion13Answer(question13Answer);
        medicalQuestions.setQuestion13AAnswer(question13AChoice);
    }

    private void setQuestion12Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question12A,
        String question12Answer) {
        medicalQuestions.setQuestion12AChoice(
            Utility.isNotNullOrEmpty(question12A) ? Utility.convertToYesOrNo(question12A) : "");
        medicalQuestions.setQuestion12Answer(
            Utility.isNotNullOrEmpty(question12Answer) ? Utility.convertToYesOrNo(question12Answer)
                : "");
    }

    private void setQuestion11Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question11A,
        String question11Answer) {
        medicalQuestions.setQuestion11AChoice(
            Utility.isNotNullOrEmpty(question11A) ? Utility.convertToYesOrNo(question11A) : "");
        medicalQuestions.setQuestion11Answer(
            Utility.isNotNullOrEmpty(question11Answer) ? Utility.convertToYesOrNo(question11Answer)
                : "");
    }

    private void setQuestion10Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question10A,
        String question10B, String question10C, String question10Answer) {
        medicalQuestions.setQuestion10AChoice(
            Utility.isNotNullOrEmpty(question10A) ? Utility.convertToYesOrNo(question10A) : "");
        medicalQuestions.setQuestion10BChoice(
            Utility.isNotNullOrEmpty(question10B) ? Utility.convertToYesOrNo(question10B) : "");
        medicalQuestions.setQuestion10CChoice(
            Utility.isNotNullOrEmpty(question10C) ? Utility.convertToYesOrNo(question10C) : "");
        medicalQuestions.setQuestion10Answer(
            Utility.isNotNullOrEmpty(question10Answer) ? Utility.convertToYesOrNo(question10Answer)
                : "");
    }

    private void setQuestion9Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question9A,
        String question9B, String question9Answer) {
        medicalQuestions.setQuestion9AChoice(
            Utility.isNotNullOrEmpty(question9A) ? Utility.convertToYesOrNo(question9A) : "");
        medicalQuestions.setQuestion9BChoice(
            Utility.isNotNullOrEmpty(question9B) ? Utility.convertToYesOrNo(question9B) : "");
        medicalQuestions.setQuestion9Answer(
            Utility.isNotNullOrEmpty(question9Answer) ? Utility.convertToYesOrNo(question9Answer)
                : "");
    }

    private void setQuestion8Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question8A,
        String question8Answer) {
        medicalQuestions.setQuestion8AChoice(
            Utility.isNotNullOrEmpty(question8A) ? Utility.convertToYesOrNo(question8A) : "");
        medicalQuestions.setQuestion8Answer(
            Utility.isNotNullOrEmpty(question8Answer) ? Utility.convertToYesOrNo(question8Answer)
                : "");
    }

    private void setQuestion7Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question7B,
        String question7C, String question7A, String question7D) {
        medicalQuestions
            .setQuestion7AChoice(Utility.isNotNullOrEmpty(question7A) ? Utility.convertToYesOrNo(
                question7A) : "");
        medicalQuestions.setQuestion7BChoice(
            Utility.isNotNullOrEmpty(question7B) ? Utility.convertToYesOrNo(question7B) : "");
        medicalQuestions.setQuestion7CChoice(
            Utility.isNotNullOrEmpty(question7C) ? Utility.convertToYesOrNo(question7C) : "");
        medicalQuestions.setQuestion7DChoice(
            Utility.isNotNullOrEmpty(question7D) ? Utility.convertToYesOrNo(question7D) : "");
    }

    private void setQuestion6Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question6A,
        String question6B, String question6Answer) {
        medicalQuestions.setQuestion6AChoice(
            Utility.isNotNullOrEmpty(question6A) ? Utility.convertToYesOrNo(question6A) : "");
        medicalQuestions
            .setQuestion6BChoice(Utility.isNotNullOrEmpty(question6B) ? Utility.convertToYesOrNo(
                question6B) : "");
        medicalQuestions.setQuestion6Answer(Utility.isNotNullOrEmpty(question6Answer) ? Utility
            .convertToYesOrNo(question6Answer) : "");
    }

    private void setQuestion5Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question5A,
        String question5B, String question5C, String question5D, String question5Answer) {
        medicalQuestions
            .setQuestion5AChoice(Utility.convertToYesOrNo(Utility.isNotNullOrEmpty(question5A) ?
                Utility.convertToYesOrNo(question5A) : ""));
        medicalQuestions
            .setQuestion5BChoice(Utility.isNotNullOrEmpty(question5B) ? Utility.convertToYesOrNo(
                question5B) : "");
        medicalQuestions.setQuestion5CChoice(Utility.isNotNullOrEmpty(question5C) ?
            Utility.convertToYesOrNo(question5C) : "");
        medicalQuestions.setQuestion5DChoice(Utility.isNotNullOrEmpty(question5D) ?
                Utility.convertToYesOrNo(question5D) : "");
        medicalQuestions.setQuestion5Answer(
            Utility.isNotNullOrEmpty(question5Answer) ? Utility.convertToYesOrNo(question5Answer)
                : "");
    }

    private void setQuestion4Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question4A,
        String question4B, String question4C) {
        medicalQuestions.setQuestion4AChoice(Utility.isNotNullOrEmpty(question4A) ?
            Utility.convertToYesOrNo(question4A) : "");
        medicalQuestions
            .setQuestion4BChoice(Utility.isNotNullOrEmpty(question4B) ? Utility.convertToYesOrNo(
                question4B) : "");
        medicalQuestions.setQuestion4CChoice(Utility.isNotNullOrEmpty(question4C) ?
            Utility.convertToYesOrNo(question4C) : "");
    }

    private void setQuestion4AnswerSubPart(NeoAggregatorMedicalQuestions medicalQuestions,
        String question4D, String question4E,
        String question4F, String question4G, String question4Answer) {
        medicalQuestions.setQuestion4DChoice(Utility.isNotNullOrEmpty(question4D) ? Utility
            .convertToYesOrNo(question4D) : "");
        medicalQuestions.setQuestion4EChoice(Utility.isNotNullOrEmpty(question4E) ? Utility
            .convertToYesOrNo(question4E) : "");
        medicalQuestions
            .setQuestion4FChoice(Utility.isNotNullOrEmpty(question4F) ? Utility.convertToYesOrNo(
                question4F) : "");
        medicalQuestions.setQuestion4GChoice(Utility.isNotNullOrEmpty(question4G) ? Utility
            .convertToYesOrNo(question4G) : "");
        medicalQuestions.setQuestion4Answer(
            Utility.isNotNullOrEmpty(question4Answer) ? Utility.convertToYesOrNo(question4Answer)
                : "");
    }

    private void setQuestion3Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question3A,
        String question3B, String question3C, String question3Answer) {
        medicalQuestions.setQuestion3AChoice(Utility.isNotNullOrEmpty(question3A) ?
            Utility.convertToYesOrNo(question3A) : "");
        medicalQuestions.setQuestion3BChoice(Utility.isNotNullOrEmpty(question3B) ?
            Utility.convertToYesOrNo(question3B) : "");
        medicalQuestions
            .setQuestion3CChoice(Utility.isNotNullOrEmpty(question3C) ? Utility.convertToYesOrNo(
                question3C) : "");
        medicalQuestions.setQuestion3Answer(Utility.isNotNullOrEmpty(question3Answer) ?
            Utility.convertToYesOrNo(question3Answer) : "");
    }

    private void setQuestion2Answer(NeoAggregatorMedicalQuestions medicalQuestions, String question2A,
        String question2B) {
        medicalQuestions.setQuestion2AChoice(Utility.isNotNullOrEmpty(question2A) ?
            Utility.convertToYesOrNo(question2A) : "");
        medicalQuestions.setQuestion2BChoice(Utility.isNotNullOrEmpty(question2B) ?
            Utility.convertToYesOrNo(question2B) : "");
    }

    private void setQuestion1Answer(NeoAggregatorMedicalQuestions medicalQuestions,
        String question1A,
        String question1B, String question1C, String question1D, String question1Answer) {
        medicalQuestions.setQuestion1AChoice(Utility.isNotNullOrEmpty(question1A) ?
            Utility.convertToYesOrNo(question1A) : "");
        medicalQuestions.setQuestion1BChoice(Utility.isNotNullOrEmpty(question1B) ?
            Utility.convertToYesOrNo(question1B) : "");
        medicalQuestions.setQuestion1CChoice(Utility.isNotNullOrEmpty(question1C) ?
            Utility.convertToYesOrNo(question1C) : "");
        medicalQuestions.setQuestion1DChoice(Utility.isNotNullOrEmpty(question1D) ?
            Utility.convertToYesOrNo(question1D) : "");
        medicalQuestions.setQuestion1Answer(Utility.isNotNullOrEmpty(question1Answer) ?
            Utility.convertToYesOrNo(question1Answer) : "");
    }

    private void convertNullValuesToBlank(Map<String, Object> dataMap) {
        Set<String> keys = dataMap.keySet();
        for (String key : keys) {
            if (Objects.isNull(dataMap.get(key))) {
                dataMap.put(key, "");
            }
        }
    }
}
