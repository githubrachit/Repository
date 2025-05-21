package com.mli.mpro.location.models;

import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.utils.Utility;

public class LocationTransformationResponsePayload {
    private AddressDetails addressDetails;

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "LocationTransformationResponsePayload{" +
                "addressDetails=" + addressDetails +
                '}';
    }
}