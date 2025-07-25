package com.zerobase.minimart.order.customer.repository;

import com.zerobase.minimart.order.customer.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    List<PurchaseHistory> findByUserId(Long userId);
}