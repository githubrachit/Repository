package com.mli.mpro.location.soa.service.impl;

import com.mli.mpro.location.common.soa.model.Header;

import com.mli.mpro.location.newApplication.model.ResponseMsgInfo;
import com.mli.mpro.location.productRecommendation.models.*;
import com.mli.mpro.location.productRecommendation.models.Payload;
import com.mli.mpro.location.productRecommendation.models.Request;
import com.mli.mpro.location.productRecommendation.models.RequestData;
import com.mli.mpro.location.soa.constants.NewApplicationConstants;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.location.soa.constants.TrainingConstants;
import com.mli.mpro.location.soa.exception.SoaCustomException;
import com.mli.mpro.location.soa.service.ProductRecommendationService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductRecommendationServiceImpl implements ProductRecommendationService {

    public static final String NO_RECOMMENDED_PRODUCTS_WERE_FOUND = "No recommended products were found.";
    public static final String RECOMMENDED_PRODUCTS_FETCHED_SUCCESSFULLY = "Recommended products fetched successfully.";
    public static final String OR_DELIMITER = " or ";
    @Value("${urlDetails.brms.cloud.flag}")
    private String brmsCloudFlag;
    @Value("${urlDetails.clientId}")
    private String cleintID;
    @Value("${urlDetails.secretKey}")
    private String secretKey;
    @Value("${urlDetails.brms.apigw.key}")
    private String brmsApigwKey;
    @Value("${urlDetails.brms.api_key}")
    private String brmsApiKey;
    @Value("${urlDetails.brms.cloud.server}")
    private String brmsCloudServer;
    @Value("${urlDetails.brms.cloud.product.suitability.matrix}")
    private String psmCloudUrl;
    @Value("${urlDetails.product.recommendation.service.url}")
    private String psmUrl;

    Logger log = LoggerFactory.getLogger(ProductRecommendationServiceImpl.class);

    @Override
    public ResponseEntity<Object> getRecommendedProductsList(InputRequest inputRequest) throws SoaCustomException {
        log.info("Request received for getRecommendedProductsList {}", inputRequest);

        validateRequest(inputRequest);

        SoaRequest soaRequest = setRequest(inputRequest.getRequest());

        log.info("soa input request for productRecommendation service {}",soaRequest);
        RestTemplate restTemplate = new RestTemplate(Utility.clientHttpRequestFactory(SoaConstants.SOA_TIMEOUT));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<SoaResponse> soaResponse;

        OutputResponse productRecommendationResponse = new OutputResponse();
        if (SoaConstants.TRUE.equalsIgnoreCase(brmsCloudFlag)) {
            String cloudUrl = brmsCloudServer.trim() + psmCloudUrl.trim();
            headers.add(SoaConstants.X_APIGW_API_ID, brmsApigwKey);
            headers.add(SoaConstants.X_API_KEY, brmsApiKey);
            HttpEntity<SoaRequest> httpEntity = new HttpEntity<>(soaRequest, headers);
            soaResponse = restTemplate.exchange(cloudUrl, HttpMethod.POST, httpEntity, SoaResponse.class);
        } else {
            headers.add(NewApplicationConstants.X_IBM_CLIENT_ID, cleintID);
            headers.add(NewApplicationConstants.X_IBM_CLIENT_SECRET, secretKey);
            HttpEntity<SoaRequest> httpEntity = new HttpEntity<>(soaRequest, headers);
            soaResponse = restTemplate.exchange(psmUrl, HttpMethod.POST, httpEntity, SoaResponse.class);
        }
        log.info("Response received form soa service for productRecommendation service {}",soaResponse);
        SoaResponse soaResult = soaResponse.getBody();
        if (soaResult != null && soaResponse.getStatusCode() == HttpStatus.OK) {
            processApiResponse(soaResult.getResponse(), productRecommendationResponse);
        } else {
            throw new SoaCustomException(TrainingConstants.FAILURE, AppConstants.INTERNAL_SERVER_ERROR, "500");
        }
        return new ResponseEntity<>(productRecommendationResponse, HttpStatus.OK);

    }

    private SoaRequest setRequest(RequestData requestData) {
        Header header = new Header(UUID.randomUUID().toString(), "FULFILLMENT");
        Payload payload = new Payload();
        payload.setChannel(requestData.getData().getChannel());
        LocalDate dateOfBirth = parseDateOfBirth(requestData.getData().getDateOfBirth());
        payload.setAge(String.valueOf(Period.between(dateOfBirth, LocalDate.now()).getYears()));
        payload.setAnnualIncome(requestData.getData().getAnnualIncome());
        payload.setLifeStage(requestData.getData().getLifeStage().toUpperCase());
        String[] needOfInsuranceList = requestData.getData().getNeedOfInsurance().split("/");
        String needOfInsurance = String.join(OR_DELIMITER, needOfInsuranceList).replaceAll("[^a-zA-Z ]", "").toUpperCase();
        payload.setNeedOfInsurance(needOfInsurance);

        Request request = new Request();
        SoaRequest soaRequest = new SoaRequest();
        request.setHeader(header);
        request.setPayload(payload);
        soaRequest.setRequest(request);
        return soaRequest;
    }
    private LocalDate parseDateOfBirth(String dateOfBirth) {
        try {
            return Utility.stringToDateFormatter(dateOfBirth, AppConstants.DATEFORMAT_WITHOUT_TIME_YYYY_MM_DD);
        } catch (DateTimeParseException e) {
            try {
                return Utility.stringToDateFormatter(dateOfBirth, AppConstants.YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid date format: " + dateOfBirth);
            }
        }
    }

    private void validateRequest(InputRequest inputRequest) throws SoaCustomException {
        log.info("Request received for getProductRecommendation  {}", inputRequest);
        if (Objects.isNull(inputRequest) || Objects.isNull(inputRequest.getRequest()) || Objects.isNull(inputRequest.getRequest().getData())) {
            throw new SoaCustomException(null, "Error in payload.", null);
        }
    }

    private OutputResponse processApiResponse(com.mli.mpro.location.productRecommendation.models.Response soaResponse, OutputResponse productRecommendationResponse)
            throws SoaCustomException {
        log.info("Response Received from SOA {}", soaResponse);
        ResponsePayload response = new ResponsePayload();
        Result result = new Result();
        if (soaResponse.getMsgInfo().getMsgCode().equals("200") && soaResponse.getPayload() != null) {
            PayloadData soaPayload = soaResponse.getPayload();
            Payload payload = new Payload();
            List<String> productList = new ArrayList<>();
            ResponseMsgInfo responseMsgInfo = new ResponseMsgInfo();
            responseMsgInfo.setMsg(soaResponse.getMsgInfo().getMsg());
            responseMsgInfo.setMsgCode(Integer.parseInt(soaResponse.getMsgInfo().getMsgCode()));
            response.setMsginfo(responseMsgInfo);
            response.getMsginfo().setMsgDescription(RECOMMENDED_PRODUCTS_FETCHED_SUCCESSFULLY);
            if (!StringUtils.hasText(soaPayload.getPlanName())) {
                response.getMsginfo().setMsgDescription(NO_RECOMMENDED_PRODUCTS_WERE_FOUND);
            } else {
                productList = List.of(soaPayload.getPlanName().split(","));
            }
            payload.setRecommendedProducts(productList);
            response.setPayload(payload);
            result.setResponse(response);
            productRecommendationResponse.setResult(result);
        } else {
            throw new SoaCustomException(soaResponse.getMsgInfo().getMsg(), AppConstants.INTERNAL_SERVER_ERROR, "500");
        }
        return productRecommendationResponse;
    }
}
