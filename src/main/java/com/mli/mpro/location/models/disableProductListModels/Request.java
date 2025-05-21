package com.mli.mpro.location.models.disableProductListModels;

public class Request {
    private String goCode;
    private String channel;
    private boolean isPosSeller;
    private boolean isCATAxis;
    private boolean isPhysicalJourney;

    public String getGoCode() {
        return goCode;
    }

    public void setGoCode(String goCode) {
        this.goCode = goCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isPosSeller() {
        return isPosSeller;
    }

    public void setIsPosSeller(boolean isPosSeller) {
        this.isPosSeller = isPosSeller;
    }

    public boolean isCATAxis() {
        return isCATAxis;
    }

    public void setIsCATAxis(boolean isCATAxis) {
        this.isCATAxis = isCATAxis;
    }

    public boolean isPhysicalJourney() {
        return isPhysicalJourney;
    }

    public void setIsPhysicalJourney(boolean isPhysicalJourney) {
        this.isPhysicalJourney = isPhysicalJourney;
    }

    @Override
    public String toString() {
        return "Request{" +
                "goCode='" + goCode + '\'' +
                ", channel='" + channel + '\'' +
                ", isPosSeller=" + isPosSeller +
                ", isCATAxis=" + isCATAxis +
                ", isPhysicalJourney=" + isPhysicalJourney +
                '}';
    }
}