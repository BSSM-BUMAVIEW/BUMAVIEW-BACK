package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.application.exception.QuestionForbiddenException;
import com.bssm.bumaview.domain.question.application.exception.QuestionNotFoundException;
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
    public QuestionResponse createQuestion(Long userId, QuestionRequest questionRequest) {
        Question question = Question.of(
                userId,
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
    public void deleteQuestion(Long questionId, Long userId, String role) {
        Question question = getAuthorizedQuestion(questionId, userId, role);

        questionRepository.delete(question);
    }

    @Transactional
    public QuestionResponse updateQuestion(Long questionId, QuestionRequest request, Long userId, String role) {
        Question question = getAuthorizedQuestion(questionId, userId, role);
        question.update(request.content(), request.category(), request.questionAt());
        return QuestionResponse.from(question);
    }

    private Question getAuthorizedQuestion(Long questionId, Long userId, String role) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> QuestionNotFoundException.EXCEPTION);

        if (!question.getUserId().equals(userId) && !"ADMIN".equals(role)) {
            throw QuestionForbiddenException.EXCEPTION;
        }
        return question;
    }

}
