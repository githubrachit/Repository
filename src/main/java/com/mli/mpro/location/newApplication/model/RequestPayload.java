package com.mli.mpro.location.newApplication.model;

public class RequestPayload {

        private String channel;
        private String roleDesignation;
        private String issuedPolicCount;
        private String tenure;

    public RequestPayload() {
    }

    public RequestPayload(String channel, String roleDesignation, String issuedPolicCount, String tenure) {
        this.channel = channel;
        this.roleDesignation = roleDesignation;
        this.issuedPolicCount = issuedPolicCount;
        this.tenure = tenure;
    }

    public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getRoleDesignation() {
            return roleDesignation;
        }

        public void setRoleDesignation(String roleDesignation) {
            this.roleDesignation = roleDesignation;
        }

        public String getIssuedPolicCount() {
            return issuedPolicCount;
        }

        public void setIssuedPolicCount(String issuedPolicCount) {
            this.issuedPolicCount = issuedPolicCount;
        }

        public String getTenure() {
            return tenure;
        }

        public void setTenure(String tenure) {
            this.tenure = tenure;
        }

        @Override
        public String toString() {
            return "Payload{" +
                    "channel='" + channel + '\'' +
                    ", roleDesignation='" + roleDesignation + '\'' +
                    ", issuedPolicCount='" + issuedPolicCount + '\'' +
                    ", tenure='" + tenure + '\'' +
                    '}';
        }
    }


