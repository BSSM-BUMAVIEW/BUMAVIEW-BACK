package com.bssm.bumaview.domain.company.presentation;

import com.bssm.bumaview.domain.company.application.CommandCompanyService;
import com.bssm.bumaview.domain.company.presentation.dto.response.CompanyResponse;
import com.bssm.bumaview.domain.company.presentation.dto.request.CompanyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CommandCompanyController {

    private final CommandCompanyService commandCompanyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest companyRequest
    ) {

        CompanyResponse companyResponse = commandCompanyService.createCompany(companyRequest);

        return ResponseEntity.ok(companyResponse);
    }
}
