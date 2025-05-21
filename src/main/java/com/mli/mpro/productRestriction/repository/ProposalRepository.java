package com.mli.mpro.productRestriction.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mli.mpro.proposal.models.ProposalDetails;

@Repository
public interface ProposalRepository extends MongoRepository<ProposalDetails, String>{
	
	@Query(fields = "{'transactionId':1,'sourcingDetails':1,'additionalFlags.sourceChannel':1,'applicationDetails.formType':1,'posvDetails.posvQuestions':1,'productDetails':1,'partyInformation':1,'lifeStyleDetails':1,'employmentDetails':1,'underwritingServiceDetails':1,'additionalFlags.showHealthQuesOnPosv':1, 'additionalFlags.isSspSwissReCase':1,'diyBrmsFieldConfigurationDetails':1}")
	ProposalDetails findByTransactionId(long transactionId);

	List<ProposalDetails> findByTransactionIdIn(List<Long> transactionIdList);

	ProposalDetails findBySourcingDetailsAgentIdAndTransactionId(String agentId, Long transactionId);

	@Query(fields = "{'additionalFlags':1}")
	ProposalDetails findAdditionalFlagsByTransactionId(long transactionId);

	@Query(value="{$and:[{'applicationDetails.underwritingStatus':'Completed'},{'applicationDetails.stage':{$in : ['5','6']}},"
			+ "{'applicationDetails.posvJourneyStatus':'POSV_RECEIVED'},{'channelDetails.channel': 'X'},{'sourcingDetails.isPosSeller':true}]}",
			fields = "{'transactionId':1,'applicationDetails':1, 'sourcingDetails':1,'underwritingServiceDetails':1, 'partyInformation':1}")
	List<ProposalDetails> findByUnderwritingStatusAndStageAndPosvJourneyStatus();

    @Query(value = "{'applicationDetails.updatedTime' : {$gte : ?0, $lte : ?1},'sourcingDetails.isPosSeller':true,'additionalFlags.posEmailStatus':false,'applicationDetails.posvJourneyStatus': {$in : ['PUSHED_TO_POSV']}}",
            fields = "{'transactionId':1,'applicationDetails':1, 'sourcingDetails':1, 'partyInformation':1}")
    List<ProposalDetails> findByApplicationDetailsUpdatedTime(Date startDate, Date endDate);

	// ful2-75750
	@Query(value = "{$and:[{'applicationDetails.createdTime' : { $gte: ?0, $lte: ?1 }},{'bank':{$exists:true}},{'applicationDetails.stage':'9'},{'channelDetails.channel': 'Thanos'},{'additionalFlags':{$exists:true}}]}", fields = "{'transactionId':1,'channelDetails.channel':1,'bank':1,'applicationDetails.stage':1,'applicationDetails.createdTime':1,'applicationDetails.policyNumber':1,'sourcingDetails.agentId':1,'additionalFlags':1,'posvDetails.posvStatus.submittedOTPDate':1}")
	List<ProposalDetails> findByApplicationDetailsCreatedTime(Date startDate, Date endDate,Pageable pageable);

	@Query(fields = "{'applicationDetails':1, 'partyInformation':1, 'channelDetails':1, 'additionalFlags':1, 'underwritingServiceDetails':1, 'paymentDetails':1, 'productDetails':1,'sourcingDetails':1}")
	ProposalDetails findByApplicationDetailsQuoteId(String quoteId);
	@Query(fields = "{'applicationDetails':1, 'partyInformation':1, 'channelDetails':1, 'additionalFlags':1, 'underwritingServiceDetails':1,'paymentDetails':1,'productDetails':1}")
	ProposalDetails findByApplicationDetailsPolicyNumberOrApplicationDetailsQuoteId(String policyNumber,String quoteId);
	ProposalDetails findByApplicationDetailsPolicyNumber(String policyNumber);
	ProposalDetails findByTransactionId(Long transactionId);
	@Query(value = "{'bancaDetails.leadId': ?0}", fields = "{'transactionId': 1}")
	Optional<ProposalDetails> findTransactionIdByTraceId(String traceId);
}