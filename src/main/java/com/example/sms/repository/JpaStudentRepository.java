package com.example.sms.repository;

import com.example.sms.model.Student;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStudentRepository implements StudentRepository {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Override
    public Student save(Student s) {
        if (s.getId() == null) {
            em.persist(s);
            return s;
        } else {
            return em.merge(s);
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(student -> em.remove(student));
    }

    @Override
    public boolean existsById(Integer id) {
        Long count = em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.id = :id", Long.class)
                       .setParameter("id", id)
                       .getSingleResult();
        return count > 0;
    }

    // Реалізація нового методу для 4 лаби
    @Override
    public List<Student> findByLastName(String lastName) {
        // Використання JPQL параметризованого запиту
        return em.createQuery("SELECT s FROM Student s WHERE s.lastName = :lname", Student.class)
                 .setParameter("lname", lastName)
                 .getResultList();
    }
}