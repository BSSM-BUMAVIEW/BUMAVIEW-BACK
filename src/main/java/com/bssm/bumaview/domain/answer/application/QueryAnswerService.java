package com.bssm.bumaview.domain.answer.application;

import com.bssm.bumaview.domain.answer.presentation.dto.response.AnswerResponse;
import com.bssm.bumaview.domain.answer.exception.AnswerNotFoundException;
import com.bssm.bumaview.domain.answer.domain.Answer;
import com.bssm.bumaview.domain.answer.domain.repository.AnswerRepository;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@CustomService(readOnly = true)
@RequiredArgsConstructor
public class QueryAnswerService {

    private final AnswerRepository answerRepository;

    public List<AnswerResponse> getAnswersOrderByLikes() {
        List<Answer> answers = answerRepository.findAllOrderByLikeCountDesc();
        return answers.stream()
                .map(AnswerResponse::from)
                .toList();
    }

    public AnswerResponse getMostLikedAnswer() {
        return answerRepository.findAllOrderByLikeCountDesc()
                .stream()
                .findFirst()
                .map(AnswerResponse::from)
                .orElseThrow(() -> AnswerNotFoundException.EXCEPTION);
    }

    public List<AnswerResponse> getAnswersByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionIdOrderByIdDesc(questionId);

        if (answers.isEmpty()) {
            throw AnswerNotFoundException.EXCEPTION;
        }

        return answers.stream()
                .map(AnswerResponse::from)
                .toList();
    }

}
