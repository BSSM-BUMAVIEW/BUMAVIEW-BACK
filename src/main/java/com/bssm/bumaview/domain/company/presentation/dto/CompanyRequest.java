package com.bssm.bumaview.domain.company.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(

        @NotBlank(message = "회사 이름 필수입니다.")
        String name,

        String logoUrl
) {
}
