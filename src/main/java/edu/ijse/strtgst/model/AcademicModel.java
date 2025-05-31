package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.AcademicDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicModel {
    public AcademicDto getRecentDetails(String tableName) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM " + tableName + " WHERE from_date >= CURRENT_DATE"
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
}
