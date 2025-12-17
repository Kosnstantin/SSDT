package com.example.sms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "grades")
public class Grade extends BaseEntity {

    // Зв'язок: багато оцінок для одного зарахування
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @Column(name = "value")
    private Double value;

    @Column(name = "date")
    private LocalDate date;

    public Grade() {}

    public Grade(Integer id, Enrollment enrollment, Double value, LocalDate date) {
        this.id = id; 
        this.enrollment = enrollment; 
        this.value = value; 
        this.date = date;
    }

    public String getLetter() {
        if (value == null) return "-";
        if (value >= 90) return "A";
        if (value >= 82) return "B";
        if (value >= 75) return "C";
        if (value >= 64) return "D";
        if (value >= 60) return "E";
        return "F";
    }

    public String getCssClass() {
    return (value != null && value >= 60) ? "grade-pass" : "grade-fail";
}

    // Геттери та сеттери
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    // Старий геттер
    public Integer getEnrollmentId() { return (enrollment != null) ? enrollment.getId() : null; }
}