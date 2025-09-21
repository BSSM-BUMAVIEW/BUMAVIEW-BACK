package com.bssm.bumaview.domain.company.presentation;

import com.bssm.bumaview.domain.company.application.CompanyService;
import com.bssm.bumaview.domain.company.application.dto.CompanyResponse;
import com.bssm.bumaview.domain.company.presentation.dto.CompanyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest companyRequest
    ) {

        CompanyResponse companyResponse = companyService.createCompany(companyRequest);

        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompany() {

        List<CompanyResponse> companies = companyService.getAllCompanies();

        return ResponseEntity.ok(companies);
    }
}
