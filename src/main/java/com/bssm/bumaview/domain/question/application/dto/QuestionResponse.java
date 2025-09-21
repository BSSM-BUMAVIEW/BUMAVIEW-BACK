package com.bssm.bumaview.domain.question.application.dto;

import com.bssm.bumaview.domain.question.domain.Question;

public record QuestionResponse(
        Long id,
        Long userId,
        String companyName,
        String content,
        String category,
        String questionAt
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getUserId(),
                question.getCompany().getName(),
                question.getContent(),
                question.getCategory(),
                question.getQuestionAt()
        );
    }
}
