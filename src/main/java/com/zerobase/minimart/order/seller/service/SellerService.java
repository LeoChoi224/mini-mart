package com.zerobase.minimart.order.seller.service;

import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.user.entity.User;

import java.util.List;

public interface SellerService {

    /**
     * 상품 등록
     */
    void add(ProductInput parameter, String userId);

    /**
     * 나의 판매 내역 보기
     */
    List<Product> getProductsByUser(String userId);

    /**
     * 판매 상태 변경
     */
    void updateStatus(Long id, String status);

    /**
     * 상품 삭제
     */
    void deleteProduct(Long id);

    /**
     * 상품 상세 정보
     */
    Product getProduct(Long id);

    /**
     * 상품 수정
     */
    boolean update(ProductInput parameter, String userId);

    /**
     * 인증된 사용자 객체를 받아 필요한 로직을 수행합니다.
     */
    void doSomething(User user);
}
