package com.bssm.bumaview.domain.subscription.presentation;

import com.bssm.bumaview.domain.subscription.application.QuerySubscriptionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class QuerySubscriptionController {

    private final QuerySubscriptionService querySubscriptionService;

    @GetMapping("/me")
    public ResponseEntity<?> mySubs(@AuthenticationPrincipal UserDetails user) {
        Long userId = Long.valueOf(user.getUsername());
        return ResponseEntity.ok(querySubscriptionService.mySubscriptions(userId));
    }
}
