package com.example.sms.model;

import java.time.Instant;
import java.util.Objects;

public abstract class BaseEntity {
    protected Integer id;
    protected Instant createdAt;
    protected Instant updatedAt;

    public BaseEntity() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void touch() { this.updatedAt = Instant.now(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
