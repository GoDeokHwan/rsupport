package com.example.rsupport.domain.notice.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttachmentRequest {
    @Schema(description = "첨부파일ID", example = "1")
    private Long attachmentId;
    @Schema(description = "삭제여부", example = "false")
    private boolean deleteFlag;
    @Schema(description = "파일정보", example = "/attach/123123040234/file.jpg")
    private String file;
}
