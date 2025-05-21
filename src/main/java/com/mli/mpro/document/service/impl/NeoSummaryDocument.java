package com.mli.mpro.document.service.impl;

import com.mli.mpro.document.enums.FamilyType;
import com.mli.mpro.document.models.DocumentStatusDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.*;

@Component("neoSummaryDocument")
@EnableAsync
public class NeoSummaryDocument implements DocumentGenerationservice {

    private static final Logger logger = LoggerFactory.getLogger(NeoSummaryDocument.class);
    private static final String TEMPLATE = "neo\\summary\\summary";

    private final SpringTemplateEngine springTemplateEngine;
    private final DocumentHelper documentHelper;

    @Autowired
    public NeoSummaryDocument(SpringTemplateEngine springTemplateEngine, DocumentHelper documentHelper) {
        this.springTemplateEngine = springTemplateEngine;
        this.documentHelper = documentHelper;
    }

    @Override
    public void generateDocument(ProposalDetails proposalDetails) {
        if (Objects.isNull(proposalDetails)) {
            logger.error("[SummaryDocument] Proposal object cannot be null for Summary Document Generation");
            return;
        }
        logger.info("[SummaryDocument] Starting Document Generation for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        long requestTime = System.currentTimeMillis();
        DocumentStatusDetails documentStatusDetails;
        try {
            Context context = new Context();
            setDataInContext(context, proposalDetails);

            String processedHtml = springTemplateEngine.process(TEMPLATE, context);
            logger.info("[SummaryDocument] Creating pdf document for equoteNumber {}, transactionId {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
            String documentBase64 = documentHelper.generatePDFDocument(processedHtml, 0);
            if (FAILED.equalsIgnoreCase(documentBase64)) {
                logger.error("[SummaryDocument] Document generation failed for equoteNumber {}, transactionId {}. Updating in DB",
                        proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
                documentStatusDetails = new DocumentStatusDetails(proposalDetails.getTransactionId(),
                        proposalDetails.getApplicationDetails().getPolicyNumber(),
                        proposalDetails.getSourcingDetails().getAgentId(), DOCUMENT_GENERATION_FAILED,
                        0, NEO_SUMMARY_DOCUMENT, proposalDetails.getApplicationDetails().getStage());
            } else {
                documentStatusDetails = saveGeneratedDocumentToS3(proposalDetails, documentBase64);
            }

        } catch (Exception e) {
            logger.error("[SummaryDocument] Exception occurred during document generation for equoteNumber {}, transactionId {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), e);
            documentStatusDetails = documentHelper.populateDocumentStatusObj(true, proposalDetails,
                    proposalDetails.getTransactionId(), TECHNICAL_FAILURE, NEO_SUMMARY_DOCUMENT);
        }
        documentHelper.updateDocumentStatus(documentStatusDetails);
        long processingTime = System.currentTimeMillis() - requestTime;
        logger.info("[SummaryDocument] Document Generation completed for equoteNumber {}, transactionId {}. Took {} ms",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), processingTime);
    }

    private void setDataInContext(Context context, ProposalDetails proposalDetails) {
        logger.info("[SummaryDocument] Setting data in context for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        Map<String, Object> variables = new HashMap<>();
        String medicalTest;

        String productId= Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())
                ? proposalDetails.getProductDetails().get(0).getProductInfo().getProductId() : BLANK;
        String productName = FamilyType.getHtmlFormByProductId(productId);
        String channel = Objects.nonNull(proposalDetails.getChannelDetails()) && Objects.nonNull(proposalDetails.getChannelDetails().getChannelId()) ?
                proposalDetails.getChannelDetails().getChannelId() : StringUtil.EMPTY_STRING;
        if (Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) &&
                Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails()) &&
                Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getTestName()) &&
                !proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getTestName().isEmpty()) {
            medicalTest = String.join(",",
                    proposalDetails.getUnderwritingServiceDetails().getMedicalShedulingDetails().getTestName());
        } else {
            medicalTest = "";
        }
        setValuesFromPartyInformation(proposalDetails, variables);
        setValuesFromEmploymentDetails(proposalDetails, variables);
        setValuesFromProductDetails(proposalDetails, variables);
        setValuesFromCkycDetails(proposalDetails, variables);
        setValuesFromEkycDetails(proposalDetails, variables);
        setValuesFromApplicationDetails(proposalDetails,variables);
        setIncomeWaiverIncome(proposalDetails, variables);
        if ((STEP_CIS.equalsIgnoreCase(productName) || SSP_CIS.equalsIgnoreCase(productName) || NEO_STPP_PRODUCT_TYPE.equalsIgnoreCase(productName))) {
            setMedicalReasonDetails(proposalDetails, variables);
        }

        variables.put("channel", channel);
        variables.put("medicalTest", medicalTest);
        context.setVariables(variables);
    }

    private void setMedicalReasonDetails(ProposalDetails proposalDetails, Map<String, Object> variables) {
        String medicalSuppress;
        String reasonForSuppress = BLANK;
        if (StringUtils.hasLength(proposalDetails.getMedicalSuppress()) && NEO_YES.equalsIgnoreCase(proposalDetails.getMedicalSuppress())) {
            medicalSuppress = YES_MEDICAL_SKIP;
        } else if (StringUtils.hasLength(proposalDetails.getMedicalSuppress()) && NEO_NO.equalsIgnoreCase(proposalDetails.getMedicalSuppress())) {
            medicalSuppress = YES_CANCEL;
        } else if (StringUtils.hasLength(proposalDetails.getMedicalSuppress()) && STATUS_OTHER_THAN_01_02.equalsIgnoreCase(proposalDetails.getMedicalSuppress())) {
            medicalSuppress = CAMEL_NO;
        } else {
            medicalSuppress = BLANK;
        }

        if (StringUtils.hasLength(proposalDetails.getReasonForSuppress())) {
            reasonForSuppress = proposalDetails.getReasonForSuppress();
        }

        variables.put("medicalSuppress", medicalSuppress);
        variables.put("reasonForSuppress", reasonForSuppress);
    }

    private void setValuesFromApplicationDetails(ProposalDetails proposalDetails, Map<String, Object> variables)
    {
        logger.info("[SummaryDocument] Setting values from ApplicationDetails for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        ApplicationDetails applicationDetails;
        if (Objects.nonNull(proposalDetails.getApplicationDetails())) {
            applicationDetails = proposalDetails.getApplicationDetails();
        } else {
            applicationDetails = new ApplicationDetails();
        }
        String isComboSale = checkIsComboPlan(proposalDetails);
        variables.put("isComboSale", isComboSale);
    }

    public static String checkIsComboPlan(ProposalDetails proposalDetails)
    {
        if (("NEO".equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) || CHANNEL_AGGREGATOR.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()))
                && ("01".equalsIgnoreCase(proposalDetails.getApplicationDetails().getIsComboSale()) || "01".equalsIgnoreCase(proposalDetails.getApplicationDetails().getIsPbComboSale()))) {
            return TRUE;
        } else {
            return FALSE;
        }

    }

    private void setValuesFromPartyInformation(ProposalDetails proposalDetails, Map<String, Object> variables) {
        logger.info("[SummaryDocument] Setting values from partyInformation for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        String name = "";
        String age = "";
        String existingCustomer = "";
        String existingEmployee = "";
        String craPincodeByConsumer = "";
        String praPincodeByConsumer = "";
        String nri = NO;
        String incomeProofWaiverEducation = "";
        String isBankAccountVerified = NO;
        if (Objects.nonNull(proposalDetails.getPartyInformation()) &&
                !proposalDetails.getPartyInformation().isEmpty() &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(0)) &&
                Objects.nonNull(proposalDetails.getPartyInformation().get(0).getBasicDetails())) {
            BasicDetails basicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
            isBankAccountVerified = Optional.ofNullable(proposalDetails.getPartyInformation().get(0).getBankDetails())
                    .map(BankDetails::getIsPennyDropVerified).orElse(NO);
            StringBuilder fullName = new StringBuilder(basicDetails.getFirstName());
            if (!StringUtils.isEmpty(basicDetails.getMiddleName())) {
                fullName.append(WHITE_SPACE).append(basicDetails.getMiddleName());
            }
            if (!StringUtils.isEmpty(basicDetails.getLastName())) {
                fullName.append(WHITE_SPACE).append(basicDetails.getLastName());
            }
            name = fullName.toString();
            age = Utility.getAge(basicDetails.getDob());
            incomeProofWaiverEducation = Utility.getNullSafeStringValueForDocs(basicDetails.getEducation(), BLANK);
            existingCustomer = Utility.convertToYesOrNo(basicDetails.getIsExistingCustomer());
            existingEmployee = Utility.convertToYesOrNo(basicDetails.getIsEmployee());
            if (Objects.nonNull(basicDetails.getAddress())) {
                praPincodeByConsumer = basicDetails.getAddress().stream()
                        .filter(Objects::nonNull)
                        .filter(address -> (PERMANENT_ADDRESS.equalsIgnoreCase(address.getAddressType()) ||
                                PERMANENT_ADDRESS_NEO.equalsIgnoreCase(address.getAddressType())))
                        .findFirst()
                        .map(Address::getAddressDetails)
                        .map(AddressDetails::getPinCode)
                        .orElse("");
                craPincodeByConsumer = basicDetails.getAddress().stream()
                        .filter(Objects::nonNull)
                        .filter(address -> (CURRENT_ADDRESS.equalsIgnoreCase(address.getAddressType()) ||
                                CURRENT_ADDRESS_NEO.equalsIgnoreCase(address.getAddressType())))
                        .findFirst()
                        .map(Address::getAddressDetails)
                        .map(AddressDetails::getPinCode)
                        .orElse("");
            }
            if (Objects.nonNull(basicDetails.getNationalityDetails())) {
                nri = INDIAN_NATIONALITY.equalsIgnoreCase(basicDetails.getNationalityDetails().getNationality()) ? NO : YES;
            }
        }
        variables.put("name", name);
        variables.put("age", age);
        variables.put("incomeWaiverAge", age);
        variables.put("incomeProofWaiverEducation", incomeProofWaiverEducation);
        variables.put("nri", nri);
        variables.put("existingCustomer", existingCustomer);
        variables.put("existingEmployee", existingEmployee);
        variables.put("craPincodeByConsumer", craPincodeByConsumer);
        variables.put("praPincodeByConsumer", praPincodeByConsumer);
        variables.put("isBankAccountVerified", Utility.convertToYesOrNo(isBankAccountVerified));
    }

    private void setValuesFromEmploymentDetails(ProposalDetails proposalDetails, Map<String, Object> variables) {
        logger.info("[SummaryDocument] Setting values from employmentDetails for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        String incomeProofWaiverOccupation;

        if (Objects.nonNull(proposalDetails.getEmploymentDetails()) &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation()) &&
                !proposalDetails.getEmploymentDetails().getPartiesInformation().isEmpty() &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0)) &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails())) {
            BasicDetails basicDetails = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails();
            incomeProofWaiverOccupation = Utility.getNullSafeStringValueForDocs(basicDetails.getOccupation(), BLANK);
        } else {
            incomeProofWaiverOccupation = "";
        }
        variables.put("incomeProofWaiverOccupation", incomeProofWaiverOccupation);
    }

    private void setValuesFromProductDetails(ProposalDetails proposalDetails, Map<String, Object> variables) {
        logger.info("[SummaryDocument] Setting values from productDetails for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        String sa;
        if (Objects.nonNull(proposalDetails.getProductDetails()) &&
                !proposalDetails.getProductDetails().isEmpty() &&
                Objects.nonNull(proposalDetails.getProductDetails().get(0)) &&
                Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo())) {
            sa = Utility.getNullSafeStringValueForDocs(proposalDetails.getProductDetails().get(0).getProductInfo().getSumAssuredAvailable(), BLANK);
            logger.info("[SummaryDocument] Setting value of SA as {} for equoteNumber {}, transactionId {}",
                    sa, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        } else {
            sa = "";
            logger.info("[SummaryDocument] Setting blank value of SA for equoteNumber {}, transactionId {}. ProductDetails {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(), proposalDetails.getProductDetails());
        }
        variables.put("sa", sa);
    }

    private void setValuesFromCkycDetails(ProposalDetails proposalDetails, Map<String, Object> variables) {
        logger.info("[SummaryDocument] Setting values from ckycDetails for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        CkycDetails ckycDetails;
        if (Objects.nonNull(proposalDetails.getCkycDetails())) {
            ckycDetails = proposalDetails.getCkycDetails();
        } else {
            ckycDetails = new CkycDetails();
        }
        String msa = Utility.getNullSafeStringValueForDocs(ckycDetails.getMsa(), BLANK);
        String panWaiverSource = Utility.getNullSafeStringValueForDocs(ckycDetails.getPanWaiverSource(), BLANK);
        String incomeWaiverSource = Utility.getNullSafeStringValueForDocs(ckycDetails.getIncomeWaiverSource(), BLANK);
        String cibilScore = Utility.getNullSafeStringValueForDocs(ckycDetails.getCibilScore(), BLANK);
        String crifScore = Utility.getNullSafeStringValueForDocs(ckycDetails.getCrifScore(), BLANK);
        String incomeWaiverFsa = Utility.getNullSafeStringValueForDocs(ckycDetails.getIncomeWaiverFSA(), BLANK);
        String isCraWaiver = Utility.convertToYesOrNo(ckycDetails.getIsCraWaiver());
        String craWaiverSource = Utility.getNullSafeStringValueForDocs(ckycDetails.getCraWaiverSource(), BLANK);
        String isCraWaivedByAml = Utility.convertToYesOrNo(ckycDetails.getIsCraWaivedByAml());
        String craPincodeByCkyc = Utility.getNullSafeStringValueForDocs(ckycDetails.getCraPinCodeFromCkyc(), BLANK);
        String craCityByCkyc = Utility.getNullSafeStringValueForDocs(ckycDetails.getCraPinCityFromCkyc(), BLANK);
        String isPraWaiver = Utility.convertToYesOrNo(ckycDetails.getIsPraWaiver());
        String praWaiverSource = Utility.getNullSafeStringValueForDocs(ckycDetails.getPraWaiverSource(), BLANK);
        String isPraWaivedByAml = Utility.convertToYesOrNo(ckycDetails.getIsPraWaivedByAml());
        String praPincodeByCkyc = Utility.getNullSafeStringValueForDocs(ckycDetails.getPraPinCodeFromCkyc(), BLANK);
        String praCityByCkyc = Utility.getNullSafeStringValueForDocs(ckycDetails.getPraPinCityFromCkyc(), BLANK);
        String isIncomeWaiver = Utility.convertToYesOrNo(ckycDetails.getIsIncomeProofUpload());
        String isPanAadhaarLinked = Utility.convertToYesOrNo(ckycDetails.getIsPanAdhaarLinked());
        variables.put("msa", msa);
        variables.put("panWaiverSource", panWaiverSource);
        variables.put("incomeWaiverSource", incomeWaiverSource);
        variables.put("cibilScore", cibilScore);
        variables.put("crifScore", crifScore);
        variables.put("incomeWaiverFsa", incomeWaiverFsa);
        variables.put("isCraWaiver", isCraWaiver);
        variables.put("craWaiverSource", craWaiverSource);
        variables.put("isCraWaivedByAml", isCraWaivedByAml);
        variables.put("craPincodeByCkyc", craPincodeByCkyc);
        variables.put("craCityByCkyc", craCityByCkyc);
        variables.put("isPraWaiver", isPraWaiver);
        variables.put("praWaiverSource", praWaiverSource);
        variables.put("isPraWaivedByAml", isPraWaivedByAml);
        variables.put("praPincodeByCkyc", praPincodeByCkyc);
        variables.put("praCityByCkyc", praCityByCkyc);
        variables.put("isIncomeWaiver", isIncomeWaiver);
        variables.put("isPanAadhaarLinked", isPanAadhaarLinked);
    }
    private void setValuesFromEkycDetails(ProposalDetails proposalDetails, Map<String, Object> variables) {
        logger.info("[SummaryDocument] Setting values from ekycDetails for equoteNumber {}, transactionId {}",
                proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        EkycDetails ekycDetails;
        if (Objects.nonNull(proposalDetails.getEkycDetails())) {
            ekycDetails = proposalDetails.getEkycDetails();
        } else {
            ekycDetails = new EkycDetails();
        }
        String isHaltMedicalWaiver = Utility.convertToYesOrNo(ekycDetails.getIsHaltMedicalWaiver());
        String isHaltIncomeProofWaiver = Utility.convertToYesOrNo(ekycDetails.getIsHaltIncomeProofWaiver());
        String videoMer = Utility.convertToYesOrNo(ekycDetails.getVideoMerFlag());
        String isDobWaiver = Utility.convertToYesOrNo(ekycDetails.getIsDobWaiver());
        String dobWaiverSource = Utility.getNullSafeStringValueForDocs(ekycDetails.getDobProofWaiverSource(), BLANK);
        String isPanWaiver = Utility.convertToYesOrNo(ekycDetails.getIsPanWaiver());
        variables.put("isHaltMedicalWaiver", isHaltMedicalWaiver);
        variables.put("isHaltIncomeProofWaiver", isHaltIncomeProofWaiver);
        variables.put("videoMer", videoMer);
        variables.put("isDobWaiver", isDobWaiver);
        variables.put("dobWaiverSource", dobWaiverSource);
        variables.put("isPanWaiver", isPanWaiver);
    }
    private void setIncomeWaiverIncome(ProposalDetails proposalDetails, Map<String, Object> variables) {
        String incomeWaiverIncome;
        String declaredAnnualIncome = null;
        String creditBureauIncome;
        if (Objects.nonNull(proposalDetails.getEmploymentDetails()) &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation()) &&
                !proposalDetails.getEmploymentDetails().getPartiesInformation().isEmpty() &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0)) &&
                Objects.nonNull(proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails())) {
            declaredAnnualIncome = proposalDetails.getEmploymentDetails().getPartiesInformation().get(0).getBasicDetails().getAnnualIncome();
        }
        if (Objects.nonNull(proposalDetails.getCkycDetails())) {
            String incomeWaiverSource = proposalDetails.getCkycDetails().getIncomeWaiverSource();
            if (CRIF.equalsIgnoreCase(incomeWaiverSource) &&
                    !StringUtils.isEmpty(proposalDetails.getCkycDetails().getCrifEstimatedIncome())) {
                creditBureauIncome = getIncomeFromIncomeRange(proposalDetails.getCkycDetails().getCrifEstimatedIncome());
            } else if (CIBIL.equalsIgnoreCase(incomeWaiverSource) &&
                    !StringUtils.isEmpty(proposalDetails.getCkycDetails().getCibilEstimatedIncome())) {
                creditBureauIncome = getIncomeFromIncomeRange(proposalDetails.getCkycDetails().getCibilEstimatedIncome());
            } else {
                creditBureauIncome = null;
            }
        } else {
            creditBureauIncome = null;
        }
        if (Objects.nonNull(declaredAnnualIncome) && Objects.nonNull(creditBureauIncome)) {
            incomeWaiverIncome = getMinimum(declaredAnnualIncome, creditBureauIncome);
        } else if (Objects.nonNull(declaredAnnualIncome)) {
            incomeWaiverIncome = declaredAnnualIncome;
        } else if (Objects.nonNull(creditBureauIncome)) {
            incomeWaiverIncome = creditBureauIncome;
        } else {
            incomeWaiverIncome = BLANK;
        }
        logger.info("[SummaryDocument] Setting incomeWaiverIncome as {} for equoteNumber {}, transactionId {}",
                incomeWaiverIncome, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        variables.put("incomeWaiverIncome", incomeWaiverIncome);
    }

    private String getIncomeFromIncomeRange(String incomeRange) {
        if (Objects.nonNull(incomeRange) && incomeRange.contains("-") && incomeRange.indexOf('-') != incomeRange.length() - 1) {
            return incomeRange.substring(incomeRange.indexOf('-') + 1);
        }
        return null;
    }

    private String getMinimum(String s1, String s2) {
        double d1 = Double.parseDouble(s1);
        double d2 = Double.parseDouble(s2);
        double min = Math.min(d1, d2);
        return (min == d1) ? s1 : s2;
    }

    private DocumentStatusDetails saveGeneratedDocumentToS3(ProposalDetails proposalDetails,
                                                            String pdfDocumentOrDocumentStatus) {
        DocumentRequestInfo documentRequestInfo = new DocumentRequestInfo(DOCUMENT_TYPE,
                pdfDocumentOrDocumentStatus, NEO_SUMMARY_DOCUMENT);
        List<DocumentRequestInfo> documentPayload = new ArrayList<>();
        documentPayload.add(documentRequestInfo);
        DocumentDetails documentDetails = new DocumentDetails(proposalDetails.getChannelDetails().getChannel(),
                proposalDetails.getTransactionId(), "SUMMARY_DOCUMENT_PDF",
                NEO_SUMMARY_DOCUMENT, documentPayload);

        String documentUploadStatus = documentHelper.executeSaveDocumentToS3(documentDetails, 0);
        if (FAILED.equalsIgnoreCase(documentUploadStatus)) {
            // update the document upload failure status in DB
            logger.info("[SummaryDocument] Document upload failed for equoteNumber {}, transactionId {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    DOCUMENT_UPLOAD_FAILED, NEO_SUMMARY_DOCUMENT);
        } else {
            // update the document upload success status in DB
            logger.info("[SummaryDocument] Document successfully uploaded to S3 for equoteNumber {}, transactionId {}",
                    proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
            return documentHelper.populateDocumentStatusObj(true, proposalDetails, proposalDetails.getTransactionId(),
                    documentUploadStatus, NEO_SUMMARY_DOCUMENT);
        }
    }
}
