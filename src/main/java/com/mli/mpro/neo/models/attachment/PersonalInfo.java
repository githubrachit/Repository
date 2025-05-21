package com.mli.mpro.neo.models.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalInfo {

    @Sensitive(MaskType.FIRST_NAME)
    private String firstName;
    @Sensitive(MaskType.MIDDLE_NAME)
    private String middleName;
    @Sensitive(MaskType.LAST_NAME)
    private String lastName;
    @Sensitive(MaskType.MOBILE)
    private String mobileNumber;
    @Sensitive(MaskType.EMAIL)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public PersonalInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public PersonalInfo setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PersonalInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public PersonalInfo setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PersonalInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "PersonalInfo{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
