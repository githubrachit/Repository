package com.mli.mpro.location.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.annotation.Sensitive;
import com.mli.mpro.utils.MaskType;
import com.mli.mpro.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blockedAgents")
public class BlockedAgentData {

    @JsonProperty("blockedAgentId")
    private String blockedAgentId;
    @JsonProperty("vertical")
    private String vertical;
    @Sensitive(MaskType.NAME)
    @JsonProperty("agentName")
    private String agentName;
    @JsonProperty("roleCode")
    private String roleCode;

    public String getBlockedAgentId() {
        return blockedAgentId;
    }

    public void setBlockedAgentId(String blockedAgentId) {
        this.blockedAgentId = blockedAgentId;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "BlockedAgentData{" +
                "blockedAgentId='" + blockedAgentId + '\'' +
                ", vertical='" + vertical + '\'' +
                ", agentName='" + agentName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }
}
