package com.example.webapp;

import static org.junit.jupiter.api.Assertions.*;

import com.example.webapp.dao.UserDao;
import com.example.webapp.dto.*;
import com.example.webapp.entity.User;
import com.example.webapp.service.*;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

class UserServiceTest {
    MemoryUsers dao;
    CaptureMail mail;
    UserServiceImpl service;

    @BeforeEach
    void setup() {
        dao = new MemoryUsers();
        mail = new CaptureMail();
        service = new UserServiceImpl(dao, mail);
    }

    RegisterDTO registration() {
        RegisterDTO d = new RegisterDTO();
        d.setUsername("student");
        d.setPassword("password123");
        d.setFullName("Nguyễn Văn A");
        d.setEmail("student@example.com");
        return d;
    }

    User register() {
        service.register(registration());
        return dao.findByEmail("student@example.com");
    }

    void activate() {
        service.activate("student@example.com", mail.otp);
    }

    @Test
    void registrationRequiresOtpAndStoresNoPlainPasswordOrOtp() {
        User u = register();
        assertFalse(u.isActive());
        assertNotEquals("password123", u.getPassword());
        assertTrue(mail.otp.matches("[0-9]{6}"));
        assertNotEquals(mail.otp, u.getActivationOtp());
        assertTrue(u.getActivationOtpExpiresAt().isAfter(LocalDateTime.now().plusMinutes(4)));
        assertThrows(
                ValidationException.class,
                () -> service.login(new LoginDTO("student", "password123")));
        activate();
        assertTrue(u.isActive());
        assertNull(u.getActivationOtp());
        assertNull(u.getActivationOtpExpiresAt());
        assertEquals(
                "student", service.login(new LoginDTO("student", "password123")).getUsername());
        assertThrows(ValidationException.class, () -> activate());
    }

    @Test
    void duplicateUsernameAndEmailAreRejected() {
        register();
        assertTrue(
                assertThrows(ValidationException.class, () -> service.register(registration()))
                        .getErrors()
                        .containsKey("username"));
        RegisterDTO d = registration();
        d.setUsername("another");
        assertTrue(
                assertThrows(ValidationException.class, () -> service.register(d))
                        .getErrors()
                        .containsKey("email"));
    }

    @Test
    void expiryAndAttemptLimitAreEnforced() {
        User u = register();
        String valid = mail.otp;
        String wrong = valid.equals("000000") ? "000001" : "000000";
        for (int i = 0; i < 5; i++)
            assertThrows(ValidationException.class, () -> service.activate(u.getEmail(), wrong));
        assertThrows(ValidationException.class, () -> service.activate(u.getEmail(), valid));
        u.setActivationAttempts(0);
        u.setActivationOtpExpiresAt(LocalDateTime.now().minusSeconds(1));
        assertTrue(
                assertThrows(ValidationException.class, () -> service.activate(u.getEmail(), valid))
                        .getMessage()
                        .contains("hết hạn"));
    }

    @Test
    void passwordResetIsSingleUseAndRejectsOldPassword() {
        User u = register();
        activate();
        service.forgotPassword(u.getEmail());
        String otp = mail.otp;
        service.resetPassword(u.getEmail(), otp, "newPassword123");
        assertNull(u.getResetOtp());
        assertNull(u.getResetOtpExpiresAt());
        assertThrows(
                ValidationException.class,
                () -> service.login(new LoginDTO("student", "password123")));
        assertNotNull(service.login(new LoginDTO("student", "newPassword123")));
        assertThrows(
                ValidationException.class,
                () -> service.resetPassword(u.getEmail(), otp, "anotherPass123"));
    }

    @Test
    void resetWrongAndExpiredCodesDoNotChangePassword() {
        User u = register();
        activate();
        service.forgotPassword(u.getEmail());
        String otp = mail.otp, old = u.getPassword();
        String wrong = otp.equals("000000") ? "000001" : "000000";
        assertThrows(
                ValidationException.class,
                () -> service.resetPassword(u.getEmail(), wrong, "newPassword123"));
        assertEquals(1, u.getResetAttempts());
        assertEquals(old, u.getPassword());
        u.setResetOtpExpiresAt(LocalDateTime.now().minusSeconds(1));
        assertThrows(
                ValidationException.class,
                () -> service.resetPassword(u.getEmail(), otp, "newPassword123"));
        assertEquals(old, u.getPassword());
    }

    @Test
    void resendInvalidatesOldOtpAndIsThrottled() {
        User u = register();
        assertThrows(ValidationException.class, () -> service.sendActivation(u.getEmail()));
        u.setActivationOtpExpiresAt(LocalDateTime.now().minusSeconds(1));
        service.sendActivation(u.getEmail());
        assertNotNull(u.getActivationOtp());
        activate();
    }

    @Test
    void failedEmailCanBeRetriedWithoutRegisteringAgain() {
        mail.fail = true;
        assertThrows(ValidationException.class, () -> service.register(registration()));
        User u = dao.findByEmail("student@example.com");
        assertNotNull(u);
        assertFalse(u.isActive());
        assertNull(u.getActivationOtp());
        mail.fail = false;
        service.sendActivation(u.getEmail());
        activate();
    }

    @Test
    void legacyLoginMigratesPasswordAndProfilePreservesIdentity() {
        User u = new User("legacy", "oldpass", "Legacy");
        u.setActive(null);
        dao.insert(u);
        assertNotNull(service.login(new LoginDTO("legacy", "oldpass")));
        assertTrue(u.getPassword().startsWith("pbkdf2$"));
        ProfileDTO d = new ProfileDTO();
        d.setFullName("Updated");
        d.setPhone("+84 912345678");
        UserDTO result = service.updateProfile(u.getId(), d, "image.png");
        assertEquals("legacy", result.getUsername());
        assertEquals("Updated", result.getFullName());
        assertEquals("image.png", result.getImage());
    }

    @Test
    void invalidFormsAreRejected() {
        assertThrows(ValidationException.class, () -> service.login(new LoginDTO(" ", " ")));
        RegisterDTO d = registration();
        d.setEmail("bad");
        assertThrows(ValidationException.class, () -> service.register(d));
        assertThrows(
                ValidationException.class, () -> service.forgotPassword("unknown@example.com"));
        register();
        assertThrows(
                ValidationException.class, () -> service.activate("student@example.com", "12x"));
    }

    static class CaptureMail extends MailService {
        String otp;
        boolean fail;

        public void sendOtp(String email, String otp, String purpose) {
            if (fail) throw new ValidationException("email", "SMTP unavailable");
            this.otp = otp;
        }
    }

    static class MemoryUsers implements UserDao {
        Map<Long, User> users = new LinkedHashMap<>();

        public User getUserByUsername(String name) {
            return users.values().stream()
                    .filter(u -> u.getUsername().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }

        public User findByEmail(String email) {
            return users.values().stream()
                    .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .orElse(null);
        }

        public User findById(Long id) {
            return users.get(id);
        }

        public void insert(User u) {
            u.setId((long) users.size() + 1);
            users.put(u.getId(), u);
        }

        public User change(Long id, Consumer<User> change) {
            User u = users.get(id);
            change.accept(u);
            return u;
        }
    }
}
