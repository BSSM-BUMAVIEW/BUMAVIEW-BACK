package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.application.exception.QuestionForbiddenException;
import com.bssm.bumaview.domain.question.application.exception.QuestionNotFoundException;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.QuestionRequest;
import com.bssm.bumaview.global.error.ErrorCode;
import com.bssm.bumaview.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
        Question question = getAuthorizedQuestion(questionId, currentUserId, role);

        questionRepository.delete(question);
    }

    @Transactional
    public QuestionResponse updateQuestion(Long id, QuestionRequest request, Long userId, String role) {
        Question question = getAuthorizedQuestion(id, userId, role);
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
