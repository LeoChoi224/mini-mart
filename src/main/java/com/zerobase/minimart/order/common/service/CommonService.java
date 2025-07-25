package com.zerobase.minimart.order.common.service;

import com.zerobase.minimart.order.common.model.ProductSearchInput;
import com.zerobase.minimart.order.common.model.ProductSearchResult;
import com.zerobase.minimart.order.entity.Product;

import java.util.List;

public interface CommonService {

    /**
     * 상품 검색 기능
     */
    List<Product> searchProducts(ProductSearchInput input);

    /**
     * 상세 페이지 - 공용
     */
    Product getProductDetail(Long id);

    /**
     * 검색 페이지 결과 반환
     */
    List<ProductSearchResult> searchPage(ProductSearchInput input);
}