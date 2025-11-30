package com.example.sms.service;

import com.example.sms.model.Student;
// Видаляємо імпорт InMemoryStudentRepository
import com.example.sms.repository.StudentRepository;
import jakarta.ejb.EJB; // Імпортуємо EJB
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class StudentServiceBean implements StudentServiceLocal {
    // Замість 'private final StudentRepository repo;'
    // та конструкторів, ми ін'єктуємо реалізацію
    @EJB
    private StudentRepository repo;
    // Конструктори видалено
    // Контейнер EJB тепер керує життєвим циклом та ін'єкціями.
    @Override
    public Student createStudent(Student s) {
        if (s == null) throw new IllegalArgumentException("Student is null");
        return repo.save(s);
    }

    @Override
    public Student getStudent(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Student> listStudents() {
        return repo.findAll();
    }

    @Override
    public Student updateStudent(Integer id, Student update) {
        Optional<Student> existing = repo.findById(id);
        if (existing.isEmpty()) return null;
        
        Student st = existing.get();
        if (update.getFirstName() != null) st.setFirstName(update.getFirstName());
        if (update.getLastName() != null) st.setLastName(update.getLastName());
        if (update.getEmail() != null) st.setEmail(update.getEmail());
        
        // Оновлюємо і групу, якщо вона передана
        if (update.getGroup() != null) st.setGroup(update.getGroup());
        
        st.touch();
        return repo.save(st); // save тепер виконає 'merge'
    }

    @Override
    public void deleteStudent(Integer id) {
        repo.deleteById(id);
    }
}