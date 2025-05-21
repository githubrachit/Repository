package com.mli.mpro.location.repository;

import com.mli.mpro.location.models.AddressMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMasterRepository extends MongoRepository<AddressMaster, String> {

    List<AddressMaster> findByLocationCodeIgnoreCaseAndTypeIgnoreCase(String locationCode, String type);

    List<AddressMaster> findByLocationNameIgnoreCaseAndTypeIgnoreCase(String locationName, String type);

}