package com.bssm.bumaview.domain.like.application.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.NotFoundException;

public class LikeNotFoundException extends NotFoundException {
  public static LikeNotFoundException EXCEPTION = new LikeNotFoundException();

  private LikeNotFoundException() {
    super(ErrorCode.LIKE_NOT_FOUND);
  }
}
