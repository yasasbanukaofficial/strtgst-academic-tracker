package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.AssignmentDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentModel {
    public boolean addAssignment(AssignmentDto assignmentDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Assignment VALUES (?, ?, ?, ?, ?, ?, ?)",
                assignmentDto.getAssignmentId(),
                assignmentDto.getSubId(),
                assignmentDto.getAssignmentName(),
                assignmentDto.getAssignmentDescription(),
                assignmentDto.getDueDate(),
                assignmentDto.getAssignmentStatus(),
                assignmentDto.getAssignmentMarks()
        );
    }

    public static String fetchExistingID(String subjectName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Subject WHERE sub_name = ?", subjectName);
        return rst.next() ? rst.getString(1) : null;
    }

}
