package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.SQLException;

public class StudentModel {
    public boolean addStudent(StudentDto studentDto) throws SQLException{
        return CrudUtil.execute(
                "INSERT INTO Student (username, email, password) VALUES (?,?,?)",
                studentDto.getUsername(),
                studentDto.getEmail(),
                studentDto.getPassword()
        );
    }
}
