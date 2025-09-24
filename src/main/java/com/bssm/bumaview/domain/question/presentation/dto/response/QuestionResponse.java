package com.bssm.bumaview.domain.question.presentation.dto.response;

import com.bssm.bumaview.domain.question.domain.Question;

public record QuestionResponse(
        Long id,
        Long userId,
        String content,
        String category,
        String questionAt
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getUserId(),
                question.getContent(),
                question.getCategory(),
                question.getQuestionAt()
        );
    }
}
