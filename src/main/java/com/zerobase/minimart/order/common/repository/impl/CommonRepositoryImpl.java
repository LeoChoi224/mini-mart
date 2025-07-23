package com.zerobase.minimart.order.common.repository.impl;

import com.zerobase.minimart.order.common.model.ProductSearchInput;
import com.zerobase.minimart.order.common.model.ProductSearchResult;
import com.zerobase.minimart.order.common.repository.CommonRepository;
import com.zerobase.minimart.order.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommonRepositoryImpl implements CommonRepository {

    private final EntityManager entityManager;

    // 1. 전체 Product 객체 반환
    @Override
    public List<Product> search(ProductSearchInput input) {
        StringBuilder sb = new StringBuilder("SELECT p FROM Product p WHERE 1=1");

        if (input.getKeyword() != null && !input.getKeyword().isBlank()) {
            sb.append(" AND p.productName LIKE :keyword");
        }
        if (input.getCategory() != null && !input.getCategory().isBlank()) {
            sb.append(" AND p.category = :category");
        }

        TypedQuery<Product> query = entityManager.createQuery(sb.toString(), Product.class);

        if (input.getKeyword() != null && !input.getKeyword().isBlank()) {
            query.setParameter("keyword", "%" + input.getKeyword() + "%");
        }
        if (input.getCategory() != null && !input.getCategory().isBlank()) {
            query.setParameter("category", input.getCategory());
        }

        return query.getResultList();
    }

    // 2. 상품 상세 조회
    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    // 3. DTO 기반 검색 결과 (향후 최적화용, 현재 사용 안함)
    @Override
    public List<ProductSearchResult> searchByKeywordAndCategory(String keyword, String category) {
        StringBuilder sb = new StringBuilder(
                "SELECT new com.zerobase.minimart.order.common.model.ProductSearchResult(" +
                        "p.id, p.productName, p.price, p.category) FROM Product p WHERE 1=1");

        if (keyword != null && !keyword.isBlank()) {
            sb.append(" AND p.productName LIKE :keyword");
        }
        if (category != null && !category.isBlank()) {
            sb.append(" AND p.category = :category");
        }

        TypedQuery<ProductSearchResult> query = entityManager.createQuery(sb.toString(), ProductSearchResult.class);

        if (keyword != null && !keyword.isBlank()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        return query.getResultList();
    }
}