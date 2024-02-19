package com.example.rsupport.model.notice.mapper;

import com.example.rsupport.model.notice.entity.NoticeEntity;
import com.example.rsupport.model.notice.entity.dto.AttachmentWithNoticeDTO;
import com.example.rsupport.model.notice.entity.dto.NoticeDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {


    AttachmentWithNoticeDTO asAttachmentWithNoticeDTO(NoticeEntity notice);

    @IterableMapping(qualifiedByName = "asDTO")
    List<NoticeDTO> asDTOList(List<NoticeEntity> notices);
    @Named("asDTO")
    NoticeDTO asDTO(NoticeEntity entity);
}
