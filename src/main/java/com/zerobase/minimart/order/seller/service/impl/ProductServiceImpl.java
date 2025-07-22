package com.zerobase.minimart.order.seller.service.impl;

import com.zerobase.minimart.order.seller.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.repository.sellerRepository;
import com.zerobase.minimart.order.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements SellerService {

    private final sellerRepository sellerRepository;

    @Override
    public void add(ProductInput parameter) {
        Product product = Product.builder()
                .productName(parameter.getProductName())
                .price(parameter.getPrice())
                .description(parameter.getDescription())
                .regDt(LocalDateTime.now())
                .build();

        sellerRepository.save(product);
    }
}