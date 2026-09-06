package com.example.webapp.dto;

import jakarta.validation.constraints.*;

public class RegisterDTO {
    @NotBlank(message = "Nhập username.")
    @Pattern(regexp = "[A-Za-z0-9_.-]{3,100}", message = "Username gồm 3–100 chữ, số, _, . hoặc -.")
    private String username;

    @NotBlank(message = "Nhập mật khẩu.")
    @Size(min = 8, max = 128, message = "Mật khẩu từ 8 đến 128 ký tự.")
    private String password;

    @NotBlank(message = "Nhập họ tên.")
    @Size(max = 255, message = "Họ tên tối đa 255 ký tự.")
    private String fullName;

    @NotBlank(message = "Nhập email.")
    @Email(message = "Email không hợp lệ.")
    @Size(max = 254, message = "Email quá dài.")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String value) {
        this.fullName = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }
}
