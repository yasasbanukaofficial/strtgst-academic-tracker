package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.AssignmentDto;
import edu.ijse.strtgst.dto.tm.AssignmentTM;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AssignmentModel {
    public boolean addAssignment(AssignmentDto assignmentDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Assignment VALUES (?, ?, ?, ?, ?, ?, ?)",
                assignmentDto.getAssignmentId(),
                assignmentDto.getAssignmentName(),
                assignmentDto.getAssignmentDescription(),
                assignmentDto.getAssignmentMarks(),
                assignmentDto.getSubName(),
                assignmentDto.getDueDate(),
                assignmentDto.getAssignmentStatus()
        );
    }

    public boolean deleteAssignment(String assignmentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Assignment WHERE assignment_id = ?", assignmentId);
    }

    public static String fetchExistingID(String subjectName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Subject WHERE sub_name = ?", subjectName);
        return rst.next() ? rst.getString(1) : null;
    }

    public ArrayList<AssignmentDto> getAllAssignments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Assignment");

        ArrayList<AssignmentDto> assignmentDtos = new ArrayList<>();
        while (rst.next()) {
            AssignmentDto assignmentDto = new AssignmentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6).toLocalDate(),
                    rst.getString(7)
            );
            assignmentDtos.add(assignmentDto);
        }

        return assignmentDtos;
    }

    public boolean editAssignment(AssignmentDto assignmentDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Assignment SET assignment_name = ?, assignment_description = ?, assignment_marks = ?, sub_name = ?, due_date = ?, assignment_status = ? WHERE assignment_id = ?",
                assignmentDto.getAssignmentName(),
                assignmentDto.getAssignmentDescription(),
                assignmentDto.getAssignmentMarks(),
                assignmentDto.getSubName(),
                assignmentDto.getDueDate(),
                assignmentDto.getAssignmentStatus(),
                assignmentDto.getAssignmentId()
        );
    }

    public ArrayList<ArrayList> getAllSubjectStatus() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT assignment_status, due_date, assignment_id FROM Assignment");
        ArrayList<ArrayList> list = new ArrayList<>();

        while (rst.next()) {
            ArrayList<String> row = new ArrayList<>();
            row.add(rst.getString("assignment_status"));
            row.add(rst.getString("due_date"));
            row.add(rst.getString("assignment_id"));
            list.add(row);
        }
        return list;
    }


    public boolean updateAssignmentStatus(String assignmentId, String newStatus) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Assignment SET assignment_status = ? WHERE assignment_id = ?",
                newStatus, assignmentId
        );
    }
}
