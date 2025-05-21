package com.mli.mpro.neo.models.attachment;

import com.mli.mpro.utils.Utility;

import java.util.List;
import java.util.StringJoiner;

public class Response {

    private List<Object> messages;

    public Response() {
    }

    public Response(List<Object> messages) {
        this.messages = messages;
    }

    public List<Object> getMessages() {
        return messages;
    }

    public Response setMessages(List<Object> messages) {
        this.messages = messages;
        return this;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("messages = " + messages)
                .toString();
    }
}
