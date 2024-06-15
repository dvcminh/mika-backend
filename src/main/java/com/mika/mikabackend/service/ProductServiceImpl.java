package com.mika.mikabackend.service;

import com.mika.mikabackend.dto.UpdateProductRequest;
import com.mika.mikabackend.dto.page.PageData;
import com.mika.mikabackend.model.Product;
import com.mika.mikabackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
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






    private final MongoTemplate mongoTemplate;


    @Override
    public PageData<Product> getProductsByTypeAndCategoryAndSubcategoryAndOfficial(String type, String category, String subcategory, Integer official, PageRequest of) {
        Page<Product> products = productRepository.findByTypeAndCategoryAndSubcategoryAndOfficialAllIgnoreCase(type, category, subcategory, official, of);
        return new PageData<>(products, "Get products by type, category, subcategory and official successfully");
    }

    @Override
    public PageData<Product> getProductsByTitleAndTypeAndCategoryAndSubcategoryAndOfficial(String title, String type, String category, String subcategory, Integer official, PageRequest of) {
        Page<Product> products = productRepository.findByTitleContainsAndTypeAndCategoryAndSubcategoryAndOfficialAllIgnoreCase(title, type, category, subcategory, official, of);
        return new PageData<>(products, "Get products by title, type, category, subcategory and official successfully");
    }

    @Override
    public PageData<Product> getProductsByFilter(String title, String type, String category, String subcategory, Integer official, PageRequest pageRequest) {
        Query query = new Query();

        if (title != null && !title.isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(title, "i"));
        }
        if (type != null && !type.isEmpty()) {
            query.addCriteria(Criteria.where("type").is(type));
        }
        if (category != null && !category.isEmpty()) {
            query.addCriteria(Criteria.where("category").is(category));
        }
        if (subcategory != null && !subcategory.isEmpty()) {
            query.addCriteria(Criteria.where("subcategory").is(subcategory));
        }
        if (official != null) {
            query.addCriteria(Criteria.where("official").is(official));
        }

        long total = mongoTemplate.count(query, Product.class);
        query.with(pageRequest);
        List<Product> products = mongoTemplate.find(query, Product.class);

        Page<Product> productPage = new PageImpl<>(products, pageRequest, total);

        return new PageData<>(productPage, "Get products by filter successfully");
    }

    @Override
    public List<Product> getProductByCategoryAndType(String type, String subcategory) {
        return productRepository.findByTypeAndSubcategoryAllIgnoreCase(type, subcategory).stream().limit(3).toList();
    }

    @Override
    public Product updateProduct(String id, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }
        product.setTitle(updateProductRequest.getTitle());
        product.setDescription(updateProductRequest.getDescription());
        product.setPrice(updateProductRequest.getPrice());
        product.setLink_item(updateProductRequest.getLink_item());
        product.setImage_url(updateProductRequest.getImage_url());
        product.setDiscount_percent_list(updateProductRequest.getDiscount_percent_list());
        product.setCountReviews(updateProductRequest.getCountReviews());
        product.setType(updateProductRequest.getType());
        product.setCategory(updateProductRequest.getCategory());
        product.setSubcategory(updateProductRequest.getSubcategory());
        product.setOfficial(updateProductRequest.getOfficial());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
