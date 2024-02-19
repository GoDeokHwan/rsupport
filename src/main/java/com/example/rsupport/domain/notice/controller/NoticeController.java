package com.example.rsupport.domain.notice.controller;

import com.example.rsupport.domain.notice.controller.request.NoticeCreateRequest;
import com.example.rsupport.domain.notice.controller.request.NoticeUpdateRequest;
import com.example.rsupport.domain.notice.service.NoticeService;
import com.example.rsupport.global.support.page.PageResponse;
import com.example.rsupport.model.notice.entity.dto.AttachmentWithNoticeDTO;
import com.example.rsupport.model.notice.entity.dto.NoticeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지사항 Controller", description = "공지사항 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 저장 API", description = "공지사항 저장 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping
    public ResponseEntity<AttachmentWithNoticeDTO> saveNotice(
            @Parameter(description = "공지사항 저장 정보") @Valid @RequestBody NoticeCreateRequest request
    ) {
        return ResponseEntity.ok(noticeService.saveNotice(request));
    }

    @Operation(summary = "공지사항 수정 API", description = "공지사항 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AttachmentWithNoticeDTO> updateNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Long id
            , @Parameter(description = "공지사항 수정 정보") @Valid @RequestBody NoticeUpdateRequest request
    ) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    @Operation(summary = "공지사항 삭제 API", description = "공지사항 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Long id
    ) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "공지사항 상세조회 API", description = "공지사항 상세조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AttachmentWithNoticeDTO> getNotice(
            @Parameter(description = "공지사항 ID") @PathVariable Long id
    ) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    @Operation(summary = "공지사항 조회 API", description = "공지사항 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<PageResponse<List<NoticeDTO>>> getNoticeList(
            @Parameter(description = "사이즈") @RequestParam(defaultValue = "1") int size
            , @Parameter(description = "페이지") @RequestParam(defaultValue = "20") int page
    ) {
        Page<NoticeDTO> response = noticeService.getNoticeList(PageRequest.of((page-1), size));
        return ResponseEntity.ok(
                new PageResponse<List<NoticeDTO>>(
                        response.getContent()
                    , response.getTotalElements()
                    , response.getPageable()
                )
        );
    }

}
