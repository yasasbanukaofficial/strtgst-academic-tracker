package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TodoListDto {
    private int todoId;
    private int studId;
    private Date dueDate;
    private String taskName;
    private String taskDescription;
    private String status; // "pending", "completed", or "overdue"
}
