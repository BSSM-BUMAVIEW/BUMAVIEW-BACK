package com.bssm.bumaview.domain.question.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {

    public static QuestionNotFoundException EXCEPTION = new QuestionNotFoundException();

    private QuestionNotFoundException() {
        super(ErrorCode.QUESTION_NOT_FOUND);
    }
}
