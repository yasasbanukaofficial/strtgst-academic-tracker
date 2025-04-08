package edu.ijse.strtgst.dto;

public class SubjectDto {
    private String subId;
    private int studId;
    private String subName;
    private int totalMarks;

    public SubjectDto() {}

    public SubjectDto(String subId, int studId, String subName, int totalMarks) {
        this.subId = subId;
        this.studId = studId;
        this.subName = subName;
        this.totalMarks = totalMarks;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "subId='" + subId + '\'' +
                ", studId=" + studId +
                ", subName='" + subName + '\'' +
                ", totalMarks=" + totalMarks +
                '}';
    }
}
