package com.example.sms.repository;

import com.example.sms.model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStudentRepositoryTest {

    @Test
    void saveAndFind() {
        InMemoryStudentRepository repo = new InMemoryStudentRepository();
        Student s = new Student(null, "Anna", "Koval", "a@mail.com", "1999-05-05", 1);
        Student saved = repo.save(s);
        assertNotNull(saved.getId());
        assertTrue(repo.existsById(saved.getId()));
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void deleteWorks() {
        InMemoryStudentRepository repo = new InMemoryStudentRepository();
        Student s = repo.save(new Student(null, "P", "Q", "p@q", "1998-01-01", 1));
        repo.deleteById(s.getId());
        assertFalse(repo.existsById(s.getId()));
    }

    @Test
    void findByIdReturnsEmptyIfNotFound() {
    InMemoryStudentRepository repo = new InMemoryStudentRepository();
    assertTrue(repo.findById(99).isEmpty());
    }

    @Test
    void saveShouldUpdateExistingStudent() {
    InMemoryStudentRepository repo = new InMemoryStudentRepository();
    Student s = repo.save(new Student(null, "John", "Doe", "john@mail.com", "1998-01-01", 1));
    s.setEmail("updated@mail.com");
    repo.save(s);
    assertEquals("updated@mail.com", repo.findById(s.getId()).get().getEmail());
    }

    @Test
    void existsByIdWorksCorrectly() {
    InMemoryStudentRepository repo = new InMemoryStudentRepository();
    Student s = repo.save(new Student(null, "Test", "T", "t@mail", "2000-01-01", 1));
    assertTrue(repo.existsById(s.getId()));
    }
}