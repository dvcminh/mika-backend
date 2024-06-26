package com.mika.mikabackend.repository;

import com.mika.mikabackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findDistinctByTypeAndCategoryOrderByPriceAsc(String type, String category, Pageable pageable);

    Page<Product> findDistinctByTypeAndCategoryAndOfficialAllIgnoreCase(String type, String category, String official, Pageable pageable);

    Page<Product> findDistinctByCategoryAllIgnoreCase(String category, Pageable pageable);

    List<Product> findByTitleContainsIgnoreCaseAndOfficial(String title, @Nullable String official, Sort sort);






    Page<Product> findByTypeAndCategoryAndSubcategoryAndOfficialAllIgnoreCase(String type, String category, String subcategory, Integer official, Pageable pageable);

    Page<Product> findByTitleContainsAndTypeAndCategoryAndSubcategoryAndOfficialAllIgnoreCase(String title, String type, String category, String subcategory, Integer official, Pageable pageable);

    List<Product> findByTypeAndSubcategoryAllIgnoreCase(String type, String subcategory);
}
