package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.AssignmentDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public boolean deleteAssignment(String assignmentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Assignment WHERE assignment_id = ?", assignmentId);
    }

    public static String fetchExistingID(String subjectName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Subject WHERE sub_name = ?", subjectName);
        return rst.next() ? rst.getString(1) : null;
    }

    public ArrayList<AssignmentDto> getAllCustomer() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Assignment");

        ArrayList<AssignmentDto> assignmentDtos = new ArrayList<>();
        while (rst.next()) {
            AssignmentDto assignmentDto = new AssignmentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5).toLocalDate(),
                    rst.getString(6),
                    rst.getString(7)
            );
            assignmentDtos.add(assignmentDto);
        }

        return assignmentDtos;
    }

}
