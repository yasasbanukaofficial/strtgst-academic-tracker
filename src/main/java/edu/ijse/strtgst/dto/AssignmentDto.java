package edu.ijse.strtgst.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class AssignmentDto {
    private String assignmentId;
    private String subId;
    private String assignmentName;
    private String assignmentDescription;
    private LocalDate dueDate;
    private String assignmentStatus;
    private String assignmentMarks;
}

