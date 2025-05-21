package com.mli.mpro.document.mapper;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.common.models.Response;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.util.*;

import static com.mli.mpro.productRestriction.util.AppConstants.BLANK;
import static com.mli.mpro.productRestriction.util.AppConstants.LIFE_INSURED;
import static com.mli.mpro.utils.Utility.*;

@Service
public class NeoAutoCancellationDataMapper {

    private static final Logger logger = LoggerFactory.getLogger(NeoAutoCancellationDataMapper.class);

    public Context setDocumentData(ProposalDetails proposalDetails) throws UserHandledException {
        logger.info("Starting NeoAutoCancellationDocument data mapping for transactionId {}", proposalDetails.getTransactionId());
        Map<String, Object> dataMap = new HashMap<>();
        String agentId = BLANK;
        String proposalNumber = BLANK;
        try {
            mappedBasicDetailsRelatedData(proposalDetails, dataMap);

            mappedNomineeDetailsRelatedData(proposalDetails, dataMap);

            mappedProductDetailsRelatedData(proposalDetails, dataMap);

            mappedPaymentDetailsRelatedData(proposalDetails, dataMap);

            if (Objects.nonNull(proposalDetails.getApplicationDetails())) {
                proposalNumber = Utility.nullSafe(proposalDetails.getApplicationDetails().getPolicyNumber());
            }
            if (Objects.nonNull(proposalDetails.getSourcingDetails())) {
                agentId = Utility.nullSafe(proposalDetails.getSourcingDetails().getAgentId());
            }
            dataMap.put("proposalNumber", proposalNumber);
            dataMap.put("agentId", agentId);
            dataMap.put("date", dateFormatter(new Date()));

        } catch (Exception e) {
            logger.error("Data addition failed for autoCancellation Document:", e);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Data addition failed for autoCancellation document");
            throw new UserHandledException(new Response(), errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Context autoCancellationDocumentContext = new Context();
        autoCancellationDocumentContext.setVariables(dataMap);
        logger.info("NeoAutoCancellationDocument data mapping complete for transactionId {}", proposalDetails.getTransactionId());
        return autoCancellationDocumentContext;
    }

    private void mappedPaymentDetailsRelatedData(ProposalDetails proposalDetails, Map<String, Object> dataMap) {
        String moneyReceivedDate = BLANK;
        String premiumPaid = BLANK;
        PaymentDetails paymentDetails = proposalDetails.getPaymentDetails();
        if (Objects.nonNull(paymentDetails)) {
            if (Objects.nonNull(paymentDetails.getReceipt()) && Objects.nonNull(paymentDetails.getReceipt().get(0).getPaymentDate())
                    && !(paymentDetails.getReceipt().get(0).getPaymentDate().isEmpty())) {
                moneyReceivedDate = proposalDetails.getPaymentDetails().getReceipt().get(0).getPaymentDate();
            }
            if (Objects.nonNull(paymentDetails.getReceipt()) && Objects.nonNull(paymentDetails.getReceipt().get(0).getAmount())
                    && !(paymentDetails.getReceipt().get(0).getAmount().isEmpty())) {
                premiumPaid = String.format("%.0f", roundOffValue(proposalDetails.getPaymentDetails().getReceipt().get(0).getAmount()));
            }
        }
        dataMap.put("moneyReceivedDate", moneyReceivedDate);
        dataMap.put("premiumPaid", premiumPaid);
    }

    private void mappedProductDetailsRelatedData(ProposalDetails proposalDetails, Map<String, Object> dataMap) {
        String monthlyIncome = BLANK;
        String sumAssuredWithPlanDetails = BLANK;
        String policyTerm = BLANK;
        String premiumPayingTerm = BLANK;
        String variantName = BLANK;
        ProductInfo productInfo = proposalDetails.getProductDetails()
                .stream()
                .findFirst()
                .map(ProductDetails::getProductInfo)
                .orElse(null);


        if (Objects.nonNull(productInfo)) {
            if (!StringUtils.isEmpty(productInfo.getBenefitMonthlyIncome())) {
                monthlyIncome = convertToBigDecimal(Double.valueOf(productInfo.getBenefitMonthlyIncome()));
            }
            if (Objects.nonNull(productInfo.getProductIllustrationResponse())) {
                sumAssuredWithPlanDetails = getPlanDetailsWithSumAssured(productInfo.getProductName(), getSumAssured(productInfo));
            }
            policyTerm = Utility.nullSafe(productInfo.getPolicyTerm());
            premiumPayingTerm = Utility.nullSafe(productInfo.getPremiumPaymentTerm());
            variantName = Utility.nullSafe(productInfo.getVariant());
        }

        dataMap.put("monthlyIncome", monthlyIncome);
        dataMap.put("sum_Assured_With_Plan_Details", sumAssuredWithPlanDetails);
        dataMap.put("policyTerm", policyTerm);
        dataMap.put("premiumPayingTerm", premiumPayingTerm);
        dataMap.put("variantName", variantName);
    }

    private void mappedNomineeDetailsRelatedData(ProposalDetails proposalDetails, Map<String, Object> dataMap) {
        String nomineeName = BLANK;
        String nomineeDob = BLANK;

        if (Objects.nonNull(proposalDetails.getNomineeDetails())
                && Objects.nonNull(proposalDetails.getNomineeDetails().getPartyDetails())
                && !proposalDetails.getNomineeDetails().getPartyDetails().isEmpty()) {
            nomineeName = proposalDetails.getNomineeDetails().getPartyDetails().get(0).getFirstName();
            nomineeDob = dateFormatter(proposalDetails.getNomineeDetails().getPartyDetails().get(0).getDob());
        }
        dataMap.put("nomineeName", nomineeName);
        dataMap.put("nomineeDOB", nomineeDob);
    }

    private void mappedBasicDetailsRelatedData(ProposalDetails proposalDetails, Map<String, Object> dataMap) {
        String proposerName = BLANK;
        String dob = BLANK;
        String contactNumber = BLANK;
        String email = BLANK;
        String address = BLANK;
        String annualIncome = BLANK;

        BasicDetails basicDetails = proposalDetails.getPartyInformation()
                .stream()
                .filter(partyInformation -> LIFE_INSURED.equalsIgnoreCase(partyInformation.getPartyType()))
                .findFirst()
                .map(PartyInformation::getBasicDetails)
                .orElse(null);

        if (Objects.nonNull(basicDetails)) {
            proposerName = getName(getTitle(basicDetails.getGender()), basicDetails.getFirstName(), basicDetails.getMiddleName(),
                    basicDetails.getLastName());
            dob = dateFormatter(basicDetails.getDob());

            if (Objects.nonNull(basicDetails.getAddress())
                    && !basicDetails.getAddress().isEmpty()
                    && Objects.nonNull(basicDetails.getAddress().get(0).getAddressDetails())) {
                AddressDetails addressDetails = basicDetails.getAddress().get(0).getAddressDetails();
                address = getAddress(addressDetails.getHouseNo(), addressDetails.getArea(),
                        addressDetails.getLandmark(), addressDetails.getPinCode(), addressDetails.getCity(),
                        addressDetails.getState(), addressDetails.getCountry());
            }

            if (!StringUtils.isEmpty(basicDetails.getAnnualIncome())) {
                annualIncome = convertToBigDecimal(Double.valueOf(basicDetails.getAnnualIncome()));
            }
        }

        PersonalIdentification personalIdentification = proposalDetails.getPartyInformation()
                .stream()
                .findFirst()
                .map(PartyInformation :: getPersonalIdentification)
                .orElse(null);

        if (Objects.nonNull(personalIdentification)) {
            email = Utility.nullSafe(personalIdentification.getEmail());
            if (Objects.nonNull(personalIdentification.getPhone()) &&
                    !personalIdentification.getPhone().isEmpty()) {
                contactNumber = Utility.nullSafe(personalIdentification.getPhone().get(0).getPhoneNumber());
            }
        }

        dataMap.put("proposerName", proposerName);
        dataMap.put("contactNumber", contactNumber);
        dataMap.put("email", email);
        dataMap.put("dob", dob);
        dataMap.put("address", address);
        dataMap.put("customerSignature", proposerName);
        dataMap.put("annualIncome", annualIncome);
    }

    private String getSumAssured(ProductInfo productInfo) {
        return String.format("%.0f", roundOffValue(productInfo.getProductIllustrationResponse().getSumAssured()));
    }

}
