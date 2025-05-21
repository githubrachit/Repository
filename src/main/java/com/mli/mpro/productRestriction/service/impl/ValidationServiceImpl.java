package com.mli.mpro.productRestriction.service.impl;

import com.mli.mpro.productRestriction.exception.CustomErrorMessage;
import com.mli.mpro.productRestriction.models.InputRequest;
import com.mli.mpro.productRestriction.models.common.ErrorResponse;
import com.mli.mpro.productRestriction.service.ValidationService;
import com.mli.mpro.productRestriction.util.AppConstants;
import com.mli.mpro.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private CustomErrorMessage customErrorMessage;
    @Override
    public void validateRequest(InputRequest inputRequest, Set<ErrorResponse> errors) {

        String pan = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPanNumber();
        String premiumPaymentTerm = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPremiumPaymentTerm();
        String policyTerm = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getPolicyTerm();
        String currentCIRiderSumAssured = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getCurrentCIRiderSumAssured();
        String currentACIRiderSumAssured = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getCurrentACIRiderSumAssured();
        String currentACORiderSumAssured = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getCurrentACORiderSumAssured();
        String termPlusAmount = inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getTermPlusAddAmount();

        if(!(validatePincode(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getCommunicationPinCode(),
                inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getCommunicationCountry()))) {
            setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.COMM_PINCODE_PATH),
                    customErrorMessage.getCustomErrorMessageList().get(AppConstants.COMM_PINCODE_PATH),errors);
        }
        if(!(validateAge(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getDateOfBirth()))){
            setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.DOB_PATH),
                    customErrorMessage.getCustomErrorMessageList().get(AppConstants.DOB_PATH),errors);
        }
        if(pan != null && !pan.trim().isEmpty()) {
            if (!(validatePan(pan))) {
                setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.PANNUMBER_PATH),
                        customErrorMessage.getCustomErrorMessageList().get(AppConstants.PANNUMBER_PATH), errors);
            }
        }
        if(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().isCIRider()) {

            validatePremiumAndPolicy(premiumPaymentTerm,policyTerm,errors);
            if(currentCIRiderSumAssured==null || currentCIRiderSumAssured.trim().isEmpty()) {
                setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.CI_RIDER_SUM_PATH),
                        customErrorMessage.getCustomErrorMessageList().get(AppConstants.CI_RIDER_SUM_PATH), errors);
            }
        }
        if(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().isACIRider()) {
            if(currentACIRiderSumAssured==null || currentACIRiderSumAssured.trim().isEmpty()) {
                setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.ACI_RIDER_SUM_PATH),
                        customErrorMessage.getCustomErrorMessageList().get(AppConstants.ACI_RIDER_SUM_PATH), errors);
            }
        }
        if(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().isACORider()) {
            if(currentACORiderSumAssured==null || currentACORiderSumAssured.trim().isEmpty()) {
                setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.ACO_RIDER_SUM_PATH),
                        customErrorMessage.getCustomErrorMessageList().get(AppConstants.ACO_RIDER_SUM_PATH), errors);
            }
        }
        if(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().isTermPlusRider()) {
            if(termPlusAmount==null || termPlusAmount.trim().isEmpty()) {
                setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.TERMPLUS_RIDER_SUM_PATH),
                        customErrorMessage.getCustomErrorMessageList().get(AppConstants.TERMPLUS_RIDER_SUM_PATH), errors);
            }
        }
        if(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getProductId().equals(AppConstants.SWAG)) {
            if(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().isPosSeller()) {
                if(!AppConstants.SWAG_POS_VARIANT_LIST.contains(inputRequest.getRequest().getRequestData().getRequestPayload().getProductRestrictionPayload().getVariant())) {
                    setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.VARIANT),
                            customErrorMessage.getCustomErrorMessageList().get(AppConstants.VARIANT), errors);
                }
            }
        }
    }

    private void validatePremiumAndPolicy(String premiumPaymentTerm , String policyTerm, Set<ErrorResponse> errors) {

        if(policyTerm==null || policyTerm.trim().isEmpty()) {
            setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.PT_PATH),
                    customErrorMessage.getCustomErrorMessageList().get(AppConstants.PT_PATH), errors);
        }
        if(premiumPaymentTerm==null || premiumPaymentTerm.trim().isEmpty()) {
            setErrorToContext(customErrorMessage.getCustomErrorCodeList().get(AppConstants.PPT_PATH),
                    customErrorMessage.getCustomErrorMessageList().get(AppConstants.PPT_PATH), errors);
        }
    }
    private boolean validatePincode(String pinCode, String country) {
        if (AppConstants.INDIA_COUNTRY.equalsIgnoreCase(country)){
            boolean pincodeRegex= Pattern.matches(AppConstants.INDIA_PINCODE_REGEX, pinCode);
            return pincodeRegex ;
        }
        else
            return Pattern.matches(AppConstants.NON_INDIA_PINCODE_REGEX, pinCode);
    }
    private boolean validateAge(Date dob) {
        int age = Integer.parseInt(Utility.getAge(dob));
        if(age < 18 || age > 65) {
            return false;
        }
        return true;
    }
    private boolean validatePan(String pan) {
            return Pattern.matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$", pan);
    }
    private void setErrorToContext(String errorCode, String errorMessage, Set<ErrorResponse> errors) {
        errors.add(new ErrorResponse(errorCode, errorMessage));
    }
}
