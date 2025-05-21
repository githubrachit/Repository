package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.document.models.SellerConsentQuestionnaire;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.document.models.SellerConsentDetails;
import com.mli.mpro.document.service.DocumentGenerationservice;
import com.mli.mpro.neo.models.attachment.GetAttachmentApiRequest;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProductDetails;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.proposal.models.RequestPayload;
import com.mli.mpro.proposal.models.SellerDeclaration;
import com.mli.mpro.proposal.models.SellerQuestion;
import com.mli.mpro.proposal.models.SourcingDetails;
import com.mli.mpro.proposal.models.ApplicationDetails;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.Metadata;
import com.mli.mpro.proposal.models.OutputResponse;
import com.mli.mpro.proposal.models.Request;
import com.mli.mpro.proposal.models.RequestData;
import com.mli.mpro.utils.Utility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.mli.mpro.proposal.models.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.UUID;

@Service
public class DocumentGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DocumentGenerator.class);
    private boolean isNeoOrAggregator = false;

    @Value("${urlDetails.proposalService}")
    private String proposalUrl;

    @Value("${bypass.header.encrypt.value}")
    private String api_client_secret;
    private DocumentGenerationservice nriQuestionareDocument;

    private DocumentGenerationservice psmDocument;

    private DocumentGenerationservice tradProposalForm;
    @Autowired
    @Qualifier("termProposalForm")
    private DocumentGenerationservice termProposalForm;

    private DocumentGenerationservice cipProposalForm;

    private DocumentGenerationservice ulipProposalFormDocument;
    @Autowired
    private DocumentGenerationservice hsaProposalFormDocument;

    private DocumentGenerationservice enachDocument;

    private DocumentGenerationservice issuerConfirmationCertifiacte;

    private DocumentGenerationservice panDobDocument;

    private DocumentGenerationservice payorPanDobDocument;

    private DocumentGenerationservice eInsuranceAccountFormDocument;

    private DocumentGenerationservice form60Document;

    private DocumentGenerationservice nriGstWaiverDocument;

    private DocumentGenerationservice annuityDocument;

    private DocumentGenerationservice paymentDocument;

    private DocumentGenerationservice digitalConsentDocument;

    private DocumentGenerationservice mwpaDocument;

    private DocumentGenerationservice ckycDocument;

    private DocumentGenerationservice investorRiskProfileDocument;

    private DocumentGenerationservice cibilDocument;

    private DocumentGenerationservice crifDocument;

    private DocumentGenerationservice ovdDocument;

    private DocumentGenerationservice bse500Document;

    private DocumentGenerationservice aWPProposalForm;

    private DocumentGenerationservice neoSummaryDocument;

    private DocumentGenerationservice neoPanDocument;

    private DocumentGenerationservice neoPhotoWaiverDocument;

    private DocumentGenerationservice neoPanSecondDocument;

    private DocumentGenerationservice ekycDocument;

    private DocumentGenerationservice neoNriQuestionnaireDocument;

	private DocumentGenerationservice gstWaiverDocument;


    @Autowired
    public DocumentGenerationservice neoEInsuranceAccountFormDocument;

    @Autowired
    public DocumentGenerationservice neoTradProposalForm;

    @Autowired
    public DocumentGenerationservice neoUlipProposalFormDocument;

    @Autowired
    public DocumentGenerationservice neoPaymentDocument;

    @Autowired
    public DocumentGenerationservice neoYblProposalForm;

    @Autowired
    public DocumentGenerationservice neoSWPProposalForm;

    @Autowired
    public DocumentGenerationservice neoSTPProposalForm;

    @Autowired
    private DocumentGenerationservice mliCkycDocument;

    private DocumentGenerationservice sellerDeclarationDocument;

    private DocumentGenerationservice ebccDocument;
  //FUL2-18168
    private DocumentGenerationservice covidQuestionnaireDocument;

    //FUL2-116960
    private DocumentGenerationservice annexureDocument;

    @Autowired
    public DocumentGenerationservice neoSSPProposalForm;
    @Autowired
    public DocumentGenerationservice NeoSTEPProposalForm;
    @Autowired
    public DocumentGenerationservice neoSWPJLProposalForm;
    @Autowired
    public DocumentGenerationservice neoPosProposalForm;
    @Autowired
    public DocumentGenerationservice neoNonPOSProposalForm;
    @Autowired
    public DocumentGenerationservice neoSwagPosProposalForm;
    @Autowired
    public DocumentGenerationservice neoSwagNonPosProposalForm;
    @Autowired
    public DocumentGenerationservice neoSGPPProposalForm;
    @Autowired
    public NeoMedicalScheduleDocument neoMedicalScheduleDocument;

    private DocumentGenerationservice moralHazardReportDocument;
    private DocumentGenerationservice instaCoiDocument;
    private DocumentGenerationservice autoCancellationDocument;
    private DocumentGenerationservice oasDocument;

    @Autowired
    private DocumentGenerationservice bankAccountVerificationDocument;


    private Map<String, DocumentGenerationservice> beans = new HashMap<>();

    @Value("${thanos.flow.enabled}")
    private boolean thanosFlowEnabled;

    @Autowired
    @Qualifier("NeoGstWaiverDocument")
    private NeoGstWaiverDocument neoGstWaiverDocument;

    @Autowired
    @Qualifier("neoPennyDropDocument")
    private DocumentGenerationservice neoPennyDropDocument;
    @Autowired
    private DocumentGenerationservice customerInformationSheetDocument;
    @Autowired
    private DocumentGenerationservice neoCISDocument;

    @Autowired
    private DocumentGenerationservice neoCISRiderDocument;
    @Autowired
    private DocumentGenerationservice neoMWPADocument;
    @Autowired
    private DocumentGenerationservice neoPayUDocument;
    @Autowired
    private DocumentGenerationservice customerInformationSheetRiderDocument;

    @Autowired
    public DocumentGenerator(DocumentGenerationservice nriQuestionareDocument, DocumentGenerationservice nriGstWaiverDocument, DocumentGenerationservice psmDocument,DocumentGenerationservice issuerConfirmationCertifiacte,
                             DocumentGenerationservice tradProposalForm, DocumentGenerationservice cipProposalForm, DocumentGenerationservice ulipProposalFormDocument, DocumentGenerationservice hsaProposalFormDocument,
                             DocumentGenerationservice enachDocument, DocumentGenerationservice panDobDocument, DocumentGenerationservice payorPanDobDocument,
                             DocumentGenerationservice eInsuranceAccountFormDocument, DocumentGenerationservice form60Document, DocumentGenerationservice annuityDocument,
                             DocumentGenerationservice paymentDocument, DocumentGenerationservice digitalConsentDocument, DocumentGenerationservice ckycDocument,
                             DocumentGenerationservice mwpaDocument, DocumentGenerationservice investorRiskProfileDocument,
                             DocumentGenerationservice cibilDocument,
                             DocumentGenerationservice crifDocument, DocumentGenerationservice ovdDocument, DocumentGenerationservice bse500Document,
                             DocumentGenerationservice AWPProposalForm, DocumentGenerationservice neoEbccAxisDocument, DocumentGenerationservice neoCkycDownloadDocument,
                             DocumentGenerationservice neoSummaryDocument,DocumentGenerationservice neoPanDocument
            , DocumentGenerationservice neoShieldProRiskDocument, DocumentGenerationservice annuityProposalForm, DocumentGenerationservice neoPhotoWaiverDocument, DocumentGenerationservice covidQuestionnaireDocument
            ,DocumentGenerationservice sellerDeclarationDocument,DocumentGenerationservice moralHazardReportDocument, DocumentGenerationservice ebccDocument,
        DocumentGenerationservice neoPanSecondDocument,DocumentGenerationservice mliCkycDocument, DocumentGenerationservice neoNriQuestionnaireDocument,DocumentGenerationservice oasDocument,
                             DocumentGenerationservice instaCoiDocument,DocumentGenerationservice gstWaiverDocument, DocumentGenerationservice autoCancellationDocument, DocumentGenerationservice ekycDocument,DocumentGenerationservice annexureDocument,DocumentGenerationservice customerInformationSheetDocument,
                             DocumentGenerationservice neoCISDocument, DocumentGenerationservice customerInformationSheetRiderDocument, DocumentGenerationservice neoMWPADocument) {
        super();
        this.nriQuestionareDocument = nriQuestionareDocument;
        this.nriGstWaiverDocument = nriGstWaiverDocument;
        this.psmDocument = psmDocument;
        this.tradProposalForm = tradProposalForm;
        this.cipProposalForm = cipProposalForm;
        this.ulipProposalFormDocument = ulipProposalFormDocument;
        this.hsaProposalFormDocument = hsaProposalFormDocument;
        this.enachDocument = enachDocument;
        this.issuerConfirmationCertifiacte = issuerConfirmationCertifiacte;
        this.panDobDocument = panDobDocument;
        this.payorPanDobDocument = payorPanDobDocument;
        this.eInsuranceAccountFormDocument = eInsuranceAccountFormDocument;
        this.form60Document = form60Document;
        this.annuityDocument = annuityDocument;
        this.paymentDocument = paymentDocument;
        this.digitalConsentDocument = digitalConsentDocument;
        this.ckycDocument = ckycDocument;
        this.mwpaDocument = mwpaDocument;
        this.investorRiskProfileDocument = investorRiskProfileDocument;
        this.cibilDocument = cibilDocument;
        this.crifDocument = crifDocument;
        this.ebccDocument = ebccDocument;
        this.aWPProposalForm = AWPProposalForm;
        this.neoSummaryDocument = neoSummaryDocument;
        this.covidQuestionnaireDocument = covidQuestionnaireDocument;//FUL2-19749
        this.sellerDeclarationDocument= sellerDeclarationDocument;
        this.neoPhotoWaiverDocument = neoPhotoWaiverDocument;
         this.mliCkycDocument = mliCkycDocument;
	this.ebccDocument = ebccDocument;
  this.sellerDeclarationDocument= sellerDeclarationDocument;
	this.covidQuestionnaireDocument = covidQuestionnaireDocument;//FUL2-18168
    this.moralHazardReportDocument = moralHazardReportDocument;
    this.ekycDocument = ekycDocument;
    this.neoNriQuestionnaireDocument = neoNriQuestionnaireDocument;
        this.instaCoiDocument = instaCoiDocument;
        this.autoCancellationDocument = autoCancellationDocument;
        this.oasDocument = oasDocument;
        this.gstWaiverDocument = gstWaiverDocument;
        this.annexureDocument = annexureDocument;
        this.customerInformationSheetDocument = customerInformationSheetDocument;
        this.neoCISDocument = neoCISDocument;
        this.customerInformationSheetRiderDocument = customerInformationSheetRiderDocument;
        this.neoMWPADocument = neoMWPADocument;
    beans.put(AppConstants.MHR_DOCUMENT,moralHazardReportDocument);
    beans.put("COVID_QUESTIONAIRE", covidQuestionnaireDocument);//FUL2-18168
        beans.put("Annexure_Details", annexureDocument);
        beans.put(AppConstants.FACT_FINDER, psmDocument);
        beans.put(AppConstants.MHR_DOCUMENT,moralHazardReportDocument);
        beans.put(AppConstants.NRI_QUESTIONAIRE, nriQuestionareDocument);
        beans.put(AppConstants.NRI_GST_WAIVER, nriGstWaiverDocument);
        beans.put(AppConstants.PROPOSAL_FORM_TRAD, tradProposalForm);
        beans.put(AppConstants.PROPOSAL_FORM_TERM, termProposalForm);
        beans.put(AppConstants.PROPOSAL_FORM_CIP, cipProposalForm);
        beans.put(AppConstants.PROPOSAL_FORM_ULIP, ulipProposalFormDocument);
        beans.put(AppConstants.PROPOSAL_FORM_HSA, hsaProposalFormDocument);
        beans.put(AppConstants.ECS_MANDATE, enachDocument);
        beans.put(AppConstants.ISSUER_CONFIRMATION, issuerConfirmationCertifiacte);
        beans.put(AppConstants.PANDOB_DOCUMENT, panDobDocument);
        beans.put(AppConstants.PAYER_PANDOB_DOCUMENT, payorPanDobDocument);
        beans.put(AppConstants.EIA_DOCUMENT, eInsuranceAccountFormDocument);
        beans.put(AppConstants.FORM60_DOCUMENT, form60Document);
        beans.put(AppConstants.ANNUITY_DOCUMENT, annuityDocument);
        beans.put(AppConstants.PAYMENT_DOCUMENT, paymentDocument);
        beans.put(AppConstants.DIGITAL_CONSENT_DOCUMENT, digitalConsentDocument);
        beans.put(AppConstants.CKYC_DOCUMENT, ckycDocument);
        beans.put(AppConstants.OBJECTIVETYPE_MWPA, mwpaDocument);
        beans.put(AppConstants.INVESTOR_RISK_PROFILE, investorRiskProfileDocument);
        beans.put(AppConstants.CIBIL, cibilDocument);
        beans.put(AppConstants.CRIF, crifDocument);
        beans.put(AppConstants.OVD, ovdDocument);
        beans.put(AppConstants.BSE500, bse500Document);
        beans.put(AppConstants.AWP_PROPOSAL_FORM, AWPProposalForm);
        beans.put(AppConstants.EBCC_AXIS, neoEbccAxisDocument);
        beans.put(AppConstants.CKYC_DOWNLOAD_DOCUMENT, neoCkycDownloadDocument);
        beans.put(AppConstants.NEO_SUMMARY_DOCUMENT, neoSummaryDocument);
        beans.put(AppConstants.PAN_VALIDATE_DOCUMENT_NAME, neoPanDocument);
        beans.put(AppConstants.SHIELD_PRO_RISK_DOCUMENT,neoShieldProRiskDocument);
        beans.put(AppConstants.PROPOSAL_FORM_GLIP,annuityProposalForm);
        beans.put(AppConstants.PHOTO_WAIVER_DOCUMENT, neoPhotoWaiverDocument);
        beans.put(AppConstants.EBCC_DOCUMENT, ebccDocument);
        beans.put(AppConstants.PAN_2_DOCUMENT, neoPanSecondDocument);
        beans.put(AppConstants.MLIC_CKYC,mliCkycDocument);
        beans.put(AppConstants.INSTA_COI_DOCUMENT,instaCoiDocument);
        beans.put(AppConstants.AUTO_GENERATE_OAS_DOCUMENT, oasDocument);
        beans.put(AppConstants.GST_WAIVER, gstWaiverDocument);
        beans.put(AppConstants.AUTO_CANCELLATION_DOCUMENT, autoCancellationDocument);
        beans.put(AppConstants.EKYC_DOCUMENT, ekycDocument);
        beans.put(AppConstants.CIS_DOCUMENT,customerInformationSheetDocument);
        beans.put(AppConstants.CIS_RIDER_DOCUMENT,customerInformationSheetRiderDocument);
    }


    /**
     * This method initiates the different document generation asyncrohonously
     */
    public List<Object> initiateDocumentGeneration(RequestPayload requestPayload) throws UserHandledException {
        validateRequestDetails(requestPayload);
	List<Object> message = new ArrayList<>();
	String retryType = requestPayload.getRetryCategory();
        ProposalDetails proposalDetails = requestPayload.getProposalDetails();
        String channel = proposalDetails.getChannelDetails() != null ? proposalDetails.getChannelDetails().getChannel() : null;
        isNeoOrAggregator = AppConstants.CHANNEL_NEO.equalsIgnoreCase(channel) || AppConstants.CHANNEL_AGGREGATOR.equalsIgnoreCase(channel)
                ? true : false;
        String bankJourney = proposalDetails.getChannelDetails() != null ? proposalDetails.getBankJourney() : null;
        if(Objects.nonNull(proposalDetails.getProductDetails().get(0).getProductInfo()) && Objects.nonNull(proposalDetails.getSourcingDetails())  && AppConstants.YES.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getIsPosp())){
            proposalDetails.getSourcingDetails().setPosSeller(true);
        }
        boolean isPosSeller = proposalDetails.getSourcingDetails() != null && proposalDetails.getSourcingDetails().isPosSeller();
        logger.info("isNeoOrAggregator value :: {}, isPos Seller : {}, retryType value : {}, bankJourney :{}, channel : {}",isNeoOrAggregator,isPosSeller, retryType, bankJourney, channel);

        logger.info("Initiating document generation for transactionId {} policyNumber and for channel {}", proposalDetails.getTransactionId(), channel);
        try {
          if (proposalDetails != null) {
            if (requestPayload.getSellerConsentDetails()!= null && requestPayload.getSellerConsentDetails().getLastModifiedDate()!= null ) {
              SellerDeclaration sellerDeclaration = proposalDetails.getSellerDeclaration();
              if (Objects.isNull(sellerDeclaration)) {
                sellerDeclaration = new SellerDeclaration();
                proposalDetails.setSellerDeclaration(sellerDeclaration);
              }
              sellerDeclaration.setDeclarationSubmitTime(requestPayload.getSellerConsentDetails().getLastModifiedDate());
            }
            initiateDocGeneration(message, retryType, requestPayload, bankJourney, channel,
                isPosSeller);
          }
        } catch (Exception ex) {
            logger.error("Error in fetching proposal details:", ex);
            message.add("Failed to initiate the document generation for transactionId " + proposalDetails.getTransactionId());
            return message;
        }
        return message;
    }

    private List<Object> initiateDocGeneration(	List<Object> message ,String retryType,RequestPayload requestPayload,
                                           String bankJourney,String channel,boolean isPosSeller){
       try{
         ProposalDetails proposalDetails = requestPayload.getProposalDetails();
        if ("ALL".equalsIgnoreCase(retryType)) {
            message.add("ALL Document generation is initiated successfully for transactionId " + proposalDetails.getTransactionId());
            initiateDocumentGenerationForAllRetryType(requestPayload,bankJourney,channel,isPosSeller);
        } else if (AppConstants.PROPOSAL_FORM_DOCUMENT.equalsIgnoreCase(retryType)) {
            DocumentGenerationservice documentToGenerate = getProposalFormDocumentType(proposalDetails.getProductDetails().get(0), channel, bankJourney, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(),isPosSeller);
            documentToGenerate.generateDocument(proposalDetails);
            //FUL2-211278_CIS_DOCUMENT_INTEGRATION
            generateCISDocument(proposalDetails);
            generateRiderDocuments(proposalDetails);
        }
        else if (AppConstants.ACR_MHR_DOCUMENT.equalsIgnoreCase(retryType) && Objects
            .nonNull(requestPayload.getSellerConsentDetails())) {
          logger
              .info("ACR_MHR_DOCUMENT Generation initiated for transactionId {} and policyNumber {}",
                  proposalDetails.getTransactionId(),
                  proposalDetails.getApplicationDetails().getPolicyNumber());
          initiateSellerDocGeneration(requestPayload, channel);
          logger
              .info("ACR_MHR_DOCUMENT Generation ended for transactionId {} and policyNumber {}",
                  proposalDetails.getTransactionId(),
                  proposalDetails.getApplicationDetails().getPolicyNumber());
        } else if (AppConstants.EIA_DOCUMENT.equalsIgnoreCase(retryType)) {
            DocumentGenerationservice documentToGenerate = getEIAFormDocumentType(channel);
            documentToGenerate.generateDocument(proposalDetails);
        } else if (AppConstants.NEO_CIS_DOCUMENT.equalsIgnoreCase(retryType)) {
            neoCISDocument.generateDocument(proposalDetails);
        } else if (AppConstants.CIS_RIDER_DOCUMENT.equalsIgnoreCase(retryType)) {
            neoCISRiderDocument.generateDocument(proposalDetails);
        } else if (AppConstants.NEO_MWPA_DOCUMENT.equalsIgnoreCase(retryType)) {
            neoMWPADocument.generateDocument(proposalDetails);
        } else if (AppConstants.PAYMENT_DOCUMENT.equalsIgnoreCase(retryType)) {
            DocumentGenerationservice documentToGenerate = getPaymentDocumentType(channel);
            if (!(AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPtfPayment())
            && proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
            && proposalDetails.getProductDetails().get(0) != null
            && proposalDetails.getProductDetails().get(0).getProductInfo() != null
            && AppConstants.GLIP_ID
                    .equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId()))) {
        documentToGenerate.generateDocument(proposalDetails);
            }
        }else if(AppConstants.EKYC_DOCUMENT.equalsIgnoreCase(retryType)) {
            beans.get(retryType).generateDocument(proposalDetails);
        }else if(AppConstants.MEDICAL_SCHEDULE_DOCUMENT.equalsIgnoreCase(retryType)) {
            neoMedicalScheduleDocument.generateDocument(proposalDetails);
        }else if (AppConstants.NRI_DOCUMENT.equalsIgnoreCase(retryType)){
            neoNriQuestionnaireDocument.generateDocument(proposalDetails);
        }  else if ( AppConstants.THANOS_DOCUMENT.equalsIgnoreCase(retryType)) {
          if (!thanosFlowEnabled) {
            logger.info("thanosFlowEnabled={} .Thus no document is generated.",thanosFlowEnabled);
            message.add("thanosFlowEnabled=" + thanosFlowEnabled + " .Thus no document is generated." + proposalDetails.getTransactionId());
            return message;
          }
          enachDocument.generateDocument(proposalDetails);
          paymentDocument.generateDocument(proposalDetails);
          nriQuestionareDocument.generateDocument(proposalDetails);
          psmDocument.generateDocument(proposalDetails);
          panDobDocument.generateDocument(proposalDetails);
          payorPanDobDocument.generateDocument(proposalDetails);
          if (AppConstants.REQUEST_SOURCE_THANOS1
					.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource())
					|| (proposalDetails.getAdditionalFlags().getIsPanModifiedForThanos() != null && Boolean.FALSE
							.equals(proposalDetails.getAdditionalFlags().getIsPanModifiedForThanos()))) {

          form60Document.generateDocument(proposalDetails);
          }
            // ful2-114424
            if (!StringUtils.isEmpty(proposalDetails.getAdditionalFlags().getGstWaiverRequired())) {
                gstWaiverDocument.generateDocument(proposalDetails);
            }
          ckycDocument.generateDocument(proposalDetails);
          ebccDocument.generateDocument(proposalDetails);
          if (getEIADocumentGenerationFlag(proposalDetails)) {
            eInsuranceAccountFormDocument.generateDocument(proposalDetails);
          }
        }else if (AppConstants.DOB_DOCUMENT_INSURED.equalsIgnoreCase(retryType)){
            panDobDocument.generateDocument(proposalDetails);
        }
		else if (Boolean.TRUE.equals(proposalDetails.getAdditionalFlags().getIsForm60acceptable())
				&& AppConstants.FORM60_CATEGORY.equalsIgnoreCase(retryType)) {
			form60Document.generateDocument(proposalDetails);
		}
        else if (AppConstants.NRI.equalsIgnoreCase(proposalDetails.getPartyInformation().get(0).getBasicDetails().getResidentialStatus())
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getGstWaiverRequired())
                && AppConstants.NRI_GST_WAIVER.equalsIgnoreCase(retryType)) {
            nriGstWaiverDocument.generateDocument(proposalDetails);
        }


        else if(retryType.equalsIgnoreCase(AppConstants.FACT_FINDER)) {
            DocumentGenerationservice documentToGenerate=beans.get(retryType);
            documentToGenerate.generateDocument(proposalDetails);
        }else if(retryType.equalsIgnoreCase(AppConstants.INVESTOR_RISK_PROFILE)) {
        	//FUL2-46310
			if (Utility.isCapitalGuaranteeSolutionSecondaryProduct(proposalDetails)) {
				ProposalDetails fetchingPrimaryPolicyDetails = fetchingPrimaryPolicyDetailsBasedOnSecondaryPolicyNumber(
						proposalDetails);
				if (Objects.nonNull(fetchingPrimaryPolicyDetails)
						& Objects.nonNull(fetchingPrimaryPolicyDetails.getApplicationDetails())
						&& org.springframework.util.StringUtils
								.hasText(fetchingPrimaryPolicyDetails.getApplicationDetails().getPolicyNumber()))
					proposalDetails.getApplicationDetails()
							.setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber()
									+ AppConstants.AND
									+ fetchingPrimaryPolicyDetails.getApplicationDetails().getPolicyNumber());
			}
			beans.get(retryType).generateDocument(proposalDetails);
        }else if(retryType.equalsIgnoreCase(AppConstants.PHYSICAL_JOURNEY)) {
        	// FUL2-97658 - Documents to generate for Physical Journey
        	logger.info("Generating document for physical journey - {}", retryType);
        	enachDocument.generateDocument(proposalDetails);

        	if (getEIADocumentGenerationFlag(proposalDetails)) {
                eInsuranceAccountFormDocument.generateDocument(proposalDetails);
            }
        	if (!(AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPtfPayment())
                    && proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
                    && proposalDetails.getProductDetails().get(0) != null
                    && proposalDetails.getProductDetails().get(0).getProductInfo() != null
                    && AppConstants.GLIP_ID
                            .equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId()))) {
                paymentDocument.generateDocument(proposalDetails);
            }
        	if (getDigitalConsentGenerationFlag(proposalDetails) && !proposalDetails.getAdditionalFlags().isYblTelesalesCase()) {
                digitalConsentDocument.generateDocument(proposalDetails);
            }
        	ckycDocument.generateDocument(proposalDetails);
        	// This document is added for the requirement of Penny Drop CR - FUL2-140549.
            if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getPennyDropConsent())
    				&& AppConstants.VERIFIED.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getPennyDropVerification())) {
            	logger.info("Generating Bank Account Verification Document");
            	bankAccountVerificationDocument.generateDocument(proposalDetails);
            }
        }else if (AppConstants.NEO_GST_WAIVER_DOC.equalsIgnoreCase(retryType)) {
            neoGstWaiverDocument.generateDocument(proposalDetails);
        } else if (AppConstants.NEO_PENNY_DROP_DOC.equalsIgnoreCase(retryType)) {
            neoPennyDropDocument.generateDocument(proposalDetails);
        } else if (AppConstants.PAYU_DOCUMENT.equalsIgnoreCase(retryType)) {
            neoPayUDocument.generateDocument(proposalDetails);
        } else if(!retryType.equalsIgnoreCase(AppConstants.FACT_FINDER)) {
            message.add("Document generation is initiated sucessfully for transactionId "
                    + proposalDetails.getTransactionId());
            DocumentGenerationservice documentToGenerate = beans.get(retryType);
            documentToGenerate.generateDocument(proposalDetails);
        }

       }catch (Exception ex){
           logger.error("Error in fetching proposal details:", ex);
       }
        return message;
    }


    private void initiateDocumentGenerationForAllRetryType(RequestPayload requestPayload,String bankJourney,String channel,boolean isPosSeller){
      ProposalDetails proposalDetails = requestPayload.getProposalDetails();
        String isNPSCustomer = proposalDetails.getPartyInformation().get(0).getBasicDetails().getIsNPSCustomer();
      nriQuestionareDocument.generateDocument(proposalDetails);
      nriGstWaiverDocument.generateDocument(proposalDetails);
        //6863-PSM Document generation status flag added
            psmDocument.generateDocument(proposalDetails);
        enachDocument.generateDocument(proposalDetails);
        issuerConfirmationCertifiacte.generateDocument(proposalDetails);
        panDobDocument.generateDocument(proposalDetails);
        payorPanDobDocument.generateDocument(proposalDetails);
        form60Document.generateDocument(proposalDetails);
        ckycDocument.generateDocument(proposalDetails);
        //FUL2-116960 swiss-re changes
        annexureDocument.generateDocument(proposalDetails);
        DocumentGenerationservice documentToGenerate = getProposalFormDocumentType(proposalDetails.getProductDetails().get(0),channel, bankJourney, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(),isPosSeller);
        documentToGenerate.generateDocument(proposalDetails);
        //FUL2-211278_CIS_DOCUMENT_INTEGRATION
        generateCISDocument(proposalDetails);
        generateRiderDocuments(proposalDetails);
        if (getEIADocumentGenerationFlag(proposalDetails)) {
            eInsuranceAccountFormDocument.generateDocument(proposalDetails);
        }
        if (getAnnuityDocumentFlag(proposalDetails)) {
            annuityDocument.generateDocument(proposalDetails);
        }
        //FUL2-11834 to identify that whether this is case of YblTeleSales
        if (getDigitalConsentGenerationFlag(proposalDetails) && !proposalDetails.getAdditionalFlags().isYblTelesalesCase()) {
            digitalConsentDocument.generateDocument(proposalDetails);
        }
        //*Start* FUL2-74120 - PTF payment changes
        if (!(AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsPtfPayment())
                && proposalDetails.getProductDetails() != null && !proposalDetails.getProductDetails().isEmpty()
                && proposalDetails.getProductDetails().get(0) != null
                && proposalDetails.getProductDetails().get(0).getProductInfo() != null
                && AppConstants.GLIP_ID
                .equals(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId()))
                && !AppConstants.YES.equalsIgnoreCase(isNPSCustomer)) {
            paymentDocument.generateDocument(proposalDetails);
        }
        mwpaDocument.generateDocument(proposalDetails);
        investorRiskProfileDocument.generateDocument(proposalDetails);
        //changes for FUL2-21156
       if (requestPayload.getSellerConsentDetails() != null
           && !AppConstants.JOURNEY_TYPE_ONBOARDING.equalsIgnoreCase(proposalDetails
           .getAdditionalFlags().getJourneyType())) {
           initiateSellerDocGeneration(requestPayload, channel);
       }else{
           if(AppConstants.YES.equalsIgnoreCase(requestPayload.getProposalDetails().getAdditionalFlags().getIsSellerDeclarationApplicable())){
               moralHazardReportDocument.generateDocument(proposalDetails);
           }
       }
      if(AppConstants.TRUE.equalsIgnoreCase(proposalDetails.getApplicationDetails().getIsCkycFetchedSuccess())) {
        mliCkycDocument.generateDocument(proposalDetails);
      }
        if(checkOasDocCreationCase(proposalDetails)){
            oasDocument.generateDocument(proposalDetails); // FUL2-76970
        }
        // This document is added for the requirement of Penny Drop CR - FUL2-140549.
        if (AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getPennyDropConsent())
				&& AppConstants.VERIFIED.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getPennyDropVerification())) {
        	logger.info("Generating Bank Account Verification Document");
        	bankAccountVerificationDocument.generateDocument(proposalDetails);
        }
    }

    private boolean checkOasDocCreationCase(ProposalDetails proposalDetails){
        OasDetails oasDetails = proposalDetails.getOasDetails();
        boolean isOasCase = false;
        if(AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel())
                && AppConstants.YES.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getIsSeniorCitizen())
                && Objects.nonNull(oasDetails)){
            if((AppConstants.YES.equalsIgnoreCase(oasDetails.getStrategicBranch())
                    && Objects.nonNull(oasDetails.getBhDetails()) && AppConstants.COMPLETED.equalsIgnoreCase(oasDetails.getBhDetails().getBhApproval()))
                    || (!AppConstants.YES.equalsIgnoreCase(oasDetails.getStrategicBranch())
                    && Objects.nonNull(oasDetails.getBhDetails()) && AppConstants.COMPLETED.equalsIgnoreCase(oasDetails.getBhDetails().getBhApproval())
                    && Objects.nonNull(oasDetails.getChDetails()) && AppConstants.COMPLETED.equalsIgnoreCase(oasDetails.getChDetails().getChApproval()))
                    && !AppConstants.Y.equalsIgnoreCase(oasDetails.getDigitalOasSkipOpted())
                    ){
                isOasCase= true;
            }
         }
        return isOasCase;
    }


    //Get payment document generator implementaion on basis of channel
    private DocumentGenerationservice getPaymentDocumentType(String channel) {
        //NEORW implementation of PAYMENT RECEIPT document generation is different for neo and mpro
        if (channel == null || channel.isEmpty() || !isNeoOrAggregator) {
            return  paymentDocument;
        }
        else{
            return neoPaymentDocument;
        }
    }

    //Get eia document generator implementaion on basis of channel
    private DocumentGenerationservice getEIAFormDocumentType(String channel) {
        //NEORW implementation of EIA form document generation is different for neo and mpro
        if (channel == null || channel.isEmpty() || !isNeoOrAggregator) {
            return eInsuranceAccountFormDocument;
        }
        else{
            return neoEInsuranceAccountFormDocument;
        }
    }


    private void validateRequestDetails(RequestPayload requestPayload) throws UserHandledException {
    	if (Objects.isNull(requestPayload) ||
            Objects.isNull(requestPayload.getProposalDetails())) {
    	    List<String> errMsg = new ArrayList<>();
    	    errMsg.add("Request not validated");
            throw new UserHandledException(null, errMsg, HttpStatus.BAD_REQUEST);
        }
	}

	/**
     * This method gets the type of the bean to inject for proposal form generation
     * based on product type/product id
     */

    private DocumentGenerationservice getProposalFormDocumentType(ProductDetails productDetails, String channel,
                                                                  String bankJourney, String equoteNumber, long transactionId,boolean isPosSeller) {

        String productType = productDetails.getProductType();
        String productId = productDetails.getProductInfo().getProductId();
        String variant = productDetails.getProductInfo().getVariant();
        DocumentGenerationservice documentToGenerate = null;

        logger.info("Getting bean to inject for product {} {}, channel {}, bankJourney {}, equoteNumber {} transactionId {}",
                productType, productId, channel, bankJourney, equoteNumber, transactionId);
        //NEORW implementation of proposal form document generation is different for neo and mpro
        if (channel == null || channel.isEmpty() || !isNeoOrAggregator) {
            documentToGenerate = getMproDocumentType(productType,productId,isPosSeller,variant);
        }else{
            if (AppConstants.ULIP.equalsIgnoreCase(productType)) {
                documentToGenerate = neoUlipProposalFormDocument;
            } else if (AppConstants.AWP.equalsIgnoreCase(productType)) {
                documentToGenerate = beans.get(AppConstants.AWP_PROPOSAL_FORM);
            } else if (AppConstants.YBL.equalsIgnoreCase(bankJourney)) {
                documentToGenerate = neoYblProposalForm;
            } else if (AppConstants.SWP.equalsIgnoreCase(productType)) {
                documentToGenerate = neoSWPProposalForm;
            } else if (AppConstants.SSP.equalsIgnoreCase(productType)) {
                documentToGenerate = NeoSTEPProposalForm;
            } else if (AppConstants.NEO_STPP_PRODUCT_TYPE.equalsIgnoreCase(productType)) {
                documentToGenerate = neoSTPProposalForm;
            }
            else if (AppConstants.STEP_NEO.equalsIgnoreCase(productType)) {
                documentToGenerate = NeoSTEPProposalForm;
            }
            else if (AppConstants.SWPJL.equalsIgnoreCase(productType)) {
              documentToGenerate = neoSWPJLProposalForm;
            }else if (AppConstants.SOFD_POS.equalsIgnoreCase(productType)) {
                documentToGenerate = neoPosProposalForm;
            } else if (AppConstants.SOFD_NONPOS.equalsIgnoreCase(productType)) {
                documentToGenerate = neoNonPOSProposalForm;
            }else if(AppConstants.SWAG_POS.equalsIgnoreCase(productType)){
                documentToGenerate = neoSwagPosProposalForm;
            }else if(AppConstants.SWAG_NON_POS.equalsIgnoreCase(productType)) {
               documentToGenerate = neoSwagNonPosProposalForm;
            }else if(AppConstants.SGPP.equalsIgnoreCase(productType)
                    || AppConstants.SGPPJL.equalsIgnoreCase(productType)) {
                documentToGenerate = neoSGPPProposalForm;
            }else {
              documentToGenerate = neoTradProposalForm;
            }
        }

        return documentToGenerate;
    }

    private DocumentGenerationservice getMproDocumentType(String productType, String productId,boolean isPosSeller,String variant){

        DocumentGenerationservice documentToGenerate = null;
        if (AppConstants.HSA.equalsIgnoreCase(productType) || productId.equalsIgnoreCase("200")) {
            documentToGenerate = beans.get(AppConstants.PROPOSAL_FORM_HSA);
        } else if (AppConstants.ULIP.equalsIgnoreCase(productType)) {
            documentToGenerate = beans.get(AppConstants.PROPOSAL_FORM_ULIP);
        } else if ("86".equalsIgnoreCase(productId)) {
            documentToGenerate = beans.get(AppConstants.PROPOSAL_FORM_CIP);
        } else if (AppConstants.AWP.equalsIgnoreCase(productType)) {
            documentToGenerate = beans.get(AppConstants.AWP_PROPOSAL_FORM);
        } //Changes Done for SGPP Proposal Form Creation
        else if(AppConstants.ANNUITY_PRODUCTS.contains(productId)){
            documentToGenerate = beans.get(AppConstants.PROPOSAL_FORM_GLIP);
        } else if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.FEATURE_PF_TERM) && AppConstants.TERM_PRODUCTS.contains(productId)) {
        	return termProposalForm;
        } else {
            documentToGenerate = beans.get(AppConstants.PROPOSAL_FORM_TRAD);
        }
        return documentToGenerate;
    }

    /**
     * Check if EIA Document is to be generated
     *
     * @return
     */
    private boolean getEIADocumentGenerationFlag(ProposalDetails proposalDetails) {
        List<PartyInformation> partyInformationList = proposalDetails.getPartyInformation();
        boolean isEIAExist = proposalDetails.getEmploymentDetails().isEIAExist();
        boolean eiaNumberExist = (StringUtils.isNotBlank(proposalDetails.getEmploymentDetails().getExistingEIANumber())
        		|| StringUtils.isNotBlank(proposalDetails.getEmploymentDetails().getPreferredInsuranceRepositoryName())) ? true : false;

        String proposerCountry = "";
        String insuredCountry = "";

        if (null != partyInformationList && !CollectionUtils.isEmpty(partyInformationList) && partyInformationList.size() >= 1) {
            proposerCountry = partyInformationList.get(0).getBasicDetails().getNationalityDetails().getNationality();
        }
        if (null != partyInformationList && !CollectionUtils.isEmpty(partyInformationList) && partyInformationList.size() >= 2) {
            insuredCountry = partyInformationList.get(1).getBasicDetails().getNationalityDetails().getNationality();
        }
	if(StringUtils.isBlank(proposerCountry)) {
	    proposerCountry = AppConstants.INDIAN_NATIONALITY;
	}
	if(StringUtils.isBlank(insuredCountry)) {
	    insuredCountry = AppConstants.INDIAN_NATIONALITY;
	}
        boolean isNRI = (StringUtils.equalsIgnoreCase(proposerCountry, AppConstants.INDIAN_NATIONALITY)
                && StringUtils.equalsIgnoreCase(insuredCountry, AppConstants.INDIAN_NATIONALITY)) ? false : true;

        if (isEIAExist && !isNRI && eiaNumberExist) {
    	    return true;
    	} else {
    	    logger.info("Skipping EIA document generation...");
    	}
        return false;
    }


    /**
     * Check if YBL Digital Consent Document is to be generated
     *
     * @return
     */
    private boolean getDigitalConsentGenerationFlag(ProposalDetails proposalDetails) {
	String requestSource = proposalDetails.getAdditionalFlags().getRequestSource();
	logger.info("request source is : {}" , requestSource);
	boolean yblFlag = false;
	if (StringUtils.equalsIgnoreCase(requestSource, AppConstants.YBL)) {
	    yblFlag = true;
	    logger.info("Digital Consent doc Generation status --- {}", yblFlag);
	    return yblFlag;
	}
	logger.info("Digital Consent doc Generation status --- {}", yblFlag);
	return yblFlag;
    }

    /**
     * Check if Annuity Document is to be generated
     * @return
     */
    private boolean getAnnuityDocumentFlag(ProposalDetails proposalDetails) {
	List<ProductDetails> productDetailsList = proposalDetails.getProductDetails();
	if (null != productDetailsList && !CollectionUtils.isEmpty(productDetailsList) && productDetailsList.size() >= 1) {
	    if (StringUtils.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductName(),
		    AppConstants.FOREVER_YOUNG_PENSION_PLAN)) {
		return true;
	    } else{
		return false;}
	}
	return false;
    }

    /**
     * Check if Direct Debit Consent Form Document is to be generated
     *
     * @return
     */

    public List<Object> getPaymentAttachment(GetAttachmentApiRequest apiRequest) {
        List<Object> messages = new ArrayList<>();
        neoPaymentDocument.createDocumentAndUploadToS3(apiRequest.getRequest().getPayload());
        messages.add("Payment document generation initiated successfully!");
        return messages;
    }

    private boolean getPSMDocumnetGenerationFlag(ProposalDetails proposalDetails) {
        try {
            if (isPosAxisNonPSMCase(proposalDetails)) {
                logger.info("PSM doc not Generated for Axis Pos Seller with transactionId {}", proposalDetails.getTransactionId());
                return false;
            }
            logger.info("PSM doc generation initiating for transaction Id {}", proposalDetails.getTransactionId());
            return true;
        }catch (Exception e){
            logger.error("Error in fetching psmFlag:", e);
        }
        return false;
    }

    public boolean isPosAxisNonPSMCase(ProposalDetails proposalDetails) {

        boolean isPosAxisSeller = proposalDetails.getSourcingDetails().isPosSeller() &&
                AppConstants.CHANNEL_AXIS.equalsIgnoreCase(proposalDetails.getChannelDetails().getChannel()) &&
                AppConstants.AXIS_STRING.equalsIgnoreCase(proposalDetails.getAdditionalFlags().getRequestSource());

        return isPosAxisSeller;
    }

    public String getDocumentBase64(ProposalDetails proposalDetails, String retryCategory) throws UserHandledException {
        DocumentGenerationservice generationService = getDocumentGenerationservice(proposalDetails, retryCategory);
        String documentBase64 = generationService.getDocumentBase64(proposalDetails);
        if (AppConstants.FAILED.equalsIgnoreCase(documentBase64)) {
            logger.error("Error generating {} snapshot for equoteNumber {} transactionId {}",
                    retryCategory, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
            throw new UserHandledException(new Response(), Collections.singletonList("Error generating document"), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            logger.info("{} document snapshot successfully generated for equoteNumber {} transactionId {}",
                    retryCategory, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId());
        }
        return documentBase64;
    }

    private DocumentGenerationservice getDocumentGenerationservice(ProposalDetails proposalDetails, String retryCategory) {
        String channel = proposalDetails.getChannelDetails() != null ? proposalDetails.getChannelDetails().getChannel() : null;
        isNeoOrAggregator = Utility.isChannelNeoOrAggregator(proposalDetails);
        String bankJourney = proposalDetails.getChannelDetails() != null ? proposalDetails.getBankJourney() : null;
        boolean isPosSeller = proposalDetails.getSourcingDetails() != null && proposalDetails.getSourcingDetails().isPosSeller();
        DocumentGenerationservice generationService;
        retryCategory = StringUtils.isBlank(retryCategory) ? StringUtils.EMPTY : retryCategory;
        if (Utility.isProductSWPJL(proposalDetails)) {
            new NeoBaseProposalForm().interChangeProposerAndLifeInsured(proposalDetails);
        }
        else if( Utility.isApplicationIsForm2(proposalDetails)){
            new NeoBaseProposalForm().changeProposerAndLifeinsuredForForm2(proposalDetails);
        }
        switch (retryCategory) {
            case AppConstants.PAYMENT_DOCUMENT:
                generationService = getPaymentDocumentType(channel);
                break;
            default:
                generationService = getProposalFormDocumentType(proposalDetails.getProductDetails().get(0),
                        channel, bankJourney, proposalDetails.getEquoteNumber(), proposalDetails.getTransactionId(),isPosSeller);
        }
        return generationService;
    }

    //off means true  --> yes

  private void initiateSellerDocGeneration(RequestPayload requestPayload, String channel) {
      if (AppConstants.YES.equalsIgnoreCase(requestPayload.getProposalDetails().getAdditionalFlags().getIsSellerDeclarationApplicable())
      || AppConstants.THANOS_CHANNEL.equalsIgnoreCase(requestPayload.getProposalDetails().getChannelDetails().getChannel())) {  //FUL2-28454
          generateSellerDeclarationAndMHR(requestPayload, channel);
      }
  }

    private void generateSellerDeclarationAndMHR(RequestPayload requestPayload, String channel) {
        sellerDeclarationDocument.generateSellerDocument(requestPayload, channel);
        setProductProposalDetails(requestPayload);
        setSellerConsentDataInProposal(requestPayload);
        moralHazardReportDocument.generateDocument(requestPayload.getProposalDetails());
    }

  private void setProductProposalDetails(RequestPayload requestPayload) {
    ProposalDetails proposalDetails = requestPayload.getProposalDetails();
    if(Objects.nonNull(proposalDetails)
        && Objects.nonNull(proposalDetails.getSalesStoriesProductDetails())
        && AppConstants.YES.equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct()) && proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId() != 0L) {
        proposalDetails = fetchingPrimaryPolicyDetailsBasedOnSecondaryPolicyNumber(proposalDetails);
      requestPayload.getProposalDetails().getApplicationDetails().setPolicyNumber(proposalDetails.getApplicationDetails().getPolicyNumber()+AppConstants.COMMA+proposalDetails.getSalesStoriesProductDetails().getSecondaryPolicyNum());
    }
  }


private ProposalDetails fetchingPrimaryPolicyDetailsBasedOnSecondaryPolicyNumber(ProposalDetails proposalDetails) {
	logger.info(
	    "Entered sales story block from secondary Proposal transactionID {} fetching primary Proposal details of "
	        + "transactionId {} ", proposalDetails.getTransactionId(),
	    proposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId());
	InputRequest request = setDataForProposalService(proposalDetails);
	try {
	  logger.info("outputResponse for transactionId {}, url {}",
	      proposalDetails.getTransactionId(), proposalUrl);
        HttpHeaders httpHeaders = Utility.setAPISecretInHeaders(api_client_secret);
        HttpEntity<InputRequest> httpEntityRequest = new HttpEntity<>(request, httpHeaders);
	  ResponseEntity<OutputResponse> outputResponse = new RestTemplate().postForEntity(proposalUrl, httpEntityRequest, OutputResponse.class);
	  if (Objects.nonNull(outputResponse.getBody())
	      && outputResponse.getBody().getResponse() != null
	      && outputResponse.getBody().getResponse().getResponseData().getResponsePayload()
	      != null
	      && outputResponse.getBody().getResponse().getResponseData().getResponsePayload()
	      .getProposalDetails() != null) {
	    proposalDetails = outputResponse.getBody().getResponse().getResponseData()
	        .getResponsePayload().getProposalDetails();
	  }
	  logger.info("outputResponse for transactionId {}, response {} ", proposalDetails.getTransactionId(), outputResponse);
	} catch (Exception ex) {
	  logger.error("Exception occurred during fetching the proposal details for transactionId {} is {} ",proposalDetails.getTransactionId(),Utility.getExceptionAsString(ex));
	}
	return proposalDetails;
}

  private InputRequest setDataForProposalService(ProposalDetails existingProposalDetails) {
    logger.info("in setDataForProposalService for proposaDetails transactionId {}",existingProposalDetails.getTransactionId());
    RequestPayload requestPayload = new RequestPayload();
    InputRequest inputRequest = null;
    try {
      String env = System.getenv("env");
      Metadata metadata = new Metadata(env, UUID.randomUUID().toString());
      ProposalDetails proposalDetails = new ProposalDetails();
      ApplicationDetails applicationDetails = proposalDetails.getApplicationDetails();
      if (applicationDetails == null) {
        applicationDetails = new ApplicationDetails();
      }
      SourcingDetails sourcingDetails = proposalDetails.getSourcingDetails();
      if (proposalDetails.getSourcingDetails() == null) {
        sourcingDetails = new SourcingDetails();
      }
      sourcingDetails.setAgentId(existingProposalDetails.getSourcingDetails().getAgentId());
      applicationDetails.setPolicyNumber(existingProposalDetails.getApplicationDetails().getPolicyNumber());
      proposalDetails.setSalesStoriesProductDetails(existingProposalDetails.getSalesStoriesProductDetails());
      proposalDetails.setTransactionId(existingProposalDetails.getSalesStoriesProductDetails().getPrimaryTransactionId());
      proposalDetails.setApplicationDetails(applicationDetails);
      proposalDetails.setSourcingDetails(sourcingDetails);
      requestPayload.setProposalDetails(proposalDetails);
      RequestData requestData = new RequestData(requestPayload);
      Request request = new Request(metadata, requestData);
      inputRequest = new InputRequest(request);
      logger.info("inpur request created for transactionId {} is {}",existingProposalDetails.getTransactionId(),inputRequest);
    } catch (NullPointerException npe) {
      logger.error("Exception Occurred in setDataForProposalService for transactionId {} is {}",existingProposalDetails.getTransactionId(),
          Utility.getExceptionAsString(npe));
    }
    return inputRequest;
  }

  private void setSellerConsentDataInProposal(RequestPayload requestPayload) {
    ProposalDetails proposalDetails = requestPayload.getProposalDetails();
    SellerDeclaration sellerDeclaration = proposalDetails.getSellerDeclaration();
    if (Objects.isNull(sellerDeclaration)) {
      sellerDeclaration = new SellerDeclaration();
      proposalDetails.setSellerDeclaration(sellerDeclaration);
    }
    SellerConsentDetails sellerConsentDetails = requestPayload.getSellerConsentDetails();
    proposalDetails.setSellerConsentDetails(sellerConsentDetails);
    SourcingDetails sourcingDetails = proposalDetails.getSourcingDetails();
    if (Objects.nonNull(sourcingDetails)) {
      sourcingDetails.setSpecifiedPersonName(sellerConsentDetails.getSellerName());
      sourcingDetails.setSpecifiedPersonCode(sellerConsentDetails.getSpCode());
    }
    sellerDeclaration
        .setSellerDisclosure(sellerConsentDetails.getSellerDisclosure() ? AppConstants.YES : AppConstants.NO);
    if (AppConstants.YES.equalsIgnoreCase(sellerDeclaration.getSellerDisclosure())) {
      List<SellerQuestion> sellerQuestions = new ArrayList<>();
      List<SellerConsentQuestionnaire> questionnaires = sellerConsentDetails.getSellerConsentQuestionnaire();
      if (!CollectionUtils.isEmpty(questionnaires)) {
        questionnaires.stream().forEach(sellerQuestionnaire -> {
          SellerQuestion sellerQuestion = new SellerQuestion();
          sellerQuestion.setQuestionId(sellerQuestionnaire.getQuestionId());
          sellerQuestion.setAnswer(sellerQuestionnaire.getAnswer());
          sellerQuestion.setAdditionalInformation(sellerQuestionnaire.getAdditionalInformation());
          sellerQuestions.add(sellerQuestion);
        });
        sellerDeclaration.setMhrSellerQuestions(sellerQuestions);
      }
      sellerDeclaration.setSellerConfirmation(true);
      sellerDeclaration.setPeriod(sellerConsentDetails.getPeriod());
      sellerDeclaration.setPeriodUnit(sellerConsentDetails.getPeriodUnit());
    }
  }
    private void generateRiderDocuments(ProposalDetails proposalDetails) {
        List<String> applicableRiders = proposalDetails.getProductDetails().get(0).getProductInfo().getApplicableCisRiders();
        if (applicableRiders != null && !applicableRiders.isEmpty()) {
            customerInformationSheetRiderDocument.generateDocument(proposalDetails);
        }
    }
   private void generateCISDocument(ProposalDetails proposalDetails){
       if( AppConstants.CAMEL_YES.equalsIgnoreCase(Utility.getGenerateCISOrNot(proposalDetails))){
           customerInformationSheetDocument.generateDocument(proposalDetails);
       }
   }
}
