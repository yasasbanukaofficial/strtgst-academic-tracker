package edu.ijse.strtgst.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class EventDto {
    private int eventId;
    private int studId;
    private String eventName;
    private String eventDescription;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status; // "upcoming", "ongoing", or "ended"

    public EventDto() {}

    public EventDto(int eventId, int studId, String eventName, String eventDescription, Date date, Timestamp startTime, Timestamp endTime, String status) {
        this.eventId = eventId;
        this.studId = studId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
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
        return "EventDto{" +
                "eventId=" + eventId +
                ", studId=" + studId +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }
}
