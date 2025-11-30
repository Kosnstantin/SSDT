package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "credits")
    private Integer credits;

    // Зв'язок Many-to-One з Teacher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id") // Це створить стовпець teacher_id у таблиці courses (Foreign Key)
    private Teacher teacher;

    // Зв'язок: один курс має багато зарахувань (Enrollments)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
    
    public Course() {}

    public Course(Integer id, String code, String title, Integer credits, Teacher teacher) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.teacher = teacher;
    }
    
    // Геттери та сеттери
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    
    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    // Допоміжний метод
    public Integer getTeacherId() {
        return (teacher != null) ? teacher.getId() : null;
    }
}