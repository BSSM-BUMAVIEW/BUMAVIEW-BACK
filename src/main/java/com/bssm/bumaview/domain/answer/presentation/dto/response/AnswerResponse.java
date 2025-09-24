package com.bssm.bumaview.domain.answer.presentation.dto.response;

import com.bssm.bumaview.domain.answer.domain.Answer;

public record AnswerResponse(

        Long id,
        Long userId,
        Long questionId,
        String content

) {
    public static AnswerResponse from(Answer answer) {

        return new AnswerResponse(
                answer.getId(),
                answer.getUser().getId(),
                answer.getQuestion().getId(),
                answer.getContent());
    }
}
