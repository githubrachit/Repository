package com.mli.mpro.bajajCapital.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BajajRetailSellerDetailsRepository extends MongoRepository<BajajRetailSellerDetails,String> {

    @Query(value="{}",fields="{'branchName':1}")
    List<BajajRetailSellerDetails> findAllLocations();

}
