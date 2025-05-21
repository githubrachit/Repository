package com.mli.mpro.location.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mli.mpro.location.models.LocationDetails;

public interface LocationRepository extends MongoRepository<LocationDetails, String> {

    List<LocationDetails> findAll();

    @Query(fields = "{ 'name' : 1, 'phCode' : 1, 'id' : 0}")
    List<LocationDetails> findByTypeAndContainedInIn(String type, String containedIn);

    List<LocationDetails> findByNameIgnoreCaseAndTypeIgnoreCase(String locationName, String type);
    
    @Query(fields = "{ 'name':1, 'id':0}")
    List<LocationDetails> findDistinctNameByType(String type);
}