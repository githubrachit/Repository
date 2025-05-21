package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author manish on 08/10/20
 */
public class NeoBaseProposalForm extends NeoBaseDocument{

    private static final Logger logger = LoggerFactory.getLogger(NeoBaseProposalForm.class);
    public static final String PAYOR = "PAYOR";

    public void interChangeProposerAndLifeInsured(ProposalDetails proposalDetails) {
//        proposalDetails.setPartyInformation(Arrays.asList(proposalDetails.getPartyInformation()
//            .get(1), proposalDetails.getPartyInformation().get(0)));
        if (!CollectionUtils.isEmpty(proposalDetails.getSecondaryLifeStyleDetails())) {
            LifeStyleDetails secondarylifeStyleDetails = proposalDetails.getSecondaryLifeStyleDetails()
                .get(0);
            secondarylifeStyleDetails
                .setInsuranceDetails(proposalDetails.getSecondaryInsuranceDetails().get(0));
            proposalDetails.setLifeStyleDetails(
                Arrays.asList(proposalDetails.getLifeStyleDetails().get(0),
                    secondarylifeStyleDetails));
        }
    }
    public void interChangeLifeinsuredForSSPJLProposerAndLifeInsured(ProposalDetails proposalDetails) {
//        proposalDetails.setPartyInformation(Arrays.asList(proposalDetails.getPartyInformation()
//            .get(1), proposalDetails.getPartyInformation().get(0)));

        swapRelationShipWithProposerMapping(proposalDetails);
        if (!CollectionUtils.isEmpty(proposalDetails.getSecondaryLifeStyleDetails())) {
            LifeStyleDetails secondarylifeStyleDetails = proposalDetails.getSecondaryLifeStyleDetails()
                    .get(0);
            secondarylifeStyleDetails
                    .setInsuranceDetails(proposalDetails.getSecondaryInsuranceDetails().get(0));
            proposalDetails.setLifeStyleDetails(
                    Arrays.asList(proposalDetails.getLifeStyleDetails().get(0),secondarylifeStyleDetails));
        }
    }

    private static void swapRelationShipWithProposerMapping(ProposalDetails proposalDetails) {
        BasicDetails proposerBasicDetails = Optional.ofNullable(proposalDetails.getPartyInformation())
                .flatMap(partyInformations -> partyInformations.stream()
                        .filter(partyInformation -> AppConstants.LIFE_INSURED.equalsIgnoreCase(partyInformation.getPartyType()))
                        .findFirst().map(PartyInformation::getBasicDetails)).orElse(null);

        BasicDetails insuredBasicDetails = Optional.ofNullable(proposalDetails.getPartyInformation())
                .flatMap(partyInformations -> partyInformations.stream()
                        .filter(partyInformation -> AppConstants.PROPOSER.equalsIgnoreCase(partyInformation.getPartyType()))
                        .findFirst().map(PartyInformation::getBasicDetails)).orElse(null);

        if (Objects.nonNull(proposerBasicDetails) && Objects.nonNull(insuredBasicDetails)) {
            String relationShipWithProposerInProposerBlock = proposerBasicDetails.getRelationshipWithProposer();
            proposerBasicDetails.setRelationshipWithProposer(insuredBasicDetails.getRelationshipWithProposer());
            insuredBasicDetails.setRelationshipWithProposer(relationShipWithProposerInProposerBlock);
        }
    }

    public void changeProposerAndLifeinsuredForForm2(ProposalDetails proposalDetails){
        if(!CollectionUtils.isEmpty(proposalDetails.getPartyInformation())
                && !CollectionUtils.isEmpty(proposalDetails.getEmploymentDetails().getPartiesInformation())){
            List<PartyInformation> partyInformationList = new ArrayList<>();
            partyInformationList.add(proposalDetails.getPartyInformation().get(1));
            partyInformationList.add(proposalDetails.getPartyInformation().get(0));
            Optional.ofNullable(proposalDetails.getPartyInformation())
                .flatMap(partyInformations -> partyInformations.stream()
                .filter(partyInformation -> "payor".equalsIgnoreCase(partyInformation.getPartyType())).findFirst())
                .ifPresent(partyInformationList::add);
            proposalDetails.setPartyInformation(partyInformationList);
            proposalDetails.getEmploymentDetails().setPartiesInformation(Arrays.asList(proposalDetails.getEmploymentDetails().getPartiesInformation().get(1), proposalDetails.getEmploymentDetails().getPartiesInformation().get(0)));

        }
    }

    public void invokeRetryLogicForProposalForm(ProposalDetails proposalDetails) throws UserHandledException {
        String isEmployee = null;
        String isCorporateCustomer = null;
        if (Objects.nonNull(proposalDetails.getPartyInformation())
            && !proposalDetails.getPartyInformation().isEmpty()) {
            Optional<BasicDetails> basicDetails = proposalDetails.getPartyInformation().stream()
                .filter(partyInformation -> partyInformation.getPartyType().equalsIgnoreCase("LifeInsured"))
                .findAny().map(PartyInformation::getBasicDetails);
            if (basicDetails.isPresent()) {
                isEmployee = basicDetails.get().getIsEmployee();
                isCorporateCustomer = basicDetails.get().getIsCorporateCustomer();
            }
            if(isEmployee ==null && isCorporateCustomer==null){
                logger.error("proposal form can not be generated until data comes from CRM for transactionId {} and at applicationStage {}",
                        proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getStage());
                Response response = new Response();
                List<String> errorMessages = new ArrayList<>();
                errorMessages.add("Data Missing");
                throw new UserHandledException(response, errorMessages, HttpStatus.BAD_REQUEST);
            } else {
                logger.info("proposal form data (isEmployee and isCorporateCustomer) are coming from CRM for transactionId {} and at applicationStage {}",
                        proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getStage());
            }
        }
    }

    public DocumentStatusDetails processDocumentGeneration(ProposalDetails proposalDetails, Context proposalFormDetailsContext, String proposalFormString,
                                                           String templateName, SpringTemplateEngine springTemplateEngine, DocumentHelper documentHelper) {
        DocumentStatusDetails documentStatusDetails;
        String pdfDocumentOrDocumentStatus = getDocumentBase64String(proposalDetails, proposalFormDetailsContext,
                proposalFormString, templateName, springTemplateEngine, documentHelper);

        if (AppConstants.FAILED.equalsIgnoreCase(pdfDocumentOrDocumentStatus)) {
            // update the document generation failure status in DB
            logger.info("{} Document generation is failed so updating in DB for transactionId {}", proposalFormString, proposalDetails.getTransactionId());
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DOCUMENT_GENERATION_FAILED, AppConstants.PROPOSAL_FORM_DOCUMENT);
        } else {
            // upload the generated document in S3
            logger.info("{} Document successfully generated for transactionId {}", proposalFormString, proposalDetails.getTransactionId());
            documentStatusDetails = documentHelper.saveGeneratedDocumentToS3(proposalDetails, pdfDocumentOrDocumentStatus, 0,
                    AppConstants.PROPOSAL_FORM_DOCUMENTID, AppConstants.PROPOSAL_FORM_DOCUMENT);
        }
        return documentStatusDetails;
    }

    public void setDataForProposalForm(ProposalDetails proposalDetails, Map<String, Object> completeDetails, DocumentHelper documentHelper) {
        String policyNumber = Utility.nullSafe(proposalDetails.getApplicationDetails().getPolicyNumber());
        setExistingCustomerAndPreviousPolicyNumberData(proposalDetails, completeDetails);
        String channel = Utility.nullSafe(proposalDetails.getChannelDetails().getChannel());
        String gocaBrokerCode = Utility.nullSafe(proposalDetails.getSourcingDetails().getAgentCode());
        String goGreen = Utility.nullSafe(proposalDetails.getApplicationDetails().getGoGreen());
        String proposerImageURL = AppConstants.DUMMY_BLANK_IMAGE_PATH;
        String isMWPA = org.apache.commons.lang3.StringUtils.EMPTY;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) &&
                Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus()) &&
                Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments())) {
            String imageType = Utility.imageType(proposalDetails);
            proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
                    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
        }
        if (Objects.nonNull(proposalDetails.getNomineeDetails()) &&
                Objects.nonNull(proposalDetails.getNomineeDetails().getPartyDetails())) {
            isMWPA = proposalDetails.getNomineeDetails().getPartyDetails().get(0).getObjectiveOfInsurance();
        }
        setAnnutyPlanDetailes(proposalDetails, completeDetails);
          if(!StringUtils.hasLength((String) completeDetails.get("payorImage"))) {
            completeDetails.put("payorImage", AppConstants.DUMMY_BLANK_IMAGE_PATH);
        }
        completeDetails.put("payorImageNA", "Image not available");
        completeDetails.put("proposalNumber", policyNumber);
        completeDetails.put("gocaBrokerCode", gocaBrokerCode);
        completeDetails.put("comboProposalNumber", "N/A");
        completeDetails.put("channel", channel);
        completeDetails.put("affinityCustomer", "N/A");
        completeDetails.put("proposerImage", proposerImageURL);
        completeDetails.put("isCovidQuestionnaire", isCovidQuestionnaireDocument(proposalDetails));
        completeDetails.put(AppConstants.IS_NOT_YBL_PROPOSAL, !Utility.isYBLProposal(proposalDetails));
        completeDetails.put("footer", !Utility.isYBLProposal(proposalDetails)? "footer_updated.png": "");

        completeDetails.put("isDiabeticQuestionnaire", isParentQuestionTrueOrFalse(proposalDetails,
                AppConstants.DIABETIC_QUESTIONNAIRE));
        completeDetails.put("isHighBloodPressureQuestionnaire", isParentQuestionTrueOrFalse(proposalDetails,
                AppConstants.HIGH_BLOOD_PRESSURE_QUESTIONNAIRE));
        completeDetails.put("isRespiratoryDisorderQuestionnaire", isParentQuestionTrueOrFalse(proposalDetails,
                AppConstants.RESPIRATORY_DISORDER_QUESTIONNAIRE));
        completeDetails.put("goGreen", goGreen);
        completeDetails.put("isMWPA", isMWPA);
    }

    private void setAnnutyPlanDetailes(ProposalDetails proposalDetails, Map<String, Object> completeDetails) {
        if(Utility.isProductBothSGPPOrSGPPJL(proposalDetails)) {
            setIsNPSCustomerAndPranNumber(proposalDetails, completeDetails);
        }
    }

    private void setIsNPSCustomerAndPranNumber(ProposalDetails proposalDetails, Map<String, Object> completeDetails) {
        if(Objects.nonNull(proposalDetails.getPartyInformation())
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0))
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())){

            String pranNumber = Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails().getPranNumber()) ?
                    proposalDetails.getPartyInformation().get(0).getBasicDetails().getPranNumber() : "" ;
            String isNPSCustomer = Utility.convertToYesOrNo(
                    proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer());
            completeDetails.put("isNPSCustomer",isNPSCustomer);
            completeDetails.put("pranNumber",pranNumber);
        }
    }

    public void setExistingCustomerAndPreviousPolicyNumberData(ProposalDetails proposalDetails, Map<String, Object> completeDetails) {
        String existingCustomer = "";
        String previousPolicyNumber = "";
        String isExistingCustomerComboSale = "";
        String existingCustomerPolicyNoComboSale = "";
        if (Objects.nonNull(proposalDetails.getPartyInformation())
                && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
            if (AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())) {
                existingCustomer = "NO";
                isExistingCustomerComboSale = Utility.convertToYesOrNo(StringUtils.isEmpty(proposalDetails.getPartyInformation().get(0).getBasicDetails()
                        .getIsExistingCustomer()) ? "02" : proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsExistingCustomer());
                existingCustomerPolicyNoComboSale= Utility.nullSafe(proposalDetails.getPartyInformation().get(0).getBasicDetails().getExistingCustomerPolicyNo());
            } else {
                existingCustomer = Utility.convertToYesOrNo(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsExistingCustomer());
                isExistingCustomerComboSale = Utility.convertToYesOrNo(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsExistingCustomerComboSale());
                previousPolicyNumber = Utility.nullSafe(proposalDetails.getPartyInformation().get(0).getBasicDetails().getExistingCustomerPolicyNo());
                existingCustomerPolicyNoComboSale = Utility.nullSafe(proposalDetails.getPartyInformation().get(0).getBasicDetails().getExistingCustomerPolicyNoComboSale());
            }
        }
        if ("YES".equalsIgnoreCase(existingCustomer)){
            completeDetails.put("existingCustomerComboSale","YES");
        }else {
            completeDetails.put("existingCustomerComboSale", isExistingCustomerComboSale);
        }
        completeDetails.put("existingPolicyNumberComboSale",existingCustomerPolicyNoComboSale);
        completeDetails.put("existingCustomer", existingCustomer);
        completeDetails.put("existingPolicyNumber", previousPolicyNumber);
    }

    public boolean isCovidQuestionnaireDocument(ProposalDetails proposalDetails) {

        if (Utility.isNewCovidQuestionApplicable(proposalDetails)) {
            return checkCovidFlag(proposalDetails);
        }
        List<String> valueList = new ArrayList<>();
        if (Objects.nonNull(proposalDetails.getLifeStyleDetails())
            && !proposalDetails.getLifeStyleDetails().isEmpty()) {
            proposalDetails.getLifeStyleDetails().stream().filter(lifeStyleDetails -> !PAYOR.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
                .forEach(lifeStyleDetails -> {
                    checkForCovidQuestionnaire(valueList, lifeStyleDetails);
                    }
                );
        }

        List<String> nationality = proposalDetails.getPartyInformation()
                .stream()
                .filter(partyInformation -> !partyInformation.getPartyType().equalsIgnoreCase(PAYOR))
                .map(PartyInformation::getBasicDetails)
                .map(BasicDetails::getNationalityDetails)
                .filter(Objects::nonNull)
                .map(NationalityDetails::getNationality)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        valueList.add(nationality.contains(AppConstants.INDIAN_NATIONALITY) ? AppConstants.NO : AppConstants.YES);
        return valueList.contains(AppConstants.YES);
    }

    private boolean checkCovidFlag(ProposalDetails proposalDetails) {
        List<Boolean> covidFlagList = new ArrayList<>();
        Optional.ofNullable(proposalDetails.getLifeStyleDetails()).ifPresent(
            lifeStyleDetailsList ->lifeStyleDetailsList.stream().filter(lifeStyleDetails -> !PAYOR.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
                .forEach(
                    lifeStyleDetails -> {
                        if (Utility.isNeoTrue(lifeStyleDetails.getCovidQuestionnaire().getEverTestedPositiveForCovid())){
                            covidFlagList.add(true);
                        }
                    }
                )
        );
        return covidFlagList.contains(true);
    }

    private void checkForCovidQuestionnaire(List<String> valueList, LifeStyleDetails lifeStyleDetails) {
        if (Objects.nonNull(lifeStyleDetails.getCovidQuestionnaire())) {
            valueList.add(Utility.convertToYesOrNo(lifeStyleDetails.getCovidQuestionnaire().getPlanToTravelOverseas()));
            valueList.add(Utility.convertToYesOrNo(lifeStyleDetails.getCovidQuestionnaire().getIsHealthWorker()));
            valueList.add(Utility.convertToYesOrNo(lifeStyleDetails.getCovidQuestionnaire().getAreYouCovidWarrior()));
        }
    }

    public boolean isParentQuestionTrueOrFalse(ProposalDetails proposalDetails, String questionnaireType) {
        List<Boolean> valueList = new ArrayList<>();
        if (Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getLifeStyleDetails())
                && !proposalDetails.getLifeStyleDetails().isEmpty()) {

            List<DiagnosedOrTreatedDetails> diagnosedOrTreatedDetails = proposalDetails
                .getLifeStyleDetails()
                .stream()
                .filter(lifeStyleDetails -> !PAYOR.equalsIgnoreCase(lifeStyleDetails.getPartyType()))
                .map(LifeStyleDetails::getHealth)
                .filter(Objects::nonNull)
                .map(Health::getDiagnosedOrTreatedDetails)
                .collect(Collectors.toList());
            diagnosedOrTreatedDetails.forEach(
                diagnosedOrTreatedDetails1 -> valueList
                    .add(checkDiagnosedOrTreatedDetails(questionnaireType,
                        diagnosedOrTreatedDetails1,proposalDetails))
            );
            return valueList.contains(true);
        }
        return false;
    }

    public Boolean checkDiagnosedOrTreatedDetails(String questionnaireType, DiagnosedOrTreatedDetails diagnosedOrTreatedDetails,ProposalDetails proposalDetails) {
        if (Objects.nonNull(diagnosedOrTreatedDetails)) {
            if (AppConstants.DIABETIC_QUESTIONNAIRE.equalsIgnoreCase(questionnaireType)
                    && Objects.nonNull(diagnosedOrTreatedDetails.getHormonal())) {
                if(AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())){
                       return AppConstants.YES.equals(Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getHormonal().getDiabetes()))
                               || AppConstants.YES.equals(Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getHormonal().getHighBloodSugar()));
                }
                return AppConstants.YES.equals(Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getHormonal().getDiabetes()));
            }

            if (AppConstants.HIGH_BLOOD_PRESSURE_QUESTIONNAIRE.equalsIgnoreCase(questionnaireType)
                    && Objects.nonNull(diagnosedOrTreatedDetails.getCardio())) {
                return AppConstants.YES.equals(Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getCardio().getHighBloodPressure()));
            }

            if (AppConstants.RESPIRATORY_DISORDER_QUESTIONNAIRE.equals(questionnaireType)
                    && Objects.nonNull(diagnosedOrTreatedDetails.getRespiratory())) {
                return AppConstants.YES.equals(Utility.convertToYesOrNo(diagnosedOrTreatedDetails.getRespiratory().getAsthma()));
            }
        }
        return false;
    }
}
