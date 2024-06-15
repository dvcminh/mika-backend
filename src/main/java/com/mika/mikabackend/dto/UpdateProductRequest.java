package com.mika.mikabackend.dto;

import lombok.Data;

@Data
public class UpdateProductRequest {
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
