
package com.mli.mpro.onboarding.superApp.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mli.mpro.onboarding.util.ApplicationUtils;
import com.mli.mpro.utils.Utility;

import java.util.Arrays;
import java.util.Date;

import static com.mli.mpro.productRestriction.util.AppConstants.LOG_TYPE_LIST;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPayload {

    private String agentId;
    private String dashboardType;
    private Date startDate;
    private Date endDate;
    private String searchType;
    private String searchKey;
    private String journeyStage;
    private int pageNumber;
    private String goCACode;
    private String requestSource;
    private String agentRole;
    private String isOnboardedProduct;
    private String isDashboardCountRequired;

    public String getIsDashboardCountRequired() {
        return isDashboardCountRequired;
    }

    public void setIsDashboardCountRequired(String isDashboardCountRequired) {
        this.isDashboardCountRequired = isDashboardCountRequired;
    }

    public RequestPayload() {
        // no-args constructor
    }

    public RequestPayload(String agentId, String dashboardType, Date startDate, Date endDate, String searchKey, String goCACode, String requestSource, String agentRole) {
        this.agentId = agentId;
        this.dashboardType = dashboardType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.searchKey = searchKey;
        this.goCACode = goCACode;
        this.requestSource = requestSource;
        this.agentRole = agentRole;
    }

    public String getAgentId() {
	return agentId;
    }

    public void setAgentId(String agentId) {
	this.agentId = agentId;
    }

    public String getDashboardType() {
	return dashboardType;
    }

    public void setDashboardType(String dashboardType) {
	this.dashboardType = dashboardType;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public String getSearchType() {
	return searchType;
    }

    public void setSearchType(String searchType) {
	this.searchType = searchType;
    }

    public String getJourneyStage() {
	return journeyStage;
    }

    public String getSearchKey() {
	return searchKey;
    }

    public void setSearchKey(String searchKey) {
	this.searchKey = searchKey;
    }

    public void setJourneyStage(String journeyStage) {
	this.journeyStage = journeyStage;
    }

    public int getPageNumber() {
	return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
	this.pageNumber = pageNumber;
    }

    public String getGoCACode() {
        return goCACode;
    }

    public void setGoCACode(String goCACode) {
        this.goCACode = goCACode;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getAgentRole() {
        return agentRole;
    }

    public void setAgentRole(String agentRole) {
        this.agentRole = agentRole;
    }

  public String getIsOnboardedProduct() {
    return isOnboardedProduct;
  }

  public void setIsOnboardedProduct(String isOnboardedProduct) {
    this.isOnboardedProduct = isOnboardedProduct;
  }

  @Override
    public String toString() {
           if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
        return "RequestPayload{" +
                "agentId='" + agentId + '\'' +
                ", dashboardType='" + dashboardType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", searchType='" + searchType + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", journeyStage='" + journeyStage + '\'' +
                ", pageNumber=" + pageNumber +
                ", goCACode='" + goCACode + '\'' +
                ", requestSource='" + requestSource + '\'' +
                ", agentRole='" + agentRole + '\'' +
                '}';
    }
}