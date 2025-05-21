package com.mli.mpro.onboarding.partner.service.impl;

import com.mli.mpro.common.exception.ErrorMessageConfig;

import com.mli.mpro.location.models.mfaOauthAgent360.ErrorResponse;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.service.APIService;
import com.mli.mpro.onboarding.partner.service.handler.APIServiceHandler;
import com.mli.mpro.onboarding.partner.service.handler.GatewayAgent360Handler;
import com.mli.mpro.onboarding.partner.util.EncodDecodUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;


@Service
public class APIServiceImpl implements APIService {
	
	private static final Logger logger = LoggerFactory.getLogger(APIServiceImpl.class);
	public static final String EXCEPTION = "Exception occured while handling excrypting error response in replacementsale request. {}";

	@Autowired
	APIServiceHandler replacementSaleServiceHandler;
	
	@Autowired
	private ErrorMessageConfig errorMessageConfig;
	
	@Autowired
	private EncodDecodUtil encodDecodUtil;

	@Autowired
	APIServiceHandler dedupeServiceHandler;


	@Autowired
	private GatewayAgent360Handler gatewayAgent360Handler;


	@Autowired
	APIServiceHandler proposalNumberServiceHandler;


	@Override
	public RequestResponse handleReplacementSale(RequestResponse inputRequest, MultiValueMap<String, String> headers){
		logger.info("ReplacementSale handle called");
		try {
			ErrorResponseParams errorResponseParams=new ErrorResponseParams();
			return replacementSaleServiceHandler.handelRequest(inputRequest, headers, errorResponseParams);
			
		} catch (Exception e) {
			logger.error(" Exception occured while handling replacementsale request. {}", Util.getExceptionAsString(e));
			RequestResponse responsePayload = new RequestResponse();
            try {
				responsePayload.setPayload(encodDecodUtil.encryptErrorResponse(e, encodDecodUtil.getEncDecryptKey(headers)));
			} catch (Exception e1) {
				logger.error(EXCEPTION, Util.getExceptionAsString(e));
			}
            return responsePayload;
			
		}

	}

	@Override
	public RequestResponse handleDedupeAPI(RequestResponse inputRequest, MultiValueMap<String, String> headers) {
		try {
			ErrorResponseParams errorResponseParams=new ErrorResponseParams();
			return dedupeServiceHandler.handelRequest(inputRequest,headers, errorResponseParams);
		} catch (Exception ex){
			logger.error(EXCEPTION, Util.getExceptionAsString(ex));
			RequestResponse responsePayload = new RequestResponse();
			try {
				responsePayload.setPayload(dedupeServiceHandler.encryptErrorResponse(ex, encodDecodUtil.getEncDecryptKey(headers)));
			} catch (Exception e) {
				logger.error(EXCEPTION, Util.getExceptionAsString(e));
			}
			return responsePayload;
		}
	}

	@Override
	public RequestResponse handleFeatureFlagDisable(MultiValueMap<String, String> headers) {
		RequestResponse requestResponse = new RequestResponse();
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setMsgCode(AppConstants.SERVICE_UNAVAILABLE_CODE);
		msgInfo.setMsg(AppConstants.SERVICE_UNAVAILABLE);
		msgInfo.setMsgDescription(AppConstants.SERVICE_UNAVAILABLE_DESC);

		try {
			requestResponse.setPayload(encodDecodUtil.encryptFeatureFlagError(msgInfo,encodDecodUtil.getEncDecryptKey(headers)));
		} catch (Exception e) {
			logger.error("Exception occured while encrypting error message for Feature flag is false");
		}
		return requestResponse;
	}

	@Override
	public RequestResponse handleGatewayAgent360(RequestResponse inputRequest, MultiValueMap<String, String> headers) throws Exception {
		RequestResponse requestResponse = new RequestResponse();
		try{
			requestResponse = gatewayAgent360Handler.handelRequest(inputRequest,headers);
		}catch (Exception e){
			logger.error("Getting exception {} while calling PB agent 360",e.getMessage());
			requestResponse.setPayload(gatewayAgent360Handler.encryptErrorResponse(e,encodDecodUtil.getEncDecryptKey(headers)));
		}
		return requestResponse;
	}

	// Handle feature flag error response for Policy Bazaar
	@Override
	public RequestResponse handlePBFeatureFlagDisable(MultiValueMap<String, String> headerMap) {
		RequestResponse requestResponse = new RequestResponse();
		ErrorResponse errorResponse = new ErrorResponse();
		List<String> errorMessages = new ArrayList<>();
		errorResponse.setErrorCode(Integer.parseInt(AppConstants.SERVICE_UNAVAILABLE_CODE));
		errorMessages.add(AppConstants.SERVICE_UNAVAILABLE);
		errorResponse.setErrorMessages(errorMessages);
		errorResponse.setErrorDescription(AppConstants.SERVICE_UNAVAILABLE_DESC);

		try{
			requestResponse.setPayload(encodDecodUtil.encryptPBFeatureFlagError(errorResponse, encodDecodUtil.getEncDecryptKey(headerMap)));
		} catch (Exception e) {
			logger.error("Exception occured while encrypting error message for PB Feature lag is false");
		}
		return requestResponse;
	}

	@Override
	public RequestResponse handleGetProposalNumber(RequestResponse inputRequest, MultiValueMap<String, String> headers, ErrorResponseParams errorResponseParams) {
		logger.info("ProposalNumber handle is called");
		try{
			return proposalNumberServiceHandler.handelRequest(inputRequest, headers, errorResponseParams);
		}catch (Exception e){
			logger.error(" Exception occured while handling getProposalNumber request. {}", Util.getExceptionAsString(e));
			RequestResponse responsePayload = new RequestResponse();
			try {
				responsePayload.setPayload(encodDecodUtil.encryptErrorResponse(e, encodDecodUtil.getEncDecryptKey(headers)));
			} catch (Exception e1) {
				logger.error(" Exception occured while handling encrypting error response in getProposalNumber request. {}", Util.getExceptionAsString(e1));
			}
			return responsePayload;
		}

	}





}
