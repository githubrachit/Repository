package com.mli.mpro.location.repository;


import com.mli.mpro.location.models.PropertyConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PropertyConfigRepository extends MongoRepository<PropertyConfig, String> {
    Optional<PropertyConfig> findByServiceNameAndEnvironment(String serviceName, String environment);
}

