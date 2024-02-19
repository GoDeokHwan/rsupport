package com.example.rsupport.model.notice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Comment(value = "공지사항 첨부파일")
@Getter
@Entity
@Table(name="notice_attachment")
public class NoticeAttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    @Comment(value = "공지사항")
    private NoticeEntity notice;
    @Column(length = 500)
    @Comment(value = "파일명")
    private String fileName;
    @Column(length = 1000)
    @Comment(value = "파일")
    private String file;
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

    public static NoticeAttachmentEntity ofFile(String file, NoticeEntity notice) {
        NoticeAttachmentEntity instance = new NoticeAttachmentEntity();
        if (StringUtils.hasText(file)) {
            String[] f = file.split("/");
            instance.fileName = f[f.length - 1];
        }
        instance.file = file;
        instance.notice = notice;
        return instance;
    }

}
