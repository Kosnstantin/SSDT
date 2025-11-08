package com.example.sms.service;

import com.example.sms.model.Student;
import com.example.sms.repository.InMemoryStudentRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class StudentServiceUnitTest {

    @Test
    public void createAndListStudents() {
        InMemoryStudentRepository repo = new InMemoryStudentRepository();
        StudentServiceBean service = new StudentServiceBean(repo);

        Student s1 = new Student(null, "Oleh", "Ivanov", "oleh@mail", "2001-02-02", 1);
        service.createStudent(s1);

        List<Student> all = service.listStudents();
        assertEquals(1, all.size());
        assertEquals("Oleh", all.get(0).getFirstName());
    }

    @Test
    public void updateStudent() {
        InMemoryStudentRepository repo = new InMemoryStudentRepository();
        StudentServiceBean service = new StudentServiceBean(repo);

        Student s = service.createStudent(new Student(null, "A", "B", "e@mail", "2000-01-01", 1));
        Student update = new Student();
        update.setFirstName("Alex");
        Student updated = service.updateStudent(s.getId(), update);
        assertEquals("Alex", updated.getFirstName());
    }

    @Test
    public void getStudentReturnsNullIfNotFound() {
    StudentServiceBean service = new StudentServiceBean(new InMemoryStudentRepository());
    assertNull(service.getStudent(999));
    }

    @Test
    public void deleteStudentRemovesEntry() {
    StudentServiceBean service = new StudentServiceBean(new InMemoryStudentRepository());
    Student s = service.createStudent(new Student(null, "Test", "Del", "t@del", "2002-03-03", 1));
    service.deleteStudent(s.getId());
    assertTrue(service.listStudents().isEmpty());
    }

    @Test
    public void createStudentThrowsExceptionIfNull() {
    StudentServiceBean service = new StudentServiceBean(new InMemoryStudentRepository());
    assertThrows(IllegalArgumentException.class, () -> service.createStudent(null));
    }
}