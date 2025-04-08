package edu.ijse.strtgst.dto;

import java.sql.Date;

public class TodoListDto {
    private int todoId;
    private int studId;
    private Date dueDate;
    private String taskName;
    private String taskDescription;
    private String status; // "pending", "completed", or "overdue"

    public TodoListDto() {}

    public TodoListDto(int todoId, int studId, Date dueDate, String taskName, String taskDescription, String status) {
        this.todoId = todoId;
        this.studId = studId;
        this.dueDate = dueDate;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TodoListDto{" +
                "todoId=" + todoId +
                ", studId=" + studId +
                ", dueDate=" + dueDate +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
