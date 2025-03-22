package com.hanzai.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Generic pagination result wrapper.
 * Contains current page data and pagination metadata.
 *
 * @param <T> type of the items in the page
 */
@Getter
@Setter
public class PageResult<T> {

    /**
     * Total number of records available (across all pages).
     */
    private long total;

    /**
     * Current page number (1-based index).
     */
    private int pageNum;

    /**
     * Size of each page (number of items per page).
     */
    private int pageSize;

    /**
     * List of data items for the current page.
     */
    private List<T> items;

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private PageResult(List<T> items, int pageNum, int pageSize, long total) {
        this.items = items != null ? items : Collections.emptyList();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * Create a PageResult with data and custom page number and size.
     *
     * @param items    list of items in the page
     * @param pageNum  current page number
     * @param pageSize size of each page
     * @param <T>      type of the data
     * @return PageResult with provided pagination metadata
     */
    public static <T> PageResult<T> of(List<T> items, int pageNum, int pageSize) {
        int totalPages = (int) Math.ceil((double) items.size() / pageSize);

        if (totalPages == 0) {
            totalPages = 1;
        }

        if (pageNum > totalPages) {
            pageNum = totalPages;
        }

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, items.size());

        List<T> pageItems = items.subList(fromIndex, toIndex);
        return new PageResult<>(pageItems, pageNum, pageSize, items.size());
    }

    /**
     * Create a PageResult with data and default page number and size.
     *
     * @param items list of items in the page
     * @param <T>   type of the data
     * @return PageResult with default pagination metadata
     */
    public static <T> PageResult<T> of(List<T> items) {
        return of(items, DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
    }

}
