package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "degree") // Наприклад: "PhD", "Professor", "Assistant"
    private String degree;

    // Зв'язок: Один викладач може вести багато курсів
    // mappedBy = "teacher" вказує на поле 'teacher' у класі Course
    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> courses = new ArrayList<>();

    public Teacher() {}

    public Teacher(Integer id, String firstName, String lastName, String email, String degree) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.degree = degree;
    }

    // Геттери та сеттери

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", name=" + getFullName() + ", email=" + email + '}';
    }
}