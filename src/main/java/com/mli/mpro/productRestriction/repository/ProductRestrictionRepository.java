package com.mli.mpro.productRestriction.repository;

import com.mli.mpro.productRestriction.models.ProductRestrictionMasterData;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRestrictionRepository extends MongoRepository<ProductRestrictionMasterData,String> {

    ProductRestrictionMasterData findByType(String type);
}
