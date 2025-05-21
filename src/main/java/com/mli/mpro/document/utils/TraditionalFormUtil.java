package com.mli.mpro.document.utils;

import com.mli.mpro.document.models.ProposalRiderDetails;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.proposal.models.BasicDetails;
import com.mli.mpro.proposal.models.EmploymentDetails;
import com.mli.mpro.proposal.models.PartyInformation;
import com.mli.mpro.proposal.models.PersonalIdentification;
import com.mli.mpro.proposal.models.PosvDetails;
import com.mli.mpro.proposal.models.ProductIllustrationResponse;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.mli.mpro.productRestriction.util.AppConstants.CAMEL_NO;
import static com.mli.mpro.productRestriction.util.AppConstants.CAMEL_YES;

public class TraditionalFormUtil {

    private static final Logger logger = LoggerFactory.getLogger(TraditionalFormUtil.class);

    public static Map<String, Object> setDataForPFForm(ProposalDetails proposalDetails,
                                                Map<String, Object> dataVariables) {
        logger.info("fetching data for proposal form main...");
        String policyNumber = proposalDetails.getApplicationDetails().getPolicyNumber();
        String ruralUrbanDetailsTagging = null;
        mapUnderwritingServiceDetails(proposalDetails, dataVariables);
        String purposeOfInsurance = Utility.setDefaultValuePosSeller(proposalDetails);
        String objectiveOfInsurance = proposalDetails.getProductDetails().get(0).getObjectiveOfInsurance();
        String pranNumber = proposalDetails.getPartyInformation().stream()
				.filter(Objects::nonNull)
				.filter(partyInfo -> AppConstants.PROPOSER.equalsIgnoreCase(partyInfo.getPartyType()))
				.filter(Objects::nonNull).map(PartyInformation::getPersonalIdentification).filter(Objects::nonNull)
				.findFirst().map(PersonalIdentification::getPranNumber).orElse("");
        BasicDetails proposerBasicDetails = proposalDetails.getPartyInformation().get(0).getBasicDetails();
        String isNPSCustomer = proposerBasicDetails.getIsNPSCustomer();
        isNPSCustomer =  StringUtils.isEmpty(isNPSCustomer) ? AppConstants.NO : isNPSCustomer ;
        String exitingCustomer = (proposalDetails.getUnderwritingServiceDetails().getDedupeDetails() != null
                && org.apache.commons.lang3.StringUtils.isNotBlank(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber())) ? "YES" : "NO";
        String previousPolicyNumber = "YES".equalsIgnoreCase(exitingCustomer)
                ?
                "YES-(".concat(proposalDetails.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber()).concat(")") : "NO";
        String channel = proposalDetails.getChannelDetails().getChannel();
        String gocaBrokerCode = proposalDetails.getSourcingDetails().getGoCABrokerCode();
        if (channel.equalsIgnoreCase(AppConstants.CHANNEL_AXIS) && !Utility.isDIYJourney(proposalDetails)) {
            gocaBrokerCode = AppConstants.CHANNEL_AXIS.concat(gocaBrokerCode);
        }
        String customerId = !StringUtils.isEmpty(proposalDetails.getBancaDetails()) ?
                proposalDetails.getBancaDetails().getCustomerId() : "";
        String productSolution = (!StringUtils.isEmpty(proposalDetails.getSalesStoriesProductDetails())
                && "YES".equalsIgnoreCase(proposalDetails.getSalesStoriesProductDetails().getIsSalesProduct())) ?
                "YES" : "N/A";
        //FUL2-202646 Go Green PF
        String physicalDocument = Optional.ofNullable(proposalDetails).map(ProposalDetails::getPosvDetails).map(PosvDetails::getGoGreen).isPresent()
                                  ?AppConstants.YES.equalsIgnoreCase(proposalDetails.getPosvDetails().getGoGreen())
                                   ?AppConstants.CAMEL_NO:AppConstants.CAMEL_YES
                                  :AppConstants.CAMEL_YES;
        String nonNriFinalStage = Utility.finalStageNationalityCheck(proposalDetails);
        dataVariables.put(AppConstants.FINAL_STAGE,nonNriFinalStage);
        dataVariables.put(AppConstants.PHYSICAL_POLICY,physicalDocument);
        dataVariables.put("objectiveOfInsurance", objectiveOfInsurance);
		dataVariables.put("pranNumber", pranNumber);
		dataVariables.put("isNPSCustomer", isNPSCustomer);
        dataVariables.put("productSolution", productSolution);
        dataVariables.put("affinityCustomer", "N/A");
        //as discussed in case of aggregator always empty
        if (proposalDetails.getChannelDetails().getChannel()
            .equalsIgnoreCase(AppConstants.CHANNEL_AGGREGATOR)) {
            dataVariables.put("existingCustomer", "");
        } else {
            dataVariables.put("existingCustomer", exitingCustomer);
        }
		dataVariables.put("existingPolicyNumber", previousPolicyNumber);

        dataVariables.put("payorImageNA", "Image not available");
        dataVariables.put("proposalNumber", policyNumber);
        dataVariables.put("gocaBrokerCode", gocaBrokerCode);
        dataVariables.put("comboProposalNumber", "N/A");
        dataVariables.put("customerId", customerId);
        dataVariables.put("channel", channel);
        dataVariables.put("purposeOfInsurance", purposeOfInsurance);
        return dataVariables;
    }
    public static void mapUnderwritingServiceDetails(ProposalDetails proposalDetails, Map<String, Object> dataVariables) {
    	String ruralUrbanDetailsTagging = null;
        if(Objects.nonNull(proposalDetails.getUnderwritingServiceDetails()) && Objects.nonNull(proposalDetails.getUnderwritingServiceDetails().getRuralUrbanDetails()) && !StringUtils.isEmpty(proposalDetails.getUnderwritingServiceDetails().getRuralUrbanDetails().getTagging())) {
        	ruralUrbanDetailsTagging = proposalDetails.getUnderwritingServiceDetails().getRuralUrbanDetails().getTagging();
        } else {
        	ruralUrbanDetailsTagging = AppConstants.BLANK;
        }
        dataVariables.put("ruralUrbanDetailsTagging", ruralUrbanDetailsTagging);
    }

    public static ProposalRiderDetails setCIRiderDetails(
        ProductIllustrationResponse illustrationResponse,ProposalRiderDetails details, String riderName) {
        String gst = AppConstants.ZERO;
        if(!org.apache.commons.lang3.StringUtils.isEmpty(illustrationResponse.getSmartHealthPlusRiderGST())) {
            gst = String.valueOf(roundOffValue(illustrationResponse.getSmartHealthPlusRiderGST()));
        }
        details.setRiderName(riderName);
        details.setCoverageTerm(String.format("%.0f", roundOffValue(illustrationResponse.getSmartHealthPlusRiderTerm())));
        details.setModalPremium(String.format("%.0f", roundOffValue(illustrationResponse.getFirstYearSmartHealthPlusPremiumSummary())));
        details.setRiderSumAssured(String.format("%.0f", roundOffValue(illustrationResponse.getSmartHealthPlusRiderSumAssured())));
        details.setRiderGST(gst);
        details.setPremiumPayingTerm(org.apache.commons.lang3.StringUtils.isEmpty(illustrationResponse.getPremiumPaymentTerm()) ? "NA" : illustrationResponse.getPremiumPaymentTerm());
        return details;
    }
    public static double roundOffValue(String value) {

        double convertedValue = 0;
        if (!org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            convertedValue = Math.round(Double.valueOf(value) * 100.00) / 100.00;
        }
        return convertedValue;
    }
}
