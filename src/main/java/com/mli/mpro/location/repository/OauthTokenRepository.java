package com.mli.mpro.location.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface OauthTokenRepository {

	String setToken(String token,int expire, String redisKey);

	String getToken(String oauthKey);
	
}
