package com.example.webapp.dto;

import jakarta.validation.constraints.*;

public class ResetPasswordDTO {
    @NotBlank(message = "Nhập mật khẩu.")
    @Size(min = 8, max = 128, message = "Mật khẩu từ 8 đến 128 ký tự.")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }
}
