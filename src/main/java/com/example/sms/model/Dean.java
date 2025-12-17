package com.example.sms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "deans")
@PrimaryKeyJoinColumn(name = "id")
public class Dean extends Person {

    @Column(name = "faculty")
    private String faculty; // Факультет, за який відповідає методист

    public Dean() {}

    public Dean(Integer id, String firstName, String lastName, String email, String username, String password, String faculty) {
        super(id, firstName, lastName, email, username, password, Role.DEAN);
        this.faculty = faculty;
    }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
}