package com.mli.mpro.tmb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AccountDetailsServiceImpl {
    @Value("${tmb.account.fetch.token.url}")
    private String tokenUrl;
    @Value("${tmb.account.fetch.api.url}")
    private String apiUrl;
    private String scope ="acctdtlscid";
    @Autowired
    OauthTokenTMBUtility oauthTokenTMBUtility;

    @Autowired
    TmbApiCallingUtility tmbApiCallingUtility;
}
