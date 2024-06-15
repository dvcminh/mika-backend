package com.mika.mikabackend.dto.page;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PageLink {

    protected static final String DEFAULT_SORT_PROPERTY = "createdAt";
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, DEFAULT_SORT_PROPERTY);

    private final int page;
    private final int pageSize;
    private final String searchText;
    private final SortOrder sortOrder;

    public PageLink(PageLink pageLink) {
        this.page = pageLink.getPage();
        this.pageSize = pageLink.getPageSize();
        this.searchText = pageLink.getSearchText();
        this.sortOrder = pageLink.getSortOrder();
    }

    public PageLink(int page, int pageSize, String searchText, SortOrder sortOrder) {
        this.page = page;
        this.pageSize = pageSize;
        this.searchText = searchText;
        this.sortOrder = sortOrder;
    }

    public PageLink(int page, int pageSize) {
        this(page, pageSize, null, null);
    }

    public PageLink(int page, int pageSize, String searchText) {
        this(page, pageSize, searchText, null);
    }

    public Sort toSort(SortOrder sortOrder) {
        if (sortOrder == null) {
            return DEFAULT_SORT;
        }
        return Sort.by(Sort.Direction.fromString(sortOrder.getDirection().toString()), sortOrder.getProperty());
    }
}
