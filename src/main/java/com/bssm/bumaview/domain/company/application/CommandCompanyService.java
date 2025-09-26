package com.bssm.bumaview.domain.company.application;

import com.bssm.bumaview.domain.company.exception.CompanyAlreadyExistsException;
import com.bssm.bumaview.domain.company.presentation.dto.response.CompanyResponse;
import com.bssm.bumaview.domain.company.domain.Company;
import com.bssm.bumaview.domain.company.domain.repository.CompanyRepository;
import com.bssm.bumaview.domain.company.presentation.dto.request.CompanyRequest;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;

@CustomService
@RequiredArgsConstructor
public class CommandCompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse createCompany(CompanyRequest companyRequest) {

        if (companyRepository.existsByName(companyRequest.name())) {
            throw CompanyAlreadyExistsException.EXCEPTION;
        }

        Company company = Company.of(
                companyRequest.name(),
                companyRequest.logoUrl()
        );
        Company saved = companyRepository.save(company);
        return CompanyResponse.from(saved);
    }
}
