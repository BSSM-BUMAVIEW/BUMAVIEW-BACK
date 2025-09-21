package com.bssm.bumaview.domain.company.presentation;

import com.bssm.bumaview.domain.company.application.CompanyService;
import com.bssm.bumaview.domain.company.application.dto.CompanyResponse;
import com.bssm.bumaview.domain.company.presentation.dto.CompanyRequest;
import com.bssm.bumaview.domain.question.application.dto.QuestionResponse;
import com.bssm.bumaview.domain.question.presentation.dto.QuestionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
