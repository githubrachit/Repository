package com.mli.mpro.proposal.models.irpPsmForNeo;

public class Returns {
    public String yrs1;
    public String yrs3;
    public String yrs5;

    // getters and setters
    public String getYrs1() {
        return yrs1;
    }
    public void setYrs1(String yrs1) {
        this.yrs1 = yrs1;
    }
    public String getYrs3() {
        return yrs3;
    }
    public void setYrs3(String yrs3) {
        this.yrs3 = yrs3;
    }
    public String getYrs5() {
        return yrs5;
    }
    public void setYrs5(String yrs5) {
        this.yrs5 = yrs5;
    }
    //toString
    @Override
    public String toString() {
        return "Returns [yrs1=" + yrs1 + ", yrs3=" + yrs3 + ", yrs5=" + yrs5 + "]";
    }

}
