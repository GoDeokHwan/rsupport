package com.example.rsupport.model.notice.entity;

import com.example.rsupport.domain.notice.controller.request.NoticeCreateRequest;
import com.example.rsupport.domain.notice.controller.request.NoticeUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Comment(value = "공지사항")
@Getter
@Entity
@Table(name="notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @Comment("공지사항 첨부파일")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notice")
    private List<NoticeAttachmentEntity> noticeAttachment;
    @Column(length = 50)
    @Comment(value = "제목")
    private String title;
    @Column
    @Comment(value = "내용")
    private String content;
    @Column(name = "start_date")
    @Comment(value = "공지 시작일시")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    @Comment(value = "공지 종료일시")
    private LocalDateTime endDate;
    @Column(name = "view_count")
    @Comment(value = "조회수")
    private Long viewCount;
    @Column(length = 100)
    @Comment(value = "작성자")
    private String writer;
    @Column(name = "delete_flag")
    @Comment(value = "삭제여부")
    private boolean deleteFlag;
    @CreationTimestamp
    @Column(name = "create_date")
    @Comment(value = "등록일시")
    private LocalDateTime createDate;
    @UpdateTimestamp
    @Column(name = "modify_date")
    @Comment(value = "수정일시")
    private LocalDateTime modifyDate;
    @Version
    private int version;

    public static NoticeEntity ofRequest(NoticeCreateRequest request) {
        NoticeEntity instance = new NoticeEntity();
        instance.title = request.getTitle();
        instance.content = request.getContent();
        instance.startDate = request.getStartDate();
        instance.endDate = request.getEndDate();
        instance.viewCount = 0L;
        // TODO 시큐리티 정책로그인 정책에 맞게 자동 대입할 수 있게 변경 필요
        instance.writer = "SYSTEM";

        if (CollectionUtils.isNotEmpty(request.getAttachments())) {
            List<NoticeAttachmentEntity> attachments = new ArrayList<>();
            for (String file : request.getAttachments()) {
                attachments.add(
                        NoticeAttachmentEntity.ofFile(file, instance)
                );
            }
            instance.noticeAttachment = attachments;
        }
        instance.deleteFlag = false;
        return instance;
    }

    public void updateRequest(NoticeUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }

    public void deleteAttachment(List<Long> deleteAttachmentId) {
        this.getNoticeAttachment().removeIf(noticeAttachment -> deleteAttachmentId.contains(noticeAttachment.getId()));
    }

    public void addAttachment(List<NoticeAttachmentEntity> addAttachments) {
        this.getNoticeAttachment().addAll(addAttachments);
    }

    public void delete() {
        this.deleteFlag = true;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }
}
