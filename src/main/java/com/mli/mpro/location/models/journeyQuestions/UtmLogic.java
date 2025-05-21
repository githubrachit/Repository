package com.mli.mpro.location.models.journeyQuestions;

import com.mli.mpro.productRestriction.util.AppConstants;

import java.util.List;

public class UtmLogic {
    private String productId;
    private String jointLife;
    private boolean isHPNonCip;
    private boolean isSspSwissReCase;
    public UtmLogic() {
    }

    public UtmLogic(String productId, String jointLife, boolean isHPNonCip, boolean isSspSwissReCase) {
        this.productId = productId;
        this.jointLife = jointLife;
        this.isHPNonCip = isHPNonCip;
        this.isSspSwissReCase = isSspSwissReCase;
    }
    public String getProductId() {
        return productId;
    }

    public String getJointLife() {
        return jointLife;
    }


    public static boolean validateUtmLogic(UtmLogic utmLogic, String questionId) {
        String sspProduct = AppConstants.SSP_PRODUCT_ID;
        String step = AppConstants.STEP;
        String sewa = AppConstants.SEWA;
        String stpp = AppConstants.STPP_PRODUCT_ID;
        if (anyMatches(questionId, "H19", "H20")) {
            return validationForH19H20(utmLogic, sspProduct, step, sewa, stpp);
        } else if (anyMatches(questionId, "H12A", "H12B")) {
            return validationForH12AH12B(utmLogic, sspProduct, step, sewa, stpp);
        } else if (anyMatches(questionId, "H14", "H15")) {
            return validationForH14H15(utmLogic, sspProduct, step, stpp);
        } else if (anyMatches(questionId, "H38", "H39", "H36A", "H36B")) {
            return validationForH38H39H36AH36B(utmLogic);
        } else if (anyMatches(questionId, "H42", "H43")) {
            return validationForH42H43(utmLogic);
        } else {
            return true;
        }

    }

    private static boolean validationForH12AH12B(UtmLogic utmLogic, String sspProduct, String step, String sewa, String stpp) {
        if (AppConstants.YES.equalsIgnoreCase(utmLogic.getJointLife().toUpperCase()) && sspProduct.equalsIgnoreCase(utmLogic.getProductId()) && !utmLogic.isHPNonCip() && utmLogic.isSspSwissReCase()) {
            return false;
        } else if (utmLogic.isHPNonCip() &&
                (((sspProduct.equalsIgnoreCase(utmLogic.getProductId()) || step.equalsIgnoreCase(utmLogic.getProductId()) || stpp.equalsIgnoreCase(utmLogic.getProductId()))  && utmLogic.isSspSwissReCase())
                        || sewa.equalsIgnoreCase(utmLogic.getProductId()))
                ) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean validationForH14H15(UtmLogic utmLogic, String sspProduct, String step, String stpp) {
        return !((sspProduct.equalsIgnoreCase(utmLogic.getProductId()) && AppConstants.YES.equalsIgnoreCase(utmLogic.getJointLife().toUpperCase()) && utmLogic.isSspSwissReCase())
                || ((sspProduct.equalsIgnoreCase(utmLogic.getProductId()) || step.equalsIgnoreCase(utmLogic.getProductId()) || stpp.equalsIgnoreCase(utmLogic.getProductId())) && utmLogic.isSspSwissReCase()));
    }

    private static boolean validationForH38H39H36AH36B(UtmLogic utmLogic) {
        return !(AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(utmLogic.getProductId()) && AppConstants.YES.equalsIgnoreCase(utmLogic.getJointLife().toUpperCase()) && utmLogic.isSspSwissReCase());
    }

    private static boolean validationForH42H43(UtmLogic utmLogic) {
        return (AppConstants.SSP_PRODUCT_ID.equalsIgnoreCase(utmLogic.getProductId()) && AppConstants.YES.equalsIgnoreCase(utmLogic.getJointLife().toUpperCase()) && utmLogic.isSspSwissReCase());
    }

    private static boolean validationForH19H20(UtmLogic utmLogic, String sspProduct, String step, String sewa, String stpp) {
        if (AppConstants.YES.equalsIgnoreCase(utmLogic.getJointLife().toUpperCase()) && sspProduct.equalsIgnoreCase(utmLogic.getProductId()) && !utmLogic.isHPNonCip() && utmLogic.isSspSwissReCase()) {
            return true;
        } else if (utmLogic.isHPNonCip() &&
                (
                        ((sspProduct.equalsIgnoreCase(utmLogic.getProductId()) || step.equalsIgnoreCase(utmLogic.getProductId()) || stpp.equalsIgnoreCase(utmLogic.getProductId())) && utmLogic.isSspSwissReCase())
                        || sewa.equalsIgnoreCase(utmLogic.getProductId()) 
                )
                ) {

            return true;
        } else {
            return false;
        }
    }

    private static boolean anyMatches(String questionId, String... questionIdFromDB){
        List<String> list = List.of(questionIdFromDB);
        return list.contains(questionId);
    }

    public boolean isHPNonCip() {
        return isHPNonCip;
    }

    public boolean isSspSwissReCase() {
        return isSspSwissReCase;
    }

    @Override
    public String toString() {
        return "UtmLogic{" +
                "productId='" + productId + '\'' +
                ", jointLife='" + jointLife + '\'' +
                ", isHPNonCip=" + isHPNonCip +
                ", isSspSwissReCase='" + isSspSwissReCase + '\'' +
                '}';
    }
}
