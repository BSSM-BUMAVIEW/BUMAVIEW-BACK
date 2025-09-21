package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = Question.of(
                questionRequest.userId(),
                questionRequest.companyId(),
                questionRequest.content(),
                questionRequest.category(),
                questionRequest.questionAt()
        );
        Question saved = questionRepository.save(question);
        return QuestionResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(QuestionResponse::from)
                .toList();
    }

    @Transactional
    public void deleteQuestion(Long questionId, Long currentUserId, String role) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
        questionRepository.delete(question);
    }
}
