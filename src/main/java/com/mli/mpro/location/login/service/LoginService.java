package com.mli.mpro.location.login.service;

import org.springframework.http.ResponseEntity;

import com.mli.mpro.location.login.model.LoginRequest;

import java.util.Set;

public interface LoginService {

	ResponseEntity<String> executeLoginService(LoginRequest loginRequest);

	ResponseEntity<String> executeLogoutService(String apiToken);

	Set<String> getKeysFromRedis(String keyPrefix);

}
