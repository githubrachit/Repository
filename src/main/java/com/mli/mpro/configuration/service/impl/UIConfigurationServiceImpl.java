package com.mli.mpro.configuration.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mli.mpro.configuration.UIConfigurationData;
import com.mli.mpro.configuration.models.AgentInfo;
import com.mli.mpro.configuration.models.UIConfiguration;
import com.mli.mpro.configuration.models.ShorterJourneyChannelDetails;
import com.mli.mpro.configuration.repository.UIConfigurationRepository;
import com.mli.mpro.configuration.service.UIConfigurationService;
import com.mli.mpro.configuration.util.FeatureFlagUtil;
import com.mli.mpro.location.soa.service.TrainingService;
import com.mli.mpro.productRestriction.util.AppConstants;

/*This class has all the implementation to get the configuration from DB*/
@Service
public class UIConfigurationServiceImpl implements UIConfigurationService {

	Logger logger = LoggerFactory.getLogger(UIConfigurationServiceImpl.class);

	private UIConfigurationRepository repository;
	private TrainingService trainingService;

	@Autowired
	public UIConfigurationServiceImpl(UIConfigurationRepository repository, TrainingService trainingService) {
		this.repository = repository;
		this.trainingService = trainingService;
	}

	/* To get the configuration using key */
	@Override
	public List<UIConfiguration> getConfigurationByKey(String key) {
		return repository.findByKeyIgnoreCase(key);
	}

	/* To get all the configurations */
	@Override
	public List<UIConfiguration> getAllConfiguration() {
		return repository.findAll();
	}

	/* To get the configuration using type */
	@Override
	public List<UIConfiguration> getConfigurationByType(String type) {
		return repository.findByTypeIgnoreCase(type);
	}
	/* To get the configuration using type */
	@Override
	public List<UIConfiguration> getConfigurationByTypeAndKey(String type,String key) {
		return repository.findByTypeAndKey(type,key);
	}

	/* To get the configuration for shorter Journey */
	@Override
	public HashMap<String, String> getShorterJourneyConfiguration(AgentInfo agentInfo) {

		HashMap<String, String> output = new HashMap();
		try{
			//calling update channel method=
			String trimGoCode = trainingService.updateChannel(agentInfo.getChannelName(), 
				agentInfo.getGoCode(), agentInfo.getRole());
				
			UIConfiguration shorterJourneyConfiguration = UIConfigurationData.shorterJourneryConfiguration;

			if(FeatureFlagUtil.isFeatureFlagEnabled(AppConstants.SHORTER_JOURNEY_FEATURE_FLAG) 
				&& (shorterJourneyConfiguration.getShorterJourneyAllStateApplicable().equalsIgnoreCase(AppConstants.YES) || shorterJourneyConfiguration.getShorterJourneyApplicableStates().stream().anyMatch(state -> state.equals(agentInfo.getState())))
				&& (shorterJourneyConfiguration.getShorterJourneyAllChannelApplicable().equalsIgnoreCase(AppConstants.YES) || shorterJourneyConfiguration.getShorterJourneyApplicableChannels().stream().anyMatch(ch -> ch.getChannelCode().equals(agentInfo.getChannelName())))
				&& (shorterJourneyConfiguration.getNonApplicableGoNames().stream().noneMatch(gn -> agentInfo.getGoName().toLowerCase().contains(gn.toLowerCase())))){

				Optional<ShorterJourneyChannelDetails> channelDetails = shorterJourneyConfiguration.getShorterJourneyApplicableChannels().stream().filter(ch -> ch.getChannelCode().equals(agentInfo.getChannelName())).findFirst();
					if(channelDetails.isPresent() && (channelDetails.get().getEnabledAllGoCode().equals(AppConstants.YES) || channelDetails.get().getGoCodes().stream().anyMatch(gc -> gc.equals(trimGoCode)))) {
						output.put("shorterJourneyFlag", AppConstants.YES);
					}else{
						output.put("shorterJourneyFlag", AppConstants.NO);	}
				}else{
					output.put("shorterJourneyFlag", AppConstants.NO);}
					return output;
		}catch(NullPointerException | ArrayIndexOutOfBoundsException npe){
			logger.error("Insufficient Data to validate shorter-journey flow, {}", npe);
			output.put("shorterJourneyFlag", AppConstants.NO);		
			return output;
		}catch(Exception e){
			logger.error("Exception occurred while validating shorterJourney flow, {}", e);
			output.put("shorterJourneyFlag", AppConstants.NO);		
			return output;
		}
	}
}
