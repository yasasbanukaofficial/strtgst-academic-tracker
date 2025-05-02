package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LectureDto {
    private int lecId;
    private String subId;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status; // "upcoming", "ongoing", or "ended"
}
