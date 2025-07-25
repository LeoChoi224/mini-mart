package com.zerobase.minimart.order.customer.service;

import com.zerobase.minimart.order.customer.entity.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryService {
    List<PurchaseHistory> getPurchaseHistory(Long userId);
}