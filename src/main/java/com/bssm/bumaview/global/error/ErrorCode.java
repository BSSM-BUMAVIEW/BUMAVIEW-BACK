package com.bssm.bumaview.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR,"001", "business exception test"),

    // Certification & & Authorization
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),

    //User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "존재하지 않는 유저입니다."),

    //Question
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Q-001", "존재하지 않는 질문입니다."),
    QUESTION_FORBIDDEN(HttpStatus.FORBIDDEN, "Q-002", "본인이 작성한 질문만 수정/삭제할 수 있습니다."),

    //Mail
    MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "M-001", "메일 발송 실패"),

    //Subscription
    SUBSCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "S-001", "구독 정보를 찾을 수 없습니다."),
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
