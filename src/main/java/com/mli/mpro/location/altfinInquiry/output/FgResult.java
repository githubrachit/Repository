package com.mli.mpro.location.altfinInquiry.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.utils.Utility;

public class FgResult {
    @JsonProperty("dcGradeUnderwritingResult")
    private String dcGradeUnderwritingResult;
    @JsonProperty("dcMirrorReportUnderwritingResult")
    private String dcMirrorReportUnderwritingResult;
    @JsonProperty("dcUnderwritingOutput")
    private String dcUnderwritingOutput;
    @JsonProperty("serviceCall1")
    private String serviceCall1;
    @JsonProperty("serviceCall2")
    private String serviceCall2;
    @JsonProperty("dcHealthScoreUnderwritingResult")
    private String dcHealthScoreUnderwritingResult;
    @JsonProperty("underwritingResult")
    private String underwritingResult;
    @JsonProperty("rafUnderwritingResult")
    private String rafUnderwritingResult;
    @JsonProperty("finWaiverType")
    private String finWaiverType;
    @JsonProperty("reqDoc4")
    private String reqDoc4;
    @JsonProperty("reqDoc3")
    private String reqDoc3;
    @JsonProperty("reqDoc2")
    private String reqDoc2;
    @JsonProperty("reqDoc1")
    private String reqDoc1;

    public String getDcGradeUnderwritingResult() {
        return dcGradeUnderwritingResult;
    }

    public void setDcGradeUnderwritingResult(String dcGradeUnderwritingResult) {
        this.dcGradeUnderwritingResult = dcGradeUnderwritingResult;
    }

    public String getDcMirrorReportUnderwritingResult() {
        return dcMirrorReportUnderwritingResult;
    }

    public void setDcMirrorReportUnderwritingResult(String dcMirrorReportUnderwritingResult) {
        this.dcMirrorReportUnderwritingResult = dcMirrorReportUnderwritingResult;
    }

    public String getDcUnderwritingOutput() {
        return dcUnderwritingOutput;
    }

    public void setDcUnderwritingOutput(String dcUnderwritingOutput) {
        this.dcUnderwritingOutput = dcUnderwritingOutput;
    }

    public String getServiceCall1() {
        return serviceCall1;
    }

    public void setServiceCall1(String serviceCall1) {
        this.serviceCall1 = serviceCall1;
    }

    public String getServiceCall2() {
        return serviceCall2;
    }

    public void setServiceCall2(String serviceCall2) {
        this.serviceCall2 = serviceCall2;
    }

    public String getDcHealthScoreUnderwritingResult() {
        return dcHealthScoreUnderwritingResult;
    }

    public void setDcHealthScoreUnderwritingResult(String dcHealthScoreUnderwritingResult) {
        this.dcHealthScoreUnderwritingResult = dcHealthScoreUnderwritingResult;
    }

    public String getUnderwritingResult() {
        return underwritingResult;
    }

    public void setUnderwritingResult(String underwritingResult) {
        this.underwritingResult = underwritingResult;
    }

    public String getRafUnderwritingResult() {
        return rafUnderwritingResult;
    }

    public void setRafUnderwritingResult(String rafUnderwritingResult) {
        this.rafUnderwritingResult = rafUnderwritingResult;
    }

    public String getFinWaiverType() {
        return finWaiverType;
    }

    public void setFinWaiverType(String finWaiverType) {
        this.finWaiverType = finWaiverType;
    }

    public String getReqDoc4() {
        return reqDoc4;
    }

    public void setReqDoc4(String reqDoc4) {
        this.reqDoc4 = reqDoc4;
    }

    public String getReqDoc3() {
        return reqDoc3;
    }

    public void setReqDoc3(String reqDoc3) {
        this.reqDoc3 = reqDoc3;
    }

    public String getReqDoc2() {
        return reqDoc2;
    }

    public void setReqDoc2(String reqDoc2) {
        this.reqDoc2 = reqDoc2;
    }

    public String getReqDoc1() {
        return reqDoc1;
    }

    public void setReqDoc1(String reqDoc1) {
        this.reqDoc1 = reqDoc1;
    }

    @Override
    public String toString() {
        if (Utility.isCalledFromLogs(Thread.currentThread())) {
            return Utility.toString(this);
        }
        return "FgResult{" +
                "dcGradeUnderwritingResult='" + dcGradeUnderwritingResult + '\'' +
                ", dcMirrorReportUnderwritingResult='" + dcMirrorReportUnderwritingResult + '\'' +
                ", dcUnderwritingOutput='" + dcUnderwritingOutput + '\'' +
                ", serviceCall1='" + serviceCall1 + '\'' +
                ", serviceCall2='" + serviceCall2 + '\'' +
                ", dcHealthScoreUnderwritingResult='" + dcHealthScoreUnderwritingResult + '\'' +
                ", underwritingResult='" + underwritingResult + '\'' +
                ", rafUnderwritingResult='" + rafUnderwritingResult + '\'' +
                ", finWaiverType='" + finWaiverType + '\'' +
                ", reqDoc4='" + reqDoc4 + '\'' +
                ", reqDoc3='" + reqDoc3 + '\'' +
                ", reqDoc2='" + reqDoc2 + '\'' +
                ", reqDoc1='" + reqDoc1 + '\'' +
                '}';
    }
}
