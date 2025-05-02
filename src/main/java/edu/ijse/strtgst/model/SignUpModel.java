package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.SQLException;

public class SignUpModel {
    public static boolean addStudent(StudentDto studentDto) throws SQLException{
        return CrudUtil.execute(
                "INSERT INTO Student (stud_id, stud_name, username, email, password, profile_picture, date_of_birth) VALUES (?,?,?,?,?,?,?,?,?)",
                studentDto.getStudId(),
                studentDto.getStudName(),
                studentDto.getUsername(),
                studentDto.getEmail(),
                studentDto.getPassword(),
                studentDto.getProfilePicture(),
                studentDto.getDateOfBirth()
        );
    }
}
