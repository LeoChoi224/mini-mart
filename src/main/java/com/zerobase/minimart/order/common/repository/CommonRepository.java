package com.zerobase.minimart.order.common.repository;

import com.zerobase.minimart.order.common.model.ProductSearchResult;
import com.zerobase.minimart.order.seller.entity.Product;
import com.zerobase.minimart.order.common.model.ProductSearchInput;

import java.util.List;

public interface CommonRepository {

    List<Product> search(ProductSearchInput input);

    Product findById(Long id);

    List<ProductSearchResult> searchByKeywordAndCategory(String keyword, String category);
}