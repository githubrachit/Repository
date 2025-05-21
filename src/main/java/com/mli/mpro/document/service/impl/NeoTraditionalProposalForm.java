package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component("neoTradProposalForm")
public class NeoTraditionalProposalForm extends NeoBaseProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoTraditionalProposalForm.class);
    public static final String PAYOR = "payor";
    boolean isNeoOrAggregator = false;
    @Autowired
    private PersonalDetailsMapper personDetailsMapper;

    @Autowired
    private DeclarationMapper declarationMapper;

    @Autowired
    private CoverageDetailsMapper coverageDetailsMapper;

    @Autowired
    private LifeInsuredDetailsMapper lifeInsuredDetailsMapper;

    @Autowired
    private MedicalDetailsMapper medicalDetailsMapper;

    @Autowired
    private ProposalCkycMapper proposalCkycMapper;

    @Autowired
    private ProposalFormAnnexureMapper proposalFormAnnexureMapper;

    @Autowired
    private ProposalMedicalDetailsMapper proposalMedicalDetailsMapper;

    @Autowired
    private CovidQuestionnaireMapper covidQuestionnaireMapper;

    @Autowired
    private NeoAdditionalQuestionnaireMapper neoAdditionalQuestionnaireMapper;

    @Autowired
    private PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

    protected String proposalFormTemplate = "neo\\stp\\Trad_ProposalForm";
    protected String proposalFormString = "STP";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {

        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();

        try {
            logger.info("Proposal Form document generation is initiated for transactionId {} and at applicationStage {} with Neo implementaion", transactionId,
                    proposalDetails.getApplicationDetails().getStage());
            isNeoOrAggregator =true;

            //NEORW-: NR-352 retry logic for proposal form generation if data from crm did not come yet
            invokeRetryLogicForProposalForm(proposalDetails);

            Context proposalFormDetailsCtx = getContextWithTemplateData(proposalDetails);

            documentStatusDetails = processDocumentGeneration(proposalDetails, proposalFormDetailsCtx, proposalFormString, proposalFormTemplate,
                    springTemplateEngine, documentHelper);

        } catch (UserHandledException ex) {
            logger.error("Proposal Form document generation failed:", ex);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.DATA_MISSING_FAILURE,AppConstants.PROPOSAL_FORM_DOCUMENT);
        } catch (Exception ex) {
            logger.error("Proposal Form Document generation failed:", ex);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.TECHNICAL_FAILURE,AppConstants.PROPOSAL_FORM_DOCUMENT);
        }

        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Proposal Form document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

    private Context getContextWithTemplateData(ProposalDetails proposalDetails) throws UserHandledException {

        String processedHtmlPersonalDetails;
        String processedHTMLMedicalDetails;
        String processedHTMLCoverageHTML;
        String processedHtmlDeclaration;
        String processedHTMLLifeInsured;

        if (Utility.isChannelNeoOrAggregator(proposalDetails) && Utility.isApplicationIsForm2(proposalDetails)) {
            changeProposerAndLifeinsuredForForm2(proposalDetails);
        }
        Context personalDetailsContext = personDetailsMapper.setDataForPersonalDetails(proposalDetails);
        Context declarationContext = declarationMapper.setDataOfDeclaration(proposalDetails);
        Context coverageDetailsCtx = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
        Context lifeInsuredCtx = lifeInsuredDetailsMapper.setDataForLifeInsured(proposalDetails);
        Context medicalDetailsctx = !isNeoOrAggregator ? medicalDetailsMapper.setMedicalData(proposalDetails) : null;
        Context covidQuestionnaireContext = covidQuestionnaireMapper.setDataForCovidQuestionnaire(proposalDetails);
        Context newCovidQuestionnaireContext = covidQuestionnaireMapper.setDataForCovidQuestionnaire(proposalDetails);
        Context covidQuestionnaireDefaultContext =  covidQuestionnaireMapper.decideDefaultTemplate(proposalDetails);
        Context diabeticQuestionnaireContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.DIABETIC_QUESTIONNAIRE);
        Context highBloodPressureQuestionnaireContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.HIGH_BLOOD_PRESSURE_QUESTIONNAIRE);
        Context respiratoryDisorderQuestionnaireAnnexureContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.RESPIRATORY_DISORDER_QUESTIONNAIRE);
        Context pphINomineeDetailsCtx = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

        Map<String, Object> completeDetails = new HashMap<>();
        Context proposalFormDetailsCtx = new Context();

        //NEORW-173: if incoming request from NEO or Aggregator then neo template will be populate for proposal form
        if (isNeoOrAggregator) {
            boolean isForm2 = Utility.isApplicationIsForm2(proposalDetails);
            processedHtmlPersonalDetails = springTemplateEngine.process("neo\\stp\\PersonalDetails", personalDetailsContext);
            processedHtmlDeclaration = springTemplateEngine.process("neo\\stp\\PFDeclaration", declarationContext);
            processedHTMLCoverageHTML = springTemplateEngine.process("neo\\stp\\CoverageDetails", coverageDetailsCtx);
            processedHTMLLifeInsured = springTemplateEngine.process(setLifeInsuredTemplate(isForm2), lifeInsuredCtx);
            Context neoMedicalDetails = proposalMedicalDetailsMapper.setMedicalData(proposalDetails);
            processedHTMLMedicalDetails = springTemplateEngine.process(setMedicalDetailsTemplate(isForm2), neoMedicalDetails);
            Context ckycDetailsContext = proposalCkycMapper.setDataForCkycDetails(proposalDetails);
            String processedHTMLCkyc = springTemplateEngine.process("neo\\ckyc", ckycDetailsContext);
            Context proposalFormAnnexureDetailsContext = proposalFormAnnexureMapper.setDataForProposalFormAnnexure(proposalDetails);
            String processedHTMLProposalFormAnnexure = springTemplateEngine.process(setProposalFormAnnexureTemplate(isForm2), proposalFormAnnexureDetailsContext);
            String processedHTMLCovidQuestionnaireAnnexure = "";
            if(Utility.isCovidAnnexureApplicable(proposalDetails)) {
                if (!Utility.isNewCovidQuestionApplicable(proposalDetails)) {
                    processedHTMLCovidQuestionnaireAnnexure = springTemplateEngine.process(setOldCovidTemplates(isForm2), covidQuestionnaireContext);
                } else {
                    processedHTMLCovidQuestionnaireAnnexure = springTemplateEngine.process(setNewCovidTemplate(isForm2), newCovidQuestionnaireContext);
                }
            }

            String processedHTMLCovidFalseQuestionnaireAnnexure = "";
            if (Utility.isCovidAnnexureApplicable(proposalDetails)) {
                processedHTMLCovidFalseQuestionnaireAnnexure = springTemplateEngine.process("neo\\covidQuestionnaireFalse", covidQuestionnaireDefaultContext);
            }
            String processedHTMLDiabeticQuestionnaireAnnexure="";
            if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewDiabeticQuestion())) {
                processedHTMLDiabeticQuestionnaireAnnexure = springTemplateEngine.process(setDiabeticQuestionnaireTemplate(isForm2), diabeticQuestionnaireContext);
            }
            String processedHTMLHighBloodPressureQuestionnaireAnnexure="";
            if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewHypertensionQuestion())) {
                processedHTMLHighBloodPressureQuestionnaireAnnexure = springTemplateEngine.process(setHighBloodPressureTemplate(isForm2), highBloodPressureQuestionnaireContext);
            }
            String processedHTMLRespiratoryDisorderQuestionnaireAnnexure="";
            if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewRespiratoryQuestion())) {
                processedHTMLRespiratoryDisorderQuestionnaireAnnexure = springTemplateEngine.process(setRespiratoryTemplate(isForm2), respiratoryDisorderQuestionnaireAnnexureContext);
            }
            completeDetails.put("ckycDetails", processedHTMLCkyc);
            completeDetails.put("proposalFormAnnexure", processedHTMLProposalFormAnnexure);
            completeDetails.put("covidQuestionnaireAnnexure", processedHTMLCovidQuestionnaireAnnexure);
            completeDetails.put("covidQuestionnaireFalseAnnexure",processedHTMLCovidFalseQuestionnaireAnnexure);
            completeDetails.put("diabeticQuestionnaireAnnexure", processedHTMLDiabeticQuestionnaireAnnexure);
            completeDetails.put("highBloodPressureQuestionnaireAnnexure", processedHTMLHighBloodPressureQuestionnaireAnnexure);
            completeDetails.put("respiratoryDisorderQuestionnaireAnnexure", processedHTMLRespiratoryDisorderQuestionnaireAnnexure);

        } else {
            processedHtmlPersonalDetails = springTemplateEngine.process("PersonalDetails", personalDetailsContext);
            processedHtmlDeclaration = springTemplateEngine.process("PFDeclaration", declarationContext);
            processedHTMLCoverageHTML = springTemplateEngine.process("CoverageDetails", coverageDetailsCtx);
            processedHTMLLifeInsured = springTemplateEngine.process("LifeInsuredDetails", lifeInsuredCtx);
            processedHTMLMedicalDetails = springTemplateEngine.process("MedicalDetails", medicalDetailsctx);
        }

        String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphINomineeDetailsCtx);

        completeDetails.put("personalDetails", processedHtmlPersonalDetails);
        completeDetails.put("declarationDetails", processedHtmlDeclaration);
        completeDetails.put("coverageDetails", processedHTMLCoverageHTML);
        completeDetails.put("lifeInsuredDetails", processedHTMLLifeInsured);
        completeDetails.put("medicalDetails", processedHTMLMedicalDetails);
        completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
        completeDetails = setDataForProposalForm(proposalDetails, completeDetails);
        proposalFormDetailsCtx.setVariables(completeDetails);
        return proposalFormDetailsCtx;
    }

    private static String setRespiratoryTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\respiratoryDisorderQuestionnaire"
            : "neo\\respiratoryDisorderQuestionnaire";
    }

    private static String setHighBloodPressureTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\highBloodPressureQuestionnaire"
            : "neo\\highBloodPressureQuestionnaire";
    }

    private static String setDiabeticQuestionnaireTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\diabeticQuestionnaire" : "neo\\diabeticQuestionnaire";
    }

    private static String setNewCovidTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\newCovidQuestionnaire" : "neo\\newCovidQuestionnaire";
    }

    private static String setOldCovidTemplates(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\covidQuestionnaire" : "neo\\covidQuestionnaire";
    }

    private static String setProposalFormAnnexureTemplate(boolean isForm2) {
        return isForm2 ? "neo\\stp\\proposalFormAnnexureForm2" : "neo\\stp\\proposalFormAnnexure";
    }

    private static String setMedicalDetailsTemplate(boolean isForm2) {
        return isForm2 ? "neo\\stp\\medicalDetailsForm2" : "neo\\stp\\MedicalDetails";
    }

    private static String setLifeInsuredTemplate(boolean isForm2) {
        return isForm2 ? "neo\\stp\\lifeInsuredDetailsForm2" : "neo\\stp\\LifeInsuredDetails";
    }

    private Map<String, Object> setDataForProposalForm(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {

        logger.info("fetching data for proposal form main...");
        String policyNumber = Utility.nullSafe(proposalDetails.getApplicationDetails().getPolicyNumber());
        String purposeOfInsurance = Utility.nullSafe(proposalDetails.getProductDetails().get(0).getNeedOfInsurance());
        String objectiveOfInsurance = Utility.nullSafe(proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance());
        //NEORW: if incoming request from NEO then existingCustomer and previousPolicyNumber will be fetched from basic Details
        if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getPartyInformation())
                && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
            setExistingCustomerAndPreviousPolicyNumberData(proposalDetails, dataVariables);
        } else {
            String exitingCustomer = (proposalDetails.getUnderwritingServiceDetails().getDedupeDetails() != null
                    && org.apache.commons.lang3.StringUtils.isNotBlank(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber())) ? "YES" : "NO";
            String previousPolicyNumber = "YES".equalsIgnoreCase(exitingCustomer)
                    ? "YES-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";
            dataVariables.put("existingCustomer", exitingCustomer);
            dataVariables.put("existingPolicyNumber", previousPolicyNumber);
        }
        String channel = proposalDetails.getChannelDetails().getChannel();
        String customerId = !StringUtils.isEmpty(proposalDetails.getBancaDetails()) ? proposalDetails.getBancaDetails().getCustomerId() : "";
        String productSolution = (!StringUtils.isEmpty(proposalDetails.getSalesStoriesProductDetails())
                && "YES".equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) ? "YES" : "N/A";
        // added null check for sourcingDetails object
        String gocaBrokerCode = "";
        if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getSourcingDetails())) {
            gocaBrokerCode = Utility.nullSafe(proposalDetails.getSourcingDetails().getAgentCode());
        } else {
            gocaBrokerCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
        }
        String payorImageURL = AppConstants.DUMMY_BLANK_IMAGE_PATH;
        // added null check for additionalFlags object
        if (Objects.nonNull(proposalDetails.getAdditionalFlags()) && proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()) {
            dataVariables.put(PAYOR, "YES");
            payorImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), "Payor", channel,
                    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
            dataVariables.put("parameter", PAYOR);
            dataVariables.put(PAYOR, proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO");

        }
        String proposerImageURL = AppConstants.DUMMY_BLANK_IMAGE_PATH;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) &&
                Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus()) &&
                Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments())) {
            String imageType = Utility.imageType(proposalDetails);
            proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
                    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
        }
        if (channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
            gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
        }

        String goGreen = "";
        if (isNeoOrAggregator && Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getApplicationDetails()) && Objects.nonNull(proposalDetails.getApplicationDetails().getGoGreen())) {
            goGreen = proposalDetails.getApplicationDetails().getGoGreen();
        }

        dataVariables.put("payorImage", payorImageURL);
        dataVariables.put("payorImageNA", "Image not available");
        dataVariables.put("proposalNumber", policyNumber);
        dataVariables.put("gocaBrokerCode", gocaBrokerCode);
        dataVariables.put("comboProposalNumber", "N/A");
        dataVariables.put("customerId", customerId);
        dataVariables.put("channel", channel);
        dataVariables.put("purposeOfInsurance", purposeOfInsurance);
        dataVariables.put("objectiveOfInsurance", objectiveOfInsurance);
        dataVariables.put("productSolution", productSolution);
        dataVariables.put("affinityCustomer", "N/A");
        dataVariables.put("proposerImage", proposerImageURL);
        dataVariables.put("isCovidQuestionnaire", isCovidQuestionnaireDocument(proposalDetails));
        dataVariables.put("isDiabeticQuestionnaire", isParentQuestionTrueOrFalse(proposalDetails,
                AppConstants.DIABETIC_QUESTIONNAIRE));
        dataVariables.put("isHighBloodPressureQuestionnaire", isParentQuestionTrueOrFalse(proposalDetails,
                AppConstants.HIGH_BLOOD_PRESSURE_QUESTIONNAIRE));
        dataVariables.put("isRespiratoryDisorderQuestionnaire", isParentQuestionTrueOrFalse(proposalDetails,
                AppConstants.RESPIRATORY_DISORDER_QUESTIONNAIRE));
        dataVariables.put("isNotYBLProposal", !Utility.isYBLProposal(proposalDetails));
        dataVariables.put("footer", !Utility.isYBLProposal(proposalDetails)? "footer_updated.png": "");
        dataVariables.put("goGreen", goGreen);
        return dataVariables;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        try {
            isNeoOrAggregator = true;
            Context context = getContextWithTemplateData(proposalDetails);
            return getDocumentBase64String(proposalDetails, context, proposalFormString, proposalFormTemplate,
                    springTemplateEngine, documentHelper);
        } catch (UserHandledException e) {
            logger.error("Proposal form generation failed for proposal with equote {} transactionId {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e);
            throw new UserHandledException(Collections.singletonList(AppConstants.DATA_MISSING_FAILURE), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Proposal form generation failed for proposal with equote {} and transactionId {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e);
            throw new UserHandledException(Collections.singletonList(AppConstants.TECHNICAL_FAILURE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
