package com.example.sms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email; // Для валідації
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED) // Стратегія JOINED (окрема таблиця для кожного класу)
public abstract class Person extends BaseEntity {

    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null") // Валідація
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "Invalid email format") // Валідація формату пошти
    private String email;

    public Person() {}

    public Person(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Геттери та сеттери
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return firstName + " " + lastName; }
}