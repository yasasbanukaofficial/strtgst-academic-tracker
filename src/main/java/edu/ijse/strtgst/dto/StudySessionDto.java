package edu.ijse.strtgst.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class StudySessionDto {
    private int ssId;
    private String ssName;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;

    public StudySessionDto() {}

    public StudySessionDto(int ssId, String ssName, Date date, Timestamp startTime, Timestamp endTime) {
        this.ssId = ssId;
        this.ssName = ssName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getSsId() {
        return ssId;
    }

    public void setSsId(int ssId) {
        this.ssId = ssId;
    }

    public String getSsName() {
        return ssName;
    }

    public void setSsName(String ssName) {
        this.ssName = ssName;
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

    @Override
    public String toString() {
        return "StudySessionDto{" +
                "ssId=" + ssId +
                ", ssName='" + ssName + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
