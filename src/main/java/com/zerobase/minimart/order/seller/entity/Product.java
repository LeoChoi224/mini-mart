package com.zerobase.minimart.order.seller.entity;

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


    private LocalDateTime regDt;
    private LocalDateTime updateDt;
}