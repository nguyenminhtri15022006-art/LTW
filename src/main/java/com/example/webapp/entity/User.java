package com.example.webapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    public User() {}

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.active = true;
    }

    // Null denotes legacy accounts only; new registration always explicitly sets false.
    public boolean isActive() {
        return !Boolean.FALSE.equals(active);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(length = 254)
    private String email;

    @Column(columnDefinition = "bit default 1")
    private Boolean active;

    @Column(length = 30)
    private String phone;

    private String image;

    @Column(length = 64)
    private String activationOtp;

    private java.time.LocalDateTime activationOtpExpiresAt;

    @Column(length = 64)
    private String resetOtp;

    private java.time.LocalDateTime resetOtpExpiresAt;

    @Column(columnDefinition = "int default 0")
    private Integer activationAttempts;

    @Column(columnDefinition = "int default 0")
    private Integer resetAttempts;

    @Version
    @Column(columnDefinition = "bigint default 0")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean value) {
        this.active = value;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String value) {
        this.image = value;
    }

    public String getActivationOtp() {
        return activationOtp;
    }

    public void setActivationOtp(String value) {
        this.activationOtp = value;
    }

    public java.time.LocalDateTime getActivationOtpExpiresAt() {
        return activationOtpExpiresAt;
    }

    public void setActivationOtpExpiresAt(java.time.LocalDateTime value) {
        this.activationOtpExpiresAt = value;
    }

    public String getResetOtp() {
        return resetOtp;
    }

    public void setResetOtp(String value) {
        this.resetOtp = value;
    }

    public java.time.LocalDateTime getResetOtpExpiresAt() {
        return resetOtpExpiresAt;
    }

    public void setResetOtpExpiresAt(java.time.LocalDateTime value) {
        this.resetOtpExpiresAt = value;
    }

    public Integer getActivationAttempts() {
        return activationAttempts;
    }

    public void setActivationAttempts(Integer value) {
        this.activationAttempts = value;
    }

    public Integer getResetAttempts() {
        return resetAttempts;
    }

    public void setResetAttempts(Integer value) {
        this.resetAttempts = value;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long value) {
        this.version = value;
    }
}
