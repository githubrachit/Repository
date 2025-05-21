package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.mapper.GSTWaiverDeclarationMapper;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.document.utils.TraditionalFormUtil;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DocumentRequestInfo;
import com.mli.mpro.proposal.models.PosvDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.mli.mpro.productRestriction.util.AppConstants.FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION;

@Component("annuityProposalForm")
@EnableAsync
public class AnnuityProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(AnnuityProposalForm.class);

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DocumentHelper documentHelper;

    @Autowired
    private PersonalDetailsMapper personDetailsMapper;

    @Autowired
    private DeclarationMapper declarationMapper;

    @Autowired
    private GLIPCoverageDetailsMapper coverageDetailsMapper;

    @Autowired
    private GSTWaiverDeclarationMapper gstWaiverDeclarationMapper;

    @Autowired
    private PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

    @Async
    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();
        int retryCount = 0;
        boolean isPosSeller = proposalDetails.getSourcingDetails() != null && proposalDetails.getSourcingDetails().isPosSeller();
        ProductDetails productDetails = proposalDetails.getProductDetails().get(0);
		String productId = productDetails.getProductInfo().getProductId();
		String pfTemplate;
		String processedHtmlPersonalDetails;
		String processedHtmlDeclaration;
		String processedHTMLCoverageHTML;
		String processedHTMLNomineeDetails;
        String gstWaiverDeclarationDetails = null;
        String processedHtmlPPHINomineeDetails = null;
        logger.info("GLIP Proposal Form document generation is initiated for transactionId {} and at applicationStage {}",
                transactionId, proposalDetails.getApplicationDetails().getStage());
        try {
            Context personalDetailscontext = personDetailsMapper.setDataForPersonalDetails(proposalDetails);
            Context coverageDetailsCtx = coverageDetailsMapper.setDataOfCoverageDetails(proposalDetails);
            Context declarationContext = declarationMapper.setDataOfDeclaration(proposalDetails);
            Context gstWaiverDeclarationContext = gstWaiverDeclarationMapper.setDataOfGSTWaiverDeclarationDocument(proposalDetails);
            Context pphiNomineeDetailsContext = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);
            Object nomineeDetails = personalDetailscontext.getVariable("nomineeDetailsCount");
            Context nomineeDetailsctx = new Context();
            nomineeDetailsctx.setVariable("nomineeDetails", nomineeDetails);
            Map<String, Object> completeDetails = new HashMap<>();
            completeDetails = setDataForGLIPProposalForm(proposalDetails, completeDetails);
            //FUL2-77213
            completeDetails = setAgentAssistedForGLIP(productDetails, completeDetails);
            //FUL2-74065 NEW PF Form for GLIP POS
			if (isPosSeller && (AppConstants.GLIP_ID.equals(productId)||AppConstants.SWAG_PP_PRODUCT_ID.equalsIgnoreCase(productId))){
				pfTemplate = "glippos\\Annuity_ProposalForm";
				processedHtmlPersonalDetails = springTemplateEngine.process("glippos\\Annuity_PersonalDetails",
						personalDetailscontext);
				processedHtmlDeclaration = springTemplateEngine.process("glippos\\Annuity_PFDeclaration", declarationContext);
				processedHTMLCoverageHTML = springTemplateEngine.process("glippos\\AnnuityCoverageDetails", coverageDetailsCtx);
				processedHTMLNomineeDetails = springTemplateEngine.process("glippos\\Annuity_NomineeDetails", nomineeDetailsctx);
			} else {
				pfTemplate = "GLIP_ProposalForm";
				processedHtmlPersonalDetails = springTemplateEngine.process("GLIP_PersonalDetails",
						personalDetailscontext);
				processedHtmlDeclaration = springTemplateEngine.process("GLIP_PFDeclaration", declarationContext);
				processedHTMLCoverageHTML = springTemplateEngine.process("AnnuityCoverageDetails", coverageDetailsCtx);
				processedHTMLNomineeDetails = springTemplateEngine.process("GLIP_NomineeDetails", nomineeDetailsctx);
                gstWaiverDeclarationDetails = springTemplateEngine.process("nri\\gstWaiverDeclarationDetails", gstWaiverDeclarationContext);
            }
            processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphiNomineeDetailsContext);
            Context proposalFormDetailsCtx = new Context();
            completeDetails.put("pdfVersion", declarationContext.getVariable("pdfVersion"));
            completeDetails.put("isNotYBLProposal", declarationContext.getVariable("isNotYBLProposal"));
            completeDetails.put("personalDetails", processedHtmlPersonalDetails);
            completeDetails.put("declarationDetails", processedHtmlDeclaration);
            completeDetails.put("coverageDetails", processedHTMLCoverageHTML);
            completeDetails.put("nomineeDetails", processedHTMLNomineeDetails);
            if(FeatureFlagUtil.isFeatureFlagEnabled(FEATURE_FLAG_NRI_GST_WAIVER_DECLARATION)
                    && null != proposalDetails.getApplicationDetails().getFormType()
                    && !(AppConstants.FORM3.equalsIgnoreCase(proposalDetails.getApplicationDetails().getFormType()))
                    && AppConstants.NRI.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getResidentialStatus())
                    && !AppConstants.YES.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer())
                    && AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())) {
                completeDetails.put("gstWaiverDeclarationDetails", gstWaiverDeclarationDetails);
            }
            completeDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
            proposalFormDetailsCtx.setVariables(completeDetails);
            String proposalFormProcessedHTML = springTemplateEngine.process(pfTemplate,
                    proposalFormDetailsCtx);
            String pdfDocumentOrDocumentStatus = documentHelper.generatePDFDocument(proposalFormProcessedHTML, 0);
            if (pdfDocumentOrDocumentStatus.equalsIgnoreCase(AppConstants.FAILED)) {
                logger.info("Document generation is failed for GLIP so updating in DB for transactionId {}", transactionId);
                String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
                documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                        policyNumber, proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DOCUMENT_GENERATION_FAILED, 0
                        , AppConstants.PROPOSAL_FORM_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
            } else {
                logger.info("Document generation is successfull");
                DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(AppConstants.DOCUMENT_TYPE,
                        pdfDocumentOrDocumentStatus, AppConstants.PROPOSAL_FORM_DOCUMENT);
                documentStatusDetails = documentHelper.getDocumentStatusDetails(proposalDetails, transactionId,
                        retryCount, documentRequestInfo);
            }
        } catch (UserHandledException ex) {
            logger.error("Proposal Form document generation failed:", ex);
            String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    policyNumber, proposalDetails.getSourcingDetails().getAgentId(), AppConstants.DATA_MISSING_FAILURE, 0,
                    AppConstants.PROPOSAL_FORM_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        } catch (Exception ex) {
            logger.error("Proposal Form Document generation failed:", ex);
            String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
            documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                    policyNumber,proposalDetails.getSourcingDetails().getAgentId(), AppConstants.TECHNICAL_FAILURE, 0,
                    AppConstants.PROPOSAL_FORM_DOCUMENT,
                    proposalDetails.getApplicationDetails().getStage());
        }
        logger.info("Document Generated and updating status in db.");
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("Proposal Form document for transactionId {} took {} miliseconds ",
                proposalDetails.getTransactionId(), processedTime);
    }

	/**
	 * @implNote This method is used to assign the value of Agent Assisted or Non
	 *           Agent Assisted to show in PF document for GLIP only
	 * @param productDetails
	 * @param completeDetails
	 * @return
	 */
	private Map<String, Object> setAgentAssistedForGLIP(ProductDetails productDetails,
			Map<String, Object> completeDetails) {
		try {
			if ((AppConstants.GLIP_ID.equals(productDetails.getProductInfo().getProductId())||
                    AppConstants.SWAG_PP_PRODUCT_ID.equalsIgnoreCase(productDetails.getProductInfo().getProductId()))
					&& productDetails.getProductInfo().getProductIllustrationResponse() != null) {
				if (AppConstants.AA.equalsIgnoreCase(
						productDetails.getProductInfo().getProductIllustrationResponse().getAnnuityPurchasedFrom())) {
					completeDetails.put(AppConstants.AGENT_ASSISTED_KEY, AppConstants.YES);
				} else if (AppConstants.NAA.equalsIgnoreCase(
						productDetails.getProductInfo().getProductIllustrationResponse().getAnnuityPurchasedFrom())) {
					completeDetails.put(AppConstants.AGENT_ASSISTED_KEY, AppConstants.NO);
				}
				return completeDetails;
			}
		} catch (Exception ex) {
			logger.error("Exception occured while setting agent assisted value");
		}
		return completeDetails;
	}

	private Map<String, Object> setDataForGLIPProposalForm(ProposalDetails proposalDetails,
                                                       Map<String, Object> dataVariables) {
        List<String> commPlanCodes = Arrays.asList("TDAJC","IAJC","IASC","IAJRC","IASRC","TDASC");
        /*
		 * Start of FUL2-20134 - PF related changes
		 * SPP - plan code setup here
		 */
        if(proposalDetails.getProductDetails().get(0) != null &&  proposalDetails.getProductDetails().get(0).getProductInfo() != null
        		&& proposalDetails.getProductDetails().get(0).getProductInfo().getProductId() != null && 
        	AppConstants.SPP_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) {
        		 commPlanCodes = Arrays.asList("TIASR", "TIAJR","TIASRC", "TIAJRC");
        	
        }
        /*
         * end of FUL2-20134 - proposer form coverage details changes
         **/
		String physicalDocument = Optional.ofNullable(proposalDetails).map(ProposalDetails::getPosvDetails)
				.map(PosvDetails::getGoGreen).isPresent()
						? AppConstants.YES.equalsIgnoreCase(proposalDetails.getPosvDetails().getGoGreen())
								? AppConstants.CAMEL_NO
								: AppConstants.CAMEL_YES
						: AppConstants.CAMEL_YES;
		String nonNriFinalStage = Utility.finalStageNationalityCheck(proposalDetails);
		dataVariables.put(AppConstants.FINAL_STAGE, nonNriFinalStage);
		dataVariables.put(AppConstants.PHYSICAL_POLICY, physicalDocument);

        dataVariables = TraditionalFormUtil.setDataForPFForm(proposalDetails,dataVariables);
        String channel = proposalDetails.getChannelDetails().getChannel();
        String secondAnnuitantImageURL = null;
        if (proposalDetails.getProductDetails().get(0).getProductInfo().getAnnuityOption().equalsIgnoreCase(AppConstants.JOINT_LIFE_ANNUITY_OPTION)) {
            secondAnnuitantImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(),
                    AppConstants.SECOND_ANNUITANT,
                    channel,
                    proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
            dataVariables.put("parameter", AppConstants.PAYOR);
            dataVariables.put("secondAnnuitantImage", secondAnnuitantImageURL);
            dataVariables.put("jointAnnuitant","YES");
        }else{
            dataVariables.put("jointAnnuitant","NO");
        }
        String imageType = Utility.imageType(proposalDetails);
        String proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
                proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
        String agentAssisted = AppConstants.NO;
        if (commPlanCodes.contains(proposalDetails.getProductDetails().get(0).getProductInfo().getPlanCode()))
            agentAssisted = AppConstants.YES;

        dataVariables.put("proposerImage", proposerImageURL);
        dataVariables.put("agentAssisted", agentAssisted);
        return dataVariables;
    }
}
