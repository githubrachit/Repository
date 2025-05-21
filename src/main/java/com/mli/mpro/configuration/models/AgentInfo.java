package com.mli.mpro.configuration.models;


import javax.validation.constraints.Pattern;

public class AgentInfo {

    @Pattern(regexp = "^$|^[a-zA-Z0-9][a-zA-Z0-9.' &,\\-()]*$", message = "Invalid State")
    private String state;
    @Pattern(regexp = "^[A-Za-z0-9]{0,10}$", message = "Invalid channelName")
    private String channelName;
    @Pattern(regexp = "^(?!\\s)[A-Za-z0-9 ]{0,20}$", message = "Invalid goCode")
    private String goCode;
    @Pattern(regexp = "(?!\\s)[A-Za-z ]{0,10}$", message = "Invalid role")
    private String role;
    @Pattern(regexp = "^(?!\\s)[A-Za-z0-9_(). \\-]{0,75}$", message = "Invalid goName")
    private String goName;

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getGoCode() {
        return goCode;
    }
    public void setGoCode(String goCode) {
        this.goCode = goCode;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getGoName() {
        return goName;
    }
    public void setGoName(String goName) {
        this.goName = goName;
    }

    @Override
    public String toString() {
        return "AgentInfo [state=" + state + ", channelName=" + channelName + ", goCode=" + goCode + ", role=" + role
                + ", goName=" + goName + "]";
    }
}
