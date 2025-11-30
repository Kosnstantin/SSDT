package com.example.sms.repository;

import com.example.sms.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student s);
    Optional<Student> findById(Integer id);
    List<Student> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    
    // новий метод для 4 лаби
    List<Student> findByLastName(String lastName);
}