package com.bssm.bumaview.domain.subscription.application.exception;

import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.NotFoundException;

public class SubscriptionNotFoundException extends NotFoundException {
  public static SubscriptionNotFoundException EXCEPTION = new SubscriptionNotFoundException();

  private SubscriptionNotFoundException() {
    super(ErrorCode.SUBSCRIPTION_NOT_FOUND);
  }
}
