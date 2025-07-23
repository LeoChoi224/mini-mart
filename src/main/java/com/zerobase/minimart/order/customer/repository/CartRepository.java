package com.zerobase.minimart.order.customer.repository;

import com.zerobase.minimart.order.customer.entity.CartItem;
import com.zerobase.minimart.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    // 사용자별 장바구니 전체 조회
    List<CartItem> findByUser(User user);

    // 사용자 + 상품 중복 여부 확인
    Optional<CartItem> findByUserAndProductId(User user, Long productId);

    // 사용자 장바구니 개별 아이템 삭제 또는 수정 용
    Optional<CartItem> findByIdAndUser(Long id, User user);
}