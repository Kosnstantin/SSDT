package com.example.sms.model;

import java.time.LocalDate;

public class Grade extends BaseEntity {
    private Integer enrollmentId;
    private Double value;
    private LocalDate date;

    public Grade() {}
    public Grade(Integer id, Integer enrollmentId, Double value, LocalDate date) {
        this.id = id; this.enrollmentId = enrollmentId; this.value = value; this.date = date;
    }

    public Integer getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Integer enrollmentId) { this.enrollmentId = enrollmentId; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}