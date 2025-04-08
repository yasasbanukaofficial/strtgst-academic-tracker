package edu.ijse.strtgst.dto;

public class SubjectStudySessionsDto {
    private int subjectSsId;
    private int studId;
    private int ssId;

    public SubjectStudySessionsDto() {}

    public SubjectStudySessionsDto(int subjectSsId, int studId, int ssId) {
        this.subjectSsId = subjectSsId;
        this.studId = studId;
        this.ssId = ssId;
    }

    public int getSubjectSsId() {
        return subjectSsId;
    }

    public void setSubjectSsId(int subjectSsId) {
        this.subjectSsId = subjectSsId;
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
        return "SubjectStudySessionsDto{" +
                "subjectSsId=" + subjectSsId +
                ", studId=" + studId +
                ", ssId=" + ssId +
                '}';
    }
}
