package com.zerobase.minimart.order.seller.service.impl;

import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.repository.SellerRepository;
import com.zerobase.minimart.order.seller.service.SellerService;
import com.zerobase.minimart.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public void add(@ModelAttribute ProductInput parameter, String userId) {
        Product product = Product.builder()
                .userId(userId)
                .productName(parameter.getProductName())
                .price(parameter.getPrice())
                .description(parameter.getDescription())
                .imageUrl(parameter.getImageUrl())
                .category(parameter.getCategory())
                .stock(parameter.getStock())
                .status(parameter.getStatus())
                .regDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .build();

        sellerRepository.save(product);
    }

    @Override
    public List<Product> getProductsByUser(String userId) {
        return sellerRepository.findByUserId(userId);
    }


    @Override
    public void updateStatus(Long id, String status) {
        Product product = sellerRepository.findById(id).orElseThrow();
        product.setStatus(status);
        product.setUpdateDt(LocalDateTime.now());
        sellerRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public Product getProduct(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(ProductInput parameter, String userId) {

        Optional<Product> optionalProduct = sellerRepository.findById(parameter.getId());
        if (optionalProduct.isEmpty()) {
            return false;
        }

        Product product = optionalProduct.get();

        if (!product.getUserId().equals(userId)) {
            return false;
        }

        product.setProductName(parameter.getProductName());
        product.setPrice(parameter.getPrice());
        product.setCategory(parameter.getCategory());
        product.setStock(parameter.getStock());
        product.setStatus(parameter.getStatus());
        product.setDescription(parameter.getDescription());
        product.setUpdateDt(LocalDateTime.now());

        sellerRepository.save(product);

        return true;
    }

    @Override
    public void doSomething(User user) {
        System.out.println("판매자 ID: " + user.getUserId());
    }


}