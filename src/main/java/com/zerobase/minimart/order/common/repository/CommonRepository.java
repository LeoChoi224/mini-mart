package com.zerobase.minimart.order.common.repository;

import com.zerobase.minimart.order.common.model.ProductSearchResult;
import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.order.common.model.ProductSearchInput;

import java.util.List;

public interface CommonRepository {

    /**
     * 상품 목록 검색
     */
    List<Product> search(ProductSearchInput input);

    /**
     * 상품 id 로 상품 조회
     */
    Product findById(Long id);

    /**
     * 결과값 반환
     */
    List<ProductSearchResult> searchByKeywordAndCategory(String keyword, String category);
}