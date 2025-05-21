package com.mli.mpro.location.models.soaCloudModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClntMsres {
    @JsonProperty("totCustRel")
    private String totCustRel;

    @JsonProperty("totCustRelAct")
    private String totCustRelAct;

    @JsonProperty("totAmtRecCltLev")
    private String totAmtRecCltLev;

    @JsonProperty("activePol")
    private String activePol;

    @JsonProperty("cntOfLapPolicy")
    private String cntOfLapPolicy;

    @JsonProperty("totAmtRecCltLevUno")
    private String totAmtRecCltLevUno;

    @JsonProperty("totCustRelUno")
    private String totCustRelUno;

    @JsonProperty("totCustRelActUno")
    private String totCustRelActUno;

    @JsonProperty("totalAFYP")
    private String totalAFYP;

    public String getTotCustRel() {
        return totCustRel;
    }

    public void setTotCustRel(String totCustRel) {
        this.totCustRel = totCustRel;
    }

    public String getTotCustRelAct() {
        return totCustRelAct;
    }

    public void setTotCustRelAct(String totCustRelAct) {
        this.totCustRelAct = totCustRelAct;
    }

    public String getTotAmtRecCltLev() {
        return totAmtRecCltLev;
    }

    public void setTotAmtRecCltLev(String totAmtRecCltLev) {
        this.totAmtRecCltLev = totAmtRecCltLev;
    }

    public String getActivePol() {
        return activePol;
    }

    public void setActivePol(String activePol) {
        this.activePol = activePol;
    }

    public String getCntOfLapPolicy() {
        return cntOfLapPolicy;
    }

    public void setCntOfLapPolicy(String cntOfLapPolicy) {
        this.cntOfLapPolicy = cntOfLapPolicy;
    }

    public String getTotAmtRecCltLevUno() {
        return totAmtRecCltLevUno;
    }

    public void setTotAmtRecCltLevUno(String totAmtRecCltLevUno) {
        this.totAmtRecCltLevUno = totAmtRecCltLevUno;
    }

    public String getTotCustRelUno() {
        return totCustRelUno;
    }

    public void setTotCustRelUno(String totCustRelUno) {
        this.totCustRelUno = totCustRelUno;
    }

    public String getTotCustRelActUno() {
        return totCustRelActUno;
    }

    public void setTotCustRelActUno(String totCustRelActUno) {
        this.totCustRelActUno = totCustRelActUno;
    }

    public String getTotalAFYP() {
        return totalAFYP;
    }

    public void setTotalAFYP(String totalAFYP) {
        this.totalAFYP = totalAFYP;
    }

    @Override
    public String toString() {
        return "ClientMeasures{" +
                "totCustRel='" + totCustRel + '\'' +
                ", totCustRelAct='" + totCustRelAct + '\'' +
                ", totAmtRecCltLev='" + totAmtRecCltLev + '\'' +
                ", activePol='" + activePol + '\'' +
                ", cntOfLapPolicy='" + cntOfLapPolicy + '\'' +
                ", totAmtRecCltLevUno='" + totAmtRecCltLevUno + '\'' +
                ", totCustRelUno='" + totCustRelUno + '\'' +
                ", totCustRelActUno='" + totCustRelActUno + '\'' +
                ", totalAFYP='" + totalAFYP + '\'' +
                '}';
    }
}
