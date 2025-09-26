package com.bssm.bumaview.domain.company.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.BusinessException;

public class CompanyAlreadyExistsException extends BusinessException {
  public static CompanyAlreadyExistsException EXCEPTION = new CompanyAlreadyExistsException();

  private CompanyAlreadyExistsException() {
    super(ErrorCode.COMPANY_ALREADY_EXISTS);
  }
}
