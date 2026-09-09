package com.example.webapp.repository;
import com.example.webapp.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.*;
import java.util.*;
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    List<Product> findAllByOrderByCreatedAtDescIdDesc(Pageable pageable);
    @EntityGraph(attributePaths = "category")
    Optional<Product> findById(Long id);
    boolean existsByCategoryId(Integer id);
}
