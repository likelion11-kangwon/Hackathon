package back.ailion.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseExceptionCode {
    // 400 BAD REQUEST
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    AUTHORIZATION_HEADER_NULL(HttpStatus.BAD_REQUEST.value(), "인증 헤더가 null입니다."),

    // 403 FORBIDDEN
    NOT_YOUR_POST(HttpStatus.FORBIDDEN.value(), "작성자가 아닙니다."),

    // 409 CONFLICT
    USER_ID_CONFLICT(HttpStatus.CONFLICT.value(), "아이디가 중복입니다."),

    // 404 NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않습니다."),

    POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),  "게시글이 존재하지 없습니다."),

    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),  "댓글이 존재하지 없습니다."),

    REPLY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),  "대댓글이 존재하지 없습니다."),

    HEART_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),  "좋아요 및 취소를 할 수 없습니다."),

    AI_INFO_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),  "해당 AI 정보가 존재하지 않습니다."),

    FAVORITE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),  "즐겨찾기 및 취소를 할 수 없습니다."),

    ALREADY_LIKED(HttpStatus.BAD_REQUEST.value(),  "이미 공감한 글입니다."),

    FILE_SIZE_EXCEED(HttpStatus.BAD_REQUEST.value(), "업로드 할 수 있는 파일의 최대 크기는 20MB 입니다."),

    EMPTY_FILE(HttpStatus.BAD_REQUEST.value(), "파일이 비어있습니다. 다시 파일을 첨부해주세요"),

    MISSING_FILE(HttpStatus.BAD_REQUEST.value(), "파일이 없습니다. 파일을 첨부해주세요"),

    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST.value(), "파일 업로드에 실패했습니다."),

    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "파일을 찾을 수 없습니다."),

    FILE_UPLOAD_EXCEEDED(HttpStatus.BAD_REQUEST.value(), "파일 업로드 개수는 10개 이하여야 합니다."),

    ALREADY_FAVORITE(HttpStatus.BAD_REQUEST.value(), "이미 즐겨찾기한 AI 입니다."),

    NON_POSITIVE_ID(HttpStatus.BAD_REQUEST.value(), "null");


    private final int httpStatusCode;
    private final String message;

    BaseExceptionCode(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

}
