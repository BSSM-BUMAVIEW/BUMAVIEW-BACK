package com.bssm.bumaview.domain.subscription.presentation;

import com.bssm.bumaview.domain.subscription.application.SubscriptionService;
import com.bssm.bumaview.domain.subscription.presentation.dto.SubscribeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Void> subscribe(@RequestBody @Valid SubscribeRequest subscribeRequest,
                                          @AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        subscriptionService.subscribe(userId, subscribeRequest.category());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(@RequestParam String category,
                                            @AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        subscriptionService.unsubscribe(userId, category);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send-now")
    public ResponseEntity<Void> sendDailyNow(@AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        subscriptionService.sendDailyQuestionNow(userId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/me")
    public ResponseEntity<?> mySubs(@AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        return ResponseEntity.ok(subscriptionService.mySubscriptions(userId));
    }
}