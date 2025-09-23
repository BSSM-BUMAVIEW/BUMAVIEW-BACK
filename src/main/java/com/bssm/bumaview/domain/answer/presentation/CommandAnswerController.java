package com.bssm.bumaview.domain.answer.presentation;

import com.bssm.bumaview.domain.answer.application.CommandAnswerService;
import com.bssm.bumaview.domain.answer.application.dto.AnswerResponse;
import com.bssm.bumaview.domain.answer.presentation.dto.AnswerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answers")
@RequiredArgsConstructor
public class CommandAnswerController {

    private final CommandAnswerService commandAnswerService;

    @PostMapping
    public ResponseEntity<AnswerResponse> createAnswer(
            @RequestBody @Valid AnswerRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        AnswerResponse response = commandAnswerService.createAnswer(userId, request);
        return ResponseEntity.ok(response);
    }
}
