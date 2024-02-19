package com.example.rsupport.global.support.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class PageResponse<T> {
    @Schema(description = "전체 카운트", example = "400")
    private Long total;

    @Schema(description = "전체 페이지", example = "15")
    private int totalPage;

    @Schema(description = "현제 페이지", example = "10")
    private int currentPage;

    @Schema(description = "요청 사이즈", example = "10")
    private int size;

    @Schema(description = "데이터")
    private T data;

    public PageResponse(T data, Long total, Pageable page) {
        this.data = data;
        this.total = total;
        this.size = page.getPageSize();
        this.currentPage = page.getPageNumber() + 1;
        this.totalPage = (int) (total % page.getPageSize() == 0 ?
                total / page.getPageSize()
                : (total / page.getPageSize() + 1));
    }
}
