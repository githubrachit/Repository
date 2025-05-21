package com.mli.mpro.location.endUser.models;

import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;

public class Data {
    @Sensitive(MaskType.ID_PROOF_NUM)
    private String clientId;
    @Sensitive(MaskType.ID_PROOF_NUM)
    private String clientSecret;
    private String channelName;
    private String defineRole;
    private String location;
    private String appName;
    private String channelType;
    private String managingBranch;
    @Sensitive(MaskType.ID_PROOF_NUM)
    private String servBrId;
    @Sensitive(MaskType.ID_PROOF_NUM)
    private String userId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDefineRole() {
        return defineRole;
    }

    public void setDefineRole(String defineRole) {
        this.defineRole = defineRole;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getManagingBranch() {
        return managingBranch;
    }

    public void setManagingBranch(String managingBranch) {
        this.managingBranch = managingBranch;
    }

    public String getServBrId() {
        return servBrId;
    }

    public void setServBrId(String servBrId) {
        this.servBrId = servBrId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "Data{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", channelName='" + channelName + '\'' +
                ", defineRole='" + defineRole + '\'' +
                ", location='" + location + '\'' +
                ", appName='" + appName + '\'' +
                ", channelType='" + channelType + '\'' +
                ", managingBranch='" + managingBranch + '\'' +
                ", servBrId='" + servBrId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
