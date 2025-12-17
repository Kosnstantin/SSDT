package com.example.sms.controller;

import com.example.sms.model.Course;
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
public class CourseController implements Serializable {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    private List<Course> courses;
    private Course currentCourse;
    private Integer selectedTeacherId; // ID вибраного викладача для форми

    // --- 1. СПИСОК КУРСІВ ---
    public List<Course> getCourses() {
        // Завжди оновлюємо список
        courses = em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        return courses;
    }

    // --- 2. ПІДГОТОВКА ДО СТВОРЕННЯ/РЕДАГУВАННЯ ---
    public String prepareCreate() {
        this.currentCourse = new Course();
        this.selectedTeacherId = null;
        return "admin-course-form?faces-redirect=true";
    }

    public String prepareEdit(Course c) {
        this.currentCourse = c;
        // Якщо у курсу є викладач, запам'ятовуємо його ID для випадаючого списку
        this.selectedTeacherId = (c.getTeacher() != null) ? c.getTeacher().getId() : null;
        return "admin-course-form?faces-redirect=true";
    }

    // --- 3. ЗБЕРЕЖЕННЯ ---
    @Transactional
    public String save() {
        // Знаходимо об'єкт викладача за ID
        if (selectedTeacherId != null) {
            Teacher t = em.find(Teacher.class, selectedTeacherId);
            currentCourse.setTeacher(t);
        } else {
            currentCourse.setTeacher(null);
        }

        if (currentCourse.getId() == null) {
            em.persist(currentCourse); // Створити новий
        } else {
            em.merge(currentCourse);   // Оновити існуючий
        }
        return "admin-courses?faces-redirect=true";
    }

    // --- 4. ВИДАЛЕННЯ ---
    @Transactional
    public void delete(Integer id) { 
        Course c = em.find(Course.class, id); 
        if (c != null) {
            em.remove(c);
        }
    }

    // --- ДОПОМІЖНИЙ МЕТОД: Список усіх викладачів для Dropdown ---
    public List<Teacher> getAllTeachers() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }

    // Геттери/Сеттери
    public Course getCurrentCourse() { return currentCourse; }
    public void setCurrentCourse(Course currentCourse) { this.currentCourse = currentCourse; }
    public Integer getSelectedTeacherId() { return selectedTeacherId; }
    public void setSelectedTeacherId(Integer selectedTeacherId) { this.selectedTeacherId = selectedTeacherId; }
}