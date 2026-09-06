package com.example.webapp.dto;

import jakarta.validation.constraints.*;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Nhập tên sản phẩm.")
    @Size(max = 255, message = "Tên tối đa 255 ký tự.")
    private String name;

    @Size(max = 4000, message = "Mô tả tối đa 4000 ký tự.")
    private String description;

    @NotNull(message = "Nhập giá hợp lệ.")
    @DecimalMin(value = "0", message = "Giá không được âm.")
    @Digits(integer = 16, fraction = 2, message = "Giá tối đa 16 chữ số và 2 số thập phân.")
    private java.math.BigDecimal price;

    @NotNull(message = "Nhập tồn kho dạng số nguyên.")
    @Min(value = 0, message = "Tồn kho không được âm.")
    private Integer stock;

    private String image;

    private java.time.LocalDateTime createdAt;

    @NotNull(message = "Chọn Category.")
    @Min(value = 1, message = "Category không hợp lệ.")
    private Integer categoryId;

    private String categoryName;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer value) {
        this.categoryId = value;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String value) {
        this.categoryName = value;
    }
}
