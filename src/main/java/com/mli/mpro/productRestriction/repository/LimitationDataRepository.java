package com.mli.mpro.productRestriction.repository;

import com.mli.mpro.productRestriction.models.LimitationData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitationDataRepository extends MongoRepository<LimitationData,String> {

    LimitationData findByChannelAndProductIdAndOccupationAndCommunicationCity(String channel,String productId,String occupation, String communicationCity);
    /* FUL2-9472 WLS Product Restriction */
    LimitationData findByProductIdAndLimitationType(String productId, String limitationType);
}
