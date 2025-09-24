package com.bssm.bumaview.domain.company.presentation;

import com.bssm.bumaview.domain.company.application.QueryCompanyService;
import com.bssm.bumaview.domain.company.presentation.dto.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class QueryCompanyController {

    private final QueryCompanyService queryCompanyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompany() {

        List<CompanyResponse> companies = queryCompanyService.getAllCompanies();

        return ResponseEntity.ok(companies);
    }
}
