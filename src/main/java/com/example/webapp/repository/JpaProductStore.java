package com.example.webapp.repository;
import com.example.webapp.dao.ProductDao;
import com.example.webapp.entity.*;
import com.example.webapp.service.ValidationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import java.util.List;
@Repository
public class JpaProductStore implements ProductDao {
    private final ProductRepository products;
    private final CategoryRepository categories;
    public JpaProductStore(ProductRepository products, CategoryRepository categories) {
        this.products = products; this.categories = categories;
    }
    public List<Product> findPage(int offset, int limit) {
        return products.findAllByOrderByCreatedAtDescIdDesc(PageRequest.of(offset / limit, limit));
    }
    public long count() { return products.count(); }
    public Product findById(long id) { return products.findById(id).orElse(null); }
    @Transactional
    public void save(Product value) {
        Product p = value.getId() == null ? new Product() : products.findById(value.getId())
            .orElseThrow(() -> new ValidationException("form", "Sản phẩm không tồn tại."));
        Category c = categories.findById(value.getCategory().getId())
            .orElseThrow(() -> new ValidationException("categoryId", "Category không tồn tại."));
        p.setName(value.getName()); p.setDescription(value.getDescription());
        p.setPrice(value.getPrice()); p.setStock(value.getStock()); p.setCategory(c);
        if (value.getImage() != null) p.setImage(value.getImage());
        products.saveAndFlush(p);
    }
    public void delete(long id) { products.deleteById(id); }
}
