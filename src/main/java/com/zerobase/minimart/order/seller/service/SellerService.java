package com.zerobase.minimart.order.seller.service;

import com.zerobase.minimart.order.seller.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;

import java.security.Principal;
import java.util.List;

public interface SellerService {

    /**
     * 상품 등록
     */
    void add(ProductInput parameter, String userId);

    /**
     * 나의 판매 내역 보기
     */
    List<Product> getProductsByUser(String userId);
}