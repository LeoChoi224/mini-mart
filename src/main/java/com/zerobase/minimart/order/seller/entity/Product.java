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

    private LocalDateTime regDt;
    private LocalDateTime updateDt;
}