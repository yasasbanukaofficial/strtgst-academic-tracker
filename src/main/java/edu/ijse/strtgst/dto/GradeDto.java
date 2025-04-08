package edu.ijse.strtgst.dto;

public class GradeDto {
    private int gradeId;
    private String grade;

    public GradeDto() {}

    public GradeDto(int gradeId, String grade) {
        this.gradeId = gradeId;
        this.grade = grade;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GradeDto{" +
                "gradeId=" + gradeId +
                ", grade='" + grade + '\'' +
                '}';
    }
}
