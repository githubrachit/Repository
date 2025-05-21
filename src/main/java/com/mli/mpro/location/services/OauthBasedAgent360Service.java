package com.mli.mpro.location.services;

import java.security.GeneralSecurityException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mli.mpro.agent.models.AgentResponse;
import com.mli.mpro.agent.models.RequestPayload;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.models.mfaOauthAgent360.MFAPayload;
import com.mli.mpro.location.models.mfaOauthAgent360.OauthBasedAgent360Response;
import com.mli.mpro.location.models.mfaOauthAgent360.ResponseData;

public interface OauthBasedAgent360Service {
    String getOauthToken() throws UserHandledException;
    String getNewOauthToken() throws UserHandledException;
    OauthBasedAgent360Response oauthBasedAgent360Call(MFAPayload payload, String token, String tokenRequired) throws UserHandledException;
	OauthBasedAgent360Response oauthBasedAgent360CallV3(MFAPayload payload, String token, String tokenRequired)
			throws UserHandledException, GeneralSecurityException, JsonProcessingException;
    OauthBasedAgent360Response setOauthBasedAgentResponse(ResponseData responseData, UserHandledException ex);
	AgentResponse agent360V3(RequestPayload payload, String token)
			throws UserHandledException, GeneralSecurityException, JsonProcessingException;

}
