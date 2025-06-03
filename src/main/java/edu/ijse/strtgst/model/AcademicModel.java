package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.AcademicDto;
import edu.ijse.strtgst.dto.SubjectDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicModel {
    public AcademicDto getRecentDetails(String tableName) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM " + tableName + " WHERE from_date >= CURRENT_DATE ORDER BY from_date ASC LIMIT 1"
        );
        while (rst.next()) {
            return new AcademicDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBoolean(4),
                    rst.getTimestamp(5),
                    rst.getTimestamp(6),
                    rst.getString(7)
            );
        }
        return null;
    }

    public boolean syncEntryByAi(String query) throws SQLException {
        return CrudUtil.execute(query);
    }

    public ArrayList<SubjectDto> getAllSubjects() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Subject");
        ArrayList<SubjectDto> subjectDtos = new ArrayList<>();
        while (rst.next()) {
            subjectDtos.add(new SubjectDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)
            ));
        }
        return subjectDtos;
    }
}
