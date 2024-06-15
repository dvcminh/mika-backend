package com.mika.mikabackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private String price;
    private String link_item;
    private String image_url;
    private String discount_percent_list;
    private String countReviews;
    private String type;
    private String category;
    private String subcategory;
    private Integer official;
}
