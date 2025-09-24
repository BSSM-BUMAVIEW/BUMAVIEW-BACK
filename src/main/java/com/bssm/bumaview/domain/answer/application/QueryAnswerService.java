package com.bssm.bumaview.domain.answer.application;

import com.bssm.bumaview.domain.answer.application.dto.AnswerResponse;
import com.bssm.bumaview.domain.answer.application.exception.AnswerNotFoundException;
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
}
