package com.mli.mpro.onboarding.medicalGridDetials.model;


import com.mli.mpro.proposal.models.MedicalGridDetails;

public class ResponsePayload {

    private MedicalGridDetails medicalGridDetails;

    public ResponsePayload() {
        super();
    }
    public ResponsePayload(MedicalGridDetails medicalGridDetails) {
        super();
        this.medicalGridDetails = medicalGridDetails;
    }
    public MedicalGridDetails getMedicalGridDetails() {
        return medicalGridDetails;
    }
    public void setMedicalGridDetails(MedicalGridDetails medicalGridDetails) {
        this.medicalGridDetails = medicalGridDetails;
    }
    @Override
    public String toString() {
        return "ResponsePayload{" +
                "medicalGridDetails=" + medicalGridDetails +
                '}';
    }

}
