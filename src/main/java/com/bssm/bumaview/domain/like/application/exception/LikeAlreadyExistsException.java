package com.bssm.bumaview.domain.like.application.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.BusinessException;

public class LikeAlreadyExistsException extends BusinessException {
    public static LikeAlreadyExistsException EXCEPTION = new LikeAlreadyExistsException();

    private LikeAlreadyExistsException() {
        super(ErrorCode.LIKE_ALREADY_EXISTS);
    }
}
