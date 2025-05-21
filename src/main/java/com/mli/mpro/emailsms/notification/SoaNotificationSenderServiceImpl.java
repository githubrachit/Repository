package com.mli.mpro.emailsms.notification;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.models.NotificationSender;
import com.mli.mpro.location.models.soaCloudModels.SoaAuthResponse;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.location.services.AuthTokenService;
import com.mli.mpro.location.services.impl.SoaCloudServiceImpl;
import com.mli.mpro.onboarding.model.datalakesms.*;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.sms.responsemodels.OutputResponse;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.APP_ID_VALUE;
import static com.mli.mpro.productRestriction.util.AppConstants.SOA_CLOUD_AUTH_TOKEN_REDIS_KEY;

@Service
public class SoaNotificationSenderServiceImpl extends NotificationSenderService {

    private Logger logger = LoggerFactory.getLogger(SoaNotificationSenderServiceImpl.class);

    private RestTemplate restTemplate;

    private com.mli.mpro.sms.requestmodels.OutputResponse inputRequest;

    private static final String ACC_ID = "MAXLI_MAPPOTP";

    private static final String APP_ID = "MAXLIT";

    public SOASmsEmailConstantBean getSoaSmsEmailConstantBean(){
        return ApplicationContextProvider.getApplicationContext().getBean(SOASmsEmailConstantBean.class);
    }

    public OauthTokenRepository getOauthTokenRepository(){
        return ApplicationContextProvider.getApplicationContext().getBean(OauthTokenRepository.class);
    }

    public AuthTokenService getAuthTokenService(){
        return ApplicationContextProvider.getApplicationContext().getBean(AuthTokenService.class);
    }

    @Override
    public String sendEmail(Object inputRequest) throws UserHandledException {
        logger.info("Sending notification email from SOA service");
        return getOutputResponse(inputRequest,getSoaSmsEmailConstantBean().getEmail(),AppConstants.TRIGGER_TYPE_EMAIL);
    }

    @Override
    public String aSyncSendEmail(Object inputRequest) throws UserHandledException {
        logger.info("Sending notification aSync email from SOA service");
        return getOutputResponse(inputRequest,getSoaSmsEmailConstantBean().getEmail(),AppConstants.TRIGGER_TYPE_EMAIL);
    }

    @Override
    public String sendSms() throws UserHandledException {
        return null;
    }

    @Override
    public String aSyncSendSms() throws UserHandledException {
        logger.info("Sending notification email from SOA service");
        return getOutputResponse(inputRequest, getSoaSmsEmailConstantBean().getSms(), AppConstants.TRIGGER_TYPE_SMS);
    }

    @Override
    public void initNotificationSender(NotificationSender notificationSender) {
        this.inputRequest = notificationSender.inputRequest;
    }

    private String getOutputResponse(Object inputRequest, String url, String triggerType) throws UserHandledException {
        ResponseEntity<Object> outputResponse=null;
        try {
            restTemplate = new RestTemplate();
            logger.info("RestTemplate object {}", restTemplate);
            String json = Utility.printJsonRequest(inputRequest);
            logger.info("Before send {} url {} inputRequest {} ",triggerType, url, json);
            if (FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.NEW_SMS_API) && AppConstants.TRIGGER_TYPE_SMS.equalsIgnoreCase(triggerType)) {
                logger.info("New SMS API is enabled");
                SOASmsEmailConstantBean soaSmsEmailConstantBean = getSoaSmsEmailConstantBean();
                logger.info("SOA Data lake authentication utility bean exists {}", soaSmsEmailConstantBean);
                RequestData request = new RequestData();
                Request req = new Request();
                Payload payload = new Payload();
                Header header = new Header();
                ResponseEntity<ResponseData> responseData = null;
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.valueToTree(inputRequest);
                com.mli.mpro.sms.requestmodels.OutputResponse requestData = objectMapper.treeToValue(jsonNode, com.mli.mpro.sms.requestmodels.OutputResponse.class);
                payload.setMsgTo(requestData.getMliSmsService().getRequestBody().getMsgTo());
                payload.setEncryptionKey(soaSmsEmailConstantBean.getEncryptionKey());
                payload.setMsgText(requestData.getMliSmsService().getRequestBody().getMsgText());
                payload.setAppAccPass(soaSmsEmailConstantBean.getAppPassKey());
                payload.setAppId(APP_ID);
                payload.setAppAccId(ACC_ID);
                header.setAppId(APP_ID_VALUE);
                header.setCorrelationId(UUID.randomUUID().toString());
                header.setMessageVersion("1.0");
                req.setPayload(payload);
                req.setHeader(header);
                request.setRequest(req);
                logger.info("sendSMS: data lake before rest call with entity {}", new ObjectMapper().writeValueAsString(request));
                responseData = restTemplate
                        .postForEntity(soaSmsEmailConstantBean.getNewDataLakeSMSAPI(), getHttpEntityForSoaApis(request, getSoaAuthToken(false)), ResponseData.class);
                logger.info("sendSMS: data lake after rest call {} service triggered with url {} and with outcome response successfully {}", triggerType, soaSmsEmailConstantBean.getNewDataLakeSMSAPI(), responseData);
                ObjectMapper objectMapper1 = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                String responseDataJson = objectMapper1.writeValueAsString(responseData.getBody());
                ResponseData responseDataSms = objectMapper1.readValue(responseDataJson, com.mli.mpro.onboarding.model.datalakesms.ResponseData.class);
                return (Objects.nonNull(responseDataSms) && (responseDataSms.getResponse().getMsgInfo().getMsgCode().equals("200"))) ? AppConstants.STATUS_SUCCESS : AppConstants.FAIL_STATUS;
            } else {
                outputResponse = restTemplate
                        .postForEntity(url, inputRequest, Object.class);
                logger.info("{} service triggered with outcome response successfully {}", triggerType, outputResponse);
                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                String json2 = objectMapper.writeValueAsString(outputResponse.getBody());
                try {
                    com.mli.mpro.sms.responsemodels.OutputResponse outputResponseSms = objectMapper.readValue(json2, com.mli.mpro.sms.responsemodels.OutputResponse.class);
                    return (Objects.nonNull(outputResponseSms) && outputResponseSms.getMliSmsServiceResponse().getResponseHeader().getGeneralResponse().getStatus().equalsIgnoreCase("OK")) ? AppConstants.STATUS_SUCCESS : AppConstants.FAIL_STATUS;
                } catch (Exception ex) {
                    try {
                        logger.info("Exception {}", ex.getMessage());
                        com.mli.mpro.posseller.email.models.OutputResponse outputResponseEmail = objectMapper.readValue(json2, com.mli.mpro.posseller.email.models.OutputResponse.class);
                        return (outputResponseEmail != null && outputResponseEmail.getResponse().getMsgInfo().getMsgCode().equals("200")) ? AppConstants.STATUS_SUCCESS : AppConstants.FAIL_STATUS;
                    } catch (Exception e) {
                        logger.info("Exception {}", e.getMessage());
                        com.mli.mpro.pratham.models.OutputResponse outputResponseEmailPratham = objectMapper.readValue(json2, com.mli.mpro.pratham.models.OutputResponse.class);
                        return Objects.nonNull(outputResponseEmailPratham) && outputResponseEmailPratham.getResponse().getMsgInfo().getMsgCode().equals("200") ? AppConstants.STATUS_SUCCESS : AppConstants.FAIL_STATUS;
                    }
                }
            }
        } catch (Exception ex) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add("Cannot connect to email service");
            logger.error("{} due to this exception",errorMsg, ex);
            throw new UserHandledException(null, errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private <T> HttpEntity<?> getHttpEntityForSoaApis(T request, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AppConstants.X_APIGW_API_ID, getSoaSmsEmailConstantBean().getxApigwApiId());//"x-apigw-api-id"
        headers.add(AppConstants.X_API_KEY, getSoaSmsEmailConstantBean().getxApiKey());//"x-api-key"
        headers.add(AppConstants.HEADER_APP_ID, AppConstants.APP_ID_VALUE);//"appId"
        headers.add(AppConstants.AUTH, AppConstants.BEARER + token);
        logger.info("With request payload for newSMS{}", request);
        return new HttpEntity<>(request, headers);
    }
    private String getSoaAuthToken(boolean isPrivateCall) throws UserHandledException {
        String redisKey = isPrivateCall ? "SoaCloudPrivateAuthRedisKey" : SOA_CLOUD_AUTH_TOKEN_REDIS_KEY;
        String token = getOauthTokenRepository().getToken(redisKey);
        if(!StringUtils.hasValue(token)){
            logger.info("Redis token is not found, request soa to generate new token");
            SoaAuthResponse authTokenResponse = getAuthTokenService().getAuthToken(isPrivateCall);
            if(Utility.isAnyObjectNull(authTokenResponse, authTokenResponse.getResponse(),
                    authTokenResponse.getResponse().getPayload(),
                    authTokenResponse.getResponse().getPayload().getAccessToken())){
                throw new UserHandledException(Collections.singletonList("Auth token generation exception"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            token = authTokenResponse.getResponse().getPayload().getAccessToken();
            int expiryTime = Integer.valueOf(authTokenResponse.getResponse().getPayload().getExpiresIn()) - 10;
            getOauthTokenRepository().setToken(token,expiryTime,redisKey);
            return token;
        } else {
            return token;
        }
    }

}
