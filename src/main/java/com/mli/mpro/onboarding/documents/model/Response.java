package com.mli.mpro.onboarding.documents.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mli.mpro.onboarding.model.Header;
import com.mli.mpro.onboarding.model.MsgInfo;

public class Response {
        @JsonProperty("msgInfo")
        MsgInfo msginfo;
        @JsonProperty("payload")
        ResponsePayload payload;

        public Response() {

        }

        public Response(MsgInfo msginfo) {
            this.msginfo = msginfo;
        }

        public Response(MsgInfo msginfo, ResponsePayload payload) {
            this.msginfo = msginfo;
            this.payload = payload;
        }

        public MsgInfo getMsginfo() {
            return msginfo;
        }

        public void setMsginfo(MsgInfo msginfo) {
            this.msginfo = msginfo;
        }

        public ResponsePayload getPayload() {
            return payload;
        }

        public void setPayload(ResponsePayload payload) {
            this.payload = payload;
        }

        @Override
        public String toString() {
            return "Response [msginfo=" + msginfo + ", payload=" + payload + "]";
        }
    }