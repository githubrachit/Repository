package com.mli.mpro.location.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.location.models.AllFund;

@Repository
public interface AllFundRepository extends MongoRepository<AllFund, String> {
	
	public 	List<AllFund>  findAll();
	
}
