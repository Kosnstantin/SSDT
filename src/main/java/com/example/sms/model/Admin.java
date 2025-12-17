package com.example.sms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends Person {

    // У адміна може не бути специфічних полів, 
    // але сам факт існування класу дозволяє Hibernate працювати коректно.

    public Admin() {}

    public Admin(Integer id, String firstName, String lastName, String email, String username, String password) {
        super(id, firstName, lastName, email, username, password, Role.ADMIN);
    }
}