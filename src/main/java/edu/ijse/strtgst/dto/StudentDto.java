package edu.ijse.strtgst.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentDto {
    private String studId;
    private String studName;
    private String username;
    private String email;
    private String password;
    private byte[] profilePicture;
    private Date dateOfBirth;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public StudentDto(String studId, String username, String email, String password) {
        this.studId = studId;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
