package com.mli.mpro.location.repository;

import com.mli.mpro.location.models.PincodeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PincodeMasterRepository extends MongoRepository<PincodeMaster, String> {

    List<PincodeMaster> findAll();

    List<PincodeMaster> findByPincode(String pincode);

    List<PincodeMaster> findByPincodeAndCityAndState(String pincode, String city, String state);

}