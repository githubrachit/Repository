package com.mli.mpro.location.repository;

import com.mli.mpro.location.models.SarthiMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SarthiMasterRepository extends MongoRepository<SarthiMaster,String> {

    SarthiMaster findByAgentId(String agentId);
}
