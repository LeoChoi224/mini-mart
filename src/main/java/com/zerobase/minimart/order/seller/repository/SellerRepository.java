package com.zerobase.minimart.order.seller.repository;

import com.zerobase.minimart.order.seller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(String userId);
}