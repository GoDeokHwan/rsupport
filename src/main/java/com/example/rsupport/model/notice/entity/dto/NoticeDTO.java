package com.example.rsupport.model.notice.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeDTO {
    @Schema(description = "공지사항ID", example = "1")
    private Long id;
    @Schema(description = "제목", example = "제목")
    private String title;
    @Schema(description = "본문", example = "본문 내용!")
    private String content;
    @Schema(description = "공지사항 시작일")
    private LocalDateTime startDate;
    @Schema(description = "공지사항 종료일")
    private LocalDateTime endDate;
    @Schema(description = "조회수", example = "120")
    private Long viewCount;
    @Schema(description = "작성자", example = "SYSTEM")
    private String writer;
    @Schema(description = "등록일")
    private LocalDateTime createDate;
    @Schema(description = "수정일")
    private LocalDateTime modifyDate;
}
