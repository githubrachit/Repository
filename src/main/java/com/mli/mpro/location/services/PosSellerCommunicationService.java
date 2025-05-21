package com.mli.mpro.location.services;

import com.mli.mpro.posseller.models.MessageRequest;

public interface PosSellerCommunicationService {
	
	 void findByUnderwritingStatusAndStageAndPosvJourneyStatus();
	
	 void sendSms(MessageRequest messageRequest);
	
	 void sendEmail(MessageRequest messageRequest);
			
	 void getPosvTriggerDetailsForEmail();

}
