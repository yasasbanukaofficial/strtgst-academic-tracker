package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskDto {
    private String taskId;
    private String taskName;
    private String taskDescription;
    private LocalDate dueDate;
    private String status; // "pending", "completed", or "overdue"
}
