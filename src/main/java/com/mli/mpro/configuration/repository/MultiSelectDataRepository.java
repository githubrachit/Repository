package com.mli.mpro.configuration.repository;

import com.mli.mpro.configuration.models.MultiSelectData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultiSelectDataRepository extends MongoRepository<MultiSelectData, String> {
    MultiSelectData findByKeyIgnoreCase(String key);
}
