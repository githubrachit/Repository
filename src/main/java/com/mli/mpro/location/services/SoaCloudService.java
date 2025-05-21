package com.mli.mpro.location.services;

import com.mli.mpro.agent.models.AgentResponse;
import com.mli.mpro.agent.models.RequestPayload;
import com.mli.mpro.agent.models.SoaApiRequest;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.location.amlulip.training.model.SoaAmlRequest;
import com.mli.mpro.location.amlulip.training.model.SoaResponsePayload;
import com.mli.mpro.location.login.model.Result;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.PolicyHistoryRequest;
import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.PolicyHistoryResponse;
import com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary.AgentCommissionSummaryRequest;
import com.mli.mpro.location.models.soaCloudModels.agentCommissionSummary.AgentCommissionSummaryResponse;
import com.mli.mpro.location.models.soaCloudModels.master360RequestModels.Request;
import com.mli.mpro.location.models.soaCloudModels.master360ResponseModels.Response;
import com.mli.mpro.location.models.soaCloudModels.SoaClient360ResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.SoaCloudResponse;
import com.mli.mpro.location.models.soaCloudModels.policySplittingModels.PolicySplittingRequest;
import com.mli.mpro.location.models.soaCloudModels.policySplittingModels.PolicySplittingResponsePayload;
import com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels.SellerInfoPayload;
import com.mli.mpro.location.models.soaCloudModels.spCodeValidationModels.SellerResponsePayload;
import com.mli.mpro.otpservice.OtpServiceRequest;
import com.mli.mpro.otpservice.OtpServiceResponse;
import com.mli.mpro.onboarding.partner.model.CpdRequest;
import com.mli.mpro.onboarding.partner.model.CpdResponse;
import org.springframework.http.ResponseEntity;

public interface SoaCloudService {
    AgentResponse fetchAgent360EncryptedData(SoaApiRequest<RequestPayload> request) throws UserHandledException;
    <T> ResponseEntity<Result> soaCombinedLoginApiFetch(T request) throws UserHandledException;
    SoaCloudResponse<SoaClient360ResponsePayload> fetchSoaClient360ApiResponse(SoaApiRequest<RequestPayload> request) throws  UserHandledException;
    SoaCloudResponse<SellerResponsePayload> fetchSOASpCodeValidationApiResponse(SoaApiRequest<SellerInfoPayload> request) throws  UserHandledException;
    SoaCloudResponse handelErrorResponse(UserHandledException ex, SoaApiRequest request);
    OtpServiceResponse fetchOTPServiceResponse(OtpServiceRequest request) throws UserHandledException;
    <T> ResponseEntity<?> callingSoaApi(T request, String url) throws UserHandledException;
    SoaCloudResponse<Response> fetchMaster360Response(SoaApiRequest<Request> request) throws UserHandledException;
    SoaCloudResponse<CpdResponse> fetchClientPolicyDetailResponse(SoaApiRequest<CpdRequest> request) throws UserHandledException;
    SoaCloudResponse<PolicySplittingResponsePayload> callPolicySplittingDataLakeAPI(SoaApiRequest<PolicySplittingRequest> request)throws  UserHandledException;
    SoaCloudResponse<AgentCommissionSummaryResponse> callAgentCommissionSummaryDataLakeAPI(SoaApiRequest<AgentCommissionSummaryRequest> request)throws  UserHandledException;
    SoaCloudResponse<PolicyHistoryResponse> callPolicyHistoryApi(SoaApiRequest<PolicyHistoryRequest> request) throws UserHandledException;
    SoaCloudResponse<SoaResponsePayload> fetchSOAAMLTrainingDLApi(SoaAmlRequest request) throws  UserHandledException;

    SoaCloudResponse<PolicyHistoryResponse> getTpaHistory(SoaApiRequest<PolicyHistoryRequest> request) throws UserHandledException;
}
