package com.zerobase.minimart.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    
    private int page;           // 현재 페이지
    private int size;           // 페이지 크기
    private long totalElements; // 전체 요소 수
    private int totalPages;     // 전체 페이지 수
    private boolean hasNext;    // 다음 페이지 존재 여부
    private boolean hasPrevious; // 이전 페이지 존재 여부
    private boolean isFirst;    // 첫 페이지 여부
    private boolean isLast;     // 마지막 페이지 여부
    
    public static PageInfo of(int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return PageInfo.builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .isFirst(page == 0)
                .isLast(page == totalPages - 1)
                .build();
    }
}
