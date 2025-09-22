package com.bssm.bumaview.domain.subscription.application.mail.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.BusinessException;

public class MailSendFailException extends BusinessException {
    public static MailSendFailException EXCEPTION = new MailSendFailException();

    private MailSendFailException() {
        super(ErrorCode.MAIL_SEND_FAIL);
    }
}
