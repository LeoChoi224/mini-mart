package com.zerobase.minimart.order.customer.service;

import com.zerobase.minimart.order.customer.dto.CartItemDto;
import com.zerobase.minimart.user.entity.User;

import java.util.List;

public interface CartService {

    /**
     * 장바구니 담기
     */
    void addToCart(User user, Long productId, int quantity);

    /**
     * 장바구니 조회
     */
    List<CartItemDto> getCartItems(User user);

    /**
     * 장바구니에서 삭제
     */
    void removeFromCart(User user, Long cartItemId);

    /**
     * 장바구니 수정
     */
    void updateQuantity(User user, Long cartItemId, int quantity);

    /**
     * 장바구니 체크아웃
     */
    void checkout(Long userId, List<Long> cartItemIds);
}