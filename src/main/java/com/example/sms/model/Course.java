package com.example.sms.model;

public class Course extends BaseEntity {
    private String code;
    private String title;
    private Integer credits;
    private Integer teacherId;

    public Course() {}
    public Course(Integer id, String code, String title, Integer credits, Integer teacherId) {
        this.id = id; this.code = code; this.title = title; this.credits = credits; this.teacherId = teacherId;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }
}