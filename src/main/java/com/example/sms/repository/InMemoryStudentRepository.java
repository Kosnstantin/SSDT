package com.example.sms.repository;

import com.example.sms.model.Student;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<Integer, Student> storage = new HashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(0);

    @Override
    public Student save(Student s) {
        if (s.getId() == null) s.setId(idGen.incrementAndGet());
        s.touch();
        storage.put(s.getId(), s);
        return s;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Integer id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return storage.containsKey(id);
    }
}