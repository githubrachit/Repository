package com.mli.mpro.document.service.impl;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.document.service.DocumentImplementationService;
import com.mli.mpro.proposal.models.InputRequest;
import com.mli.mpro.proposal.models.irpPsmForNeo.IrpTags;
import com.mli.mpro.proposal.models.irpPsmForNeo.Suitability;
import com.mli.mpro.utils.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mli.mpro.common.models.DocumentResponsePayload;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.ProposalDetails;

import java.util.List;

@Service
public class DocumentImplementationDecider implements DocumentImplementationService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentImplementationDecider.class);

    @Autowired
    private PSMDocumentB2C psmDocumentService;

	@Override
	public DocumentResponsePayload decideDocumentToGenerate(ProposalDetails proposalDetails, String documentType) {
	DocumentResponsePayload base64Document = null;
	try {
	    switch (documentType) {
	    case "PSM":
		logger.info("PSM Document generation initiating for policy number {}", proposalDetails.getApplicationDetails().getPolicyNumber());
		
		String existingLICover = proposalDetails.getPsmDetails().getExistingLICover();
		String totalSumAssured = proposalDetails.getPsmDetails().getTotalSumAssured();
		proposalDetails.getPsmDetails().setExistingLICover(totalSumAssured);
		proposalDetails.getPsmDetails().setIsExistingLICover(existingLICover);
		base64Document = psmDocumentService.generateDocumentByType(proposalDetails);
		break;
	    default:
		base64Document = new DocumentResponsePayload();
		base64Document.setDocumentGenerationStatus("Document Type is incorrect");

	    }
	} catch (Exception ex) {
	    logger.error("Exception occured while generation document for policy number:",ex);
	    base64Document = new DocumentResponsePayload();
	    base64Document.setDocumentGenerationStatus(AppConstants.FAILED);
	}
	return base64Document;
    }

	public DocumentResponsePayload documentGenerateForNewVersion(ProposalDetails proposalDetails){
		DocumentResponsePayload base64Document = null;
		try {
			base64Document = psmDocumentService.generateDocumentVersionOne(proposalDetails);
		} catch (Exception ex){
			logger.error("Exception occured while generation document for new version IRP & PSM ",ex);
			base64Document = new DocumentResponsePayload();
			base64Document.setDocumentGenerationStatus(AppConstants.FAILED);
		}
		return base64Document;
	}

	@Override
	public String documentGenerateValidation(InputRequest inputRequest){
		String errorMessage;
		if (inputRequest == null || inputRequest.getRequest() == null || inputRequest.getRequest().getRequestData() == null
				|| inputRequest.getRequest().getRequestData().getRequestPayload() == null) {
			errorMessage = "Invalid inputRequest details";
			return errorMessage;
		}
		ProposalDetails proposalDetails = inputRequest.getRequest().getRequestData().getRequestPayload().getProposalDetails();
		if (proposalDetails == null) {
			errorMessage = "ProposalDetails can't be null";
			return errorMessage;
		}
		try {
			validateUlipProductCheck(proposalDetails);
			validateSuitability(proposalDetails);
			if(AppConstants.Y.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getIsUlipProduct())){
				validateIrpNeoDetails(proposalDetails);
			}
		} catch (UserHandledException ex){
			errorMessage = ex.getErrorMessages().get(0);
			return errorMessage;
		}
		return null;
	}

	private void validateIrpNeoDetails(ProposalDetails proposalDetails) throws UserHandledException{
		if(proposalDetails.getNeoIrp()==null){
			throw new UserHandledException(List.of("NeoIrpDetails can't be null"), HttpStatus.BAD_REQUEST);
		}
		IrpTags irpTags = proposalDetails.getNeoIrp();
		if(irpTags.getFund() == null || irpTags.getFund().isEmpty()){
			throw new UserHandledException(List.of("Fund details can't be null or empty"), HttpStatus.BAD_REQUEST);
		}
		if(irpTags.getFund().stream().anyMatch(fund -> fund.getReturns() == null)){
			throw new UserHandledException(List.of("Fund returns can't be null"), HttpStatus.BAD_REQUEST);
		}
		if(irpTags.getFund().stream().anyMatch(fund -> Utility.isAnyStringNullOrEmpty(fund.getName(), fund.getCategory(), fund.getAllocation()))){
			throw new UserHandledException(List.of("Fund details are missing"), HttpStatus.BAD_REQUEST);
		}
		if(irpTags.getFund().stream().anyMatch(fund -> (StringUtils.isEmpty(fund.getReturns().getYrs1()) && StringUtils.isEmpty(fund.getReturns().getYrs3())
				&& StringUtils.isEmpty(fund.getReturns().getYrs5())))){
			throw new UserHandledException(List.of("Fund returns are missing"), HttpStatus.BAD_REQUEST);
		}
	}

	private void validateUlipProductCheck(ProposalDetails proposalDetails) throws  UserHandledException{
		if(proposalDetails.getProductDetails()==null || proposalDetails.getProductDetails().get(0) ==null){
			throw new UserHandledException(List.of("ProductDetails can't be null"), HttpStatus.BAD_REQUEST);
		}
		if(StringUtils.isEmpty(proposalDetails.getProductDetails().get(0).getIsUlipProduct())){
			throw new UserHandledException(List.of("UlipProduct can't be null or empty"), HttpStatus.BAD_REQUEST);
		}
	}

	private void validateSuitability(ProposalDetails proposalDetails) throws UserHandledException{
		if(proposalDetails.getSuitability()==null){
			throw new UserHandledException(List.of("Suitability can't be null"), HttpStatus.BAD_REQUEST);
		}
		Suitability suitability = proposalDetails.getSuitability();
		if (Utility.isAnyStringNullOrEmpty(suitability.getAge(),
				suitability.getAnnualIncome(),
				suitability.getBuyingFor(),
				suitability.getGoalHorizon(),
				suitability.getFinancialAndFamilyGoals(),
				suitability.getPaymentPreference())){
			throw new UserHandledException(List.of("Suitability details are missing"), HttpStatus.BAD_REQUEST);
		}
		if(AppConstants.N.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getIsUlipProduct()) && Utility.isAnyStringNullOrEmpty(suitability.getRiskAppetite())){
			throw new UserHandledException(List.of("Suitability details are missing"), HttpStatus.BAD_REQUEST);
		}
	}

}