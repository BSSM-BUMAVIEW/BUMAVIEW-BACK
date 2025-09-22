package com.bssm.bumaview.domain.answer.presentation.dto;

public record AnswerRequest(
        Long questionId,
        String content
) {
}
