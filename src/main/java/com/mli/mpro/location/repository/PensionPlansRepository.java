package com.mli.mpro.location.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.proposal.models.PensionPlans;

@Repository
public interface PensionPlansRepository extends MongoRepository<PensionPlans, String> {
	
	 List<PensionPlans> findAll();
}
