package com.mli.mpro.configuration.service;

import java.util.HashMap;
import java.util.List;

import com.mli.mpro.configuration.models.AgentInfo;
import com.mli.mpro.configuration.models.UIConfiguration;

public interface UIConfigurationService {

	List<UIConfiguration> getConfigurationByKey(String key);

	List<UIConfiguration> getAllConfiguration();

	List<UIConfiguration> getConfigurationByType(String type);

	List<UIConfiguration> getConfigurationByTypeAndKey(String type, String key);

	HashMap<String, String> getShorterJourneyConfiguration(AgentInfo agentInfo);

}
