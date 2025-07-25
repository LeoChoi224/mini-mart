package com.zerobase.minimart.order.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Long id;
    private String productName;
    private int price;
    private int quantity;

    private long totalPrice;
    private String imageUrl;
}