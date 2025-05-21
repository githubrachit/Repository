package com.mli.mpro.location.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.yblAccount.models.yblAccountDetails;

@Repository
public interface GstAccountRepository extends MongoRepository<yblAccountDetails, String> {
	
	yblAccountDetails findByType(String type);

}
