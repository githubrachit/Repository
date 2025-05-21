package com.mli.mpro.productRestriction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.EncryptionRequest;
import com.mli.mpro.common.models.EncryptionResponse;
import com.mli.mpro.common.models.Result;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.emailsms.service.EmailSmsService;
import com.mli.mpro.productRestriction.models.*;
import com.mli.mpro.productRestriction.service.PinCodeValidationService;
import com.mli.mpro.productRestriction.service.ProductRestrictionService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.psm.service.PsmService;
import com.mli.mpro.psm.utility.Util;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.MultiValueMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.mli.mpro.utils.MDCHelper.setLogVariable;

@RestController
@RequestMapping(path = "/locationservices/restriction")
public class ProductRestrictionController {

    private static final Logger log = LoggerFactory.getLogger(ProductRestrictionController.class);
    // Adding literals for Logger Info
    private static final String REQ_PROCESSED = "Request is being processed at controller";
    private static final String REQ_OBJ = "Request Object : {}";
    private static final String ERR_OCCURED = "Error occurred for request object {}";
    private static final String BAD_REQ = "badRequest";
    private ProductRestrictionService productRestrictionService;
    private List<String> errorMessages;
    private List<String> errorFields;
    private List<String> restrictionMessages;
    private EmailSmsService emailService;

    @Autowired
    private ErrorMessageConfig errorMessageConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PsmService psmService;
    @Autowired
    private PinCodeValidationService pinCodeValidationService;


    @Autowired
    public ProductRestrictionController(ProductRestrictionService productRestrictionService,EmailSmsService emailService) {
        this.productRestrictionService = productRestrictionService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    public ResponseEntity<OutputResponse> updateProductRestrictionData(@RequestBody InputRequest inputRequest) throws UserHandledException {

        setLogVariable(inputRequest,false);
        log.info("/updateData api call");

        errorMessages = new ArrayList<>();
        log.info(REQ_PROCESSED);
        log.debug(REQ_OBJ, inputRequest);

        boolean shouldUpdateProductRestrictionData = inputRequest.getRequest().getRequestData().getRequestPayload().isShouldUpdatedProductRestrictionData();
        boolean shouldUpdateRestrictionData = inputRequest.getRequest().getRequestData().getRequestPayload().isShouldUpdateRestrictionData();

        if (shouldUpdateProductRestrictionData) {
            errorMessages = shouldUpdateRestrictionData((InputRequest request) -> {
                String type = request.getRequest().getRequestData().getRequestPayload().getType();
                List<String> data = request.getRequest().getRequestData().getRequestPayload().getData();
                String status = request.getRequest().getRequestData().getRequestPayload().getStatus();
                return productRestrictionService.update(type, data, status);
            }, inputRequest);
        }
        if (shouldUpdateRestrictionData) {
            errorMessages = shouldUpdateRestrictionData((InputRequest request) -> {
                RestrictionData restrictionData = request.getRequest().getRequestData().getRequestPayload().getRestrictionData();
                return productRestrictionService.updateRestrictionData(restrictionData);
            }, inputRequest);
        }
        return prepareOKResponseEntity(inputRequest);
    }

    private ResponseEntity<OutputResponse> prepareOKResponseEntity(@RequestBody InputRequest inputRequest) {
        OutputResponse outputResponse = new OutputResponse();
        Response response = new Response();
        ResponseData responseData = new ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();

        responsePayload.setMessages(errorMessages);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        response.setMetadata(inputRequest.getRequest().getMetadata());
        outputResponse.setResponse(response);
        log.info("Response is received.");
        log.debug("Response object : {}",outputResponse);
        return new ResponseEntity<>(outputResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/validateData", method = RequestMethod.POST)
    public ResponseEntity<OutputResponse> validateProposalData(@RequestBody InputRequest inputRequest) throws UserHandledException {

        long transactionId = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getTransactionId();
        setLogVariable(inputRequest,true);
        log.info("/validateData api call for transaction id {}",transactionId);

        OutputResponse outputResponse = new OutputResponse();
        Response response = new Response();
        ResponseData responseData = new ResponseData();
        ResponsePayload responsePayload = new ResponsePayload();
        ErrorResponse errorResponse = new ErrorResponse();
        ValidateProposalDataResponse validateProposalDataResponse;
        errorMessages = new ArrayList<>();
        errorFields = new ArrayList<>();
        String productId = null,shouldEnableDoc = AppConstants.BLANK;
                String validatePasaQuestions = null;

        log.info(REQ_PROCESSED);
        log.info(REQ_OBJ, inputRequest);
        ProductRestrictionPayload productRestrictionPayload = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload();
        try{
            productId = productRestrictionPayload.getProductId();
            validateProposalDataResponse = productRestrictionService.validateProposalData(productRestrictionPayload);
            errorMessages = validateProposalDataResponse.getFailedMessages();
            errorFields = validateProposalDataResponse.getErrorFields();
            restrictionMessages = validateProposalDataResponse.getRestrictionMessages().stream().distinct().collect(Collectors.toList());
            /* FUL2-9472 WLS Product Restriction */
            shouldEnableDoc = validateProposalDataResponse.getShouldEnableDoc();
            //FUL2-124056 PASA Validation for PASA questions
            validatePasaQuestions =  validatePasaQuestions(inputRequest);
        } catch (Exception e) {
            log.error("Error occurred while validating proposal data for request object {} : ",inputRequest, e);
            errorMessages = new ArrayList<>();
            errorMessages.add(errorMessageConfig.getErrorMessages().get(BAD_REQ));
            throw new UserHandledException(errorMessages, HttpStatus.BAD_REQUEST);
        }
        errorResponse.setErrorMessages(errorMessages);
        responsePayload.setMessages(restrictionMessages);
        responsePayload.setErrorFields(errorFields);
        /* FUL2-9472 WLS Product Restriction */
        responsePayload.setShouldEnableDoc(shouldEnableDoc);
        /*
        FUL2-5523 Enabling CIP Plan for NRI Customers : Enabling Country Restriction for CIP.
        */
        /*
        F21-578 WLP City and GIP Sourcing Restriction Logic: checking the eligibility for WLS and GIP product
         */
        if(restrictionMessages.contains(AppConstants.BLACKLISTED_COUNTRIES)){
            responsePayload.setEligible(false);
        }else if (AppConstants.TRUE.equalsIgnoreCase(productRestrictionPayload.getIsCIRiderSelected()) && !AppConstants.productSet.contains(productId)){
            responsePayload.setEligible((errorMessages.isEmpty() && restrictionMessages.isEmpty()));
        } else {
            responsePayload.setEligible((errorMessages.isEmpty() && restrictionMessages.isEmpty()) || !AppConstants.productSet.contains(productId));
        }
        setPasaValidationTag(inputRequest, responsePayload, validatePasaQuestions);
        responseData.setResponsePayload(responsePayload);
        response.setResponseData(responseData);
        response.setMetadata(inputRequest.getRequest().getMetadata());
        response.setErrorResponse(errorResponse);
        outputResponse.setResponse(response);
        log.debug("Response Object : {}",outputResponse);
        log.info("Response is received.");
        return new ResponseEntity<>(outputResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/product-validator")
    public RequestResponse productValidator(@RequestBody RequestResponse inputRequest,@RequestHeader MultiValueMap<String, String> headerMap) throws UserHandledException, JsonProcessingException {
        RequestResponse responsePayload;
        ErrorResponseParams errorResponseParams=new ErrorResponseParams();
        long requestedTime = System.currentTimeMillis();
        log.info(REQ_PROCESSED);
        log.info(REQ_OBJ, inputRequest);
        try {
            responsePayload = productRestrictionService.validateProduct(inputRequest, headerMap,errorResponseParams);
        }
        catch (Exception e){
            log.error(ERR_OCCURED
                    , Utility.getExceptionAsString(e));
            return Utility.errorResponse(HttpStatus.BAD_REQUEST, Collections.singletonList(errorMessageConfig
                    .getErrorMessages().get(AppConstants.BAD_REQUEST_TEXT)), errorResponseParams.getEncryptionSource(), errorResponseParams.getIVandKey());
        }
        finally {
            log.info("Time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responsePayload;
    }

    @RequestMapping(value = "/updateRestrictionData", method = RequestMethod.POST)
    public ResponseEntity<OutputResponse> updateRestrictionData(@RequestBody InputRequest inputRequest) throws UserHandledException {
        setLogVariable(inputRequest,false);
        log.info("/updateData api call");
        errorMessages = new ArrayList<>();

        log.info(REQ_PROCESSED);
        log.debug(REQ_OBJ, inputRequest);

        errorMessages = shouldUpdateRestrictionData((InputRequest request) -> {
            String type = request.getRequest().getRequestData().getRequestPayload().getType();
            List<String> data = request.getRequest().getRequestData().getRequestPayload().getData();
            String status = request.getRequest().getRequestData().getRequestPayload().getStatus();
            return productRestrictionService.update(type, data, status);
        }, inputRequest);

        return prepareOKResponseEntity(inputRequest);
    }
    private String validatePasaQuestions(InputRequest inputRequest) {
        String pasaQuestionsStatus = "";
        try {
            ArrayList<String> pasaQuestions = new ArrayList<String>();
            pasaQuestions.add(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPasaQ1());
            pasaQuestions.add(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPasaQ2());
            pasaQuestions.add(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPasaQ3());
            pasaQuestions.add(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPasaQ4());
            Optional<String> pasaValidation = pasaQuestions.stream().filter(p -> p.equals("")).findAny();
            if (pasaValidation.isPresent()) {
                pasaQuestionsStatus = "unanswered";
                return pasaQuestionsStatus;
            } else {
                return "answered";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pasaQuestionsStatus;
    }

    private void setPasaValidationTag(InputRequest inputRequest, ResponsePayload responsePayload, String pasaValidation) {
        try {
            if (Boolean.TRUE.equals(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.ENABLEPASATAGS) &&
                    inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getIsPasaEligible().equalsIgnoreCase("YES") &&
                    null != inputRequest && null != inputRequest.getRequest() &&
                    null != inputRequest.getRequest().getRequestData().getRequestPayload() &&
                    null != inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload())) {
                responsePayload.setPasaQuestionsValidation(pasaValidation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> shouldUpdateRestrictionData(RestrictionDataProcessor processor,@RequestBody InputRequest inputRequest) throws UserHandledException {
        try {
            return processor.processRestrictionData(inputRequest);
        } catch (Exception e) {
            log.error("Error occurred while checking should update restriction data for request object : {}",inputRequest, e);
            errorMessages = new ArrayList<>();
            errorMessages.add(errorMessageConfig.getErrorMessages().get(BAD_REQ));
            throw new UserHandledException(errorMessages, HttpStatus.BAD_REQUEST);
        }
    }

    @FunctionalInterface
    public interface RestrictionDataProcessor {
        List<String> processRestrictionData(@RequestBody InputRequest inputRequest) throws UserHandledException, NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException;
    }

    //Ful2-32236
    @PostMapping("/sendEmailSmsForAgencyYblPos")
    public ResponseEntity<String> sendEmailSmsForAgencyYblPos(@RequestBody ProductRestrictionPayload productRestrictionPayload) throws UserHandledException {
        this.emailService.sendEmailAndSmsForPos(productRestrictionPayload);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/sspPincodeValidation")
    public OutputPayload getValidSumAssured(@RequestBody InputPayload inputPayload)
            throws UserHandledException {
        if (Objects.isNull(inputPayload)) {
            return new OutputPayload(99, "Error: Bad Request");
        }
        return pinCodeValidationService.getAllowedSumAssured(inputPayload);
    }


    @PostMapping(path = "/product-suitability")
    public RequestResponse getSuitableProduct(@RequestBody RequestResponse inputPayload,@RequestHeader MultiValueMap<String, String> headerMap) throws UserHandledException, JsonProcessingException {
        RequestResponse responsePayload;
        ErrorResponseParams errorResponseParams=new ErrorResponseParams();
        long requestedTime = System.currentTimeMillis();
        try {
            log.info("Inside try PSM Details {} " , headerMap);
            responsePayload = psmService.getPsmDetails(inputPayload,headerMap, errorResponseParams);
        } catch (Exception e) {
            log.error(ERR_OCCURED, Utility.getExceptionAsString(e));
            return Util.errorResponse(HttpStatus.BAD_REQUEST, Collections.singletonList(errorMessageConfig.getErrorMessages().get(AppConstants.BAD_REQUEST_TEXT)), errorResponseParams.getEncryptionSource(), errorResponseParams.getIVandKey());
        } finally {
            log.info("Time {} sec took to process the request", Utility.getProcessedTime(requestedTime));
        }
        return responsePayload;
    }

}
