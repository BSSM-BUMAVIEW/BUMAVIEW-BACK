package com.bssm.bumaview.global.error.exception;

import com.bssm.bumaview.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
