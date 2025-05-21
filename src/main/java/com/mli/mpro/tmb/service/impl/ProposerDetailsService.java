package com.mli.mpro.tmb.service.impl;


import com.mli.mpro.productRestriction.repository.ProposalRepository;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.tmb.model.AccountDetailsResponse;
import com.mli.mpro.tmb.model.CustomerDetailsResponse;
import com.mli.mpro.tmb.model.OnboardingState;
import com.mli.mpro.tmb.model.SaveProposalRequest;
import com.mli.mpro.tmb.repository.OnboardingEventStateRepository;
import com.mli.mpro.tmb.service.CustomerDetailsService;
import com.mli.mpro.tmb.utility.ServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProposerDetailsService {

    private static final Logger log = LoggerFactory.getLogger(ProposerDetailsService.class);

    @Autowired
    CustomerDetailsService customerDetailsService;
    @Autowired
    TmbProposalTransFormationImpl tmbProposalTransFormation;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    OnboardingEventStateRepository onboardingEventStateRepository;

    @Async
    public void setProposerDetails(OnboardingState onboardingState, String eventType) {
        try {
            SaveProposalRequest request = new SaveProposalRequest();
            preprareCustomerfetchRequest(onboardingState, eventType, request);
            log.info("Getting customer details for transactionId {}", request.getTransactionId());
            performAPICallsForTMBJourney(onboardingState,request);
            if (isTmbAPISuccess(request) && AppConstants.Y.equalsIgnoreCase(onboardingState.getEvents().get("pdf").getEkyc())) {
                boolean isDatasaved = saveProposalAndRedirect(request);
                if (isDatasaved) {
                    onboardingState.getEvents().get(eventType).setEventDone(true);
                }
            }
            onboardingEventStateRepository.save(onboardingState);
        } catch (Exception e) {
            log.error("Error occurred while getting customer details {}", e.getMessage());
        }
    }

    private boolean isTmbAPISuccess(SaveProposalRequest request) {

        if ("pdf".equalsIgnoreCase(request.getEventType()) && ("000").equalsIgnoreCase( request.getCustomerDetailsResponse().getStatuscode())
                && ("000").equalsIgnoreCase( request.getAccountDetailsResponse().getStatuscode())) {
            return true;
        } else return "idf".equalsIgnoreCase(request.getEventType()) && ("000").equalsIgnoreCase(request.getCustomerDetailsResponse().getStatuscode());
    }

    private boolean saveProposalAndRedirect(SaveProposalRequest request) {
        ProposalDetails proposalDetails;
        long transactionId = 0L;
        try {
            proposalDetails = proposalRepository.findByTransactionId(request.getTransactionId());
            if (Objects.nonNull(proposalDetails) && AppConstants.INSURED_DATA_FETCH.equalsIgnoreCase(request.getEventType())) {
                List<PartyInformation> partyInformation = proposalDetails.getPartyInformation();
                PartyInformation insuredPartyInformation = tmbProposalTransFormation.getInsuredPartyInformation(request);
                partyInformation.set(1, insuredPartyInformation);
                proposalDetails.setPartyInformation(partyInformation);
                proposalDetails.getAdditionalFlags().setInsurerVerified(true);
                proposalDetails.getAdditionalFlags().setCurrentInsurerVerified(false);
                log.info("Proposal already exists for transactionId {} ", request.getTransactionId());
            } else {
                proposalDetails = new ProposalDetails();
                proposalDetailsTransformation(request, proposalDetails);
            }
            tmbProposalTransFormation.addMandatoryFields(proposalDetails);
            transactionId = proposalDetails.getTransactionId();
            ProposalDetails savedProposalDetails;
            log.info("transactionId generated for saving in db {}", transactionId);
            savedProposalDetails = proposalRepository.save(proposalDetails);
            log.info("Proposal saved successfully for transactionId {} {} ", savedProposalDetails.getTransactionId(), savedProposalDetails);
            return true;

        } catch (Exception ex) {
            log.error("Error occurred while saving proposal and redirecting {} ", ex.getMessage());
        }
        return false;
    }

    private void proposalDetailsTransformation(SaveProposalRequest request, ProposalDetails proposalDetails) {
        try {

            proposalDetails.setTransactionId(request.getTransactionId());
            tmbProposalTransFormation.addBancaDetails(request, proposalDetails);
            tmbProposalTransFormation.addPartyInformation(request, proposalDetails);
            tmbProposalTransFormation.addBank(request, proposalDetails);
            tmbProposalTransFormation.addCkycDetails(request, proposalDetails);
            tmbProposalTransFormation.addNomineeDetails(proposalDetails);
            tmbProposalTransFormation.addSourcingDetails(request,proposalDetails);
            tmbProposalTransFormation.addChannelDetails(proposalDetails);
            tmbProposalTransFormation.addProductDetails(proposalDetails);
            tmbProposalTransFormation.addPsmDetails(proposalDetails);
            tmbProposalTransFormation.addApplicationDetails(proposalDetails);
            tmbProposalTransFormation.addAdditonalFlag(proposalDetails);
            tmbProposalTransFormation.addEmploymentDetails(proposalDetails);
            tmbProposalTransFormation.addPaymentDetails(proposalDetails);


        } catch (Exception e) {
            log.error("Error occurred while transforming proposal details for transactionId {}", request.getTransactionId());
        }
    }


    private void preprareCustomerfetchRequest(OnboardingState onboardingState, String eventType, SaveProposalRequest request) {
        request.setTransactionId(onboardingState.getTransactionId());
        request.setCustomerId(onboardingState.getEvents().get(eventType).getCustomerId());
        request.setEventType(eventType);
        request.setAgentId(onboardingState.getAgentId());
        request.setCorrelationId(onboardingState.getEvents().get(eventType).getBankCorrelationId());
    }

    private void performAPICallsForTMBJourney(OnboardingState onboardingState,SaveProposalRequest request) {
        try {
            log.info("Performing API calls for TMB journey for  transactionId {}", request.getTransactionId());
            callTMBCustomerDetails(onboardingState,request);
            if (("000").equalsIgnoreCase( request.getCustomerDetailsResponse().getStatuscode()) && ("pdf").equalsIgnoreCase(request.getEventType())) {
                callTMBAccountDetails(onboardingState,request);
            }
        } catch (Exception e) {
            log.error("Error occurred while performing API calls for TMB journey for transactionId {}", request.getTransactionId());
        }
    }

    private void callTMBAccountDetails(OnboardingState onboardingState,SaveProposalRequest request) {
        AccountDetailsResponse accountDetailsResponse = customerDetailsService.fetchAccountDetails(onboardingState,request);
        if (Objects.nonNull(accountDetailsResponse) && ("000").equalsIgnoreCase(accountDetailsResponse.getStatuscode())) {
            request.setAccountDetailsResponse(accountDetailsResponse);
        } else {
            log.error("Error occurred while fetching account details for customer Id {}", request.getCustomerId());
        }
    }


    private void callTMBCustomerDetails(OnboardingState onboardingState,SaveProposalRequest request) {
        CustomerDetailsResponse customerDetailsResponse = customerDetailsService.fetchCustomerDetails(onboardingState,request);
        if (Objects.nonNull(customerDetailsResponse)) {
            if (ServiceConstants.EKYC_FAILURE.equalsIgnoreCase(customerDetailsResponse.getResponse())){
                log.info("Customer Id is not registered with ckyc for customer Id {}", request.getCustomerId());
                onboardingState.getEvents().get(request.getEventType()).setEkyc("N");
            }
            if (("000").equalsIgnoreCase(customerDetailsResponse.getStatuscode())) {
                onboardingState.getEvents().get(request.getEventType()).setEkyc(customerDetailsResponse.getKycFlag());
            }
            request.setCustomerDetailsResponse(customerDetailsResponse);
        }
    }


}
