package com.mli.mpro.configuration.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mli.mpro.configuration.models.UIConfiguration;

/*To get the UIConfigurationRepository data from DB*/
public interface UIConfigurationRepository extends MongoRepository<UIConfiguration, String> {
	List<UIConfiguration> findByKeyIgnoreCase(String key);

	List<UIConfiguration> findByTypeIgnoreCase(String type);

	List<UIConfiguration> findByTypeAndKey(String type, String key);
}
