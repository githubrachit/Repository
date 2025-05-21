package com.mli.mpro.location.repository;

import com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels.VerbiageMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerbiageMasterRepository extends MongoRepository<VerbiageMaster,String> {


    VerbiageMaster findByRequirementId(String reqId);
}
