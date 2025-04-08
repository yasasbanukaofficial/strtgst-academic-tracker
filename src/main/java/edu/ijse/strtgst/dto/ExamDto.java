package edu.ijse.strtgst.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class ExamDto {
    private int examId;
    private String subId;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String examType; // "written", "practical", or "oral"

    public ExamDto() {}

    public ExamDto(int examId, String subId, Date date, Timestamp startTime, Timestamp endTime, String examType) {
        this.examId = examId;
        this.subId = subId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examType = examType;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
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

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @Override
    public String toString() {
        return "ExamDto{" +
                "examId=" + examId +
                ", subId='" + subId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", examType='" + examType + '\'' +
                '}';
    }
}
