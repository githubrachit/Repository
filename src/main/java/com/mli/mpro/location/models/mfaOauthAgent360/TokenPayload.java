package com.mli.mpro.location.models.mfaOauthAgent360;

import com.mli.mpro.utils.Utility;

public class TokenPayload {
    private String source;
    private String user;
    private String subject;

    public TokenPayload(String source, String user, String subject) {
        this.source = source;
        this.user = user;
        this.subject = subject;
    }

    public String getSource() {
        return source;
    }

    public String getUser() {
        return user;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
           return Utility.toString(this);
        }
        return "TokenPayload{" +
                "source='" + source + '\'' +
                ", user='" + user + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
