package com.bssm.bumaview.domain.company.presentation.dto.response;

import com.bssm.bumaview.domain.company.domain.Company;

public record CompanyResponse(

        Long id,
        String name,
        String logoUrl
) {
    public static CompanyResponse from(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getLogoUrl()
        );
    }
}
