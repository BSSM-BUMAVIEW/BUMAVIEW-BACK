package com.bssm.bumaview.global.error.exception;

import com.bssm.bumaview.global.error.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }
}
