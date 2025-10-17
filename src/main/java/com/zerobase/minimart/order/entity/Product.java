package com.zerobase.minimart.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;  // 등록자 이메일

    private String productName;
    private int price;
    private String description;

    private String category;
    private int stock;
    private String status; // 판매 상태 (예: 판매중, 품절 등)
    private String imageUrl;

    // 새로 추가된 필드들
    private int originalPrice; // 원가 (할인 전 가격)
    private int discountRate; // 할인율 (0-100)
    private int shippingFee; // 배송비
    private int shippingDays; // 배송 소요일
    private String mainImageUrl; // 대표 이미지 URL
    private boolean isNew; // 신상품 여부
    private boolean isBest; // 베스트 상품 여부
    private boolean isFeatured; // 추천 상품 여부
    private String productOptions; // 상품 옵션 (JSON 형태로 저장)
    private int viewCount; // 조회수
    private int purchaseCount; // 구매수

    private LocalDateTime regDt;
    private LocalDateTime updateDt;
}