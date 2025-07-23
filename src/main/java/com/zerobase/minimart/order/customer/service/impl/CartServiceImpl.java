package com.zerobase.minimart.order.customer.service.impl;

import com.zerobase.minimart.order.customer.entity.CartItem;
import com.zerobase.minimart.order.customer.repository.CartRepository;
import com.zerobase.minimart.order.customer.service.CartService;
import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.order.seller.repository.SellerRepository;
import com.zerobase.minimart.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final SellerRepository sellerRepository;

    @Override
    public void addToCart(User user, Long productId, int quantity) {
        CartItem existing = cartRepository.findByUserAndProductId(user, productId).orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartRepository.save(existing);
        } else {
            Product product = sellerRepository.findById(productId).orElseThrow();
            CartItem newItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(quantity)
                    .regDt(LocalDateTime.now())
                    .build();
            cartRepository.save(newItem);
        }
    }

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void removeFromCart(User user, Long cartItemId) {
        CartItem item = cartRepository.findByIdAndUser(cartItemId, user)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목이 존재하지 않음"));
        cartRepository.delete(item);
    }

    @Override
    public void updateQuantity(User user, Long cartItemId, int quantity) {
        CartItem item = cartRepository.findByIdAndUser(cartItemId, user)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목이 존재하지 않음"));
        item.setQuantity(quantity);
        cartRepository.save(item);
    }
}