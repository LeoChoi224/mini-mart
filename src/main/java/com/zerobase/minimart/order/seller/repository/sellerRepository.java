package com.zerobase.minimart.order.seller.repository;

import com.zerobase.minimart.order.seller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface sellerRepository extends JpaRepository<Product, Long> {
}