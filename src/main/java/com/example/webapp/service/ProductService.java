package com.example.webapp.service;

import com.example.webapp.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    int PAGE_SIZE = 6;

    List<ProductDTO> newest();

    List<ProductDTO> page(int page);

    long count();

    ProductDTO getById(long id);

    void save(ProductDTO dto);

    void delete(long id);
}
