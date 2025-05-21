package com.mli.mpro.bankdetails.service.impl;

import java.util.Objects;

import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.bankdetails.model.SOABankHeader;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.soa.constants.SoaConstants;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import com.mli.mpro.bankdetails.model.SOABankResponse;
import com.mli.mpro.bankdetails.service.BankDetailsService;
import com.mli.mpro.location.ifsc.model.RequestData;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.oauthToken.Service.OauthTokenService;
import com.mli.mpro.utils.Utility;

@Component
public class BankDetailsManager {

    private static final Logger logger = LoggerFactory.getLogger(BankDetailsManager.class);

    @Autowired
    private BankDetailsService bankDetailsService;

    @Value("${urlDetails.oauthTokenUrl}")
    private String oauthTokenUrl;
    @Value("${urlDetails.authorization.username}")
    private String authClientId;
    @Value("${urlDetails.authorization.password}")
    private String authClientSecret;
    @Value("${urlDetails.authUserName}")
    private String authTokenUsername;
    @Value("${urlDetails.authPassword}")
    private String authTokenPassword;
    @Value("${redis.oauthkey}")
    private String oauthkey;

    @Autowired
    private OauthTokenRepository oauthTokenRepo;

    public SOABankResponse getBankDetailsByIFSCAndMICR(RequestData bankDetailsRequest) {
        SOABankResponse response = new SOABankResponse();
        String oAuthToken = AppConstants.BLANK;
        if (Objects.nonNull(bankDetailsRequest)) {
            try {
                if (!FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SOA_CLOUD_BANK_API)) {
                    OauthTokenService oauthTokenService = () -> {
                        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
                        multiValueMap.add(AppConstants.GRANT_TYPE, AppConstants.PASSWORD);
                        multiValueMap.add(AppConstants.SCOPE, AppConstants.SCOPE_VALUE);
                        multiValueMap.add(AppConstants.USERNAME, authTokenUsername);
                        multiValueMap.add(AppConstants.PASSWORD, authTokenPassword);
                        return Utility.getOAuthAccessToken(oauthTokenUrl, authClientId, authClientSecret, authTokenUsername,
                                authTokenPassword, oauthTokenRepo, oauthkey, multiValueMap);
                    };

                    oAuthToken = oauthTokenService.getAccessToken();
                    if (StringUtils.isBlank(oAuthToken)) {
                        logger.error(
                                "Received empty oAuthToken for transactionId {}, means something wrong in invoking oAuthService",
                                bankDetailsRequest.getTransactionId());
                    }
                }

                response = bankDetailsService.fetchBankDetails(bankDetailsRequest, oAuthToken);
            } catch (Exception ex) {
                logger.error("Getting exception {} while calling Oauth Service for transactionId {}",ex.getMessage(),bankDetailsRequest.getTransactionId());
                setExceptionMessage(response);
            }
        }
        return response;
    }

    private void setExceptionMessage(SOABankResponse response){
        MsgInfo msgInfo = new MsgInfo();
        SOABankHeader header = new SOABankHeader();
        msgInfo.setMsgCode("500");
        msgInfo.setMsgDescription(SoaConstants.FAILURE);
        msgInfo.setMsg(AppConstants.ERR_MSG);
        response.setMsgInfo(msgInfo);
        response.setHeader(header);
    }
}