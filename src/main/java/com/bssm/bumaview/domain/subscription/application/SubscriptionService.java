package com.bssm.bumaview.domain.subscription.application;

import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.subscription.application.exception.SubscriptionNotFoundException;
import com.bssm.bumaview.domain.subscription.application.mail.MailService;
import com.bssm.bumaview.domain.subscription.domain.MailSubscription;
import com.bssm.bumaview.domain.subscription.domain.repository.MailSubscriptionRepository;
import com.bssm.bumaview.domain.user.domain.User;
import com.bssm.bumaview.domain.user.domain.exception.UserNotFoundException;
import com.bssm.bumaview.domain.user.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final MailSubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;           // 유저 이메일 조회용
    private final QuestionRepository questionRepository;
    private final MailService mailService;

    @Transactional
    public void subscribe(Long loginUserId, String category) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        MailSubscription sub = subscriptionRepository.findByUserIdAndCategory(loginUserId, category)
                .orElseGet(() -> MailSubscription.builder()
                        .userId(loginUserId)
                        .email(user.getEmail())
                        .category(category)
                        .active(true)
                        .build());

        sub.activate();
        subscriptionRepository.save(sub);
    }

    @Transactional
    public void unsubscribe(Long loginUserId, String category) {
        MailSubscription sub = subscriptionRepository.findByUserIdAndCategory(loginUserId, category)
                .orElseThrow(() -> SubscriptionNotFoundException.EXCEPTION);
        sub.deactivate();
        subscriptionRepository.save(sub);
    }

    @Transactional(readOnly = true)
    public List<MailSubscription> mySubscriptions(Long loginUserId) {
        return subscriptionRepository.findAllByUserId(loginUserId);
    }

    /**
     * 매일 보낼 때 사용
     */
    public void sendDailyQuestionTo(MailSubscription sub) {
        questionRepository.findRandomByCategory(sub.getCategory())
                .ifPresent(q -> {
                    String subject = "[BumaView] 오늘의 면접 질문";
                    String body = """
                            카테고리: %s \n
                            질문: %s
                            """.formatted(q.getCategory(), q.getContent());
                    mailService.sendSimple(sub.getEmail(), subject, body);
                });
    }
}