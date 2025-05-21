package com.mli.mpro.onboarding.partner.validation.schema;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ValidationRepository extends MongoRepository<Schema, String> {

}