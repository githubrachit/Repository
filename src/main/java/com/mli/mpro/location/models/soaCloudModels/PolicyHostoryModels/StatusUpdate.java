package com.mli.mpro.location.models.soaCloudModels.PolicyHostoryModels;

public class StatusUpdate {
    public String dateAndTime;
    public String gridReceived;
    public String status;
    public String comments;

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getGridReceived() {
        return gridReceived;
    }

    public void setGridReceived(String gridReceived) {
        this.gridReceived = gridReceived;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "StatusUpdate{" +
                "dateAndTime='" + dateAndTime + '\'' +
                ", gridReceived='" + gridReceived + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
