package com.example.sms.controller;

import com.example.sms.model.Grade;
import com.example.sms.model.Person;
import com.example.sms.model.Role;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Named
@RequestScoped
public class GradeController {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Inject
    private LoginController loginController;

    public List<Grade> getMyGrades() {
        Person currentUser = loginController.getCurrentUser();
        
        // Перевірка безпеки: показуємо тільки студентам
        if (currentUser == null || currentUser.getRole() != Role.STUDENT) {
            return Collections.emptyList();
        }

        // JPQL Запит: Вибрати Оцінки, де Enrollment -> Student -> ID == поточний ID
        return em.createQuery("SELECT g FROM Grade g WHERE g.enrollment.student.id = :studentId", Grade.class)
                .setParameter("studentId", currentUser.getId())
                .getResultList();
    }
}