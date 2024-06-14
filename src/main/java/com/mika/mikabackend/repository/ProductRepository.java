package com.mika.mikabackend.repository;

import com.mika.mikabackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findDistinctByTypeAndCategoryOrderByPriceAsc(String type, String category, Pageable pageable);

    Page<Product> findDistinctByTypeAndCategoryAndOfficialAllIgnoreCase(String type, String category, String official, Pageable pageable);

    Page<Product> findDistinctByCategoryAllIgnoreCase(String category, Pageable pageable);

    List<Product> findByTitleContainsIgnoreCaseAndOfficial(String title, @Nullable String official, Sort sort);
}
