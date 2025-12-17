package com.example.sms.controller;

import com.example.sms.model.Role;
import com.example.sms.model.Teacher;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AdminTeacherController implements Serializable {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    private List<Teacher> teachers;
    private Teacher currentTeacher;

    // --- СПИСОК ---
    public List<Teacher> getTeachers() {
        teachers = em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
        return teachers;
    }

    // --- ПІДГОТОВКА ДО СТВОРЕННЯ ---
    public String prepareCreate() {
        this.currentTeacher = new Teacher();
        return "teacher-form?faces-redirect=true";
    }

    // --- ПІДГОТОВКА ДО РЕДАГУВАННЯ ---
    public String prepareEdit(Teacher t) {
        this.currentTeacher = t;
        return "teacher-form?faces-redirect=true";
    }

    // --- ЗБЕРЕЖЕННЯ ---
    @Transactional
    public String save() {
        currentTeacher.setRole(Role.TEACHER);

        if (currentTeacher.getId() == null) {
            // Новий викладач -> Persist
            em.persist(currentTeacher);
        } else {
            // Редагування -> Merge
            // Логіка пароля (якщо пустий - лишаємо старий)
            if (currentTeacher.getPassword() == null || currentTeacher.getPassword().isEmpty()) {
                Teacher old = em.find(Teacher.class, currentTeacher.getId());
                if (old != null) {
                    currentTeacher.setPassword(old.getPassword());
                }
            }
            em.merge(currentTeacher);
        }
        return "teachers-list?faces-redirect=true";
    }

    // --- ВИДАЛЕННЯ ---
    @Transactional
    public void delete(Integer id) {
        Teacher t = em.find(Teacher.class, id);
        if (t != null) {
            em.remove(t);
        }
    }

    // Геттери та Сеттери
    public Teacher getCurrentTeacher() { return currentTeacher; }
    public void setCurrentTeacher(Teacher currentTeacher) { this.currentTeacher = currentTeacher; }
}