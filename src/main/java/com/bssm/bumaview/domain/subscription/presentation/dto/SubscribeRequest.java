package com.bssm.bumaview.domain.subscription.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record SubscribeRequest(

        @NotBlank (message = "카테고리는 필수입니다.")
        String category
) { }
