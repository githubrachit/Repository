package com.mli.mpro.location.altfinInquiry.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.proposal.models.Cs3;
import com.mli.mpro.utils.Utility;

public class AltfinSoaPayload {
    @JsonProperty("cs1")
    private Cs1 cs1;
    @JsonProperty("cs2")
    private Object cs2;
    @JsonProperty("cs3")
    private Cs3 cs3;

    public Cs1 getCs1() {
        return cs1;
    }

    public void setCs1(Cs1 cs1) {
        this.cs1 = cs1;
    }

    public Object getCs2() {
        return cs2;
    }

    public void setCs2(Object cs2) {
        this.cs2 = cs2;
    }

    public Cs3 getCs3() {
        return cs3;
    }

    public void setCs3(Cs3 cs3) {
        this.cs3 = cs3;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "AltfinSoaPayload{" +
                "cs1=" + cs1 +
                ", cs2=" + cs2 +
                ", cs3=" + cs3 +
                '}';
    }
}
