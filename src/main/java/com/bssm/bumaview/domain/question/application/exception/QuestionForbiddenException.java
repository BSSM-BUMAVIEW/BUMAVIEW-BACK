package com.bssm.bumaview.domain.question.application.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.BusinessException;

public class QuestionForbiddenException extends BusinessException {
    public static QuestionForbiddenException EXCEPTION = new QuestionForbiddenException();

    private QuestionForbiddenException() {
        super(ErrorCode.QUESTION_FORBIDDEN);
    }
}
