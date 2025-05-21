package com.mli.mpro.configuration.repository;

import java.util.List;
import java.util.Map;

import com.mli.mpro.configuration.models.HashingConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mli.mpro.configuration.models.Configuration;

/*To get the configuration data from DB*/
public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    List<Configuration> findByKeyIgnoreCase(String key);

    List<Configuration> findByTypeIgnoreCase(String type);

    Configuration findByType(String type);

    Configuration findByKey(String key);

}
