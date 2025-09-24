package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.question.presentation.dto.response.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@CustomService(readOnly = true)
@RequiredArgsConstructor
public class QueryQuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(QuestionResponse::from)
                .toList();
    }
}
