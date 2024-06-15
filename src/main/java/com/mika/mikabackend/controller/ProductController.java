package com.mika.mikabackend.controller;

import com.mika.mikabackend.dto.UpdateProductRequest;
import com.mika.mikabackend.dto.page.PageData;
import com.mika.mikabackend.model.Product;
import com.mika.mikabackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getProductsByCategory")
    public Page<Product> getProductsByCategory(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return productService.getProductsByCategory(category, PageRequest.of(page, size, Sort.by(direction, sortBy)));
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

    @GetMapping("/findProductById")
    public Product findProductById(@RequestParam String id,
                                   @RequestParam(required = false) String official,
                                   @RequestParam(defaultValue = "price") String sortBy,
                                   @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return productService.findProductById(id);
    }


    @GetMapping("/getProductsByTypeAndCategoryAndSubcategoryAndOfficial")
    public PageData<Product> getProductsByTypeAndCategoryAndSubcategoryAndOfficial(@RequestParam(value = "title", required = false) String title,
                                                                                   @RequestParam(value = "type", required = false) String type,
                                                                                   @RequestParam(value = "category", required = false) String category,
                                                                                   @RequestParam(value = "subcategory", required = false) String subcategory,
                                                                                   @RequestParam(value = "official", required = false) Integer official,
                                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                   @RequestParam(defaultValue = "price") String sortBy,
                                                                                   @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        return productService.getProductsByFilter(title, type, category, subcategory, official, PageRequest.of(page, size, Sort.by(direction, sortBy)));
//        if (title.isEmpty()) {
//            return productService.getProductsByTypeAndCategoryAndSubcategoryAndOfficial(type, category, subcategory, official, PageRequest.of(page, size, Sort.by(direction, sortBy)));
//
//        }
//        return productService.getProductsByTitleAndTypeAndCategoryAndSubcategoryAndOfficial(title, type, category, subcategory, official, PageRequest.of(page, size, Sort.by(direction, sortBy)));

    }

    @GetMapping("/getSimilarProductsByCategoryAndType")
    public List<Product> getSimilarProductsByCategoryAndType(@RequestParam(value = "type", required = false) String type,
                                                             @RequestParam(value = "subcategory", required = false) String subcategory) {
        return productService.getProductByCategoryAndType(type, subcategory);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest updateProductRequest) {
        Product updatedProduct = productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete product successfully");
    }

}
