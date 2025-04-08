package edu.ijse.strtgst.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class LectureDto {
    private int lecId;
    private String subId;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status; // "upcoming", "ongoing", or "ended"

    public LectureDto() {}

    public LectureDto(int lecId, String subId, Date date, Timestamp startTime, Timestamp endTime, String status) {
        this.lecId = lecId;
        this.subId = subId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getLecId() {
        return lecId;
    }

    public void setLecId(int lecId) {
        this.lecId = lecId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LectureDto{" +
                "lecId=" + lecId +
                ", subId='" + subId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }
}
