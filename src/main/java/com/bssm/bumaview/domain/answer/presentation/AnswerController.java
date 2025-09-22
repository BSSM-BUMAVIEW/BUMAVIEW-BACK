package com.bssm.bumaview.domain.answer.presentation;

import com.bssm.bumaview.domain.answer.application.AnswerService;
import com.bssm.bumaview.domain.answer.application.dto.AnswerResponse;
import com.bssm.bumaview.domain.answer.presentation.dto.AnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponse> createAnswer(
            @RequestBody AnswerRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        AnswerResponse response = answerService.createAnswer(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top")
    public ResponseEntity<List<AnswerResponse>> getAnswersOrderByLikes() {
        return ResponseEntity.ok(answerService.getAnswersOrderByLikes());
    }
}
