package com.zerobase.minimart.order.common.model;

import lombok.Data;

@Data
public class ProductSearchInput {

    private String keyword;
    private String category;

}