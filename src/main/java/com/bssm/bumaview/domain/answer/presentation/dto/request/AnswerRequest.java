package com.bssm.bumaview.domain.answer.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerRequest(

        @NotNull(message = "질문 ID는 필수입니다.")
        Long questionId,

        @NotBlank(message = "답변 내용은 필수입니다.")
        String content
) {
}
