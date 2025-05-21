package com.mli.mpro.email.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mli.mpro.email.models.EmailDetails;

@Repository
public interface EmailRepository extends MongoRepository<EmailDetails,String>{
    
    public EmailDetails findByDocumentType(String documentType);

}
