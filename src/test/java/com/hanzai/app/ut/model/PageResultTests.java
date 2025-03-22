package com.hanzai.app.ut.model;

import com.hanzai.app.model.PageResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageResultTests {

    private List<Integer> generateTestData(int totalItems) {
        return IntStream.range(1, totalItems + 1).boxed().toList();
    }

    @Test
    public void testWithDefaultPagination() {
        List<Integer> allItems = generateTestData(25);
        PageResult<Integer> pageResult = PageResult.of(allItems);
        assertEquals(10, pageResult.getItems().size());
        assertEquals(1, pageResult.getPageNum());
        assertEquals(10, pageResult.getPageSize());
        assertEquals(25, pageResult.getTotal());
    }

    @Test
    public void testWithCustomPagination() {
        List<Integer> allItems = generateTestData(25);
        PageResult<Integer> pageResult = PageResult.of(allItems, 2, 5);
        assertEquals(5, pageResult.getItems().size());
        assertEquals(2, pageResult.getPageNum());
        assertEquals(5, pageResult.getPageSize());
        assertEquals(25, pageResult.getTotal());
    }

    @Test
    public void testWithPageNumExceeds() {
        List<Integer> allItems = generateTestData(25);
        PageResult<Integer> pageResult = PageResult.of(allItems, 10, 5);
        assertEquals(5, pageResult.getItems().size());
        assertEquals(5, pageResult.getPageNum());
        assertEquals(5, pageResult.getPageSize());
        assertEquals(25, pageResult.getTotal());
    }

    @Test
    public void testWithPageSizeExceeds() {
        List<Integer> allItems = generateTestData(5);
        PageResult<Integer> pageResult = PageResult.of(allItems, 1, 10);
        assertEquals(5, pageResult.getItems().size());
        assertEquals(1, pageResult.getPageNum());
        assertEquals(10, pageResult.getPageSize());
        assertEquals(5, pageResult.getTotal());
    }

    @Test
    public void testWithEmptyList() {
        List<Integer> emptyList = generateTestData(0);
        PageResult<Integer> pageResult = PageResult.of(emptyList, 1, 10);
        assertEquals(0, pageResult.getItems().size());
        assertEquals(1, pageResult.getPageNum());
        assertEquals(10, pageResult.getPageSize());
        assertEquals(0, pageResult.getTotal());
    }
}
