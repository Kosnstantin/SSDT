package com.example.sms.model;

import jakarta.persistence.*; // Імпортуємо JPA
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass // Вказує, що це базовий клас для сутностей
public abstract class BaseEntity {

    @Id // Первинний ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоінкремент
    @Column(name = "id")
    protected Integer id;

    @Column(name = "created_at", updatable = false)
    protected Instant createdAt;

    @Column(name = "updated_at")
    protected Instant updatedAt;

    public BaseEntity() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // Метод PreUpdate для автоматичного оновлення updatedAt
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Метод touch() з 2 лаби не потрібен, якщо є @PreUpdate, 
    // але лишимо, оскільки сервіс його викликає
    public void touch() { 
        this.updatedAt = Instant.now(); 
    }

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