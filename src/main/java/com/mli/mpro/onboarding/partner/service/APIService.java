package com.mli.mpro.onboarding.partner.service;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.onboarding.model.RequestResponse;
import com.mli.mpro.productRestriction.models.ErrorResponseParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.MultiValueMap;


public interface APIService {

	public RequestResponse handleReplacementSale(RequestResponse inputRequest, MultiValueMap<String, String> headers);


	public RequestResponse handleFeatureFlagDisable(MultiValueMap<String, String> headerMap);

	public RequestResponse handleDedupeAPI(RequestResponse inputRequest, MultiValueMap<String, String> headers);

	public RequestResponse handleGatewayAgent360(RequestResponse inputRequest, MultiValueMap<String, String> headers) throws Exception;

	public RequestResponse handlePBFeatureFlagDisable(MultiValueMap<String, String> headerMap);

	public RequestResponse handleGetProposalNumber(RequestResponse inputRequest, MultiValueMap<String, String> headers, ErrorResponseParams errorResponseParams);

}

