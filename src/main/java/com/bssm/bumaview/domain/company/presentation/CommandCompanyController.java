package com.bssm.bumaview.domain.company.presentation;

import com.bssm.bumaview.domain.company.application.CommandCompanyService;
import com.bssm.bumaview.domain.company.application.dto.CompanyResponse;
import com.bssm.bumaview.domain.company.presentation.dto.CompanyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CommandCompanyController {

    private final CommandCompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest companyRequest
    ) {

        CompanyResponse companyResponse = companyService.createCompany(companyRequest);

        return ResponseEntity.ok(companyResponse);
    }
}
