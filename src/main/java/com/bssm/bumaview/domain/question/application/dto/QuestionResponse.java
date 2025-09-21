package com.bssm.bumaview.domain.question.application.dto;

import com.bssm.bumaview.domain.question.domain.Question;

public record QuestionResponse(
        Long id,
        Long userId,
        Long companyId,
        String content,
        String category,
        String questionAt
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getUserId(),
                question.getCompanyId(),
                question.getContent(),
                question.getCategory(),
                question.getQuestionAt()
        );
    }
}
