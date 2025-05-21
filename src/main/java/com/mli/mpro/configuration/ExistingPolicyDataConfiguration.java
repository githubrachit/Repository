package com.mli.mpro.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.mli.mpro.location.models.ExistingPolicyStatus;
import com.mli.mpro.location.repository.ExistingPolicyStatusRepository;

@Configuration
public class ExistingPolicyDataConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(ExistingPolicyDataConfiguration.class);

	private ExistingPolicyStatusRepository existingPolicyStatusRepository;

	public static List<ExistingPolicyStatus> existingPolicyStatus = new ArrayList<>();

	@Autowired
	public ExistingPolicyDataConfiguration(ExistingPolicyStatusRepository existingPolicyStatusRepository) {
		this.existingPolicyStatusRepository = existingPolicyStatusRepository;
		logger.info("loading Existing policy status data at startup");
		existingPolicyStatus = existingPolicyStatusRepository.findAll();
	}
}
