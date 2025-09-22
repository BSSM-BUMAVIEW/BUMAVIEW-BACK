package com.bssm.bumaview.domain.like.presentation;

import com.bssm.bumaview.domain.like.application.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{answerId}")
    public ResponseEntity<String> likeAnswer(
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        likeService.likeAnswer(userId, answerId);
        return ResponseEntity.ok("좋아요 ");
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<String> unlikeAnswer(
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        likeService.unlikeAnswer(userId, answerId);
        return ResponseEntity.ok("좋아요 취소");
    }
}
