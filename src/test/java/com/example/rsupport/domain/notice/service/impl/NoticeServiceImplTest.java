package com.example.rsupport.domain.notice.service.impl;

import com.example.rsupport.domain.notice.controller.request.NoticeCreateRequest;
import com.example.rsupport.domain.notice.controller.request.NoticeUpdateRequest;
import com.example.rsupport.domain.notice.service.NoticeService;
import com.example.rsupport.global.exception.ApiException;
import com.example.rsupport.global.exception.ResponseMessage;
import com.example.rsupport.model.notice.entity.dto.AttachmentWithNoticeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class NoticeServiceImplTest {
    @Autowired
    private NoticeService noticeService;

    @Test
    void 공지사항_생성() {
        // given
        String title = "제목";
        String content = "내용내용";
        NoticeCreateRequest request = new NoticeCreateRequest(
                title
                , content
                , null
                , null
                , null
        );

        // when
        AttachmentWithNoticeDTO attachmentWithNoticeDTO = noticeService.saveNotice(request);

        // then
        assertEquals(attachmentWithNoticeDTO.getTitle(), title);
        assertEquals(attachmentWithNoticeDTO.getContent(), content);
    }

    @Test
    void 공지사항_수정() {
        // given
        String updateTitle = "수정 제목";
        String updateContent = "수정 내용내용";
        NoticeUpdateRequest updateRequest = new NoticeUpdateRequest(
                updateTitle
                , updateContent
                , LocalDateTime.now().minusDays(1)
                , LocalDateTime.now().plusDays(10)
                , null
        );

        // when
        AttachmentWithNoticeDTO update = noticeService.updateNotice(1L, updateRequest);

        // then
        assertEquals(update.getTitle(), updateTitle);
        assertEquals(update.getContent(), updateContent);
    }

    @Test
    void 공지사항_수정_항목못찾음_오류() {
        // given
        String updateTitle = "수정 제목";
        String updateContent = "수정 내용내용";
        NoticeUpdateRequest updateRequest = new NoticeUpdateRequest(
                updateTitle
                , updateContent
                , LocalDateTime.now().minusDays(1)
                , LocalDateTime.now().plusDays(10)
                , null
        );

        // when
        ApiException exception= assertThrows(ApiException.class, () -> {
            noticeService.updateNotice(-1L, updateRequest);
        });
        // then
        assertEquals(exception.getMessage(), ResponseMessage.NOTICE_NOT_FOUND.getMessage());
    }

    @Test
    void 공지사항_삭제() {
        // given
        Long id = 2L;

        // when
        noticeService.deleteNotice(id);
        ApiException exception= assertThrows(ApiException.class, () -> {
           noticeService.getNotice(id);
        });
        // then
        assertEquals(exception.getMessage(), ResponseMessage.NOTICE_NOT_FOUND.getMessage());
    }

}