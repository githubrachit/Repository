package com.mli.mpro.onboarding.medicalGridDetials.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.model.MsgInfo;

public class Response {
        @JsonProperty("msgInfo")
        MsgInfo msginfo;
        @JsonProperty("payload")
        com.mli.mpro.onboarding.medicalGridDetials.model.ResponsePayload payload;

        public Response() {

        }

        public Response(MsgInfo msginfo) {
            this.msginfo = msginfo;
        }

        public Response(MsgInfo msginfo, com.mli.mpro.onboarding.medicalGridDetials.model.ResponsePayload payload) {
            this.msginfo = msginfo;
            this.payload = payload;
        }

        public MsgInfo getMsginfo() {
            return msginfo;
        }

        public void setMsginfo(MsgInfo msginfo) {
            this.msginfo = msginfo;
        }

        public com.mli.mpro.onboarding.medicalGridDetials.model.ResponsePayload getPayload() {
            return payload;
        }

        public void setPayload(com.mli.mpro.onboarding.medicalGridDetials.model.ResponsePayload payload) {
            this.payload = payload;
        }

        @Override
        public String toString() {
            return "Response [msginfo=" + msginfo + ", payload=" + payload + "]";
        }
    }