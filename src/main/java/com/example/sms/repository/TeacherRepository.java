package com.example.sms.repository;

import com.example.sms.model.Teacher;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository {
    Teacher save(Teacher teacher);
    Optional<Teacher> findById(Integer id);
    List<Teacher> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
}