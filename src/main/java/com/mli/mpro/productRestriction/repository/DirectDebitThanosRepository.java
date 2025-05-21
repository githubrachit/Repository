package com.mli.mpro.productRestriction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.proposal.models.DirectDebitThanos;

@Repository
public interface DirectDebitThanosRepository extends MongoRepository<DirectDebitThanos, String>{

}
