package com.example.sms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA-сутність
@Table(name = "groups") // Яка відповідає таблиці "groups"
public class Group extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    // Зв'язок: одна група має багато студентів
    // "mappedBy = "group"" вказує на поле 'group' у класі Student
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();
    
    public Group() {}

    public Group(Integer id, String name, Integer year) {
        this.id = id; this.name = name; this.year = year;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}