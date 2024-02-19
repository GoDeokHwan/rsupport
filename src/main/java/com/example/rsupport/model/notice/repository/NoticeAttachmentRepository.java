package com.example.rsupport.model.notice.repository;

import com.example.rsupport.model.notice.entity.NoticeAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachmentEntity, Long> {
}
