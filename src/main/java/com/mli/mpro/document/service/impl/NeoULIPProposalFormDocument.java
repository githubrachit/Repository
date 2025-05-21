package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.DedupeDetails;
import com.mli.mpro.proposal.models.LifeStyleDetails;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;

import java.util.*;

/**
 * @author akshom4375
 *
 */
@Component("neoUlipProposalFormDocument")
public class NeoULIPProposalFormDocument extends NeoBaseProposalForm implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(ULIPProposalFormDocument.class);
    public static final String PAYOR = "payor";
    public static final String NOMINEE_DETAILS = "nomineeDetails";
    boolean isNeoOrAggregator = true;
    @Autowired
    private ULIPDeclarationMapper ulipDeclarationMapper;

    @Autowired
    private ULIPLifeInsuredMapper ulipLifeInsuredMapper;

    @Autowired
    private ULIPChildAndCoverageMapper ulipChildAndCoverageMapper;

    @Autowired
    private ULIPNomineeMapper ulipNomineeMapper;

    @Autowired
    private ULIPPersonalDetailsMapper ulipPersonalMapper;

    @Autowired
    private MedicalDetailsMapper ulipMedicalMapper;

    @Autowired
    private ProposalCkycMapper proposalCkycMapper;

    @Autowired
    private ProposalMedicalDetailsMapper proposalMedicalDetailsMapper;

    @Autowired
    private ProposalFormAnnexureMapper proposalFormAnnexureMapper;

    @Autowired
    private CovidQuestionnaireMapper covidQuestionnaireMapper;

    @Autowired
    private NeoAdditionalQuestionnaireMapper neoAdditionalQuestionnaireMapper;

    @Autowired
    protected LifeInsuredDetailsMapper lifeInsuredDetailsMapper;

    @Autowired
    protected PPHINomineeDetailsMapper pphiNomineeDetailsMapper;

    final String checkbox = "classpath:static/checkbox.png";
    protected String proposalFormTemplate = "neo\\osp\\proposalForm";
    protected String proposalFormString = "ULIP";

    /**
     * Method to generate ULIP PRoposal document. Spring Template Engine is used to bind the
     * data dynamically to the static HTML which is stored in templates folder.
     */
    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        logger.info("START -- Neo implementaion", "%m");
        DocumentStatusDetails documentStatusDetails = null;
        long transactionId = proposalDetails.getTransactionId();
        long requestedTime = System.currentTimeMillis();

        try {
            Context ctx = getContextWithTemplateData(proposalDetails);

            documentStatusDetails = processDocumentGeneration(proposalDetails, ctx, proposalFormString, proposalFormTemplate,
                    springTemplateEngine, documentHelper);

        } catch (UserHandledException ex) {
            logger.error("Error occurred while ULIP Proposal Form Document generation:", ex);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.DATA_MISSING_FAILURE,AppConstants.PROPOSAL_FORM_DOCUMENT);
        } catch (Exception ex) {
            logger.error("Error occurred while ULIP Proposal Form Document generation:", ex);
            documentStatusDetails=documentHelper.populateDocumentStatusObj(isNeoOrAggregator,proposalDetails, transactionId,AppConstants.TECHNICAL_FAILURE,AppConstants.PROPOSAL_FORM_DOCUMENT);
        }

        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processedTime = (System.currentTimeMillis() - requestedTime);
        logger.info("ULIP Proposal Form document for transactionId {} took {} miliseconds ", proposalDetails.getTransactionId(), processedTime);    }

    private Context getContextWithTemplateData(ProposalDetails proposalDetails) throws UserHandledException {

        String processedHTMLPersonalDetails = null;
        String processedHTMLMedicalDetails = null;
        String processedHTMLNomineeDetails = null;
        String processedHTMLChildAndCoverageDetails = null;
        String processedHTMLDeclarationDetails = null;
        String processedHTMLLifeInsuredDetails = null;
        Map<String, Object> completeULIPProposalFormDetails = new HashMap<>();

        if(Utility.isChannelNeoOrAggregator(proposalDetails) && Utility.isApplicationIsForm2(proposalDetails)) {
            changeProposerAndLifeinsuredForForm2(proposalDetails);
        }
        Context medicalDetailsContext = !isNeoOrAggregator ? ulipMedicalMapper.setMedicalData(proposalDetails) : null;
        Context personalDetailsContext = ulipPersonalMapper.setDataForProposalForm(proposalDetails);
        Context nomineeDetailsContext = ulipNomineeMapper.setDataOfNominee(proposalDetails);
        Context coverageDetailsContext = ulipChildAndCoverageMapper.setDataOfCoverage(proposalDetails);
        Context declarationContext = ulipDeclarationMapper.setDataOfDeclaration(proposalDetails);
        Context ulipLifeInsuredContext = null;

        if (Utility.isApplicationIsForm2(proposalDetails) && Utility.isChannelNeoOrAggregator(proposalDetails)) {
            ulipLifeInsuredContext = lifeInsuredDetailsMapper.setDataForLifeInsured(proposalDetails);
        } else {
            ulipLifeInsuredContext = ulipLifeInsuredMapper.setDataOfLifeInsured(proposalDetails);
        }

        Context covidQuestionnaireContext = covidQuestionnaireMapper.setDataForCovidQuestionnaire(proposalDetails);
        Context newCovidQuestionnaireContext = covidQuestionnaireMapper.setDataForCovidQuestionnaire(proposalDetails);
        Context diabeticQuestionnaireContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.DIABETIC_QUESTIONNAIRE);
        Context highBloodPressureQuestionnaireAnnexureContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.HIGH_BLOOD_PRESSURE_QUESTIONNAIRE);
        Context respiratoryDisorderQuestionnaireAnnexureContext = neoAdditionalQuestionnaireMapper.setDataForAdditionalQuestionnaire(
                proposalDetails, AppConstants.RESPIRATORY_DISORDER_QUESTIONNAIRE);
        Context pphINomineeDetailsCtx = pphiNomineeDetailsMapper.setDataForPPHINomineeAndAppointee(proposalDetails);

        String productId = proposalDetails.getProductDetails().get(0).getProductInfo().getProductId();
        String productName = proposalDetails.getProductDetails().get(0).getProductInfo().getProductName();
        logger.info("Setting ULIP context data in a dataMap");
        logger.info("Setting ULIP context data for ULIP product : {} ",productName);
        completeULIPProposalFormDetails = setDocumentData(proposalDetails, completeULIPProposalFormDetails);

        String finalProcessedHtml = null;

        Context ctx = new Context();
        //NEORW: If incoming request from NEO or Aggregator then used neo osp templates for ULIP(OSP) Proposal Form Data
        if (isNeoOrAggregator) {
            logger.info("Setting ULIP context data in a dataMap for NEO");
            boolean isForm2 = Utility.isApplicationIsForm2(proposalDetails);
            processedHTMLPersonalDetails = springTemplateEngine.process("neo\\osp\\personalDetails", personalDetailsContext);
            processedHTMLChildAndCoverageDetails = springTemplateEngine.process("neo\\osp\\coverageDetails", coverageDetailsContext);
            processedHTMLLifeInsuredDetails = springTemplateEngine.process(setLifeInsuredTemplate(isForm2), ulipLifeInsuredContext);
            processedHTMLDeclarationDetails = springTemplateEngine.process("neo\\osp\\pfDeclaration", declarationContext);
            Context medDetailsContext = proposalMedicalDetailsMapper.setMedicalData(proposalDetails);
            Context ckycDetailsContext = proposalCkycMapper.setDataForCkycDetails(proposalDetails);
            Context proposalAnnexureDetailsContext = proposalFormAnnexureMapper.setDataForProposalFormAnnexure(proposalDetails);
            Context covidQuestionnaireDefaultContext =  covidQuestionnaireMapper.decideDefaultTemplate(proposalDetails);
            processedHTMLMedicalDetails = springTemplateEngine.process(setMedicalDetailsTemplate(isForm2), medDetailsContext);
            String processedHTMLCkyc = springTemplateEngine.process("neo\\osp\\ckyc", ckycDetailsContext);
            String processedHtmlAnnexureDetails = "";
            if(Utility.isChannelAggregator(proposalDetails)){
                processedHtmlAnnexureDetails = springTemplateEngine.process(setProposalAnnexureTemplatePB(isForm2), proposalAnnexureDetailsContext);
            }else{
                processedHtmlAnnexureDetails = springTemplateEngine.process(setProposalAnnexureTemplate(isForm2), proposalAnnexureDetailsContext);
            }
            String processedHTMLCovidQuestionnaireAnnexure = "";
            if (Utility.isCovidAnnexureApplicable(proposalDetails)) {
                if (!Utility.isNewCovidQuestionApplicable(proposalDetails)) {
                    processedHTMLCovidQuestionnaireAnnexure = springTemplateEngine.process(setOldCovidTemplate(isForm2), covidQuestionnaireContext);
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
                processedHTMLDiabeticQuestionnaireAnnexure = springTemplateEngine.process(setDiabeticTemplate(isForm2), diabeticQuestionnaireContext);
            }
            String processedHTMLHighBloodPressureQuestionnaireAnnexure="";
            if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewHypertensionQuestion())) {
                processedHTMLHighBloodPressureQuestionnaireAnnexure = springTemplateEngine.process(setHighBloodPressureTemplate(isForm2), highBloodPressureQuestionnaireAnnexureContext);
            }
            String processedHTMLRespiratoryDisorderQuestionnaireAnnexure="";
            if(Utility.isNotNeoYes(proposalDetails, details -> details.getNewRespiratoryQuestion())) {
                processedHTMLRespiratoryDisorderQuestionnaireAnnexure = springTemplateEngine.process(setRespiratoryTemplate(isForm2), respiratoryDisorderQuestionnaireAnnexureContext);
            }
            completeULIPProposalFormDetails.put("ckycDetails", processedHTMLCkyc);
            completeULIPProposalFormDetails.put("proposalFormAnnexure", processedHtmlAnnexureDetails);
            completeULIPProposalFormDetails.put("covidQuestionnaireAnnexure", processedHTMLCovidQuestionnaireAnnexure);
            completeULIPProposalFormDetails.put("covidQuestionnaireFalseAnnexure",processedHTMLCovidFalseQuestionnaireAnnexure);
            completeULIPProposalFormDetails.put("diabeticQuestionnaireAnnexure", processedHTMLDiabeticQuestionnaireAnnexure);
            completeULIPProposalFormDetails.put("highBloodPressureQuestionnaireAnnexure", processedHTMLHighBloodPressureQuestionnaireAnnexure);
            completeULIPProposalFormDetails.put("respiratoryDisorderQuestionnaireAnnexure", processedHTMLRespiratoryDisorderQuestionnaireAnnexure);

        } else if(productId.equalsIgnoreCase(AppConstants.FWP_PRODUCTCODE)) {
            logger.info("InSide If Setting ULIP FWP Data transactionId {} productName : {}",proposalDetails.getTransactionId(), productName);
            //Context nomineeDetailsContext = ulipNomineeMapper.setDataOfNominee(proposalDetails);
            processedHTMLPersonalDetails = springTemplateEngine.process("fwp\\personalDetails", personalDetailsContext);
            processedHTMLMedicalDetails = springTemplateEngine.process("fwp\\medicalDetails", medicalDetailsContext);
            processedHTMLNomineeDetails = springTemplateEngine.process("fwp\\nomineeDetails", nomineeDetailsContext);
            processedHTMLChildAndCoverageDetails = springTemplateEngine.process("fwp\\childAndCoverageDetails", coverageDetailsContext);
            processedHTMLDeclarationDetails = springTemplateEngine.process("fwp\\declarationDetails", declarationContext);
            processedHTMLLifeInsuredDetails = springTemplateEngine.process("fwp\\insuredDetails", ulipLifeInsuredContext);
            completeULIPProposalFormDetails.put(NOMINEE_DETAILS, processedHTMLNomineeDetails);
        } else {
            logger.info("InSide Else Setting ULIP Data transactionId {} productName : {}",proposalDetails.getTransactionId(), productName);
            String formType = proposalDetails.getApplicationDetails().getFormType();
            String schemeType = proposalDetails.getApplicationDetails().getSchemeType();
            if (AppConstants.SELF.equalsIgnoreCase(formType)
                || Utility.schemeBCase(formType, schemeType)) {
                Context nomineeDetailsContext_ulip = ulipNomineeMapper.setDataOfNominee(proposalDetails);
                processedHTMLNomineeDetails = springTemplateEngine.process("ulip\\nomineeDetails", nomineeDetailsContext_ulip);
                completeULIPProposalFormDetails.put(NOMINEE_DETAILS, processedHTMLNomineeDetails);
            }

            processedHTMLPersonalDetails = springTemplateEngine.process("ulip\\personalDetails", personalDetailsContext);
            processedHTMLMedicalDetails = springTemplateEngine.process("ulip\\medicalDetails", medicalDetailsContext);
            processedHTMLChildAndCoverageDetails = springTemplateEngine.process("ulip\\childAndCoverageDetails", coverageDetailsContext);
            processedHTMLDeclarationDetails = springTemplateEngine.process("ulip\\declarationDetails", declarationContext);
            processedHTMLLifeInsuredDetails = springTemplateEngine.process("ulip\\insuredDetails", ulipLifeInsuredContext);
        }

        String processedHtmlPPHINomineeDetails = springTemplateEngine.process("pphi\\PPHI_NomineeDetails", pphINomineeDetailsCtx);

        completeULIPProposalFormDetails.put("medicalDetails", processedHTMLMedicalDetails);
        completeULIPProposalFormDetails.put("personalDetails", processedHTMLPersonalDetails);
        completeULIPProposalFormDetails.put(NOMINEE_DETAILS, processedHTMLNomineeDetails);
        completeULIPProposalFormDetails.put("childAndCoverageDetails", processedHTMLChildAndCoverageDetails);
        completeULIPProposalFormDetails.put("insuredDetails", processedHTMLLifeInsuredDetails);
        completeULIPProposalFormDetails.put("declarationDetails", processedHTMLDeclarationDetails);
        completeULIPProposalFormDetails.put("pphiNomineeDetails", processedHtmlPPHINomineeDetails);
        completeULIPProposalFormDetails = setDocumentData(proposalDetails, completeULIPProposalFormDetails);

        ctx.setVariables(completeULIPProposalFormDetails);

        ctx.setVariables(completeULIPProposalFormDetails);
        return ctx;
    }

    private static String setRespiratoryTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\respiratoryDisorderQuestionnaire"
            : "neo\\respiratoryDisorderQuestionnaire";
    }

    private static String setHighBloodPressureTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\highBloodPressureQuestionnaire"
            : "neo\\highBloodPressureQuestionnaire";
    }

    private static String setDiabeticTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\diabeticQuestionnaire" : "neo\\diabeticQuestionnaire";
    }

    private static String setNewCovidTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\newCovidQuestionnaire" : "neo\\newCovidQuestionnaire";
    }

    private static String setOldCovidTemplate(boolean isForm2) {
        return isForm2 ? "neo\\swpjl\\covidQuestionnaire" : "neo\\covidQuestionnaire";
    }

    private static String setProposalAnnexureTemplate(boolean isForm2) {
        return isForm2 ? "neo\\osp\\proposalFormAnnexureForm2" : "neo\\osp\\proposalFormAnnexure";
    }
    private static String setMedicalDetailsTemplate(boolean isForm2) {
        return isForm2 ? "neo\\osp\\medicalDetailsForm2" : "neo\\osp\\medicalDetails";
    }
    private static String setLifeInsuredTemplate(boolean isForm2) {
        return isForm2 ? "neo\\osp\\lifeInsuredDetailsForm2" : "neo\\osp\\lifeInsuredDetails";
    }

    private static String setProposalAnnexureTemplatePB(boolean isForm2) {
        return isForm2 ? "neo\\aggregator\\proposalFormAnnexureOSPForm2" : "neo\\aggregator\\proposalFormAnnexureOSP";
    }

    /**
     * Setting data from input json for ULIP Proposal Document to be generated
     *
     * @param proposalDetails
     * @return
     * @throws UserHandledException
     */
    private Map<String, Object> setDocumentData(ProposalDetails proposalDetails, Map<String, Object> dataVariables) throws UserHandledException {
        logger.info("START data processing for main template of ULIP Proposal Form");
        try {
            String existingPolicy = "";
            String purposeOfInsurance = "";
            String objectiveOfInsurance = "";
            String productSolution = "";
            String previousPolicyNumber = "";
            String policyNumber = Utility.nullSafe(proposalDetails.getApplicationDetails().getPolicyNumber());
            String isMWPA = StringUtils.EMPTY;

            List<ProductDetails> productDetailsList = Objects.nonNull(proposalDetails.getProductDetails())
                    ? proposalDetails.getProductDetails() : Collections.emptyList();
            List<DedupeDetails> dedupeDetailsList = Objects.nonNull(proposalDetails.getUnderwritingServiceDetails())
                    && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails())
                    ? proposalDetails.getUnderwritingServiceDetails().getDedupeDetails() : Collections.emptyList();

            if (null != productDetailsList && !CollectionUtils.isEmpty(productDetailsList) && productDetailsList.size() >= 1) {
                purposeOfInsurance = productDetailsList.get(0).getNeedOfInsurance();
                objectiveOfInsurance = productDetailsList.get(0).getObjectiveOfInsurance();
            }
            if (null != dedupeDetailsList && !CollectionUtils.isEmpty(dedupeDetailsList) && dedupeDetailsList.size() >= 1) {
                existingPolicy = (dedupeDetailsList != null && StringUtils.isNotBlank(dedupeDetailsList.get(0).getPreviousPolicyNumber())) ? AppConstants.YES
                        : AppConstants.NO;
            }
            //NEORW: if incoming request from NEO then existingCustomer and previousPolicyNumber will be fetched from basic Details
            if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getPartyInformation())
                    && !proposalDetails.getPartyInformation().isEmpty()
                    && Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
                setExistingCustomerAndPreviousPolicyNumberData(proposalDetails, dataVariables);
            } else {
                previousPolicyNumber = "YES".equalsIgnoreCase(existingPolicy)
                        ? "-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";
                dataVariables.put("previousPolicyNumber", previousPolicyNumber);
            }

            String channel = proposalDetails.getChannelDetails().getChannel();
            String customerId = Objects.nonNull(proposalDetails.getBancaDetails()) ? Utility.nullSafe(proposalDetails.getBancaDetails().getCustomerId())
                    : AppConstants.BLANK;
            String ssnCode = "";
            if (!isNeoOrAggregator) {
                ssnCode = org.apache.commons.lang3.StringUtils.equalsIgnoreCase(channel, "BY") ? proposalDetails.getSourcingDetails().getAgentId()
                        : proposalDetails.getSourcingDetails().getSpecifiedPersonDetails().getSpSSNCode();
            }
            String gocaBrokerCode = "";
            if (isNeoOrAggregator && Objects.nonNull(proposalDetails.getSourcingDetails())) {
                gocaBrokerCode = Utility.nullSafe(proposalDetails.getSourcingDetails().getAgentCode());
            } else {
                gocaBrokerCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
            }
            String proposerImageURL = AppConstants.DUMMY_BLANK_IMAGE_PATH;
            //NEORW:- handle null check for underWritingStatus object
            if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails())
                    && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus())
                    && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments())) {
                String imageType  = Utility.imageType(proposalDetails) ;
                proposerImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), imageType, channel,
                        proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
            }
            String payorImageURL = AppConstants.DUMMY_BLANK_IMAGE_PATH;
            if (Objects.nonNull(proposalDetails.getAdditionalFlags()) && proposalDetails.getAdditionalFlags().isPayorDiffFromPropser()) {
                dataVariables.put(PAYOR, "YES");
                payorImageURL = documentHelper.getImageURL(proposalDetails.getTransactionId(), "Payor", channel,
                        proposalDetails.getUnderwritingServiceDetails().getUnderwritingStatus().getRequiredDocuments());
                dataVariables.put("parameter", PAYOR);
            }
            dataVariables.put("payorImage", payorImageURL);
            if (StringUtils.equalsIgnoreCase(channel, AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
                gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
            }


            String goGreen = "";
            if (isNeoOrAggregator && Objects.nonNull(proposalDetails) && Objects.nonNull(proposalDetails.getApplicationDetails()) && Objects.nonNull(proposalDetails.getApplicationDetails().getGoGreen())) {
                goGreen = proposalDetails.getApplicationDetails().getGoGreen();
            }

            productSolution = ((null != proposalDetails.getSalesStoriesProductDetails())
                    && "YES".equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) ? "YES" : AppConstants.NA;
            dataVariables.put(PAYOR, proposalDetails.getAdditionalFlags().isPayorDiffFromPropser() ? "YES" : "NO");
            dataVariables.put("channel", channel);
            if (Objects.nonNull(proposalDetails.getNomineeDetails()) &&
                    Objects.nonNull(proposalDetails.getNomineeDetails().getPartyDetails())){
                isMWPA = proposalDetails.getNomineeDetails().getPartyDetails().get(0).getObjectiveOfInsurance();
            }
            dataVariables.put("isMWPA", isMWPA);
            dataVariables.put("objectiveOfInsurance", objectiveOfInsurance);
            dataVariables.put("purposeOfInsurance", purposeOfInsurance);
            dataVariables.put("customerId", customerId);
            dataVariables.put("gocaBrokerCode", gocaBrokerCode);
            dataVariables.put("policyNumber", policyNumber);
            dataVariables.put("proposerImageURL", proposerImageURL);
            dataVariables.put("ssnCode", ssnCode);
            dataVariables.put("existingPolicy", StringUtils.isNotEmpty(existingPolicy) ? existingPolicy : AppConstants.NO);

            dataVariables.put("productSolution", productSolution);
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
            logger.info("END ", "%m");
            return dataVariables;
        } catch (Exception ex) {
            logger.error("Data addition failed for ULIP Proposal Document:", ex);
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ULIP Document Data addition failed");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
