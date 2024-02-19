package com.example.rsupport.domain.notice.service;

import com.example.rsupport.domain.notice.controller.request.NoticeCreateRequest;
import com.example.rsupport.domain.notice.controller.request.NoticeUpdateRequest;
import com.example.rsupport.global.config.redis.RedisCacheName;
import com.example.rsupport.model.notice.entity.dto.AttachmentWithNoticeDTO;
import com.example.rsupport.model.notice.entity.dto.NoticeDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface NoticeService {
    AttachmentWithNoticeDTO saveNotice(NoticeCreateRequest request);

    @CacheEvict(value = RedisCacheName.NOTICE, key = "#id", cacheManager = "redisCacheManager")
    AttachmentWithNoticeDTO updateNotice(Long id, NoticeUpdateRequest request);

    @CacheEvict(value = RedisCacheName.NOTICE, key = "#id", cacheManager = "redisCacheManager")
    void deleteNotice(Long id);

    @Cacheable(value = RedisCacheName.NOTICE, key = "#id", cacheManager = "redisCacheManager")
    AttachmentWithNoticeDTO getNotice(Long id);

    Page<NoticeDTO> getNoticeList(PageRequest of);
}
