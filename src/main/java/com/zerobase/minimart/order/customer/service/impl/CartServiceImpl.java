package com.zerobase.minimart.order.customer.service.impl;

import com.zerobase.minimart.order.customer.dto.CartItemDto;
import com.zerobase.minimart.order.customer.entity.CartItem;
import com.zerobase.minimart.order.customer.entity.PurchaseHistory;
import com.zerobase.minimart.order.customer.repository.CartRepository;
import com.zerobase.minimart.order.customer.repository.PurchaseHistoryRepository;
import com.zerobase.minimart.order.customer.service.CartService;
import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.order.seller.repository.SellerRepository;
import com.zerobase.minimart.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final SellerRepository sellerRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;

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
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .build();
            cartRepository.save(newItem);
        }
    }

    @Override
    public List<CartItemDto> getCartItems(User user) {
        List<CartItem> items = cartRepository.findByUser(user);

        return items.stream()
                .map(item -> CartItemDto.builder()
                        .id(item.getId())
                        .productName(item.getProduct().getProductName())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .imageUrl(item.getProduct().getImageUrl())
                        .build())
                .collect(Collectors.toList());
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

    @Override
    public void checkout(Long userId, List<Long> cartItemIds) {
        // 1. 선택된 장바구니 항목 가져오기
        List<CartItem> items = cartRepository.findAllById(cartItemIds);

        // 2. 구매 이력 저장 (예: PurchaseHistory 엔티티 필요)
        for (CartItem item : items) {
            PurchaseHistory history = PurchaseHistory.builder()
                    .userId(userId)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .price(item.getProduct().getPrice())
                    .purchaseDt(LocalDateTime.now())
                    .build();
            purchaseHistoryRepository.save(history);
        }

        // 3. 장바구니에서 제거
        cartRepository.deleteAllById(cartItemIds);
    }
}