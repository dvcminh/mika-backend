package com.mika.mikabackend.service;

import com.mika.mikabackend.dto.UpdateProductRequest;
import com.mika.mikabackend.dto.page.PageData;
import com.mika.mikabackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByTypeAndCategory(String type, String category, int page, int size);
    Page<Product> getProductsByTypeAndCategory(String type, String category, String official, Pageable pageable);
    Page<Product> getProductsByCategory(String category, Pageable pageable);
    List<Product> findAll();

    List<Product> findProductByName(String name, String official, Sort sort);

    Product findProductById(String id);

    PageData<Product> getProductsByTypeAndCategoryAndSubcategoryAndOfficial(String type, String category, String subcategory, Integer official, PageRequest of);
    PageData<Product> getProductsByTitleAndTypeAndCategoryAndSubcategoryAndOfficial(String title, String type, String category, String subcategory, Integer official, PageRequest of);

    PageData<Product> getProductsByFilter(String title, String type, String category, String subcategory, Integer official, PageRequest of);
    List<Product> getProductByCategoryAndType(String type, String subcategory);

    Product updateProduct(String id, UpdateProductRequest updateProductRequest);

    void deleteProduct(String id);
}
