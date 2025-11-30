package com.example.sms.repository;

import com.example.sms.model.Teacher;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaTeacherRepository implements TeacherRepository {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Override
    public Teacher save(Teacher teacher) {
        if (teacher.getId() == null) {
            em.persist(teacher);
            return teacher;
        } else {
            return em.merge(teacher);
        }
    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        Teacher t = em.find(Teacher.class, id);
        return Optional.ofNullable(t);
    }

    @Override
    public List<Teacher> findAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(t -> em.remove(t));
    }

    @Override
    public boolean existsById(Integer id) {
        Long count = em.createQuery("SELECT COUNT(t) FROM Teacher t WHERE t.id = :id", Long.class)
                       .setParameter("id", id)
                       .getSingleResult();
        return count > 0;
    }
}