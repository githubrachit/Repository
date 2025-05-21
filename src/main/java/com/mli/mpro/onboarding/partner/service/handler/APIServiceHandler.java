package com.mli.mpro.onboarding.partner.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.auditservice.AuditService;
import com.mli.mpro.auditservice.models.AuditingDetails;
import com.mli.mpro.auditservice.models.ResponseObject;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.onboarding.partner.model.InputRequest;
import com.mli.mpro.onboarding.partner.model.SOAResponse;
import com.mli.mpro.onboarding.partner.util.EncodDecodUtil;
import com.mli.mpro.onboarding.partner.validation.Validation;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.onboarding.util.EncryptionDecryptionTransformUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import com.mli.mpro.productRestriction.service.impl.ProductRestrictionServiceImpl;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.EncryptionDecryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public abstract class APIServiceHandler {

	private static final Logger logger = LoggerFactory.getLogger(APIServiceHandler.class);

	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
    private AuditService auditService;
	
	@Autowired
	private ErrorMessageConfig errorMessageConfig;
	
	@Autowired
	private EncodDecodUtil encodDecodUtil;

	@Autowired
	private ProductRestrictionServiceImpl productRestrictionService;
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public String decrypt(RequestResponse inputRequest, MultiValueMap<String, String> headers) throws GeneralSecurityException {
		
		
		String key = encodDecodUtil.getEncDecryptKey(headers);
		String decryptedString = null;
		
		logger.debug("Encryption key is {} ", key);
		
//		String decryptedString = inputRequest.getPayload();// Util.decryptRequest(inputRequest.getPayload());
	
		if(key==null) {
			key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
		}

		decryptedString = EncryptionDecryptionTransformUtil.decrypt(inputRequest.getPayload(), key) ;
		logger.info("APIServiceHandler Decrypt request is {} ", decryptedString);
		return decryptedString;
	}
	

	
	public InputRequest deserialize(String decryptedString) throws Exception {
		
		
		InputRequest inputRequest = null;
		
		try {
			
			inputRequest = objectMapper.readValue(decryptedString, InputRequest.class);
			return inputRequest;
			
		} catch (JsonMappingException e) {
			
			logger.error("Error while mapping the string : {} to InputRequest ", decryptedString);
			e.printStackTrace();
			throw e;
		} catch (JsonProcessingException e) {
			
			logger.error("Error while processing the string : {} to InputRequest ", decryptedString);
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public abstract Validation validate(String inputRequest)  throws JsonProcessingException;

	public abstract String setValidationErrorResponse(MsgInfo msgInfo) throws JsonProcessingException;
	
	public InputRequest transformRequest(InputRequest inputRequest) {
		logger.info("APIServiceHandler transformRequest request is {}",inputRequest);
		return inputRequest;
	}
	
	public abstract SOAResponse invokeService(InputRequest inputRequest) throws Exception;
	
	public abstract RequestResponse transformResponse(SOAResponse soaResponse,InputRequest inputRequest) throws JsonProcessingException, UserHandledException;
	
	public abstract RequestResponse processRsponse(RequestResponse requestResponse);

	public abstract String encryptErrorResponse(Exception ex, String key) throws Exception;

	public String encryptResponse(RequestResponse requestResponse, MultiValueMap<String, String> headers) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		logger.info("APIServiceHandler encryptResponse request");
		
		String key = encodDecodUtil.getEncDecryptKey(headers);

		if(key==null){
		    key = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get(AppConstants.ENC_KEY);
		}

		String encryptedResponse = EncryptionDecryptionTransformUtil.encrypt(requestResponse.getPayload(), key);
		
		logger.info("Response to be encrypted is : {}", requestResponse.getPayload());
		
		requestResponse.setPayload(encryptedResponse);
		
		return requestResponse.getPayload();
	}

	
	/**
	 * Only this method from the class is ment to be invoked for API service class.
	 * @throws Exception 
	 */
	public RequestResponse handelRequest(RequestResponse requestResponse, MultiValueMap<String, String> headers, ErrorResponseParams errorResponseParams) throws Exception {

		String encryptionSource=null;
		String kek=null;
		String decryptedRequest = null;
		RequestResponse responsePayload = null;
		InputRequest inputRequest = null;
		String channelSource = null;
		try {
			logger.info("Inside handleRequest");
			encryptionSource = productRestrictionService.getEncryptionSource(headers);
			if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
				kek = headers.getFirst("kek");
				logger.info("Inside ujjivan utility kek {} ", kek);
				errorResponseParams.setEncryptionSource(encryptionSource);
				decryptedRequest = productRestrictionService.decryption(requestResponse.getPayload(), kek, errorResponseParams);
				//utility code for Ujjivan
				logger.info("Inside ujjivan utility decryptedString {} ", decryptedRequest);
			} else {
				decryptedRequest = this.decrypt(requestResponse, headers);
			}
			inputRequest = this.deserialize(decryptedRequest);
			Validation validation = this.validate(decryptedRequest);
			if(validation.isValid()) {
				this.transformRequest(inputRequest);
				SOAResponse soaResponse = this.invokeService(inputRequest);
				responsePayload = this.transformResponse(soaResponse,inputRequest);
			} else {
				responsePayload = new RequestResponse();
				MsgInfo msgInfo = new MsgInfo();
				msgInfo.setMsgCode(AppConstants.BAD_REQUEST_CODE);
				msgInfo.setMsgDescription(AppConstants.BAD_REQUEST_MESSAGE);
				msgInfo.setErrors(new ArrayList(validation.getErrors()));
				responsePayload.setPayload(setValidationErrorResponse(msgInfo));
			}

			this.processRsponse(responsePayload);

			if (AppConstants.UJJIVAN.equalsIgnoreCase(encryptionSource)) {
				responsePayload.setPayload(EncryptionDecryptionUtil.encrypt(responsePayload.getPayload(), errorResponseParams.getIVandKey()[0], errorResponseParams.getIVandKey()[1].getBytes()));
			} else {
				this.encryptResponse(responsePayload, headers);
			}
		} catch (UserHandledException e) {
            logger.error("Error occurred for request object {}", Util.getExceptionAsString(e));
          responsePayload = new RequestResponse();
          responsePayload.setPayload(this.encryptErrorResponse(e, encodDecodUtil.getEncDecryptKey(headers)));
          return responsePayload;
		} catch (Exception e) {
            logger.error("Error occurred for request object {}", Util.getExceptionAsString(e));
            responsePayload = new RequestResponse();
            responsePayload.setPayload(this.encryptErrorResponse(e, encodDecodUtil.getEncDecryptKey(headers)));
            return responsePayload;
        }
		return responsePayload;
	}


}
