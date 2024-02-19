package com.example.rsupport.model.notice.entity.dto;

import com.example.rsupport.model.notice.entity.NoticeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeAttachmentDTO {
    @Schema(description = "공지사항 첨부파일ID", example = "1")
    private Long id;
    @Schema(description = "파일명", example = "file.jpg")
    private String fileName;
    @Schema(description = "파일", example = "./attach/132415235/file.jpg")
    private String file;
    @Schema(description = "등록일")
    private LocalDateTime createDate;
    @Schema(description = "수정일")
    private LocalDateTime modifyDate;
}
