package com.bssm.bumaview.domain.question.presentation;

import com.bssm.bumaview.domain.question.application.QuestionService;
import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.QuestionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @Valid @RequestBody QuestionRequest questionRequest,
            @AuthenticationPrincipal UserDetails user
    ) {

        Long userId = Long.valueOf(user.getUsername());
        QuestionResponse questionResponse = questionService.createQuestion(userId, questionRequest);

        return ResponseEntity.ok(questionResponse);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        List<QuestionResponse> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/question-at-search")
    public ResponseEntity<List<QuestionResponse>> searchByQuestionAt(@RequestParam String questionAt) {
        List<Question> questions = questionRepository.findByQuestionAt(questionAt);
        return ResponseEntity.ok(questions.stream().map(QuestionResponse::from).toList());
    }

    @GetMapping("/category-search")
    public ResponseEntity<List<QuestionResponse>> searchByCategory(@RequestParam String category) {
        List<Question> questions = questionRepository.findByCategory(category);
        return ResponseEntity.ok(questions.stream().map(QuestionResponse::from).toList());
    }

    @GetMapping("/company-search")
    public ResponseEntity<List<QuestionResponse>> searchByCompany(@RequestParam Long companyId) {
        List<Question> questions = questionRepository.findByCompanyId(companyId);
        return ResponseEntity.ok(questions.stream().map(QuestionResponse::from).toList());
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
            @Valid @RequestBody QuestionRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        String role = user.getAuthorities().stream()
                .findFirst().get().getAuthority();

        QuestionResponse updated = questionService.updateQuestion(id, request, userId, role);
        return ResponseEntity.ok(updated);
    }


}
