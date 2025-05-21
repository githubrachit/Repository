package com.mli.mpro.productRestriction.repository;

import com.mli.mpro.productRestriction.models.RestrictionData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestrictionRepository extends MongoRepository<RestrictionData,String> {
    RestrictionData findByProductIdAndChannel(String productId, String channel);

    RestrictionData findByProductIdAndChannelAndCustomerClassification(String productId, String channel, String customerClassification);
}
