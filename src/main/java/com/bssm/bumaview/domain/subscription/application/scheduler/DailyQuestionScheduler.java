package com.bssm.bumaview.domain.subscription.application.scheduler;

import com.bssm.bumaview.domain.subscription.application.SubscriptionService;
import com.bssm.bumaview.domain.subscription.domain.MailSubscription;
import com.bssm.bumaview.domain.subscription.domain.repository.MailSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyQuestionScheduler {
    private final MailSubscriptionRepository subscriptionRepo;
    private final SubscriptionService subscriptionService;

            //(cron = "*/10 * * * * *", zone = "Asia/Seoul") 10초 마다
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul") // 매일 09:00
    public void sendDaily() {
        List<MailSubscription> subs = subscriptionRepo.findAllByActiveTrue();
        subs.forEach(subscriptionService::sendDailyQuestionTo);
    }
}
