package com.bssm.bumaview.domain.company.application;

import com.bssm.bumaview.domain.company.application.dto.CompanyResponse;
import com.bssm.bumaview.domain.company.domain.repository.CompanyRepository;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@CustomService(readOnly = true)
@RequiredArgsConstructor
public class QueryCompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyResponse::from)
                .toList();
    }
}
