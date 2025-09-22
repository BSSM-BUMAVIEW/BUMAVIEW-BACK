package com.bssm.bumaview.domain.answer.application.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.NotFoundException;

public class AnswerNotFoundException extends NotFoundException {
    public static AnswerNotFoundException EXCEPTION = new AnswerNotFoundException();

    private AnswerNotFoundException() {
        super(ErrorCode.ANSWER_NOT_FOUND);
    }
}
