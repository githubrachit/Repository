package com.mli.mpro.neo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.utils.Utility;

import java.util.StringJoiner;

/**
 * The type Header.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Header {

    private String soaMsgVersion;
    private String soaCorrelationId;
    private String soaAppId;
    private String soaUserId;
    private String soaPassword;

    /**
     * No args constructor for use in serialization
     */
    public Header() {
    }

    /**
     * Instantiates a new Header.
     *
     * @param soaCorrelationId the soa correlation id
     * @param soaAppId         the soa app id
     */
    public Header(String soaCorrelationId, String soaAppId) {
        super();
        this.soaCorrelationId = soaCorrelationId;
        this.soaAppId = soaAppId;
    }

    /**
     * Gets soa msg version.
     *
     * @return the soa msg version
     */
    public String getSoaMsgVersion() {
        return soaMsgVersion;
    }

    /**
     * Sets soa msg version.
     *
     * @param soaMsgVersion the soa msg version
     */
    public void setSoaMsgVersion(String soaMsgVersion) {
        this.soaMsgVersion = soaMsgVersion;
    }

    /**
     * Gets soa correlation id.
     *
     * @return the soa correlation id
     */
    public String getSoaCorrelationId() {
        return soaCorrelationId;
    }

    /**
     * Sets soa correlation id.
     *
     * @param soaCorrelationId the soa correlation id
     */
    public void setSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
    }

    /**
     * With soa correlation id header.
     *
     * @param soaCorrelationId the soa correlation id
     * @return the header
     */
    public Header withSoaCorrelationId(String soaCorrelationId) {
        this.soaCorrelationId = soaCorrelationId;
        return this;
    }

    /**
     * Gets soa app id.
     *
     * @return the soa app id
     */
    public String getSoaAppId() {
        return soaAppId;
    }

    /**
     * Sets soa app id.
     *
     * @param soaAppId the soa app id
     */
    public void setSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
    }

    /**
     * With soa app id header.
     *
     * @param soaAppId the soa app id
     * @return the header
     */
    public Header withSoaAppId(String soaAppId) {
        this.soaAppId = soaAppId;
        return this;
    }

    /**
     * Gets soa user id.
     *
     * @return the soa user id
     */
    public String getSoaUserId() {
        return soaUserId;
    }

    /**
     * Sets soa user id.
     *
     * @param soaUserId the soa user id
     */
    public void setSoaUserId(String soaUserId) {
        this.soaUserId = soaUserId;
    }

    /**
     * Gets soa password.
     *
     * @return the soa password
     */
    public String getSoaPassword() {
        return soaPassword;
    }

    /**
     * Sets soa password.
     *
     * @param soaPassword the soa password
     */
    public void setSoaPassword(String soaPassword) {
        this.soaPassword = soaPassword;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("soaAppId = " + soaAppId)
                .add("soaCorrelationId = " + soaCorrelationId)
                .add("soaMsgVersion = " + soaMsgVersion)
                .add("soaPassword = " + soaPassword)
                .add("soaUserId = " + soaUserId)
                .toString();
    }
}
