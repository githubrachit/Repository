package com.mli.mpro.document.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mli.mpro.document.models.DocumentPushInfo;

public interface TimePeriodRepository extends MongoRepository<DocumentPushInfo, String> {
	
	public DocumentPushInfo findByReason(String reason);
}
