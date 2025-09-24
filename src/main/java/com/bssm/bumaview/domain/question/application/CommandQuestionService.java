package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.company.domain.Company;
import com.bssm.bumaview.domain.company.domain.repository.CompanyRepository;
import com.bssm.bumaview.domain.question.presentation.dto.response.QuestionResponse;
import com.bssm.bumaview.domain.question.exception.QuestionForbiddenException;
import com.bssm.bumaview.domain.question.exception.QuestionNotFoundException;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionRequest;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionUpdateRequest;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;

@CustomService
@RequiredArgsConstructor
public class CommandQuestionService {

    private final QuestionRepository questionRepository;
    //리펙터링 필요
    private final CompanyRepository companyRepository;

    public QuestionResponse createQuestion(Long userId, QuestionRequest questionRequest) {

        //리펙터링 필요
        Company company = companyRepository.findByName(questionRequest.companyName())
                .orElseThrow();

        Question question = Question.of(
                userId,
                company,
                questionRequest.content(),
                questionRequest.category(),
                questionRequest.questionAt()
        );
        Question saved = questionRepository.save(question);
        return QuestionResponse.from(saved);
    }

    public void deleteQuestion(Long questionId, Long userId, String role) {
        Question question = getAuthorizedQuestion(questionId, userId, role);

        questionRepository.delete(question);
    }

    public QuestionResponse updateQuestion(Long questionId, QuestionUpdateRequest request, Long userId, String role) {

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
