package com.mli.mpro.document.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mli.mpro.proposal.models.EmploymentDetails;
import com.mli.mpro.proposal.models.PosvDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentDetails;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.ProposalDetails;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Component("cipProposalForm")
@EnableAsync
public class CIPProposalForm implements DocumentGenerationservice {


    private static final Logger logger = LoggerFactory.getLogger(CIPProposalForm.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private PersonalDetailsMapper personDetailsMapper;

    @Autowired
    private DeclarationMapper declarationMapper;

    @Autowired
    private CoverageDetailsMapper coverageDetailsMapper;

    @Autowired
    private LifeInsuredDetailsMapper lifeInsuredDetailsMapper;

    @Autowired
    private CIPMedicalDetailsMapper medicalDetailsMapper;

    @Autowired
    private PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

    @Override
    @Async
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails = null;
        String documentUploadStatus = "";
        long transactionId = proposalDetails.getTransactionId();
        int retryCount = 0;
        long requestedTime = System.currentTimeMillis();
        try {
            Map<String, Object> completeDetails = new HashMap<>();
            Context personalDetailscontext = personDetailsMapper.setDataForPersonalDetails(proposalDetails);
            Context declarationContext = declarationMapper.setDataOfDeclaration(proposalDetails);
            Context coverageDetailsCtx = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
            Context lifeInsuredCtx = lifeInsuredDetailsMapper.setDataForLifeInsured(proposalDetails);
            Context medicalDetailsctx = medicalDetailsMapper.setDataForMedicalDetails(proposalDetails);
            Context pphiNomineeDetailsCtx = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

            if (AppConstants.SELF.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType())
                    || Utility.schemeBCase(proposalDetails.getApplicationDetails().getFormType(), proposalDetails.getApplicationDetails().getSchemeType())) {
                Object nomineeDetails = personalDetailscontext.getVariable("nomineeDetailsCount");
                Context nomineeDetailsctx = new Context();
                nomineeDetailsctx.setVariable("nomineeDetailsCount", nomineeDetails);
                String processedHTMLNomineeDetails = springTemplateEngine.process("CIP_NomineeDetails", nomineeDetailsctx);
                completeDetails.put("nomineeDetails", processedHTMLNomineeDetails);
            }
            completeDetails = setDataForProposalForm(proposalDetails, completeDetails);
            String processedHtmlPersonalDetails = springTemplateEngine.process("CIP_PersonalDetails", personalDetailscontext);
            String processedHtmlDeclaration = springTemplateEngine.process("CIP_Declaration", declarationContext);
            String processedHTMLCoverageHTML = springTemplateEngine.process("CIP_CoverageDetails", coverageDetailsCtx);
            String processedHTMLlifeInsured = springTemplateEngine.process("CIP_LifeInsuredDetails", lifeInsuredCtx);
            String processedHTMLMedicalDetails = springTemplateEngine.process("CIP_MedicalDetails", medicalDetailsctx);
            String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphiNomineeDetailsCtx);
            Context ctx = new Context();
            completeDetails.put("personalDetails", processedHtmlPersonalDetails);
            completeDetails.put("declarationDetails", processedHtmlDeclaration);
            completeDetails.put("coverageDetails", processedHTMLCoverageHTML);
            completeDetails.put("lifeInsuredDetails", processedHTMLlifeInsured);
            completeDetails.put("medicalDetails", processedHTMLMedicalDetails);
            completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
            completeDetails.put("isNotYBLProposal", declarationContext.getVariable("isNotYBLProposal"));
            completeDetails.put("pdfVersion", declarationContext.getVariable("pdfVersion"));
            ctx.setVariables(completeDetails);
            String processedHtml1 = springTemplateEngine.process("Trad_ProposalForm", ctx);
            String encodedString = documentHelper.generatePDFDocument(processedHtml1, 0);

            if (encodedString.equalsIgnoreCase(AppConstants.FAILED)) {
                // update the document generation failure status in db
                logger.info("Document generation is failed so updating in DB for transactionId {}", transactionId);
                documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
                        proposalDetails.getApplicationDetails().getStage());
            } else {
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE, encodedString,
                        AppConstants.PROPOSAL_FORM_DOCUMENT);
                documentStatusDetails = documentHelper.getDocumentStatusDetails(proposalDetails, transactionId, retryCount, documentRequestInfo);
            }
        } catch (UserHandledException ex) {
		logger.error("Proposal Form document generation failed:",ex);
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        } catch (Exception ex) {
		logger.error("Proposal Form Document generation failed:",ex);
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(), proposalDetails.getApplicationDetails().getPolicyNumber(),
                    proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0, AppConstants.PROPOSAL_FORM_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        }

        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Proposal Form document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);
    }


    private Map<String, Object> setDataForProposalForm(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {

        String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
        String purposeOfInsurance = Utility.setDefaultValuePosSeller(proposalDetails);
        String objectiveOfInsurance = proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance();
        String exitingCustomer = (proposalDetails.getUnderwritingServiceDetails().getDedupeDetails() != null
                && !StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber())) ? "YES" : "NO";
        String previousPolicyNumber = "YES".equalsIgnoreCase(exitingCustomer)
                ? "YES-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";
        String channel = proposalDetails.getChannelDetails().getChannel();
        String customerId = !StringUtils.isEmpty(proposalDetails.getBancaDetails()) ? proposalDetails.getBancaDetails().getBancaId() : "";
        String productSolution = (!StringUtils.isEmpty(proposalDetails.getSalesStoriesProductDetails())
                && "YES".equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) ? "YES" : "N/A";
        String gocaBrokerCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
        String payorImageURL = null;
        if (proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()) {
            dataVariables.put(AppConstants.PAYOR, "YES");
            payorImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), AppConstants.PAYOR, channel,
                    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
            dataVariables.put("parameter", AppConstants.PAYOR);
            dataVariables.put("payorImage", payorImageURL);

        }
        String imageType = Utility.imageType(proposalDetails);
        String proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
                proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
        if (channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
            gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
        }
        //FUL2-202646 Go Green PF
        String physicalDocument = Optional.ofNullable(proposalDetails).map(ProposalDetails::getPosvDetails).map(PosvDetails::getGoGreen).isPresent()
                                   ?AppConstants.YES.equalsIgnoreCase(proposalDetails.getPosvDetails().getGoGreen())
                                    ?AppConstants.CAMEL_NO:AppConstants.CAMEL_YES
                                   :AppConstants.CAMEL_YES;
        String nonNriFinalStage = Utility.finalStageNationalityCheck(proposalDetails);
        dataVariables.put(AppConstants.FINAL_STAGE,nonNriFinalStage);
        dataVariables.put(AppConstants.PHYSICAL_POLICY,physicalDocument);
        dataVariables.put(AppConstants.PAYOR, proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO");
        dataVariables.put("payorImageNA", "Image not available");
        dataVariables.put("proposalNumber", policyNumber);
        dataVariables.put("gocaBrokerCode", gocaBrokerCode);
        dataVariables.put("comboProposalNumber", "N/A");
        dataVariables.put("customerId", customerId);
        dataVariables.put("channel", channel);
        dataVariables.put("purposeOfInsurance", purposeOfInsurance);
        dataVariables.put("objectiveOfInsurance", (!StringUtils.isEmpty(channel) && AppConstants.CHANNEL_YBL.equalsIgnoreCase(channel) 
        		&& OBJ_OF_INSURANCE_CIP.equalsIgnoreCase(objectiveOfInsurance)) ? OBJ_OF_INSURANCE_HEALTH : objectiveOfInsurance);
        dataVariables.put("productSolution", productSolution);
        dataVariables.put("affinityCustomer", "N/A");
        dataVariables.put("existingCustomer", exitingCustomer);
        dataVariables.put("proposerImage", proposerImageURL);
        dataVariables.put("existingPolicyNumber", previousPolicyNumber);
        return dataVariables;

    }
}
