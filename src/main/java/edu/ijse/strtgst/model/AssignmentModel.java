package edu.ijse.strtgst.model;

import edu.ijse.strtgst.db.DBConnection;
import edu.ijse.strtgst.dto.AssignmentDto;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.CrudUtil;
import edu.ijse.strtgst.util.IdLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AssignmentModel {
    private boolean saveAssignment(AssignmentDto assignmentDto) throws SQLException {
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

    public boolean addAssignment(AssignmentDto assignmentDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isSaved = saveAssignment(assignmentDto);
            if (isSaved){
                String subId = fetchExistingID(assignmentDto.getSubName());
                if (subId == null){
                    AlertUtil.setErrorAlert("Assignment not saved");
                }
                boolean subMarksUpdated = updateSubMarks(subId, assignmentDto.getAssignmentMarks());
                if (subMarksUpdated){
                    boolean gradeMarksUpdate = addGradeMarks(subId, assignmentDto.getAssignmentMarks());
                    if (gradeMarksUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e){
            AlertUtil.setErrorAlert("Error when adding an assignment");
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
            return true;
        }
    }

    private boolean addGradeMarks(String subId, String assignmentMarks) throws SQLException {
        int marks = Integer.parseInt(assignmentMarks);
        String grade = (marks >= 75) ? "A" : (marks >= 65) ? "B" : (marks >= 55) ? "C" : (marks >= 45) ? "D" : "F";

        ResultSet rst = CrudUtil.execute("SELECT * FROM GRADE WHERE sub_id = ?", subId);
        if (rst.next()){
            return CrudUtil.execute("UPDATE GRADE SET marks = marks + ?, grade = ? WHERE sub_id = ?", assignmentMarks, grade, subId);
        } else {
            String gradeId = loadNextGradeID();
            LocalDateTime currentDateTime = LocalDateTime.now();
            return CrudUtil.execute(
                    "INSERT INTO GRADE VALUES (?, ?, ?, ?, ?)",
                    gradeId,
                    subId,
                    assignmentMarks,
                    grade,
                    currentDateTime
            );
        }

    }

    private boolean updateSubMarks(String subId, String assignmentMarks) throws SQLException {
        return CrudUtil.execute("UPDATE Subject SET total_marks = total_marks + ? WHERE sub_id = ?", assignmentMarks, subId);
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

    public ArrayList<ArrayList> getAllAssignmentStatus() throws SQLException {
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

    public String getPendingOrOverdueAssignmentCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Assignment WHERE assignment_status = 'Pending' OR assignment_status = 'Overdue'");
        while (rst.next()){
            return rst.getString(1);
        }
        return "0";
    }

     public static String loadNextGradeID(){
        try {
            return IdLoader.getNextID("Grade", "grade_id");
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading a Grade ID");
            e.printStackTrace();
        }
        return "G001";
    }

    public static String loadNextSubjectID(){
        try {
            return IdLoader.getNextIdForTwoChar("Subject", "sub_id");
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when loading a Grade ID");
            e.printStackTrace();
        }
        return "SU001";
    }
}
