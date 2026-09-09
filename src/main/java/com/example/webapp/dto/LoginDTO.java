package com.example.webapp.dto;

public class LoginDTO {
    @jakarta.validation.constraints.NotBlank(message = "Nhập username.")
    @jakarta.validation.constraints.Size(max = 100, message = "Username tối đa 100 ký tự.")
    private String username;
    @jakarta.validation.constraints.NotBlank(message = "Nhập mật khẩu.")
    @jakarta.validation.constraints.Size(max = 128, message = "Mật khẩu tối đa 128 ký tự.")
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
