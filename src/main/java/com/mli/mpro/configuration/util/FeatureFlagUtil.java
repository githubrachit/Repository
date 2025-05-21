package com.mli.mpro.configuration.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.configuration.models.FeatureFlag;
import com.mli.mpro.configuration.repository.FeatureFlagrepository;
import com.mli.mpro.utils.Utility;

@Component
public final class FeatureFlagUtil {

	@Autowired
	private FeatureFlagrepository featureFlagrepository;

	private static final Logger log = LoggerFactory.getLogger(FeatureFlagUtil.class);

	private List<String> errorMessages = new ArrayList<>();
	
	static Map<String, Boolean> featureFlagMap = new HashMap<>();

	private FeatureFlagUtil() {
	}
	
	/**
	 * Gets the data from db while restart.
	 * @throws UserHandledException 
	 */
	@PostConstruct
	public void init() throws UserHandledException {
		log.info("FeatureFlagUtil init method initialized");
		List<FeatureFlag> featureFlags = new ArrayList<>();
		try {
			featureFlags = featureFlagrepository.findAll();
				log.info("Size of feature flag list : {}", featureFlags.size());
			for (FeatureFlag featureFlag : featureFlags) {
                validate(featureFlag);
				}
			log.info("Size of feature flag map : {}", featureFlagMap.size());
		} catch (Exception e) {
			errorMessages.add(Utility.getExceptionAsString(e));
			log.error("Error in calling location FeatureFlag  API {} ", Utility.getExceptionAsString(e));
			throw new UserHandledException(errorMessages, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
	}

	private void validate(FeatureFlag featureFlag) {
		if (!ObjectUtils.isEmpty(featureFlag) && !StringUtils.isEmpty(featureFlag.getFeatureName())
				&& !ObjectUtils.isEmpty(featureFlag.getEnabled()) && !StringUtils.isEmpty(featureFlag.getJiraId())) {
			featureFlagMap.put(featureFlag.getFeatureName(), featureFlag.getEnabled());
		}
	}
	
	/**
	 * Gets the data from db.
	 * @return map
	 */
	public static Map<String, Boolean> getFeatureFlagData() {
		return featureFlagMap;
	}

	/**
	 * Gets the feature name.
	 * @param featureName
	 * @return the enabled or not
	 */
	public static Boolean isFeatureFlagEnabled(String featureName) {
		if (featureFlagMap.containsKey(featureName) && featureFlagMap.get(featureName)!= null) {
			log.info("feature name is",featureName);
			return featureFlagMap.get(featureName);
		}
		return false;
	}
}
