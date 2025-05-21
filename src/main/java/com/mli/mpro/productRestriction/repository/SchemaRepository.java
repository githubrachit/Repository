package com.mli.mpro.productRestriction.repository;

import com.mli.mpro.productRestriction.models.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchemaRepository extends MongoRepository<Schema, String> {
}
