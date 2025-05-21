package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.*;


/**
 * @author manish on 22/05/20
 */
@Component("AWPProposalForm")
@EnableAsync
public class AWPProposalForm extends NeoBaseProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(AWPProposalForm.class);

    @Autowired
    protected PersonalDetailsMapper personalDetailsMapper;
    @Autowired
    protected CoverageDetailsMapper coverageDetailsMapper;
    @Autowired
    protected LifeInsuredDetailsMapper lifeInsuredDetailsMapper;
    @Autowired
    protected ProposalMedicalDetailsMapper proposalMedicalDetailsMapper;
    @Autowired
    protected DeclarationMapper declarationMapper;
    @Autowired
    protected ProposalCkycMapper proposalCkycMapper;
    @Autowired
    protected ProposalFormAnnexureMapper proposalFormAnnexureMapper;
    @Autowired
    protected CovidQuestionnaireMapper covidQuestionnaireMapper;
    @Autowired
    protected NeoAdditionalQuestionnaireMapper neoAdditionalQuestionnaireMapper;
    @Autowired
    protected PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

    protected String personalDetailsTemplate = "neo\\awp\\personalDetails";
    protected String coverageDetailsTemplate = "neo\\awp\\coverageDetails";
    protected String insuredDetailsTemplate = "neo\\awp\\lifeInsuredDetails";
    protected String insuredDetailsTemplateForm2 = "neo\\awp\\lifeInsuredDetailsForm2";
    protected String medicalDetailsTemplate = "neo\\awp\\medicalDetails";
    protected String medicalDetailsTemplateForm2 = "neo\\awp\\medicalDetailsForm2";
    protected String medicalDetailsTemplateAxisD2C = "neo\\pos\\medicalDetailsAxisD2C";
    protected String declarationDetailsTemplate = "neo\\awp\\pfDeclaration";
    protected String ckycDetailsTemplate = "neo\\awp\\ckyc";
    protected String annexureDetailsTemplate = "neo\\awp\\proposalFormAnnexure";
    protected String annexureDetailsTemplateForm2 = "neo\\awp\\proposalFormAnnexureForm2";
    protected String covidQuestionnaireAnnexureTemplate = "neo\\covidQuestionnaire";
    protected String covidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\covidQuestionnaire";
    protected String covidQuestionnaireFalseAnnexureTemplate = "neo\\covidQuestionnaireFalse";
    protected String newCovidQuestionnaireAnnexureTemplate = "neo\\newCovidQuestionnaire";
    protected String newCovidQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\newCovidQuestionnaire";
    protected String diabeticQuestionnaireAnnexureTemplate = "neo\\diabeticQuestionnaire";
    protected String diabeticQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\diabeticQuestionnaire";
    protected String highBloodPressureQuestionnaireAnnexureTemplate = "neo\\highBloodPressureQuestionnaire";
    protected String highBloodPressureQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\highBloodPressureQuestionnaire";
    protected String respiratoryDisorderQuestionnaireAnnexureTemplate = "neo\\respiratoryDisorderQuestionnaire";
    protected String respiratoryDisorderQuestionnaireAnnexureTemplateForm2 = "neo\\swpjl\\respiratoryDisorderQuestionnaire";
    protected String proposalFormTemplate = "neo\\awp\\proposalForm";
    protected String proposalFormString = "AWP";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        if(Utility.isProductSWPJL(proposalDetails)){
            interChangeProposerAndLifeInsured(proposalDetails);
        }
        else if(Utility.isApplicationIsForm2(proposalDetails)){
                configureForm2Templates(proposalDetails);
            changeProposerAndLifeinsuredForForm2(proposalDetails);
        }
        else if(Utility.isSSPJLProduct(proposalDetails)){
            interChangeLifeinsuredForSSPJLProposerAndLifeInsured(proposalDetails);
        }
        DocumentStatusDetails documentStatusDetails;
        long requestedTime = System.currentTimeMillis();

        try {
            logger.info("{} Proposal Form document generation is initiated for transactionId {} and at applicationStage {}", proposalFormString, proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getStage());

            //NEORW-: NR-352 retry logic for proposal form generation if data from crm did not come yet
            invokeRetryLogicForProposalForm(proposalDetails);

            Context proposalFormDetailsContext = getContextWithTemplateData(proposalDetails);

            documentStatusDetails = processDocumentGeneration(proposalDetails, proposalFormDetailsContext, proposalFormString,
                    proposalFormTemplate, springTemplateEngine, documentHelper);

        } catch (UserHandledException ex) {
            logger.error("Proposal Form document generation failed:", ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DATA_MISSING_FAILURE, AppConstants.PROPOSAL_FORM_DOCUMENT);
        } catch (Exception ex) {
            logger.error("Proposal Form Document generation failed:", ex);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.TECHNICAL_FAILURE, AppConstants.PROPOSAL_FORM_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("{} Proposal Form document for transactionId {} took {} milliseconds ", proposalFormString, proposalDetails.getTransactionId(), processedTime);
    }

    private void configureForm2Templates(ProposalDetails proposalDetails) {
        insuredDetailsTemplate = insuredDetailsTemplateForm2;
        medicalDetailsTemplate = medicalDetailsTemplateForm2;
        annexureDetailsTemplate = annexureDetailsTemplateForm2;
        covidQuestionnaireAnnexureTemplate = covidQuestionnaireAnnexureTemplateForm2;
        newCovidQuestionnaireAnnexureTemplate = newCovidQuestionnaireAnnexureTemplateForm2;
        diabeticQuestionnaireAnnexureTemplate = diabeticQuestionnaireAnnexureTemplateForm2;
        highBloodPressureQuestionnaireAnnexureTemplate = highBloodPressureQuestionnaireAnnexureTemplateForm2;
        respiratoryDisorderQuestionnaireAnnexureTemplate = respiratoryDisorderQuestionnaireAnnexureTemplateForm2;
        }

    private Context getContextWithTemplateData(ProposalDetails proposalDetails) throws UserHandledException {
        Context personalDetailsContext = personalDetailsMapper.setDataForPersonalDetails(proposalDetails);
        Context coverageDetailsContext = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
        Context lifeInsuredDetailsContext = lifeInsuredDetailsMapper.setDataForLifeInsured(proposalDetails);
        Context medicalDetailsContext = proposalMedicalDetailsMapper.setMedicalData(proposalDetails);
        Context declarationDetailsContext = declarationMapper.setDataOfDeclaration(proposalDetails);
        Context ckycDetailsContext = proposalCkycMapper.setDataForCkycDetails(proposalDetails);
        Context proposalAnnexureDetailsContext = proposalFormAnnexureMapper.setDataForProposalFormAnnexure(proposalDetails);
        Context covidQuestionnaireAnnexureContext = covidQuestionnaireMapper.setDataForCovidQuestionnaire(proposalDetails);
        Context newCovidQuestionnaireAnnexureContext = covidQuestionnaireMapper.setDataForCovidQuestionnaire(proposalDetails);
        Context covidQuestionnaireDefaultContext =  covidQuestionnaireMapper.decideDefaultTemplate(proposalDetails);
        Context diabeticQuestionnaireAnnexureContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.DIABETIC_QUESTIONNAIRE);
        Context highBloodPressureQuestionnaireAnnexureContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.HIGH_BLOOD_PRESSURE_QUESTIONNAIRE);
        Context respiratoryDisorderQuestionnaireAnnexureContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.RESPIRATORY_DISORDER_QUESTIONNAIRE);
        Context pphiNomineeDetailsContext = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

        String processedHtmlPersonalDetails = springTemplateEngine.process(personalDetailsTemplate, personalDetailsContext);
        String processedHtmlCoverageDetails = springTemplateEngine.process(coverageDetailsTemplate, coverageDetailsContext);
        String processedHtmlInsuredDetails = springTemplateEngine.process(insuredDetailsTemplate, lifeInsuredDetailsContext);
        String processedHtmlMedicalDetails = "";
        if (Utility.isAxisJourney(proposalDetails)) {
            processedHtmlMedicalDetails = springTemplateEngine.process(medicalDetailsTemplateAxisD2C, medicalDetailsContext);
        }  else {
            processedHtmlMedicalDetails = springTemplateEngine.process(medicalDetailsTemplate, medicalDetailsContext);
        }
        String processedHtmlDeclarationDetails = springTemplateEngine.process(declarationDetailsTemplate, declarationDetailsContext);
        String processedHtmlCkycDetails = springTemplateEngine.process(ckycDetailsTemplate, ckycDetailsContext);
        if (proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && (Utility.isProductSWPJL(proposalDetails))) {
            annexureDetailsTemplate = "neo\\aggregator\\proposalFormAnnexureswpjl";
        } else if (proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && Utility.isApplicationIsForm2(proposalDetails)) {
            annexureDetailsTemplate = "neo\\aggregator\\proposalFormAnnexureForm2";
        } else if (proposalDetails.getChannelDetails().getChannel().equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)
                && Utility.isSSPJLProduct(proposalDetails)) {
            annexureDetailsTemplate = "neo\\aggregator\\proposalFormAnnexureSSPJL";
        } else if (proposalDetails.getChannelDetails().getChannel()
                .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && !Utility.checkInfluencerChannelCode(proposalDetails.getChannelDetails().getInfluencerChannel()) && (Utility.isSSPProduct(proposalDetails) || Utility.isProductSTEP(proposalDetails))) {
            annexureDetailsTemplate = "neo\\aggregator\\sspProposalFormAnnexure";
        } else if (proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && !Utility.checkInfluencerChannelCode(proposalDetails.getChannelDetails().getInfluencerChannel())) {
            annexureDetailsTemplate = "neo\\aggregator\\proposalFormAnnexure";
        } else if (proposalDetails.getChannelDetails().getChannel()
                .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR) && Utility.checkInfluencerChannelCode(proposalDetails.getChannelDetails().getInfluencerChannel()) && (Utility.isSSPProduct(proposalDetails) || Utility.isProductSTEP(proposalDetails))) {
            annexureDetailsTemplate = "neo\\step\\proposalFormAnnexure";
        }
        String processedHTMLCovidQuestionnaireAnnexure = "";
        if (Utility.isCovidAnnexureApplicable(proposalDetails)) {
            if (!Utility.isNewCovidQuestionApplicable(proposalDetails)) {
                processedHTMLCovidQuestionnaireAnnexure = springTemplateEngine.process(covidQuestionnaireAnnexureTemplate, covidQuestionnaireAnnexureContext);
            } else {
                processedHTMLCovidQuestionnaireAnnexure = springTemplateEngine.process(newCovidQuestionnaireAnnexureTemplate, newCovidQuestionnaireAnnexureContext);
            }
        }
        String processedHtmlAnnexureDetails = springTemplateEngine.process(annexureDetailsTemplate, proposalAnnexureDetailsContext);
        String processedHTMLCovidFalseQuestionnaireAnnexure = "";
        if (Utility.isCovidAnnexureApplicable(proposalDetails)) {
             processedHTMLCovidFalseQuestionnaireAnnexure = springTemplateEngine.process(covidQuestionnaireFalseAnnexureTemplate, covidQuestionnaireDefaultContext);
        }
        String processedHTMLDiabeticQuestionnaireAnnexure="";
        if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewDiabeticQuestion())) {
           processedHTMLDiabeticQuestionnaireAnnexure = springTemplateEngine.process(diabeticQuestionnaireAnnexureTemplate,
                    diabeticQuestionnaireAnnexureContext);
        }
        String processedHTMLHighBloodPressureQuestionnaireAnnexure="";
        if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewHypertensionQuestion())) {
            processedHTMLHighBloodPressureQuestionnaireAnnexure = springTemplateEngine.process(highBloodPressureQuestionnaireAnnexureTemplate,
                    highBloodPressureQuestionnaireAnnexureContext);
        }
        String processedHTMLRespiratoryDisorderQuestionnaireAnnexure="";
        if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewRespiratoryQuestion())) {
            processedHTMLRespiratoryDisorderQuestionnaireAnnexure = springTemplateEngine.process(respiratoryDisorderQuestionnaireAnnexureTemplate,
                    respiratoryDisorderQuestionnaireAnnexureContext);
        }
        String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphiNomineeDetailsContext);

        Map<String, Object> completeDetails = new HashMap<>();
        Context proposalFormDetailsContext = new Context();
        completeDetails.put("pdfVersion", declarationDetailsContext.getVariable("pdfVersion"));
        completeDetails.put("isNotYBLProposal", declarationDetailsContext.getVariable("isNotYBLProposal"));
        completeDetails.put("personalDetails", processedHtmlPersonalDetails);
        completeDetails.put("coverageDetails", processedHtmlCoverageDetails);
        completeDetails.put("lifeInsuredDetails", processedHtmlInsuredDetails);
        completeDetails.put("medicalDetails", processedHtmlMedicalDetails);
        completeDetails.put("declarationDetails", processedHtmlDeclarationDetails);
        completeDetails.put("ckycDetails", processedHtmlCkycDetails);
        completeDetails.put("proposalFormAnnexure", processedHtmlAnnexureDetails);
        completeDetails.put("covidQuestionnaireAnnexure", processedHTMLCovidQuestionnaireAnnexure);
        completeDetails.put("covidQuestionnaireFalseAnnexure",processedHTMLCovidFalseQuestionnaireAnnexure);
        completeDetails.put("diabeticQuestionnaireAnnexure", processedHTMLDiabeticQuestionnaireAnnexure);
        completeDetails.put("highBloodPressureQuestionnaireAnnexure", processedHTMLHighBloodPressureQuestionnaireAnnexure);
        completeDetails.put("respiratoryDisorderQuestionnaireAnnexure", processedHTMLRespiratoryDisorderQuestionnaireAnnexure);
        completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
        setDataForProposalForm(proposalDetails, completeDetails, documentHelper);
        proposalFormDetailsContext.setVariables(completeDetails);
        return proposalFormDetailsContext;
    }

    protected String getProcessedTemplate(String template,Context context){
        return springTemplateEngine.process(template,context);
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        try {
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
