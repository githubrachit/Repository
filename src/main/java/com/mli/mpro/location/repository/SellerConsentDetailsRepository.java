package com.mli.mpro.location.repository;

import com.mli.mpro.document.models.SellerConsentDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerConsentDetailsRepository extends MongoRepository<SellerConsentDetails, String> {
    SellerConsentDetails findByUniqueId(String uniqueId);

    SellerConsentDetails findByPolicyNumber(String policyNumber);

}
