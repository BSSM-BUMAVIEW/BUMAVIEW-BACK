package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = Question.of(
                questionRequest.userId(),
                questionRequest.companyId(),
                questionRequest.content()
        );
        Question saved = questionRepository.save(question);
        return QuestionResponse.from(saved);
    }

}
