package com.example.rsupport.domain.notice.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class NoticeCreateRequest {
    @Schema(description = "제목", example = "제목1")
    @NotBlank(message = "제목은 필수 값입니다.")
    private String title;
    @Schema(description = "본문", example = "본문 내용")
    @NotBlank(message = "본문 내용은 필수 값입니다.")
    private String content;
    @Schema(description = "공지사항 시작일")
    private LocalDateTime startDate;
    @Schema(description = "공지사항 종료일")
    private LocalDateTime endDate;
    @Schema(description = "공지사항 첨부파일")
    private List<String> attachments;
}
