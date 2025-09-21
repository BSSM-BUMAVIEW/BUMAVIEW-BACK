package com.bssm.bumaview.domain.question.presentation;

import com.bssm.bumaview.domain.question.application.QuestionService;
import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestBody QuestionRequest questionRequest) {

        QuestionResponse questionResponse = questionService.createQuestion(questionRequest);

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
}
