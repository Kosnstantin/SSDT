package com.example.sms.controller;

import com.example.sms.model.Course;
import com.example.sms.model.Enrollment;
import com.example.sms.model.Student;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
@RequestScoped
public class EnrollmentController {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    private Integer selectedStudentId;
    private Integer selectedCourseId;
    private String semester = "Fall 2025"; // Значення за замовчуванням

    // --- 1. СПИСОК ВСІХ ЗАРАХУВАНЬ ---
    public List<Enrollment> getAllEnrollments() {
        return em.createQuery("SELECT e FROM Enrollment e ORDER BY e.id DESC", Enrollment.class).getResultList();
    }

    // --- 2. ЗАПИСАТИ СТУДЕНТА ---
    @Transactional
    public String enroll() {
        if (selectedStudentId == null || selectedCourseId == null) {
            errorMessage("Please select both student and course.");
            return null;
        }

        // Перевірка: чи вже записаний цей студент на цей курс?
        Long count = em.createQuery("SELECT COUNT(e) FROM Enrollment e WHERE e.student.id = :sId AND e.course.id = :cId", Long.class)
                .setParameter("sId", selectedStudentId)
                .setParameter("cId", selectedCourseId)
                .getSingleResult();

        if (count > 0) {
            errorMessage("Student is already enrolled in this course!");
            return null;
        }

        Student s = em.find(Student.class, selectedStudentId);
        Course c = em.find(Course.class, selectedCourseId);

        if (s != null && c != null) {
            Enrollment e = new Enrollment(null, s, c, semester, "Active");
            em.persist(e);
            return "manage-enrollments?faces-redirect=true"; // Перезавантажити сторінку
        }
        return null;
    }

    // --- 3. ВІДРАХУВАТИ (Видалити) ---
    @Transactional
    public void delete(Integer id) {
        Enrollment e = em.find(Enrollment.class, id);
        if (e != null) {
            em.remove(e);
        }
    }

    // --- ДОПОМІЖНІ СПИСКИ ДЛЯ DROPDOWN ---
    public List<Student> getAllStudents() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public List<Course> getAllCourses() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    private void errorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    // Геттери та Сеттери
    public Integer getSelectedStudentId() { return selectedStudentId; }
    public void setSelectedStudentId(Integer selectedStudentId) { this.selectedStudentId = selectedStudentId; }
    public Integer getSelectedCourseId() { return selectedCourseId; }
    public void setSelectedCourseId(Integer selectedCourseId) { this.selectedCourseId = selectedCourseId; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}