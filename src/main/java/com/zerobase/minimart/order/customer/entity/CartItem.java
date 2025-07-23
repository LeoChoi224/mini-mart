package com.zerobase.minimart.order.customer.entity;

import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관 상품
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    // 장바구니 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private int quantity; // 수량
    private LocalDateTime regDt; // 담은 시간
}