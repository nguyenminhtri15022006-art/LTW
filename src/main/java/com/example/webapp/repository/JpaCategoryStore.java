package com.example.webapp.repository;
import com.example.webapp.dao.CategoryDao;
import com.example.webapp.entity.Category;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class JpaCategoryStore implements CategoryDao {
    private final CategoryRepository repository;
    public JpaCategoryStore(CategoryRepository repository) { this.repository = repository; }
    public List<Category> findAll() { return repository.findAll(org.springframework.data.domain.Sort.by("id")); }
    public Category findById(int id) { return repository.findById(id).orElse(null); }
    public void insert(Category c) { repository.saveAndFlush(c); }
    public void update(Category c) { repository.saveAndFlush(c); }
    public void delete(int id) { repository.deleteById(id); }
}
