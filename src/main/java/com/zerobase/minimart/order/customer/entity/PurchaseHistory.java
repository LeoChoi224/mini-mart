package com.zerobase.minimart.order.customer.entity;

import com.zerobase.minimart.order.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    private Product product;

    private int quantity;
    private int price;
    private LocalDateTime purchaseDt;
}