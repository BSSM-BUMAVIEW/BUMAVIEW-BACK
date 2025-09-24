package com.bssm.bumaview.domain.question.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record QuestionUpdateRequest(

        @NotBlank(message = "질문 내용은 필수입니다.")
        String content,

        @NotBlank (message = "카테고리는 필수입니다.")
        String category,

        @NotBlank (message = "질문 연도는 필수입니다.")
        String questionAt
) {
}
