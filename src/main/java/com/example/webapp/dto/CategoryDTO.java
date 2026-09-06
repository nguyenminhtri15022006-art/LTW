package com.example.webapp.dto;

public class CategoryDTO {
    private Integer id;

    @jakarta.validation.constraints.NotBlank(message = "Nhập tên Category.")
    @jakarta.validation.constraints.Size(max = 255, message = "Tên Category tối đa 255 ký tự.")
    private String name;

    public CategoryDTO() {}

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
