package com.example.sms.repository;

import com.example.sms.model.Student;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless // Це EJB-компонент
public class JpaStudentRepository implements StudentRepository {

    // 1. Ін'єктуємо EntityManager
    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Override
    public Student save(Student s) {
        // Метод touch() викликається у сервісному шарі
        // em.persist() - для нового, em.merge() - для існуючого
        if (s.getId() == null) {
            em.persist(s);
            return s;
        } else {
            return em.merge(s);
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        // em.find() шукає за первинним ключем
        Student student = em.find(Student.class, id);
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findAll() {
        // Використовуємо JPQL (Java Persistence Query Language)
        return em.createQuery("SELECT s FROM Student s", Student.class)
                 .getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        // Треба спочатку знайти сутність, а потім видалити
        findById(id).ifPresent(student -> {
            em.remove(student);
        });
    }

    @Override
    public boolean existsById(Integer id) {
        // Ефективний запит, щоб перевірити існування
        Long count = em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.id = :id", Long.class)
                       .setParameter("id", id)
                       .getSingleResult();
        return count > 0;
    }
}