package com.example.sms.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    public void studentCreationAndToString() {
        Student s = new Student(1, "Ivan", "Petrenko", "ivan@mail.com", "2000-01-01", 1);
        assertEquals("Ivan", s.getFirstName());
        assertTrue(s.toString().contains("Ivan"));
    }

    @Test
    public void testFullName() {
    Student s = new Student(2, "Anna", "Koval", "a@k.com", "1999-05-05", 1);
    assertEquals("Anna Koval", s.getFullName());
    }

    @Test
    public void testSettersAndGetters() {
    Student s = new Student();
    s.setFirstName("Oleh");
    s.setLastName("Shevchenko");
    s.setEmail("oleh@mail.com");
    s.setDob("2001-04-04");
    s.setGroupId(3);

    assertEquals("Oleh", s.getFirstName());
    assertEquals("Shevchenko", s.getLastName());
    assertEquals("oleh@mail.com", s.getEmail());
    assertEquals("2001-04-04", s.getDob());
    assertEquals(3, s.getGroupId());
    }
}