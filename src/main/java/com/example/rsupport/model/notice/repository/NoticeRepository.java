package com.example.rsupport.model.notice.repository;

import com.example.rsupport.model.notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    @Query(value = "select t1 from NoticeEntity t1 left join fetch t1.noticeAttachment where t1.id = :id")
    Optional<NoticeEntity> findByIdWithAttachment(@Param("id") Long id);

    Page<NoticeEntity> findAllByDeleteFlagOrderByIdDesc(boolean deleteFlag, Pageable pageable);
}
