package com.example.rsupport.domain.notice.service.impl;

import com.example.rsupport.domain.notice.controller.request.AttachmentRequest;
import com.example.rsupport.domain.notice.controller.request.NoticeUpdateRequest;
import com.example.rsupport.global.exception.ApiException;
import com.example.rsupport.global.exception.ResponseMessage;
import com.example.rsupport.global.support.file.FileSupport;
import com.example.rsupport.domain.notice.controller.request.NoticeCreateRequest;
import com.example.rsupport.domain.notice.service.NoticeService;
import com.example.rsupport.global.util.json.ObjectMapperHelper;
import com.example.rsupport.model.notice.entity.NoticeAttachmentEntity;
import com.example.rsupport.model.notice.entity.NoticeEntity;
import com.example.rsupport.model.notice.entity.dto.AttachmentWithNoticeDTO;
import com.example.rsupport.model.notice.entity.dto.NoticeDTO;
import com.example.rsupport.model.notice.mapper.NoticeMapper;
import com.example.rsupport.model.notice.repository.NoticeAttachmentRepository;
import com.example.rsupport.model.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeAttachmentRepository noticeAttachmentRepository;
    private final FileSupport fileSupport;
    private final NoticeMapper noticeMapper;

    @Override
    public AttachmentWithNoticeDTO saveNotice(NoticeCreateRequest request) {
        NoticeEntity notice = NoticeEntity.ofRequest(request);
        noticeRepository.save(notice);
        if (CollectionUtils.isNotEmpty(notice.getNoticeAttachment())) {
            noticeAttachmentRepository.saveAll(notice.getNoticeAttachment());
        }
        return noticeMapper.asAttachmentWithNoticeDTO(notice);
    }

    @Override
    public AttachmentWithNoticeDTO updateNotice(Long id, NoticeUpdateRequest request) {
        NoticeEntity notice = noticeRepository.findByIdWithAttachment(id)
                .orElseThrow(() -> new ApiException(ResponseMessage.NOTICE_NOT_FOUND));

        if (CollectionUtils.isNotEmpty(request.getAttachments())) {
            List<Long> deleteAttachmentId = new ArrayList<>();
            List<NoticeAttachmentEntity> addAttachments = new ArrayList<>();
            for (AttachmentRequest attachmentRequest : request.getAttachments()) {
                if (!ObjectUtils.isEmpty(attachmentRequest.getAttachmentId())
                    && attachmentRequest.isDeleteFlag()) {
                    deleteAttachmentId.add(attachmentRequest.getAttachmentId());
                } else if (ObjectUtils.isEmpty(attachmentRequest.getAttachmentId())
                    && StringUtils.hasText(attachmentRequest.getFile())) {
                    addAttachments.add(
                            NoticeAttachmentEntity.ofFile(attachmentRequest.getFile(), notice)
                    );
                }
            }

            notice.deleteAttachment(deleteAttachmentId);
            if (CollectionUtils.isNotEmpty(deleteAttachmentId)) {
                List<NoticeAttachmentEntity> deleteAttachments = noticeAttachmentRepository.findAllById(deleteAttachmentId);
                for (NoticeAttachmentEntity noticeAttachment : deleteAttachments) {
                    fileSupport.deleteFile(noticeAttachment.getFile());
                }
                noticeAttachmentRepository.deleteAll(deleteAttachments);
            }

            notice.addAttachment(addAttachments);

            noticeAttachmentRepository.saveAll(addAttachments);
        }

        notice.updateRequest(request);

        return noticeMapper.asAttachmentWithNoticeDTO(notice);
    }

    @Override
    public void deleteNotice(Long id) {
        NoticeEntity notice = noticeRepository.findByIdWithAttachment(id)
                .orElseThrow(() -> new ApiException(ResponseMessage.NOTICE_NOT_FOUND));
        notice.delete();
    }

    @Override
    public AttachmentWithNoticeDTO getNotice(Long id) {
        NoticeEntity notice = noticeRepository.findByIdWithAttachment(id)
                .orElseThrow(() -> new ApiException(ResponseMessage.NOTICE_NOT_FOUND));
        if (notice.isDeleteFlag()) {
            throw new ApiException(ResponseMessage.NOTICE_NOT_FOUND);
        }
        notice.addViewCount();
        return noticeMapper.asAttachmentWithNoticeDTO(notice);
    }

    @Override
    public Page<NoticeDTO> getNoticeList(PageRequest page) {
        Page<NoticeEntity> notices = noticeRepository.findAllByDeleteFlagOrderByIdDesc(false, page);
        return notices.map(noticeMapper::asDTO);
    }
}
