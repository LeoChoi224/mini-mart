package com.zerobase.minimart.order.seller.service.impl;

import com.zerobase.minimart.order.seller.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.repository.SellerRepository;
import com.zerobase.minimart.order.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public void add(@ModelAttribute ProductInput parameter, String userId) {
        Product product = Product.builder()
                .productName(parameter.getProductName())
                .price(parameter.getPrice())
                .description(parameter.getDescription())
                .userId(userId)
                .regDt(LocalDateTime.now())
                .build();

        sellerRepository.save(product);
    }

    @Override
    public List<Product> getProductsByUser(String userId) {
        return sellerRepository.findByUserId(userId);
    }


}