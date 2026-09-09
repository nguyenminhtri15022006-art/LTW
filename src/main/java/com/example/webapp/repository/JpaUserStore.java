package com.example.webapp.repository;
import com.example.webapp.dao.UserDao;
import com.example.webapp.entity.User;
import com.example.webapp.service.ValidationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.function.Consumer;
@Repository
public class JpaUserStore implements UserDao {
    private final UserRepository repository;
    public JpaUserStore(UserRepository repository) { this.repository = repository; }
    public User getUserByUsername(String name) {
        return name == null ? null : repository.findByUsernameIgnoreCase(name.trim()).orElse(null);
    }
    public User findByEmail(String email) {
        return email == null ? null : repository.findByEmailIgnoreCase(email.trim()).orElse(null);
    }
    public User findById(Long id) { return repository.findById(id).orElse(null); }
    public void insert(User user) { repository.saveAndFlush(user); }
    @Transactional
    public User change(Long id, Consumer<User> change) {
        User user = repository.lockById(id).orElseThrow(() -> new ValidationException("form", "Tài khoản không tồn tại."));
        change.accept(user);
        repository.flush();
        return user;
    }
}
