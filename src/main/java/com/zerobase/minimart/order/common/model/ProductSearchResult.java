package com.zerobase.minimart.order.common.model;

import lombok.Data;

@Data
public class ProductSearchResult {
    private Long id;
    private String productName;
    private int price;
    private String category;
}