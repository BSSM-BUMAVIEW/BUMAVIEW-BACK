package com.bssm.bumaview.domain.user.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public static UserNotFoundException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
