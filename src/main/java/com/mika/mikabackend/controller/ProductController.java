package com.mika.mikabackend.controller;

import com.mika.mikabackend.model.Product;
import com.mika.mikabackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getProductsByTypeAndCategory")
    public Page<Product> getProductsByTypeAndCategory(@RequestParam(value = "type", required = false) String type,
                                                      @RequestParam(value = "category", required = false) String category,
                                                      @RequestParam(value = "official", required = false, defaultValue = "") String official,
                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "price") String sortBy,
                                                      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return productService.getProductsByTypeAndCategory(type, category, official, PageRequest.of(page, size, Sort.by(direction, sortBy)));
    }

    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/findProductByName")
    public List<Product> findProductByName(@RequestParam String name,
                                           @RequestParam(required = false) String official,
                                           @RequestParam(defaultValue = "price") String sortBy,
                                           @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return productService.findProductByName(name, official, Sort.by(direction, sortBy));
    }
}
