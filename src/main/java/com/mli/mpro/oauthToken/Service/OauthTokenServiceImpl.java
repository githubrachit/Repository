package com.mli.mpro.oauthToken.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mli.mpro.location.repository.OauthTokenRepository;
import com.mli.mpro.utils.Utility;

@Service
public class OauthTokenServiceImpl implements OauthTokenService {

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

	private OauthTokenRepository oauthTokenRepo;

	@Autowired
	public OauthTokenServiceImpl(OauthTokenRepository oauthTokenRepo) {
		super();
		this.oauthTokenRepo = oauthTokenRepo;
	}

	@Override
	public String getAccessToken() {
		return Utility.getAouthAccessToken(oauthTokenUrl,authClientId, authClientSecret, authTokenUsername, authTokenPassword, oauthTokenRepo, oauthkey);
	}

}
