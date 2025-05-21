package com.mli.mpro.onboarding.brmsBroker.model;

public class DiyBrmsFieldConfigurationDetails {

    private UTMBasedLogic utmBasedLogic;

    public UTMBasedLogic getUtmBasedLogic() {
        return utmBasedLogic;
    }

    public void setUtmBasedLogic(UTMBasedLogic utmBasedLogic) {
        this.utmBasedLogic = utmBasedLogic;
    }

    @Override
    public String toString() {
        return "DiyBrmsFieldConfigurationDetails{" +
                "utmBasedLogic=" + utmBasedLogic +
                '}';
    }
}
