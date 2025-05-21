package com.mli.mpro.location.services;

import java.util.List;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.BranchDetailsResponse;
import com.mli.mpro.common.models.InputRequest;
import com.mli.mpro.common.models.genericModels.GenericApiRequest;
import com.mli.mpro.common.models.genericModels.GenericApiResponse;
import com.mli.mpro.common.models.ResumeJourneyRequest;
import com.mli.mpro.common.models.ResumeJourneyResponse;
import com.mli.mpro.location.models.ExistingPolicyStatus;
import com.mli.mpro.location.models.FundsData;
import com.mli.mpro.location.models.PincodeMaster;
import com.mli.mpro.location.models.SarthiResponsePayload;
import com.mli.mpro.location.models.zeroqc.ekyc.UIRequestPayload;
import com.mli.mpro.location.models.zeroqc.ekyc.UIResponsePayload;
import com.mli.mpro.otpservice.OtpServiceRequest;
import com.mli.mpro.otpservice.OtpServiceResponse;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.proposal.models.PensionPlans;

import com.mli.mpro.samlTraceId.TraceIdRequest;
import com.mli.mpro.samlTraceId.TraceIdResponse;
import org.springframework.data.domain.Pageable;
import com.mli.mpro.yblAccount.models.OutputResponse;
import org.springframework.retry.annotation.Retryable;

public interface LocationService {

    List<String> getAllCountriesByContinent(String continent);

    List<String> getAllStatesByCountry(String country);

    List<String> getAllCitiesByStates(String state);

    List<Object> getPhoneCodeForCountry(String continent);

    List<String> getCompanyNames(String type);

    List<String> getAllOtpStatesByCountry(String country);

    List<String> getAllOtpCitiesByStates(String state);

    //F21-262 Method to get branch code
    List<BranchCodeService> getBranchCode(Pageable pageable);

    void transformLocation(AddressDetails addressDetails);

    List<PincodeMaster> getLocationByPincode(String pincode);

    OutputResponse getGstYblAccount(String type);
    
    List<PensionPlans> getPensionPlans();

    void generateDirectDebitBulk();
    
    FundsData getUlipFunds(String productId);

    List<String> getDisableProducts(String channel, String goCode, boolean isPosSeller, boolean isCATAxis, boolean isPhysicalJourney);

    ExistingPolicyStatus getExistingPolicy(String productId);
    List<String> getAllUniqueCities();

    @Retryable(value = {Exception.class}, maxAttempts = 3)
    BranchDetailsResponse getAxisBranchDetails(String transactionId, String channelCode) throws Exception;

    OtpServiceResponse fetchJ2OTPServiceResponse(OtpServiceRequest request) throws UserHandledException;

    SarthiResponsePayload fetchSarthiData(InputRequest inputRequest);

    GenericApiResponse<ResumeJourneyResponse> validateResumeJourneyData(GenericApiRequest<ResumeJourneyRequest> request);
    GenericApiResponse<UIResponsePayload> getEkycBrmsDetails(GenericApiRequest<UIRequestPayload> request) throws UserHandledException;

    TraceIdResponse getTransactionIdByTraceId(TraceIdRequest traceIdRequest);
}
