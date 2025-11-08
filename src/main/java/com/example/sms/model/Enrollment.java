package com.example.sms.model;

public class Enrollment extends BaseEntity {
    private Integer studentId;
    private Integer courseId;
    private String semester;
    private String status;

    public Enrollment() {}
    public Enrollment(Integer id, Integer studentId, Integer courseId, String semester, String status) {
        this.id = id; this.studentId = studentId; this.courseId = courseId;
        this.semester = semester; this.status = status;
    }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}