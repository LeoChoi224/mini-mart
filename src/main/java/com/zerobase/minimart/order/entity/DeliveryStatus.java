package com.zerobase.minimart.order.entity;

import com.zerobase.minimart.order.customer.entity.PurchaseHistory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;
    
    private String status; // 주문상태: 주문완료, 결제완료, 배송준비중, 배송중, 배송완료, 구매확정, 취소, 환불
    private String trackingNumber; // 운송장 번호
    private String deliveryCompany; // 택배사
    private String deliveryAddress; // 배송 주소
    private String recipientName; // 수령인
    private String recipientPhone; // 수령인 연락처
    
    private LocalDateTime statusDate; // 상태 변경일
    private String memo; // 메모
    
    private LocalDateTime regDt;
    private LocalDateTime updateDt;
    
    // 상태 상수
    public static final String ORDER_COMPLETED = "주문완료";
    public static final String PAYMENT_COMPLETED = "결제완료";
    public static final String PREPARING = "배송준비중";
    public static final String SHIPPING = "배송중";
    public static final String DELIVERED = "배송완료";
    public static final String CONFIRMED = "구매확정";
    public static final String CANCELLED = "취소";
    public static final String REFUNDED = "환불";
}
