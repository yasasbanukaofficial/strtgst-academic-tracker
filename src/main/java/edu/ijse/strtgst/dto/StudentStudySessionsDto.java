package edu.ijse.strtgst.dto;

public class StudentStudySessionsDto {
    private int studSsId;
    private int studId;
    private int ssId;

    public StudentStudySessionsDto() {}

    public StudentStudySessionsDto(int studSsId, int studId, int ssId) {
        this.studSsId = studSsId;
        this.studId = studId;
        this.ssId = ssId;
    }

    public int getStudSsId() {
        return studSsId;
    }

    public void setStudSsId(int studSsId) {
        this.studSsId = studSsId;
    }

    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public int getSsId() {
        return ssId;
    }

    public void setSsId(int ssId) {
        this.ssId = ssId;
    }

    @Override
    public String toString() {
        return "StudentStudySessionsDto{" +
                "studSsId=" + studSsId +
                ", studId=" + studId +
                ", ssId=" + ssId +
                '}';
    }
}
