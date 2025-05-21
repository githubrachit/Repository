package com.mli.mpro.location.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mli.mpro.location.models.ExistingPolicyStatus;

public interface ExistingPolicyStatusRepository extends MongoRepository<ExistingPolicyStatus, String> {
	public List<ExistingPolicyStatus> findAll();
}
