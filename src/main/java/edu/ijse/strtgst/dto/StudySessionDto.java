package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudySessionDto {
    private int ssId;
    private String ssName;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
}
