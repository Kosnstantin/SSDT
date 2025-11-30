package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "dob")
    private String dob;

    // Зв'язок: багато студентів належать одній групі
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id") // Назва стовпця-зовнішнього ключа
    private Group group;

    // Зв'язок: один студент має багато зарахувань (Enrollments)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student() {}

    // Конструктор оновлено для прийому об'єкта Group
    public Student(Integer id, String firstName, String lastName, String email, String dob, Group group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.group = group;
    }
    
    // Геттери та сеттери
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    
    // Геттер/сеттер для Group
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    // Геттер/сеттер для Enrollments
    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
    
    // Старий getGroupId() може бути корисним (але тепер він бере id з об'єкта)
    public Integer getGroupId() { return (group != null) ? group.getId() : null; }
    
    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + getFullName() + ", email=" + email + '}';
    }
}