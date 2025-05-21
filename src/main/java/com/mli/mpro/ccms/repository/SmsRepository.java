package com.mli.mpro.ccms.repository;

import com.mli.mpro.ccms.model.SmsDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends MongoRepository<SmsDetails,String>{

    public SmsDetails findByDocumentType(String documentType);

}
