package com.mli.mpro.onboarding.partner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class FatherName {

    @Sensitive(MaskType.FIRST_NAME)
    @JsonProperty("fatherFirstName")
    private String fatherFirstName;

    @Sensitive(MaskType.MIDDLE_NAME)
    @JsonProperty("fatherMiddleName")
    private String fatherMiddleName;

    @Sensitive(MaskType.LAST_NAME)
    @JsonProperty("fatherLastName")
    private String fatherLastName;

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "FatherName{" +
                "fatherFirstName='" + fatherFirstName + '\'' +
                ", fatherMiddleName='" + fatherMiddleName + '\'' +
                ", fatherLastName='" + fatherLastName + '\'' +
                '}';
    }
}
