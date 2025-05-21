package com.mli.mpro.location.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mli.mpro.location.models.CompanyDetailsMasterData;


public interface CompanyListRepository  extends MongoRepository<CompanyDetailsMasterData, String>{
    

    @Query(fields = "{ 'companyName' : 1}")
    List<CompanyDetailsMasterData> findCompanyListByType(String type);

}
