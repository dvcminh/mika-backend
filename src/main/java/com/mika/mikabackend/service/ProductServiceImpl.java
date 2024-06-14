package com.mika.mikabackend.service;

import com.mika.mikabackend.model.Product;
import com.mika.mikabackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductsByTypeAndCategory(String type, String category, int page, int size) {
        return null;
    }

    public Page<Product> getProductsByTypeAndCategory(String type, String category, String official, Pageable pageable) {
        return productRepository.findDistinctByTypeAndCategoryAndOfficialAllIgnoreCase(type, category, official, pageable);
    }

    @Override
    public Page<Product> getProductsByCategory(String category, Pageable pageable) {
        return productRepository.findDistinctByCategoryAllIgnoreCase(category, pageable);
    }

    public Product getProduct(String id) {
        return null;
    }

    public List<Product> getProductsByCategory(String category, int page, int size) {
        return null;
    }

    public List<Product> getProductsByType(String type, int page, int size) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductByName(String name, String official, Sort sort) {
        return productRepository.findByTitleContainsIgnoreCaseAndOfficial(name, official, sort);
    }

    @Override
    public Product findProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }
}
