package com.bssm.bumaview.domain.answer.presentation;

import com.bssm.bumaview.domain.answer.application.CommandAnswerService;
import com.bssm.bumaview.domain.answer.application.QueryAnswerService;
import com.bssm.bumaview.domain.answer.application.dto.AnswerResponse;
import com.bssm.bumaview.domain.answer.presentation.dto.AnswerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answers")
@RequiredArgsConstructor
public class QueryAnswerController {

    private final QueryAnswerService queryAnswerService;

    @GetMapping("/top")
    public ResponseEntity<List<AnswerResponse>> getAnswersOrderByLikes() {
        return ResponseEntity.ok(queryAnswerService.getAnswersOrderByLikes());
    }
}
