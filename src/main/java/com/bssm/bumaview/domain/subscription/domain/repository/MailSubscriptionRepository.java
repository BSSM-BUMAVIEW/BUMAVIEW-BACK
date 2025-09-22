package com.bssm.bumaview.domain.subscription.domain.repository;

import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.subscription.domain.MailSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailSubscriptionRepository extends JpaRepository<MailSubscription, Long> {

    List<MailSubscription> findAllByUserId(Long userId);

    Optional<MailSubscription> findByUserIdAndCategory(Long userId, String category);

    List<MailSubscription> findAllByActiveTrue();

}