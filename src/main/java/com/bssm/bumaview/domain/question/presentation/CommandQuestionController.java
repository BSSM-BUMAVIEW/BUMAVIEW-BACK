package com.bssm.bumaview.domain.question.presentation;

import com.bssm.bumaview.domain.question.application.CommandQuestionService;
import com.bssm.bumaview.domain.question.presentation.dto.response.QuestionResponse;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionRequest;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class CommandQuestionController {

    private final CommandQuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @Valid @RequestBody QuestionRequest questionRequest,
            @AuthenticationPrincipal UserDetails user
    ) {

        Long userId = Long.valueOf(user.getUsername());
        QuestionResponse questionResponse = questionService.createQuestion(userId, questionRequest);

        return ResponseEntity.ok(questionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        String role = user.getAuthorities().stream()
                .findFirst().get().getAuthority();

        questionService.deleteQuestion(id, userId, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionUpdateRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        String role = user.getAuthorities().stream()
                .findFirst().get().getAuthority();

        QuestionResponse updated = questionService.updateQuestion(id, request, userId, role);
        return ResponseEntity.ok(updated);
    }
}
