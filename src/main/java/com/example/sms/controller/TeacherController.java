package com.example.sms.controller;

import com.example.sms.model.*;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped // Важливо: зберігає стан (вибраний курс/студент) між кліками
public class TeacherController implements Serializable {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Inject
    private LoginController loginController;

    // Стан
    private List<Course> myCourses;
    private Course selectedCourse;
    private List<Enrollment> selectedCourseEnrollments;
    
    // Для форми редагування
    private Enrollment selectedEnrollmentForForm;
    private Double gradeInput;

    // --- 1. ЗАВАНТАЖЕННЯ СПИСКУ КУРСІВ ---
    public void loadMyCourses() {
        Person currentUser = loginController.getCurrentUser();
        if (currentUser != null && currentUser.getRole() == Role.TEACHER) {
            myCourses = em.createQuery("SELECT c FROM Course c WHERE c.teacher.id = :teacherId", Course.class)
                    .setParameter("teacherId", currentUser.getId())
                    .getResultList();
        } else {
            myCourses = Collections.emptyList();
        }
    }

    public List<Course> getMyCourses() {
        if (myCourses == null) {
            loadMyCourses();
        }
        return myCourses;
    }

    // --- 2. ПЕРЕХІД ДО ДЕТАЛЕЙ КУРСУ ---
    public String viewCourse(Course course) {
        this.selectedCourse = course;
        // Очищаємо форму редагування при переході на новий курс
        this.selectedEnrollmentForForm = null;
        this.gradeInput = null;
        
        reloadEnrollments();
        return "teacher-course-details?faces-redirect=true";
    }
    
    private void reloadEnrollments() {
        if (selectedCourse != null) {
            this.selectedCourseEnrollments = em.createQuery(
                "SELECT e FROM Enrollment e WHERE e.course.id = :courseId", Enrollment.class)
                .setParameter("courseId", selectedCourse.getId())
                .getResultList();
        }
    }

    // --- 3. ПІДГОТОВКА ДО ОЦІНЮВАННЯ (Натиснули Select) ---
    public void prepareGrading(Enrollment e) {
        this.selectedEnrollmentForForm = e;
        // Якщо e == null (натиснули Cancel), очищаємо інпут
        if (e != null) {
            this.gradeInput = getCurrentGradeValue(e);
        } else {
            this.gradeInput = null;
        }
    }

    // --- 4. ЗБЕРЕЖЕННЯ ОЦІНКИ З ФОРМИ ---
    @Transactional
    public void saveGradeFromForm() {
        if (selectedEnrollmentForForm != null && gradeInput != null) {
            updateGradeLogic(selectedEnrollmentForForm, gradeInput);
            
            // Очищення
            selectedEnrollmentForForm = null;
            gradeInput = null;
            
            // Оновлюємо список, щоб у таблиці з'явилася нова цифра
            reloadEnrollments();
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Grade updated successfully!"));
        }
    }

    // Логіка оновлення в БД
    private void updateGradeLogic(Enrollment enrollment, Double newVal) {
        try {
            List<Grade> grades = enrollment.getGrades();
            Grade gradeToUpdate;

            if (grades != null && !grades.isEmpty()) {
                // Оновлюємо існуючу
                gradeToUpdate = grades.get(0);
                gradeToUpdate.setValue(newVal);
                gradeToUpdate.setDate(LocalDate.now());
                em.merge(gradeToUpdate);
            } else {
                // Створюємо нову
                Grade newGrade = new Grade(null, enrollment, newVal, LocalDate.now());
                em.persist(newGrade);
                // Додаємо в список об'єкта в пам'яті, щоб не робити зайвий запит
                if (grades != null) grades.add(newGrade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Допоміжний метод: отримати число оцінки або 0.0
    public Double getCurrentGradeValue(Enrollment e) {
        if (e.getGrades() != null && !e.getGrades().isEmpty()) {
            return e.getGrades().get(0).getValue();
        }
        return 0.0;
    }

    // --- ГЕТТЕРИ ТА СЕТТЕРИ ---
    public Course getSelectedCourse() { return selectedCourse; }
    public List<Enrollment> getSelectedCourseEnrollments() { return selectedCourseEnrollments; }
    
    public Enrollment getSelectedEnrollmentForForm() { return selectedEnrollmentForForm; }
    public Double getGradeInput() { return gradeInput; }
    public void setGradeInput(Double gradeInput) { this.gradeInput = gradeInput; }
}