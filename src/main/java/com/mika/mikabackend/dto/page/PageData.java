package com.mika.mikabackend.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class PageData<T> {
    final private List<T> data;
    final private Integer totalPages;
    final private Long totalElements;
    final private Boolean hasNext;
    final private boolean success = true;
    final private String message;
    final HttpStatus status = HttpStatus.OK;

    public PageData(Page<T> page, String message) {
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.hasNext = page.hasNext();
        this.message = message;
    }

    public PageData() {
        this.data = null;
        this.totalPages = null;
        this.totalElements = null;
        this.hasNext = null;
        this.message = null;
    }
}
