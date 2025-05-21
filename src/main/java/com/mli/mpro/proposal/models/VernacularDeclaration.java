package com.mli.mpro.proposal.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;

public class VernacularDeclaration {
    private String vernacularAccepted;
    @Sensitive(MaskType.FIRST_NAME)
    private String vernacularFirstName;
    @Sensitive(MaskType.LAST_NAME)
    private String vernacularLastName;
    @Sensitive(MaskType.ADDRESS)
    private String vernacularAddress;

    public String getVernacularAccepted() {
        return vernacularAccepted;
    }

    public void setVernacularAccepted(String vernacularAccepted) {
        this.vernacularAccepted = vernacularAccepted;
    }

    public String getVernacularFirstName() {
        return vernacularFirstName;
    }

    public void setVernacularFirstName(String vernacularFirstName) {
        this.vernacularFirstName = vernacularFirstName;
    }

    public String getVernacularLastName() {
        return vernacularLastName;
    }

    public void setVernacularLastName(String vernacularLastName) {
        this.vernacularLastName = vernacularLastName;
    }

    public String getVernacularAddress() {
        return vernacularAddress;
    }

    public void setVernacularAddress(String vernacularAddress) {
        this.vernacularAddress = vernacularAddress;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "VernacularDeclaration{" +
                "vernacularAccepted='" + vernacularAccepted + '\'' +
                ", vernacularFirstName='" + vernacularFirstName + '\'' +
                ", vernacularLastName='" + vernacularLastName + '\'' +
                ", vernacularAddress='" + vernacularAddress + '\'' +
                '}';
    }
}
