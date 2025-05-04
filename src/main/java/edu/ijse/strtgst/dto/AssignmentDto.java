package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class AssignmentDto {
    private int assignmentId;
    private String subId;
    private String assignmentName;
    private String assignmentDescription;
    private Date dueDate;
    private String assignmentStatus; // "pending", "completed", or "overdue"
    private int assignmentMarks;
}

