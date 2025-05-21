package com.mli.mpro.configuration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.configuration.models.FeatureFlag;


@Repository
public interface FeatureFlagrepository extends MongoRepository<FeatureFlag, String> {
	
	FeatureFlag findByFeatureName(String featureName);

}
