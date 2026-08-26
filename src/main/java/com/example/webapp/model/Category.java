package com.example.webapp.model;

/**
 * Model class đại diện cho một Category (danh mục) trong hệ thống.
 */
public class Category {
    private int id;
    private String name;

    // Constructor rỗng
    public Category() {
    }

    // Constructor đầy đủ
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "'}";
    }
}
