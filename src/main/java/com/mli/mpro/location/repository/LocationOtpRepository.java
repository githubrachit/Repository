package com.mli.mpro.location.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mli.mpro.location.models.LocationDetailsOtp;

public interface LocationOtpRepository extends MongoRepository<LocationDetailsOtp, String> {
	
	// Create New LocationOtpRepository for OTP State and City Validations.
	
	List<LocationDetailsOtp> findAll();

    @Query(fields = "{ 'name' : 1, 'phCode' : 1, 'id' : 0}")
    List<LocationDetailsOtp> findByTypeAndContainedInIn(String type, String containedIn);

}
