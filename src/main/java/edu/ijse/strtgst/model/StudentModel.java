package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentModel {
    public boolean addStudent(StudentDto studentDto) throws SQLException{
        return CrudUtil.execute(
                "INSERT INTO Student (stud_id, username, email, password) VALUES (?,?,?,?)",
                studentDto.getStudId(),
                studentDto.getUsername(),
                studentDto.getEmail(),
                studentDto.getPassword()
        );
    }

    public static StudentDto getStudent(String username) throws SQLException{
        ResultSet rst = CrudUtil.execute("SELECT * FROM Student WHERE username = ?", username);
        if (rst.next()){
             return new StudentDto(
                     rst.getString(1),
                     rst.getString(3),
                     rst.getString(4),
                     rst.getString(5)
             );
        }
        return null;
    }

    public boolean fetchExistingUsername(String username) throws SQLException{
        ResultSet rst = CrudUtil.execute("SELECT username FROM Student WHERE username = ?", username);
        return rst.next();
    }

    public boolean fetchExistingEmail(String email) throws SQLException{
        ResultSet rst = CrudUtil.execute("SELECT email FROM Student WHERE email = ?", email);
        return rst.next();
    }
}
