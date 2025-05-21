package com.mli.mpro.oauthToken.Service;

import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BrokerChOauthTokenServiceImpl implements OauthTokenService {

    @Value("${urlDetails.oauthTokenUrl}")
    private String oauthTokenUrl;
    @Value("${urlDetails.broker.client.id}")
    private String authClientId;
    @Value("${urlDetails.broker.client.secret}")
    private String authClientSecret;
    @Value("${urlDetails.broker.authUserName}")
    private String  authTokenUsername;
    @Value("${urlDetails.broker.authPassword}")
    private String authTokenPassword;
    @Value("${redis.mfa360Oauthkey}")
    private String mfa360Oauthkey;


    private OauthTokenRepository oauthTokenRepo;

    @Autowired
    public BrokerChOauthTokenServiceImpl(OauthTokenRepository oauthTokenRepo){
        super();
        this.oauthTokenRepo = oauthTokenRepo;
    }

    @Override
    public String getAccessToken() {
        return Utility.getAouthAccessToken(oauthTokenUrl, authClientId, authClientSecret, authTokenUsername, authTokenPassword,oauthTokenRepo, mfa360Oauthkey);
    }
}
