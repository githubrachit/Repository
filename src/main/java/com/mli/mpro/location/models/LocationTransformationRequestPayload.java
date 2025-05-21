
package com.mli.mpro.location.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.proposal.models.AddressDetails;
import com.mli.mpro.utils.Utility;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationTransformationRequestPayload {
    AddressDetails addressDetails;

    /**
     * No args constructor for use in serialization
     */
    public LocationTransformationRequestPayload() {
    }

    /**
     * @param addressDetails
     */
    public LocationTransformationRequestPayload(AddressDetails addressDetails) {
        super();
        this.addressDetails = addressDetails;
    }

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
        return "LocationTransformationRequestPayload{" +
                "addressDetails=" + addressDetails +
                '}';
    }
}
