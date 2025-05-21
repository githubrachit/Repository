package com.mli.mpro.onboarding.superApp.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgRespInfo {
        private String msgCode;
        private String msg;
        private String msgDescription;
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }



        @JsonProperty("errors")
        private List<Object> errors;

        public List<Object> getErrors() {
            return errors;
        }

        public void setErrors(List<Object> errors) {
            this.errors = errors;
        }

        public MsgRespInfo(String msgCode, String msg, String msgDescription, List<Object> errors) {
            this.msgCode = msgCode;
            this.msg = msg;
            this.msgDescription = msgDescription;
            this.errors = errors;
        }

        public MsgRespInfo() {

        }

        public MsgRespInfo(String msgCode, String msg, String msgDescription) {
            this.msgCode = msgCode;
            this.msg = msg;
            this.msgDescription = msgDescription;
        }
        public String getMsgCode() {
            return msgCode;
        }
        public void setMsgCode(String msgCode) {
            this.msgCode = msgCode;
        }
        public String getMsg() {
            return msg;
        }
        public void setMsg(String msg) {
            this.msg = msg;
        }
        public String getMsgDescription() {
            return msgDescription;
        }
        public void setMsgDescription(String msgDescription) {
            this.msgDescription = msgDescription;
        }

        @Override
        public String toString() {
            return "MsgInfo [msgCode=" + msgCode + ", msg=" + msg + ", msgDescription=" + msgDescription + "]";
        }

}
