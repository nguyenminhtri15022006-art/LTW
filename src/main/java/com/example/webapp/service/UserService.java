package com.example.webapp.service;

import com.example.webapp.dto.*;

public interface UserService {
    boolean authenticate(LoginDTO dto);

    UserDTO login(LoginDTO dto);

    UserDTO getUserDetails(String username);

    void register(RegisterDTO dto);

    void sendActivation(String email);

    void activate(String email, String otp);

    void forgotPassword(String email);

    void resetPassword(String email, String otp, String password);

    UserDTO updateProfile(Long id, ProfileDTO dto, String image);
}
