package com.zerobase.minimart.order.seller.model;

import lombok.Data;

@Data
public class ProductInput {
    private String productName;
    private int price;
    private String description;
}