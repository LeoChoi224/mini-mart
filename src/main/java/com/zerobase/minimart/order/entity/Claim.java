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
public class Claim {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    private String claimType; // 클레임 유형: REFUND(환불), EXCHANGE(교환), RETURN(반품)
    private String reason; // 사유
    private String description; // 상세 설명
    private String status; // 상태: PENDING(대기), APPROVED(승인), REJECTED(거절), COMPLETED(완료)
    private String adminMemo; // 관리자 메모
    
    private int refundAmount; // 환불 금액
    private String refundMethod; // 환불 방법: CARD(카드), BANK(계좌이체), CASH(현금)
    private String refundAccount; // 환불 계좌 정보
    
    private LocalDateTime claimDate; // 클레임 신청일
    private LocalDateTime processDate; // 처리일
    private LocalDateTime completeDate; // 완료일
    
    private LocalDateTime regDt;
    private LocalDateTime updateDt;
    
    // 클레임 유형 상수
    public static final String REFUND = "REFUND";
    public static final String EXCHANGE = "EXCHANGE";
    public static final String RETURN = "RETURN";
    
    // 상태 상수
    public static final String PENDING = "PENDING";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";
    public static final String COMPLETED = "COMPLETED";
}
