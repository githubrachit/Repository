package com.mli.mpro.location.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mli.mpro.location.models.OTPDetails;

public interface OTPDetailsRepository extends MongoRepository<OTPDetails, String> {

    List<OTPDetails> findByTransactionIdAndActionTypeAndOtpGeneratedTimeBetween(Long transactionId,String actionType, Date startDate, Date endDate);
    List<OTPDetails> findByCustomerIdAndOtpGeneratedTimeBetween(String customerId, Date startDate, Date endDate);
}
