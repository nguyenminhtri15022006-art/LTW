package com.example.webapp.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "products",
        indexes = @Index(name = "IX_products_newest", columnList = "createdAt,id"))
public class Product {
    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = java.time.LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 4000)
    private String description;

    @Column(nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    private String image;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public java.math.BigDecimal getPrice() {
        return price;
    }

    public void setPrice(java.math.BigDecimal value) {
        this.price = value;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer value) {
        this.stock = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String value) {
        this.image = value;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime value) {
        this.createdAt = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category value) {
        this.category = value;
    }
}
