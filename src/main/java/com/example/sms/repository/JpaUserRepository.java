package com.example.sms.repository;

import com.example.sms.model.Person;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class JpaUserRepository implements UserRepository {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    @Override
    public Optional<Person> findByUsername(String username) {
        try {
            Person person = em.createQuery("SELECT p FROM Person p WHERE p.username = :username", Person.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(person);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}