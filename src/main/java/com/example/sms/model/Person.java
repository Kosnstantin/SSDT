package com.example.sms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person extends BaseEntity {

    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "Invalid email format")
    private String email;

    // --- НОВІ ПОЛЯ ДЛЯ КУРСОВОГО ---
    
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // У реальності тут має бути хеш, поки що текст

    @Enumerated(EnumType.STRING) // Зберігаємо в БД як текст ("ADMIN"), а не цифру
    @Column(name = "role", nullable = false)
    private Role role;

    // --- КОНСТРУКТОРИ ---

    public Person() {}

    // Оновлений конструктор
    public Person(Integer id, String firstName, String lastName, String email, String username, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // --- ГЕТТЕРИ ТА СЕТТЕРИ ---

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getFullName() { return firstName + " " + lastName; }
}