package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
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

/**
 * @author Akash sharma on 12/09/23
 */
@Component("neoSGPPProposalForm")
public class NeoSGPPProsalForm extends NeoBaseProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoSGPPProsalForm.class);

    @Autowired
    private DocumentHelper documentHelperSGPP;
    @Autowired
    private PersonalDetailsMapper personDetailsMapper;
    @Autowired
    private DeclarationMapper declarationMapper;
    @Autowired
    private GLIPCoverageDetailsMapper coverageDetailsMapper;
    @Autowired
    protected ProposalCkycMapper proposalCkycMapper;

    private String proposalFormString = "SGPP";
    private String proposalFormTemplate = "neo\\sgpp\\proposalFormAnnuity";
    private String personalDetailsTemplate = "neo\\sgpp\\personalDetailsAnnuity";
    private String coverageDetailsTemplate = "neo\\sgpp\\coverageDetailsAnnuity";
    private String nomineeDetailsTemplate = "neo\\sgpp\\nomineeDetailsAnnuity";
    private String pfDeclarationTemplate = "neo\\sgpp\\pfDeclarationAnnuity";
    private String ckycDeclarationTemplate = "neo\\awp\\ckyc";


    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails;
        long requestedTime = System.currentTimeMillis();

        try {
            logger.info("{} Proposal Form document generation is initiated for transactionId {} and at applicationStage {}", proposalFormString, proposalDetails.getTransactionId(),
                    proposalDetails.getApplicationDetails().getStage());
            Context proposalFormDetailsContext = getContextWithTemplateData(proposalDetails);
            documentStatusDetails = processDocumentGeneration(proposalDetails, proposalFormDetailsContext, proposalFormString,
                    proposalFormTemplate, springTemplateEngine, documentHelperSGPP);

        } catch (UserHandledException ex) {
            logger.error("Proposal Form document generation failed:", ex);
            documentStatusDetails = documentHelperSGPP.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.DATA_MISSING_FAILURE, AppConstants.PROPOSAL_FORM_DOCUMENT);
        } catch (Exception ex) {
            logger.error("Proposal Form Document generation failed:", ex);
            documentStatusDetails = documentHelperSGPP.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    AppConstants.TECHNICAL_FAILURE, AppConstants.PROPOSAL_FORM_DOCUMENT);
        }
        documentHelperSGPP.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("{} Proposal Form document for transactionId {} took {} milliseconds ", proposalFormString, proposalDetails.getTransactionId(), processedTime);
    }

    private Context getContextWithTemplateData(ProposalDetails proposalDetails) throws UserHandledException {
        Map<String, Object> completeDetails = new HashMap<>();
        Context personalDetailscontext = personDetailsMapper.setDataForPersonalDetails(proposalDetails);
        Context coverageDetailsCtx = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
        Context declarationContext = declarationMapper.setDataOfDeclaration(proposalDetails);
        Context ckycDetailsContext = proposalCkycMapper.setDataForCkycDetails(proposalDetails);
        Object nomineeDetails = personalDetailscontext.getVariable("nomineeDetailsCount");
        Context nomineeDetailsContext = new Context();
        nomineeDetailsContext.setVariable("nomineeDetailsCount", nomineeDetails);
        String processedHtmlPersonalDetails = springTemplateEngine.process(personalDetailsTemplate, personalDetailscontext);
        String processedHtmlCoverageDetails = springTemplateEngine.process(coverageDetailsTemplate, coverageDetailsCtx);
        String processedHTMLNomineeDetails = springTemplateEngine.process(nomineeDetailsTemplate, nomineeDetailsContext);
        String processedHtmlDeclaration = springTemplateEngine.process(pfDeclarationTemplate, declarationContext);
        String processedHtmlCkycDetails = springTemplateEngine.process(ckycDeclarationTemplate,ckycDetailsContext);
        Context proposalFormDetailsContext = new Context();
        completeDetails.put("personalDetails", processedHtmlPersonalDetails);
        completeDetails.put("declarationDetails", processedHtmlDeclaration);
        completeDetails.put("coverageDetails", processedHtmlCoverageDetails);
        completeDetails.put("nomineeDetails", processedHTMLNomineeDetails);
        completeDetails.put("ckycDetails",processedHtmlCkycDetails);
        setDataForProposalForm(proposalDetails, completeDetails, documentHelperSGPP);
        proposalFormDetailsContext.setVariables(completeDetails);
        return proposalFormDetailsContext;
    }
    @Override
    public String getDocumentBase64(ProposalDetails proposalDetails) throws UserHandledException {
        try {
            Context context = getContextWithTemplateData(proposalDetails);
            return getDocumentBase64String(proposalDetails, context, proposalFormString, proposalFormTemplate,
                    springTemplateEngine, documentHelper);
        } catch (UserHandledException e) {
            logger.error("Proposal form generation failed for proposal with equote {} transactionId {} errorMsg {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e.getErrorMessages());
            throw new UserHandledException(Collections.singletonList(AppConstants.DATA_MISSING_FAILURE), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Proposal form generation failed for proposal with equote {} and transactionId {} errorMsg {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e.getMessage());
            throw new UserHandledException(Collections.singletonList(AppConstants.TECHNICAL_FAILURE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
