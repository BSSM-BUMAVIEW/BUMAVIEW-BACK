package com.bssm.bumaview.domain.question.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequest(

        @NotNull (message = "회사 이름는 필수입니다.")
        String companyName,

        @NotBlank (message = "질문 내용은 필수입니다.")
        String content,

        @NotBlank (message = "카테고리는 필수입니다.")
        String category,

        @NotBlank (message = "질문 연도는 필수입니다.")
        String questionAt
) {}