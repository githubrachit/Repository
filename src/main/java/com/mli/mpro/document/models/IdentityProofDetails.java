package com.mli.mpro.document.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class IdentityProofDetails {
    
    private boolean form60Flag;
    
    private String proofName;

    @Sensitive(MaskType.ID_PROOF_NUM)
    private String proofNumber;
    
    private String proofExpiryDate;
    
    private String idProofAuthority;

    public boolean isForm60Flag() {
        return form60Flag;
    }

    public void setForm60Flag(boolean form60Flag) {
        this.form60Flag = form60Flag;
    }

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public String getProofNumber() {
        return proofNumber;
    }

    public void setProofNumber(String proofNumber) {
        this.proofNumber = proofNumber;
    }

    public String getProofExpiryDate() {
        return proofExpiryDate;
    }

    public void setProofExpiryDate(String proofExpiryDate) {
        this.proofExpiryDate = proofExpiryDate;
    }

    public String getIdProofAuthority() {
        return idProofAuthority;
    }

    public void setIdProofAuthority(String idProofAuthority) {
        this.idProofAuthority = idProofAuthority;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "IdentityProofDetails{" +
                "form60Flag=" + form60Flag +
                ", proofName='" + proofName + '\'' +
                ", proofNumber='" + proofNumber + '\'' +
                ", proofExpiryDate='" + proofExpiryDate + '\'' +
                ", idProofAuthority='" + idProofAuthority + '\'' +
                '}';
    }
}
