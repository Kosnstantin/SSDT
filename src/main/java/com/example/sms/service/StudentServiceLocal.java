package com.example.sms.service;

import com.example.sms.model.Student;
import java.util.List;

public interface StudentServiceLocal {
    
    Student createStudent(Student s);
    Student getStudent(Integer id);
    List<Student> listStudents();
    Student updateStudent(Integer id, Student update);
    void deleteStudent(Integer id);
    public Student findStudent(Integer id);
}

