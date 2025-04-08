package edu.ijse.strtgst.dto;

import java.sql.Date;

public class AssignmentDto {
    private int assignmentId;
    private String subId;
    private String assignmentName;
    private String assignmentDescription;
    private Date dueDate;
    private String assignmentStatus; // "pending", "completed", or "overdue"
    private int assignmentMarks;

    public AssignmentDto() {}

    public AssignmentDto(int assignmentId, String subId, String assignmentName, String assignmentDescription, Date dueDate, String assignmentStatus, int assignmentMarks) {
        this.assignmentId = assignmentId;
        this.subId = subId;
        this.assignmentName = assignmentName;
        this.assignmentDescription = assignmentDescription;
        this.dueDate = dueDate;
        this.assignmentStatus = assignmentStatus;
        this.assignmentMarks = assignmentMarks;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public int getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(int assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }

    @Override
    public String toString() {
        return "AssignmentDto{" +
                "assignmentId=" + assignmentId +
                ", subId='" + subId + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                ", assignmentDescription='" + assignmentDescription + '\'' +
                ", dueDate=" + dueDate +
                ", assignmentStatus='" + assignmentStatus + '\'' +
                ", assignmentMarks=" + assignmentMarks +
                '}';
    }
}

