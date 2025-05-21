package com.mli.mpro.configuration;

import com.mli.mpro.configuration.models.UIConfiguration;
import com.mli.mpro.configuration.repository.UIConfigurationRepository;
import com.mli.mpro.productRestriction.util.AppConstants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UIConfigurationData {

    private static final Logger logger = LoggerFactory.getLogger(UIConfigurationData.class);

    public static List<UIConfiguration> uiConfigurationList = new ArrayList<>();

    public static final UIConfiguration shorterJourneryConfiguration = new UIConfiguration();

    private UIConfigurationRepository uiConfigurationRepository;

    @Autowired
    public UIConfigurationData(UIConfigurationRepository uiConfigurationRepository) {
        this.uiConfigurationRepository = uiConfigurationRepository;
        this.uiConfigurationRepository = uiConfigurationRepository;
        logger.info("loading ui configuration data at startup");
        uiConfigurationList = uiConfigurationRepository.findByTypeAndKey("uiswitch", "NIFTY_INDEX");
    }

    public static UIConfiguration getShorterJourneryConfiguration() {
        return shorterJourneryConfiguration;
    }

    @EventListener(ApplicationReadyEvent.class)
        public void getUIConfiguration() {

        if(uiConfigurationRepository==null) {
            logger.info("uiConfigurationRepository is not intialized");
            return;
        }  
        UIConfiguration config = uiConfigurationRepository.findByTypeIgnoreCase(AppConstants.SHORTER_JOURNEY).get(0);

        synchronized (shorterJourneryConfiguration) {
        shorterJourneryConfiguration.setShorterJourneyAllChannelApplicable(config.getShorterJourneyAllChannelApplicable());
        shorterJourneryConfiguration.setShorterJourneyAllStateApplicable(config.getShorterJourneyAllStateApplicable());
        shorterJourneryConfiguration.setShorterJourneyApplicableChannels(config.getShorterJourneyApplicableChannels());
        shorterJourneryConfiguration.setShorterJourneyApplicableStates(config.getShorterJourneyApplicableStates());
        shorterJourneryConfiguration.setNonApplicableGoNames(config.getNonApplicableGoNames());}
    }
}
