package edu.ijse.strtgst.model;

import edu.ijse.strtgst.db.DBConnection;
import edu.ijse.strtgst.dto.SubjectDto;
import edu.ijse.strtgst.util.AlertUtil;
import edu.ijse.strtgst.util.CrudUtil;
import edu.ijse.strtgst.util.IdLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SubjectModel {
    private boolean saveSubject(SubjectDto subjectDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Subject VALUES (?, ?, ?, ?, ?)",
                subjectDto.getSubId(),
                subjectDto.getStudId(),
                subjectDto.getSubName(),
                subjectDto.getSubDescription(),
                subjectDto.getTotalMarks()
        );
    }

    public boolean addSubject(SubjectDto subjectDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isSaved = saveSubject(subjectDto);
            if (isSaved){
                String subId = fetchExistingID(subjectDto.getSubName());
                if (subId == null){
                    AlertUtil.setErrorAlert("Subject not saved");
                }
                boolean subMarksUpdated = updateSubMarks(subId, subjectDto.getTotalMarks());
                if (subMarksUpdated){
                    boolean gradeMarksUpdate = addGradeMarks(subId, subjectDto.getTotalMarks());
                    if (gradeMarksUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e){
            AlertUtil.setErrorAlert("Error when adding an Subject");
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
            return true;
        }
    }

    private boolean addGradeMarks(String subId, String subjectMarks) throws SQLException {
        int marks = Integer.parseInt(subjectMarks);
        String grade = (marks >= 75) ? "A" : (marks >= 65) ? "B" : (marks >= 55) ? "C" : (marks >= 45) ? "D" : "F";
        ResultSet rst = CrudUtil.execute("SELECT * FROM GRADE WHERE sub_id = ?", subId);
        if (rst.next()){
            return CrudUtil.execute("UPDATE GRADE SET marks = marks + ?, grade = ? WHERE sub_id = ?", subjectMarks, grade, subId);
        } else {
            String gradeId = loadNextGradeID();
            LocalDateTime currentDateTime = LocalDateTime.now();
            return CrudUtil.execute(
                    "INSERT INTO GRADE VALUES (?, ?, ?, ?, ?)",
                    gradeId,
                    subId,
                    subjectMarks,
                    grade,
                    currentDateTime
            );
        }
    }

    private boolean updateSubMarks(String subId, String SubjectMarks) throws SQLException {
        return CrudUtil.execute("UPDATE Subject SET total_marks = total_marks + ? WHERE sub_id = ?", SubjectMarks, subId);
    }

    public boolean deleteSubject(String SubjectId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Subject WHERE Subject_id = ?", SubjectId);
    }

    public static String fetchExistingID(String subjectName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Subject WHERE sub_name = ?", subjectName);
        return rst.next() ? rst.getString(1) : null;
    }

    public ArrayList<SubjectDto> getAllSubjects() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Subject");
        ArrayList<SubjectDto> subjectDtos = new ArrayList<>();
        while (rst.next()) {
            SubjectDto subjectDto = new SubjectDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    public boolean editSubject(SubjectDto subjectDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Subject SET sub_name = ?, description = ?, total_marks = ? WHERE sub_id = ?",
                subjectDto.getSubName(),
                subjectDto.getSubDescription(),
                subjectDto.getTotalMarks(),
                subjectDto.getSubId()
        );
    }

    public ArrayList<ArrayList> getAllSubjectStatus() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Subject_status, due_date, Subject_id FROM Subject");
        ArrayList<ArrayList> list = new ArrayList<>();  
        while (rst.next()) {
            ArrayList<String> row = new ArrayList<>();
            row.add(rst.getString("Subject_status"));
            row.add(rst.getString("due_date"));
            row.add(rst.getString("Subject_id"));
            list.add(row);
        }
        return list;
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


    

}
