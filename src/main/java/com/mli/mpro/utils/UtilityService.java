package com.mli.mpro.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

import static com.mli.mpro.productRestriction.util.AppConstants.DCB_GOCODE;

@Service
public class UtilityService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${spring.profiles.active}")
    private String environment;
    @Value("${bypass.header.encrypt.value}")
    private String apiClientSecret;

    @Value("#{${urlDetails.gocode}}")
    Map<String, List<String>> ujjivan_replica_comb;
    public static final String IGNORE_GOCODE = "^[Bb]{1}[Ww]{1}(00[1-5])";
    private static Logger logger = LoggerFactory.getLogger(Utility.class);

    public boolean replicaOfUjjivanChannel(String channel, String goCode) {

        try {
            logger.info("inside replicaujiivan channel gocode parameter value from aws {}", ujjivan_replica_comb);
            List<String> goCodeList = ujjivan_replica_comb.get(channel);
            logger.info("inside replicaujiivan {}", goCodeList);
            if (!StringUtils.isEmpty(channel) && goCodeList != null) {
                if (goCodeList.stream().anyMatch(e -> goCode.toUpperCase().startsWith(e.toUpperCase()))) {
                    if (AppConstants.CHANNEL_DCB.equalsIgnoreCase(channel)
                            && Pattern.compile(DCB_GOCODE).matcher(goCode).matches()) {
                        return true;
                    }
                    if (AppConstants.CHANNEL_DCB.equalsIgnoreCase(channel)
                            && (Pattern.compile(IGNORE_GOCODE).matcher(goCode).matches())) {
                        return false;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.getMessage();
            logger.error("Error occurred in replicaOfUjjivanChannel {}", Utility.getExceptionAsString(e));
        }
        return false;

    }

    public void setFeatureFlagNIFTYFundForSTART(Map<String, Object> dataVariables, String transactionId) {
        boolean isFeatureFlagNIFTYFund = false;
        try {
            Map<String,String> request = new HashMap<>();
            request.put("transactionId", transactionId);
            String url = AppConstants.PF_IRP_CONFIG.get(environment);
            HttpHeaders headers =new HttpHeaders();
            headers.add(AppConstants.API_CLIENT_SECRET,apiClientSecret);
            HttpEntity entityRequest=new HttpEntity(request,headers);
            ResponseEntity<Object> response = restTemplate.postForEntity(url, entityRequest, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,String> res = objectMapper.convertValue(response.getBody(), Map.class);
            if (res != null && res.containsKey("isFundRequiredToShow")) {
                isFeatureFlagNIFTYFund = Boolean.parseBoolean(res.get("isFundRequiredToShow"));
            }
        }catch (Exception e){
            logger.error("Error while fetching PF_IRP_CONFIG from DPC {}",e.getMessage());
        }
        dataVariables.put("isFeatureFlagNIFTYFund", isFeatureFlagNIFTYFund);
    }
}