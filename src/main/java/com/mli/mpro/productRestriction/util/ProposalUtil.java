package com.mli.mpro.productRestriction.util;

import com.mli.mpro.agent.models.MsgInfo;
import com.mli.mpro.common.exception.ErrorMessageConfig;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.config.BeanUtil;
import com.mli.mpro.config.ExternalServiceConfig;
import com.mli.mpro.docsApp.models.DocsappResponse;
import com.mli.mpro.productRestriction.models.planCodeModels.PlanCodeResponse;
import com.mli.mpro.proposal.models.*;
import com.mli.mpro.productRestriction.models.ProductRestrictionPayload;
import com.mli.mpro.productRestriction.models.proposalFormRequestModels.Header;
import com.mli.mpro.productRestriction.models.proposalFormRequestModels.OutputRequest;
import com.mli.mpro.productRestriction.models.proposalFormRequestModels.Payload;
import com.mli.mpro.productRestriction.models.proposalFormRequestModels.Request;
import com.mli.mpro.proposal.models.ProposalDetails;
import com.mli.mpro.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mli.mpro.productRestriction.util.AppConstants.*;
import static com.mli.mpro.productRestriction.util.AppConstants.OTHERS;
import static java.util.Objects.nonNull;

@Component
public class ProposalUtil {
    private static final Logger log = LoggerFactory.getLogger(ProposalUtil.class);
    @Value("${proposal.bypass.header.encrypt.value}")
    private String apiClientSecret;

    public OutputRequest setDataForProposalForm(ProductRestrictionPayload productRestrictionPayload) throws UserHandledException {
        log.info("setting data for proposal form {}", productRestrictionPayload);
        Request proposalFormRequest = new Request();
        Header proposalFormHeader = new Header();
        Payload requestPayload = new Payload();
        OutputRequest outputRequest = new OutputRequest();
        try {
            proposalFormHeader.setSoaAppId(FULFILLMENT);
            proposalFormHeader.setSoaCorrelationId(String.valueOf(productRestrictionPayload.getTransactionId()));
            log.info("setting SoaAppId {} and SoaCorrelationId {}", proposalFormHeader.getSoaAppId(),
                    proposalFormHeader.getSoaCorrelationId());
            proposalFormRequest.setHeader(proposalFormHeader);
            log.info("Setting Header {}", proposalFormRequest.getHeader());

            // Setting Proposal Form data
            requestPayload.setObjectiveOfInsurance(productRestrictionPayload.getObjectiveOfInsurance().trim().isEmpty() ? AppConstants.INDIVIDUAL_POLICY :
                    productRestrictionPayload.getObjectiveOfInsurance().toUpperCase());

            if (DEPENDENT.equalsIgnoreCase(productRestrictionPayload.getFormType())
                    || (FORM3.equalsIgnoreCase(productRestrictionPayload.getFormType())
                    && !Utility.isSchemeBCase(productRestrictionPayload.getSchemeType()))){
                // FORM-C Scheme-A check FUL2-60217
                createReqPayloadForGivenPartyDetails(productRestrictionPayload,requestPayload,INSURED);
            } else {
                createReqPayloadForGivenPartyDetails(productRestrictionPayload,requestPayload,PROPOSER);
            }
            requestPayload.setFormType(Utility.getFormTypeTransformation(productRestrictionPayload.getFormType()
                    , productRestrictionPayload.getObjectiveOfInsurance()));
            requestPayload.setNomineeRelationship(N);
            requestPayload.setRelationshipWithPayor(N);
            requestPayload.setFsa(ZERO);
            requestPayload.setIsPep(N);
            requestPayload.setPayorOrNot(N);
            if(productRestrictionPayload.isCIRider()){
                requestPayload.setPolicyTerm(productRestrictionPayload.getPolicyTerm());
                requestPayload.setPremiumPaymentTerm(productRestrictionPayload.getPremiumPaymentTerm());
            }
            requestPayload.setIsDiabetic(productRestrictionPayload.isDiabetic() ? Y:N);

            // Rider details
            requestPayload.setCiRiderSa(Objects.isNull(productRestrictionPayload.getCurrentCIRiderSumAssured()) || productRestrictionPayload.getCurrentCIRiderSumAssured().trim().isEmpty()?
                    ZERO:productRestrictionPayload.getCurrentCIRiderSumAssured());
            requestPayload.setClientCiDdRiderSA(Objects.isNull(productRestrictionPayload.getCurrentCIRiderSumAssured()) || productRestrictionPayload.getCurrentCIRiderSumAssured().trim().isEmpty()?
                    ZERO:productRestrictionPayload.getCurrentCIRiderSumAssured());
            requestPayload.setPolicyCiDdRiderSA(Objects.isNull(productRestrictionPayload.getCurrentCIRiderSumAssured()) || productRestrictionPayload.getCurrentCIRiderSumAssured().trim().isEmpty()?
                    ZERO:productRestrictionPayload.getCurrentCIRiderSumAssured());
            requestPayload.setClientAcoRiderSA(Objects.isNull(productRestrictionPayload.getCurrentACORiderSumAssured()) || productRestrictionPayload.getCurrentACORiderSumAssured().trim().isEmpty()?
                    ZERO:productRestrictionPayload.getCurrentACORiderSumAssured());
            requestPayload.setPolicyAcoRiderSA(Objects.isNull(productRestrictionPayload.getCurrentACORiderSumAssured()) || productRestrictionPayload.getCurrentACORiderSumAssured().trim().isEmpty() ?
                    ZERO:productRestrictionPayload.getCurrentACORiderSumAssured());
            requestPayload.setClientAciRiderSa(Objects.isNull(productRestrictionPayload.getCurrentACIRiderSumAssured()) || productRestrictionPayload.getCurrentACIRiderSumAssured().trim().isEmpty()?
                    ZERO:productRestrictionPayload.getCurrentACIRiderSumAssured());
            requestPayload.setPolicyAciRiderSa(Objects.isNull(productRestrictionPayload.getCurrentACIRiderSumAssured()) || productRestrictionPayload.getCurrentACIRiderSumAssured().trim().isEmpty()?
                    ZERO:productRestrictionPayload.getCurrentACIRiderSumAssured());

            if(productRestrictionPayload.isWOPPlusRider() && productRestrictionPayload.getProductId().equals(SSP_PRODUCT_ID)) {
                   requestPayload.setPolicyWopRiderSA(String.valueOf(productRestrictionPayload.getSumAssured()));
            }
            else {
                    requestPayload.setPolicyWopRiderSA(ZERO);
                }
            requestPayload.setInitiativeType(NA);
            requestPayload.setMedicalQuestion19(N);
            requestPayload.setMedicalQuestion20(N);
            requestPayload.setMedicalQuestion20A(N);
            requestPayload.setMedicalQuestion20B(N);
            requestPayload.setMedicalQuestion20C(N);
            requestPayload.setMedicalQuestion20D(N);
            requestPayload.setMedicalQuestion21(N);
            requestPayload.setMedicalQuestion21A(N);
            requestPayload.setMedicalQuestion21B(N);
            requestPayload.setMedicalQuestion22(N);
            requestPayload.setMedicalQuestion22A(N);
            requestPayload.setMedicalQuestion22B(N);
            requestPayload.setMedicalQuestion22C(N);
            requestPayload.setMedicalQuestion22D(N);
            requestPayload.setMedicalQuestion23(N);
            requestPayload.setMedicalQuestion23A(N);
            requestPayload.setMedicalQuestion23B(N);
            requestPayload.setMedicalQuestion23C(N);
            requestPayload.setMedicalQuestion23D(N);
            requestPayload.setMedicalQuestion23E(N);
            requestPayload.setMedicalQuestion24(N);
            requestPayload.setMedicalQuestion24A(N);
            requestPayload.setMedicalQuestion24B(N);
            requestPayload.setMedicalQuestion24C(N);
            requestPayload.setMedicalQuestion25(N);
            requestPayload.setMedicalQuestion25A(N);
            requestPayload.setMedicalQuestion25B(N);
            requestPayload.setMedicalQuestion25C(N);
            requestPayload.setMedicalQuestion25D(N);
            requestPayload.setMedicalQuestion26(N);
            requestPayload.setMedicalQuestion26A(N);
            requestPayload.setMedicalQuestion27(N);
            requestPayload.setMedicalQuestion27A(N);
            requestPayload.setMedicalQuestion27B(N);
            requestPayload.setMedicalQuestion27C(N);
            requestPayload.setMedicalQuestion27D(N);
            requestPayload.setMedicalQuestion27E(N);
            requestPayload.setMedicalQuestion27F(N);
            requestPayload.setMedicalQuestion28(N);
            requestPayload.setMedicalQuestion28A(N);
            requestPayload.setMedicalQuestion28B(N);
            requestPayload.setMedicalQuestion28C(N);
            requestPayload.setMedicalQuestion28D(N);
            requestPayload.setMedicalQuestion28E(N);
            requestPayload.setMedicalQuestion28F(N);
            requestPayload.setMedicalQuestion28G(N);
            requestPayload.setMedicalQuestion29(N);
            requestPayload.setMedicalQuestion29A(N);
            requestPayload.setMedicalQuestion29B(N);
            requestPayload.setMedicalQuestion29C(N);
            requestPayload.setMedicalQuestion29D(N);
            requestPayload.setMedicalQuestion29E(N);
            requestPayload.setMedicalQuestion29F(N);
            requestPayload.setMedicalQuestion29G(N);
            requestPayload.setMedicalQuestion29H(N);
            requestPayload.setMedicalQuestion29I(N);
            requestPayload.setMedicalQuestion29J(N);
            requestPayload.setMedicalQuestion29K(N);
            requestPayload.setMedicalQuestion29L(N);
            requestPayload.setMedicalQuestion29M(N);
            requestPayload.setMedicalQuestion29N(N);
            requestPayload.setMedicalQuestion30(N);
            requestPayload.setMedicalQuestion30A(N);
            requestPayload.setMedicalQuestion30B(N);
            requestPayload.setMedicalQuestion30C(N);
            requestPayload.setMedicalQuestion30D(N);
            requestPayload.setMedicalQuestion30E(N);
            requestPayload.setMedicalQuestion30F(N);
            requestPayload.setMedicalQuestion30G(N);
            requestPayload.setAlcoholfreqQuestion37B(N);
            requestPayload.setAlcoholQtyQuestion37C(ZERO);
            requestPayload.setAlcoholType37A(N);
            requestPayload.setCanceCareQuestion31(N);
            requestPayload.setCanceCareQuestion31A(N);
            requestPayload.setCanceCareQuestion32(N);
            requestPayload.setCanceCareQuestion32A(N);
            requestPayload.setCanceCareQuestion33(N);
            requestPayload.setCanceCareQuestion33A(N);
            requestPayload.setCanceCareQuestion34(N);
            requestPayload.setCanceCareQuestion34A(N);
            requestPayload.setCanceCareQuestion35(N);
            requestPayload.setCanceCareQuestion35A(N);
            requestPayload.setTobaccoFreqQuestion36B(N);
            requestPayload.setTobaccoQtyQuestion36C(ZERO);
            requestPayload.setTobaccoTypeQuestion36A(N);
            requestPayload.setSmokingQuantity(ZERO);
            requestPayload.setSmokingFrequency(NA);
            requestPayload.setLiquorQuantity(ZERO);
            requestPayload.setLiquorFrequency(NA);
            requestPayload.setWineQuantity(ZERO);
            requestPayload.setWineFrequency(NA);
            requestPayload.setBeerQuantity(ZERO);
            requestPayload.setBeerFrequency(NA);
            requestPayload.setTobaccoQuantity(ZERO);
            requestPayload.setTobaccoFrequency(NA);

            requestPayload.setWeightChange(N);
            requestPayload.setPrevPolDecPost(N);
            requestPayload.setTag1(ZERO);
            requestPayload.setTag2(NA);
            requestPayload.setTag3(FALSE);
            requestPayload.setTag4(NA);
            requestPayload.setTag5(ZERO);
            requestPayload.setProposerNationality(productRestrictionPayload.getNationality().toUpperCase());
            requestPayload.setPraCity(productRestrictionPayload.getCommunicationCity().toUpperCase());
            requestPayload.setPraPincode(productRestrictionPayload.getCommunicationPinCode());
            requestPayload.setExistingCustomer(NA);
            requestPayload.setProposerOccupation(productRestrictionPayload.getOccupation().toUpperCase());
            requestPayload.setProposerEducation(productRestrictionPayload.getEducation().toUpperCase());
            requestPayload.setBse500(N);
            requestPayload.setSmokerCode(Boolean.TRUE.equals(productRestrictionPayload.isSmoker()) ? Y : N);
            requestPayload.setBankRelationSince(NA);
            requestPayload.setProposerOrganisationType("");
            requestPayload.setRiskScore(ZERO);
            requestPayload.setCovidQuest(NA);
            requestPayload.setIsVaccinated(NA);

            requestPayload.setPosQuestion35(N);
            requestPayload.setPosQuestion35A(N);
            requestPayload.setPosQuestion36(N);
            requestPayload.setPosQuestion36A(N);
            requestPayload.setPosQuestion37(N);
            requestPayload.setPosQuestion37A(N);
            requestPayload.setPosQuestion38(N);
            requestPayload.setPosQuestion38A(N);
            requestPayload.setPosQuestion39(N);
            requestPayload.setPosAlcoholFreq(N);
            requestPayload.setPosAlcoholFreq(N);
            requestPayload.setPosAlcoholQty(ZERO);
            requestPayload.setPosAlcoholType(N);
            requestPayload.setAlcoholType37A(N);
            requestPayload.setPosDrug(N);
            requestPayload.setPosDrugDetails(N);
            requestPayload.setPosTobaccoFreq(N);
            requestPayload.setPosTobaccoQty(ZERO);
            requestPayload.setPosTobaccoType(N);
            requestPayload.setHobbyAsPartOfJob(N);
            requestPayload.setHobbyAsPassion(N);
            requestPayload.setHobbyAsHolidayActivity(N);
            requestPayload.setPraCountry(productRestrictionPayload.getCommunicationCountry().toUpperCase());

            requestPayload.setPolicyAfyp(Objects.isNull(productRestrictionPayload.getPremiumCommitment()) ||
                    productRestrictionPayload.getPremiumCommitment().trim().isEmpty() ? ZERO:productRestrictionPayload.getPremiumCommitment());
            requestPayload.setClientAfyp(ZERO);
            requestPayload.setInsuredIndustry(StringUtils.isEmpty(productRestrictionPayload.getIndustryType()) ? NA : productRestrictionPayload.getIndustryType().toUpperCase());
            ProposalDetails proposalDetails = new ProposalDetails();
            ChannelDetails channelDetails = new ChannelDetails();
            SourcingDetails sourcingDetails = new SourcingDetails();
            sourcingDetails.setPosSeller(productRestrictionPayload.isPosSeller());
            channelDetails.setChannel(productRestrictionPayload.getChannel());
            proposalDetails.setChannelDetails(channelDetails);
            List<ProductDetails> productDetails = new ArrayList<>();
            ProductDetails productDetails1 = new ProductDetails();
            ProductInfo productInfo = new ProductInfo();
            ProductIllustrationResponse productIllustrationResponse = new ProductIllustrationResponse();
            productIllustrationResponse.setCoverageTerm(productRestrictionPayload.getPolicyTerm());
            productIllustrationResponse.setAnnuityPurchasedFrom(productRestrictionPayload.getAnnuityPurchasedFrom());
            productInfo.setProductIllustrationResponse(productIllustrationResponse);
            productInfo.setAnnuityOption(productRestrictionPayload.getAnnuityOption());
            productInfo.setAnnuityType(productRestrictionPayload.getAnnuityType());
            productInfo.setPremiumType(productRestrictionPayload.getPremiumType());
            productInfo.setProductId(productRestrictionPayload.getProductId());
            productInfo.setIsJointLife(productRestrictionPayload.getIsJointLife());
            productInfo.setLimitedTerm(productRestrictionPayload.getLimitedTerm());
            productInfo.setPremiumPaymentTerm(productRestrictionPayload.getPremiumPaymentTerm());
            productInfo.setPolicyTerm(productRestrictionPayload.getPolicyTerm());
            productInfo.setVariant(productRestrictionPayload.getVariant());
            productDetails1.setProductType(Objects.isNull(productRestrictionPayload.getProductType()) || productRestrictionPayload.getProductType().trim().isEmpty()
                    ? TRADITIONAL : productRestrictionPayload.getProductType());
            // swag and swag_par
            if(Utility.or(productRestrictionPayload.getProductId().equals(SWAG),productRestrictionPayload.getProductId().equals(SWAG_PAR),
                    productRestrictionPayload.getProductId().equals(SWP_PRODUCT_ID))) {
                sourcingDetails.setPosSeller(productRestrictionPayload.isPosSeller());
                productInfo.setPcb(productRestrictionPayload.getIsPCB());
                proposalDetails.setSourcingDetails(sourcingDetails);
                requestPayload.setTermPlusRiderSumAssured(Objects.isNull(productRestrictionPayload.getTermPlusAddAmount()) || productRestrictionPayload.getTermPlusAddAmount().trim().isEmpty()
                        ? ZERO:productRestrictionPayload.getTermPlusAddAmount());
            }
            if(productRestrictionPayload.getProductId().equals(SWAG_PAR)) {
                productInfo.setLimitedTerm(productRestrictionPayload.getLimitedTerm());
            }
            productDetails1.setProductInfo(productInfo);
            boolean isJointLife = false;
            if(productRestrictionPayload.getProductId().equals(SSP_PRODUCT_ID)) {
                isJointLife = Utility.isSSPJointLife(productDetails1);
            }
            productDetails.add(productDetails1);
            proposalDetails.setProductDetails(productDetails);
            proposalDetails.setSourcingDetails(sourcingDetails);
            if(productRestrictionPayload!=null && productRestrictionPayload.getProductId()!=null && productRestrictionPayload.getProductId().equals(STPP_PRODUCT_ID)){
                AdditionalFlags additionalFlags = new AdditionalFlags();
                additionalFlags.setIsFalconJourney(FALCON_YES);
                additionalFlags.setIsFalconProduct(FALCON_YES);
                proposalDetails.setAdditionalFlags(additionalFlags);
            }
            long requestedTime = System.currentTimeMillis();
            log.info("request time for planCode fetch {}",requestedTime);
            String planCode = fetchPlanCode(proposalDetails);
            log.info("Total time taken by planCode fetch {}",System.currentTimeMillis()-requestedTime);
            log.info("planCode value for proposalDetails is {}",planCode);
            requestPayload.setProductCode(StringUtils.hasLength(planCode) ? planCode : NA);
            requestPayload.setAcrQuestion(N);
            requestPayload.setNriQuestResidingCountry(StringUtils.hasText(productRestrictionPayload.getCommunicationCountry()) ?
                    productRestrictionPayload.getCommunicationCountry().toUpperCase() : NA);
            requestPayload.setVideoPosv(NA);
            requestPayload.setPcb(YES.equalsIgnoreCase(productRestrictionPayload.getIsPCB())? Y:N);
            proposalFormRequest.setPayload(requestPayload);
            outputRequest.setRequest(proposalFormRequest);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> errorMessages = new ArrayList<>();
            ErrorMessageConfig errorMessageConfig = BeanUtil.getBean(ErrorMessageConfig.class);
            errorMessages.add(errorMessageConfig.getErrorMessages().get("requestCreation"));
            throw new UserHandledException(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  outputRequest;
    }

    private void createReqPayloadForGivenPartyDetails(ProductRestrictionPayload productRestrictionPayload, com.mli.mpro.productRestriction.models.proposalFormRequestModels.Payload requestPayload, final String partyType) {
        java.util.Date date = productRestrictionPayload.getDateOfBirth();
        requestPayload.setAge(Utility.getAge(date));
        requestPayload.setPregnancyComplicationDetails(N);
        requestPayload.setIsPregnant(N);
        requestPayload.setMonthsOfPregnancy(ZERO);
        requestPayload.setPregnancyComplications(N);
        requestPayload.setConvicted(N);
        requestPayload.setFamilyHistoryAdverse(N);
        requestPayload.setInsuredHeight(ZERO);
        requestPayload.setInsuredWeight(ZERO);
        requestPayload.setCompanyType(N);
        requestPayload.setProposerAge(Utility.getAge(date));
        String occupation = productRestrictionPayload.getOccupation();
        String insurerOccupation = productRestrictionPayload.getInsurerOccupation();
        if (insurerOccupation==null || insurerOccupation.trim().isEmpty()) {
            requestPayload.setInsuredOccupation(occupation.toUpperCase());
        } else {
            requestPayload.setInsuredOccupation(insurerOccupation.toUpperCase());
        }

        String gender = genderConverter(
                productRestrictionPayload.getGender());
        if (!gender.isEmpty()) {
            requestPayload.setGender(gender);
        } else {
            requestPayload.setGender(N);
        }

        requestPayload.setExistingInsurance(N);
        requestPayload.setOfferedAtModifiedTerms(N);
        String nationality = productRestrictionPayload.getNationality();
        if (!nationality.isEmpty()) {
            requestPayload.setNationality(nationality.toUpperCase());
        } else {
            requestPayload.setNationality(N);
        }
        requestPayload.setInsuredMaritalStatus(N);
        String education = productRestrictionPayload.getEducation();
        if (!education.isEmpty()) {
            requestPayload.setEducation(education.toUpperCase());
        } else {
            requestPayload.setEducation(N);
        }
        requestPayload.setAvocation(N);
        String countryName = productRestrictionPayload.getPermanentCountry();
        String proposerCRACountryName = N;
        try {
            proposerCRACountryName = productRestrictionPayload.getCommunicationCountry();
            log.info("countryNameInsured {}", proposerCRACountryName);
        } catch (Exception ex) {
            log.error("Exception occured while getting the value of insured country name {}", ex.getMessage());
        }
        if (!countryName.isEmpty()) {
            requestPayload.setCountryName(countryName.toUpperCase());
        } else {
            // FUL2-47427
            if (AppConstants.FORM2.equalsIgnoreCase(productRestrictionPayload.getFormType())) {
                requestPayload.setCountryName(proposerCRACountryName.toUpperCase());
            } else {
                requestPayload.setCountryName(N);
            }
        }
        requestPayload.setPosTravelQuestion(N);
        requestPayload.setPosTravelDetails(N);
        requestPayload.setAirForceRelatedQues1(N);
        requestPayload.setAirForceRelatedQues2(N);
        requestPayload.setAirForceRelatedQues3(N);
        requestPayload.setAirForceRelatedQues4(N);
        requestPayload.setAirForceRelatedQues5(N);

        requestPayload.setArmedForceRelatedQues1(N);
        requestPayload.setArmedForceRelatedQues2(N);
        requestPayload.setArmedForceRelatedQues3(N);
        requestPayload.setArmedForceRelatedQues4(N);

        requestPayload.setDivingRelatedQues1(N);
        requestPayload.setDivingRelatedQues2(N);
        requestPayload.setDivingRelatedQues3(N);
        requestPayload.setDivingRelatedQues4(N);
        requestPayload.setDivingRelatedQues5(N);

        requestPayload.setNavyAndMerchaentRelatedQues1(N);
        requestPayload.setNavyAndMerchaentRelatedQues2(N);
        requestPayload.setNavyAndMerchaentRelatedQues3(N);
        requestPayload.setNavyAndMerchaentRelatedQues4(N);
        requestPayload.setNavyAndMerchaentRelatedQues5(N);

        requestPayload.setMiningRelatedQues1(N);
        requestPayload.setMiningRelatedQues2(N);
        requestPayload.setInsuredIndustry(NA);

        String industry = productRestrictionPayload.getIndustryType();
        if(industry==null || industry.trim().isEmpty()) {
            requestPayload.setInsuredIndustry(NA);
        }
        else {
            requestPayload.setInsuredIndustry(industry.toUpperCase());
        }
        requestPayload.setProposerGender(gender);
        requestPayload.setOilAndNaturalGasRelatedQues1(N);
        requestPayload.setNatureOfJob(NA);
        String pan = productRestrictionPayload.getPanNumber();
        if(pan != null && !pan.trim().isEmpty()) {
                requestPayload.setPanNumber(pan);
            }
        else {
            requestPayload.setPanNumber(NA);
            }
        requestPayload.setParentsInsurance(ZERO);
        requestPayload.setParentsIncome(ZERO);
        requestPayload.setSpouseInsurance(ZERO);
        requestPayload.setSpouseIncome(ZERO);

        requestPayload.setAgentCode(NA);
        requestPayload.setProposerIncome(productRestrictionPayload.getIncome());
        requestPayload.setInsuredsuc(ZERO);
        requestPayload.setFormType(productRestrictionPayload.getFormType());
        requestPayload.setTag1(NA);
        requestPayload.setTag2(NA);
        requestPayload.setTag3(NA);
        requestPayload.setTag4(NA);
        requestPayload.setTag5(NA);
        requestPayload.setCibilScore(ZERO);
        requestPayload.setCibilIncome(ZERO);
        requestPayload.setCustomerClasificationCode(Objects.isNull(productRestrictionPayload.getCustomerClassification()) || productRestrictionPayload.getCustomerClassification().trim().isEmpty()
                ? NA:productRestrictionPayload.getCustomerClassification());
        requestPayload.setAgentLevel(ZERO);
        requestPayload.setCraCity(productRestrictionPayload.getCommunicationCity());
        requestPayload.setAppSignDate(String.valueOf(LocalDate.now()));
        requestPayload.setCraPinCode(Objects.isNull(productRestrictionPayload.getCommunicationPinCode()) || productRestrictionPayload.getCommunicationPinCode().trim().isEmpty()
                ? NA:productRestrictionPayload.getCommunicationPinCode());
        requestPayload.setChannelGoCode(NA);
        requestPayload.setMsa(ZERO);
        requestPayload.setSumAssured(String.valueOf(productRestrictionPayload.getSumAssured()));
        requestPayload.setInsuredsuc(ZERO);
        requestPayload.setBirthMonth(ZERO);
        requestPayload.setBirthDate(ZERO);
        requestPayload.setInsuredIncome(productRestrictionPayload.getIncome());
        requestPayload.setPfMedicalQTag(NA);
    }
    private String genderConverter(String value) {
        String answer = "";
        if ("Male".equalsIgnoreCase(value)) {
            answer = MALE;
        } else if ("Female".equalsIgnoreCase(value)) {
            answer = FEMALE;
        } else if ("Transgender".equalsIgnoreCase(value)) {
            answer = OTHERS;
        }
        return answer;

    }

    public String fetchPlanCode(ProposalDetails proposalDetails) {
        InputRequest inputRequest = new InputRequest();
        RequestData requestData = new RequestData();
        com.mli.mpro.proposal.models.Request request = new com.mli.mpro.proposal.models.Request();
        RequestPayload requestPayload = new RequestPayload();
        HttpHeaders headers = new HttpHeaders();
        headers.add("api_client_secret", apiClientSecret);
        String planCode = NA;
        try {
            requestPayload.setProposalDetails(proposalDetails);
            requestData.setRequestPayload(requestPayload);
            request.setRequestData(requestData);
            inputRequest.setRequest(request);
            HttpEntity<InputRequest> planCodeRequest = new HttpEntity<>(inputRequest,headers);
            log.info("planCode request for BRMS {}", planCodeRequest);
            String planCodeFetchApiUrl = BeanUtil.getBean(ExternalServiceConfig.class).getUrlDetails().get("planCodeFetchApi");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<PlanCodeResponse> outputResponse = restTemplate.postForEntity(planCodeFetchApiUrl, planCodeRequest, PlanCodeResponse.class);
            PlanCodeResponse responseBody = outputResponse.getBody();
            if (responseBody != null && "200".equalsIgnoreCase(responseBody.getResponse().getMsgInfo().getMsgCode())) {
                planCode = nonNull(outputResponse.getBody().getResponse().getPayload().getPlanCode())?outputResponse.getBody().getResponse().getPayload().getPlanCode():outputResponse.getBody().getResponse().getPayload().getPlanCodePOSV();
                log.info("planCode value for TransactionId {} is {}", proposalDetails.getTransactionId(), planCode);
            }
            if (Objects.isNull(planCode) || !org.springframework.util.StringUtils.hasText(planCode) && GLIP_PRODUCT_ID.equalsIgnoreCase(proposalDetails.getProductDetails().get(0).getProductInfo().getProductId())) {
                planCode = GLIPPLANCODE;
            }
        } catch (Exception ex) {
            log.error("error occurred for transactionId {} is {}", proposalDetails.getTransactionId(), Utility.getExceptionAsString(ex));
        }
        return planCode;
    }

    /**
     * Gets the product details.
     *
     * @param proposalDetails the proposal details
     * @param index the index
     * @return the product details
     */
    public static ProductDetails getProductDetails(ProposalDetails proposalDetails,int index) {
        if(proposalDetails==null
                || proposalDetails.getProductDetails()==null
                || proposalDetails.getProductDetails().isEmpty()) {
            return new ProductDetails();
        }
        return proposalDetails.getProductDetails().get(index);
    }

    public static String getPlanCodeBasedOnProductType(ProposalDetails proposalDetails) throws UserHandledException {
        String planCode = "";
        ProductDetails productDetails = getProductDetails(proposalDetails, 0);
        String productType = productDetails.getProductType();
        try {
            if (productType.equalsIgnoreCase(AppConstants.PRODUCT_TYPE_ULIP)) {
                planCode = productDetails.getProductInfo().getPlanCodePOSV();
                planCode = planCode.trim();
            } else {
                planCode = productDetails.getProductInfo().getPlanCode();
                planCode = planCode.trim();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UserHandledException();
        }
        return planCode;
    }

    public static MsgInfo setMsgInfoForGetProposal(String msgCode, String errorMsg) {
        MsgInfo msgInfo;
        if ("200".equalsIgnoreCase(msgCode)) {
            msgInfo = new MsgInfo(DATA_FETCH_SUCCESS, msgCode, DATA_FETCH_SUCCESS);
        } else if ("400".equalsIgnoreCase(msgCode)) {
            msgInfo = new MsgInfo("Bad Request or Required key is missing", msgCode, "Bad Request/ Unauthorized/ Request Timed out");
        } else {
            msgInfo = new MsgInfo(errorMsg, msgCode, "Failure");
        }
        return msgInfo;
    }

    public static MsgInfo setMsgInfoForGetProposal(String msgCode) {
        MsgInfo msgInfo;
        if ("200".equalsIgnoreCase(msgCode)) {
            msgInfo = new MsgInfo(DATA_FETCH_SUCCESS, msgCode, DATA_FETCH_SUCCESS);
        } else if ("400".equalsIgnoreCase(msgCode)) {
            msgInfo = new MsgInfo("Bad Request or Required key is missing", msgCode, "Bad Request/ Unauthorized/ Request Timed out");
        } else {
            msgInfo = new MsgInfo("Data is not fetched successfully", msgCode, "Failure");
        }
        return msgInfo;
    }

}
