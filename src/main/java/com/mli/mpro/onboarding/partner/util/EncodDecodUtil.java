package com.mli.mpro.onboarding.partner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.mfaOauthAgent360.ErrorResponse;
import com.mli.mpro.onboarding.model.MsgInfo;
import com.mli.mpro.onboarding.partner.model.ReplacementOutputResponse;
import com.mli.mpro.onboarding.partner.model.ReplacementSaleAPIResponse;
import com.mli.mpro.onboarding.partner.model.ReplacementSaleReponsePayload;
import com.mli.mpro.onboarding.util.AESEncryptDecryptUtil;
import com.mli.mpro.onboarding.util.EncryptionDecryptionTransformUtil;
import com.mli.mpro.onboarding.util.Util;
import com.mli.mpro.productRestriction.util.AppConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class EncodDecodUtil {
	
	private final Logger logger = LoggerFactory.getLogger(EncodDecodUtil.class);
	
	@Value("#{${urlDetails.partner.enc.dec.key.map}}")
	private Map<String, String> partnerKeyMap;
	
	@Value("#{${urlDetails.partner.clientid.partner.map}}")
	private Map<String, String> clientIdPartnerMap;
	
	@Value("${jwt.secret.key}")
	private String jwtTokenSecretKey;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	public String getEncDecryptKey(MultiValueMap<String, String> headers) {
		
		String clientId = getClientIdFromToken(headers);
		String key = null;
		
				
		if(null != clientId) {
			key = getPartnerSpecificAPIKey(clientId);// pick this from auth header
		}
		
		logger.error("Encryption decryption key is {} for clientId {}", key, clientId);
		
		return key;
	}

	public String getPartnerSpecificAPIKey(String clientId) {
		
		String encKey = null;
		String partnerName = clientIdPartnerMap.get(clientId);
		if(null != partnerName) {
			encKey = partnerKeyMap.get(partnerName);
		} else {
			logger.error("Given clientId {} is not mapped with any partner", clientId);
		}
		
		return encKey;
		
	}
	
	public String getClientIdFromToken(MultiValueMap<String, String> headers) {
		
		logger.info("Fetching clientId from aut token");
		
		String clientId = null;
		
		String authorizationToken = headers.getFirst(AppConstants.AUTH.toLowerCase());

		if (Objects.nonNull(authorizationToken))
			clientId = getSecrete(authorizationToken);


		return clientId;
	}

	private String getSecrete(String authorizationToken) {
		String clientId  = null;
		try {
			String[] jwtParts = authorizationToken.split("\\.");
			String payload = new String(java.util.Base64.getUrlDecoder().decode(jwtParts[1]));
			JsonObject jsonObject = new Gson().fromJson(payload, JsonObject.class);
			JsonElement payloadElement = jsonObject.get(AppConstants.CLIENTID);
			clientId = payloadElement.getAsString();
			logger.info("set secret successfully");
		} catch (Exception e) {
			logger.error("exception while getting jwt token paylaod {}", Util.getExceptionAsString(e));
		}
		return clientId;
	}


	public Map<String, String> jwtDecodeMap(String token, String secret) {
		Map<String, String> jwtDecodeMap = new HashMap<>();
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			Map<String, String> payload = claims.get("payload", Map.class);
			String clientId = payload.get(AppConstants.CLIENTID);

			if (clientId != null) {
				jwtDecodeMap.put(AppConstants.CLIENTID, clientId);
			}
			
			logger.info("token decoded successfully and clientId is : {}", clientId);
		} catch (SignatureException e) {
			jwtDecodeMap.put(AppConstants.ERROR, "Invalid Token: invalid signature");
		} catch (ExpiredJwtException e) {
			jwtDecodeMap.put(AppConstants.ERROR, "JWT token has expired");
		} catch (MalformedJwtException e) {
			jwtDecodeMap.put(AppConstants.ERROR, "JWT toekn  has malformed");
		} catch (JwtException e) {
			jwtDecodeMap.put(AppConstants.ERROR, "Invalid JWT token");
		}
		return jwtDecodeMap;
	}
	
	public String encryptErrorResponse(Exception ex, String key) throws Exception{

		ReplacementSaleAPIResponse replacementSaleResponse =  new ReplacementSaleAPIResponse();
		ReplacementOutputResponse outputResponse = new ReplacementOutputResponse();

		if(ex instanceof UserHandledException) {
			logger.error("User Handled exception occurred for request object {}", Util.getExceptionAsString(ex));

			MsgInfo msgInfo = new MsgInfo(AppConstants.BAD_REQUEST_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);
			replacementSaleResponse.setMsgInfo(msgInfo);
			outputResponse.setResponse(replacementSaleResponse);

		} else {
		    MsgInfo msgInfo = new MsgInfo(AppConstants.INTERNAL_SERVER_ERROR_CODE, AppConstants.FAIL_STATUS, AppConstants.RESPONSE_FAILURE);

			replacementSaleResponse.setMsgInfo(msgInfo);
			outputResponse.setResponse(replacementSaleResponse);
		    logger.info("Exception Response Message : {}",replacementSaleResponse);
		}
		return EncryptionDecryptionTransformUtil.encrypt(objectMapper.writeValueAsString(replacementSaleResponse),key);
	}
	
	public Map<String, String> getPartnerKeyMap() {
		return partnerKeyMap;
	}

	public void setPartnerKeyMap(Map<String, String> partnerKeyMap) {
		this.partnerKeyMap = partnerKeyMap;
	}

	public Map<String, String> getClientIdPartnerMap() {
		return clientIdPartnerMap;
	}

	public void setClientIdPartnerMap(Map<String, String> clientIdPartnerMap) {
		this.clientIdPartnerMap = clientIdPartnerMap;
	}

	public String encryptFeatureFlagError(MsgInfo errorResponse, String key) throws Exception {
		return EncryptionDecryptionTransformUtil.encrypt(objectMapper.writeValueAsString(errorResponse),key);
	}

	public String encryptPBFeatureFlagError(ErrorResponse errorResponse, String key) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		return AESEncryptDecryptUtil.encrypt(objectMapper.writeValueAsString(errorResponse),key);
	}

}
