package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@PrimaryKeyJoinColumn(name = "id")
public class Teacher extends Person {

    @Column(name = "degree")
    private String degree;

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> courses = new ArrayList<>();

    public Teacher() {}

    public Teacher(Integer id, String firstName, String lastName, String email, String degree) {
        super(id, firstName, lastName, email);
        this.degree = degree;
    }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", name=" + getFullName() + ", degree=" + degree + '}';
    }
}