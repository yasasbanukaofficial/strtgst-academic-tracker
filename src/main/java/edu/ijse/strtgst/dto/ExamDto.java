package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ExamDto {
    private int examId;
    private String subId;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String examType; // "written", "practical", or "oral"
}
