package com.example.sms.controller;

import com.example.sms.model.Group;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GroupController implements Serializable {

    @PersistenceContext(unitName = "sms-persistence-unit")
    private EntityManager em;

    private List<Group> groups;
    private Group currentGroup;

    // --- 1. СПИСОК ГРУП ---
    public List<Group> getGroups() {
        // Оновлюємо список кожного разу
        groups = em.createQuery("SELECT g FROM Group g ORDER BY g.year DESC, g.name ASC", Group.class).getResultList();
        return groups;
    }

    // --- 2. ПІДГОТОВКА ДО СТВОРЕННЯ ---
    public String prepareCreate() {
        this.currentGroup = new Group();
        return "group-form?faces-redirect=true";
    }

    // --- 3. ПІДГОТОВКА ДО РЕДАГУВАННЯ ---
    public String prepareEdit(Group g) {
        this.currentGroup = g;
        return "group-form?faces-redirect=true";
    }

    // --- 4. ЗБЕРЕЖЕННЯ ---
    @Transactional
    public String save() {
        if (currentGroup.getId() == null) {
            em.persist(currentGroup);
        } else {
            em.merge(currentGroup);
        }
        return "groups-list?faces-redirect=true";
    }

    // --- 5. ВИДАЛЕННЯ ---
    @Transactional
    public void delete(Integer id) {
        Group g = em.find(Group.class, id);
        if (g != null) {
            // Важливо: перед видаленням треба перевірити, чи є в групі студенти.
            em.remove(g);
        }
    }

    // Геттери та Сеттери
    public Group getCurrentGroup() { return currentGroup; }
    public void setCurrentGroup(Group currentGroup) { this.currentGroup = currentGroup; }
}