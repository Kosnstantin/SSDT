package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enrollments")
public class Enrollment extends BaseEntity {

    // Зв'язок: багато зарахувань для одного студента
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // Зв'язок: багато зарахувань на один курс
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "semester")
    private String semester;

    @Column(name = "status")
    private String status;
    
    // Зв'язок: одне зарахування має багато оцінок
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    public Enrollment() {}

    public Enrollment(Integer id, Student student, Course course, String semester, String status) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.status = status;
    }

    // Геттери та сеттери
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Grade> getGrades() { return grades; }
    public void setGrades(List<Grade> grades) { this.grades = grades; }

    // Старі геттери
    public Integer getStudentId() { return (student != null) ? student.getId() : null; }
    public Integer getCourseId() { return (course != null) ? course.getId() : null; }
}