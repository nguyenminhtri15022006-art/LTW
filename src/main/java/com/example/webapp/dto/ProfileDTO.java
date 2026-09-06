package com.example.webapp.dto;

import jakarta.validation.constraints.*;

public class ProfileDTO {
    @NotBlank(message = "Nhập họ tên.")
    @Size(max = 255, message = "Họ tên tối đa 255 ký tự.")
    private String fullName;

    @Pattern(regexp = "^$|[+0-9() .-]{7,30}", message = "Số điện thoại gồm 7–30 ký tự hợp lệ.")
    private String phone;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String value) {
        this.fullName = value;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }
}
