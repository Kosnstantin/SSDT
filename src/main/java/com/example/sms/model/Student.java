package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "id") // Зв'язок з таблицею people
public class Student extends Person {

    @Column(name = "dob")
    private String dob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student() {}

    // Конструктор використовує super() для ініціалізації батьківських полів
    public Student(Integer id, String firstName, String lastName, String email, String dob, Group group) {
        super(id, firstName, lastName, email);
        this.dob = dob;
        this.group = group;
    }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
    
    public Integer getGroupId() { return (group != null) ? group.getId() : null; }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + getFullName() + '}';
    }
}