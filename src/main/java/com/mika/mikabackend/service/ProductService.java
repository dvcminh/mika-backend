package com.mika.mikabackend.service;

import com.mika.mikabackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByTypeAndCategory(String type, String category, int page, int size);
    Page<Product> getProductsByTypeAndCategory(String type, String category, String official, Pageable pageable);
    List<Product> findAll();

    List<Product> findProductByName(String name, String official, Sort sort);
}
