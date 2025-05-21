package com.mli.mpro.location.repository;

import com.mli.mpro.location.models.TransformationMasterDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationMasterDetailsRepository extends MongoRepository<TransformationMasterDetails, String> {
}
