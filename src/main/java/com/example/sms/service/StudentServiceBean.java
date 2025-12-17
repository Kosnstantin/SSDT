package com.example.sms.service;

import com.example.sms.model.Student;
import com.example.sms.repository.StudentRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class StudentServiceBean implements StudentServiceLocal {

    @EJB
    private StudentRepository repo;

    @Override
    public Student createStudent(Student s) {
        if (s == null) throw new IllegalArgumentException("Student is null");
        return repo.save(s);
    }

    @Override
    public Student getStudent(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // --- ДОДАНО ЦЕЙ МЕТОД (Щоб виправити помилку компіляції) ---
    @Override
    public Student findStudent(Integer id) {
        return repo.findById(id).orElse(null);
    }
    // ------------------------------------------------------------

    @Override
    public List<Student> listStudents() {
        return repo.findAll();
    }

    @Override
    public Student updateStudent(Integer id, Student update) {
        Optional<Student> existing = repo.findById(id);
        if (existing.isEmpty()) return null;
        
        Student st = existing.get();
        
        // Оновлюємо основні поля
        if (update.getFirstName() != null) st.setFirstName(update.getFirstName());
        if (update.getLastName() != null) st.setLastName(update.getLastName());
        if (update.getEmail() != null) st.setEmail(update.getEmail());
        if (update.getDob() != null) st.setDob(update.getDob()); // Дату народження теж варто оновлювати
        
        // Оновлюємо групу
        if (update.getGroup() != null) st.setGroup(update.getGroup());

        // --- ВАЖЛИВО: ОНОВЛЕННЯ БЕЗПЕКИ (Логін, Пароль, Роль) ---
        // Без цього адмін не зможе змінити пароль студенту!
        if (update.getUsername() != null) st.setUsername(update.getUsername());
        if (update.getPassword() != null && !update.getPassword().isEmpty()) {
            st.setPassword(update.getPassword());
        }
        if (update.getRole() != null) st.setRole(update.getRole());
        // -------------------------------------------------------
        
        st.touch();
        return repo.save(st); 
    }

    @Override
    public void deleteStudent(Integer id) {
        repo.deleteById(id);
    }
}