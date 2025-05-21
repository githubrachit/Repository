package com.mli.mpro.location.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.location.models.RecommendedFunds;

@Repository
public interface RecommendedFundRepository extends MongoRepository<RecommendedFunds, String> {

	public 	List<RecommendedFunds> findAll();
}
