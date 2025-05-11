package edu.ijse.strtgst.dto.tm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AssignmentTM {
    private String assignmentName;
    private LocalDate assignmentDueDate;
    private String assignmentStatus;
    private String assignmentMarks;
}
