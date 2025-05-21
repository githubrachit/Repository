package com.mli.mpro.location.repository;

import java.util.concurrent.TimeUnit;

import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import org.springframework.util.StringUtils;

@Repository
public class OauthTokenRepositoryImpl implements OauthTokenRepository {

	private static final Logger log = LoggerFactory.getLogger(OauthTokenRepositoryImpl.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public String setToken(String token, int expire, String redisKey) {
		try{
			if(StringUtils.hasText(redisKey)){
				log.info("Service intiate to save token in redis");
				log.info("Set Redis key {} and value {} expire {} ",redisKey, token, expire);
				redisTemplate.opsForValue().set(redisKey, token);
				redisTemplate.expire(redisKey, expire, TimeUnit.SECONDS);
				return "Success";
			}
		}catch (Exception e){
			log.error("Exception occurred while setting the token", Utility.getExceptionAsString(e));
		}
		return "Failure";
	}

	@Override
	public String getToken(String key) {
		log.info("Service intiate to get token from redis");
		Object oautToken = redisTemplate.opsForValue().get(key);
		String expire = String.valueOf(redisTemplate.getExpire(key));
		log.info("Get Redis key {} and value {} expire {} ",key, oautToken, expire);
		return (String) oautToken;
	}

}
