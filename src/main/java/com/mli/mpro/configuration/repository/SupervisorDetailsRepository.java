package com.mli.mpro.configuration.repository;


import com.mli.mpro.configuration.models.SupervisorDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorDetailsRepository extends MongoRepository<SupervisorDetails, String> {
    SupervisorDetails findBySpCodeIgnoreCase(String spCode);

}
