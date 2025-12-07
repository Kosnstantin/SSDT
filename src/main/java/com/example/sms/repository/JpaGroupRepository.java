package com.example.sms.repository;

import com.example.sms.model.Group;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaGroupRepository implements GroupRepository {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Override
    public List<Group> findAll() {
        return em.createQuery("SELECT g FROM Group g", Group.class).getResultList();
    }

    @Override
    public Optional<Group> findById(Integer id) {
        return Optional.ofNullable(em.find(Group.class, id));
    }
}