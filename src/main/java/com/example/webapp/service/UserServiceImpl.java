package com.example.webapp.service;

import com.example.webapp.dao.*;
import com.example.webapp.dto.*;
import com.example.webapp.entity.User;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final MailService mail;
    private static final SecureRandom RANDOM = new SecureRandom();

    public UserServiceImpl() {
        this(new UserDaoImpl(), new MailService());
    }

    public UserServiceImpl(UserDao dao) {
        this(dao, new MailService());
    }

    public UserServiceImpl(UserDao dao, MailService mail) {
        this.userDao = dao;
        this.mail = mail;
    }

    public boolean authenticate(LoginDTO dto) {
        try {
            return login(dto) != null;
        } catch (ValidationException e) {
            return false;
        }
    }

    public UserDTO login(LoginDTO dto) {
        var errors = new LinkedHashMap<String, String>();
        if (dto == null || dto.getUsername() == null || dto.getUsername().isBlank())
            errors.put("username", "Nhập username.");
        if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank())
            errors.put("password", "Nhập mật khẩu.");
        if (!errors.isEmpty()) throw new ValidationException(errors);
        User u = userDao.getUserByUsername(dto.getUsername());
        if (u == null) throw new ValidationException("username", "Tài khoản không tồn tại.");
        if (!PasswordService.matches(dto.getPassword(), u.getPassword()))
            throw new ValidationException("password", "Mật khẩu không đúng.");
        if (!u.isActive())
            throw new ValidationException(
                    "form", "Tài khoản chưa kích hoạt. Hãy xác nhận OTP email.");
        if (!u.getPassword().startsWith("pbkdf2$")) {
            final String old = u.getPassword();
            u =
                    userDao.change(
                            u.getId(),
                            v -> {
                                if (v.getPassword().equals(old))
                                    v.setPassword(PasswordService.hash(dto.getPassword()));
                            });
        }
        return toDTO(u);
    }

    public UserDTO getUserDetails(String username) {
        return toDTO(userDao.getUserByUsername(username));
    }

    private static String email(String input) {
        EmailDTO dto = new EmailDTO();
        dto.setEmail(input == null ? "" : input.trim().toLowerCase(Locale.ROOT));
        FormValidation.validate(dto);
        return dto.getEmail();
    }

    public void register(RegisterDTO dto) {
        dto.setUsername(dto.getUsername() == null ? "" : dto.getUsername().trim());
        dto.setEmail(email(dto.getEmail()));
        FormValidation.validate(dto);
        var errors = new LinkedHashMap<String, String>();
        if (userDao.getUserByUsername(dto.getUsername()) != null)
            errors.put("username", "Username đã được sử dụng.");
        if (userDao.findByEmail(dto.getEmail()) != null)
            errors.put("email", "Email đã được sử dụng.");
        if (!errors.isEmpty()) throw new ValidationException(errors);
        User u =
                new User(
                        dto.getUsername(),
                        PasswordService.hash(dto.getPassword()),
                        dto.getFullName().trim());
        u.setEmail(dto.getEmail());
        u.setActive(false);
        try {
            userDao.insert(u);
        } catch (jakarta.persistence.PersistenceException e) {
            throw new ValidationException(
                    "form",
                    "Không tạo được tài khoản. Username hoặc email có thể vừa được sử dụng.");
        }
        sendActivation(dto.getEmail());
    }

    public void sendActivation(String address) {
        issue(email(address), false);
    }

    public void forgotPassword(String address) {
        issue(email(address), true);
    }

    private void issue(String address, boolean reset) {
        User u = userDao.findByEmail(address);
        if (u == null) throw new ValidationException("email", "Email chưa được đăng ký.");
        String otp = String.format(Locale.ROOT, "%06d", RANDOM.nextInt(1000000));
        userDao.change(
                u.getId(),
                v -> {
                    if (reset && !v.isActive())
                        throw new ValidationException(
                                "email", "Tài khoản chưa kích hoạt. Hãy xác nhận email trước.");
                    if (!reset && v.isActive())
                        throw new ValidationException(
                                "email", "Tài khoản đã kích hoạt. Bạn có thể đăng nhập.");
                    LocalDateTime expires =
                            reset ? v.getResetOtpExpiresAt() : v.getActivationOtpExpiresAt();
                    if (expires != null && expires.minusMinutes(4).isAfter(LocalDateTime.now()))
                        throw new ValidationException(
                                "email", "Vui lòng chờ 60 giây trước khi gửi lại OTP.");
                    if (reset) {
                        v.setResetOtp(digest(otp));
                        v.setResetOtpExpiresAt(LocalDateTime.now().plusMinutes(5));
                        v.setResetAttempts(0);
                    } else {
                        v.setActivationOtp(digest(otp));
                        v.setActivationOtpExpiresAt(LocalDateTime.now().plusMinutes(5));
                        v.setActivationAttempts(0);
                    }
                });
        try {
            mail.sendOtp(address, otp, reset ? "Đặt lại mật khẩu" : "Kích hoạt tài khoản");
        } catch (ValidationException e) {
            userDao.change(
                    u.getId(),
                    v -> {
                        if (reset && Objects.equals(v.getResetOtp(), digest(otp))) {
                            v.setResetOtp(null);
                            v.setResetOtpExpiresAt(null);
                        }
                        if (!reset && Objects.equals(v.getActivationOtp(), digest(otp))) {
                            v.setActivationOtp(null);
                            v.setActivationOtpExpiresAt(null);
                        }
                    });
            throw e;
        }
    }

    public void activate(String address, String otp) {
        verify(address, otp, null, false);
    }

    public void resetPassword(String address, String otp, String password) {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setPassword(password);
        FormValidation.validate(dto);
        verify(address, otp, password, true);
    }

    private void verify(String address, String otp, String password, boolean reset) {
        OtpDTO dto = new OtpDTO();
        dto.setOtp(otp);
        FormValidation.validate(dto);
        User u = userDao.findByEmail(email(address));
        if (u == null) throw new ValidationException("email", "Email chưa được đăng ký.");
        String[] failure = {null};
        userDao.change(
                u.getId(),
                v -> {
                    String expected = reset ? v.getResetOtp() : v.getActivationOtp();
                    LocalDateTime expires =
                            reset ? v.getResetOtpExpiresAt() : v.getActivationOtpExpiresAt();
                    Integer count = reset ? v.getResetAttempts() : v.getActivationAttempts();
                    int attempts = count == null ? 0 : count;
                    if (expected == null || expires == null)
                        failure[0] = "Chưa có OTP hoặc OTP đã dùng. Hãy yêu cầu mã mới.";
                    else if (!expires.isAfter(LocalDateTime.now()))
                        failure[0] = "OTP đã hết hạn. Hãy yêu cầu mã mới.";
                    else if (attempts >= 5) failure[0] = "Đã sai OTP 5 lần. Hãy yêu cầu mã mới.";
                    else if (!MessageDigest.isEqual(
                            expected.getBytes(StandardCharsets.UTF_8),
                            digest(otp).getBytes(StandardCharsets.UTF_8))) {
                        if (reset) v.setResetAttempts(attempts + 1);
                        else v.setActivationAttempts(attempts + 1);
                        failure[0] = "OTP không đúng. Còn " + (4 - attempts) + " lần thử.";
                    } else if (reset) {
                        v.setPassword(PasswordService.hash(password));
                        v.setResetOtp(null);
                        v.setResetOtpExpiresAt(null);
                        v.setResetAttempts(0);
                    } else {
                        v.setActive(true);
                        v.setActivationOtp(null);
                        v.setActivationOtpExpiresAt(null);
                        v.setActivationAttempts(0);
                    }
                });
        // Throw after commit so a failed attempt is persisted.
        if (failure[0] != null) throw new ValidationException("otp", failure[0]);
    }

    private static String digest(String otp) {
        try {
            return HexFormat.of()
                    .formatHex(
                            MessageDigest.getInstance("SHA-256")
                                    .digest(otp.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public UserDTO updateProfile(Long id, ProfileDTO dto, String image) {
        FormValidation.validate(dto);
        return toDTO(
                userDao.change(
                        id,
                        u -> {
                            u.setFullName(dto.getFullName().trim());
                            u.setPhone(dto.getPhone());
                            if (image != null) u.setImage(image);
                        }));
    }

    private UserDTO toDTO(User u) {
        if (u == null) return null;
        UserDTO d = new UserDTO(u.getId(), u.getUsername(), u.getFullName());
        d.setEmail(u.getEmail());
        d.setPhone(u.getPhone());
        d.setImage(u.getImage());
        return d;
    }
}
