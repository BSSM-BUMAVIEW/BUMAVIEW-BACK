package com.bssm.bumaview.domain.company.application;

import com.bssm.bumaview.domain.company.application.dto.CompanyResponse;
import com.bssm.bumaview.domain.company.domain.Company;
import com.bssm.bumaview.domain.company.domain.repository.CompanyRepository;
import com.bssm.bumaview.domain.company.presentation.dto.CompanyRequest;
import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponse createCompany(CompanyRequest companyRequest) {

        Company company = Company.of(
                companyRequest.name(),
                companyRequest.logoUrl()
        );
        Company saved = companyRepository.save(company);
        return CompanyResponse.from(saved);
    }
}
