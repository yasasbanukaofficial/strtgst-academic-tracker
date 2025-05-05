package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.StudentDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentModel {
    public String getNextID() throws SQLException{
        ResultSet rst = CrudUtil.execute("SELECT stud_id FROM Student ORDER BY stud_id DESC LIMIT 1");
        char firstCharacter = 'S';

        if (rst.next()){
            String lastId = rst.getString(1);
            String lastIdNumString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(firstCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return firstCharacter + "001";
    }

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
}
