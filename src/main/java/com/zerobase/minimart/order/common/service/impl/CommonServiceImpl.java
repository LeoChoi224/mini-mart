package com.zerobase.minimart.order.common.service.impl;

import com.zerobase.minimart.order.common.model.ProductSearchInput;
import com.zerobase.minimart.order.common.model.ProductSearchResult;
import com.zerobase.minimart.order.common.repository.CommonRepository;
import com.zerobase.minimart.order.common.service.CommonService;
import com.zerobase.minimart.order.seller.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommonServiceImpl implements CommonService {

    private final CommonRepository commonRepository;

    @Override
    public List<Product> searchProducts(ProductSearchInput input) {
        return commonRepository.search(input);
    }

    @Override
    public Product getProductDetail(Long id) {
        return commonRepository.findById(id);
    }

    @Override
    public List<ProductSearchResult> searchPage(ProductSearchInput input) {
        return commonRepository.searchByKeywordAndCategory(input.getKeyword(), input.getCategory());
    }

}