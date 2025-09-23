package com.bssm.bumaview.domain.subscription.application;

import com.bssm.bumaview.domain.subscription.domain.MailSubscription;
import com.bssm.bumaview.domain.subscription.domain.repository.MailSubscriptionRepository;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@CustomService(readOnly = true)
@RequiredArgsConstructor
public class QuerySubscriptionService {

    private final MailSubscriptionRepository mailSubscriptionRepository;

    public List<MailSubscription> mySubscriptions(Long loginUserId) {
        return mailSubscriptionRepository.findAllByUserId(loginUserId);
    }
}
