package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author manish on 22/05/20
 */
@Component("neoYblProposalForm")
public class NeoYBLProposalForm extends NeoBaseProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoYBLProposalForm.class);

    @Autowired
    private PersonalDetailsMapper personalDetailsMapper;
    @Autowired
    private CoverageDetailsMapper coverageDetailsMapper;
    @Autowired
    private LifeInsuredDetailsMapper lifeInsuredDetailsMapper;
    @Autowired
    private DeclarationMapper declarationMapper;
    @Autowired
    private ProposalMedicalDetailsMapper proposalMedicalDetailsMapper;
    @Autowired
    private PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

    protected String templateName = "neo\\ybl\\proposalForm";
    protected String proposalFormString = "YBL";

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {

        DocumentStatusDetails documentStatusDetails;
        long requestedTime = System.currentTimeMillis();

        try {
            logger.info("YBL Proposal Form document generation is initiated for transactionId {} and at applicationStage {}", proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getStage());

            Context proposalFormDetailsContext = getContextWithTemplateData(proposalDetails);

            documentStatusDetails = processDocumentGeneration(proposalDetails, proposalFormDetailsContext, proposalFormString, templateName,
                    springTemplateEngine, documentHelper);

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
        logger.info("YBL Proposal Form document for transactionId {} took {} milliseconds ", proposalDetails.getTransactionId(), processedTime);
    }

    private Context getContextWithTemplateData(ProposalDetails proposalDetails) throws UserHandledException {
        Context personalDetailsContext = personalDetailsMapper.setDataForPersonalDetails(proposalDetails);
        Context coverageDetailsContext = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
        Context lifeInsuredDetailsContext = lifeInsuredDetailsMapper.setDataForLifeInsured(proposalDetails);
        Context declarationDetailsContext = declarationMapper.setDataOfDeclaration(proposalDetails);
        Context medicalDetailsContext = proposalMedicalDetailsMapper.setMedicalData(proposalDetails);
        Context nonMandatoryDetailsContext = setDataForNonMandatoryQuestions(personalDetailsContext, proposalDetails);
        Context pphINomineeDetailsCtx = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

        String processedHtmlPersonalDetails = springTemplateEngine.process("neo\\ybl\\personalDetails", personalDetailsContext);
        String processedHtmlCoverageDetails = springTemplateEngine.process("neo\\ybl\\coverageDetails", coverageDetailsContext);
        String processedHtmlInsuredDetails = springTemplateEngine.process("neo\\ybl\\lifeInsuredDetails", lifeInsuredDetailsContext);
        String processedHtmlMedicalDetails = springTemplateEngine.process("neo\\ybl\\medicalDetails", medicalDetailsContext);
        String processedHtmlDeclarationDetails = springTemplateEngine.process("neo\\ybl\\pfDeclaration", declarationDetailsContext);
        String processedHtmlNonMandatoryDetails = springTemplateEngine.process("neo\\ybl\\nonMandatoryQuestions", nonMandatoryDetailsContext);
        String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphINomineeDetailsCtx);

        Map<String, Object> completeDetails = new HashMap<>();
        Context proposalFormDetailsContext = new Context();

        completeDetails.put("personalDetails", processedHtmlPersonalDetails);
        completeDetails.put("coverageDetails", processedHtmlCoverageDetails);
        completeDetails.put("lifeInsuredDetails", processedHtmlInsuredDetails);
        completeDetails.put("medicalDetails", processedHtmlMedicalDetails);
        completeDetails.put("nonMandatoryQuestions", processedHtmlNonMandatoryDetails);
        completeDetails.put("declarationDetails", processedHtmlDeclarationDetails);
        completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
        setDataForProposalForm(proposalDetails, completeDetails, documentHelper);
        completeDetails.put("nonMandatoryQuestionFlag", proposalDetails.isnMQuestionsLinkExpired());
        proposalFormDetailsContext.setVariables(completeDetails);
        return proposalFormDetailsContext;
    }

    private Context setDataForNonMandatoryQuestions(Context personalDetailsContext, ProposalDetails proposalDetails) {

        logger.info("Mapping nonMandatoryQuestions details for transactionId {}", proposalDetails.getTransactionId());
        Map<String, Object> dataVariables = new HashMap<>();
        Context nonMandatoryDetailsContext = new Context();

        String spouseOccupation = "";
        String spouseIncome = "";
        String spouseInsuranceCover = "";
        String policyNumberWithMLI = "";

        if (Objects.nonNull(proposalDetails.getPartyInformation())
                && !proposalDetails.getPartyInformation().isEmpty()
                && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {

            BasicDetails basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
            policyNumberWithMLI = basicDetails.getExistingCustomerPolicyNo();

            if (Objects.nonNull(basicDetails.getMarriageDetails())) {
                spouseOccupation = basicDetails.getMarriageDetails().getSpouseOccupation();
                spouseIncome = basicDetails.getMarriageDetails().getSpouseAnnualIncome();
                spouseInsuranceCover = basicDetails.getMarriageDetails().getTotalInsuranceCoverOnSpouse();
            }
        }

        dataVariables.put("jobTitle", personalDetailsContext.getVariable("occupation"));
        dataVariables.put("employerName", personalDetailsContext.getVariable("nameOfEmployer"));
        dataVariables.put("nomineeDetails", personalDetailsContext.getVariable("nomineeDetailsCount"));
        dataVariables.put("spouseOccupation", spouseOccupation);
        dataVariables.put("spouseIncome", spouseIncome);
        dataVariables.put("spouseInsurance", spouseInsuranceCover);
        dataVariables.put("policyNumberWithMLI", policyNumberWithMLI);

        nonMandatoryDetailsContext.setVariables(dataVariables);
        logger.info("Mapping nonMandatoryQuestions details is completed for transactionId {} ", proposalDetails.getTransactionId());
        return nonMandatoryDetailsContext;
    }

    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        try {
            Context context = getContextWithTemplateData(proposalDetails);
            return getDocumentBase64String(proposalDetails, context, proposalFormString, templateName,
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
