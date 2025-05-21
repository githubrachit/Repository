package com.mli.mpro.common.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class YblReplacementSaleRequestPayload {
   @Sensitive(MaskType.PAN_NUM)
    private String panNumber;
    private String transactionId;
    private String channel;
    private boolean isYblTelesalesCase;
    private String formType;

    public String getPanNumber() {
        return panNumber;
    }


    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean getIsYblTelesalesCase() {
        return isYblTelesalesCase;
    }

    public void setIsYblTelesalesCase(boolean isYblTelesalesCase) {
        this.isYblTelesalesCase = isYblTelesalesCase;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "YblReplacementSaleRequest{" +
                "panNumber='" + panNumber + '\'' +
                "transactionId='" + transactionId + '\'' +
                "channel='" + channel + '\'' +
                "isYblTelesalesCase='" + isYblTelesalesCase + '\'' +
                "formType='" + formType + '\'' +
                '}';
    }
}
