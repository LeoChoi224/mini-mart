package com.zerobase.minimart.order.entity;

import com.zerobase.minimart.order.customer.entity.PurchaseHistory;
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
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;
    
    private int rating; // 평점 (1-5)
    private String title; // 리뷰 제목
    private String content; // 리뷰 내용
    private String imageUrl; // 리뷰 이미지
    private boolean isVisible; // 공개 여부
    private int helpfulCount; // 도움됨 수
    
    private LocalDateTime regDt;
    private LocalDateTime updateDt;
    
    // 평점 상수
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;
}
