package edu.ijse.strtgst.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDto {
    private String subId;
    private int studId;
    private String subName;
    private int totalMarks;
}
