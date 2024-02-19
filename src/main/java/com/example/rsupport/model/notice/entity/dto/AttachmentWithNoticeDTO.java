package com.example.rsupport.model.notice.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AttachmentWithNoticeDTO extends NoticeDTO {
    @Schema(description = "첨부파일")
    private List<NoticeAttachmentDTO> noticeAttachment;

    public AttachmentWithNoticeDTO(Long id, String title, String content, LocalDateTime startDate, LocalDateTime endDate, Long viewCount, String writer, LocalDateTime createDate, LocalDateTime modifyDate, List<NoticeAttachmentDTO> noticeAttachment) {
        super(id, title, content, startDate, endDate, viewCount, writer, createDate, modifyDate);
        this.noticeAttachment = noticeAttachment;
    }
}
