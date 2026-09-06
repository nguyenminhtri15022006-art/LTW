package com.example.webapp.dto;

import jakarta.validation.constraints.*;

public class EmailDTO {
    @NotBlank(message = "Nhập email.")
    @Email(message = "Email không hợp lệ.")
    @Size(max = 254, message = "Email quá dài.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }
}
