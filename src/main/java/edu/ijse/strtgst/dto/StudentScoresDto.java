package edu.ijse.strtgst.dto;

import java.sql.Timestamp;

public class StudentScoresDto {
    private int scoreId;
    private String subId;
    private int gradeId;
    private Timestamp updateAt;

    public StudentScoresDto() {}

    public StudentScoresDto(int scoreId, String subId, int gradeId, Timestamp updateAt) {
        this.scoreId = scoreId;
        this.subId = subId;
        this.gradeId = gradeId;
        this.updateAt = updateAt;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "StudentScoresDto{" +
                "scoreId=" + scoreId +
                ", subId='" + subId + '\'' +
                ", gradeId=" + gradeId +
                ", updateAt=" + updateAt +
                '}';
    }
}
