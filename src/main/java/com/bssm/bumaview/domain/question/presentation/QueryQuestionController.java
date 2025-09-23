package com.bssm.bumaview.domain.question.presentation;

import com.bssm.bumaview.domain.question.application.QueryQuestionService;
import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QueryQuestionController {

    private final QueryQuestionService queryQuestionService;
    private final QuestionRepository questionRepository;

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        List<QuestionResponse> questions = queryQuestionService.getAllQuestions();
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

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(questionRepository.count());
    }

    @GetMapping("/count/category")
    public ResponseEntity<Long> countByCategory(@RequestParam String category) {
        Long count = questionRepository.countByCategory(category);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/question-at")
    public ResponseEntity<Long> countByQuestionAt(@RequestParam String questionAt) {
        Long count = questionRepository.countByQuestionAt(questionAt);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/company")
    public ResponseEntity<Long> countByCompanyId(@RequestParam Long companyId) {
        Long count = questionRepository.countByCompanyId(companyId);
        return ResponseEntity.ok(count);
    }
}
