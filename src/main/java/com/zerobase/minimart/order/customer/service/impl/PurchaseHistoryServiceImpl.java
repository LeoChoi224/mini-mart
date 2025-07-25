package com.zerobase.minimart.order.customer.service.impl;

import com.zerobase.minimart.order.customer.entity.PurchaseHistory;
import com.zerobase.minimart.order.customer.repository.PurchaseHistoryRepository;
import com.zerobase.minimart.order.customer.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    @Override
    public List<PurchaseHistory> getPurchaseHistory(Long userId) {
        return purchaseHistoryRepository.findByUserId(userId);
    }
}