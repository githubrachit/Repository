package com.mli.mpro.neo.models.enums;

import java.util.HashMap;
import java.util.Map;

public enum JourneyStatusCode {

    NEW_LEAD("01"),
    EQUOTE_GENERATED("02"),
    QUICK_ELIGIBILITY("03"),
    INTERCEPT_1("04"),
    INTERCEPT_2("05"),
    PRE_PAYMENT("06"),
    PAYMENT_SUCCESS("07"),
    PAYMENT_TRUNCATED("08"),
    PAYMENT_FAILURE("09"),
    PAYMENT_CONFIRMATION("10"),
    PERSONAL_DETAILS("11"),
    EMPLOYMENT_DETAILS("12"),
    NOMINEE_DETAILS("13"),
    CKYC_DETAILS("14"),
    LIFESTYLE_DETAILS("15"),
    PAYOR_DETAILS("16"),
    BANK_DETAILS("17"),
    REVIEW_DETAILS("18"),
    IRP_DETAILS("19"),
    FUND_SELECTION("20"),
    OTP_AUTHENTICATION("21"),
    SCHEDULE_MEDICAL("22"),
    DOCUMENT_UPLOAD("23"),
    PROPOSAL("24"),
    POLICY_LOGIN("25");

    static Map<String, JourneyStatusCode> journeyStatusCodeMap = new HashMap<>();

    static {
        for (JourneyStatusCode journeyStatusCode : JourneyStatusCode.values()) {
            journeyStatusCodeMap.put(journeyStatusCode.code, journeyStatusCode);
        }
    }

    private String code;

    JourneyStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static JourneyStatusCode findByCode(String code) {
        return journeyStatusCodeMap.get(code);
    }
}
