package com.example.sms.repository;

import com.example.sms.model.Person;
import java.util.Optional;

public interface UserRepository {
    Optional<Person> findByUsername(String username);
}