package com.bssm.bumaview.domain.question.presentation.dto;

public record QuestionRequest(
        Long userId,
        Long companyId,
        String content
) {}