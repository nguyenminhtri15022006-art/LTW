package com.example.webapp.dto;

import jakarta.validation.constraints.*;

public class OtpDTO {
    @NotBlank(message = "Nhập OTP.")
    @Pattern(regexp = "[0-9]{6}", message = "OTP phải gồm 6 chữ số.")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String value) {
        this.otp = value;
    }
}
