package com.example.sms.repository;

import com.example.sms.model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> findAll();
    Optional<Group> findById(Integer id);
}