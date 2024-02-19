package com.example.rsupport.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS(HttpStatus.OK, "성공")
    , SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "관리자에게 문의해주세요.")

    // FILE
    , FILE_FORMATTER_EXCEPTION(HttpStatus.BAD_REQUEST,"[%s] 파일명에 잘못된 문자가 포함되어 있습니다.")
    , FILE_NOT_SAVE_EXCEPTION(HttpStatus.BAD_REQUEST, "[%s] 파일을 저장할 수 없습니다.")
    , FILE_FOLDER_MAKE_EXCEPTION(HttpStatus.BAD_REQUEST, "폴더를 생성할 수 없습니다.")
    , FILE_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, "폴더를 삭제 할 수 없습니다.")

    // Notice
    , NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다.")
    ;
    private HttpStatus status;
    private String message;
}
