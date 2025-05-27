package edu.ijse.strtgst.model;

import edu.ijse.strtgst.dto.LectureDto;
import edu.ijse.strtgst.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureModel {
    public boolean addLecture(LectureDto lectureDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Lecture VALUES (?, ?, ?, ?, ?, ?, ?)",
                lectureDto.getLecId(),
                lectureDto.getSubId(),
                lectureDto.getLectureName(),
                lectureDto.getDate(),
                lectureDto.getStartTime(),
                lectureDto.getEndTime(),
                lectureDto.getStatus()
        );
    }
}
